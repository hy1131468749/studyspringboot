package com.glodio.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glodio.bean.ConnectPlatform;
import com.glodio.bean.ConnectType;
import com.glodio.bean.Device;
import com.glodio.bean.User;
import com.glodio.bean.device.CmdGCEntity;
import com.glodio.bean.device.CmdGCMap;
import com.glodio.bean.device.CmdMCEntity;
import com.glodio.bean.device.CmdMCEntityDatas;
import com.glodio.bean.device.CmdMCMap;
import com.glodio.hongguan.OnenetHGDeviceService;
import com.glodio.onenet.OnenetDeviceService;
import com.glodio.onenet.bean.DeviceAddResponse;
import com.glodio.onenet.bean.DeviceAddResponse.DeviceAddInfo;
import com.glodio.onenet.bean.DeviceInfoRequest;
import com.glodio.onenet.bean.DeviceInfoRequest.Location;
import com.glodio.service.ConnectTypeService;
import com.glodio.service.IConnectPlatformService;
import com.glodio.service.IDDeviceHWNBOperationService;
import com.glodio.service.IDDeviceNokiaNBOperationService;
import com.glodio.service.IDeviceService;
import com.glodio.util.ConncetTypeNumber;
import com.glodio.util.LogFile;
import com.glodio.util.OldETreeNode;

@Controller
@RequestMapping("/system/station")
public class DeviceManagerController {
	@Autowired
	IDeviceService iDeviceService;

	@Autowired
	IDDeviceNokiaNBOperationService idDeviceNokiaNBOperationService;

	@Autowired
	IDDeviceHWNBOperationService idDeviceHWNBOperationService;

	@Autowired
	private ConnectTypeService connectTypeService;

	@Autowired
	private IConnectPlatformService iConnectPlatformService;

	private Integer CONNECTTYPE_HUAIWEI = ConncetTypeNumber.CONNECTTYPE_HUAIWEI;
	private Integer CONNECTTYPE_UDP = ConncetTypeNumber.CONNECTTYPE_UDP;
	private Integer CONNECTTYPE_NOKIA = ConncetTypeNumber.CONNECTTYPE_NOKIA;
	private Integer CONNECTTYPE_ONENET = ConncetTypeNumber.CONNECTTYPE_ONENET;
	private Integer CONNECTTYPE_SMOKEDETECTOR = ConncetTypeNumber.CONNECTTYPE_SMOKEDETECTOR;

	/**
	 * 目前只能查询本部门下的设备，如果需要查询部门和部门一下的可以修改
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deviceList")
	@ResponseBody
	public Map<String, Object> deviceList(HttpServletRequest request,
			@RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		User user = getUser(request);
		if(user == null){
			return dataMap;
		}
			
		params.put("orgId", user.getOrgId());
		PageHelper.startPage(pageNumber, pageSize);
		ArrayList<Device> list = iDeviceService.queryList(params);
		dataMap.put("deviceList", list);
		long total = ((Page) list).getPages();
		dataMap.put("total", total);
		return dataMap;
	}

	@RequestMapping("/addDevice")
	@ResponseBody
	public Map<String, Object> addDevice(HttpServletRequest request, Device device, Integer typeId) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();
		Map<String, Object> regMap = new HashMap<>();
		boolean result = false;
		// 平台注册状态
		boolean platform = false;
		// 平台名称
		String platformName = "";
		// 数据库存储状态
		boolean dbStatus = false;
		// 设备类型
		String deviceType = null;
		// 华为平台注册返回的id
		String hwNBId = "";
		// 诺基亚平台设备类型
		String identifier = "";
		// 把当前录入的部门传给Id

		Device hasDevice = iDeviceService.queryBean(device.getDeviceId());
		if (hasDevice != null) {
			dataMap.put("result", false);
			dataMap.put("msg", "改设备号数据库已存在");
			return dataMap;
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			device.setOrgId(user.getOrgId());
		}

		switch (device.getDeviceType()) {
		case 0:
			deviceType = "ManholeCover";
			identifier = "GLODIOTECH-COVER-V1";// 井盖
			break;
		case 1:
			deviceType = "Dustbin";
			identifier = "GLODIOTECH-TRASH-V1";// 垃圾箱
			break;
		case 2:
			deviceType = "EnviromentMonitor";
			identifier = "GLODIOTECH-TRASH-V1";// 环保暂用垃圾箱identifier
			break;
		case 3:
			deviceType = "SmokeDetector";
			identifier = "GLODIOTECH-TRASH-V1";// 烟感暂用垃圾箱identifier
			break;

		default:
			break;
		}

		// 向第三方平台注册设备
		if (typeId == CONNECTTYPE_NOKIA) { // 诺基亚平台
			platformName = "Nokia-NBIoT";
			regMap = idDeviceNokiaNBOperationService.addDevice(device.getDeviceId(), identifier,
					device.getConnectPlatform() + "");
			if (Integer.parseInt(regMap.get("flag").toString()) == 0) {
				platform = true;
			}
		} else if (typeId == CONNECTTYPE_UDP) {
			platformName = "UDP-PROTOCOL";
			platform = true;
		} else if (typeId == CONNECTTYPE_HUAIWEI) {// 华为平台
			platformName = "HUAWEI-NBIoT";
			regMap = idDeviceHWNBOperationService.addDevice(device.getDeviceId(), device.getConnectPlatform() + "");
			if (Integer.parseInt(regMap.get("flag").toString()) == 0) {
				hwNBId = regMap.get("msg").toString();
				regMap = idDeviceHWNBOperationService.setDeviceInfor(device.getDeviceId(), hwNBId,
						device.getDeviceType(), device.getConnectPlatform() + "");
				if (Integer.parseInt(regMap.get("flag").toString()) == 0) {
					platform = true;
				}
			}
		} else if (typeId == CONNECTTYPE_ONENET) {
			platformName = "OneNet-NBIoT";
			DeviceInfoRequest deviceInfoRequest = new DeviceInfoRequest();
			deviceInfoRequest.setDesc(device.getRemark());
			deviceInfoRequest.setIsObserve(true);
			Location location = new Location();
			location.setLat(Double.parseDouble(device.getLat()));
			location.setLon(Double.parseDouble(device.getLng()));
			deviceInfoRequest.setTitle(device.getDeviceName());
			deviceInfoRequest.setLocation(location);
			deviceInfoRequest.setProtocol("LWM2M");
			ObjectNode authNode = new ObjectMapper().createObjectNode();
			authNode.put(device.getDeviceId(), device.getDeviceId());
			deviceInfoRequest.setAuthInfo(authNode);
			DeviceAddResponse deviceAddResponse = OnenetDeviceService.addDevice(deviceInfoRequest,
					device.getConnectPlatform() + "");
			if (deviceAddResponse == null || deviceAddResponse.getErrno() != 0) {
				dataMap.put("result", false);
				return dataMap;
			}
			DeviceAddInfo deviceAddInfo = deviceAddResponse.getData();
			if (deviceAddInfo == null) {
				dataMap.put("result", false);
				return dataMap;
			}
			// 暂时使用NbDeviceId代替平台的Id
			device.setNbDeviceId(deviceAddInfo.getDeviceId());
			platform = true;
			regMap.put("flag", 0);
		} else if (typeId == CONNECTTYPE_SMOKEDETECTOR) {

			regMap = registerDevices(device);
			if (Integer.parseInt(regMap.get("flag").toString()) == 0) {
				platform = true;
			}

		}

		// 数据库存储
		if (platform) {
			device.setCreateTime(new Date(System.currentTimeMillis()));
			device.setModifyTime(new Date(System.currentTimeMillis()));
			if (!hwNBId.equals("")) {
				device.setNbDeviceId(hwNBId);
			}
			if (iDeviceService.addBean(device) == 1) {
				dbStatus = true;
				result = true;
			}
		}

		// 设置identifier值
		if (device.getConnectPlatform() != 3) {
			identifier = "无";
		}

		LogFile.devLogger.info(
				"[New device] device namae:[{}],SN:[{}],type:[{}],"
						+ "platform:[{}],identifier:[{}],register result:[{}]," + "to database:[{}]",
				device.getDeviceName(), device.getDeviceId(), deviceType, platformName, identifier,
				device.getConnectPlatform() == 0 ? result
						: regMap.get("flag").toString() + regMap.get("code").toString() + regMap.get("msg").toString(),
				dbStatus);

		dataMap.put("result", result);
		return dataMap;
	}

	@RequestMapping("/delDevice")
	@ResponseBody
	public Map<String, Object> delDevice(HttpServletRequest request, @Param("deviceId") String deviceId)
			throws Exception {
		Map<String, Object> dataMap = new HashMap<>();
		Map<String, Object> delMap = new HashMap<>();
		boolean result = false;
		boolean platform = false;
		// 平台名称
		String platformName = "";
		// 数据库存储状态
		boolean dbStatus = false;

		// 查询设备信息
		Device bean = iDeviceService.queryBean(deviceId);
		ConnectPlatform connectPlatform = iConnectPlatformService.selectByPrimaryKey((long)bean.getConnectPlatform());
		
		// 删除第三方平台信息
		if (bean != null) {
			if (connectPlatform.getTypeId() == CONNECTTYPE_NOKIA ) {
				platformName = "Nokia-NBIoT";
				delMap = idDeviceNokiaNBOperationService.deleteDevice(bean.getDeviceId(),
						bean.getConnectPlatform() + "");
				if (Integer.parseInt(delMap.get("flag").toString()) == 0) {
					platform = true;
				}
			} else if (connectPlatform.getTypeId() == CONNECTTYPE_UDP) {
				platformName = "UDP-PROTOCOL";
				platform = true;
				delMap.put("flag", 0);
				delMap.put("code", "");
				delMap.put("msg", "");
			} else if (connectPlatform.getTypeId() == CONNECTTYPE_HUAIWEI) {
				platformName = "HUAWEI-NBIoT";
				delMap = idDeviceHWNBOperationService.deleteDevice(bean.getNbDeviceId(),
						bean.getConnectPlatform() + "");
				if (Integer.parseInt(delMap.get("flag").toString()) == 0) {
					platform = true;
				}
			} else if(connectPlatform.getTypeId() == CONNECTTYPE_ONENET) {
				platformName = "OneNet-NBIoT";
				OnenetDeviceService.deleteDevice(bean.getNbDeviceId(), bean.getConnectPlatform() + "");
				platform = true;
				delMap.put("flag", 0);
			}else if(connectPlatform.getTypeId() == CONNECTTYPE_SMOKEDETECTOR) {
				platformName = "OneNet-NBIoT";
				// TODO 未对数据进行处理
				//Map<String, Object> unRegisterDevice = SmokeDetectorService.unRegisterDevice(bean.getDeviceId(), bean.getConnectPlatform()+"");
				delMap = OnenetHGDeviceService.deleteDevice(bean.getDeviceId(), bean.getConnectPlatform()+"");
				if("0".equals(delMap.get("flag"))){
				    platform = true;
				}
				
			}
		}

		// 删除数据库中设备信息
		if (platform) {
			if (iDeviceService.deleteBean(deviceId) == 1) {
				dbStatus = true;
				if (CmdGCMap.getObject(deviceId) != null) {
					CmdGCMap.remove(deviceId);
				}

				if (CmdMCMap.getObject(deviceId) != null) {
					CmdMCMap.remove(deviceId);
				}

				result = true;
			}
		}

		LogFile.devLogger.info(
				"[Del device] device namae:[{}],SN:[{}],platform:[{}]," + "result:[{}],from database:[{}]" + "",
				bean.getDeviceName(), bean.getDeviceId(), platformName,
				bean.getConnectPlatform() == 0 ? result
						: delMap.get("flag").toString() + delMap.get("code").toString() + delMap.get("msg").toString(),
				dbStatus);

		dataMap.put("result", result);
		return dataMap;
	}

	@RequestMapping("/getParamForMC")
	@ResponseBody
	public String getParamForMC(HttpServletRequest request, @Param("deviceId") String deviceId) throws Exception {
		CmdMCEntity entity = null;

		if (CmdMCMap.getObject(deviceId) != null) {
			entity = (CmdMCEntity) CmdMCMap.getObject(deviceId);
		} else {
			entity = new CmdMCEntity(deviceId);
		}

		return entity.toString();
	}

	@RequestMapping("/getParamForGC")
	@ResponseBody
	public String getParamForGC(HttpServletRequest request, @Param("deviceId") String deviceId) throws Exception {
		CmdGCEntity entity = null;

		if (CmdGCMap.getObject(deviceId) != null) {
			entity = (CmdGCEntity) CmdGCMap.getObject(deviceId);
		} else {
			entity = new CmdGCEntity(deviceId);
		}

		return entity.toString();
	}

	@RequestMapping("/setParamForMC")
	@ResponseBody
	public Map<String, Object> setParamForMC(HttpServletRequest request, @Param("deviceId") String deviceId,
			@Param("DATAS") CmdMCEntityDatas DATAS) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();

		CmdMCEntity entity = new CmdMCEntity(deviceId);
		entity.setDATAS(DATAS);

		CmdMCMap.add(deviceId, entity);
		boolean result = true;

		dataMap.put("result", result);
		return dataMap;
	}

	@RequestMapping("/setParamForGC")
	@ResponseBody
	public Map<String, Object> setParamForGC(HttpServletRequest request, @Param("deviceId") String deviceId,
			@Param("gcEntity") CmdGCEntity gcEntity) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();

		CmdGCEntity entity = new CmdGCEntity(deviceId);
		entity.setTS(gcEntity.getTS());

		CmdGCMap.add(deviceId, entity);
		boolean result = true;

		dataMap.put("result", result);
		return dataMap;
	}

	private User getUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		return user;
	}

	/**
	 * 查询设备平台和设备协议
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/qyeryTreeList")
	@ResponseBody
	public List<OldETreeNode> qyeryTreeList(@RequestParam(defaultValue = "0", value = "id") Integer parentId) {
		List<OldETreeNode> resultList = new ArrayList<>();
		if (parentId == 0) {
			List<ConnectType> connectTypes = connectTypeService.selectList();
			if (connectTypes != null) {
				OldETreeNode oldETreeNode = null;
				for (ConnectType bean : connectTypes) {
					oldETreeNode = new OldETreeNode();
					oldETreeNode.setOpen(false);
					oldETreeNode.setIsParent(true);
					oldETreeNode.setName(bean.getTypeName());
					oldETreeNode.setId(bean.getId());

					resultList.add(oldETreeNode);

				}
			}
		} else {
			List<ConnectPlatform> connectPlatforms = iConnectPlatformService.selectListByTypeId(parentId);
			if (connectPlatforms != null) {
				OldETreeNode oldETreeNode = null;
				for (ConnectPlatform bean : connectPlatforms) {
					oldETreeNode = new OldETreeNode();
					oldETreeNode.setOpen(false);
					oldETreeNode.setIsParent(false);
					oldETreeNode.setName(bean.getName() + "(" + bean.getIp() + ")");
					oldETreeNode.setId(bean.getId().intValue());
					oldETreeNode.setpId(parentId);
					resultList.add(oldETreeNode);
				}
			}
		}

		return resultList;
	}

	/**
	 * 
	 * @param device
	 * @param type
	 */
	private Map<String, Object> registerDevices(Device device) {
		Map<String, Object> reg =null; 
		try {
			reg = OnenetHGDeviceService.registerDevice(device.getDeviceId(), device.getConnectPlatform()+"");
		}  catch (Exception e) {
			e.printStackTrace();
		}
	    
		return reg;
	}

}

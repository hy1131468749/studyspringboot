package com.glodio.service;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.glodio.bean.ConnectPlatform;
import com.glodio.bean.Device;
import com.glodio.bean.DeviceCover;
import com.glodio.bean.DeviceDustbin;
import com.glodio.bean.DeviceEmgd;
import com.glodio.bean.DeviceSmokeDetector;
import com.glodio.bean.device.EMGDData;
import com.glodio.bean.device.EMGDDataBean;
import com.glodio.bean.device.GCData;
import com.glodio.bean.device.GCDataBean;
import com.glodio.bean.device.GCMap;
import com.glodio.bean.device.MCData;
import com.glodio.bean.device.MCDataBean;
import com.glodio.bean.device.MCMap;
import com.glodio.bean.device.SmokeDetectorData;
import com.glodio.bean.device.SmokeDetectorDataBean;
import com.glodio.serviceimpl.ConnectPlatformServiceImpl;
import com.glodio.util.ConncetTypeNumber;
import com.glodio.util.DeviceTypeNumber;
import com.glodio.util.SmokeDetectorDataType;
import com.glodio.util.StringUtil;

import net.sf.json.JSONObject;

@Service
public class DataHandleService {

	@Resource
	private IDeviceCoverService iDeviceCoverService;
	@Resource
	private IDeviceDustbinService iDeviceDustbinService;
	@Autowired
	private DeviceEmgdService deviceEmgdService;
	@Autowired
	private DeviceSmokeDetectorService deviceSmokeDetectorService;
	@Autowired
	private IConnectPlatformService connectPlatformService;

	public void handleData(Device device, JSONObject json) {
		JSONObject rawData = getRowData(device, json);
		if (rawData == null)
			return;
		switch (device.getDeviceType()) {
		case DeviceTypeNumber.MANHOLECOVER:
			MCDataBean mcDataBean = (MCDataBean) JSONObject.toBean(rawData, MCDataBean.class);
			if (mcDataBean != null) {
				manholecoverHandle(mcDataBean, device);
			}
			break;
		case DeviceTypeNumber.DUSTBIN:
			GCDataBean gcDataBean = (GCDataBean) JSONObject.toBean(rawData, GCDataBean.class);
			if (gcDataBean != null) {
				dustbinHandle(gcDataBean, device);
			}
			break;
		case DeviceTypeNumber.ENVIROMENTMONITOR:
			EMGDDataBean eMGDDataBean = getObjectMapperBean(rawData.toString(), EMGDDataBean.class);
			if (eMGDDataBean != null) {
				emgdHandel(eMGDDataBean, device);
			}
			break;
		case DeviceTypeNumber.SMOKEDETECTOR:
			SmokeDetectorDataBean smokeDetectorDataBean = getObjectMapperBean(rawData.toString(),
					SmokeDetectorDataBean.class);
			if (smokeDetectorDataBean != null) {
				smokeDetectorHandel(smokeDetectorDataBean, device);
			}
			break;
		default:
			break;
		}

	}

	/**
	 * 处理井盖的数据
	 * 
	 * @param mcDataBean
	 * @param device
	 */
	private void manholecoverHandle(MCDataBean mcDataBean, Device device) {
		if (mcDataBean.getSN() != null && mcDataBean.getDATAS().getData6() != null
				&& (mcDataBean.getDATAS().getData7() != null || mcDataBean.getDATAS().getData10() != null)) {
			MCData mcData = compareToMCData(mcDataBean);
			DeviceCover deviceCover = new DeviceCover();
			deviceCover.setIsOpen(mcData.getIsOpen());
			deviceCover.setWaterLevel(mcData.getWaterLine());
			deviceCover.setReportTime(new Date());
			deviceCover.setReportDay(new Date());
			deviceCover.setDeviceId(device.getDeviceId());
			deviceCover.setDeviceName(device.getDeviceName());
			device.setLat(device.getLat());
			device.setLat(device.getLng());
			iDeviceCoverService.addBean(deviceCover);
			MCMap.addData(mcDataBean.getSN(), mcData);
		}
	}

	private void dustbinHandle(GCDataBean gcDataBean, Device device) {
		if (gcDataBean.getSN() != null && gcDataBean.getDT().getFL() != null && gcDataBean.getDT().getFR() != null
				&& gcDataBean.getDT().getSS() != null && gcDataBean.getDT().getBS() != null) {
			GCData gcData = compareToGCData(gcDataBean);
			GCMap.addData(gcDataBean.getSN(), gcData);

			DeviceDustbin deviceDustbin = new DeviceDustbin();
			deviceDustbin.setReportDay(new Date());
			deviceDustbin.setReportTime(new Date());
			deviceDustbin.setFullLeft(gcData.getFullLeft());
			deviceDustbin.setFullRight(gcData.getFullRight());
			deviceDustbin.setSmokeStatus(gcData.getSmokeStatus());
			deviceDustbin.setObliquityStatus(gcData.getObliquityStatus());
			deviceDustbin.setLat(device.getLat());
			deviceDustbin.setLat(device.getLng());
			deviceDustbin.setDeviceId(device.getDeviceId());
			deviceDustbin.setDeviceName(gcData.getDeviceName());
			iDeviceDustbinService.addBean(deviceDustbin);

		}
	}

	private void emgdHandel(EMGDDataBean eMGDDataBean, Device device) {
		if (eMGDDataBean.getSN() != null) {
			EMGDData eMGDData = compareToEMGDData(eMGDDataBean);

			DeviceEmgd deviceEmgd = new DeviceEmgd();

			if (eMGDData.getHUN() != null) {
				deviceEmgd.setHun(eMGDData.getHUN().getV());
			}
			if (eMGDData.getTMP() != null) {
				deviceEmgd.setTmp(eMGDData.getTMP().getV());
			}
			if (deviceEmgd.getP25() != null) {
				deviceEmgd.setP25(eMGDData.getP25().getV());
			}
			if (deviceEmgd.getP10() != null) {
				deviceEmgd.setP10(eMGDData.getPM10().getV());
			}
			deviceEmgd.setReportTime(new Date());
			deviceEmgd.setReportDay(new Date());
			deviceEmgd.setLat(device.getLat());
			deviceEmgd.setLng(device.getLng());
			deviceEmgd.setDeviceId(device.getDeviceId());
			deviceEmgd.setDeviceName(device.getDeviceName());
			deviceEmgdService.insertSelective(deviceEmgd);

		}

	}

	private void smokeDetectorHandel(SmokeDetectorDataBean smokeDetectorDataBean, Device device) {
		if (smokeDetectorDataBean.getSN() != null && smokeDetectorDataBean.getDATAS() != null) {
			SmokeDetectorData smokeDetectorData = compareToSmokeDetectorData(smokeDetectorDataBean);
			boolean isHas = true;
			DeviceSmokeDetector deviceSmokeDetector = new DeviceSmokeDetector();

			if (!StringUtil.strIsNullOrEmpty(smokeDetectorData.getIF1())) {
				deviceSmokeDetector.setIf1(smokeDetectorData.getIF1());
			}
			if (!StringUtil.strIsNullOrEmpty(smokeDetectorData.getIF2())) {
				deviceSmokeDetector.setIf2(smokeDetectorData.getIF2());
			}
			if (!StringUtil.strIsNullOrEmpty(smokeDetectorData.getIF1Status())) {
				deviceSmokeDetector.setIf1Status(Integer.parseInt(smokeDetectorData.getIF1Status()));
			}
			if (!StringUtil.strIsNullOrEmpty(smokeDetectorData.getIF2Status())) {
				deviceSmokeDetector.setIf2Status(Integer.parseInt(smokeDetectorData.getIF2Status()));
			}
			deviceSmokeDetector.setReportDay(new Date());
			deviceSmokeDetector.setReportTime(new Date());
			deviceSmokeDetector.setDeviceId(device.getDeviceId());
			deviceSmokeDetector.setDeviceName(device.getDeviceName());
			deviceSmokeDetector.setLat(device.getLat());
			deviceSmokeDetector.setLng(device.getLng());
			deviceSmokeDetectorService.insertSelective(deviceSmokeDetector);

		}

	}

	private MCData compareToMCData(MCDataBean bean) {
		// 转换对象为前端需要的bean
		MCData mcData = new MCData();
		mcData.setSN(bean.getSN());
		mcData.setIsOnline("在线");
		mcData.setReportTime(new Date(System.currentTimeMillis()));
		mcData.setWaterLine(Integer.parseInt(bean.getDATAS().getData6()) == 1 ? "告警" : "正常");
		mcData.setIsOpen((Integer.parseInt(bean.getDATAS().getData7()) == 1
				|| Integer.parseInt(bean.getDATAS().getData10()) == 1) ? "告警" : "正常");

		return mcData;
	}

	public GCData compareToGCData(GCDataBean bean) {
		// 转换对象为前端需要的bean
		GCData gcData = new GCData();
		gcData.setSN(bean.getSN());
		gcData.setIsOnline("在线");
		gcData.setReportTime(new Date(System.currentTimeMillis()));
		gcData.setFullLeft(Integer.parseInt(bean.getDT().getFL()) == 1 ? "满" : "未满");
		gcData.setFullRight(Integer.parseInt(bean.getDT().getFR()) == 1 ? "满" : "未满");
		gcData.setSmokeStatus(Integer.parseInt(bean.getDT().getSS()) == 1 ? "有" : "无");
		gcData.setObliquityStatus(Integer.parseInt(bean.getDT().getBS()) == 1 ? "异常" : "正常");

		return gcData;

	}

	public EMGDData compareToEMGDData(EMGDDataBean bean) {
		// 转换对象为前端需要的bean
		EMGDData eMGDData = new EMGDData();
		eMGDData.setSN(bean.getSN());
		eMGDData.setIsOnline("在线");
		eMGDData.setReportTime(new Date());
		eMGDData.setHUN(bean.getDT().getHUN());
		eMGDData.setTMP(bean.getDT().getTMP());
		eMGDData.setP25(bean.getDT().getP25());
		eMGDData.setPM10(bean.getDT().getPM10());
		return eMGDData;

	}

	public SmokeDetectorData compareToSmokeDetectorData(SmokeDetectorDataBean bean) {
		// 转换对象为前端需要的bean
		SmokeDetectorData smokeDetectorData = new SmokeDetectorData();
		smokeDetectorData.setSN(bean.getSN());
		smokeDetectorData.setIsOnline("在线");
		smokeDetectorData.setReportTime(new Date(System.currentTimeMillis()));
		smokeDetectorData.setCMD(bean.getCMD());
		if (SmokeDetectorDataType.AUBD.equals(smokeDetectorData.getCMD())) {
			smokeDetectorData.setIF1Status(bean.getIF1());
			smokeDetectorData.setIF2Status(bean.getIF2());
		} else if (SmokeDetectorDataType.UBD.equals(smokeDetectorData.getCMD())) {
			smokeDetectorData.setIF1(bean.getIF1());
			smokeDetectorData.setIF2(bean.getIF2());
		}
		return smokeDetectorData;

	}

	private <T> T getObjectMapperBean(String json, Class<T> mclass) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		T bean = null;
		try {
			bean = objectMapper.readValue(json, mclass);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}

	private JSONObject getRowData(Device device, JSONObject json) {

		// 得到平台的id 看看是哪个平台类型 根据平台类型 解析数据
		ConnectPlatform connectPlatform = connectPlatformService.selectByPrimaryKey((long) device.getConnectPlatform());
		if (connectPlatform == null) {
			return null;
		}
		Integer type = connectPlatform.getTypeId();
		if (type == null) {
			return null;
		}

		JSONObject rowData = null;
		switch (type) {
		case ConncetTypeNumber.CONNECTTYPE_HUAIWEI:
			rowData = getRowDataByHuaWei(json);
			break;
		case ConncetTypeNumber.CONNECTTYPE_NOKIA:
			rowData = json;
			break;
		case ConncetTypeNumber.CONNECTTYPE_ONENET:
			rowData = json;
			break;
		case ConncetTypeNumber.CONNECTTYPE_UDP:
			rowData = json;
			break;
		default:
			break;
		}
		return rowData;

	}

	/**
	 * 华为解析数据
	 * 
	 * @param json
	 * @return
	 */
	private JSONObject getRowDataByHuaWei(JSONObject json) {
		JSONObject rawData = null;
		JSONObject service = json.optJSONObject("service");
		if (service == null)
			return rawData;
		JSONObject data = service.optJSONObject("data");
		if (data == null)
			return rawData;
		rawData = data.optJSONObject("rawData");
		return rawData;

	}

}

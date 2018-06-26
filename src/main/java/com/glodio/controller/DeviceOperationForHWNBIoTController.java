package com.glodio.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.bean.Device;
import com.glodio.service.IConnectPlatformService;
import com.glodio.service.IDDeviceHWNBOperationService;
import com.glodio.service.IDeviceService;
import com.glodio.service.ISystemResourceService;
import com.glodio.util.ConnectPlatformMap;

@RequestMapping("/system/device/hwnb")
@Controller
public class DeviceOperationForHWNBIoTController {
	
	@Resource
	IDDeviceHWNBOperationService idDeviceHWNBOperationService;
	@Resource
	ISystemResourceService iSystemResourceService;
	@Resource
	IConnectPlatformService IConnectPlatformService;
	
	@Resource
	IDeviceService iDeviceService;
	
	@RequestMapping(value = "/addDevice")
	public @ResponseBody Map<String, Object> addDevice(HttpServletRequest request,@Param("imei") String imei,@Param("type") int type,String connectPlatorm) throws Exception {
		Map<String, Object> dataMap = idDeviceHWNBOperationService.addDevice(imei,connectPlatorm);

		if(Integer.parseInt(dataMap.get("flag").toString()) == 0) {
			dataMap = idDeviceHWNBOperationService.setDeviceInfor(imei,(String)dataMap.get("msg"),type,connectPlatorm);
		}
		
		return dataMap;
	}
	
/*	@RequestMapping(value = "/setDeviceInfor")
	public @ResponseBody Map<String, Object> setDeviceInfor(HttpServletRequest request,@Param("imei") String imei,@Param("deviceId") String deviceId) throws Exception {
		dataMap = idDeviceHWNBOperationService.setDeviceInfor(imei,deviceId);
		
		return dataMap;
	}*/
	
	
	@RequestMapping(value = "/delDevice")
	public @ResponseBody Map<String, Object> delDevice(HttpServletRequest request,@Param("deviceId") String deviceId) throws Exception {
		Device bean = iDeviceService.queryBean(deviceId);
		Map<String, Object> dataMap = idDeviceHWNBOperationService.deleteDevice(deviceId,bean.getConnectPlatform()+"");
		
		return dataMap;
	}
	
	@RequestMapping(value = "/queryDevice")
	public @ResponseBody Map<String, Object> queryDevice(HttpServletRequest request,@Param("deviceId") String deviceId,@Param("appId") String appId) throws Exception {
		Device bean = iDeviceService.queryBean(deviceId);
		Map<String, Object> dataMap = idDeviceHWNBOperationService.queryDevice(deviceId, appId,bean.getConnectPlatform()+"");
		
		return dataMap;
	}
	//id为选择平台的id
	@RequestMapping(value = "/queryAllDevice")
	public @ResponseBody Map<String, Object> queryAllDevice(HttpServletRequest request,@Param("appId") String appId,@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize,String id) throws Exception {
		Map<String, Object> dataMap = idDeviceHWNBOperationService.queryAllDevice(appId,pageNo,pageSize,id);
		
		return dataMap;
	}
	
	@RequestMapping(value = "/sendCommand")
	public @ResponseBody Map<String, Object> sendCommand(HttpServletRequest request,@Param("imei") String imei,@Param("deviceId") String deviceId) throws Exception {
		Device device = iDeviceService.queryBean(deviceId);
		String appId = ConnectPlatformMap.getInstance().get(device.getConnectPlatform()+"").getNbAppid();
		Map<String, Object> dataMap = idDeviceHWNBOperationService.sendCommandForRE(appId,imei, deviceId,device.getConnectPlatform()+"");
		
		return dataMap;
	}
	
	
}

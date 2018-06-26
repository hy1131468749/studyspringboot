package com.glodio.serviceimpl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.glodio.hwnbiot.DeviceService;
import com.glodio.hwnbiot.beans.CommandBody;
import com.glodio.hwnbiot.beans.DeviceInfo;
import com.glodio.service.IDDeviceHWNBOperationService;

@Service("idDeviceHWNBOperationService")
public class DDeviceHWNBOperationServiceImpl implements IDDeviceHWNBOperationService {
	private Map<String, Object> dataMap = null;
	private String id;

	@Override
	public Map<String, Object> addDevice(String imei, String id) throws Exception{
		dataMap = DeviceService.registerDevice(imei,id);

		return dataMap;
	}
	
	@Override
	public Map<String, Object> deleteDevice(String deviceId, String id) throws Exception {
		dataMap = DeviceService.deleteDevice(deviceId,id);

		return dataMap;
	}

	@Override
	public Map<String, Object> setDeviceInfor(String imei,String deviceId,int type, String id) throws Exception {
		dataMap= DeviceService.setDeviceInfor(imei, deviceId,type,id);

		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommand(String appId, Object jsonObj, String id) throws Exception {
		dataMap= DeviceService.sendCommand(appId, jsonObj,id);

		return dataMap;
	}

	@Override
	public Map<String, Object> queryDevice(String deviceId, String appId, String id) throws Exception {
		dataMap = DeviceService.queryDevice(deviceId, appId,id);

		return dataMap;
	}

	@Override
	public Map<String, Object> queryAllDevice(String appId, int pageNo, int pageSize, String id) throws Exception {
		dataMap = DeviceService.queryAllDevice(appId,pageNo,pageSize,id);

		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommands(Object jsonObj, String id) throws Exception {
		dataMap= DeviceService.sendCommands(jsonObj,id);

		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommandForSetThreshold(String appId, String imei, String deviceId,
			String[] strArrayIF, String[] strArrayThresold, String id) throws Exception {
		dataMap= DeviceService.sendCommandForSetThreshold(appId, imei, deviceId, strArrayIF, strArrayThresold,id);
		
		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommandForSetTimeInterval(String appId, String imei, String deviceId,
			int reportInterval, int beatInterval, String id) throws Exception {
		dataMap= DeviceService.sendCommandForSetTimeInterval(appId, imei, deviceId, reportInterval, beatInterval,id);
		
		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommandForAlarmClear(String appId, String imei, String deviceId, String id) throws Exception {
		dataMap= DeviceService.sendCommandForAlarmClear(appId, imei, deviceId,id);
		
		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommandForAlarmMute(String appId, String imei, String deviceId, String id) throws Exception {
		dataMap= DeviceService.sendCommandForAlarmMute(appId, imei, deviceId,id);
		
		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommandForReboot(String appId, String imei, String deviceId,String id) throws Exception {
		dataMap= DeviceService.sendCommandForReboot(appId, imei, deviceId,id);
		
		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommandForRE(String appId, String imei, String deviceId,String id) throws Exception {
		dataMap= DeviceService.sendCommandForRE(appId, imei, deviceId,id);
		
		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommandForSensorData(String appId, String imei, String deviceId,String id) throws Exception {
		dataMap= DeviceService.sendCommandForSensorData(appId, imei, deviceId,id);
		
		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommandForGetThreshold(String appId, String imei, String deviceId,String id) throws Exception {
		dataMap= DeviceService.sendCommandForGetThreshold(appId, imei, deviceId,id);
		
		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommandForGetTimeInterval(String appId, String imei, String deviceId,String id)
			throws Exception {
		dataMap= DeviceService.sendCommandForGetTimeInterval(appId, imei, deviceId,id);
		
		return dataMap;
	}

	@Override
	public Map<String, Object> sendCommandForGetIFConfig(String appId, String imei, String deviceId,String id) throws Exception {
		dataMap= DeviceService.sendCommandForGetIFConfig(appId, imei, deviceId,id);
		
		return dataMap;
	}
}

package com.glodio.service;

import java.util.Map;

public interface IDDeviceHWNBOperationService {

	Map<String, Object> addDevice(String imei,String id) throws Exception;
	
	Map<String, Object> setDeviceInfor(String imei,String deviceId,int type,String id) throws Exception;
	
	Map<String, Object> deleteDevice(String deviceId,String id) throws Exception;
	
	Map<String, Object> queryDevice(String deviceId,String appId,String id) throws Exception;
	
	Map<String, Object> queryAllDevice(String appId,int pageNo,int pageSize,String id) throws Exception;
	
	Map<String, Object> sendCommand(String appId,Object jsonObj,String id) throws Exception;
	
	Map<String, Object> sendCommands(Object jsonObj,String id) throws Exception;
	
	Map<String, Object> sendCommandForSetThreshold(String appId,String imei,String deviceId,String[] strArrayIF,String[] strArrayThresold,String id) throws Exception;
	
	Map<String, Object> sendCommandForSetTimeInterval(String appId,String imei,String deviceId,int reportInterval,int beatInterval,String id) throws Exception;
	
	Map<String, Object> sendCommandForAlarmClear(String appId,String imei,String deviceId,String id) throws Exception;
	
	Map<String, Object> sendCommandForAlarmMute(String appId,String imei,String deviceId,String id) throws Exception;
	
	Map<String, Object> sendCommandForReboot(String appId,String imei,String deviceId,String id) throws Exception;
	
	Map<String, Object> sendCommandForRE(String appId,String imei,String deviceId,String id) throws Exception;
	
	Map<String, Object> sendCommandForSensorData(String appId,String imei,String deviceId,String id) throws Exception;
	
	Map<String, Object> sendCommandForGetThreshold(String appId,String imei,String deviceId,String id) throws Exception;
	
	Map<String, Object> sendCommandForGetTimeInterval(String appId,String imei,String deviceId,String id) throws Exception;
	
	Map<String, Object> sendCommandForGetIFConfig(String appId,String imei,String deviceId,String id) throws Exception;
	
}

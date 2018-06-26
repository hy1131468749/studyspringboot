package com.glodio.service;

import java.util.Map;

public interface IDDeviceNokiaNBOperationService {
	
	Map<String, Object> addDevice(String imei,String identifier, String id) throws Exception;
	
	Map<String, Object> deleteDevice(String imei, String id) throws Exception;
	
	Map<String, Object> sendCommand(String imei,Object jsonObject, String id)  throws Exception;
}

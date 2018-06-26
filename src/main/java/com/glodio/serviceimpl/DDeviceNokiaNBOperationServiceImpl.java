package com.glodio.serviceimpl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.glodio.nokianbiot.NokiaDeviceService;
import com.glodio.service.IDDeviceNokiaNBOperationService;

@Service("idDeviceNokiaNBOperationService")
public class DDeviceNokiaNBOperationServiceImpl implements IDDeviceNokiaNBOperationService {

	@Override
	public Map<String, Object> addDevice(String imei, String identifier, String id) throws Exception {
		Map<String, Object> map = NokiaDeviceService.registerDevice(imei, identifier,id);
		
		return map;
	}

	@Override
	public Map<String, Object> deleteDevice(String imei, String id) throws Exception {
		Map<String, Object> map = NokiaDeviceService.deleteDevice(imei,id);
		
		return map;
	}

	@Override
	public Map<String, Object> sendCommand(String imei, Object jsonObject, String id) throws Exception {
		Map<String, Object> map = NokiaDeviceService.sendCommand(imei,jsonObject,id);
		
		return map;
	}

}

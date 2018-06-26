package com.glodio.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.service.IDDeviceNokiaNBOperationService;
import com.glodio.service.ISystemResourceService;
import com.glodio.util.HexUtilty;
import com.glodio.util.JsonUtil;

import net.sf.json.JSONObject;

@RequestMapping("/system/device/nokia")
@Controller
public class DeviceOperationForNokiaNBIoTController {
	
	@Resource
	IDDeviceNokiaNBOperationService idDeviceNokiaNBOperationService;
	@Resource
	ISystemResourceService iSystemResourceService;
	
	@RequestMapping(value = "/addDevice")
	public @ResponseBody Map<String, Object> addDevice(HttpServletRequest request,@Param("imei") String imei,@Param("identifier") String identifier) throws Exception {
		String id = "";// TODO 未解决id 传参问题
		Map<String, Object> dataMap = idDeviceNokiaNBOperationService.addDevice(imei, identifier,id);
		
		return dataMap;
	}
	
	@RequestMapping(value = "/delDevice")
	public @ResponseBody Map<String, Object> delDevice(HttpServletRequest request,@Param("imei") String imei) throws Exception {
		String id = "";// TODO 未解决id 传参问题
		Map<String, Object> dataMap = idDeviceNokiaNBOperationService.deleteDevice(imei,id);
		
		return dataMap;
	}
	
	@RequestMapping(value = "/sendCommand")
	public @ResponseBody Map<String, Object> sendCommand(HttpServletRequest request,@Param("imei") String imei) throws Exception {
		
/*		{
		    "CMD": "ACT",
		    "SN": "863703030746197",
		    "DATAS": {
		        "status": "1",
		        "travel_switch": "1",
		        "hinge": "0",
		        "beacon_interval": "24",
		        "level_interval": "15",
		        "travel_interval": "15"
		    }
		}*/
		String id = "";// TODO id问题未解决
		Map<String, Object> map = new HashMap<>();
		map.put("CMD","ACT");
		map.put("SN", imei);
		
		JSONObject jsonObject = new JSONObject(); 
		jsonObject.put("status", "1");
		jsonObject.put("travel_switch", "1");
		jsonObject.put("hinge", "0");
		jsonObject.put("beacon_interval", "12");
		jsonObject.put("level_interval", "20");
		jsonObject.put("travel_interval", "20");
		map.put("DATAS", jsonObject);
		
		JSONObject resourceJson = new JSONObject();
		
		String strMap = JsonUtil.jsonObj2Sting(map);
		String strHex = HexUtilty.strTo16(strMap);
		resourceJson.put("resourceValue", strHex);
		System.out.println(resourceJson.toString());
		
		
		//String strCmd = ""

		Map<String, Object> dataMap = idDeviceNokiaNBOperationService.sendCommand(imei, resourceJson,id);
		return dataMap;
	}
	
}

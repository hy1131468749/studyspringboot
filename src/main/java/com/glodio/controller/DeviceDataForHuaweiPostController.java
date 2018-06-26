package com.glodio.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.bean.Device;
import com.glodio.service.DataHandleService;
import com.glodio.service.IDeviceCoverService;
import com.glodio.service.IDeviceDustbinService;
import com.glodio.service.IDeviceService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/system/device/huawei")
public class DeviceDataForHuaweiPostController {
	
	@Resource
	private IDeviceService iDeviceService;
	
	@Resource
	private IDeviceCoverService iDeviceCoverService;
	
	@Resource
	private IDeviceDustbinService iDeviceDustbinService;
	
	
	
	@Autowired
	private DataHandleService dataHandleService;
	
	@ResponseBody
	@RequestMapping(value="/updateDeviceData",method=RequestMethod.POST)
	public void updateDeviceData(@RequestBody JSONObject json) throws Exception {
		
		System.out.println(json.toString());
		if(json != null && json.get("deviceId") != null) {
			Device device = iDeviceService.queryBeanByNbDeviceId(json.get("deviceId").toString()); 
			if(device == null){
				return ;
			}
			dataHandleService.handleData(device,json);
		}
	}
	
	private JSONObject getRowData(JSONObject json){
		JSONObject rawData = null;
		JSONObject service = json.optJSONObject("service");
		if(service == null)
			return rawData;
		JSONObject data = service.optJSONObject("data");
		if(data == null)
			return rawData; 
		rawData = data.optJSONObject("rawData");
		return rawData;
		
	}
	
	
	
	
    
    
   
    
 

}

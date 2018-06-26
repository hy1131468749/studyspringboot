package com.glodio.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.bean.Device;
import com.glodio.bean.DeviceCover;
import com.glodio.bean.DeviceDustbin;
import com.glodio.bean.DeviceEmgd;
import com.glodio.bean.DeviceHggas;
import com.glodio.bean.DeviceParam;
import com.glodio.bean.DeviceSmokeDetector;
import com.glodio.bean.User;
import com.glodio.bean.device.EMGDData;
import com.glodio.bean.device.EMGDDataBean;
import com.glodio.bean.device.EMGDDataValue;
import com.glodio.bean.device.GCData;
import com.glodio.bean.device.MCData;
import com.glodio.bean.device.SmokeDetectorData;
import com.glodio.bean.device.TestList;
import com.glodio.service.DeviceEmgdService;
import com.glodio.service.DeviceHggasService;
import com.glodio.service.DeviceParamService;
import com.glodio.service.DeviceSmokeDetectorService;
import com.glodio.service.IDeviceCoverService;
import com.glodio.service.IDeviceDustbinService;
import com.glodio.service.IDeviceService;
import com.glodio.util.DeviceTypeNumber;
import com.glodio.util.StringUtil;


@Controller
@RequestMapping("/system/device")
public class DeviceDataRequstController {

	@Autowired
	IDeviceService iDeviceService;
	@Autowired
	IDeviceCoverService iDeviceCoverService;
	@Autowired
	IDeviceDustbinService iDeviceDustbinService;
	@Autowired
	DeviceEmgdService deviceEmgdService;
	@Autowired
	DeviceSmokeDetectorService deviceSmokeDetectorService;
	@Autowired
	DeviceHggasService deviceHggasService;
	@Autowired
	DeviceParamService deviceParamService;
	

	
	@ResponseBody
	@RequestMapping(value="/manholeCoverData",method=RequestMethod.GET)
	public Map<String, Object> manholeCoverData(HttpServletRequest request) throws Exception {

		Map<String, Object> dataMap = new HashMap<>();
		ArrayList<MCData> mcList = new ArrayList<>();
		
		//数据集中是否含有设备的数据的状态
		boolean bFlag = false;
		
		//查询所有井盖设备
		User user = (User)request.getSession().getAttribute("user");
	    if(user == null){
	    	return dataMap;
	    }
		ArrayList<Device> devList = iDeviceService.queryBeansForDeviceType(DeviceTypeNumber.MANHOLECOVER,user.getOrgId());
		List<String> deviceIds = new ArrayList<>();
	    if(devList != null && !devList.isEmpty()){
	    	for(Device device:devList){
	    		deviceIds.add(device.getDeviceId());
	    	}
	    }
		if(deviceIds.isEmpty()){
			return dataMap;
		}
		List<DeviceCover> devCoverList = iDeviceCoverService.selectLastByDeviceIds(deviceIds);

		for(Device device:devList) {
			bFlag = false;
			for(DeviceCover cover:devCoverList) {
				if(device.getDeviceId().equals(cover.getDeviceId())) {
					MCData data = new MCData();
					data.setSN(device.getDeviceId());
					data.setDeviceName(device.getDeviceName());
					data.setLng(device.getLng());
					data.setLat(device.getLat());
					if(comPareOneDay(cover.getReportTime())){
					    data.setIsOnline("在线");
					}else{
						data.setIsOnline("离线");
					}
					
					data.setWaterLine(cover.getWaterLevel());
					data.setIsOpen(cover.getIsOpen());
					mcList.add(data);
					bFlag = true;
					break;
				}
			}
			
			if(!bFlag) {
				MCData data = new MCData(device.getDeviceName(),device.getDeviceId(),device.getLng(),device.getLat());
				mcList.add(data);
			}
		}
		
		dataMap.put("mcList", mcList);
		return dataMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/garbageCanData",method=RequestMethod.GET)
	public Map<String, Object> GarbageCanData(HttpServletRequest request) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();
		ArrayList<GCData> gcList = new ArrayList<>();
		
		//数据集中是否含有设备的数据的状态
		boolean bFlag = false;
		User user = (User)request.getSession().getAttribute("user");	
		//查询所有垃圾箱设备
		if(user == null){
			return dataMap;
		}
		ArrayList<Device> devList = iDeviceService.queryBeansForDeviceType(DeviceTypeNumber.DUSTBIN,user.getOrgId());
		List<String> deviceIds = new ArrayList<>();
	    if(devList != null && !devList.isEmpty()){
	    	for(Device device:devList){
	    		deviceIds.add(device.getDeviceId());
	    	}
	    }
		if(deviceIds.isEmpty()){
			return dataMap;
		}
		List<DeviceDustbin> devDustList =  iDeviceDustbinService.selectLastByDeviceIds(deviceIds);
		//组装数据 
		for(Device device:devList) {
			bFlag = false;
			for(DeviceDustbin dustbin:devDustList) {
				if(device.getDeviceId().equals(dustbin.getDeviceId())) {
					GCData data = new GCData();
					data.setSN(device.getDeviceId());
					data.setDeviceName(device.getDeviceName());
					data.setLng(device.getLng());
					data.setLat(device.getLat());
					if(comPareOneDay(dustbin.getReportTime())){
					    data.setIsOnline("在线");
					}else{
						data.setIsOnline("离线");
					}
					data.setFullLeft(dustbin.getFullLeft());
					data.setFullRight(dustbin.getFullRight());
					data.setSmokeStatus(dustbin.getSmokeStatus());
					data.setObliquityStatus(dustbin.getObliquityStatus());
					gcList.add(data);
					bFlag = true;
					break;
				}
			}
			
			if(!bFlag) {
				GCData data = new GCData(device.getDeviceName(), device.getDeviceId(),device.getLng(),device.getLat());
				gcList.add(data);
			}
		}
		
		dataMap.put("gcList",gcList);
		return dataMap;
	}
	
	/**
	 * 查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/emgdData")
	public Map<String, Object> emgdData(HttpServletRequest request) throws Exception {

		Map<String, Object> dataMap = new HashMap<>();
		ArrayList<EMGDData> emgdList = new ArrayList<>();
		
		//数据集中是否含有设备的数据的状态
		boolean bFlag = false;
		
		
		User user = (User)request.getSession().getAttribute("user");
	    if(user == null){
	    	return dataMap;
	    }
		ArrayList<Device> devList = iDeviceService.queryBeansForDeviceType(DeviceTypeNumber.ENVIROMENTMONITOR,user.getOrgId());
		List<String> deviceIds = new ArrayList<>();
	    if(devList != null && !devList.isEmpty()){
	    	for(Device device:devList){
	    		deviceIds.add(device.getDeviceId());
	    	}
	    }
	    if(deviceIds.isEmpty()){
	    	return dataMap;
	    }
	    List<DeviceEmgd> deviceEmgdList = deviceEmgdService.selectLastByDeviceIds(deviceIds);
		for(Device device:devList) {
			bFlag = false;
			for(DeviceEmgd deviceEmgd:deviceEmgdList) {
				if(device.getDeviceId().equals(deviceEmgd.getDeviceId())) {
					EMGDData data = new EMGDData();
					data.setDeviceId(device.getDeviceId());
					//因为之前都有的代码都这样写，所以这里保留了
					data.setSN(device.getDeviceId());
					data.setDeviceName(device.getDeviceName());
					data.setLng(device.getLng());
					data.setLat(device.getLat());
					if(comPareOneDay(deviceEmgd.getReportTime())){
					    data.setIsOnline("在线");
					    bFlag = true;
					}else{
						data.setIsOnline("离线");
						bFlag = false;
						break;
					}
					data.setHUN(new EMGDDataValue(null,deviceEmgd.getHun()));
					data.setTMP(new EMGDDataValue(null,deviceEmgd.getTmp()));
					data.setPM10(new EMGDDataValue(null,deviceEmgd.getP10()));
					data.setP25(new EMGDDataValue(null,deviceEmgd.getP25()));
					emgdList.add(data);
					break;
				}
			}
			
			if(!bFlag) {
				EMGDData data = new EMGDData();
				data.setDeviceName(device.getDeviceName());
				data.setDeviceId(device.getDeviceId());
				data.setLat(device.getLat());
				data.setLng(device.getLng());
				data.setSN(device.getDeviceId());
				data.setHUN(new EMGDDataValue(null,"离线"));
				data.setTMP(new EMGDDataValue(null,"离线"));
				data.setPM10(new EMGDDataValue(null,"离线"));
				data.setP25(new EMGDDataValue(null,"离线"));
				emgdList.add(data);
			}
		}
		
		dataMap.put("emgdList", emgdList);
		return dataMap;
	}
	
	
    
    /**
	 * 查询烟感 当前用红光 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/smokeDetectorData")
	public Map<String, Object> smokeDetectorData(HttpServletRequest request) throws Exception {

		Map<String, Object> dataMap = new HashMap<>();
		ArrayList<SmokeDetectorData> smokeDetectorDataList = new ArrayList<>();
		
		//数据集中是否含有设备的数据的状态
		boolean bFlag = false;
		
		
		User user = (User)request.getSession().getAttribute("user");
	    if(user == null){
	    	return dataMap;
	    }
		ArrayList<Device> devList = iDeviceService.queryBeansForDeviceType(DeviceTypeNumber.SMOKEDETECTOR,user.getOrgId());
		List<String> deviceIds = new ArrayList<>();
	    if(devList != null && !devList.isEmpty()){
	    	for(Device device:devList){
	    		deviceIds.add(device.getDeviceId());
	    	}
	    }
        if(deviceIds.isEmpty()){ 
        	return dataMap;
        }
	
	    List<DeviceParam> deviceParams = deviceParamService.selectListByDeviceIds(deviceIds);
	    Map<String,String> reportFrequencyMap = new HashMap<>();
	    if(deviceParams != null){
	    	for(DeviceParam deviceParam : deviceParams){
	    		reportFrequencyMap.put(deviceParam.getDeviceId(), deviceParam.getReportFrequency());
	    	}
	    }
	    List<DeviceHggas> deviceHggasList = deviceHggasService.selectLastByDeviceIds(deviceIds);
		for(Device device:devList) {
			bFlag = false;
			for(DeviceHggas deviceHggas:deviceHggasList) {
				if(device.getDeviceId().equals(deviceHggas.getDeviceId())) {
					bFlag = true;
					SmokeDetectorData data = new SmokeDetectorData();
					data.setDeviceId(deviceHggas.getDeviceId());
					//因为之前都有的代码都这样写，所以这里保留了
					data.setSN(device.getDeviceId());
					data.setDeviceName(device.getDeviceName());
					data.setLng(device.getLng());
					data.setLat(device.getLat());
					data.setIF2(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deviceHggas.getReportTime()));
					if(comPareSmokeTime(reportFrequencyMap,deviceHggas)){
					    data.setIsOnline("在线");
						if(1 == deviceHggas.getAlarmStatus()){
							data.setIF1("告警");
						}else{
							data.setIF1("正常");
						}
					}else{
						data.setIsOnline("离线");						
						data.setIF1("离线");
					}
				
					smokeDetectorDataList.add(data);
					break;
				}
			}
			
			if(!bFlag) {
				SmokeDetectorData data = new SmokeDetectorData();
				data.setDeviceName(device.getDeviceName());
				data.setDeviceId(device.getDeviceId());
				data.setLat(device.getLat());
				data.setLng(device.getLng());
				data.setSN(device.getDeviceId());
				data.setIF1("离线");
				data.setIF2("");
			
				smokeDetectorDataList.add(data);
			}
		}
		
		dataMap.put("smokeDetectorDataList", smokeDetectorDataList);
		return dataMap;
	}
    
	/**
	 * 查询烟感
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/smokeDetectorData2")
	public Map<String, Object> smokeDetectorData2(HttpServletRequest request) throws Exception {

		Map<String, Object> dataMap = new HashMap<>();
		ArrayList<SmokeDetectorData> smokeDetectorDataList = new ArrayList<>();
		
		//数据集中是否含有设备的数据的状态
		boolean bFlag = false;
		
		
		User user = (User)request.getSession().getAttribute("user");
	    if(user == null){
	    	return dataMap;
	    }
		ArrayList<Device> devList = iDeviceService.queryBeansForDeviceType(DeviceTypeNumber.SMOKEDETECTOR,user.getOrgId());
		List<String> deviceIds = new ArrayList<>();
	    if(devList != null && !devList.isEmpty()){
	    	for(Device device:devList){
	    		deviceIds.add(device.getDeviceId());
	    	}
	    }
	    List<DeviceSmokeDetector> deviceSmokeDetectorList = deviceSmokeDetectorService.selectByDeviceIds(deviceIds);
		for(Device device:devList) {
			bFlag = false;
			for(DeviceSmokeDetector deviceSmokeDetecto:deviceSmokeDetectorList) {
				if(device.getDeviceId().equals(deviceSmokeDetecto.getDeviceId())) {
					SmokeDetectorData data = new SmokeDetectorData();
					data.setDeviceId(deviceSmokeDetecto.getDeviceId());
					//因为之前都有的代码都这样写，所以这里保留了
					data.setSN(device.getDeviceId());
					data.setDeviceName(deviceSmokeDetecto.getDeviceName());
					data.setLng(deviceSmokeDetecto.getLng());
					data.setLat(deviceSmokeDetecto.getLat());
					if(comPareOneDay(deviceSmokeDetecto.getReportTime())){
					    data.setIsOnline("在线");
					    bFlag = true;
					}else{
						data.setIsOnline("离线");
						bFlag = false;
						break;
					}
					if(1 == deviceSmokeDetecto.getIf1Status()){
						data.setIF1("告警");
					}else{
						data.setIF1(deviceSmokeDetecto.getIf1());
					}
					if(1 == deviceSmokeDetecto.getIf2Status()){
						data.setIF2("告警");
					}else{
						data.setIF2(deviceSmokeDetecto.getIf2());
					}
					smokeDetectorDataList.add(data);
					break;
				}
			}
			
			if(!bFlag) {
				SmokeDetectorData data = new SmokeDetectorData();
				data.setDeviceName(device.getDeviceName());
				data.setDeviceId(device.getDeviceId());
				data.setLat(device.getLat());
				data.setLng(device.getLng());
				data.setSN(device.getDeviceId());
				data.setIF1("离线");
				data.setIF2("离线");
				
				smokeDetectorDataList.add(data);
			}
		}
		
		dataMap.put("smokeDetectorDataList", smokeDetectorDataList);
		return dataMap;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/testData",method=RequestMethod.GET)
	public Map<String, Object> testData(HttpServletRequest request) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();
		
		dataMap.put("ttList",TestList.getInstance());
		return dataMap;
	}
	/**
	 * 判断上报时间是否过了一天
	 */
	private boolean comPareOneDay(Date reportTime){
	     if(reportTime == null)
	         return false;
	     Calendar nowTime = Calendar.getInstance();
	     Calendar nextDayTime = Calendar.getInstance();
	     nextDayTime.setTime(reportTime);
	     nextDayTime.add(Calendar.DATE, 1);
	     if(nextDayTime.after(nowTime))
	    	 return true;
	     else
	    	 return false;
	     
	}
	
	/**
	 * 看看烟感是否离线
	 * @param reportTime
	 * @return
	 */
	private boolean comPareSmokeTime(Map<String,String> map,DeviceHggas deviceHggas ){
	    
	     Calendar nowTime = Calendar.getInstance();
	     Calendar nextTime = Calendar.getInstance(); 
	     String reportFrequency = map.get(deviceHggas.getDeviceId());
	     int time = StringUtil.convertInt(reportFrequency);
	     if(time == 0){
	    	 time = 60;
	     }
	     long duetime = deviceHggas.getReportTime().getTime()+time * 1000+3*60*1000;
	     nextTime.setTimeInMillis(duetime);
	     if(nextTime.after(nowTime))
	    	 return true;
	     else
	    	 return false;
	     
	}
	
	@RequestMapping("/test11")
	public String test11 (@RequestBody EMGDDataBean d){
		
		return "";
	}
	
	
}

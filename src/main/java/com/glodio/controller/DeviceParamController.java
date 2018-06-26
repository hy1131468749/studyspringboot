package com.glodio.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.bean.Device;
import com.glodio.bean.DeviceParam;
import com.glodio.hongguan.OnenetHGDeviceService;
import com.glodio.service.DeviceParamService;
import com.glodio.service.IDeviceService;
import com.glodio.util.StringUtil;

@Controller
@RequestMapping("/system/deviceParamController")
public class DeviceParamController {
	
	@Autowired
	private DeviceParamService deviceParamService;
	
	@Autowired
	private IDeviceService iDeviceService;
	
	/**
	 * 保存和更新数据
	 * @param deviceParam
	 * @return
	 */
	@RequestMapping(value="/saveAndUpdate")
	@ResponseBody
	public Map<String,Object> saveAndUpdate(DeviceParam deviceParam){
		Map<String,Object> resultMap = new HashMap<>();
		String deviceId = deviceParam.getDeviceId();
		deviceParam.setImsi(deviceId);
		
		deviceParam.setUpdateTime(new Date());
		if(StringUtil.strIsNullOrEmpty(deviceId)){
			resultMap.put("result", false);
			return resultMap;
		}
		DeviceParam olDeviceParam = deviceParamService.selectByDeviceIdAndType(deviceId, 1);
		if(olDeviceParam == null){
			deviceParam.setCreateTime(new Date());
			deviceParam.setType(1);//设置为参数
			deviceParamService.insertSelective(deviceParam);
		}else{
			olDeviceParam.setUpdateTime(new Date());
			initUpdateDeviceParam(olDeviceParam,deviceParam);
			deviceParamService.updateByPrimaryKey(olDeviceParam);
		}
		resultMap.put("result", true);
		return resultMap;
	}
	
	@RequestMapping(value="/findDetail")
	@ResponseBody
	public Map<String,Object> findDetail(@RequestParam String deviceId){
		Map<String,Object> resultMap = new HashMap<>();
		DeviceParam bean = deviceParamService.selectByDeviceIdAndType(deviceId, 1);		
		resultMap.put("bean", bean);
		return resultMap;
	}
	
	
	
	/**
	 * 下发命令
	 * @param deviceId
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value="/issueCommand")
	@ResponseBody
	public Map<String,Object> issueCommand(@RequestParam String deviceId) throws Exception{
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("result", false);
		DeviceParam deviceParam = deviceParamService.selectByDeviceIdAndType(deviceId,1);		
		if(deviceParam == null){
			return resultMap;
		}
		
		Device device = iDeviceService.queryBean(deviceId);
		if(device == null){
			return resultMap;
		}
		
	
//		opCode = 105
//		指令流水ID  1015
//		采样频率  1008   1-10
//		上报频率 1009	 >10秒  <65535
//		电量阈值  1010   1~100
//		检测频率 1012   设备侧未用：10
//		紧急采样频率  1013   min：1 -10
//		紧急上报频率  1014   > 10秒   < 65535
	
		
		
		
		return resultMap;
		
	}
	
	/**
	 * 设备自检
	 * @param deviceId
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	@RequestMapping(value="/selfCheck")
	@ResponseBody
	public Map<String,Object> selfCheck(@RequestParam String deviceId) throws Exception{
	
		
		return null;
	}
	
	
	@RequestMapping(value="/queryInf")
	@ResponseBody
	public Map<String,Object> queryInf(String imis){
		
		try {
			OnenetHGDeviceService.queryDeviceInfo(imis, 46+"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}
	
	
	private void initUpdateDeviceParam(DeviceParam oldBean,DeviceParam newBean){
		oldBean.setSignalIntensity(newBean.getSignalIntensity());
		oldBean.setElectricity(newBean.getElectricity());
		oldBean.setElectricityThreshold(newBean.getElectricityThreshold());
		oldBean.setReportFrequency(newBean.getReportFrequency());
		oldBean.setDetectionFrequency(newBean.getDetectionFrequency());
		oldBean.setSampleFrequency(newBean.getSampleFrequency());
		oldBean.setUrgentReportFrequency(newBean.getUrgentReportFrequency());
		oldBean.setUrgentSampleFrequency(newBean.getUrgentSampleFrequency());
	}

}

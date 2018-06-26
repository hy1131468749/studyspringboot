package com.glodio.hongguan;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glodio.bean.Device;
import com.glodio.bean.DeviceHggas;
import com.glodio.bean.DeviceParam;
import com.glodio.controller.DeviceDataForOnenetPostController;
import com.glodio.controller.SpringContextUtil;
import com.glodio.hongguan.bean.RealDataParse;
import com.glodio.hongguan.bean.ReportData;
import com.glodio.service.DeviceHggasService;
import com.glodio.service.DeviceParamService;
import com.glodio.service.IDeviceService;
import com.glodio.util.JsonUtil;
import com.glodio.util.LogFile;
import com.glodio.util.StringUtil;

import net.sf.json.JSONObject;

/*
 * 设备上报数据数据处理线程
 * 
 * */
public class OnenetHGMessageProcessService implements Runnable {
	
	private static Logger log = LoggerFactory.getLogger(OnenetHGMessageProcessService.class);
	

	private DeviceHggasService deviceHggasService = (DeviceHggasService) SpringContextUtil
			.getBean("deviceHggasServiceImpl");
	private DeviceParamService deviceParamService = (DeviceParamService) SpringContextUtil
			.getBean("deviceParamServiceImpl");
	
	private IDeviceService ideviceService = (IDeviceService) SpringContextUtil
			.getBean("iDeviceService");
   
	@Override
	public void run() {
		while (true) {
			try {
				// 获取上报数据
				String jsonString = OnenetHGDeviceDataQueue.getInstance().take();
				LogFile.dataLogger.info("获取的原始数据"+jsonString);
				// System.out.println(jsonString);
				// 转换上报数据
				ReportData data = (ReportData) JSONObject.toBean(JSONObject.fromObject(jsonString), ReportData.class);
				// 解析上报数据
				String Info = RealDataParse.hexString2String(data.getInfo());
				LogFile.dataLogger.info("获取的解析数据"+Info);
				// 存储上报数据入库
				JSONObject jsonObject = JSONObject.fromObject(Info);
				//设备传上来的时间戳是10位 java是13位 所以必须要进行处理
				if(jsonObject.containsKey("selfCheckResult")){
					String selfCheckResult =jsonObject.getString("selfCheckResult"); 
					//如果设备自检正常
					if("0000".equals(selfCheckResult)){
						String deviceId = jsonObject.optString("deviceId");
						DeviceParam deviceParam = deviceParamService.selectByDeviceId(deviceId);
						if(deviceParam == null){
							continue ;
						}
						handleSelfCheck(deviceParam,jsonObject);
					}
				}
				else if (jsonObject.containsKey("flow")) {		
					String flow = jsonObject.getString("flow");
					DeviceParam deviceParam = deviceParamService.selectByLikeFlow(flow);
					if(deviceParam == null){
						continue;
					}
					deviceParam.setIssueState(2);
					deviceParam.setUpdateTime(new Date());
					deviceParamService.updateByPrimaryKeySelective(deviceParam);
					
					
				} else if(jsonObject.containsKey("deviceId") && jsonObject.containsKey("alarmStatus")){
					long reportTime = jsonObject.optLong("reportTime", 0);
					reportTime =  reportTime*1000;
					if (reportTime == 0) {
						reportTime = System.currentTimeMillis();
					}
					jsonObject.remove("reportTime");
					DeviceHggas deviceHggas = JsonUtil.jsonString2SimpleObj(jsonObject.toString(), DeviceHggas.class);
					Device device = ideviceService.queryBean(deviceHggas.getDeviceId());
					deviceHggas.setOrgId(device.getOrgId());
					deviceHggas.setDeviceName(device.getDeviceName());
					deviceHggas.setReportTime(new Date(reportTime));
					deviceHggasService.insertSelective(deviceHggas);
				}
				// 。。。。。
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void handleSelfCheck(DeviceParam deviceParam,JSONObject json){
	     String electricityThreshold = json.optString("electricityThreshold");
	     String sampleFrequency = json.optString("sampleFrequency");	
	     String reportFrequency = json.optString("reportFrequency");	
	     String detectionFrequency = json.optString("detectionFrequency");
	     String urgentSampleFrequency = json.optString("urgentSampleFrequency");	
	     String urgentReportFrequency = json.optString("urgentReportFrequency");	
		if(!StringUtil.strIsNullOrEmpty(electricityThreshold)){
			deviceParam.setElectricityThreshold(electricityThreshold);
		}
		if(!StringUtil.strIsNullOrEmpty(sampleFrequency)){
			deviceParam.setSampleFrequency(sampleFrequency);
		}
		if(!StringUtil.strIsNullOrEmpty(reportFrequency)){
			deviceParam.setReportFrequency(reportFrequency);
		}
		if(!StringUtil.strIsNullOrEmpty(detectionFrequency)){
			deviceParam.setDetectionFrequency(detectionFrequency);
		}
		if(!StringUtil.strIsNullOrEmpty(urgentSampleFrequency)){
			deviceParam.setUrgentSampleFrequency(urgentSampleFrequency);
		}
		if(!StringUtil.strIsNullOrEmpty(urgentReportFrequency)){
			deviceParam.setUrgentReportFrequency(urgentReportFrequency);
		}
		deviceParamService.updateByPrimaryKeySelective(deviceParam);
	}
	
	
}

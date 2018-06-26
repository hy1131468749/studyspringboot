package com.glodio.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.bean.device.GCData;
import com.glodio.bean.device.GCDataBean;
import com.glodio.bean.device.MCData;
import com.glodio.bean.device.MCDataBean;
import com.glodio.bean.device.TestData;
import com.glodio.bean.device.TestList;
import com.glodio.service.IDeviceCoverService;
import com.glodio.service.IDeviceDustbinService;
import com.glodio.util.JsonUtil;
import com.glodio.util.LogFile;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/system/device/udp")
public class DeviceDataForUdpPostController {
	
	@Resource
	IDeviceCoverService iDeviceCoverService;
	@Resource
	IDeviceDustbinService iDeviceDustbinService;
	
	@ResponseBody
	@RequestMapping(value="/updateDeviceData",method=RequestMethod.POST)
	public void updateDeviceData(@RequestBody JSONObject json) throws Exception {
		if(json != null && json.get("data") != null) {
			
			//类型名
			String deviceType = "ManholeCover";
			//保存结果
			boolean bResult = false;
			//解析数据
			String strTemp = json.getString("data");
			JSONObject jsonObject = new JSONObject();
			LogFile.devLogger.info("[Device data from UDP platform post] UpdateDeviceData:[{}]", new Date(System.currentTimeMillis())+"  "+strTemp);
			//判断是否是json字符串
			if(JsonUtil.isGoodJson(strTemp)) {
				jsonObject = JSONObject.fromObject(strTemp);
				// 井盖
				if(jsonObject.containsKey("DATAS")) {
					MCDataBean mcDataBean = (MCDataBean)JSONObject.toBean(jsonObject, MCDataBean.class);
					
					//保存数据
					bResult = iDeviceCoverService.coverDataProcess(mcDataBean);
					deviceType = "ManholeCover";
				}
				else {// 垃圾箱
					GCDataBean gcDataBean = (GCDataBean)JSONObject.toBean(jsonObject,GCDataBean.class);
					
					//保存数据
					bResult = iDeviceDustbinService.dustbinDataProcess(gcDataBean);
					deviceType = "GarbageCan";
				}
				
				LogFile.devLogger.info("[Save Device data into database from UDP platform] DeviceType:[{}],result:[{}]", deviceType,bResult);
			}
			else { // 非json数据
				TestData testData = new TestData();
				testData.setTime(new Date(System.currentTimeMillis()));
				testData.setData(strTemp);

				TestList.getInstance().add(testData);
			}

		}
	}
	
	public MCData compareToMCData(MCDataBean bean) {
		// 转换对象为前端需要的bean
		MCData  mcData = new MCData();
		mcData.setSN(bean.getSN());
		mcData.setIsOnline("在线");
		mcData.setReportTime(new Date(System.currentTimeMillis()));
		mcData.setWaterLine(Integer.parseInt(bean.getDATAS().getData6()) == 1 ? "告警" : "正常");
		mcData.setIsOpen((Integer.parseInt(bean.getDATAS().getData7()) == 1 || Integer.parseInt(bean.getDATAS().getData10()) == 1 ) ? "告警" : "正常");
		
		return mcData;
	}
	
	public GCData compareToGCData(GCDataBean bean) {
		// 转换对象为前端需要的bean
		GCData gcData = new GCData();
		gcData.setSN(bean.getSN());
		gcData.setIsOnline("在线");
		gcData.setReportTime(new Date(System.currentTimeMillis()));
		gcData.setFullLeft(Integer.parseInt(bean.getDT().getFL()) == 1 ? "满" : "未满");
		gcData.setFullRight(Integer.parseInt(bean.getDT().getFR()) == 1 ? "满" : "未满");
		gcData.setSmokeStatus(Integer.parseInt(bean.getDT().getSS()) == 1 ? "有" : "无");
		gcData.setObliquityStatus(Integer.parseInt(bean.getDT().getBS()) == 1 ? "异常" : "正常");
		
		return gcData;
		
	}
}

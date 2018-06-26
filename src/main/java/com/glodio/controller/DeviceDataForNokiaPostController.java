package com.glodio.controller;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.bean.Device;
import com.glodio.nokianbiot.beans.NokiaData;
import com.glodio.service.DataHandleService;
import com.glodio.service.IDDeviceNokiaNBOperationService;
import com.glodio.service.IDeviceCoverService;
import com.glodio.service.IDeviceDustbinService;
import com.glodio.service.IDeviceService;
import com.glodio.util.HexUtilty;
import com.glodio.util.LogFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/system/device/nokia")
public class DeviceDataForNokiaPostController {


	@Resource
	private IDeviceService iDeviceService;
	
	@Resource
	IDDeviceNokiaNBOperationService idDeviceNokiaNBOperationService;
	@Resource
	IDeviceCoverService iDeviceCoverService;
	@Resource
	IDeviceDustbinService iDeviceDustbinService;

	@Autowired
	private DataHandleService dataHandleService;
	@ResponseBody
	@RequestMapping(value = "/updateDeviceData", method = RequestMethod.GET)
	public Map<String, Object> updateDeviceData(HttpServletRequest request) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();

		dataMap.put("test", "test");
		return dataMap;
	}

	@ResponseBody
	@RequestMapping(value = "/updateDeviceData", method = RequestMethod.POST)
	public void updateDeviceData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String line = null;
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		reader.close();
		// 下发命令和保存数据
		String temp = buffer.toString();
		if (temp.contains("subscriptionId") && temp.contains("value")) {
			LogFile.devLogger.info("[Device data from Nokia platform post] UpdateDeviceData:[{}]",
					new Date(System.currentTimeMillis()) + "  " + temp);
			// 解析数据
			NokiaData data = (NokiaData) JSONObject.toBean(JSONObject.fromObject(temp), NokiaData.class);
			ArrayList<JSONObject> list = (ArrayList<JSONObject>) data.getReports();
			JSONArray jsonArray = JSONArray.fromObject(list);
			JSONObject reportJson = (JSONObject) jsonArray.get(0);
			//就是deviceId 
			String serialNumber = reportJson.optString("serialNumber");
			Device device = iDeviceService.queryBean(serialNumber);
			if(device == null){
				return;
			}
			
			
			String strData = HexUtilty.hexStringToString(reportJson.getString("value"));
            JSONObject strDataJson = JSONObject.fromObject(strData);
            if (strDataJson!=null) {
            	dataHandleService.handleData(device,strDataJson);
			}
		}

	}
	
	
	
	

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value="/updateDeviceData",method=RequestMethod.POST)
	 * public void updateDeviceData(HttpServletRequest
	 * request,HttpServletResponse response) throws Exception { String id =
	 * "";// TODO 未解决id 传参问题
	 * 
	 * Map<String, Object> dataMap = new HashMap<>(); Calendar calendar =
	 * Calendar.getInstance(); long
	 * difference1=calendar.getTimeInMillis();//当前时间总秒数 long difference2 = 0;//
	 * 执行结束后时间毫秒数 //System.out.println("DMP post data time:"+new
	 * Date(System.currentTimeMillis())+" 当前毫秒数:"+difference1);
	 * 
	 * // 读取数据 String line = null; StringBuffer buffer = new StringBuffer();
	 * BufferedReader reader = request.getReader(); while((line =
	 * reader.readLine()) != null) { buffer.append(line); } reader.close();
	 * 
	 * // 下发命令和保存数据 String temp = buffer.toString();
	 * if(temp.contains("subscriptionId") && temp.contains("value")) {
	 * LogFile.devLogger.info(
	 * "[Device data from Nokia platform post] UpdateDeviceData:[{}]", new
	 * Date(System.currentTimeMillis())+"  "+temp); // 解析数据 NokiaData data =
	 * (NokiaData)JSONObject.toBean(JSONObject.fromObject(temp),
	 * NokiaData.class); ArrayList<JSONObject> list =
	 * (ArrayList<JSONObject>)data.getReports();
	 * 
	 * JSONArray jsonArray = JSONArray.fromObject(list); JSONObject reportJson =
	 * (JSONObject)jsonArray.get(0); String strData =
	 * HexUtilty.hexStringToString(reportJson.getString("value"));
	 * 
	 * // 数据json JSONObject json = null;
	 * 
	 * // 垃圾箱下方命令内容 CmdGCEntity gcEntity = null; // 接收到的垃圾箱数据 GCDataBean
	 * gcDataBean = null; // 井盖下方命令内容 CmdMCEntity mcEntity = null; // 接收到的井盖数据
	 * MCDataBean mcDataBean = null; // 下发设备类型 int type = -1; //数据保存到数据库返回结果
	 * boolean bResult = false;
	 * 
	 * // 下发命令组装后的json JSONObject resourceJson = null; // 开始区分设备 // "DATAS":井盖
	 * if(JsonUtil.isGoodJson(strData)) { if(strData.contains("DATAS")) {
	 * if(!strData.contains("result")) // 下发命令井盖回复:result,成功标志 { // 字符串转json
	 * json = JSONObject.fromObject(strData); // json转MCDataBean对象 mcDataBean =
	 * (MCDataBean)JSONObject.toBean(json, MCDataBean.class); if(mcDataBean !=
	 * null) { // 获取下发命令内容 mcEntity =
	 * (CmdMCEntity)CmdMCMap.getObject(mcDataBean.getSN()); if(mcEntity == null)
	 * { mcEntity = new CmdMCEntity(mcDataBean.getSN()); }
	 * 
	 * resourceJson = new JSONObject(); // 转16进制 String strHex =
	 * HexUtilty.strTo16(mcEntity.toString()); resourceJson.put("resourceValue",
	 * strHex); //System.out.println(
	 * "ManholeCover Command send start... content:"+" "
	 * +resourceJson.toString()); dataMap =
	 * idDeviceNokiaNBOperationService.sendCommand(mcDataBean.getSN(),
	 * resourceJson,id); type = 0;
	 * 
	 * bResult = iDeviceCoverService.coverDataProcess(mcDataBean); } } } else //
	 * 垃圾箱-命令 { // 字符串转json json = JSONObject.fromObject(strData); gcDataBean =
	 * (GCDataBean)JSONObject.toBean(json,GCDataBean.class); if(gcDataBean !=
	 * null) { gcEntity = (CmdGCEntity)CmdGCMap.getObject(gcDataBean.getSN());
	 * if(gcEntity == null) { gcEntity = new CmdGCEntity(gcDataBean.getSN()); }
	 * 
	 * resourceJson = new JSONObject(); String strHex =
	 * HexUtilty.strTo16(gcEntity.toString()); resourceJson.put("resourceValue",
	 * strHex); //System.out.println("Dustbin Command send start... content:"+
	 * " "+resourceJson.toString()); dataMap =
	 * idDeviceNokiaNBOperationService.sendCommand(gcDataBean.getSN(),
	 * resourceJson,id); type = 1;
	 * 
	 * bResult = iDeviceDustbinService.dustbinDataProcess(gcDataBean); } }
	 * 
	 * //calendar = Calendar.getInstance(); //difference2 =
	 * calendar.getTimeInMillis();//当前时间总秒数 //System.out.println(
	 * "Command send over time:"+" "+new Date(System.currentTimeMillis())+
	 * " 执行时间总毫秒数:"+Math.abs((difference1-difference2))); //System.out.println(
	 * "Command send result:"+" "+dataMap.get("flag").toString()+" "
	 * +dataMap.get("msg").toString()+" "+dataMap.get("code")); //数据解析及下发日志
	 * LogFile.devLogger.info(
	 * "[Device data from Nokia platform post] parse data:[{}],sendCommand content:[{}],result:[{}]"
	 * , type==0?mcDataBean.toString():gcDataBean.toString(),type==0?mcEntity.
	 * toString():gcEntity.toString(), dataMap.get("flag").toString()+" "
	 * +dataMap.get("msg").toString()+" "+dataMap.get("code")); // 保存数据日志
	 * LogFile.devLogger.info(
	 * "[Save Device data into database from Nokia platform] DeviceType:[{}],result:[{}]"
	 * , type==0?"ManholeCover":"GarbageCan",bResult);
	 * 
	 * } }
	 * 
	 * }
	 */

	
}






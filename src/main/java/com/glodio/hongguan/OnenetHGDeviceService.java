package com.glodio.hongguan;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.glodio.hongguan.bean.Device;
import com.glodio.hongguan.bean.OnenetHGConst;
import com.glodio.hongguan.bean.Sign;
import com.glodio.hwnbiot.StreamClosedHttpResponse;

import com.glodio.nokianbiot.HttpUtilForNokia;
import com.glodio.util.AESUtil;
import com.glodio.util.ConnectPlatformMap;
import com.glodio.util.Dateutil;
import com.glodio.util.HexUtilty;
import com.glodio.util.JsonUtil;
import com.glodio.util.LogFile;
import com.glodio.util.UrlConfiguration;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OnenetHGDeviceService {
	private static final String ERROR_INIT_MESSAGE = "系统内部错误";
    private static final String ERROR_HEAD_MAP = "http head is null";
    
    private static final String HTTP_RETURN_BODY_NULL = "Http:返回信息为null";
    private static final String HTTP_FORBIDDEN_STATUS = "HTTP/1.1 403 Forbidden";
    
    
    /**
     * 添加设备
     * 
     * @param imei device imei
     * @param identifier device model
     * 
     * @return map
     * 
     */
	public static Map<String, Object> registerDevice(String imei,String connectPlatformId) throws IOException, Exception {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 注册设备结果标志
    	// 0:注册成功  1:注册失败 
    	int flag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	String partnerID = getPartnerID(connectPlatformId);
    	String partnerKey = getPartnerKey(connectPlatformId);
    	
    	
    	if(!OnenetHGHeaderMap.getInstance().isEmpty()) {// 判断http-head
    		// body部分
    		Device device = new Device(imei);
    		JSONArray jsonArray = new JSONArray();
    		jsonArray.add(device);
    		Map<String, Object> map = new HashMap<>();
    		map.put("devices", jsonArray);
            // 设备信息转换成json字符串
            String aesBody = AESUtil.encrypt(JsonUtil.jsonObj2Sting(map), OnenetHGConst.SECRETKEY_GAS);
    		
    		// header部分
            Map<String, String> hearerMap =new HashMap<>();
            hearerMap.put("partnerID", partnerID);
            hearerMap.put("sequenceId", String.valueOf(System.currentTimeMillis()));
            hearerMap.put("accessToken", getAccessToken(connectPlatformId));
            String timestamp = Dateutil.getTimestampForStr();
            hearerMap.put("timestamp", timestamp);
        	// sign签名-未加密的body
        	hearerMap.put("sign", Sign.signValue(JsonUtil.jsonObj2Sting(map),partnerID, partnerKey, timestamp));

            System.out.println("Register device header:"+JsonUtil.jsonObj2Sting(hearerMap)+" "+"Init body"+JsonUtil.jsonObj2Sting(map)+" "+" AES body:"+aesBody);
           
            // 获取注册地址信息
            String url = UrlConfiguration.getOnenetHGRegisterDevicesUrl(connectPlatformId);
            // 开始注册设备
            StreamClosedHttpResponse closedHttpResponse = HttpUtilForNokia.doPostJsonForString(url, hearerMap, aesBody);
            // 处理返回结果
            if(closedHttpResponse != null) {// 有返回
            	strStatusCode = closedHttpResponse.getStatusLine().toString();
            	if(strStatusCode.contains("200")) {
            		flag = 0;
            	}
            	if(closedHttpResponse.getContent() != null && (!closedHttpResponse.getContent().equals(""))) {
            		System.out.println(closedHttpResponse.getContent());
            		strMsg = AESUtil.decrypt(closedHttpResponse.getContent(),OnenetHGConst.SECRETKEY_GAS);
            	}
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_HEAD_MAP;
    	}
    	LogFile.cmdLogger.info("menthod:registerDevice,"+"imei:"+imei);
    	LogFile.cmdLogger.info("menthod:registerDevice,"+"result:"+strMsg);    
    	dataMap.put("flag", flag);
    	dataMap.put("code", strStatusCode);
    
        return dataMap;
    }

    /**
     * 删除设备
     *
     * @param String imei
     */
    public static Map<String, Object> deleteDevice(String imei,String connectPlatformId) throws IOException, Exception {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int flag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	String partnerID = getPartnerID(connectPlatformId);
    	String partnerKey = getPartnerKey(connectPlatformId);
    	if(!OnenetHGHeaderMap.getInstance().isEmpty()) {// 判断http-head
        	// 删除设备url信息
            String url = UrlConfiguration.getOnenetHGUnRegisterDeviceUrl(connectPlatformId);
            
            JSONObject initBodyJson = new JSONObject();
            initBodyJson.put("imei", imei);
    		
            // 设备信息转换成json字符串
            String aesBody = AESUtil.encrypt(initBodyJson.toString(), OnenetHGConst.SECRETKEY_GAS);
    		
    		// header部分
            Map<String, String> hearerMap =new HashMap<>();
            hearerMap.put("partnerID", partnerID);
            hearerMap.put("sequenceId", String.valueOf(System.currentTimeMillis()));
            hearerMap.put("accessToken", getAccessToken(connectPlatformId));
            String timestamp = Dateutil.getTimestampForStr();
            hearerMap.put("timestamp", timestamp);
        	// sign签名-未加密的body
        	hearerMap.put("sign", Sign.signValue(initBodyJson.toString(),partnerID, partnerKey, timestamp));

            System.out.println("Delete device header:"+JsonUtil.jsonObj2Sting(hearerMap)+" "+"Init body"+initBodyJson.toString()+" "+" AES body:"+aesBody);
            
            // 删除请求
            StreamClosedHttpResponse closedHttpResponse = HttpUtilForNokia.doPostJsonForString(url, hearerMap,aesBody);
            // 处理返回结果
            if(closedHttpResponse != null) {// 有返回
            	strStatusCode = closedHttpResponse.getStatusLine().toString();
            	if(strStatusCode.contains("200")) {
            		flag = 0;
            	}
            	if(closedHttpResponse.getContent() != null && (!closedHttpResponse.getContent().equals(""))) {
            		strMsg = AESUtil.decrypt(closedHttpResponse.getContent(),OnenetHGConst.SECRETKEY_GAS);
            	}
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_HEAD_MAP;
    	}
    	LogFile.cmdLogger.info("menthod:deleteDevice,"+"imei:"+imei);
    	LogFile.cmdLogger.info("menthod:deleteDevice,"+"result:"+strMsg);
    	dataMap.put("flag", flag);
    	dataMap.put("code", strStatusCode);
    	
    	
        return dataMap;
    }
    
    
    /**
     * 设备信息查询
     *
     * @param String imei
     */
    public static Map<String, Object> queryDeviceInfo(String imei,String connectPlatformId) throws IOException, Exception
    {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int flag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	String partnerID = getPartnerID(connectPlatformId);
    	String partnerKey = getPartnerKey(connectPlatformId);
    	if(!OnenetHGHeaderMap.getInstance().isEmpty()) {// 判断http-head
        	// 删除设备url信息
            String url = UrlConfiguration.getOnenetHGDeviceInfoUrl(connectPlatformId);
            
            JSONObject initBodyJson = new JSONObject();
            initBodyJson.put("deviceId", imei);
            //initBodyJson.put("opCode", opCode);
            
            // 设备信息转换成json字符串
            String aesBody = AESUtil.encrypt(initBodyJson.toString(), OnenetHGConst.SECRETKEY_GAS);
    		
    		// header部分
            Map<String, String> hearerMap =new HashMap<>();
            hearerMap.put("partnerID", partnerID);
            hearerMap.put("sequenceId", String.valueOf(System.currentTimeMillis()));
            hearerMap.put("accessToken", getAccessToken(connectPlatformId));
            String timestamp = Dateutil.getTimestampForStr();
            hearerMap.put("timestamp", timestamp);
        	// sign签名-未加密的body
        	hearerMap.put("sign", Sign.signValue(initBodyJson.toString(),partnerID, partnerKey, timestamp));

            System.out.println("Query device information header:"+JsonUtil.jsonObj2Sting(hearerMap)+" "+"Init body"+initBodyJson.toString()+" "+" AES body:"+aesBody);
             
            // 删除请求
            StreamClosedHttpResponse closedHttpResponse = HttpUtilForNokia.doPostJsonForString(url, hearerMap,aesBody);
            // 处理返回结果
            if(closedHttpResponse != null) {// 有返回
            	strStatusCode = closedHttpResponse.getStatusLine().toString();
            	if(strStatusCode.contains("200")) {
            		flag = 0;
            	}
            	if(closedHttpResponse.getContent() != null && (!closedHttpResponse.getContent().equals(""))) {
            		strMsg = AESUtil.decrypt(closedHttpResponse.getContent(),OnenetHGConst.SECRETKEY_GAS);
            	}
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_HEAD_MAP;
    	}
    	LogFile.cmdLogger.info("menthod:queryDeviceInfo,"+"imei:"+imei);
    	LogFile.cmdLogger.info("menthod:queryDeviceInfo,"+"result:"+strMsg);
    	dataMap.put("flag", flag);
    	dataMap.put("code", strStatusCode);
    	// 返回JSON字符串处理" \" "问题
  		
        
        return dataMap;
    }
    
    
    /**
     * nokia发送device控制命令
     *
     * @param jsonString 发送body-json字符串
     * 
     * @return dataMap
     * 
     * @throws IOException
     * @throws Exception
     */
    public static Map<String, Object> sendCommand(String jsonString,String connectPlatformId)throws IOException, Exception
    {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int flag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if(!OnenetHGHeaderMap.getInstance().isEmpty()) {// 判断token
            String url = UrlConfiguration.getOnenetHGDeviceOpUrl(connectPlatformId);
            
            // 设备信息转换成json字符串
            String aesBody = AESUtil.encrypt(jsonString, OnenetHGConst.SECRETKEY_GAS);
    		
    		// header部分
            Map<String, String> hearerMap =new HashMap<>();
            hearerMap.put("partnerID", OnenetHGConst.PARTNERID_GAS);
            hearerMap.put("sequenceId", String.valueOf(System.currentTimeMillis()));
            hearerMap.put("accessToken", getAccessToken(connectPlatformId));
            String timestamp = Dateutil.getTimestampForStr();
            hearerMap.put("timestamp", timestamp);
        	// sign签名-未加密的body
        	hearerMap.put("sign", Sign.signValue(jsonString,OnenetHGConst.PARTNERID_GAS, OnenetHGConst.PARTNERKEY_GAS, timestamp));

            System.out.println("Send command to device header:"+JsonUtil.jsonObj2Sting(hearerMap)+" "+"Init body"+jsonString+" "+" AES body:"+aesBody);
            
            StreamClosedHttpResponse closedHttpResponse = HttpUtilForNokia.doPostJsonForString(url, hearerMap,aesBody);

            if(closedHttpResponse != null) 
            {// 有返回
            	strStatusCode = closedHttpResponse.getStatusLine().toString();
            	if(strStatusCode.contains("200")) 
            	{
            		flag = 0;
            	}
            	if(closedHttpResponse.getContent() != null && (!closedHttpResponse.getContent().equals(""))) {
            		strMsg = AESUtil.decrypt(closedHttpResponse.getContent(),OnenetHGConst.SECRETKEY_GAS);
            	}
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_HEAD_MAP;
    	}
    	LogFile.cmdLogger.info("menthod:sendCommand,"+"cmd:"+jsonString);
    	LogFile.cmdLogger.info("menthod:sendCommand,"+"result:"+strMsg);
    	dataMap.put("flag", flag);

    	dataMap.put("code", strStatusCode);
    	// 返回JSON字符串处理" \" "问题
  		
  		
        return dataMap;
    }
    


    
    
    public static String getPartnerID(String connectPlatformId){
    	return ConnectPlatformMap.getInstance().get(connectPlatformId).getSmokeDetectorPartnerid();
    }
    
    public static String getPartnerKey(String connectPlatformId){
    	return ConnectPlatformMap.getInstance().get(connectPlatformId).getSmokeDetectorPartnerKey();
    }
    
    public static String getAccessToken(String connectPlatformId){
    	return OnenetHGHeaderMap.getInstance().get(connectPlatformId).get("accessToken");
    }

}

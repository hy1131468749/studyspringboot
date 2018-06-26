package com.glodio.nokianbiot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SpinnerListModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.alibaba.fastjson.JSON;
import com.glodio.hwnbiot.HttpUtil;
import com.glodio.hwnbiot.StreamClosedHttpResponse;
import com.glodio.hwnbiot.beans.Command;
import com.glodio.hwnbiot.beans.CommandBody;
import com.glodio.hwnbiot.beans.DTOCloud2NA;
import com.glodio.hwnbiot.beans.DTONA2Cloud;
import com.glodio.hwnbiot.beans.DeviceInfo;
import com.glodio.hwnbiot.beans.ExceptionMsg;
import com.glodio.hwnbiot.beans.NBCommonConst;
import com.glodio.hwnbiot.beans.NBErrorCode;
import com.glodio.hwnbiot.maps.AuthHeaderMap;
import com.glodio.util.HexUtilty;
import com.glodio.util.JsonUtil;
import com.glodio.util.SystemKeyValueMap;
import com.glodio.util.UrlConfiguration;
import com.glodio.util.Uuid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NokiaDeviceService {
	private static final String ERROR_INIT_MESSAGE = "系统内部错误";
    private static final String ERROR_HEAD_MAP = "http head is null";
    
    private static final String HTTP_RETURN_BODY_NULL = "Http:返回信息为null";
    private static final String HTTP_FORBIDDEN_STATUS = "HTTP/1.1 403 Forbidden";
    
    
    /**
     * 添加设备
     * 
     * @param imei device imei
     * @param identifier device model
     * @param id 
     * 
     * @return map
     * 
     */
	public static Map<String, Object> registerDevice(String imei,String identifier, String id) throws IOException, Exception {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 注册设备结果标志
    	// 0:注册成功  1:注册失败 
    	int flag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
            
/*          { "serialNumber": "932742369734",
            "protocol":"NBIOT-SGI",
            "identifier":"IND_KAR_BLR_NBIOT_001",
            "address":"9182987654322",
            "groupName":"DM.MFORM",
            "additionalParams":{
            "IP":"192.168.0.112",
            "PORT":"8080",
            "SGIID":"892376",
            "IMSI":"21423573963325326"}}’*/
    	
    	if(NokiaAuthHeaderMap.getInstance().size() > 0) {// 判断http-head
            Map<String,Object> map = new HashMap<>();
            map.put("serialNumber", imei);
            map.put("protocol", "NBIOT-SGI");
            //map.put("identifier", SystemKeyValueMap.getInstance().get("nokia_identifier"));
            map.put("identifier", identifier);
            String ip = imei2IP(imei);
            map.put("address", ip);
            map.put("groupName", SystemKeyValueMap.getInstance().get("nokia_group_name"));
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("IP", ip);
            jsonObject.put("SGIID", "nokia-coap");
            jsonObject.put("IMSI", imei);
            map.put("additionalParams", jsonObject);

            // 设备信息转换成json字符串
            String jsonRequest = JsonUtil.jsonObj2Sting(map);
            System.out.println("Register device content:"+" "+jsonRequest);
           
            // 获取注册地址信息
            String url = UrlConfiguration.getNokiaDeviceRegisterUrl(id);
            // 开始注册设备
            StreamClosedHttpResponse closedHttpResponse = HttpUtilForNokia.doPostJsonForString(url, NokiaAuthHeaderMap.getInstance().get(id), jsonRequest);
            // 处理返回结果
            if(closedHttpResponse != null) {// 有返回
            	strStatusCode = closedHttpResponse.getStatusLine().toString();
            	strMsg = closedHttpResponse.getContent();
            	if(strStatusCode.contains("200")) {
            		flag = 0;
            	}
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_HEAD_MAP;
    	}
            
    	dataMap.put("flag", flag);
    	dataMap.put("msg", strMsg);
    	dataMap.put("code", strStatusCode);
  
        return dataMap;
    }
	
	public static String imei2IP(String imei) {
		String strTmep = imei.substring(imei.length()-8, imei.length());
		
		String ip = "";
		for(int i = 0;i < 4;i++) {
			ip += strTmep.substring(i*2,i*2+2);
			if(i != 3) {
				ip += ".";
			}
		}
		
		return ip;
	}

    /**
     * 删除设备
     * @param id 
     *
     * @param String imei
     */
    
    public static Map<String, Object> deleteDevice(String imei, String id) throws IOException, Exception
    {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int flag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if(NokiaAuthHeaderMap.getInstance().size() > 0) {// 判断http-head
        	// 删除设备url信息
            String url = UrlConfiguration.getNokiaDeviceDeleteUrl(imei,id);
            
            // 删除请求
            StreamClosedHttpResponse closedHttpResponse = HttpUtilForNokia.doDeleteForString(url, NokiaAuthHeaderMap.getInstance().get(id));
            // 处理返回结果
            if(closedHttpResponse != null) {// 有返回
            	strStatusCode = closedHttpResponse.getStatusLine().toString();
            	strMsg = closedHttpResponse.getContent();
            	if(strStatusCode.contains("200")) {
            		flag = 0;
            	}
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_HEAD_MAP;
    	}

    	dataMap.put("flag", flag);
    	dataMap.put("msg", strMsg);
    	dataMap.put("code", strStatusCode);
        
        return dataMap;
    }
    
    
    /**
     * 删除设备, 包含网关类型设备和一般设备
     *
     * @param deviceId 设备ID
     * @param appId
     */
    /*    	  "criteria": {
    "manufacturerData": {
      "firmwareVersion": "string",
      "make": "string",
      "model": "string"
    },
    "serialNumbers": [
      "string"
    ]
  },
  "deletionPolicy": 0,
  "groupName": "string",
  "resources": [
    {
      "conditions": {
        "gt": 0,
        "lt": 0,
        "pmin": 0,
        "steps": 0
      },
      "resourcePath": "string"
    }
  ],
  "subscriptionType": "string"
	}*/
    
/*    public static Map<String, Object> subscribeResource(String imei) throws IOException, Exception
    {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int iFlag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if(NokiaAuthHeaderMap.getInstance().size() > 0) {// 判断token
            
            // 设备信息转换成json字符串
            //String jsonRequest = JsonUtil.jsonObj2Sting(map);
           
            // 获取注册地址信息
            String url = UrlConfiguration.getNokiaDeviceRegisterUrl();
            // 开始注册设备
            StreamClosedHttpResponse httpResponse = HttpUtilForNokia.doPostJsonForString(url, NokiaAuthHeaderMap.getInstance(), jsonRequest);
         
            // 处理返回结果
            if(httpResponse != null) {// 有返回
            	strStatusCode = httpResponse.getStatusLine().toString();
            	System.out.println(strStatusCode);
            	
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_AUTH_TOKEN;
    	}

    	dataMap.put("flag", iFlag);
    	dataMap.put("msg", strMsg);
    	dataMap.put("code", strStatusCode);
        
        return dataMap;
    }*/
    
    /**
     * nokia发送device控制命令
     *
     * @param imei 应用appId
     * @param jsonObject 命令json
     * @param id 
     * 
     * @return dataMap
     * 
     * @throws IOException
     * @throws Exception
     */
    public static Map<String, Object> sendCommand(String imei,Object jsonObject, String id)throws IOException, Exception
    {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int flag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if(NokiaAuthHeaderMap.getInstance().size() > 0) {// 判断token
            String url = UrlConfiguration.getNokiaDeviceCommandUrl(imei,id);

            String jsonRequest = JsonUtil.jsonObj2Sting(jsonObject);
            
            //System.out.println("url:"+url);
            //System.out.println("send content:"+jsonRequest);
            
            //System.out.println("url: "+url+"  command:"+jsonRequest+" AuthHeaderMap.size:"+AuthHeaderMap.getInstance().size());
            
            StreamClosedHttpResponse closedHttpResponse = HttpUtilForNokia.doPutJsonForString(url,NokiaAuthHeaderMap.getInstance().get(id), jsonRequest);

            if(closedHttpResponse != null) 
            {// 有返回
            	strStatusCode = closedHttpResponse.getStatusLine().toString();
            	strMsg = closedHttpResponse.getContent();
            	//System.out.println("Command result statusCode:"+" "+strStatusCode +" "+" message:"+" "+strMsg);
            	if(strStatusCode.contains("202")) 
            	{
            		flag = 0;
            	}
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_HEAD_MAP;
    	}
        
    	dataMap.put("flag", flag);
/*    	if(iFlag == 0) {
       		Map<String,Object> mapTypes = JSON.parseObject(strMsg);
    		dataMap.put("msg", mapTypes);
    	}
    	else {
    		
    	}*/
    	dataMap.put("msg", strMsg);
    	dataMap.put("code", strStatusCode);
        
        return dataMap;
    }


    
   
    

}

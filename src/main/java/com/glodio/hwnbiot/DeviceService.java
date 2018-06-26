package com.glodio.hwnbiot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.alibaba.fastjson.JSON;
import com.glodio.bean.ConnectPlatform;
import com.glodio.hwnbiot.beans.Command;
import com.glodio.hwnbiot.beans.CommandBody;
import com.glodio.hwnbiot.beans.DTOCloud2NA;
import com.glodio.hwnbiot.beans.DTONA2Cloud;
import com.glodio.hwnbiot.beans.DeviceInfo;
import com.glodio.hwnbiot.beans.ExceptionMsg;
import com.glodio.hwnbiot.beans.NBCommonConst;
import com.glodio.hwnbiot.beans.NBErrorCode;
import com.glodio.hwnbiot.maps.AuthHeaderMap;
import com.glodio.util.ConnectPlatformMap;
import com.glodio.util.JsonUtil;
import com.glodio.util.SystemKeyValueMap;
import com.glodio.util.UrlConfiguration;
import com.glodio.util.Uuid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DeviceService {
	private static final String ERROR_INIT_MESSAGE = "系统内部错误";
	private static final String ERROR_KEY_VALUE = "系统参数获取不成功";
    private static final String ERROR_AUTH_TOKEN = "NBIoT_token不存在";
    
    private static final String HTTP_RETURN_BODY_NULL = "NBIoT:返回信息为null";
    private static final String HTTP_FORBIDDEN_STATUS = "HTTP/1.1 403 Forbidden";
    
    
    /**
     * 注册直连设备
     * @return AddDeviceDTOCloud2NA
     * 
     *
     */
	public static Map<String, Object> registerDevice(String imei,String id) throws IOException, Exception {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 注册设备结果标志
    	// 0:注册成功  1:注册失败 
    	int iFlag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if(AuthHeaderMap.getInstance().size() > 0) {// 判断token
        	// 定义设备相关信息
            DTONA2Cloud dtona2Cloud = new DTONA2Cloud();
            
            // 设备附加码信息-用imei代替
            dtona2Cloud.setVerifyCode(imei.toUpperCase());
            // 设置设备编码-用imei代替
            dtona2Cloud.setNodeId(imei.toUpperCase());
            // 设置设备所属当前用户
            //dtona2Cloud.setEndUserId("currentuser");
            dtona2Cloud.setEndUserId(imei.toUpperCase());
            // 设置添加设备有效时间-0不限时间
            dtona2Cloud.setTimeout(0);
            // 设备信息转换成json字符串
            String jsonRequest = JsonUtil.jsonObj2Sting(dtona2Cloud);
           
            // 获取注册地址信息
            String url = UrlConfiguration.getDeviceRegisterUrl(id);
            // 开始注册设备
            StreamClosedHttpResponse httpResponse = HttpUtil.doPostJsonForString(url, AuthHeaderMap.getInstance().get(id), jsonRequest,id);
            // 处理返回结果
            if(httpResponse != null) {// 有返回
            	strStatusCode = httpResponse.getStatusLine().toString();

            	if (httpResponse.getContent().contains("error_code")) {
                	ExceptionMsg errorResponse = JsonUtil.jsonString2SimpleObj(httpResponse.getContent(), ExceptionMsg.class);
                    if(errorResponse.getError_code() != null) {
                    	strMsg = NBErrorCode.getHttpErrorMsg(errorResponse.getError_code());
                    	if(strMsg.equals("NBIoT_其他错误")) {
                    		strMsg = errorResponse.getError_desc();
                    	}
                    }
                }
            	else {// 结果正常
            		iFlag = 0;
            		DTOCloud2NA cloud2na = JsonUtil.jsonString2SimpleObj(httpResponse.getContent(), DTOCloud2NA.class);
            		strMsg = cloud2na.getDeviceId();
            	}
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
    }

    /**
     * 删除设备, 包含网关类型设备和一般设备
     *
     * @param deviceId 设备在华为平台上注册产生的id
     * 
     */
    
    public static Map<String, Object> deleteDevice(String deviceId,String id) throws IOException, Exception
    {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int iFlag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if(AuthHeaderMap.getInstance().size() > 0) {// 判断token
        	// 删除设备url信息
            String url = UrlConfiguration.getDeviceDeleteUrl(deviceId,id);
            url = url+"?appId="+SystemKeyValueMap.getInstance().get("nb_appId");
            
            // 删除请求
            StreamClosedHttpResponse httpResponse = HttpUtil.doDeleteForString(url, AuthHeaderMap.getInstance().get(id),id);
            // 处理返回结果
            if(httpResponse != null) {// 有返回
            	strStatusCode = httpResponse.getStatusLine().toString();

            	if (httpResponse.getContent()!= null && httpResponse.getContent().contains("error_code")) {
                	ExceptionMsg errorResponse = JsonUtil.jsonString2SimpleObj(httpResponse.getContent(), ExceptionMsg.class);
                    if(errorResponse.getError_code() != null) {
                    	strMsg = NBErrorCode.getHttpErrorMsg(errorResponse.getError_code());
                    	if(strMsg.equals("NBIoT_其他错误")) {
                    		strMsg = errorResponse.getError_desc();
                    	}
                    }
                }
            	else {// 结果正常
                	iFlag = 0;
            		if(strStatusCode.contains("204") && httpResponse.getContent() == null)  {
            			strMsg = "NBIoT_设备删除成功";
            		}
            		else {
            			strMsg = httpResponse.getContent();
            		}
            		
            	}
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
    }

    /**
     * 修改设置设备信息
     *
     * @param deviceId 设备ID
     * @param request 设备信息对象
     * @param type 设备类型,type：设备类型 0-井盖2.0 ,1-垃圾箱 2.0,2-环保3.0 ,3-烟感监测1.0 ,4-环保1.0 
     */
    
    public static Map<String, Object> setDeviceInfor(String imei,String deviceId,int type,String id) throws IOException, Exception
    {
    	// type：设备类型 0-井盖2.0 ,1-垃圾箱 2.0,2-环保3.0 ,3-烟感监测1.0 ,4-环保1.0 
    	String model = "";
    	String deviceType = "";
    	
    	if(type == 0) 
    	{
        	model = "MC-200";
        	deviceType = "ManholeCover";
    	} 
    	else if(type == 1)
    	{
    		model = "GC-200";
    		deviceType = "GarbageCan";
    	}
    	else if(type == 2) 
    	{
    		model = "EM-300";
    		deviceType = "EnvironmentMonitor";
    	}
    	else if(type == 3) 
    	{
    		model = "SD-100";
    		deviceType = "SafetyDetector";
    	}
    	else if(type == 4)
    	{
    		model = "EM-100";
    		deviceType = "EnvironmentMonitor";
    	}

    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int iFlag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if((!model.equals("")) && (!deviceType.equals(""))) {
        	if(AuthHeaderMap.getInstance().size() > 0) {// 判断token
                // 设备信息对象转换成json字符串
                if(SystemKeyValueMap.getInstance().size() > 0) {
                	// 设置设备信息url
                    String url = UrlConfiguration.getDeviceUpdateUrl(deviceId,id);
                    url = url+"?appId="+ConnectPlatformMap.getInstance().get(id).getNbAppid();
                	
                    DeviceInfo deviceInfo = new DeviceInfo();
                    deviceInfo.setNodeId(imei);
                    deviceInfo.setManufacturerId(ConnectPlatformMap.getInstance().get(id).getNbManufacturerId());
                    deviceInfo.setManufacturerName(ConnectPlatformMap.getInstance().get(id).getNbManufacturerName());
              
                   // deviceInfo.setModel(SystemKeyValueMap.getInstance().get("nb_model"));
                    deviceInfo.setModel(model);
                    deviceInfo.setProtocolType(ConnectPlatformMap.getInstance().get(id).getNbProtocolType());
                    //deviceInfo.setDeviceType(SystemKeyValueMap.getInstance().get("nb_device_type"));
                    deviceInfo.setDeviceType(deviceType);
                    
                    String jsonInfor = JsonUtil.jsonObj2Sting(deviceInfo);
                    
                    // 发送设置设备信息请求
                    StreamClosedHttpResponse httpResponse = HttpUtil.doPutJsonForString(url,AuthHeaderMap.getInstance().get(id), jsonInfor,id);

                    if(httpResponse != null) {// 有返回
                    	strStatusCode = httpResponse.getStatusLine().toString();

                    	if (httpResponse.getContent()!= null && httpResponse.getContent().contains("error_code")) {
                        	ExceptionMsg errorResponse = JsonUtil.jsonString2SimpleObj(httpResponse.getContent(), ExceptionMsg.class);
                            if(errorResponse.getError_code() != null) {
                            	strMsg = NBErrorCode.getHttpErrorMsg(errorResponse.getError_code());
                            	if(strMsg.equals("NBIoT_其他错误")) {
                            		strMsg = errorResponse.getError_desc();
                            	}
                            }
                        }
                    	else {// 结果正常
                        	iFlag = 0;
                    		if(strStatusCode.contains("204") && httpResponse.getContent() == null)  {
                    			strMsg = "NBIoT_设置设备信息成功";
                    		}
                    		else {
                    			strMsg = httpResponse.getContent();
                    		}
                    	}
                    }
                    else {// 返回为空
                    	strMsg = HTTP_RETURN_BODY_NULL;
                    }
                }
                else {
                	strMsg = ERROR_KEY_VALUE;
                }
        	}
        	else {
        		strMsg = ERROR_AUTH_TOKEN;
        	}
    	}
        
    	dataMap.put("flag", iFlag);
    	dataMap.put("msg", strMsg);
    	dataMap.put("code", strStatusCode);
        
        return dataMap;
    }
    
    /*
     * 查询单个设备信息
     * 
     * 
     **/
    public static Map<String, Object> queryDevice(String deviceId,String appId,String id) throws IOException, Exception
    {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int iFlag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if(AuthHeaderMap.getInstance().size() > 0) {// 判断token
        	
        	// 查询设备信息url
        	String url = UrlConfiguration.getDataDeviceUrl(deviceId,id);
        	url = url+"?appId="+appId;
        	
        	//发送查询请求,返回查询信息，判断查询信息
        	HttpResponse httpResponse = HttpUtil.doGet(url, AuthHeaderMap.getInstance().get(id),id);
        	String responseBody = HttpUtil.getHttpResponseBody(httpResponse);
        	
            if(responseBody != null) {// 有返回
            	strStatusCode = httpResponse.getStatusLine().toString();

            	if (responseBody.contains("error_code")) {
                	ExceptionMsg errorResponse = JsonUtil.jsonString2SimpleObj(responseBody, ExceptionMsg.class);
                    if(errorResponse.getError_code() != null) {
                    	strMsg = NBErrorCode.getHttpErrorMsg(errorResponse.getError_code());
                    	if(strMsg.equals("NBIoT_其他错误")) {
                    		strMsg = errorResponse.getError_desc();
                    	}
                    }
                }
            	else {// 结果正常
                	iFlag = 0;
                	strMsg = responseBody;
            	}
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_AUTH_TOKEN;
    	}
        
    	dataMap.put("flag", iFlag);
    	
    	if(iFlag == 0) {
       		Map<String,Object> mapTypes = JSON.parseObject(strMsg);
    		dataMap.put("msg", mapTypes);
    	}
    	else {
    		dataMap.put("msg", strMsg);
    	}
    
    	dataMap.put("code", strStatusCode);
    	
		return dataMap;
	}
    
    /*
     * 查询全部设备信息
     * 
     * 
     **/
    public static Map<String, Object> queryAllDevice(String appId,int pageNo,int pageSize,String id) throws IOException, Exception
    {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int iFlag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if(AuthHeaderMap.getInstance().size() > 0) {// 判断token
        	
        	// 查询所有设备信息url
        	String url = UrlConfiguration.getDataDevicesUrl(id);
        	url = url+"?appId="+appId+"&pageNo="+pageNo+"&pageSize="+pageSize;
        	
        	HttpResponse httpResponse = HttpUtil.doGet(url, AuthHeaderMap.getInstance().get(id),id);
        	String responseBody = HttpUtil.getHttpResponseBody(httpResponse);
        	
            if(responseBody != null) {// 有返回
            	strStatusCode = httpResponse.getStatusLine().toString();

            	if (responseBody.contains("error_code")) {
                	ExceptionMsg errorResponse = JsonUtil.jsonString2SimpleObj(responseBody, ExceptionMsg.class);
                    if(errorResponse.getError_code() != null) {
                    	strMsg = NBErrorCode.getHttpErrorMsg(errorResponse.getError_code());
                    	if(strMsg.equals("NBIoT_其他错误")) {
                    		strMsg = errorResponse.getError_desc();
                    	}
                    }
                }
            	else {// 结果正常
                	iFlag = 0;
            		strMsg = responseBody;
            	}
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_AUTH_TOKEN;
    	}

    	dataMap.put("flag", iFlag);
    	if(iFlag == 0) {
       		Map<String,Object> mapTypes = JSON.parseObject(strMsg);
    		dataMap.put("msg", mapTypes);
    	}
    	else {
    		dataMap.put("msg", strMsg);
    	}
    	dataMap.put("code", strStatusCode);
    	
		return dataMap;
		
	}
    
    
    /**
     * 发送device控制命令
     *
     * @param appId 应用appId
     * @param commandBody 控制命令参数包括header和body
     * @return dataMap
     * @throws IOException
     */
    public static Map<String, Object> sendCommand(String appId,Object jsonObject,String id)throws IOException, Exception
    {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int iFlag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if(AuthHeaderMap.getInstance().size() > 0) {// 判断token
            String url = UrlConfiguration.getDeviceCommandUrl(id);
            url = url+"?appId="+appId;

            String jsonRequest = JsonUtil.jsonObj2Sting(jsonObject);
            
            //System.out.println("send content:"+jsonRequest);
            
            //System.out.println("url: "+url+"  command:"+jsonRequest+" AuthHeaderMap.size:"+AuthHeaderMap.getInstance().size());
            
            HttpResponse httpResponse = HttpUtil.doPostJson(url,AuthHeaderMap.getInstance().get(id), jsonRequest,id);

            String responseBody = HttpUtil.getHttpResponseBody(httpResponse);
            if(responseBody != null) {// 有返回
            	strStatusCode = httpResponse.getStatusLine().toString();

            	if (responseBody.contains("error_code")) {
                	ExceptionMsg errorResponse = JsonUtil.jsonString2SimpleObj(responseBody, ExceptionMsg.class);
                    if(errorResponse.getError_code() != null) {
                    	strMsg = NBErrorCode.getHttpErrorMsg(errorResponse.getError_code());
                    	if(strMsg.equals("NBIoT_其他错误")) {
                    		strMsg = errorResponse.getError_desc();
                    	}
                    }
                }
            	else {// 结果正常
                	iFlag = 0;
            		strMsg = responseBody;
            	}
            }
            else {// 返回为空
            	strMsg = HTTP_RETURN_BODY_NULL;
            }
    	}
    	else {
    		strMsg = ERROR_AUTH_TOKEN;
    	}
        
    	dataMap.put("flag", iFlag);
    	if(iFlag == 0) {
       		Map<String,Object> mapTypes = JSON.parseObject(strMsg);
    		dataMap.put("msg", mapTypes);
    	}
    	else {
    		dataMap.put("msg", strMsg);
    	}
    	dataMap.put("code", strStatusCode);
        
        return dataMap;
    }
    
    
    /**
     * 批量发送device控制命令
     *
     * @param body 控制命令参数包括BatchParam和command
     * @return dataMap
     * @throws IOException
     */
	public static Map<String, Object> sendCommands(Object jsonObject,String id)throws IOException, Exception
    {
    	Map<String, Object> dataMap = new HashMap<>();
    	// 0:成功  1:失败 
    	int iFlag = 1;
    	// 返回消息
    	String strMsg = ERROR_INIT_MESSAGE;
    	// http请求返回状态码
    	String strStatusCode = HTTP_FORBIDDEN_STATUS;
    	
    	if(AuthHeaderMap.getInstance().size() > 0) {// 判断token
            String url = UrlConfiguration.getDeviceCommandsUrl(id);

            String jsonRequest = JsonUtil.jsonObj2Sting(jsonObject);
            //System.out.println(jsonRequest);
            
            StreamClosedHttpResponse httpResponse = HttpUtil.doPostJsonGetStatusLine(url,AuthHeaderMap.getInstance().get(id), jsonRequest,id);
            
            if(httpResponse != null) {// 有返回
            	strStatusCode = httpResponse.getStatusLine().toString();

            	if (httpResponse.getContent()!= null && httpResponse.getContent().contains("error_code")) {
                	ExceptionMsg errorResponse = JsonUtil.jsonString2SimpleObj(httpResponse.getContent(), ExceptionMsg.class);
                    if(errorResponse.getError_code() != null) {
                    	strMsg = NBErrorCode.getHttpErrorMsg(errorResponse.getError_code());
                    	if(strMsg.equals("NBIoT_其他错误")) {
                    		strMsg = errorResponse.getError_desc();
                    	}
                    }
                }
            	else {// 结果正常
                	iFlag = 0;
            		strMsg = httpResponse.getContent();
            	}
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
    }
	
	public static Map<String, Object> sendCommandForSetThreshold(String appId,String imei,String deviceId,String[] strArrayIF,String[] strArrayThresold,String id) throws Exception {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId(deviceId);
    	commandBody.setCallbackURL(UrlConfiguration.getCmdConfirmCallbackUrl(id));
    	Command command = new Command();
    	command.setServiceId(NBCommonConst.APP_HWNBIOT_SERVICCE_ID);
    	command.setMethod(NBCommonConst.APP_HWNBIOT_COMMON_SET_PARAMETER_COMMAND);
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", imei);
    	jsonObject.put("CMD", "TCST");
    	jsonObject.put("FLOW", Uuid.getUUID());
    	System.out.println("Set Threshold flow:"+jsonObject.getString("FLOW"));
    	jsonObject.put("TIME", String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000)))));
    	JSONArray jsonArray = null;
    	if(strArrayIF.length == strArrayThresold.length) {
    		jsonArray = new JSONArray();
    		JSONObject tempJson = null;
    		for(int i = 0;i < strArrayIF.length;i++) {
    			tempJson = new JSONObject();
    			tempJson.put("IF", Integer.parseInt(strArrayIF[i]));
    			String[] strArrayTemp = strArrayThresold[i].split(":");
    			if(strArrayTemp.length > 0) {
    				if(strArrayTemp.length == 2) {
    					if(!strArrayTemp[0].equals("null")) {
    						tempJson.put("LTH", Integer.parseInt((strArrayTemp[0])));
    					}
    					if(!strArrayTemp[1].equals("null")) {
    						tempJson.put("HTH", Integer.parseInt(strArrayTemp[1]));
    					}
    				}
        			else {
        				tempJson.put("HTH", Integer.parseInt(strArrayTemp[0]));
        			}
    			}
    			
    			jsonArray.add(tempJson);
    		}
    	}
    	
    	jsonObject.put("DATAS", jsonArray);
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	Map<String, Object> dataMap = new HashMap<>();
		dataMap = sendCommand(appId, commandBody,id);

    	return dataMap;
	}
	
	public static Map<String, Object> sendCommandForSetTimeInterval(String appId,String imei,String deviceId,int reportInterval,int beatInterval,String id) throws Exception {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId(deviceId);
    	commandBody.setCallbackURL(UrlConfiguration.getCmdConfirmCallbackUrl(id));
    	Command command = new Command();
    	command.setServiceId(NBCommonConst.APP_HWNBIOT_SERVICCE_ID);
    	command.setMethod(NBCommonConst.APP_HWNBIOT_COMMON_SET_PARAMETER_COMMAND);
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", imei);
    	jsonObject.put("CMD", "CST");
    	jsonObject.put("FLOW", Uuid.getUUID());
    	System.out.println("Set TimeInterval flow:"+jsonObject.getString("FLOW"));
    	jsonObject.put("TIME", String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000)))));
    	Map<String, Object> datas = new HashMap<>();
    	datas.put("reportInterval", reportInterval);
    	datas.put("beaconInterval", beatInterval);
    	jsonObject.put("DATAS", datas);
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	Map<String, Object> dataMap = new HashMap<>();
		dataMap = sendCommand(appId, commandBody,id);

    	return dataMap;
	}
	
	public static Map<String, Object> sendCommandForAlarmClear(String appId,String imei,String deviceId,String id) throws Exception {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId(deviceId);
    	commandBody.setCallbackURL(UrlConfiguration.getCmdConfirmCallbackUrl(id));
    	Command command = new Command();
    	command.setServiceId(NBCommonConst.APP_HWNBIOT_SERVICCE_ID);
    	command.setMethod(NBCommonConst.APP_HWNBIOT_COMMON_SET_COMMAND);
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", imei);
    	jsonObject.put("CMD", "ACLR");
    	jsonObject.put("FLOW", Uuid.getUUID());
    	System.out.println("Alarm Clear flow:"+jsonObject.getString("FLOW"));
    	jsonObject.put("TIME", String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000)))));
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	Map<String, Object> dataMap = new HashMap<>();
		dataMap = sendCommand(appId, commandBody,id);

    	return dataMap;
	}
	
	public static Map<String, Object> sendCommandForAlarmMute(String appId,String imei,String deviceId,String id) throws Exception {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId(deviceId);
    	commandBody.setCallbackURL(UrlConfiguration.getCmdConfirmCallbackUrl(id));
    	Command command = new Command();
    	command.setServiceId(NBCommonConst.APP_HWNBIOT_SERVICCE_ID);
    	command.setMethod(NBCommonConst.APP_HWNBIOT_COMMON_SET_COMMAND);
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", imei);
    	jsonObject.put("CMD", "AMUTE");
    	jsonObject.put("FLOW", Uuid.getUUID());
    	System.out.println("Alarm Mute flow:"+jsonObject.getString("FLOW"));
    	jsonObject.put("TIME", String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000)))));
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	Map<String, Object> dataMap = new HashMap<>();
		dataMap = sendCommand(appId, commandBody,id);

    	return dataMap;
	}
	
	public static Map<String, Object> sendCommandForReboot(String appId,String imei,String deviceId,String id) throws Exception {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId(deviceId);
    	commandBody.setCallbackURL(UrlConfiguration.getCmdConfirmCallbackUrl(id));
    	Command command = new Command();
    	command.setServiceId(NBCommonConst.APP_HWNBIOT_SERVICCE_ID);
    	command.setMethod(NBCommonConst.APP_HWNBIOT_COMMON_SET_COMMAND);
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", imei);
    	jsonObject.put("CMD", "RBT");
    	jsonObject.put("FLOW", Uuid.getUUID());
    	System.out.println("Reboot flow:"+jsonObject.getString("FLOW"));
    	jsonObject.put("TIME", String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000)))));
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	Map<String, Object> dataMap = new HashMap<>();
		dataMap = sendCommand(appId, commandBody,id);

    	return dataMap;
	}
	
	public static Map<String, Object> sendCommandForRE(String appId,String imei,String deviceId, String id) throws Exception {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId(deviceId);
    	commandBody.setCallbackURL(UrlConfiguration.getCmdConfirmCallbackUrl(id));
    	Command command = new Command();
    	command.setServiceId(NBCommonConst.APP_HWNBIOT_SERVICCE_ID);
    	command.setMethod(NBCommonConst.APP_HWNBIOT_COMMON_RE_COMMAND);
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", imei);
    	jsonObject.put("RE", "true");
    	jsonObject.put("FLOW", Uuid.getUUID());
    	System.out.println("Heart flow:"+jsonObject.getString("FLOW"));
    	jsonObject.put("TIME", String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000)))));
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	Map<String, Object> dataMap = new HashMap<>();
		dataMap = sendCommand(appId, commandBody,id);

    	return dataMap;
	}
	
	public static Map<String, Object> sendCommandForSensorData(String appId,String imei,String deviceId, String id) throws Exception {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId(deviceId);
    	commandBody.setCallbackURL(UrlConfiguration.getCmdConfirmCallbackUrl(id));
    	Command command = new Command();
    	command.setServiceId(NBCommonConst.APP_HWNBIOT_SERVICCE_ID);
    	command.setMethod(NBCommonConst.APP_HWNBIOT_COMMON_SET_COMMAND);
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", imei);
    	jsonObject.put("CMD", "SDG");
    	jsonObject.put("FLOW", Uuid.getUUID());
    	System.out.println("Sensor Data flow:"+jsonObject.getString("FLOW"));
    	jsonObject.put("TIME", String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000)))));
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	Map<String, Object> dataMap = new HashMap<>();
		dataMap = sendCommand(appId, commandBody,id);

    	return dataMap;
	}
	
	public static Map<String, Object> sendCommandForGetThreshold(String appId,String imei,String deviceId, String id) throws Exception {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId(deviceId);
    	commandBody.setCallbackURL(UrlConfiguration.getCmdConfirmCallbackUrl(id));
    	Command command = new Command();
    	command.setServiceId(NBCommonConst.APP_HWNBIOT_SERVICCE_ID);
    	command.setMethod(NBCommonConst.APP_HWNBIOT_COMMON_SET_COMMAND);
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", imei);
    	jsonObject.put("CMD", "TCDG");
    	jsonObject.put("FLOW", Uuid.getUUID());
    	System.out.println("Get Threshold flow:"+jsonObject.getString("FLOW"));
    	jsonObject.put("TIME", String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000)))));
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	Map<String, Object> dataMap = new HashMap<>();
		dataMap = sendCommand(appId, commandBody,id);

    	return dataMap;
	}
	
	public static Map<String, Object> sendCommandForGetTimeInterval(String appId,String imei,String deviceId, String id) throws Exception {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId(deviceId);
    	commandBody.setCallbackURL(UrlConfiguration.getCmdConfirmCallbackUrl(id));
    	Command command = new Command();
    	command.setServiceId(NBCommonConst.APP_HWNBIOT_SERVICCE_ID);
    	command.setMethod(NBCommonConst.APP_HWNBIOT_COMMON_SET_COMMAND);
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", imei);
    	jsonObject.put("CMD", "CDG");
    	jsonObject.put("FLOW", Uuid.getUUID());
    	//System.out.println("Get TimeInterval flow:"+jsonObject.getString("FLOW"));
    	jsonObject.put("TIME", String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000)))));
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	Map<String, Object> dataMap = new HashMap<>();
		dataMap = sendCommand(appId, commandBody,id);

    	return dataMap;
	}
	
	public static Map<String, Object> sendCommandForGetIFConfig(String appId,String imei,String deviceId, String id) throws Exception {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId(deviceId);
    	commandBody.setCallbackURL(UrlConfiguration.getCmdConfirmCallbackUrl(id));
    	Command command = new Command();
    	command.setServiceId(NBCommonConst.APP_HWNBIOT_SERVICCE_ID);
    	command.setMethod(NBCommonConst.APP_HWNBIOT_COMMON_SET_COMMAND);
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", imei);
    	jsonObject.put("CMD", "ICDG");
    	jsonObject.put("FLOW", Uuid.getUUID());
    	System.out.println("Get Ifconfig flow:"+jsonObject.getString("FLOW"));
    	jsonObject.put("TIME", String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000)))));
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	Map<String, Object> dataMap = new HashMap<>();
		dataMap = sendCommand(appId, commandBody,id);

    	return dataMap;
	}
	
	
    
/*    private static void checkSendCommandReponseBody(String responseBody, String deviceId) throws ServiceException
    {
        if(responseBody == null)
        {
            //log.error("(responseBody is null, fail to translate jsonResponse to String");
            //throw new ServiceException(NA_NSCL_GENERATE_DEVICE_VERIFICATION_NSCL_INVALID);
        }

        if ((responseBody.contains("error_code")) && (responseBody.contains(
                DEVICE_NOTONLINE)))
        {
            //log.error("The device is not online. response body:{}", responseBody);
            //throw new ServiceException(ExpandExceptionEnum.APP_NA_COMMAND_DEVICE_NOTONLINE);
        }
        else if ((responseBody.contains("error_code")) && (responseBody.contains(
                DEVICE_COMMAND_MUTE)))
        {
            //log.error("The device command is muted. response body:{}", responseBody);
            //throw new ServiceException(ExpandExceptionEnum.APP_NA_COMMAND_DEVICE_MUTED);
        }
        else if ((responseBody.contains("error_code")))
        {
           // log.error("response fail when send device command to nscl. response body:{}", responseBody);
            //throw new ServiceException(ExpandExceptionEnum.APP_NA_COMMAND_DEVICE_FAILED);
        }
    }*/
    
    public static void main(String[] args, String id) {
    	CommandBody commandBody = new CommandBody();
    	commandBody.setDeviceId("3834R3-3434-3434-3434");
    	commandBody.setCallbackURL("HTTP://DDDFDWERECDFDFWRWERWEDFDFDVBSDFDEERDFDFFGFDF");
    	Command command = new Command();
    	command.setServiceId("SDData");
    	command.setMethod("GET_DEVICE_TIME_INTERVAL");
    	
    	 //{"SN":"SN0000000001","CMD":"CDG","FLOW":"a397a255-53be-41fc-996b-521413e9e232","TIME":"1501145277"},
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("SN", "125465458565345");
    	jsonObject.put("CMD", "CDG");
    	jsonObject.put("FLOW", "a397a255-53be-41fc-996b-521413e9e232");
    	jsonObject.put("TIME", System.currentTimeMillis());
    	command.setParas(jsonObject);
    	commandBody.setCommand(command);
    	
    	try {
			DeviceService.sendCommand("sdfsdfsdfwerwerew", commandBody,id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
    
    
    
/*    public static String sendCommand(String deviceId,CommandBean request)
            throws IOException, Exception
    {
    	String strSendResult = "send failure";
        String url = UrlConfiguration.getDeviceCommandUrl(deviceId);
        url = url+"/commands";

        String jsonRequest = JsonUtil.jsonObj2Sting(JSONObject.fromObject(request));
        //log.info("device.url:{},request:{}.",url,jsonRequest);
        //LogFile.getInstance().WriteFile("sendCommand", "sendCommand_url_jsonRequest","device.url:"+url+" request:"+jsonRequest);
        HttpResponse httpResponse = HttpUtil.doPostJson(url,AuthHeaderMap.getInstance(), jsonRequest);

        if ((responseBody != null) && (responseBody.contains("error_code")))
        {
        	System.out.println(responseBody);
            //log.error("The response is invalid.");
            //throw new HttpResponseException(HttpExceptionEnum.HTTP_RESPONSE_INVALID);
        	LogFile.getInstance().WriteFile("sendCommand", "sendCommand_responseBody_failure","The response is invalid:"+responseBody);
            return "sendCommand information failure.";
        }
        
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        	strSendResult = "send success";
        }
        else {
        	strSendResult = "";
        }
        
        //LogFile.getInstance().WriteFile("sendCommand", "sendCommand_responseBody_success","The response is success:"+strSendResult);
        return strSendResult;
    }*/
    

}

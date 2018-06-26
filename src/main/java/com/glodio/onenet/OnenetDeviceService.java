package com.glodio.onenet;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import httpclient.HttpHandler;
import httpclient.HttpRequestException;
import httpclient.HttpResponseException;
import httpclient.JsonUtil;
import com.glodio.onenet.bean.CommandParams;
import com.glodio.onenet.bean.DeviceAddResponse;
import com.glodio.onenet.bean.DeviceInfoRequest;
import com.glodio.onenet.bean.OnenetCommonResponse;

public class OnenetDeviceService
{
	/**
	 * 日志工具
	 */
	private static final Logger logger = LoggerFactory.getLogger(OnenetDeviceService.class);

	/**
	 * 添加设备
	 * 
	 * @param request
	 * @return
	 * @throws HttpResponseException
	 * @throws org.apache.http.client.HttpResponseException
	 * @throws JsonProcessingException 
	 */
	public static DeviceAddResponse addDevice(DeviceInfoRequest request,String id) throws HttpResponseException, org.apache.http.client.HttpResponseException, JsonProcessingException
	{
		logger.debug("request={} - start", request);

		String url = OneNetUrlConfig.getAddDeviceUrl(id);
		String jsonRequest = JsonUtil.obj2StingNoNull(request);
		
		logger.info("add device.url:{},request:{}.", url, jsonRequest);

		DeviceAddResponse deviceAddResp = null;
		
		String responseBody = new HttpHandler(OneNetHttpClient.getInstance()).doPostJsonForString(url, OneNetHttpClient.getInstance().getAuthHeader(), jsonRequest);
		//device_id":"31140321
		if (StringUtils.isNotEmpty(responseBody))
		{
			deviceAddResp = JsonUtil.string2Obj(responseBody, DeviceAddResponse.class);

			if (!deviceAddResp.getError().equals("succ"))
			{
				logger.error("add device failed.");
				logger.error("the response string is :{}", responseBody);
				
				if(deviceAddResp.getError().indexOf("imei exists") > -1)
				{
					throw new OnenetExistedException();
				}
				else
				{
					throw new OnenetApiException();
				}
			}
		}
		
		logger.debug("DeviceInfoRequest - end");
		return deviceAddResp;
	}

	/**
	 * 删除设备
	 * 
	 * @param deviceId
	 * @return
	 * @throws IOException
	 * @throws HttpRequestException
	 * @throws HttpResponseException
	 * @throws URISyntaxException
	 * @throws ServiceException
	 */
	public static String deleteDevice(String deviceId,String id)
			throws IOException, HttpRequestException, HttpResponseException, URISyntaxException, ServiceException
	{
		logger.debug("deviceId={} - start", deviceId);

		String url = OneNetUrlConfig.getDelDeviceUrl(deviceId,id);
		logger.info("delete device.url:{}.", url);

		String responseBody = new HttpHandler(OneNetHttpClient.getInstance()).doDeleteForString(url, OneNetHttpClient.getInstance().getAuthHeader());
		
		OnenetCommonResponse resultResp = null;
		if (StringUtils.isNotEmpty(responseBody))
		{
			resultResp = JsonUtil.string2Obj(responseBody, OnenetCommonResponse.class);

			if (!resultResp.getError().equals("succ"))
			{
				logger.error("add device failed.");
				logger.error("the response string is :{}", responseBody);
				throw new OnenetApiException();
			}
		}

		logger.debug("String - end");
		return responseBody;
	}

	/**
	 * 修改设备信息
	 * 
	 * @param deviceId
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws HttpRequestException
	 * @throws HttpResponseException
	 * @throws URISyntaxException
	 * @throws ServiceException
	 */
	public static OnenetCommonResponse modifyDevice(String deviceId, DeviceInfoRequest request,String id)
			throws IOException, HttpRequestException, HttpResponseException, URISyntaxException, ServiceException
	{
		logger.debug("deviceId={}, request={} - start", deviceId, request);

		String url = OneNetUrlConfig.getUpdateDeviceUrl(deviceId,id);
		String jsonRequest = JsonUtil.obj2Sting(request);
		
		logger.info("udpate device.url:{},request:{}.", url, jsonRequest);

		OnenetCommonResponse resultResp = null;
		
		String responseBody = new HttpHandler(OneNetHttpClient.getInstance()).doPostJsonForString(url, OneNetHttpClient.getInstance().getAuthHeader(), jsonRequest);

		if (StringUtils.isNotEmpty(responseBody))
		{
			resultResp = JsonUtil.string2Obj(responseBody, OnenetCommonResponse.class);

			if (!resultResp.getError().equals("succ"))
			{
				logger.error("update device failed.");
				logger.error("the response string is :{}", responseBody);
				throw new OnenetApiException();
			}
		}
		
		logger.debug("String, DeviceInfoRequest - end");
		return resultResp;
	}
	
	/**
	 * 下发设备命令
	 * 
	 * @param deviceId
	 * @param content
	 * @return
	 * @throws IOException
	 * @throws HttpRequestException
	 * @throws HttpResponseException
	 * @throws URISyntaxException
	 * @throws ServiceException
	 */
	public static OnenetCommonResponse sendCommand(CommandParams params, Object content,String id)
			throws IOException, HttpRequestException, HttpResponseException, URISyntaxException, ServiceException
	{
		logger.debug("params={}, content={} - start", params, content);

		String url = OneNetUrlConfig.getCommandSendUrl(params,id);
		String jsonRequest = JsonUtil.obj2Sting(content);
		
		logger.info("send command. url:{},request:{}.", url, jsonRequest);

		OnenetCommonResponse resultResp = null;
		
		String responseBody = new HttpHandler(OneNetHttpClient.getInstance()).doPostJsonForString(url, OneNetHttpClient.getInstance().getAuthHeader(), jsonRequest);

		if (StringUtils.isNotEmpty(responseBody))
		{
			resultResp = JsonUtil.string2Obj(responseBody, OnenetCommonResponse.class);

			if (!resultResp.getError().equals("succ"))
			{
				logger.error("send command failed.");
				logger.error("the response string is :{}", responseBody);
				throw new OnenetApiException();
			}
		}
		
		logger.debug("CommandParams, Object - end");
		return resultResp;
	}
	
	
	/*public static void main(String[] args) throws Exception {
		short sValue = 2102;
		byte[] src = new byte[2];
		
		src[0] = (byte)((sValue >> 8) & 0xFF);
		src[1] = (byte)(sValue & 0xFF);
		
		System.out.println("src[0]:"+" "+src[0]+" "+"src[1]:"+" "+src[1]);
		return;
	}*/
}

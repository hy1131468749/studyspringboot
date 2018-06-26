package com.glodio.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.onenet.bean.ReceiverDataUpload;
import com.glodio.service.DataHandleService;

import httpclient.Util;



/**
 * 数据接收接口类： OneNet平台
 *
 * Created by Roy on 2017/5/17.
 */
@Controller
@RequestMapping("/onenet/receiver")
public class DeviceDataForOnenetPostController
{
	private static Logger log = LoggerFactory.getLogger(DeviceDataForOnenetPostController.class);

	// 用户自定义token和OneNet第三方平台配置里的token一致
	private static String token = "ajhgash121saa";
	
	// aeskey和OneNet第三方平台配置里的token一致
	private static String aeskey = "HCQ0t=LXHVCBJVhxbhDzLQnXHbA=";
	
	@Autowired
	private DataHandleService dataHandleService;
	/**
	 * 功能描述：第三方平台数据接收。
	 * <p>
	 * <ul>
	 * 注:
	 * <li>1.OneNet平台为了保证数据不丢失，有重发机制，如果重复数据对业务有影响，数据接收端需要对重复数据进行排除重复处理。</li>
	 * <li>2.OneNet每一次post数据请求后，等待客户端的响应都设有时限，在规定时限内没有收到响应会认为发送失败。
	 * 接收程序接收到数据时，尽量先缓存起来，再做业务逻辑处理。</li>
	 * </ul>
	 * 
	 * @param body
	 *            数据消息
	 * @return 任意字符串。OneNet平台接收到http 200的响应，才会认为数据推送成功，否则会重发。
	 * @throws IOException
	 */
	@RequestMapping(value = "/datas", method = RequestMethod.POST)
	@ResponseBody
	public String receive(@RequestBody ReceiverDataUpload receiverData, HttpServletRequest request)
			throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException, IOException
	{
		log.info("data receive:  {} --- ", receiverData);
		/************************************************
		 * 解析数据推送请求，非加密模式。 如果是明文模式使用以下代码
		 **************************************************/
		/************* 明文模式 start ****************/

		if (receiverData != null)
		{
/*			new DataReceiverService<ReceiverDataUpload>(oneNetDataReceiver).startReceiveThread(receiverData);
			
//			boolean dataRight = Util.checkSignature(receiverData, token);
//			if (dataRight)
//			{
//				log.info("data receive: content" + receiverData.toString());
//				
//				new DataReceiverService<ReceiverDataUpload>(oneNetDataReceiver).startReceiveThread(receiverData);
//			}
//			else
//			{
//				log.info("data receive: signature error");
//			}
*/		}
		else
		{
			log.info("data receive: body empty error");
		}
		/************* 明文模式 end ****************/

		/********************************************************
		 * 解析数据推送请求，加密模式
		 *
		 * 如果是加密模式使用以下代码
		 ********************************************************/
		/************* 加密模式 start ****************/
		/*if (receiverData != null)
		{
			boolean dataRight1 = Util.checkSignature(receiverData, token);
			if (dataRight1)
			{
				String msg = Util.decryptMsg(receiverData, aeskey);
				log.info("data receive: content" + msg);
			} 
			else
			{
				log.info("data receive: signature error ");
			}
		}
		else
		{
			log.info("data receive: body empty error");
		}*/
		/************* 加密模式 end ****************/
		return "ok";
	}

	/**
	 * 功能说明： URL&Token验证接口。如果验证成功返回msg的值，否则返回其他值。
	 * 
	 * @param msg
	 *            验证消息
	 * @param nonce
	 *            随机串
	 * @param signature
	 *            签名
	 * @return msg值
	 */

	@RequestMapping(value = "/datas", method = RequestMethod.GET)
	@ResponseBody
	public String check(@RequestParam(value = "msg") String msg, @RequestParam(value = "nonce") String nonce,
			@RequestParam(value = "signature") String signature) throws UnsupportedEncodingException
	{
		log.info("url&token check: msg:{} nonce{} signature:{}", msg, nonce, signature);
		
		if (Util.checkToken(msg, nonce, signature, token))
		{
		    
			return msg;
		}
		else
		{
			return "error";
		}
		
	}
}

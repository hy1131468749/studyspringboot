package com.glodio.hongguan.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.glodio.util.HexUtilty;

public class Sign {
	public static String signValue(String body,String partnerId,String partnerKey,String timestamp) {
		StringBuilder sb = new StringBuilder();
		sb.append(body).append(partnerId).append(partnerKey).append(timestamp);
		
		MessageDigest md = null;
		try {
			
			md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(sb.toString().getBytes("utf-8"));
			
			// 转16进制字符串
			String md5Str = HexUtilty.BytestoHexString(bytes);
			
			return md5Str;
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "";
		}
	}
	
}

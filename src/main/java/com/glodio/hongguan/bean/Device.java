package com.glodio.hongguan.bean;

import java.util.HashMap;
import java.util.Map;

import com.glodio.util.JsonUtil;

import net.sf.json.JSONArray;

public class Device {
	private String deviceId;
	
	private String SN;

	//鹏讯的厂商key为0005
	private String productKey;
	
	private String imei;
	
	private String deviceMac;
	
	private String deviceModel;
	
	private String cardId;
	
	private int netType;
	
	private String appId;
	
	private Attribute[] attrs;
	
	public Device (String imei) {
		this.deviceId = imei;
		this.SN = imei;
		this.imei = imei;
		this.appId = "00005";
		this.netType = 1;
		this.productKey = "0005";
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}
	

	public String getProductKey() {
		return productKey;
	}
	

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public int getNetType() {
		return netType;
	}

	public void setNetType(int netType) {
		this.netType = netType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public Attribute[] getAttrs() {
		return attrs;
	}

	public void setAttrs(Attribute[] attrs) {
		this.attrs = attrs;
	}
	

	
	
}

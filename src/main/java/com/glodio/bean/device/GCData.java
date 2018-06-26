package com.glodio.bean.device;

import java.util.Date;

import net.sf.json.JSONObject;

public class GCData {
	private String deviceName;
	
	private String SN;
	
	private String fullLeft;
	
	private String fullRight;
	
	private String smokeStatus;
	
	private String obliquityStatus;
	
	private String isOnline;
	
	private Date reportTime;
	
	// 纬度
	private String lat;
	// 经度
	private String lng;
	
	public GCData() {
		
	}
	
	// 离线构造函数
	public GCData(String deviceName,String SN,String lng,String lat) {
		this.deviceName = deviceName;
		this.SN = SN;
		this.fullLeft = "离线";
		this.fullRight = "离线";
		this.smokeStatus = "离线";
		this.obliquityStatus = "离线";
		this.isOnline = "离线";
		this.lng = lng;
		this.lat = lat;
		this.reportTime = new Date(System.currentTimeMillis());
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getFullLeft() {
		return fullLeft;
	}

	public void setFullLeft(String fullLeft) {
		this.fullLeft = fullLeft;
	}

	public String getFullRight() {
		return fullRight;
	}

	public void setFullRight(String fullRight) {
		this.fullRight = fullRight;
	}

	public String getSmokeStatus() {
		return smokeStatus;
	}

	public void setSmokeStatus(String smokeStatus) {
		this.smokeStatus = smokeStatus;
	}

	public String getObliquityStatus() {
		return obliquityStatus;
	}

	public void setObliquityStatus(String obliquityStatus) {
		this.obliquityStatus = obliquityStatus;
	}
	
	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("SN", this.SN);
		jsonObject.put("fullLeft", this.fullLeft);
		jsonObject.put("fullRight", this.fullRight);
		jsonObject.put("smokeStatus", this.smokeStatus);
		jsonObject.put("obliquityStatus", this.obliquityStatus);
		jsonObject.put("isOnline", this.isOnline);
		jsonObject.put("reportTime", this.reportTime);
		
		return jsonObject.toString();
	}
 
}

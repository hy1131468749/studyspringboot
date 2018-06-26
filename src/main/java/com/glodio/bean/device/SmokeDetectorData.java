package com.glodio.bean.device;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.json.JSONObject;

public class SmokeDetectorData {
	@JsonProperty("deviceName")
	private String deviceName;
	@JsonProperty("deviceId")
	private String deviceId;
	@JsonProperty("SN")
	private String SN;
	@JsonProperty("CMD")
	private String CMD;
	@JsonProperty("IF1")
	private String IF1;
	@JsonProperty("IF2")
	private String IF2;
	@JsonProperty("IF1Status")
	private String IF1Status;
	@JsonProperty("IF2Status")
	private String IF2Status;
	@JsonProperty("isOnline")
	private String isOnline;
	@JsonProperty("reportTime")
	private Date reportTime;
	
	// 纬度
	@JsonProperty("lat")
	private String lat;
	// 经度
	@JsonProperty("lng")
	private String lng;
	
	
	
	public String getCMD() {
		return CMD;
	}



	public void setCMD(String cMD) {
		CMD = cMD;
	}



	public SmokeDetectorData() {
		
	}
	
	

	public String getIF1Status() {
		return IF1Status;
	}



	public void setIF1Status(String iF1Status) {
		IF1Status = iF1Status;
	}



	public String getIF2Status() {
		return IF2Status;
	}



	public void setIF2Status(String iF2Status) {
		IF2Status = iF2Status;
	}



	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getIF1() {
		return IF1;
	}

	public void setIF1(String iF1) {
		IF1 = iF1;
	}

	public String getIF2() {
		return IF2;
	}

	public void setIF2(String iF2) {
		IF2 = iF2;
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



	public String getDeviceId() {
		return deviceId;
	}



	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
}

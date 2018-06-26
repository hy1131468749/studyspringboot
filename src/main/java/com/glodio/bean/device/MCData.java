package com.glodio.bean.device;

import java.util.Date;

import net.sf.json.JSONObject;

public class MCData {
	private String deviceName;
	
	private String SN;
	
	private String waterLine;
	
	private String isOpen;

	private String isOnline;
	
	private Date reportTime;
	
	// 纬度
	private String lat;
	// 经度
	private String lng;
	
	public MCData() {
		
	}
	
	public MCData(String deviceName,String deviceId,String lng,String lat) {
		this.deviceName = deviceName;
		this.SN = deviceId;
		this.isOnline = "离线";
		this.waterLine = "离线";
		this.isOpen = "离线";
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

	public String getWaterLine() {
		return waterLine;
	}

	public void setWaterLine(String waterLine) {
		this.waterLine = waterLine;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deviceName", this.deviceName);
		jsonObject.put("isOnline", this.isOnline);
		
		return jsonObject.toString();
	}
}

package com.glodio.hwnbiot.beans;

public class DeviceDataDATA {
	private String notifyType;
	private String deviceId;
	private String gatewayId;
	private DataServiceDATA service;
	public String getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getGatewayId() {
		return gatewayId;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	public DataServiceDATA getService() {
		return service;
	}
	public void setService(DataServiceDATA service) {
		this.service = service;
	}

}

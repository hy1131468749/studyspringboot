package com.glodio.hwnbiot.beans;

public class DeviceData {
	private String notifyType;
	private String deviceId;
	private String gatewayId;
	private DataService service;
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
	public DataService getService() {
		return service;
	}
	public void setService(DataService service) {
		this.service = service;
	}

}

package com.glodio.hwnbiot.beans;

public class DeviceDataDATAS {
	private String notifyType;
	private String deviceId;
	private String gatewayId;
	private DataServiceDATAS service;
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
	public DataServiceDATAS getService() {
		return service;
	}
	public void setService(DataServiceDATAS service) {
		this.service = service;
	}

}

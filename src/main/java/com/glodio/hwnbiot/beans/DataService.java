package com.glodio.hwnbiot.beans;

public class DataService {
	private String serviceType;
	private String eventTime;
	private String serviceId;
	private ServiceDataRawData data;
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public ServiceDataRawData getData() {
		return data;
	}
	public void setData(ServiceDataRawData data) {
		this.data = data;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}

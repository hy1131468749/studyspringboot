package com.glodio.hwnbiot.beans;

public class BatchCommandBody {
	private String appId;
	
	private Integer timeout;
	
	private String taskName;
	
	private String taskType;
	
	private BatchParam param;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public BatchParam getParam() {
		return param;
	}
	public void setParam(BatchParam param) {
		this.param = param;
	}
}

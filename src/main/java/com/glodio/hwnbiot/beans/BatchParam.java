package com.glodio.hwnbiot.beans;

import java.util.List;

public class BatchParam {
	
	private String type;
	
	private List<String> deviceList;
	
	private Command command;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<String> deviceList) {
		this.deviceList = deviceList;
	}
	public Command getCommand() {
		return command;
	}
	public void setCommand(Command command) {
		this.command = command;
	}
}

package com.glodio.hongguan.bean;

import java.util.ArrayList;

public class CommandsInfo {
	private String deviceId;
	
	private long commandTime;
	
	private ArrayList<CommandDetail> commandDetailList;

	private String imei;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public long getCommandTime() {
		return commandTime;
	}

	public void setCommandTime(long commandTime) {
		this.commandTime = commandTime;
	}

	public ArrayList<CommandDetail> getCommandDetailList() {
		return commandDetailList;
	}

	public void setCommandDetailList(ArrayList<CommandDetail> commandDetailList) {
		this.commandDetailList = commandDetailList;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	

}

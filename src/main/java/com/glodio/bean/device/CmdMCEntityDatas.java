package com.glodio.bean.device;

public class CmdMCEntityDatas {
	private String status = "0";
	
	private String travel_switch = "0";
	
	private String hinge = "0";
	
	private String beacon_interval = "12";
	
	private String level_interval = "20";
	
	private String travel_interval = "20";
	
	public CmdMCEntityDatas() {
	}
	
	public CmdMCEntityDatas(String status,String travel_switch,
							String hinge,String beacon_interval,
							String level_interval,String travel_interval) {
		this.status = status;
		this.travel_switch = travel_switch;
		this.hinge = hinge;
		this.beacon_interval = beacon_interval;
		this.level_interval = level_interval;
		this.travel_interval = travel_interval;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTravel_switch() {
		return travel_switch;
	}

	public void setTravel_switch(String travel_switch) {
		this.travel_switch = travel_switch;
	}

	public String getHinge() {
		return hinge;
	}

	public void setHinge(String hinge) {
		this.hinge = hinge;
	}

	public String getBeacon_interval() {
		return beacon_interval;
	}

	public void setBeacon_interval(String beacon_interval) {
		this.beacon_interval = beacon_interval;
	}

	public String getLevel_interval() {
		return level_interval;
	}

	public void setLevel_interval(String level_interval) {
		this.level_interval = level_interval;
	}

	public String getTravel_interval() {
		return travel_interval;
	}

	public void setTravel_interval(String travel_interval) {
		this.travel_interval = travel_interval;
	}
	
}

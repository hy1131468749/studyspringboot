package com.glodio.bean.device;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.New;

import net.sf.json.JSONObject;

public class CmdMCEntity {
/*	map.put("CMD","ACT");
	map.put("SN", imei);
	
	jsonObject = new JSONObject(); 
	jsonObject.put("status", "1");
	jsonObject.put("travel_switch", "1");
	jsonObject.put("hinge", "0");
	jsonObject.put("beacon_interval", "12");
	jsonObject.put("level_interval", "20");
	jsonObject.put("travel_interval", "20");
	map.put("DATAS", jsonObject);*/
	
	private String CMD = "ACT";
	
	private String SN = "8888";
	
	private CmdMCEntityDatas DATAS;
	
	public CmdMCEntity(String deviceId) {
		this.CMD = "ACT";
		this.SN = deviceId;
		this.DATAS = new CmdMCEntityDatas("0", "0", "0", "12", "20", "20");
	}
	
	public String getCMD() {
		return CMD;
	}

	public void setCMD(String cMD) {
		CMD = cMD;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public CmdMCEntityDatas getDATAS() {
		return DATAS;
	}

	public void setDATAS(CmdMCEntityDatas dATAS) {
		DATAS = dATAS;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("CMD", this.CMD);
		jsonObject.put("SN", this.SN);
		jsonObject.put("DATAS", this.DATAS);
		
		return jsonObject.toString();
	}
}

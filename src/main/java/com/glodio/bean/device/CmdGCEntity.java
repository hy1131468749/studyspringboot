package com.glodio.bean.device;

import net.sf.json.JSONObject;

public class CmdGCEntity {
	private String ACT;
	
	private String SN;
	
	private String TS;
	
/*	public CmdGCEntity(String act,String sn,String ts) {
		this.ACT = "7";
		this.SN = "8888";
		this.TS = "14400000";
	}*/
	
	public CmdGCEntity(String sn) {
		this.ACT = "7";
		this.SN = sn;
		this.TS = String.valueOf((long)(Math.ceil((System.currentTimeMillis()/1000))));
	}
	
	public CmdGCEntity() {
	}

	public String getACT() {
		return ACT;
	}

	public void setACT(String aCT) {
		ACT = aCT;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getTS() {
		return TS;
	}

	public void setTS(String tS) {
		TS = tS;
	}
	
	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ACT", this.ACT);
		jsonObject.put("SN", this.SN);
		jsonObject.put("TS", this.TS);
		
		return jsonObject.toString();
	}
	
	
}

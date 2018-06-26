package com.glodio.bean.device;

import net.sf.json.JSONObject;

public class MCDataBean {

	private String SN;

	private String CMD;

	private String FLOW;

	private String TIME;

	private String STATUS;

	private DATAS DATAS;

	public String getSN() {
		return SN;
	}

	public void setSN(String SN) {
		this.SN = SN;
	}

	public String getCMD() {
		return CMD;
	}

	public void setCMD(String CMD) {
		this.CMD = CMD;
	}

	public String getFLOW() {
		return FLOW;
	}

	public void setFLOW(String FLOW) {
		this.FLOW = FLOW;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String TIME) {
		this.TIME = TIME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String STATUS) {
		this.STATUS = STATUS;
	}

	public DATAS getDATAS() {
		return DATAS;
	}

	public void setDATAS(DATAS DATAS) {
		this.DATAS = DATAS;
	}
	
	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("SN", this.SN);
		jsonObject.put("CMD", this.CMD);
		jsonObject.put("FLOW", this.FLOW);
		jsonObject.put("TIME", this.TIME);
		jsonObject.put("STATUS", this.STATUS);
		jsonObject.put("DATAS", this.DATAS);
		
		return jsonObject.toString();
	}

}

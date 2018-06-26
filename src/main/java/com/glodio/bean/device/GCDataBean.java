package com.glodio.bean.device;

import net.sf.json.JSONObject;

public class GCDataBean {
	private String TP;
	private String MN;
	private String DTP;
	private String SN;
	private String ACT;
	private String TS;
	private GCDATAS DT;
	
	public String getTP() {
		return TP;
	}
	public void setTP(String tP) {
		TP = tP;
	}
	public String getMN() {
		return MN;
	}
	public void setMN(String mN) {
		MN = mN;
	}
	public String getDTP() {
		return DTP;
	}
	public void setDTP(String dTP) {
		DTP = dTP;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getACT() {
		return ACT;
	}
	public void setACT(String aCT) {
		ACT = aCT;
	}
	public String getTS() {
		return TS;
	}
	public void setTS(String tS) {
		TS = tS;
	}
	public GCDATAS getDT() {
		return DT;
	}
	public void setDT(GCDATAS dT) {
		DT = dT;
	}
	
	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("TP", this.TP);
		jsonObject.put("MN", this.MN);
		jsonObject.put("DTP", this.DTP);
		jsonObject.put("SN", this.SN);
		jsonObject.put("ACT", this.ACT);
		jsonObject.put("TS", this.TS);
		jsonObject.put("DT", this.DT);
		
		return jsonObject.toString();
	}
}

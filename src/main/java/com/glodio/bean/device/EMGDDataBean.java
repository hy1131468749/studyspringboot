package com.glodio.bean.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EMGDDataBean {
	@JsonProperty("SN")
	private String SN;
	@JsonProperty("ACT")
	private String ACT;
	@JsonProperty("TS")
	private String TS;
	@JsonProperty("DT")
	private EMGDData DT;
	
	public EMGDDataBean() {
		
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
	public EMGDData getDT() {
		return DT;
	}
	public void setDT(EMGDData dT) {
		DT = dT;
	}

     
}

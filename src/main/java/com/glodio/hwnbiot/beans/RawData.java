package com.glodio.hwnbiot.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class RawData  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String CMD;
	private String SN;
	private String FLOW;
	private String TIME;

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

	public String getFLOW() {
		return FLOW;
	}

	public void setFLOW(String fLOW) {
		FLOW = fLOW;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}

}

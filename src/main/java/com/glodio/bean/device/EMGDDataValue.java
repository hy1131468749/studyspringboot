package com.glodio.bean.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EMGDDataValue {

	@JsonProperty("C")
	private String C;
	@JsonProperty("V")
	private String V;
	
	public EMGDDataValue() {
		
	}
    
	public EMGDDataValue(String c, String v) {
		super();
		C = c;
		V = v;
	}

	
	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getV() {
		return V;
	}

	public void setV(String v) {
		V = v;
	}

	
	

}

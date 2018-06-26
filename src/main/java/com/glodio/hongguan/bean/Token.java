package com.glodio.hongguan.bean;

public class Token {
	private AccessToken data;
	
	private String resultCode;
	
	private String resultInfo;

	public AccessToken getData() {
		return data;
	}

	public void setData(AccessToken data) {
		this.data = data;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	
	
}

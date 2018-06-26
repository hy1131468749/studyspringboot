package com.glodio.hwnbiot.beans;

public class ExceptionMsg {
    
    private String error_code;
    private String error_desc;

    public ExceptionMsg(){}

    public ExceptionMsg(String errorCode, String errorDesc)
    {
        this.setError_code(errorCode);
        this.setError_desc(errorDesc);
    }

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_desc() {
		return error_desc;
	}

	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}
}

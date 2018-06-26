package com.glodio.hwnbiot.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.sf.json.JSONObject;

public class Command {

	@NotNull
    @Size(max = 128)
    private String serviceId;
	
    @NotNull
    @Size(max = 64)
    private String method;
    
    private JSONObject paras;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    
	public JSONObject getParas() {
		return paras;
	}

	public void setParas(JSONObject paras) {
		this.paras = paras;
	}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CommandNA2CloudHeader [mode=");
        builder.append(", method=");
        builder.append(method);
        builder.append(", seviceId=");
        builder.append(serviceId);
        return builder.toString();
    }
}

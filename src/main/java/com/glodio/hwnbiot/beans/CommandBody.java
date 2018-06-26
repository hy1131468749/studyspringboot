package com.glodio.hwnbiot.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommandBody {

	@NotNull
    private String deviceId;
	
	@NotNull
    Command command;
    
	@Size(max = 1024)
	private String callbackURL;
    
    public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}



	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCallbackURL() {
		return callbackURL;
	}

	public void setCallbackURL(String callbackURL) {
		this.callbackURL = callbackURL;
	}
    
    
}

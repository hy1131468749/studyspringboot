package com.glodio.nokianbiot.beans;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * nokia report device data object
 * 
 * @author qizhong.jiang
 *
 */
public class NokiaData {
	//reports  "registrations":[],"deregistrations":[],"updates":[],"expirations":[],"responses":[]}
	
	private List<JSONObject> reports;
	
	private List<JSONObject> registrations;
	
	private List<JSONObject> deregistrations;
	
	private List<JSONObject> updates;
	
	private List<JSONObject> expirations;
	
	private List<JSONObject> responses;

	public List<JSONObject> getReports() {
		return reports;
	}

	public void setReports(List<JSONObject> reports) {
		this.reports = reports;
	}

	public List<JSONObject> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<JSONObject> registrations) {
		this.registrations = registrations;
	}

	public List<JSONObject> getDeregistrations() {
		return deregistrations;
	}

	public void setDeregistrations(List<JSONObject> deregistrations) {
		this.deregistrations = deregistrations;
	}

	public List<JSONObject> getUpdates() {
		return updates;
	}

	public void setUpdates(List<JSONObject> updates) {
		this.updates = updates;
	}

	public List<JSONObject> getExpirations() {
		return expirations;
	}

	public void setExpirations(List<JSONObject> expirations) {
		this.expirations = expirations;
	}

	public List<JSONObject> getResponses() {
		return responses;
	}

	public void setResponses(List<JSONObject> responses) {
		this.responses = responses;
	}
	
}

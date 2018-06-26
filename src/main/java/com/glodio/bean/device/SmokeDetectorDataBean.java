package com.glodio.bean.device;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.glodio.util.SmokeDetectorDataType;

import net.sf.json.JSONObject;

public class SmokeDetectorDataBean {
    @JsonProperty("SN")
	private String SN;
    //类型 :UBD 传值,AUBD 报警 
    @JsonProperty("CMD")
	private String CMD;
    @JsonProperty("FLOW")
	private String FLOW;
    @JsonProperty("TIME")
	private String TIME;
    @JsonProperty("DATAS")
	private Object DATAS;
	
	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getCMD() {
		return CMD;
	}

	public void setCMD(String cMD) {
		CMD = cMD;
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

	
	
	public Object getDATAS() {
		return DATAS;
	}

	public void setDATAS(Object dATAS) {
		DATAS = dATAS;
	}

	@SuppressWarnings("unchecked")
	public String getIF1(){
		String IF1 = "";
		if(SmokeDetectorDataType.AUBD.equals(CMD)){
			Map<String,String> map = (Map<String,String>)DATAS;
			if(map != null){
				IF1 = map.get("IF1");
			}
		}else if(SmokeDetectorDataType.UBD.equals(CMD)){
			List<Map<String,String>> mapList = (List<Map<String, String>>) DATAS;
	        if(mapList == null){
	        	return IF1;
	        }
	        for(Map<String,String> map : mapList){
	        	if(map.containsKey("IF1")){
	        		IF1 = map.get("IF1");
	        		break;
	        	}
	        }
		}
		return IF1;
	}
	
	@SuppressWarnings("unchecked")
	public String getIF2(){
		String IF2 = "";
		if(SmokeDetectorDataType.AUBD.equals(CMD)){
			Map<String,String> map = (Map<String,String>)DATAS;
			if(map != null){
				IF2 = map.get("IF2");
			}
		}else if(SmokeDetectorDataType.UBD.equals(CMD)){
			List<Map<String,String>> mapList = (List<Map<String, String>>) DATAS;
	        if(mapList == null){
	        	return IF2;
	        }
	        for(Map<String,String> map : mapList){
	        	if(map.containsKey("IF2")){
	        		IF2 = map.get("IF2");
	        		break;
	        	}
	        }
		}
		return IF2;
	}
	
	
	
}

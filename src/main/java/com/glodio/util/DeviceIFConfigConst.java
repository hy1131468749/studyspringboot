package com.glodio.util;

import java.util.HashMap;
import java.util.Map;

/*
 *@function 保存主机接口配置参数已下发，未下发则收到数据下发，如已下发，则保存key-value (SN-"ID0000001")  
 *
 */
public class DeviceIFConfigConst {
	private final static Map<String,Object> ifConfigMap = new HashMap<>();
	
	public static Map<String, Object> getIFConfigMap() {
		if(ifConfigMap == null) {
			return new HashMap<>();
		}
		else {
			return ifConfigMap;
		}
	}
}

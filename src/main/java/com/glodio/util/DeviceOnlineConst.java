package com.glodio.util;

import java.util.HashMap;
import java.util.Map;

public class DeviceOnlineConst {
	private final static Map<String,Object> onlineMap = new HashMap<>();
	
	public static Map<String, Object> getOnlineMap() {
		return onlineMap;
	}
}

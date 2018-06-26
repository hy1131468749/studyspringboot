package com.glodio.util;

import java.util.HashMap;
import java.util.Map;

import com.glodio.bean.ConnectPlatform;

public class SystemKeyValueMap {
	private static Map<String, String> map = new HashMap<>();
	
	public synchronized static Map<String, String> getInstance() {
		if(map == null) {
			map = new HashMap<>();
		}
		
		return map;
	}
}

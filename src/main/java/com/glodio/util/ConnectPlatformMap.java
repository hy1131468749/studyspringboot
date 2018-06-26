package com.glodio.util;

import java.util.HashMap;
import java.util.Map;

import com.glodio.bean.ConnectPlatform;

public class ConnectPlatformMap {
	private static Map<String, ConnectPlatform> map = new HashMap<>();
	
	public synchronized static Map<String, ConnectPlatform> getInstance() {
		if(map == null) {
			map = new HashMap<>();
		}
		
		return map;
	}
}

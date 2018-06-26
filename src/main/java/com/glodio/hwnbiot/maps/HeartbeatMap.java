package com.glodio.hwnbiot.maps;

import java.util.HashMap;
import java.util.Map;

public class HeartbeatMap {
	private final static Map<String, Object> map = new HashMap<>();
	
	public synchronized static Map<String, Object> getMap() {
		return map;
	}
	
	public synchronized static void addMap(String key,Object value) {
		map.put(key, value);
	}
	
	public synchronized static void removeMap(String key) {
		map.remove(key);
	}
	
	public synchronized static void clearMap() {
		map.clear();
	}
}

package com.glodio.bean.device;

import java.util.HashMap;
import java.util.Map;

public class CmdGCMap {

	private  static final Map<String, Object> map = new HashMap<>();
	
	public synchronized static Object getObject(String key) {
		
		Object object = map.containsKey(key) ? map.get(key) : null;
		
		return object;
	}
	
	public synchronized static void add(String key,Object object) {
		map.put(key, object);
	}
	
	public synchronized static void remove(String key) {
		map.remove(key);
	}
	
	public synchronized static void clearMap() {
		map.clear();
	}
	
	

}

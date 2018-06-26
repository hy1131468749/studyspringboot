package com.glodio.hongguan;

import java.util.HashMap;
import java.util.Map;

public class OnenetHGHeaderMap {
	private volatile static Map<String, Map<String,String>> instance = null;
	
	private OnenetHGHeaderMap() {}
	
	public static Map<String, Map<String,String>> getInstance() {
		if(instance == null) {
			synchronized (OnenetHGHeaderMap.class) {
				instance = new HashMap<>();
			}
			
		}
		
		return instance;
	}
}

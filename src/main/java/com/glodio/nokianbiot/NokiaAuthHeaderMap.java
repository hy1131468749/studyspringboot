package com.glodio.nokianbiot;

import java.util.HashMap;
import java.util.Map;

public class NokiaAuthHeaderMap {
	private static Map<String, Map<String,String>> map = null;
	
	public static Map<String, Map<String,String>> getInstance() {
		if(map == null) {
			map = new HashMap<>();
		}
		
		return map;
	}
}

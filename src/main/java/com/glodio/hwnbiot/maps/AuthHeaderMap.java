package com.glodio.hwnbiot.maps;

import java.util.HashMap;
import java.util.Map;

public class AuthHeaderMap {
	private static Map<String, Map<String,String>> map = null;
	
	public static Map<String,  Map<String,String>> getInstance() {
		if(map == null) {
			map = new HashMap<>();
		}
		
		return map;
	}
}

package com.glodio.hwnbiot.maps;

import java.util.HashMap;
import java.util.Map;

public class TokenMap {
	private static Map<String, String> map = null;
	
	public static Map<String, String> getInstance() {
		if(map == null) {
			map = new HashMap<>();
		}
		
		return map;
	}
}

package com.glodio.util;

import java.util.HashMap;
import java.util.Map;

public class DeviceAlarmThresholdConst {
	private final static Map<String,Object> alarmThresholdMap = new HashMap<>();
	
	public static Map<String, Object> getAlarmThresholdMap() {
		if(alarmThresholdMap == null) {
			return new HashMap<>();
		}
		else {
			return alarmThresholdMap;
		}
	}
}

package com.glodio.bean.device;

import java.util.HashMap;
import java.util.Map;

public class MCMap {
	private static Map<String,Object> mcMap = new HashMap<>();
	
	public synchronized static Map<String,Object> getMap() {
		return mcMap;
	}
	
	// 增加数据:先删除旧数据
	public synchronized static void addData(String SN,MCData data) {
		mcMap.put(SN, data);
	}
	
	public synchronized static void removeData(String SN) {
		mcMap.remove(SN);
	}
	
	public synchronized static void clearMap() {
		mcMap.clear();
	}
}

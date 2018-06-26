package com.glodio.bean.device;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author admin
 *
 * 垃圾箱设备数据保存，每个设备保存1条最新数据
 */
public class GCMap {
	private static Map<String,Object> gcMap = new HashMap<>();
	
	public synchronized static Map<String,Object> getMap() {
		return gcMap;
	}
	
	// 增加数据:先删除旧数据
	public synchronized static void addData(String SN,GCData data) {
		gcMap.put(SN, data);
	}
	
	public synchronized static void removeData(String SN) {
		gcMap.remove(SN);
	}
	
	public synchronized static void clearMap() {
		gcMap.clear();
	}
}

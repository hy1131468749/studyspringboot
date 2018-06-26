package com.glodio.util;

import java.util.HashMap;
import java.util.Map;

public class CacheContext<T> {
	private Map<String, T> cache = new HashMap<>();
	
	/*
	 *
	 * @ 通过key，返回泛型对象 
	 * @param key-对象名称
	 * 
	 */
	public T get(String key) {
		if(cache.containsKey(key)) {
			return cache.get(key);
		}
		else {
			return null;
		}
		
	}
	
	// 增加或更新缓存对象
	public void addOrUpdateCache(String key,T value) {
		cache.put(key, value);
	}
	
	// 清空缓存
	public void evictCache(String key) {
		if(cache.containsKey(key)) {
			cache.remove(key);
		}
	}
	
	// 清空所有缓存
	public void evictCache() {
		cache.clear();
	}
}

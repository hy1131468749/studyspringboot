package com.glodio.util;

import java.util.HashMap;
import java.util.Map;

public class FileUploadProcess {
    //为了防止多用户并发，使用线程安全的Hashtable
    private static Map<Object, Object> map = new HashMap<>();
    
    public static void put(Object key, Object value){
    	map.put(key, value);
    }
    
    public static Object get(Object key){
        return map.get(key);
    }
    
    public static Object remove(Object key){
        return map.remove(key);
    }

}

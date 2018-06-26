package com.glodio.util;

public class StringUtil {

    public static boolean strIsNullOrEmpty(String s) {
        return (null == s || s.trim().length() < 1);
    }
    
    public static int convertInt(String s){
    	int i =0;
    	try{
    		if(!strIsNullOrEmpty(s)){
    			i = Integer.parseInt(s);
    		}
    		
    	}catch(Exception e){
    		i =0;
    		System.out.println("转换出现问题");
    	}
    	return i;
    }
}

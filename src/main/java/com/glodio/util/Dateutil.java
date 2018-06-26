package com.glodio.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dateutil {
	private String lastTime;
	private Date date;
	
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public static int[] getTimeArr(Date time){
		int[] arr = new int[4];
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd-HH");
		String t = sdt.format(time);
		String[] strArr= t.split("-");
		for (int i = 0; i < strArr.length; i++) {
			arr[i] = Integer.parseInt(strArr[i]);
		}
		
		return arr;
	}
	
	public static String getTimestampForStr() {
		String format = "yyyyMMddHHmmss";
  
	    SimpleDateFormat sdf = new SimpleDateFormat(format);  
	    return sdf.format(new Date(Long.valueOf(System.currentTimeMillis()))); 
	}
	
}

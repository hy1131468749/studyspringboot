package com.glodio.bean.device;

import java.util.LinkedList;

public class TestList {
	private static LinkedList<TestData> ttList = new LinkedList<>();
	
	public synchronized static LinkedList<TestData> getInstance() {
		if(ttList == null) {
			ttList = new LinkedList<>();
		}
		
		return ttList;
	}
}

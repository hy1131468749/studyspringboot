package com.glodio.serviceimpl;

import java.util.Queue;

import com.glodio.service.IDeviceCoverService;

public class DeviceDataProcessThread implements Runnable {
	
	private Queue<String> queueData;
	
	private IDeviceCoverService iDeviceCoverService;
	
	public DeviceDataProcessThread(Queue<String> queue,IDeviceCoverService iDeviceCoverService) {
		this.queueData = queue;
		this.iDeviceCoverService = iDeviceCoverService;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			for(String data:queueData) {
				
			}
		}
	}

}

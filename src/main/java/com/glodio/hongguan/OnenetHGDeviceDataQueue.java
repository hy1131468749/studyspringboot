package com.glodio.hongguan;

import java.util.concurrent.LinkedBlockingQueue;

/*
 * 
 *Onenet平台-鸿官设备上报数据阻塞队列类 
 * 
 *
 */
public class OnenetHGDeviceDataQueue {
	private static LinkedBlockingQueue<String> queue = null;
	
	public static LinkedBlockingQueue<String> getInstance() {
		if(queue == null) {
			synchronized (OnenetHGDeviceDataQueue.class) {
				queue = new LinkedBlockingQueue<>();
			}
		}
		
		return queue;
	}
}

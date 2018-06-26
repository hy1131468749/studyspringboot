package com.glodio.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.glodio.hongguan.OnenetHGMessageProcessService;
import com.glodio.hongguan.OnenetHGTokenTimerTask;


@WebListener
public class InitOneneHGListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		

		// token定时器
		//OnenetHGTokenTimerTask.OnStartAccess(1600);
		
		// header定时器
		//OnenetHGHttpHeaderTimerTask.OnStartAccess(200);
		
		// 设备数据处理线程
	//	OnenetHGMessageProcessService processService = new OnenetHGMessageProcessService();
	//	Thread msgThread = new Thread(processService);
	//	msgThread.start();

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}

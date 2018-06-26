package com.glodio.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class InitNokiaNBIoTListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 初始化开始
		//NokiaAuthHeaderTimerTask.OnStartAccess(10);
		//NokiaApplicationRegisterTimerTask.OnStartAccess(20);
		//NokiaCreateResourceSubscribeTimerTask.OnStartAccess(40);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}

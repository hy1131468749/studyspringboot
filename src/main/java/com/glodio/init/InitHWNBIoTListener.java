package com.glodio.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.glodio.hwnbiot.tasks.SubscribeTimerTask;
import com.glodio.hwnbiot.tasks.TokenAndAuthHeaderTimerTask;

import com.glodio.util.UrlConfiguration;
@WebListener
public class InitHWNBIoTListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		// 初始化Token+AuthHeader map 参数
		//System.out.println("test");
	//	TokenAndAuthHeaderTimerTask.OnStartAccess(60*60);
	//	SubscribeTimerTask.OnStartAccess(60);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}

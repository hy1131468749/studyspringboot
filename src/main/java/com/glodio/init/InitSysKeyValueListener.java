package com.glodio.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.glodio.service.ISystemResourceService;
import com.glodio.tasks.SystemKeyValueTimerTask;
@WebListener
public class InitSysKeyValueListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		final ISystemResourceService iSystemResourceService = wac.getBean(ISystemResourceService.class);
		System.out.println("开始执行初始化---");
		// 系统参数初始化任务
		SystemKeyValueTimerTask.OnStartAccess(10, iSystemResourceService);
		System.out.println("开始执行初始化-----");
	
		
		
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}

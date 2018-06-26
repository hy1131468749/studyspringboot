package com.glodio.init;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.glodio.bean.device.GCData;
import com.glodio.bean.device.GCMap;
import com.glodio.bean.device.MCData;
import com.glodio.bean.device.MCMap;
@WebListener
public class InitDeviceOfflineListener implements ServletContextListener {


	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		//System.out.println("web startp,init start...");
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());

        TimerTask task = new TimerTask() {  
            @Override  
            public void run() {
        		Calendar calendar = Calendar.getInstance();
        		long difference1=calendar.getTimeInMillis();//当前时间总秒数
        		
/*        		for(MCData mcData:MCMap.getList()) {
            		calendar.setTime(mcData.getReportTime());
    				long difference2 = calendar.getTimeInMillis();//设备最后上线秒数
            		if(Math.abs((difference1-difference2)/(60*1000)) > 2) {//如果现在大于设备最后上线时间超过2.2分钟，则设置设备离线
                		MCMap.getList().get(MCMap.getList().indexOf(mcData)).setIsOnline("离线");
            		}
        		}
        		
        		for(GCData mcData:GCList.getList()) {
            		calendar.setTime(mcData.getReportTime());
    				long difference2 = calendar.getTimeInMillis();//设备最后上线秒数
            		if(Math.abs((difference1-difference2)/(60*1000)) > 2) {//如果现在大于设备最后上线时间超过2.2分钟，则设置设备离线
            			GCList.getList().get(GCList.getList().indexOf(mcData)).setIsOnline("离线");
            		}
        		}	*/
            }  
        };
        
        Timer timer = new Timer();  
        long delay = 60*1000;  
        long intevalPeriod =60 * 1000;  
        // schedules the task to be run in an interval  
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);  


	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}

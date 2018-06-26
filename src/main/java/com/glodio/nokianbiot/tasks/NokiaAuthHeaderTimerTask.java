package com.glodio.nokianbiot.tasks;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.glodio.bean.ConnectPlatform;
import com.glodio.nokianbiot.NokiaAuthHeaderMap;
import com.glodio.util.ConncetTypeNumber;
import com.glodio.util.ConnectPlatformMap;
import com.glodio.util.LogFile;
import com.glodio.util.SystemKeyValueMap;


public class NokiaAuthHeaderTimerTask {
    private static int interval = 60;
    private static Timer timer = null;
    private static TimerTask timerTask = null;

    /**
     * 初始nokia-http请求head
     */
    @SuppressWarnings("restriction")
	public static void initHeaderMap() {
    	Map<String, ConnectPlatform> map = ConnectPlatformMap.getInstance();
		Collection<ConnectPlatform> valuse = map.values();
		Iterator<ConnectPlatform> iterators = valuse.iterator();
		while(iterators.hasNext()){
			ConnectPlatform connectPlatform  = iterators.next();
			Map<String,String> data = null;
			if(connectPlatform.getTypeId() == ConncetTypeNumber.CONNECTTYPE_NOKIA){//诺基亚的平台才执行这个操作
				String strNokiaUserName = SystemKeyValueMap.getInstance().get("nokia_user_name").toString();
		    	String strNokiaPassword = SystemKeyValueMap.getInstance().get("nokia_password").toString();
				String strAuthorization = new sun.misc.BASE64Encoder().encode((strNokiaUserName+":"+strNokiaPassword).getBytes());
				data = new HashMap<>();
				data.put("Authorization", "Basic"+" "+strAuthorization);
				data.put("Content-Type", "application/json");
				data.put("Accept", "application/json");
				NokiaAuthHeaderMap.getInstance().put(connectPlatform.getId()+"", data);
				LogFile.devLogger.info("[Nokia Auth header] Authorization:[Basic {}],Content-Type:[{}],Accept:[{}]", 
		    			strAuthorization, data.get("Content-Type").toString(),data.get("Accept").toString());
			}	
		}
    	
    	
    
    }
    
    /**
     * 定时更新AccessToken
     * @param autoScends 任务执行间隔秒数
     * @param loginUrl 请求token地址
     */
    public static void OnStartAccess(int autoScends) 
    {
        if (autoScends == 0) 
        {
            autoScends = interval;
        }
        
        //Date autoTime = new Date();
        timerTask = new TimerTask() 
        {
            @Override
            public void run() {
            	if(!SystemKeyValueMap.getInstance().isEmpty()) 
            	{
                	if(NokiaAuthHeaderMap.getInstance().isEmpty()) 
                	{
                		initHeaderMap();
                	}
                	else 
                	{
                		OnStopAccess();
                	}
            	}
            }
        };
        timer = new Timer();
        long delay = 10*1000;
        timer.scheduleAtFixedRate(timerTask, delay, autoScends * 1000);
    }
    
    /**
     * 关闭定时更新AccessToken
     */
    public static void OnStopAccess() {
        if (timerTask != null) {
            timerTask.cancel();
        }

        if (timer != null) {
            timer.cancel();
        }
    }
    
}

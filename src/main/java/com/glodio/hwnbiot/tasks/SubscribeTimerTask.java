package com.glodio.hwnbiot.tasks;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.glodio.bean.ConnectPlatform;
import com.glodio.hwnbiot.SubscribeInit;
import com.glodio.hwnbiot.maps.AuthHeaderMap;
import com.glodio.util.ConncetTypeNumber;
import com.glodio.util.ConnectPlatformMap;

public class SubscribeTimerTask {
    private static int interval = 60;
    private static Timer timer = null;
    private static TimerTask timerTask = null;

    /**
     * 定时更新AccessToken
     * @param autoScends 任务执行间隔秒数
     * @param loginUrl 请求token地址
     */
    public static void OnStartAccess(int autoScends) {
        if (autoScends == 0) {
            autoScends = interval;
        }
        //Date autoTime = new Date();
        timerTask = new TimerTask() {
            @Override
            public void run() {
            	if(!AuthHeaderMap.getInstance().isEmpty()) {
            		Map<String, ConnectPlatform> map = ConnectPlatformMap.getInstance();
            		Collection<ConnectPlatform> valuse = map.values();
            		Iterator<ConnectPlatform> iterators = valuse.iterator();
            		while(iterators.hasNext()){
            			ConnectPlatform connectPlatform  = iterators.next();            		
            			if(connectPlatform.getTypeId() == ConncetTypeNumber.CONNECTTYPE_HUAIWEI){//只有华为的平台才执行这个操作
            				SubscribeInit  subscribeInit = new SubscribeInit();
            		        subscribeInit.init(connectPlatform.getId()+"");
            		    }
            		}
            		OnStopAccess();
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

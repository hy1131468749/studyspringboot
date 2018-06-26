package com.glodio.tasks;

import java.util.Timer;
import java.util.TimerTask;

import com.glodio.service.ISystemResourceService;
import com.glodio.util.SystemKeyValueMap;


public class SystemKeyValueTimerTask {
    private static int interval = 60;
    private static Timer timer = null;
    private static TimerTask timerTask = null;

    /**
     * 定时更新AccessToken
     * @param autoScends 任务执行间隔秒数
     * @param loginUrl 请求token地址
     */
    public static void OnStartAccess(int autoScends,ISystemResourceService iSystemResourceService) {
        if (autoScends == 0) {
            autoScends = interval;
        }
        //Date autoTime = new Date();
        timerTask = new TimerTask() {
            @Override
            public void run() {
            	if(SystemKeyValueMap.getInstance().isEmpty()) {
            		iSystemResourceService.GetKeyValueMap();
            		//获取所有平台信息
            		iSystemResourceService.getConnectPlatformMap();
            		
            	}
            	else {
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

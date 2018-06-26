package com.glodio.hongguan;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.glodio.bean.ConnectPlatform;
import com.glodio.hongguan.bean.OnenetHGConst;
import com.glodio.nokianbiot.HttpUtilForNokia;

import com.glodio.util.AESUtil;
import com.glodio.util.ConncetTypeNumber;
import com.glodio.util.ConnectPlatformMap;
import com.glodio.util.JsonUtil;
import com.glodio.util.UrlConfiguration;
import com.glodio.util.Uuid;

import net.sf.json.JSONObject;

public class OnenetHGHttpHeaderTimerTask {
    private static int interval = 60;
    private static Timer timer = null;
    private static TimerTask timerTask = null;

    /**
     * 定时更新Token
     */
    public static void updateHeader() {
    	
    	Map<String, ConnectPlatform> map = ConnectPlatformMap.getInstance();
		Collection<ConnectPlatform> valuse = map.values();
		Iterator<ConnectPlatform> iterators = valuse.iterator();
		while (iterators.hasNext()) {
			ConnectPlatform connectPlatform = iterators.next();
			Map<String, String> data = null;
			if (connectPlatform.getTypeId() == ConncetTypeNumber.CONNECTTYPE_SMOKEDETECTOR) {
				// 红光平台才执行这个操作
				String connectPlatformId = connectPlatform.getId()+""; 
				Map<String,String> dataMap = OnenetHGHeaderMap.getInstance().get(connectPlatformId);
				if(dataMap == null){
					dataMap = new HashMap<>();
				}
				dataMap.put("partnerID", connectPlatform.getSmokeDetectorPartnerid());
		    	// header中的accessToken字段由定时刷新token定时器添加
		    	//OnenetHongHeaderMap.getInstance().put("accessToken", OnenetHongConst.partnerID);
				dataMap.put("sequenceId", String.valueOf(System.currentTimeMillis()));
		    	//String timestamp = String.valueOf(System.currentTimeMillis()/1000);
		    	//OnenetHGHeaderMap.getInstance().put("sign", Sign.signValue(OnenetHGConst.PARTNERID, OnenetHGConst.PARTNERKEY, timestamp));
		    	//OnenetHGHeaderMap.getInstance().put("timestamp", timestamp);
				OnenetHGHeaderMap.getInstance().put(connectPlatformId, dataMap);
			}
		}
    
    	System.out.println("hearer string:"+JsonUtil.jsonObj2Sting(OnenetHGHeaderMap.getInstance()));
    }
    
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
            	updateHeader();
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

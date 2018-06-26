package com.glodio.hwnbiot.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.glodio.bean.ConnectPlatform;
import com.glodio.hwnbiot.HttpConnectionManager;
import com.glodio.hwnbiot.beans.Token;
import com.glodio.hwnbiot.maps.AuthHeaderMap;
import com.glodio.hwnbiot.maps.TokenMap;
import com.glodio.util.ConncetTypeNumber;
import com.glodio.util.ConnectPlatformMap;
import com.glodio.util.SystemKeyValueMap;
import com.glodio.util.UrlConfiguration;

import net.sf.json.JSONObject;

public class TokenAndAuthHeaderTimerTask {
    private static int interval = 60;
    private static Timer timer = null;
    private static TimerTask timerTask = null;

    /**
     * 定时更新Token
     */
    public static void requestToken(final ConnectPlatform connectPlatform) {
    	HttpPost httpPost = null;
    	CloseableHttpClient httpClient = null;
    	try {
    		// 设置地址
    		String id = connectPlatform.getId()+"";
    		httpPost = new HttpPost(UrlConfiguration.getLoginUrl(id));
            // 设置实体参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("appId", ConnectPlatformMap.getInstance().get(id).getNbAppid()));
            list.add(new BasicNameValuePair("secret", ConnectPlatformMap.getInstance().get(id).getNbSecret()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(entity);  
        
            HttpResponse response = null;
            httpClient = HttpConnectionManager.getInstance().getHttpClient(id);
            response = httpClient.execute(httpPost);
            
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "UTF-8");
                    //System.out.println(new Date(System.currentTimeMillis())+" "+result);
                    if(result.lastIndexOf("}") != -1) {
                    	JSONObject object = JSONObject.fromObject(result);
                    	Token token = (Token) JSONObject.toBean(object, Token.class);
                    	TokenMap.getInstance().put(id, token.getAccessToken());
                    	Map<String,String> map = new HashMap<String, String>();
                    	map.put("app_key",  ConnectPlatformMap.getInstance().get(id).getNbAppid());
                    	map.put("Authorization",  "Bearer" +" "+ token.getAccessToken());
                    	map.put("Content-Type",  "application/json");
                        AuthHeaderMap.getInstance().put(id,map);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
			if(httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
    	
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
            	if(!ConnectPlatformMap.getInstance().isEmpty()) {
            		Map<String, ConnectPlatform> map = ConnectPlatformMap.getInstance();
            		Collection<ConnectPlatform> valuse = map.values();
            		Iterator<ConnectPlatform> iterators = valuse.iterator();
            		while(iterators.hasNext()){
            			ConnectPlatform connectPlatform  = iterators.next();
            			if(connectPlatform.getTypeId() == ConncetTypeNumber.CONNECTTYPE_HUAIWEI){//只有华为的平台才执行这个操作
            			requestToken(connectPlatform);
            			}	
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

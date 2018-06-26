package com.glodio.hwnbiot;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import com.glodio.hwnbiot.beans.NBCommonConst;
import com.glodio.hwnbiot.maps.AuthHeaderMap;
import com.glodio.util.StringUtil;
import com.glodio.util.UrlConfiguration;

import net.sf.json.*;

public class SubscribeInit {

    private void regUrltoIoCM(JSONObject message, String sendUrl, String id) throws IOException
    {
        int count = 0;
        Long sleepTime = NBCommonConst.REGISTER_URL_MIN_PERIOD;
        
        HttpResponse httpResponse = null;

        while (true)
        {
            ++count;

            if(sleepTime < NBCommonConst.REGISTER_URL_MAX_PERIOD)
            {
                sleepTime = Math.round(Math.pow(2, count)) * NBCommonConst.SECOND_TO_MILLISECOND;
            }
            else
            {
                sleepTime = NBCommonConst.REGISTER_URL_MAX_PERIOD;
            }

            try
            {
                httpResponse = HttpUtil.doPostJson(sendUrl, AuthHeaderMap.getInstance().get(id), message.toString(),id);
                
                System.out.println("Message: "+message.toString()+" "+"Subcribe_URL: "+sendUrl+"  "+"  HttpStatusLine: "+httpResponse.getStatusLine());
                String result = HttpUtil.getHttpResponseBody(httpResponse);
                if ((httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK
                        || httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED)
                        && (StringUtil.strIsNullOrEmpty(result)))
                {
                    break;
                }
            }
            catch (HttpResponseException e)
            {
            	System.out.println(e.getMessage());
            }
            catch (Exception e)
            {
            	System.out.println(e.getMessage());
            }

            try
            {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e)
            {
            	e.printStackTrace();
            }
        }
    }

    /*
     *开始设备操作线程 
     * @param message:1、notifyType通知类型 2、
     * 
     * */
    private void startThread(final JSONObject message, final String url,String id)
    {
        Thread regUrl = new Thread()
        {
            @Override
            public void run()
            {
                try {
					regUrltoIoCM(message, url,id);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        };

        //TemporaryThreadService.getInstance().submit(regUrl);
        regUrl.start();
    }

    public void init(String id)
    {
    	//System.out.println("start init....");
    	// 订阅设备数据推送接口
    	regUpdDeviceDataUrl(id);
        //regAddDeviceUrl();
        //regUpdDeviceInfoUrl();
        //regActDeviceUrl();
        //regDropDeviceUrl();
        //regUpdSrvInfoUrl();
        //regCmdConfirmUrl();
        //regCmdRspUrl();
        //regDeviceEventUrl();
    }

    // ---------------------------------------below 1.1------------------------------------
    private void regActDeviceUrl(String id)
    {
        JSONObject message = new JSONObject();
        message.put("notifyType", NBCommonConst.NOTIFY_TYPE_ACTIVE_DEVICE);
        message.put("callbackurl", UrlConfiguration.getActDevCallbackUrl(id));

        startThread(message, UrlConfiguration.getDataSubscriptionUrl(id),id);
    }

    private void regAddDeviceUrl(String id) //
    {
        JSONObject message = new JSONObject();
        message.put("notifyType", NBCommonConst.NOTIFY_TYPE_ADD_DEVICE);
        message.put("callbackurl", UrlConfiguration.getAddDevCallbackUrl(id));

        startThread(message, UrlConfiguration.getDataSubscriptionUrl(id),id);
    }
    
    private void regDropDeviceUrl(String id) //
    {
        JSONObject message = new JSONObject();
        message.put("notifyType", NBCommonConst.NOTIFY_TYPE_DROP_DEVICE);
        message.put("callbackurl", UrlConfiguration.getDropDevCallbackUrl(id));

        startThread(message, UrlConfiguration.getDataSubscriptionUrl(id),id);
    }

    private void regUpdDeviceInfoUrl(String id) //
    {
        JSONObject message = new JSONObject();
        message.put("notifyType", NBCommonConst.NOTIFY_TYPE_UPDATE_DEVICEINFO);
        message.put("callbackurl", UrlConfiguration.getUpdDevInfoCallbackUrl(id));

        startThread(message, UrlConfiguration.getDataSubscriptionUrl(id),id);
    }

    private void regUpdSrvInfoUrl(String id)
    {
        JSONObject message = new JSONObject();
        message.put("notifyType", NBCommonConst.NOTIFY_TYPE_UPDATE_SERVICEINFO);
        message.put("callbackurl", UrlConfiguration.getUpdSrvInfoCallbackUrl(id));

        startThread(message, UrlConfiguration.getDataSubscriptionUrl(id),id);
    }

    private void regUpdDeviceDataUrl(String id) //
    {
        JSONObject message = new JSONObject();
        message.put("notifyType", NBCommonConst.NOTIFY_TYPE_UPDATE_DEVICEDATA);
        message.put("callbackurl", UrlConfiguration.getUpdDevDataCallbackUrl(id));

        startThread(message, UrlConfiguration.getDataSubscriptionUrl(id),id);
    }

    private void regCmdConfirmUrl(String id)
    {
        JSONObject message = new JSONObject();
        message.put("notifyType", NBCommonConst.NOTIFY_TYPE_MESSAGE_CONFIRM);
        message.put("callbackurl", UrlConfiguration.getCmdConfirmCallbackUrl(id));

        startThread(message, UrlConfiguration.getDataSubscriptionUrl(id),id);
    }

    private void regCmdRspUrl(String id)
    {
        JSONObject message = new JSONObject();
        message.put("notifyType", NBCommonConst.NOTIFY_TYPE_COMMAND_RSP);
        message.put("callbackurl", UrlConfiguration.getCmdRspCallbackUrl(id));

        startThread(message, UrlConfiguration.getDataSubscriptionUrl(id),id);
    }

    private void regDeviceEventUrl(String id)
    {
        JSONObject message = new JSONObject();
        message.put("notifyType", NBCommonConst.NOTIFY_TYPE_DEVICE_EVENT);
        message.put("callbackurl", UrlConfiguration.getDeviceEventCallbackUrl(id));

        startThread(message, UrlConfiguration.getDataSubscriptionUrl(id),id);
    }

}

package com.glodio.hwnbiot.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.glodio.hwnbiot.DeviceService;
import com.glodio.hwnbiot.beans.BatchCommandBody;
import com.glodio.hwnbiot.beans.BatchParam;
import com.glodio.hwnbiot.beans.Command;
import com.glodio.hwnbiot.maps.AuthHeaderMap;
import com.glodio.hwnbiot.maps.DeviceIdMap;
import com.glodio.hwnbiot.maps.HeartbeatMap;
import com.glodio.util.SystemKeyValueMap;
import com.glodio.util.Uuid;

import net.sf.json.JSONObject;

public class HeartbeatTimerTask {
    private static int interval = 60;
    private static Timer timer = null;
    private static TimerTask timerTask = null;


    /**
     * 定时更新AccessToken
     * @param autoScends 任务执行间隔秒数
     * @param loginUrl 请求token地址
     */
    public static void OnStartAccessToken(int autoScends) {
        if (autoScends == 0) {
            autoScends = interval;
        }
        //Date autoTime = new Date();
        timerTask = new TimerTask() {
            @Override
            public void run() {
            	//System.out.println("heartbeat start...");
            	String deviceId = "";
            	String sn = "";
            	if(AuthHeaderMap.getInstance().size() > 0 && HeartbeatMap.getMap().size() > 0 && DeviceIdMap.getInstance().size() > 0 && SystemKeyValueMap.getInstance().size() > 0) {
					Set<Map.Entry<String,Object>> beatSet = HeartbeatMap.getMap().entrySet();
					Set<Map.Entry<String, String>> idSet = DeviceIdMap.getInstance().entrySet();
					List<String> list = new ArrayList<>();
					for(Map.Entry<String, Object> beatEntry:beatSet) {
						for(Map.Entry<String, String> idEntry:idSet) {
							if(beatEntry.getKey().equals(idEntry.getKey())) {
								list.add(idEntry.getValue());
								deviceId = idEntry.getValue();
								sn = idEntry.getKey();
							}
						}
					}
					
					//System.out.println(sn+" "+" "+deviceId+" "+list.get(0));
					BatchCommandBody body = new BatchCommandBody();
					BatchParam param = new BatchParam();
					Command command = new Command();
					
					command.setServiceId("SDData");
					command.setMethod("RESPONSE_DEVICE_BEAT");
			    	JSONObject jsonObject = new JSONObject();
			    	jsonObject.put("SN", "AAAABBBB");
			    	jsonObject.put("RE", "true");
			    	jsonObject.put("FLOW", "a397a255-53be-41fc-996b-521413e9e232");
			    	jsonObject.put("TIME", String.valueOf(System.currentTimeMillis()));
			    	command.setParas(jsonObject);
			    	
			    	param.setType("DeviceList");
			    	param.setDeviceList(list);
			    	param.setCommand(command);
			    	
			    	body.setAppId(SystemKeyValueMap.getInstance().get("nb_appId"));
			    	body.setTimeout(10);//分钟
			    	body.setTaskName("Heartbeat"+"_"+Uuid.getUUID());
			    	body.setTaskType("DeviceCmd");
			    	body.setParam(param);
			    	//TODO 未做
			    	
			    /*	try {
					//	Map<String, Object> map = DeviceService.sendCommands(body);
			    		//Map<String, Object> map = DeviceService.sendCommand(SystemKeyValueMap.getInstance().get("nb_appId"), commandBody);
					//	System.out.println("send commands status is "+map.get("flag")+" "+map.get("msg"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
/*					if(AuthHeaderMap.getInstance().size() > 0) {
						deviceId = "938b426f-0705-40ae-829f-c95d357f5a84";
						sn = "863703030110592";
				    	CommandBody commandBody = new CommandBody();
				    	commandBody.setDeviceId(deviceId);
				    	commandBody.setCallbackURL("http://glodiotest.f3322.net:7080/SPGD/system/device/hwnb/");
				    	Command command = new Command();
				    	command.setServiceId("SDData");
				    	command.setMethod("RESPONSE_DEVICE_BEAT");
				    	
				    	 //{"SN":"SN0000000001","CMD":"CDG","FLOW":"a397a255-53be-41fc-996b-521413e9e232","TIME":"1501145277"},
				    	JSONObject jsonObject = new JSONObject();
				    	jsonObject.put("SN", sn);
				    	jsonObject.put("RE", "true");
				    	jsonObject.put("FLOW", "a397a255-53be-41fc-996b-521413e9e232");
				    	jsonObject.put("TIME", System.currentTimeMillis());
				    	command.setParas(jsonObject);
				    	commandBody.setCommand(command);
				    	
				    	try {
				    		Map<String, Object> map = DeviceService.sendCommand(SystemKeyValueMap.getInstance().get("nb_appId"), commandBody);
							System.out.println("send commands status is "+map.get("flag")+map.get("msg")+map.get("code"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}*/

			
            	}
            }
        };
        timer = new Timer();
        long delay = 10*1000;
        timer.scheduleAtFixedRate(timerTask, delay, autoScends * 1000);
    }
    
    
  //响应终端心跳-----(NB命令)RESPONSE_DEVICE_BEAT
   // {"SN":"SN0000000001","RE":"true","FLOW":"a397a255-53be-41fc-996b-521413e9e232","TIME":"1501145277"}

    
    /**
     * 关闭定时更新AccessToken
     */
    public static void OnStopAccessToken() {
        if (timerTask != null) {
            timerTask.cancel();
        }

        if (timer != null) {
            timer.cancel();
        }
    }
    
}

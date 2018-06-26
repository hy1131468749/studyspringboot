package com.glodio.nokianbiot.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.glodio.bean.ConnectPlatform;
import com.glodio.hwnbiot.HttpUtil;
import com.glodio.hwnbiot.StreamClosedHttpResponse;
import com.glodio.nokianbiot.HttpUtilForNokia;
import com.glodio.nokianbiot.NokiaAuthHeaderMap;
import com.glodio.util.ConncetTypeNumber;
import com.glodio.util.ConnectPlatformMap;
import com.glodio.util.JsonUtil;
import com.glodio.util.LogFile;
import com.glodio.util.SystemKeyValueMap;
import com.glodio.util.UrlConfiguration;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NokiaCreateResourceSubscribeTimerTask {
	private static int interval = 60;
	private static Timer timer = null;
	private static TimerTask timerTask = null;

	/**
	 * 初始化订阅 订阅消息body:"criteria": { "manufacturerData": { "firmwareVersion":
	 * "string", "make": "string", "model": "string" }, "serialNumbers": [
	 * "string" ] }, "deletionPolicy": 0, "groupName": "string", "resources": [
	 * { "conditions": { "gt": 0, "lt": 0, "pmin": 0, "steps": 0 },
	 * "resourcePath": "string" } ], "subscriptionType": "string" }
	 * 
	 * @return boolean
	 */
	public static boolean initSubscribe() {
		// init return result
		boolean result = false;
		Map<String, ConnectPlatform> connectPlatformMap = ConnectPlatformMap.getInstance();
		Collection<ConnectPlatform> valuse = connectPlatformMap.values();
		Iterator<ConnectPlatform> iterators = valuse.iterator();
		while (iterators.hasNext()) {
			ConnectPlatform connectPlatform = iterators.next();
			Map<String, String> data = null;
			if (connectPlatform.getTypeId() == ConncetTypeNumber.CONNECTTYPE_NOKIA) {// 诺基亚的平台才执行这个操作
				Map<String, Object> map = new HashMap<>();
				JSONArray jsonArray = new JSONArray();
				// 组装serialNumbers
				/*
				 * JSONObject serialNumbers = new JSONObject();
				 * serialNumbers.put("serialNumbers", jsonArray); // 组装criteria
				 * map.put("criteria", serialNumbers);
				 */
				// 组装deletionPolicy
				map.put("deletionPolicy", 0);
				// 组装groupName
				map.put("groupName", "DM.TEST.GLODIOTECH");

				JSONObject resource = new JSONObject();
				// 组装conditions
				Map<String, Object> conditions = new HashMap<>();
				// conditions.put("gt", 0);
				// conditions.put("lt", 0);
				conditions.put("pmin", 0);
				conditions.put("steps", 0);
				resource.put("conditions", conditions);
				resource.put("resourcePath", "uplinkMsg/0/data");
				// 组装resources
				jsonArray.add(resource);
				map.put("resources", jsonArray);
				// 组装subscriptionType
				map.put("subscriptionType", "resources");
				String jsonRequest = JsonUtil.jsonObj2Sting(map);

				// System.out.println("Subscribe url:
				// "+UrlConfiguration.getNokiaSubscriptionUrl());
				// System.out.println("Subscribe request body: "+jsonRequest);
				StreamClosedHttpResponse closedHttpResponse = null;
				String strStatusCode = "";
				// System.out.println("Delete all subscribe start...");
				try {
					closedHttpResponse = HttpUtilForNokia.doDeleteForString(
							UrlConfiguration.getNokiaDelAllSubscribeUrl(connectPlatform.getId() + ""),
							NokiaAuthHeaderMap.getInstance().get(connectPlatform.getId() + ""));
					if (closedHttpResponse != null) {
						strStatusCode = closedHttpResponse.getStatusLine().toString();
						LogFile.devLogger.info("[Nokia Subscribe] Delete all subscribe url:[{}],result:[{}]",
								UrlConfiguration.getNokiaDelAllSubscribeUrl(connectPlatform.getId() + ""),
								strStatusCode + " " + closedHttpResponse.getContent());
						// System.out.println("Delete all subscribe
						// statuCode:"+" "+strStatusCode+"
						// message:"+closedHttpResponse.getContent());
						if (strStatusCode.contains("200") || strStatusCode.contains("204")) {
							// System.out.println("New Subscribe start...");
							closedHttpResponse = HttpUtilForNokia.doPostJsonForString(
									UrlConfiguration.getNokiaSubscriptionUrl(connectPlatform.getId() + ""),
									NokiaAuthHeaderMap.getInstance().get(connectPlatform.getId() + ""), jsonRequest);
							if (closedHttpResponse != null) {// 有返回
								strStatusCode = closedHttpResponse.getStatusLine().toString();
								LogFile.devLogger.info("[Nokia Subscribe] New subscribe url:[{}],body:[{}],result:[{}]",
										UrlConfiguration.getNokiaSubscriptionUrl(connectPlatform.getId() + ""),
										jsonRequest, strStatusCode + " " + closedHttpResponse.getContent());
								// System.out.println("Subscribe statuCode:"+"
								// "+strStatusCode+" message:"+"
								// "+closedHttpResponse.getContent());
								if (strStatusCode.contains("202")) {
									result = true;
								}
							}
						}
					}

				} catch (Exception e) {
					LogFile.devLogger.info("[Nokia Subscribe]  Exception:[{}]", e.getMessage());
					// System.out.println(e.getMessage());
				}
			}
		}
		return result;
	}

	/**
	 * 定时更新AccessToken
	 * 
	 * @param autoScends
	 *            任务执行间隔秒数
	 * @param loginUrl
	 *            请求token地址
	 */
	public static void OnStartAccess(int autoScends) {
		if (autoScends == 0) {
			autoScends = interval;
		}
		// Date autoTime = new Date();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				if (!NokiaAuthHeaderMap.getInstance().isEmpty()) {
					if (initSubscribe()) {
						OnStopAccess();
					}
				}
			}
		};
		timer = new Timer();
		long delay = 10 * 1000;
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

package com.glodio.nokianbiot.tasks;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.glodio.bean.ConnectPlatform;
import com.glodio.hwnbiot.StreamClosedHttpResponse;
import com.glodio.nokianbiot.HttpUtilForNokia;
import com.glodio.nokianbiot.NokiaAuthHeaderMap;
import com.glodio.util.ConncetTypeNumber;
import com.glodio.util.ConnectPlatformMap;
import com.glodio.util.JsonUtil;
import com.glodio.util.LogFile;
import com.glodio.util.UrlConfiguration;

import net.sf.json.JSONObject;

public class NokiaApplicationRegisterTimerTask {
	private static int interval = 60;
	private static Timer timer = null;
	private static TimerTask timerTask = null;

	/**
	 * nokia 应用客户端注册
	 */
	public static boolean nokiaApplicatonRegister() {
		boolean result = false;
		Map<String, ConnectPlatform> map = ConnectPlatformMap.getInstance();
		Collection<ConnectPlatform> valuse = map.values();
		Iterator<ConnectPlatform> iterators = valuse.iterator();
		while (iterators.hasNext()) {
			ConnectPlatform connectPlatform = iterators.next();
			Map<String, String> data = null;
			if (connectPlatform.getTypeId() == ConncetTypeNumber.CONNECTTYPE_NOKIA) {// 诺基亚的平台才执行这个操作
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("authorization", NokiaAuthHeaderMap.getInstance().get(connectPlatform.getId()+"").get("Authorization"));
				Map<String, Object> bodyMap = new HashMap<>();
				bodyMap.put("headers", jsonObject);
				bodyMap.put("url",UrlConfiguration.getNokiaNotifyCallbackUrl());
				String jsonRequest = JsonUtil.jsonObj2Sting(bodyMap);
				// System.out.println("Applicaton register url:
				// "+UrlConfiguration.getNokiaAppRegisterUrl());
				// System.out.println("Applicaton register request body:
				// "+jsonRequest);
				// System.out.println("Applicaton register start...");
				try {
					StreamClosedHttpResponse closedHttpResponse = HttpUtilForNokia.doPutJson(
							UrlConfiguration.getNokiaAppRegisterUrl(connectPlatform.getId() + ""),
							NokiaAuthHeaderMap.getInstance().get(connectPlatform.getId() + ""), jsonRequest);
					if (closedHttpResponse != null) {// 有返回
						String strStatusCode = closedHttpResponse.getStatusLine().toString();
						LogFile.devLogger.info("[Nokia Application Register] register url:[{}], body:[{}],"
								+ ":[{}]",
								UrlConfiguration.getNokiaAppRegisterUrl(connectPlatform.getId() + ""), jsonRequest,
								strStatusCode + " " + closedHttpResponse.getContent());
						// System.out.println("Application Register
						// statusCode:"+" "+strStatusCode + " message:"+"
						// "+closedHttpResponse.getContent());
						if (strStatusCode.contains("200")) {
							result = true;
						}
					}
				} catch (Exception e) {
					LogFile.devLogger.info("[Nokia Application Register]  Exception:[{}]", e.getMessage());
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
					if (nokiaApplicatonRegister()) {
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

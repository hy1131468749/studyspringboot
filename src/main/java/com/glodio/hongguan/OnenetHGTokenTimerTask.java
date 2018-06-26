package com.glodio.hongguan;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.glodio.bean.ConnectPlatform;
import com.glodio.hongguan.bean.AccessToken;
import com.glodio.hongguan.bean.OnenetHGConst;
import com.glodio.hongguan.bean.Sign;
import com.glodio.hongguan.bean.Token;
import com.glodio.hwnbiot.StreamClosedHttpResponse;
import com.glodio.nokianbiot.HttpUtilForNokia;
import com.glodio.util.AESUtil;
import com.glodio.util.ConncetTypeNumber;
import com.glodio.util.ConnectPlatformMap;
import com.glodio.util.Dateutil;
import com.glodio.util.SystemKeyValueMap;
//import com.glodio.util.JsonUtil;
//import com.glodio.util.Md5Utils;
import com.glodio.util.UrlConfiguration;

import net.sf.json.JSONObject;

public class OnenetHGTokenTimerTask {
	private static int interval = 3000;
	private static Timer timer = null;
	private static TimerTask timerTask = null;

	private static boolean isFirst = true;

	/**
	 * 定时更新Token
	 */
	public static void requestToken() {
		try {
			Map<String, ConnectPlatform> map = ConnectPlatformMap.getInstance();
			Collection<ConnectPlatform> valuse = map.values();
			Iterator<ConnectPlatform> iterators = valuse.iterator();
			while (iterators.hasNext()) {
				ConnectPlatform connectPlatform = iterators.next();
				Map<String, String> data = null;
				if (connectPlatform.getTypeId() == ConncetTypeNumber.CONNECTTYPE_SMOKEDETECTOR) {// 红光平台才执行这个操作
					// body部分
					String connectPlatformId = connectPlatform.getId() + "";
					String partnerID = ConnectPlatformMap.getInstance().get(connectPlatformId)
							.getSmokeDetectorPartnerid();
					String partnerKey = ConnectPlatformMap.getInstance().get(connectPlatformId)
							.getSmokeDetectorPartnerKey();

					JSONObject jsonObject = new JSONObject();
					jsonObject.put("partnerKey", partnerKey);
					String body = jsonObject.toString();
					// 对body部分进行AES加密
					String aesBody = AESUtil.encrypt(body, OnenetHGConst.SECRETKEY_GAS);

					// header部分
					Map<String, String> hearerMap = new HashMap<>();
					hearerMap.put("partnerID", partnerID);
					hearerMap.put("sequenceId", String.valueOf(System.currentTimeMillis()));
					// sign签名
					String timestamp = Dateutil.getTimestampForStr();
					hearerMap.put("sign", Sign.signValue(body, partnerID, partnerKey, timestamp));
					hearerMap.put("timestamp", timestamp);

					// System.out.println("sign before body
					// content:"+body+OnenetHGConst.PARTNERID+OnenetHGConst.PARTNERKEY+timestamp);
					// System.out.println("sign1
					// content:"+Sign.signValue(body,OnenetHGConst.PARTNERID,
					// OnenetHGConst.PARTNERKEY, timestamp));
					// System.out.println("sign2
					// content:"+Md5Utils.stringMD5(body+OnenetHGConst.PARTNERID+OnenetHGConst.PARTNERKEY+timestamp));
					// System.out.println("Request Token"+"
					// header:"+JsonUtil.jsonObj2Sting(hearerMap)+"
					// "+"body:"+aesBody);

					StreamClosedHttpResponse closedHttpResponse = HttpUtilForNokia
							.doPostJsonForString(UrlConfiguration.getOnenetHGTokenUrl(connectPlatformId), hearerMap, aesBody);
					if (closedHttpResponse != null) {// 有返回
						if (closedHttpResponse.getContent() != null && (!closedHttpResponse.getContent().equals(""))) {
							String content = AESUtil.decrypt(closedHttpResponse.getContent(), OnenetHGConst.SECRETKEY_GAS);
							Token token = (Token) JSONObject.toBean(JSONObject.fromObject(content), Token.class);
							AccessToken accessToken = token.getData();
							System.out.println("accessToken:" + accessToken.getAccessToken());
							Map<String, String> dataMap = OnenetHGHeaderMap.getInstance()
									.get(connectPlatform.getId() + "");
							if (dataMap == null) {
								dataMap = new HashMap<>();
							}
							dataMap.put("accessToken", accessToken.getAccessToken());
							OnenetHGHeaderMap.getInstance().put(connectPlatform.getId() + "", dataMap);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
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

		if (autoScends <= 0) {
			autoScends = interval;
		}
		// Date autoTime = new Date();
		timerTask = new TimerTask() {
			@Override
			public void run() {

				if (isFirst == true) {
					try {
						Thread.sleep(1000 * 20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isFirst = false;
				}

				if (!SystemKeyValueMap.getInstance().isEmpty()) {
					System.out.println("开始初始化");
					requestToken();
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

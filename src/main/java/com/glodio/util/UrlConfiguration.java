package com.glodio.util;

import com.glodio.bean.ConnectPlatform;
import com.glodio.util.SystemKeyValueMap;

public class UrlConfiguration {
	public static final String HTTP = "http://";
	public static final String HTTPS = "https://";

	private static String iotIp;

	private static String iotPort;

	private static String naAddressIp;

	private static String naAddressPort;

	///////////////////////////////////////////////////////
	// 华为NBIoT平台
	// 鉴权
	public static final String LOGIN = "/iocm/app/sec/v1.1.0/login"; // 登录
	// 注册直连设备
	public static final String DEVICE_REGISTER = "/iocm/app/reg/v1.1.0/devices"; // 注册
	// 发现非直连设备
	public static final String DEVICE_CONTROL = "/iocm/app/signaltrans/v1.1.0/devices/%s/services/%s/sendCommand";
	// 查询设备激活状态
	public static final String DEVICE_ACTIVE_STATUS = "/iocm/app/reg/v1.1.0/devices/%s";
	// 删除设备
	public static final String DEVICE_DELETE = "/iocm/app/dm/v1.1.0/devices/%s"; // 删除
	// 修改设备信息
	public static final String DEVICE_UPDATE = "/iocm/app/dm/v1.1.0/devices/%s"; // 修改

	// 按条件批量查询设备信息列表
	public static final String DATA_DEVICES = "/iocm/app/dm/v1.1.0/devices"; // 查询所有设备
	// 查询单个设备信息
	public static final String DATA_DEVICE = "/iocm/app/dm/v1.1.0/devices/%s"; // 查询单个设备
	// application订阅平台信息数据
	public static final String DATA_SUBSCRIPTION = "/iocm/app/sub/v1.2.0/subscribe";// iocm/app/sub/v1.2.0/subscribe
	// 查询设备历史数据
	public static final String DATA_DEVICE_HISTORY = "/iocm/app/data/v1.1.0/deviceDataHistory";
	// 查询设备能力
	public static final String DATA_DEVICE_CAPABILITIES = "/iocm/app/data/v1.1.0/deviceCapabilities";

	// 命令下发
	public static final String DEVICE_COMMAND = "/iocm/app/cmd/v1.4.0/deviceCommands";

	// 批量命令下发
	public static final String DEVICE_COMMANDS = "/iocm/app/batchtask/v1.1.0/tasks";

	// 创建规则
	public static final String RULE_ADD = "/iocm/app/rule/v1.2.0/rules";
	// 更新规则
	public static final String RULE_UPDATE = "/iocm/app/rule/v1.2.0/rules";
	// 修改规则状态
	public static final String RULE_MODIFY_STATUS = "/iocm/app/rule/v1.2.0/rules/{ruleId}";
	// 删除规则
	public static final String RULE_DELETE = "/iocm/app/rule/v1.2.0/rules/{ruleId}";
	// 查找规则
	public static final String RULE_QUERY = "/iocm/app/v1.2.0/rules";

	// public static final String NSCL_UPDATE_DEVICE_DATA_CALLBACK =
	// "/system/device/hwnb/updateDeviceData";
	public static final String NSCL_UPDATE_DEVICE_DATA_CALLBACK = "/system/device/huawei/updateDeviceData";
	public static final String NSCL_DEVICE_CMD_CONFIRM_CALLBACK = "/system/device/huawei/commandConfirmData";

	public static final String NSCL_ADD_DEVICE_CALLBACK = "/system/device/huawei/addDevice";
	public static final String NSCL_ACTIVE_DEVICE_CALLBACK = "/system/device/huawei/activeDevice";
	public static final String NSCL_UPDATE_DEVICE_INFO_CALLBACK = "/system/device/huawei/updateDeviceInfo";
	public static final String NSCL_DROP_DEVICE_CALLBACK = "/system/device/huawei/dropDevice";

	public static final String NSCL_DEVICE_CMD_RSP_CALLBACK = "/system/device/huawei/commandRspData";
	public static final String NSCL_DEVICE_EVENT_CALLBACK = "/system/device/huawei/event";
	public static final String NSCL_UPDATE_SERVICE_INFO_CALLBACK = "/system/device/huawei/updateServiceInfo";

	///////////////////////////////////////////////////////////////////////////////////
	// Nokia NBIoT url resource
	public static final String NOKIA_APPLICATION_REGISTER = "/m2m/applications/registration";
	public static final String NOKIA_NOTIFY_CALLBACK_URL = "/system/device/nokia/updateDeviceData";
	public static final String NOKIA_DEVICE_REGISTER = "/m2m/endpoints";
	public static final String NOKIA_DEVICE_DELETE = "/m2m/endpoints/%s";
	public static final String NOKIA_APPLICATION_SUBSCRIPTION = "/m2m/subscriptions?type=resources";
	public static final String NOKIA_ALL_SUBSCRIPTION = "/m2m/subscriptions?type=resources";
	public static final String NOKIA_DEVICE_COMMAND = "/m2m/endpoints/%s/downlinkMsg/0/data";
	public static final String NOKIA_DELETE_ALL_SUBSCRIPTION = "/m2m/subscriptions";

	/////////////////////////////////////////////////////////////////////////////////////
	// onenet-hongguan
	// 获取token
	public static final String ONENET_HONG_ACCESS_TOKEN = "/token/accessToken";
	// 批量设备登记
	public static final String ONENET_HONG_REGISTER_DEVICE = "/devices/registerDevices";
	// 设备注销
	public static final String ONENET_HONG_UNREGISTER_DEVICE = "/devices/unRegisterDevice";
	// 设备信息查询
	public static final String ONENET_HONG_DEVICE_INFORMATION = "/devices/deviceInfo";
	// 设备操作
	public static final String ONENET_HONG_DEVICE_OPERATION = "/devices/op";
	// 获取设备数据
	public static final String ONENET_HONG_DEVICE_DATA = "/devices/#{partnerID}";

	// public static final String NSCL_QUERY_DEVICE_INFO_CALLBACK =
	// "/EMGD/system/callback/nb/qyeryDeviceInfo";//查询设备不需要订阅回调
	// 获取API Server的详细地址
	private static String getIoTIp(String id) {
		// url="https://186.25.1.56:8743"
		String url = new StringBuilder(ConnectPlatformMap.getInstance().get(id).getHttp()).append("://")
				.append(ConnectPlatformMap.getInstance().get(id).getIp()).append(":")
				.append(ConnectPlatformMap.getInstance().get(id).getPort()).toString();

		return url;
	}

	private static String getNaAddress() {
		String url = new StringBuilder(SystemKeyValueMap.getInstance().get("na_http")).append("://")
				.append(SystemKeyValueMap.getInstance().get("na_ip")).append(":")
				.append(SystemKeyValueMap.getInstance().get("na_port")).append("/")
				.append(SystemKeyValueMap.getInstance().get("web_name")).toString();

		return url;
	}

	////////////////////////////////////////////////////////////
	private static String getNokiaIoTIp(String id) {
		// String url="http://223.167.110.4:8000";
		// TODO 数据库没字段 有问题
		ConnectPlatform bean = ConnectPlatformMap.getInstance().get(id);
		String url = bean.getHttp() + "://" + bean.getIp() + ":" + bean.getPort();
		return url;
	}

	public static String getNokiaAppRegisterUrl(String id) {
		return getNokiaIoTIp(id) + NOKIA_APPLICATION_REGISTER;
	}

	public static String getNokiaNotifyCallbackUrl() {
		return getNaAddress() + NOKIA_NOTIFY_CALLBACK_URL;
	}

	public static String getNokiaDeviceRegisterUrl(String id) {
		return getNokiaIoTIp(id) + NOKIA_DEVICE_REGISTER;
	}

	public static String getNokiaDeviceDeleteUrl(String imei, String id) {
		return getNokiaIoTIp(id) + String.format(NOKIA_DEVICE_DELETE, imei);
	}

	public static String getNokiaSubscriptionUrl(String id) {
		return getNokiaIoTIp(id) + NOKIA_APPLICATION_SUBSCRIPTION;
	}

	public static String getNokiaAllSubscriptionUrl(String id) {
		return getNokiaIoTIp(id) + NOKIA_ALL_SUBSCRIPTION;
	}

	public static String getNokiaDeviceCommandUrl(String imei, String id) {
		return getNokiaIoTIp(id) + String.format(NOKIA_DEVICE_COMMAND, imei);
	}

	public static String getNokiaDelAllSubscribeUrl(String id) {
		return getNokiaIoTIp(id) + NOKIA_DELETE_ALL_SUBSCRIPTION;
	}

	///////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param deviceId
	 * @return send command for url
	 */
	public static String getDeviceCommandUrl(String id) {
		return getIoTIp(id) + DEVICE_COMMAND;
		// return getIoTIp() + String.format(DEVICE_COMMAND, deviceId);
	}

	/**
	 * 
	 * @param deviceId
	 * @return send commands for url
	 */
	public static String getDeviceCommandsUrl(String id) {
		return getIoTIp(id) + DEVICE_COMMANDS;
	}

	/**
	 * 获取登录URL
	 * 
	 * @return
	 */
	public static String getLoginUrl(String id) {
		return getIoTIp(id) + LOGIN;
	}

	/**
	 * 获取注册设备URL
	 * 
	 * @return
	 */
	public static String getDeviceRegisterUrl(String id) {
		return getIoTIp(id) + DEVICE_REGISTER;
	}

	/**
	 * 获取发现非直连设备URL
	 * 
	 * @return
	 */
	public static String getDeviceControlUrl(String deviceId, String serviceId, String id) {
		return getIoTIp(id) + String.format(DEVICE_CONTROL, deviceId, serviceId);
	}

	/**
	 * 获取查询设备激活状态URL
	 * 
	 * @return
	 */
	public static String getDeviceActiveStatusUrl(String deviceId, String id) {
		return getIoTIp(id) + String.format(DEVICE_ACTIVE_STATUS, deviceId);
	}

	/**
	 * 获取删除设备URL
	 * 
	 * @return
	 */
	public static String getDeviceDeleteUrl(String deviceId, String id) {
		return getIoTIp(id) + String.format(DEVICE_DELETE, deviceId);
	}

	/**
	 * 获取修改设备信息URL
	 * 
	 * @return
	 */
	public static String getDeviceUpdateUrl(String deviceId, String id) {
		return getIoTIp(id) + String.format(DEVICE_UPDATE, deviceId);
	}

	/**
	 * 获取按条件批量查询设备信息列表URL
	 * 
	 * @return
	 */
	public static String getDataDevicesUrl(String id) {
		return getIoTIp(id) + DATA_DEVICES;
	}

	/**
	 * 获取查询单个设备信息URL
	 * 
	 * @return
	 */
	public static String getDataDeviceUrl(String deviceId, String id) {
		return getIoTIp(id) + String.format(DATA_DEVICE, deviceId);
	}

	/**
	 * 获取application订阅平台信息数据URL
	 * 
	 * @return
	 */
	public static String getDataSubscriptionUrl(String id) {
		return getIoTIp(id) + DATA_SUBSCRIPTION;
	}

	/**
	 * 获取查询设备历史数据URL
	 * 
	 * @return
	 */
	public static String getDataDeviceHistoryUrl(String id) {
		return getIoTIp(id) + DATA_DEVICE_HISTORY;
	}

	/**
	 * 获取查询设备URL
	 * 
	 * @return
	 */
	public static String getDataDeviceCapabilitiesUrl(String id) {
		return getIoTIp(id) + DATA_DEVICE_CAPABILITIES;
	}

	/**
	 * 获取创建规则URL
	 * 
	 * @return
	 */
	public static String getRuleDddUrl(String id) {
		return getIoTIp(id) + RULE_ADD;
	}

	/**
	 * 获取更新规则URL
	 * 
	 * @return
	 */
	public static String getRuleUpdateUrl(String id) {
		return getIoTIp(id) + RULE_UPDATE;
	}

	/**
	 * 获取修改规则状态URL
	 * 
	 * @return
	 */
	public static String getRuleModifyStatusUrl(String id) {
		return getIoTIp(id) + RULE_MODIFY_STATUS;
	}

	/**
	 * 获取删除规则URL
	 * 
	 * @return
	 */
	public static String getRuleDeleteUrl(String id) {
		return getIoTIp(id) + RULE_DELETE;
	}

	/**
	 * 获取查找规则URL
	 * 
	 * @return
	 */
	public static String getRuleQueryUrl(String id) {
		return getIoTIp(id) + RULE_QUERY;
	}

	// 消息通知

	public static String getActDevCallbackUrl(String id) {
		return getNaAddress() + NSCL_ACTIVE_DEVICE_CALLBACK;
	}

	// 增加设备回调URL
	public static String getAddDevCallbackUrl(String id) {
		return getNaAddress() + NSCL_ADD_DEVICE_CALLBACK;
	}

	public static String getUpdDevInfoCallbackUrl(String id) {
		return getNaAddress() + NSCL_UPDATE_DEVICE_INFO_CALLBACK;
	}

	public static String getUpdDevDataCallbackUrl(String id) {
		return getNaAddress() + NSCL_UPDATE_DEVICE_DATA_CALLBACK;
	}

	public static String getDropDevCallbackUrl(String id) {
		return getNaAddress() + NSCL_DROP_DEVICE_CALLBACK;
	}

	public static String getCmdConfirmCallbackUrl(String id) {
		return getNaAddress() + NSCL_DEVICE_CMD_CONFIRM_CALLBACK;
	}

	public static String getCmdRspCallbackUrl(String id) {
		return getNaAddress() + NSCL_DEVICE_CMD_RSP_CALLBACK;
	}

	public static String getDeviceEventCallbackUrl(String id) {
		return getNaAddress() + NSCL_DEVICE_EVENT_CALLBACK;
	}

	public static String getUpdSrvInfoCallbackUrl(String id) {
		return getNaAddress() + NSCL_UPDATE_SERVICE_INFO_CALLBACK;
	}

	public static String getIotIp() {
		return iotIp;
	}

	public static void setIotIp(String iotIp) {
		UrlConfiguration.iotIp = iotIp;
	}

	public static String getIotPort() {
		return iotPort;
	}

	public static void setIotPort(String iotPort) {
		UrlConfiguration.iotPort = iotPort;
	}

	public static String getNaAddressIp() {
		return naAddressIp;
	}

	public static void setNaAddressIp(String naAddressIp) {
		UrlConfiguration.naAddressIp = naAddressIp;
	}

	public static String getNaAddressPort() {
		return naAddressPort;
	}

	public static void setNaAddressPort(String naAddressPort) {
		UrlConfiguration.naAddressPort = naAddressPort;
	}
	
	/**********oennethongguag的相关方法************/
    private static String getOnenetHGIp(String connectPlatformId) {
        ConnectPlatform c = ConnectPlatformMap.getInstance().get(connectPlatformId);
        return c.getIp();
    }
    
    public static String getOnenetHGTokenUrl(String connectPlatformId){
        return getOnenetHGIp(connectPlatformId) + ONENET_HONG_ACCESS_TOKEN;
    }
    
    public static String getOnenetHGRegisterDevicesUrl(String connectPlatformId){
        return getOnenetHGIp(connectPlatformId) + ONENET_HONG_REGISTER_DEVICE;
    }
    
    public static String getOnenetHGUnRegisterDeviceUrl(String connectPlatformId){
        return getOnenetHGIp(connectPlatformId) + ONENET_HONG_UNREGISTER_DEVICE;
    }
    
    public static String getOnenetHGDeviceInfoUrl(String connectPlatformId){
        return getOnenetHGIp(connectPlatformId) + ONENET_HONG_DEVICE_INFORMATION;
    }
    
    public static String getOnenetHGDeviceOpUrl(String connectPlatformId){
        return getOnenetHGIp(connectPlatformId) + ONENET_HONG_DEVICE_OPERATION;
    }
    
    public static String getOnenetHGDeviceDataUrl(String connectPlatformId){
        return getOnenetHGIp(connectPlatformId) + ONENET_HONG_DEVICE_DATA;
    }
    
	
}

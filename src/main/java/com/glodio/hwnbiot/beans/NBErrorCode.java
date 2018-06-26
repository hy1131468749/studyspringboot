package com.glodio.hwnbiot.beans;

public class NBErrorCode {
    public static String getHttpErrorMsg(String error_code) {
    	String msg = "NBIoT错误";
    	
    	switch (error_code) {
		case "100208":
			msg = "NBIoT_appId或secret错误";
			break;
		case "100022":
			msg = "NBIoT_输入参数无效";
			break;
		case "100006":
			msg = "NBIoT_refreshToken无效";
			break;
		case "100002":
			msg = "NBIoT_错误的token信息";
			break;
		case "100025":
			msg = "NBIoT_获取不到appId鉴权信息";
			break;
		case "100203":
			msg = "NBIoT_应用不存在";
			break;
		case "100217":
			msg = "NBIoT_应用未被授权";
			break;
		case "100007":
			msg = "NBIoT_参数不合法";
			break;
		case "100412":
			msg = "NBIoT_当前应用下设备数量达到上限";
			break;
		case "100003":
			msg = "NBIoT_验证码无效";
			break;
		case "100416":
			msg = "NBIoT_设备已经绑定";
			break;
		case "100001":
			msg = "NBIoT_内部处理错误";
			break;
		case "103026":
			msg = "NBIoT_License不存在";
			break;
		case "103028":
			msg = "NBIoT_License无资源";
			break;
		case "103027":
			msg = "NBIoT_License的销售项不存在";
			break;
		case "100403":
			msg = "NBIoT_设备不存在";
			break;
		case "100418":
			msg = "NBIoT_设备数据不存在";
			break;
		case "100023":
			msg = "NBIoT_数据中数据异常";
			break;
		case "102203":
			msg = "NBIoT_命令名称无效";
			break;
		case "100431":
			msg = "NBIoT_服务类型不存在";
			break;
		case "100428":
			msg = "NBIoT_设备不在线";
			break;
		case "100432":
			msg = "NBIoT_设备命令已经muted";
			break;
		case "100216":
			msg = "NBIoT_应用输入无效";
			break;
		case "100218":
			msg = "NBIoT_网关id和pageNo不能同时为null";
			break;
		case "100220":
			msg = "NBIoT_获取appKey失败";
			break;
		case "100222":
			msg = "NBIoT_回调地址非法";
			break;
		case "101017":
			msg = "NBIoT_从oss获取新的回调地址失败";
			break;
		case "101016":
			msg = "NBIoT_获取iotws地址失败";
			break;
		case "105002":
			msg = "NBIoT_批量任务名已存在";
			break;
		default:
			msg = "NBIoT_其他错误";
			break;
		}
    	
    	return msg;
	}
}

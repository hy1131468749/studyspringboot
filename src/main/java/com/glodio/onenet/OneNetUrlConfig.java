package com.glodio.onenet;

import com.glodio.util.ConnectPlatformMap;

import com.glodio.onenet.bean.CommandParams;

public class OneNetUrlConfig
{
	private static final String HTTP = "http:";
	private static final String HTTPS = "https:";
    
    // 添加设备
    public static final String ADD_DEVICE = "//api.heclouds.com/devices";
    
    // 更新设备
    public static final String UPDATE_DEVICE = "//api.heclouds.com/devices/%s";
    
    // 删除设备
    public static final String DEL_DEVICE = "//api.heclouds.com/devices/%s";
    
    // 下发命令
    public static final String SEND_COMMAND = "//api.heclouds.com/nbiot/execute?imei=%s&obj_id=%s&obj_inst_id=%s&res_id=%s";
    
    public static String getProtocol(String id)
    {
    	return ConnectPlatformMap.getInstance().get(id).getHttp();
    }
    
    /**
     * 获取添加设备 URL
     * @return
     */
    public static String getAddDeviceUrl(String id)
    {
    	return getProtocol(id)+":" + ADD_DEVICE;
    }
    
    /**
     * 获取更新设备 URL
     * @return
     */
    public static String getUpdateDeviceUrl(String deviceId,String id)
    {
    	return getProtocol(id)+":" + String.format(UPDATE_DEVICE, deviceId);
    }
    
    /**
     * 获取删除设备 URL
     * @return
     */
    public static String getDelDeviceUrl(String deviceId,String id)
    {
    	return getProtocol(id)+":" + String.format(DEL_DEVICE, deviceId);
    }
    
    /**
     * 获取下发命令  URL
     * @return
     */
    public static String getCommandSendUrl(CommandParams params,String id)
    {
    	return getProtocol(id)+":" + String.format(SEND_COMMAND, params.getImei(), params.getDataStreamId(), params.getDataPointKey(), params.getResourceId());
    }
}

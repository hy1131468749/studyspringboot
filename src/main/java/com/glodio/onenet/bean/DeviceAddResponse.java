package com.glodio.onenet.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 添加设备的响应信息
 * @author Administrator
 *
 */
public class DeviceAddResponse extends OnenetCommonResponse
{
	private DeviceAddInfo data;
	
	public DeviceAddInfo getData()
	{
		return data;
	}

	public void setData(DeviceAddInfo data)
	{
		this.data = data;
	}
	
	@Override
	public String toString()
	{
		return "DeviceAddResponse [data=" + data + "]";
	}

	/**
	 * 返回的设备添加信息
	 * @author Administrator
	 *
	 */
	public static class DeviceAddInfo
	{
		@JsonProperty(value = "device_id")
		private String deviceId;

		public String getDeviceId()
		{
			return deviceId;
		}

		public void setDeviceId(String deviceId)
		{
			this.deviceId = deviceId;
		}

		@Override
		public String toString()
		{
			return "DeviceAddInfo [deviceId=" + deviceId + "]";
		}
	}
}

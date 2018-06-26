package com.glodio.onenet.bean;

/**
 * 
 * 命令请求参数封装类
 * 
 * @author Administrator
 *
 */
public class CommandParams
{
	// 设备imei号，平台唯一，必填参数
	protected String imei;
	
	// 数据流id
	protected String dataStreamId;
	
	// 数据点key
	protected String dataPointKey;
	
	// 设备的资源id
	protected String resourceId;

	public String getImei()
	{
		return imei;
	}

	public void setImei(String imei)
	{
		this.imei = imei;
	}

	public String getDataStreamId()
	{
		return dataStreamId;
	}

	public void setDataStreamId(String dataStreamId)
	{
		this.dataStreamId = dataStreamId;
	}

	public String getDataPointKey()
	{
		return dataPointKey;
	}

	public void setDataPointKey(String dataPointKey)
	{
		this.dataPointKey = dataPointKey;
	}

	public String getResourceId()
	{
		return resourceId;
	}

	public void setResourceId(String resourceId)
	{
		this.resourceId = resourceId;
	}
}

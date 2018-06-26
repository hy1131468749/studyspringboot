package com.glodio.onenet.bean;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 添加设备的请求信息
 * 
 * @author Administrator
 *
 */
public class DeviceInfoRequest
{
	private String title;

	@JsonProperty(value = "auth_info")
	private Object authInfo;

	private String desc;

	private List<String> tags;

	@Pattern(regexp = "(EDP|MQTT|HTTP|MODBUS|JTEXT|LWM2M)")
	private String protocol = "LWM2M";

	private Location location;

	@Pattern(regexp = "(true|false)")
	@JsonProperty(value = "private")
	private Boolean isPrivate = true;

	@JsonProperty(value = "obsv")
	private Boolean isObserve;

	private Map<String, Object> other;

	private Integer interval;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Object getAuthInfo()
	{
		return authInfo;
	}

	public void setAuthInfo(Object authInfo)
	{
		this.authInfo = authInfo;
	}

	public Boolean getIsObserve()
	{
		return isObserve;
	}

	public void setIsObserve(Boolean isObserve)
	{
		this.isObserve = isObserve;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public List<String> getTags()
	{
		return tags;
	}

	public void setTags(List<String> tags)
	{
		this.tags = tags;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public void setProtocol(String protocol)
	{
		this.protocol = protocol;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public Boolean getIsPrivate()
	{
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate)
	{
		this.isPrivate = isPrivate;
	}

	public Map<String, Object> getOther()
	{
		return other;
	}

	public void setOther(Map<String, Object> other)
	{
		this.other = other;
	}

	public Integer getInterval()
	{
		return interval;
	}

	public void setInterval(Integer interval)
	{
		this.interval = interval;
	}

	/**
	 * 地理位置
	 * 
	 * @author Administrator
	 *
	 */
	public static class Location
	{
		private double lon;

		private double lat;

		private double ele;

		public double getLon()
		{
			return lon;
		}

		public void setLon(double lon)
		{
			this.lon = lon;
		}

		public double getLat()
		{
			return lat;
		}

		public void setLat(double lat)
		{
			this.lat = lat;
		}

		public double getEle()
		{
			return ele;
		}

		public void setEle(double ele)
		{
			this.ele = ele;
		}
	}
}

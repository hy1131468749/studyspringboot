package com.glodio.onenet.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 订阅的上报数据
 * 
 * 数据格式：{"msg":{"at":1502421053000,"type":1,"ds_id":"temperature","value":52,"dev_id":11164801},
 *           "msg_signature":"nN8XTr7divBttwZja88Rvw==", 
 *           "nonce":"_V^r9A6D"}
 * @author Administrator
 *
 */
public class ReceiverDataUpload
{
	private DeviceData msg;
	
	@JsonProperty(value = "enc_msg")
	private String encMsg;

	@JsonProperty(value = "msg_signature")
	private String msgSignature;

	private String nonce;

	public DeviceData getMsg()
	{
		return msg;
	}

	public void setMsg(DeviceData msg)
	{
		this.msg = msg;
	}

	public String getEncMsg()
	{
		return encMsg;
	}

	public void setEncMsg(String encMsg)
	{
		this.encMsg = encMsg;
	}

	public String getMsgSignature()
	{
		return msgSignature;
	}

	public void setMsgSignature(String msgSignature)
	{
		this.msgSignature = msgSignature;
	}

	public String getNonce()
	{
		return nonce;
	}

	public void setNonce(String nonce)
	{
		this.nonce = nonce;
	}
	
	@Override
	public String toString()
	{
		return "ReceiverDataDTOCloud2NA [msg=" + msg + ", msgSignature=" + msgSignature + ", nonce=" + nonce + "]";
	}

	/**
	 * 上报的设备数据
	 * @author Administrator
	 *
	 */
	public static class DeviceData
	{
		private String type;

		@JsonProperty(value = "dev_id")
		private String deviceId;

		@JsonProperty(value = "ds_id")
		private String dataStreamId;

		private Object value;

		@JsonProperty(value = "at")
		private String timestamp;

		public String getType()
		{
			return type;
		}

		public void setType(String type)
		{
			this.type = type;
		}

		public String getDeviceId()
		{
			return deviceId;
		}

		public void setDeviceId(String deviceId)
		{
			this.deviceId = deviceId;
		}

		public String getDataStreamId()
		{
			return dataStreamId;
		}

		public void setDataStreamId(String dataStreamId)
		{
			this.dataStreamId = dataStreamId;
		}

		public Object getValue()
		{
			return value;
		}

		public void setValue(Object value)
		{
			this.value = value;
		}

		public String getTimestamp()
		{
			return timestamp;
		}

		public void setTimestamp(String timestamp)
		{
			this.timestamp = timestamp;
		}

		@Override
		public String toString()
		{
			return "ReceiverDataDTO [type=" + type + ", deviceId=" + deviceId + ", dataStreamId=" + dataStreamId
					+ ", value=" + value + ", timestamp=" + timestamp + "]";
		}
	}
}

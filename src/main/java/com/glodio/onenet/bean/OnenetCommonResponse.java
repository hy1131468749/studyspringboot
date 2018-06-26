package com.glodio.onenet.bean;

/**
 * OneNet 平台的通用响应信息
 * @author Administrator
 *
 */
public class OnenetCommonResponse
{
	private Integer errno;
	
	private String error;

	public Integer getErrno()
	{
		return errno;
	}

	public void setErrno(Integer errno)
	{
		this.errno = errno;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	@Override
	public String toString()
	{
		return "OnenetCommonResponse [errno=" + errno + ", error=" + error + "]";
	}
}

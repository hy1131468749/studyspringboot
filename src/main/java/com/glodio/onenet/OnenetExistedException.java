package com.glodio.onenet;

public class OnenetExistedException extends RuntimeException
{
	private String msg;
	
	public OnenetExistedException()
	{
		super();
	}

	public OnenetExistedException(String msg)
	{
		this.msg = msg;
	}
}

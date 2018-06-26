/*
 * Copyright Notice:
 *      Copyright  1998-2008, Huawei Technologies Co., Ltd.  ALL Rights Reserved.
 *
 *      Warning: This computer software sourcecode is protected by copyright law
 *      and international treaties. Unauthorized reproduction or distribution
 *      of this sourcecode, or any portion of it, may result in severe civil and
 *      criminal penalties, and will be prosecuted to the maximum extent
 *      possible under the law.
 */
package com.glodio.onenet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import httpclient.AuthHttpClient;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class OneNetHttpClient implements AuthHttpClient
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(OneNetHttpClient.class);

	private static OneNetHttpClient instance;

	// OneNet 平台配置
	private String apiKey = "HCQ0t=LXHVCBJVhxbhDzLQnXHbA=";
	
	private OneNetHttpClient()
	{
		
	}

	public static synchronized OneNetHttpClient getInstance()
	{
		logger.debug("<no args> - start");

		if (instance == null)
		{
			instance = new OneNetHttpClient();
		}

		logger.debug("<no args> - end");
		return instance;
	}

	public Map<String, String> getAuthHeader()
	{
		logger.debug("<no args> - start");

		Map<String, String> header = new HashMap<>();
		header.put("api-key", apiKey);

		logger.debug("<no args> - end");
		return header;
	}

	@Override
	public CloseableHttpClient getHttpClient() throws Exception
	{
		logger.debug("<no args> - start");

		CloseableHttpClient returnCloseableHttpClient = HttpClients.createDefault();
		logger.debug("<no args> - end");
		return returnCloseableHttpClient;
	}
}

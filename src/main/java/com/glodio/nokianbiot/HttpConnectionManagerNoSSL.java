package com.glodio.nokianbiot;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpConnectionManagerNoSSL {

	private static PoolingHttpClientConnectionManager cm = null;
	private static HttpConnectionManagerNoSSL instance = new HttpConnectionManagerNoSSL();
	
	private static CloseableHttpClient httpClient = null;
	
	private static RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000)
			.setConnectionRequestTimeout(60000).setStaleConnectionCheckEnabled(true).build();
	static {
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				// .register("https", sslsf)
				.register("http", new PlainConnectionSocketFactory()).build();
		cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
		
		
		httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(defaultRequestConfig)
				.build();
	}

	public static HttpConnectionManagerNoSSL getInstance() {

		return instance;
	}

	private HttpConnectionManagerNoSSL() {

	}

	public CloseableHttpClient  getHttpClient() {

		return httpClient;
	}
}

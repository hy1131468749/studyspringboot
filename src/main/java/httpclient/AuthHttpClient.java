package httpclient;

import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;

public interface AuthHttpClient
{
	Map<String, String> getAuthHeader();
	
	CloseableHttpClient getHttpClient() throws Exception ;
}

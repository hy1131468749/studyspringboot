package httpclient;

import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DefaultHttpClient implements AuthHttpClient
{
	private static DefaultHttpClient instance;

	private DefaultHttpClient()
	{
	}

	public static synchronized DefaultHttpClient getInstance()
	{
		if (instance == null)
		{
			instance = new DefaultHttpClient();
		}
		return instance;
	}
	
	@Override
	public Map<String, String> getAuthHeader()
	{
		return null;
	}

	@Override
	public CloseableHttpClient getHttpClient() throws Exception
	{
		return HttpClients.createDefault();
	}

}

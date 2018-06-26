package httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHandler
{
	private static final Logger log = LoggerFactory.getLogger(HttpHandler.class);
	
	public final static String HTTPGET = "GET";
	public final static String HTTPPUT = "PUT";
	public final static String HTTPPOST = "POST";
	public final static String HTTPDELETE = "DELETE";
	
	public final static String HTTPACCEPT = "Accept";
	public final static String CONTENT_LENGTH = "Content-Length";
	public final static String CHARSET_UTF8 = "UTF-8";

	private AuthHttpClient authHttpClient;
	
	public HttpHandler()
	{
		this.authHttpClient = DefaultHttpClient.getInstance();
	}
	
	public HttpHandler(AuthHttpClient authHttpClient)
	{
		this.authHttpClient = authHttpClient;
	}

	public void setHttpClient(AuthHttpClient authHttpClient) 
	{
		this.authHttpClient = authHttpClient;
	}
	
	public HttpResponse doPost(String url, Map<String, String> headerMap, StringEntity stringEntity)
			throws HttpResponseException
	{
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);

		request.setEntity(stringEntity);

		return executeHttpRequest(request);
	}

	public HttpResponse doPost(String url, Map<String, String> headerMap, InputStream inStream)
			throws HttpResponseException
	{
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);

		request.setEntity(new InputStreamEntity(inStream, 0));

		return executeHttpRequest(request);
	}

	public HttpResponse doPostWithParas(String url, Map<String, String> headerMap, Map<String, String> params)
			throws HttpResponseException, UnsupportedEncodingException
	{
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);

		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<String, String> elem = iterator.next();
			paramsList.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
		}
		
		if (paramsList.size() > 0)
		{
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsList, "UTF-8");
			request.setEntity(entity);
		}
		
		return executeHttpRequest(request);
	}
	
	public String doPostWithParasForString(String url, Map<String, String> headerMap, Map<String, String> params)
			throws HttpResponseException, UnsupportedEncodingException
	{
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);

		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<String, String> elem = iterator.next();
			paramsList.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
		}
		
		if (paramsList.size() > 0)
		{
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsList, "UTF-8");
			request.setEntity(entity);
		}
		
		HttpResponse response = executeHttpRequest(request);
		if (null == response)
		{
			log.error("The response body is null.");
		}
		
		return ((StreamClosedHttpResponse) response).getContent();
	}
	
	public HttpResponse doPostJson(String url, Map<String, String> headerMap, String content)
			throws HttpResponseException
	{
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);
		request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));

		return executeHttpRequest(request);
	}

	public String doPostJsonForString(String url, Map<String, String> headerMap, String content)
			throws HttpResponseException
	{
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);

		request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));

		HttpResponse response = executeHttpRequest(request);
		if (null == response)
		{
			log.error("The response body is null.");
			// throw new
			// HttpResponseException(HttpExceptionEnum.HTTP_RESPONSE_BODY_IS_NULL);
		}

		return ((StreamClosedHttpResponse) response).getContent();
	}

	public HttpResponse doPut(String url, Map<String, String> headerMap, InputStream inStream)
			throws HttpResponseException
	{
		HttpPut request = new HttpPut(url);
		addRequestHeader(request, headerMap);

		request.setEntity(new InputStreamEntity(inStream));

		return executeHttpRequest(request);
	}

	public HttpResponse doPutJson(String url, Map<String, String> headerMap, String content)
			throws HttpResponseException
	{
		HttpPut request = new HttpPut(url);
		addRequestHeader(request, headerMap);

		request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));

		return executeHttpRequest(request);
	}

	public String doPutJsonForString(String url, Map<String, String> headerMap, String content)
			throws HttpResponseException
	{
		HttpResponse response = doPutJson(url, headerMap, content);
		if (null == response)
		{
			log.error("The response body is null.");
		}

		return ((StreamClosedHttpResponse) response).getContent();
	}

	public HttpResponse doGet(String url, Map<String, String> headerMap) throws HttpResponseException
	{
		HttpGet request = new HttpGet(url);
		addRequestHeader(request, headerMap);

		return executeHttpRequest(request);
	}

	public HttpResponse doGetWithParas(String url, Map<String, String> queryParams,
			Map<String, String> headerMap) throws HttpResponseException, HttpRequestException, URISyntaxException
	{
		HttpGet request = new HttpGet();
		addRequestHeader(request, headerMap);

		URIBuilder builder;
		try
		{
			builder = new URIBuilder(url);
		} catch (URISyntaxException e)
		{
			log.error("URISyntaxException: {}", e);
			throw new HttpRequestException(HttpExceptionEnum.HTTP_URI_INVALID);
		}

		if (queryParams != null && !queryParams.isEmpty())
		{
			builder.setParameters(paramsConverter(queryParams));
		}
		request.setURI(builder.build());

		return executeHttpRequest(request);
	}

	public String doGetWithParasForString(String url, Map<String, String> queryParams,
			Map<String, String> headerMap) throws HttpResponseException, HttpRequestException, URISyntaxException
	{
		HttpResponse response = doGetWithParas(url, queryParams, headerMap);
		if (null == response)
		{
			log.error("The response body is null.");
		}

		return ((StreamClosedHttpResponse) response).getContent();
	}

	public HttpResponse doDelete(String url, Map<String, String> headerMap) throws HttpResponseException
	{
		HttpDelete request = new HttpDelete(url);
		addRequestHeader(request, headerMap);

		return executeHttpRequest(request);
	}

	public String doDeleteForString(String url, Map<String, String> headerMap) throws HttpResponseException
	{
		HttpResponse response = doDelete(url, headerMap);
		if (null == response)
		{
			log.error("The response body is null.");
		}

		log.info("The response body is {}.", ((StreamClosedHttpResponse) response).getContent());
		return ((StreamClosedHttpResponse) response).getContent();
	}

	// 设置httppost头部，把map值，设置成httppost头部
	private static void addRequestHeader(HttpUriRequest request, Map<String, String> headerMap)
	{
		if (headerMap == null)
		{
			return;
		}

		for (String headerName : headerMap.keySet())
		{
			if (CONTENT_LENGTH.equalsIgnoreCase(headerName))
			{
				continue;
			}

			String headerValue = headerMap.get(headerName);
			request.addHeader(headerName, headerValue);
		}
	}

	// 执行请求
	private HttpResponse executeHttpRequest(HttpUriRequest request) throws HttpResponseException
	{
		HttpResponse response = null;
		CloseableHttpClient httpclient = null;

		try
		{
			httpclient = authHttpClient.getHttpClient();
			if (httpclient != null)
			{
				response = httpclient.execute(request);
			}
		} catch (Exception e)
		{
			log.error("executeHttpRequest failed.");
			e.printStackTrace();
			// throw new
			// HttpResponseException(HttpExceptionEnum.HTTP_REQUEST_FAILED);
		} finally
		{
			try
			{
				// 获取流中内容，并关闭连接
				response = new StreamClosedHttpResponse(response);
				httpclient.close();
			} catch (IOException e)
			{
				log.error("IOException: " + e.getMessage());
			}
		}

		return response;
	}

	// 把httppost请求返回的httpresponse转换成字符串
	public static String getHttpResponseBody(HttpResponse response) throws UnsupportedOperationException, IOException
	{
		if (response == null)
		{
			return null;
		}

		String body = null;

		if (response instanceof StreamClosedHttpResponse)
		{
			body = ((StreamClosedHttpResponse) response).getContent();
		} else
		{
			HttpEntity entity = response.getEntity();
			if (entity != null && entity.isStreaming())
			{
				String encoding = entity.getContentEncoding() != null ? entity.getContentEncoding().getValue() : null;
				body = StreamUtil.inputStream2String(entity.getContent(), encoding);
			}
		}

		return body;
	}
	
	private static List<NameValuePair> paramsConverter(Map<String, String> params)
	{
		List<NameValuePair> nvps = new LinkedList<NameValuePair>();
		Set<Map.Entry<String, String>> paramsSet = params.entrySet();
		for (Map.Entry<String, String> paramEntry : paramsSet)
		{
			nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
		}

		return nvps;
	}
}

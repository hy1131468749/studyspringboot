package com.glodio.hwnbiot;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HttpUtil
{
/*    public final static String HTTPGET = "GET";

    public final static String HTTPPUT = "PUT";

    public final static String HTTPPOST = "POST";

    public final static String HTTPDELETE = "DELETE";

    public final static String HTTPACCEPT = "Accept";*/

    public final static String CONTENT_LENGTH = "Content-Length";

    public final static String CHARSET_UTF8 = "UTF-8";

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static HttpResponse doPost(String url, Map<String, String> headerMap,
            StringEntity stringEntity,String id) throws Exception
    {
        HttpPost request = new HttpPost(url);
        addRequestHeader(request, headerMap);

        request.setEntity(stringEntity);

        return executeHttpRequest(request,id);
    }

    public static HttpResponse doPost(String url, Map<String, String> headerMap,
            InputStream inStream, String id) throws Exception
    {
        HttpPost request = new HttpPost(url);
        addRequestHeader(request, headerMap);

        request.setEntity(new InputStreamEntity(inStream, 0));

        return executeHttpRequest(request,id);
    }

    
    //向IoT平台发送订阅功能函数
    public static HttpResponse doPostJson(String url,
            Map<String, String> headerMap, String content, String id)
                    throws HttpResponseException
    {
        HttpPost request = new HttpPost(url);
        addRequestHeader(request, headerMap);
        request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
        
        
        return executeHttpRequest(request,id);
    }

    public static StreamClosedHttpResponse doPostJsonForString(String url,
            Map<String, String> headerMap, String content, String id)
                    throws HttpResponseException
    {
    	
        HttpPost request = new HttpPost(url);
        addRequestHeader(request, headerMap);

        request.setEntity(
                new StringEntity(content, ContentType.APPLICATION_JSON));

        HttpResponse response = executeHttpRequest(request,id);
        if (null == response)
        {
        	return null;
            //log.error("The response body is null.");
        }
        
        return ((StreamClosedHttpResponse) response);
    }
    
	public static StreamClosedHttpResponse doPostJsonGetStatusLine(
			String url, Map<String, String> headerMap, String content, String id) throws HttpResponseException{
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);

		request.setEntity(new StringEntity(content,
				ContentType.APPLICATION_JSON));

		HttpResponse response = executeHttpRequest(request,id);
		if (null == response) {
			//System.out.println("The response body is null.");
			return null;
		}

		return (StreamClosedHttpResponse) response;
	}
    
    
    public static HttpResponse doPut(String url, Map<String, String> headerMap,
            InputStream inStream, String id) throws HttpResponseException
    {
        HttpPut request = new HttpPut(url);
        addRequestHeader(request, headerMap);

        request.setEntity(new InputStreamEntity(inStream));

        return executeHttpRequest(request,id);
    }

    public static HttpResponse doPutJson(String url,
            Map<String, String> headerMap, String content, String id)
                    throws HttpResponseException
    {
        HttpPut request = new HttpPut(url);
        addRequestHeader(request, headerMap);

        request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));

        return executeHttpRequest(request,id);
    }
    
    public static StreamClosedHttpResponse doPutJsonForString(String url,
            Map<String, String> headerMap, String content, String id)
                    throws HttpResponseException
    {
    	HttpResponse response = doPutJson(url, headerMap, content,id);
        if (null == response)
        {
        	return null;
        	//System.out.println("The response body is null.");
            //throw new HttpResponseException(HttpExceptionEnum.HTTP_RESPONSE_BODY_IS_NULL);
        }

        return ((StreamClosedHttpResponse) response);
    }

    public static HttpResponse doGet(String url, Map<String, String> headerMap, String id)
            throws HttpResponseException
    {
        HttpGet request = new HttpGet(url);
        addRequestHeader(request, headerMap);

        return executeHttpRequest(request,id);
    }

    public static HttpResponse doDelete(String url,
            Map<String, String> headerMap, String id) throws HttpResponseException
    {
        HttpDelete request = new HttpDelete(url);
        addRequestHeader(request, headerMap);

        return executeHttpRequest(request,id);
    }
    
    public static StreamClosedHttpResponse doDeleteForString(String url,
            Map<String, String> headerMap, String id) throws HttpResponseException
    {
        HttpResponse response = doDelete(url, headerMap,id);
        if (null == response)
        {
        	return null;
            //sslog.error("The response body is null.");
        }

        return ((StreamClosedHttpResponse) response);
    }

    //设置httppost头部，把map值，设置成httppost头部
    private static void addRequestHeader(HttpUriRequest request,
            Map<String, String> headerMap)
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
            //System.out.println(headerName+headerValue);
            request.addHeader(headerName, headerValue);
        }
    }

    //执行请求
    private static HttpResponse executeHttpRequest(HttpUriRequest request, String id)
            throws HttpResponseException
    {
        HttpResponse response = null;
        CloseableHttpClient httpclient = null;

        try
        {
        	httpclient = HttpConnectionManager.getInstance().getHttpClient(id);
        	if (httpclient != null) {
        		response = httpclient.execute(request);
			}
        }
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
        	return null;
        }
        finally
        {
            try
            {
                // 获取流中内容，并关闭连接
            	if(response != null) {
            		response = new StreamClosedHttpResponse(response);
            	}
                
                httpclient.close();
            }
            catch (IOException e)
            {
            	System.out.println(e.getMessage());
            	return null;
            }
        }

        return response;
    }

    //把httppost请求返回的httpresponse转换成字符串
    public static String getHttpResponseBody(HttpResponse response)
            throws UnsupportedOperationException, IOException
    {
        if (response == null)
        {
            return null;
        }
        
        String body = null;

        if (response instanceof StreamClosedHttpResponse)
        {
            body = ((StreamClosedHttpResponse) response).getContent();
        }
        else
        {
            HttpEntity entity = response.getEntity();
            if (entity != null && entity.isStreaming())
            {
                String encoding = entity.getContentEncoding() != null
                        ? entity.getContentEncoding().getValue() : null;
                body = StreamUtil.inputStream2String(entity.getContent(),
                        encoding);
            }
        }

        return body;
    }
}

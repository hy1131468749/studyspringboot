package com.glodio.nokianbiot;

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

import com.glodio.hwnbiot.StreamClosedHttpResponse;
import com.glodio.hwnbiot.StreamUtil;

public class HttpUtilForNokia
{
/*    public final static String HTTPGET = "GET";

    public final static String HTTPPUT = "PUT";

    public final static String HTTPPOST = "POST";

    public final static String HTTPDELETE = "DELETE";

    public final static String HTTPACCEPT = "Accept";*/

    public final static String CONTENT_LENGTH = "Content-Length";

    public final static String CHARSET_UTF8 = "UTF-8";

    private static final Logger log = LoggerFactory.getLogger(HttpUtilForNokia.class);

    public static HttpResponse doPost(String url, Map<String, String> headerMap,
            StringEntity stringEntity) throws Exception
    {
        HttpPost request = new HttpPost(url);
        addRequestHeader(request, headerMap);

        request.setEntity(stringEntity);

        return executeHttpRequest(request);
    }

    public static HttpResponse doPost(String url, Map<String, String> headerMap,
            InputStream inStream) throws Exception
    {
        HttpPost request = new HttpPost(url);
        addRequestHeader(request, headerMap);

        request.setEntity(new InputStreamEntity(inStream, 0));

        return executeHttpRequest(request);
    }

    
    //向IoT平台发送订阅功能函数
    public static HttpResponse doPostJson(String url,
            Map<String, String> headerMap, String content)
                    throws HttpResponseException
    {
        HttpPost request = new HttpPost(url);
        addRequestHeader(request, headerMap);
        request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
        
        
        return executeHttpRequest(request);
    }

    public static StreamClosedHttpResponse doPostJsonForString(String url,
            Map<String, String> headerMap, String content)
                    throws HttpResponseException
    {
    	
        HttpPost request = new HttpPost(url);
        addRequestHeader(request, headerMap);

        request.setEntity(
                new StringEntity(content, ContentType.APPLICATION_JSON));

        StreamClosedHttpResponse response = executeHttpRequestForStream(request);
        if (null == response)
        {
        	return null;
        }
        
        return response;
    }
    
	public static StreamClosedHttpResponse doPostJsonGetStatusLine(
			String url, Map<String, String> headerMap, String content) throws HttpResponseException{
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);

		request.setEntity(new StringEntity(content,
				ContentType.APPLICATION_JSON));

		StreamClosedHttpResponse response = executeHttpRequestForStream(request);
		if (null == response) {
			//System.out.println("The response body is null.");
			return null;
		}

		return response;
	}
    
    
    public static HttpResponse doPut(String url, Map<String, String> headerMap,
            InputStream inStream) throws HttpResponseException
    {
        HttpPut request = new HttpPut(url);
        addRequestHeader(request, headerMap);

        request.setEntity(new InputStreamEntity(inStream));

        return executeHttpRequest(request);
    }

    public static StreamClosedHttpResponse doPutJson(String url,
            Map<String, String> headerMap, String content)
                    throws HttpResponseException
    {
        HttpPut request = new HttpPut(url);
        addRequestHeader(request, headerMap);

        request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));

        return executeHttpRequestForStream(request);
    }
    
    public static StreamClosedHttpResponse doPutJsonForString(String url,
            Map<String, String> headerMap, String content)
                    throws HttpResponseException
    {
    	StreamClosedHttpResponse response = doPutJson(url, headerMap, content);
        if (null == response)
        {
        	return null;
        	//System.out.println("The response body is null.");
            //throw new HttpResponseException(HttpExceptionEnum.HTTP_RESPONSE_BODY_IS_NULL);
        }

        return response;
    }

    public static HttpResponse doGet(String url, Map<String, String> headerMap)
            throws HttpResponseException
    {
        HttpGet request = new HttpGet(url);
        addRequestHeader(request, headerMap);

        return executeHttpRequest(request);
    }

    public static StreamClosedHttpResponse doDelete(String url,
            Map<String, String> headerMap) throws HttpResponseException
    {
        HttpDelete request = new HttpDelete(url);
        addRequestHeader(request, headerMap);

        return executeHttpRequestForStream(request);
    }
    
    public static StreamClosedHttpResponse doDeleteForString(String url,
            Map<String, String> headerMap) throws HttpResponseException
    {
    	StreamClosedHttpResponse response = doDelete(url, headerMap);
        if (null == response)
        {
        	return null;
            //sslog.error("The response body is null.");
        }

        return response;
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
            //System.out.println(headerName+" "+headerValue);
            request.addHeader(headerName, headerValue);
        }
    }
    
    //执行请求
    private static StreamClosedHttpResponse executeHttpRequestForStream(HttpUriRequest request)
            throws HttpResponseException
    {
    	CloseableHttpResponse  response = null;
        CloseableHttpClient httpclient = null;
        StreamClosedHttpResponse closedHttpResponse = null;

        try
        {
        	httpclient = HttpConnectionManagerNoSSL.getInstance().getHttpClient();
        	System.out.println("httpclient:"+httpclient.hashCode());
        	if (httpclient != null) {
        		response = httpclient.execute(request);
			}
        }
        catch (Exception e)
        {   
        	e.printStackTrace();
        	System.out.println("exception:"+e.getMessage());
        	return null;
        	
        }
        finally
        {
            try
            {
            	if(response != null) 
            	{
            		closedHttpResponse = new StreamClosedHttpResponse(response);
            		response.close();
            	}
   
               
            }
            catch (IOException e)
            {
            	System.out.println("IOException:"+e.getMessage());
            	return null;
            }
        }

        return closedHttpResponse;
    }

    //执行请求
    private static HttpResponse executeHttpRequest(HttpUriRequest request)
            throws HttpResponseException
    {
        HttpResponse response = null;
        CloseableHttpClient httpclient = null;

        try
        {
        	httpclient = HttpConnectionManagerNoSSL.getInstance().getHttpClient();
        	if (httpclient != null) {
        		response = httpclient.execute(request);
        		
			}
        }
        catch (Exception e)
        {
        	System.out.println("exception:"+e.getMessage());
        	return null;
        }
        finally
        {
            try
            {
/*                // 获取流中内容，并关闭连接
            	if(response != null) {//Attempted read from closed stream 执行2次StreamClosedHttpResponse会造成此错误。
            	//原因解释：EntityUtils.toString(entity)方法只能调用一次，entity所得到的流是不可重复读取的也就是说所得的到实体只能一次消耗完,不能多次读取。
            	 // 所以去掉转换和打印信息，直接返回httpresponse
            		//System.out.println("httpClient resunt: "+new StreamClosedHttpResponse(response).getContent());
            		//response =  new StreamClosedHttpResponse(response);
            		
            	}*/
            	
                
                httpclient.close();
            }
            catch (IOException e)
            {
            	System.out.println("IOException:"+e.getMessage());
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

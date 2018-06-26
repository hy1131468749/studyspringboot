package com.glodio.hwnbiot;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.glodio.util.ConnectPlatformMap;
import com.glodio.util.SystemKeyValueMap;

public class HttpConnectionManager {

    private PoolingHttpClientConnectionManager cm = null;
    private static HttpConnectionManager instance = null;
    private SSLConnectionSocketFactory sslsf = null;
    private Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
    
    public static HttpConnectionManager getInstance() {
		if(instance == null) {
			instance =  new HttpConnectionManager();
		}
		
		return instance;
	}
    
    public HttpConnectionManager() {

    }
    
    public CloseableHttpClient getHttpClient(String id) {
    	sslsf = null;
        try {
			String path = "";
			path = this.getClass().getResource("/").getPath();
			path = path+"secret/";
			path = path.replace("\\","\\/");
        	
            // 1、导入自己证书
            KeyStore selfCert = KeyStore.getInstance("pkcs12");
            selfCert.load(new FileInputStream(path+ConnectPlatformMap.getInstance().get(id).getNbPkcs12()), ConnectPlatformMap.getInstance().get(id).getNbPkcs12Password().toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
            kmf.init(selfCert, ConnectPlatformMap.getInstance().get(id).getNbPkcs12Password().toCharArray());

            // 2、导入服务器CA证书
            KeyStore caCert = KeyStore.getInstance("jks");
            caCert.load(new FileInputStream(path+ConnectPlatformMap.getInstance().get(id).getNbCa()), ConnectPlatformMap.getInstance().get(id).getNbCaPassword().toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
            tmf.init(caCert);
            
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        	
            sslsf = new SSLConnectionSocketFactory(sc, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("https", sslsf)
                //.register("http", new PlainConnectionSocketFactory())
                .build();
        cm =new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(cm.getMaxTotal());
        
    	@SuppressWarnings("deprecation")
		RequestConfig defaultRequestConfig = RequestConfig.custom()
    			  .setSocketTimeout(5000)
    			  .setConnectTimeout(5000)
    			  .setConnectionRequestTimeout(5000)
    			  .setStaleConnectionCheckEnabled(true)
    			  .build();
    	
    	CloseableHttpClient httpClient = HttpClients.custom()
        .setConnectionManager(cm)
        .setDefaultRequestConfig(defaultRequestConfig)
        .build();          

    	return httpClient;
	}
}

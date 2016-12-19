package com.andaily.springoauth.infrastructure.httpclient;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Wrapper HttpClient operations
 * Default request method: GET
 *
 * @author Shengzhao Li
 */
public class HttpClientExecutor {

    /*
    * Available content Types
    * */
    public static final List<ContentType> CONTENT_TYPES = Arrays.asList(
            ContentType.TEXT_PLAIN, ContentType.TEXT_HTML,
            ContentType.TEXT_XML, ContentType.APPLICATION_XML,
            ContentType.APPLICATION_SVG_XML, ContentType.APPLICATION_XHTML_XML,
            ContentType.APPLICATION_ATOM_XML,
            ContentType.APPLICATION_JSON);


    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpClientExecutor.class);
    //Convert mill seconds to second unit
    protected static final int MS_TO_S_UNIT = 1000;

    //https prefix
    protected static final String HTTPS = "https";

    protected static HttpsTrustManager httpsTrustManager = new HttpsTrustManager();

    protected String url;

    protected int maxConnectionSeconds = 0;

    protected String contentType;

    protected Map<String, String> requestParams = new HashMap<String, String>();

    public HttpClientExecutor(String url) {
        this.url = url;
    }


    @SuppressWarnings("unchecked")
    public <T extends HttpClientExecutor> T maxConnectionSeconds(int maxConnectionSeconds) {
        this.maxConnectionSeconds = maxConnectionSeconds;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends HttpClientExecutor> T addRequestParam(String key, String value) {
        this.requestParams.put(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends HttpClientExecutor> T contentType(String contentType) {
        this.contentType = contentType;
        return (T) this;
    }


    public void execute(HttpResponseHandler responseHandler) {
        try {
            final CloseableHttpResponse response = sendRequest();
            responseHandler.handleResponse(new MkkHttpResponse(response));
            response.close();
        } catch (Exception e) {
            LOGGER.error("Send request to url[" + url + "] failed", e);
        }

    }

    /*
    * Execute and handle exception by yourself
    * */
    public void executeWithException(HttpResponseHandler responseHandler) throws Exception {
        final CloseableHttpResponse response = sendRequest();
        responseHandler.handleResponse(new MkkHttpResponse(response));
        response.close();
    }


    private Map<String,String> headers = new HashMap<>();


    public void addHeaders (Map<String,String> newHeaders){
        headers = new HashMap<>();
        headers.putAll(newHeaders);
    }

    private String postBody;

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public void buildPostBody(HttpPost post){
        StringEntity entity = new StringEntity(this.postBody,"utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
    }

    protected CloseableHttpResponse sendRequest() throws Exception {
        HttpUriRequest request = retrieveHttpRequest();
        setContentType(request);

        HttpPost post = new HttpPost(url);

        if(headers != null && headers.size()>0){
            for(Map.Entry<String,String> entry : headers.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                request.addHeader(key,value);
                post.addHeader(key,value);
            }
        }
        request.addHeader("Encoding","UTF-8");

        CloseableHttpClient client = retrieveHttpClient();

        if(!org.springframework.util.StringUtils.isEmpty(postBody)){

            buildPostBody(post);
            post.addHeader("Content-Type","application/json");
            return client.execute(post);
        }else{
            return client.execute(request);
        }
    }

    private void setContentType(HttpUriRequest request) {
        if (StringUtils.isNotEmpty(this.contentType)) {
            request.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
            LOGGER.debug("Set HttpUriRequest[{}] contentType: {}", request, contentType);
        }
    }

    protected CloseableHttpClient retrieveHttpClient() {
        final RequestConfig requestConfig = requestConfig();
        if (isHttps()) {
            //Support SSL
//            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(createSSLContext());
//            return HttpClients.custom().setDefaultRequestConfig(requestConfig).setSSLSocketFactory(sslConnectionSocketFactory).build();


            try {

                    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                        //信任所有
                        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
                    //ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。
                    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,
                            SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                    return HttpClients.custom().setSSLSocketFactory(sslsf).build();
            }catch (Exception e){
                LOGGER.error("https init error");
            }
            System.out.println("returning null.....");
            return null;

        } else {
            return HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        }
    }

    private RequestConfig requestConfig() {
        final int maxConnMillSeconds = this.maxConnectionSeconds * MS_TO_S_UNIT;
        return RequestConfig.custom()
                .setSocketTimeout(maxConnMillSeconds)
                .setConnectTimeout(maxConnMillSeconds).build();
    }


    private SSLContext createSSLContext() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new HttpsTrustManager[]{httpsTrustManager}, null);
            return sslContext;
        } catch (Exception e) {
            throw new IllegalStateException("Create SSLContext error", e);
        }
    }

    protected boolean isHttps() {
        return url.toLowerCase().startsWith(HTTPS);
    }

    private HttpUriRequest retrieveHttpRequest() {
        final RequestBuilder builder = createRequestBuilder();
        addRequestParams(url,builder);
        return builder.setUri(url).build();
    }

    protected void addRequestParams(String url,RequestBuilder builder) {
        url =url+"?";

        final Set<String> keySet = requestParams.keySet();
        for (String key : keySet) {

            String value = requestParams.get(key);

            if("appNum".equals(key)){
                url += "appNum="+value+"&";
            }

            if("appSecretKey".equals(key)){
                url += "appSecretKey="+value+"&";
            }

            if("redirectUrl".equals(key)){
                url += "redirectUrl="+value+"&";
            }

            if("code".equals(key)){
                url += "code="+value+"&";
            }
            if("grantType".equals(key)){
                url += "grantType="+value+"&";
            }

            builder.addParameter(key, requestParams.get(key));
        }
    }

    protected RequestBuilder createRequestBuilder() {
        return RequestBuilder.get();
    }


    /**
     * Default X509TrustManager implement
     */
    private static class HttpsTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            //ignore
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            //ignore
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
package com.tusdao.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author wxcsdb88
 * @since 2017/11/13 11:29
 */
public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static int DEFAULT_TIMES = 1000;
    private static int DEFAULT_CONNECT_TIMEOUT = 10;
    private static int DEFAULT_SOCKET_TIME_OUT = 60;
    private static String DEFAULT_CONTENT_TYPE = "application/json";
    private static String DEFAULT_CHAR_SET = "UTF-8";
    /**
     * SOCKET_TIME_OUT 连接上一个url后，获取response的返回等待时间 ，即在与目标url建立连接后，
     * 等待放回response的最大时间，在规定时间内没有返回响应的话就抛出SocketTimeout
     * 超时时间 10s
     */
    private int socketTimeout;

    /**
     * CONNECT_TIMEOUT 客户端发送请求到与目标url建立起连接的最大时间
     * 超时时间 3s
     */
    private int connectTimeout;

    private String contentType;

    private String charSet;

    private CloseableHttpClient httpClient;

    public HttpUtils() {
        this(DEFAULT_SOCKET_TIME_OUT, DEFAULT_CONNECT_TIMEOUT, DEFAULT_CONTENT_TYPE, DEFAULT_CHAR_SET);
    }

    public HttpUtils(int socketTimeout, int connectTimeout) {
        this(socketTimeout, connectTimeout, DEFAULT_CONTENT_TYPE, DEFAULT_CHAR_SET);
    }

    public HttpUtils(int socketTimeout, int connectTimeout, String contentType) {
        this(socketTimeout, connectTimeout, contentType, DEFAULT_CHAR_SET);
    }

    private HttpUtils(int socketTimeout, int connectTimeout, String contentType, String charSet) {
        socketTimeout = socketTimeout * DEFAULT_TIMES;
        connectTimeout = connectTimeout * DEFAULT_TIMES;
        if (socketTimeout <= 0) {
            socketTimeout = DEFAULT_SOCKET_TIME_OUT;
        }
        if (connectTimeout <= 0) {
            connectTimeout = DEFAULT_CONNECT_TIMEOUT;
        }
        if (contentType == null || "".equals(contentType)) {
            contentType = DEFAULT_CONTENT_TYPE;
        }
        if (charSet == null || "".equals(charSet)) {
            charSet = DEFAULT_CHAR_SET;
        }
        this.socketTimeout = socketTimeout;
        this.connectTimeout = connectTimeout;
        this.contentType = contentType;
        this.charSet = charSet;
        httpClient = HttpClients.createDefault();
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public String get(String url) throws Exception {
        return get(url, "", socketTimeout, connectTimeout);
    }

    /**
     * Get 请求
     *
     * @param url   请求url
     * @param token 请求 token, 存在于header中
     * @return
     * @throws Exception
     */
    public String get(String url, String token) throws Exception {
        return get(url, token, socketTimeout, connectTimeout);
    }

    public String get(String url, int socketTimeout, int connectTimeout) throws Exception {
        return get(url, "", socketTimeout, connectTimeout);
    }

    /**
     * Get 请求
     *
     * @param url            请求url
     * @param token          请求 token, 存在于header中
     * @param socketTimeout  获取response的返回等待时间
     * @param connectTimeout 客户端发送请求到与目标url建立起连接的最大时间
     * @return
     * @throws Exception
     */
    public String get(String url, String token, int socketTimeout, int connectTimeout) throws Exception {
        Map<String, String> headerMap = new HashMap<>(1);
        headerMap.put("token", token);
        return get(url, headerMap, socketTimeout, connectTimeout);
    }

    /**
     * Get 请求
     *
     * @param url            请求url
     * @param headerMap      请求头
     * @param socketTimeout  获取response的返回等待时间
     * @param connectTimeout 客户端发送请求到与目标url建立起连接的最大时间
     * @return
     * @throws Exception
     */
    public String get(String url, Map<String, String> headerMap, int socketTimeout, int connectTimeout) throws Exception {
        CloseableHttpResponse httpResponse = null;
        HttpEntity entityResponse = null;
        HttpGet get = new HttpGet(url);

        if (headerMap != null && headerMap.size() != 0) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                if (entry.getValue() != null && !"".equals(entry.getValue())) {
                    get.addHeader(entry.getKey(), entry.getValue());
                }
            }
        }
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            get.setConfig(requestConfig);
            httpResponse = httpClient.execute(get);
            entityResponse = httpResponse.getEntity();
            logger.debug(String.format("[Get] url=%s", url));
            return EntityUtils.toString(entityResponse);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (entityResponse != null && !entityResponse.isStreaming()) {
                httpResponse.close();
            }
        }
    }

    public String post(String url) throws Exception {
        return post(url, null, contentType, charSet, socketTimeout, connectTimeout);
    }

    public String post(String url, String token) throws Exception {
        Map<String, String> headerMap = new HashMap<>(1);
        headerMap.put("token", token);
        return post(url, null, headerMap, charSet, socketTimeout, connectTimeout);
    }

    public String post(String url, String token, String contentType) throws Exception {
        Map<String, String> headerMap = new HashMap<>(2);
        headerMap.put("token", token);
        headerMap.put(HTTP.CONTENT_TYPE, contentType);
        return post(url, null, headerMap, charSet, socketTimeout, connectTimeout);
    }

    public String post(String url, String token, Map<String, Object> paramMap) throws Exception {
        Map<String, String> headerMap = new HashMap<>(1);
        headerMap.put("token", token);
        return post(url, paramMap, headerMap, charSet, socketTimeout, connectTimeout);
    }

    public String post(String url, String token, Map<String, Object> paramMap, String contentType) throws Exception {
        Map<String, String> headerMap = new HashMap<>(2);
        headerMap.put("token", token);
        headerMap.put(HTTP.CONTENT_TYPE, contentType);
        return post(url, paramMap, headerMap, charSet, socketTimeout, connectTimeout);
    }

    public String post(String url, Map<String, Object> paramMap) throws Exception {
        return post(url, paramMap, contentType, charSet, socketTimeout, connectTimeout);
    }

    public String post(String url, Map<String, Object> paramMap, int socketTimeout, int connectTimeout) throws Exception {
        return post(url, paramMap, contentType, charSet, socketTimeout, connectTimeout);
    }

    /**
     * @param url
     * @param paramMap
     * @param contentType
     * @return
     * @throws Exception
     */
    public String post(String url, Map<String, Object> paramMap, String contentType) throws Exception {
        Map<String, String> headerMap = new HashMap<>(1);
        headerMap.put(HTTP.CONTENT_TYPE, contentType);
        return post(url, paramMap, headerMap, charSet, socketTimeout, connectTimeout);
    }

    public String post(String url, Map<String, Object> paramMap, String contentType, String charset) throws Exception {
        Map<String, String> headerMap = new HashMap<>(1);
        headerMap.put(HTTP.CONTENT_TYPE, contentType);
        return post(url, paramMap, headerMap, charset, socketTimeout, connectTimeout);
    }

    public String post(String url, Map<String, Object> paramMap, String contentType, String charset, int socketTimeout, int connectTimeout) throws Exception {
        Map<String, String> headerMap = new HashMap<>(1);
        headerMap.put(HTTP.CONTENT_TYPE, contentType);
        return post(url, paramMap, headerMap, charset, socketTimeout, connectTimeout);
    }

    public String post(String url, Map<String, Object> paramMap, Map<String, String> headerMap) throws Exception {
        return post(url, paramMap, headerMap, charSet, socketTimeout, connectTimeout);
    }

    /**
     * post 请求
     *
     * @param url            请求url
     * @param paramMap       请求参数
     * @param headerMap      请求头
     * @param charset        编码
     * @param socketTimeout  获取response的返回等待时间
     * @param connectTimeout 客户端发送请求到与目标url建立起连接的最大时间
     * @return
     * @throws Exception
     */
    public String post(String url, Map<String, Object> paramMap, Map<String, String> headerMap, String charset, int socketTimeout, int connectTimeout) throws Exception {
        CloseableHttpResponse httpResponse = null;
        HttpEntity entityResponse = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(requestConfig);

            if (headerMap != null && headerMap.size() != 0) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (entry.getValue() != null && !"".equals(entry.getValue())) {
                        httpPost.addHeader(entry.getKey(), entry.getValue());
                    }
                }
            }
            JSONObject jsonObject = new JSONObject();
            if (paramMap != null) {
                for (String key : paramMap.keySet()) {
                    jsonObject.put(key, paramMap.get(key));
                }
            }
            StringEntity stringEntity = new StringEntity(jsonObject.toString(), charset);
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);
            entityResponse = httpResponse.getEntity();
            logger.debug(String.format("[Post] url=%s", url));
            return EntityUtils.toString(entityResponse);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (entityResponse != null && !entityResponse.isStreaming()) {
                httpResponse.close();
            }
        }
    }


    public String post(JSON json, String url, String token) throws Exception {
        Map<String, String> headerMap = new HashMap<>(1);
        headerMap.put("token", token);
        return post(json, url, headerMap, charSet, socketTimeout, connectTimeout);
    }

    public String post(JSON json, String url, Map<String, String> headerMap) throws Exception {
        return post(json, url, headerMap, charSet, socketTimeout, connectTimeout);
    }

    public String post(JSON json, String url, Map<String, String> headerMap, String charset, int socketTimeout, int connectTimeout) throws Exception {
        CloseableHttpResponse httpResponse = null;
        HttpEntity entityResponse = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(requestConfig);

            if (headerMap != null && headerMap.size() != 0) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (entry.getValue() != null && !"".equals(entry.getValue())) {
                        httpPost.addHeader(entry.getKey(), entry.getValue());
                    }
                }
            }
            String jsonString = JSONObject.toJSONString("");
            if (json != null) {
                jsonString = json.toString();
            }
            StringEntity stringEntity = new StringEntity(jsonString, charset);
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);
            entityResponse = httpResponse.getEntity();
            logger.debug(String.format("[Post] url=%s", url));
            return EntityUtils.toString(entityResponse);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (entityResponse != null && !entityResponse.isStreaming()) {
                httpResponse.close();
            }
        }
    }

    public String postJsonString(String jsonString, String url, int socketTimeout, int connectTimeout) throws Exception {
        return postJsonString(jsonString, url, null, charSet, socketTimeout, connectTimeout);
    }

    public String postJsonString(String jsonString, String url, String token) throws Exception {
        Map<String, String> headerMap = new HashMap<>(1);
        headerMap.put("token", token);
        return postJsonString(jsonString, url, headerMap, charSet, socketTimeout, connectTimeout);
    }

    public String postJsonString(String jsonString, String url, Map<String, String> headerMap, String charset, int socketTimeout, int connectTimeout) throws Exception {
        CloseableHttpResponse httpResponse = null;
        HttpEntity entityResponse = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            if (socketTimeout == 0) {
                socketTimeout = DEFAULT_SOCKET_TIME_OUT * DEFAULT_TIMES;
            }
            if (connectTimeout == 0) {
                connectTimeout = DEFAULT_CONNECT_TIMEOUT * DEFAULT_TIMES;
            }
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(requestConfig);

            if (headerMap != null && headerMap.size() != 0) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (entry.getValue() != null && !"".equals(entry.getValue())) {
                        httpPost.addHeader(entry.getKey(), entry.getValue());
                    }
                }
            }
            String defaultJsonString = JSONObject.toJSONString("");
            if (jsonString == null) {
                jsonString = defaultJsonString;
            }
            if (charset == null || "".equals(charset)) {
                charset = DEFAULT_CHAR_SET;
            }

            StringEntity stringEntity = new StringEntity(jsonString, charset);
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);
            entityResponse = httpResponse.getEntity();
            logger.debug(String.format("[Post] url=%s", url));
            return EntityUtils.toString(entityResponse);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (entityResponse != null && !entityResponse.isStreaming()) {
                httpResponse.close();
            }
        }
    }

    public String postByteArray(byte[] data, String url, String token) throws Exception {
        CloseableHttpResponse httpResponse = null;
        HttpEntity entityResponse = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            if (socketTimeout == 0) {
                socketTimeout = DEFAULT_SOCKET_TIME_OUT * DEFAULT_TIMES;
            }
            if (connectTimeout == 0) {
                connectTimeout = DEFAULT_CONNECT_TIMEOUT * DEFAULT_TIMES;
            }
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new ByteArrayEntity(data));
            httpPost.setHeader("token", token);
            httpResponse = httpClient.execute(httpPost);
            entityResponse = httpResponse.getEntity();
            logger.debug(String.format("[Post] url=%s", url));
            return EntityUtils.toString(entityResponse);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (entityResponse != null && !entityResponse.isStreaming()) {
                httpResponse.close();
            }
        }
    }

    /**
     * add by tiancq
     * 新增httpclient post请求方法
     */

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;

    public static String doPostForNew(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = null;
        if (apiUrl.startsWith("https")) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

}

package com.openparts.common.utils;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.NoHttpResponseException;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class HttpUtils {

    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";

    // 路由(MAX_PER_ROUTE)是对最大连接数（MAX_TOTAL）的细分，整个连接池的限制数量实际使用DefaultMaxPerRoute并非MaxTotal。
    // 设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)，
    private static final int DEFAULT_MAX_TOTAL = 512;       // 最大支持的连接数
    private static final int DEFAULT_MAX_PER_ROUTE = 64;    // 针对某个域名的最大连接数

    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;     // 跟目标服务建立连接超时时间，根据自己的业务调整
    private static final int DEFAULT_SOCKET_TIMEOUT = 3000;         // 请求的超时时间（建联后，获取response的返回等待时间）
    private static final int DEFAULT_TIMEOUT = 1000;                // 从连接池中获取连接的超时时间

    static {
        httpClient = getHttpClient();
    }

    public static String httpGet(String url) throws Exception {
        return httpGet(url, null);
    }

    public static String httpGet(String url, Map<String, String> params) throws Exception {
        return httpGet(url, params, CHARSET);
    }

    public static String httpGet(String url, Map<String, String> params, String charset) throws Exception {
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value.toString()));
                }
            }
            String queryString = EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            if (url.indexOf("?") > 0) {
                url += "&" + queryString;
            } else {
                url += "?" + queryString;
            }

        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
            return result;
        } finally {
            response.close();
        }
    }

    public static String httpPostString(String url, String strEntity) throws Exception {
        StringEntity entity = new StringEntity(strEntity, "UTF-8");
        return httpPost(url, null, entity);
    }

    public static String httpPost(String url, HttpEntity requestEntity) throws Exception {
        return httpPost(url, null, requestEntity);
    }

    /**
     * 发送body为form格式
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, Map<String, String> params, HttpEntity requestEntity) throws Exception {
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
            String queryString = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
            if (url.indexOf("?") > 0) {
                url += "&" + queryString;
            } else {
                url += "?" + queryString;
            }
        }

        HttpPost httpPost = new HttpPost(url);
        if (requestEntity != null) {
            httpPost.setEntity(requestEntity);
        }

        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            return result;
        } finally {
            response.close();
        }
    }

    /**
     * 发送body为输入流 sample
     */
    public static String postBodyAsStream(String url, InputStream inputStream, String encoding) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        HttpEntity body = new InputStreamEntity(inputStream);
        httpPost.setEntity(body);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            return result;
        } finally {
            response.close();
        }
    }

    public static String postBodyAsMultipart(String url, Map<String, ContentBody> contentBodies) throws Exception {
        return postBodyAsMultipart(url, contentBodies, CHARSET);
    }

    /**
     * 发送body为multipart form sample
     */
    public static String postBodyAsMultipart(String url, Map<String, ContentBody> contentBodies, String charset)
            throws Exception {
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder mb = MultipartEntityBuilder.create();
        mb.setCharset(Charset.forName(charset));
        for (Map.Entry<String, ContentBody> entry : contentBodies.entrySet()) {
            mb.addPart(entry.getKey(), entry.getValue());
        }
        httpPost.setEntity(mb.build());
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            return result;
        } finally {
            response.close();
        }
    }

    public static InputStream httpGetStream(String url, Map<String, String> params) throws Exception {
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value.toString()));
                }
            }
            String queryString = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
            if (url.indexOf("?") > 0) {
                url += "&" + queryString;
            } else {
                url += "?" + queryString;
            }

        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null && entity.isStreaming()) {
                return entity.getContent();
            }
            return null;
        } finally {
            response.close();
        }
    }

    public static String buildUrl(String url, Map<String, String> params, String charset) {
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value.toString()));
                    }
                }
                String queryString = EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
                if (url.indexOf("?") > 0) {
                    url += "&" + queryString;
                } else {
                    url += "?" + queryString;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return url;
    }

    private static CloseableHttpClient getHttpClient() {
        ConnectionConfig config = ConnectionConfig.custom()
                                                  .setCharset(Charsets.UTF_8)
                                                  .build();

        RequestConfig requestConfig = RequestConfig.custom()
                                                   .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
                                                   .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                                                   .setConnectionRequestTimeout(DEFAULT_TIMEOUT)
                                                   .build();

        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);

        // 指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // 信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy)
                    .build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext,
                                                        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 5) {                          // 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) { // 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {   // 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {  // 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {    // 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) { // 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {            // SSL握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        return HttpClientBuilder.create()
                                .setMaxConnPerRoute(DEFAULT_MAX_PER_ROUTE)
                                .setMaxConnTotal(DEFAULT_MAX_TOTAL)
                                .setRetryHandler(httpRequestRetryHandler)
                                .setConnectionManager(connManager)
                                .setDefaultConnectionConfig(config)
                                .setDefaultRequestConfig(requestConfig)
                                .build();
    }

    public static void main(String[] args) throws Exception {
        String url = "http://stat.whatsmars.com/ds/x2/f00qvewaxsqdnrzazhhyguhduc7wd8sv.png";
        InputStream inputStream = HttpUtils.httpGetStream(url, null);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(
                    new File("/Users/javahongxi/Documents/test22222.png"));
            while (true) {
                int i = inputStream.read();
                if (i == -1) {
                    break;
                }
                fileOutputStream.write((byte) i);
            }
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
    }
}

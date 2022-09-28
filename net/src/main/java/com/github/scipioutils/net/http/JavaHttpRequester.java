package com.github.scipioutils.net.http;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.net.http.def.*;
import com.github.scipioutils.net.http.listener.ExecuteErrorHandler;
import com.github.scipioutils.net.http.listener.RequestBodyHandler;
import com.github.scipioutils.net.http.listener.ResponseBodyHandler;
import com.github.scipioutils.net.http.listener.SSLContextInitializer;
import lombok.Data;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * HTTP client based on original Java
 *
 * @author Alan Scipio
 * create date: 2022/9/22
 */
@Data
public class JavaHttpRequester implements HttpRequester {

    private Proxy proxy;

    /**
     * 发生异常时的回调
     */
    private ExecuteErrorHandler executeErrorHandler;

    /**
     * 输出请求体的实现
     */
    private RequestBodyHandler requestBodyHandler = RequestBodyHandler.DEFAULT;

    /**
     * 接收响应体的实现
     */
    private ResponseBodyHandler responseBodyHandler = ResponseBodyHandler.DEFAULT;

    /**
     * SSLContext初始化
     */
    private SSLContextInitializer sslContextInitializer = SSLContextInitializer.DEFAULT;

    /**
     * 信任管理器（决定了信任哪些SSL证书）
     * <p>注：默认信任全部SSL证书</p>
     */
    private TrustManager[] trustManagers = new TrustManager[]{new AllTrustX509TrustManager()};

    //========================================== ↓↓↓↓↓↓ Other APIs ↓↓↓↓↓↓ ==========================================

    /**
     * 关闭代理
     *
     * @param isGlobalSet 是否全局设置
     */
    public JavaHttpRequester turnOffProxy(boolean isGlobalSet) {
        if (isGlobalSet) {
            System.setProperty("http.proxySet", "false");
            System.getProperties().remove("http.proxyHost");
            System.getProperties().remove("http.proxyPort");
            System.getProperties().remove("https.proxyHost");
            System.getProperties().remove("https.proxyPort");
        } else {
            proxy = null;
        }
        return this;
    }

    public JavaHttpRequester turnOffProxy() {
        return turnOffProxy(false);
    }

    /**
     * 打开代理
     *
     * @param isGlobalSet 是否全局设置
     * @param host        代理服务器地址
     * @param port        代理服务器端口
     */
    public JavaHttpRequester turnOnProxy(boolean isGlobalSet, String host, int port) {
        if (StringUtils.isBlank(host)) {
            throw new IllegalArgumentException("proxy host is null");
        }
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("proxy port of host is out of range [0-65535], actual value: " + port);
        }
        if (isGlobalSet) {
            System.setProperty("http.proxySet", "true");
            System.setProperty("http.proxyHost", host);
            System.setProperty("http.proxyPort", port + "");
            System.setProperty("https.proxyHost", host);
            System.setProperty("https.proxyPort", port + "");
        } else {
            SocketAddress sa = new InetSocketAddress(host, port);
            proxy = new Proxy(Proxy.Type.HTTP, sa);
        }
        return this;
    }

    public JavaHttpRequester turnOnProxy(String host, int port) {
        return turnOnProxy(false, host, port);
    }

    /**
     * 设置用Fiddler监听java的请求
     *
     * @see <a href="https://www.telerik.com/fiddler/fiddler-classic">Fiddler Website</a>
     */
    public JavaHttpRequester setFiddlerProxy() {
        System.out.println("============ Set default Fiddler settings for java application ============");
        System.setProperty("proxySet", "true");
        System.setProperty("proxyPort", "8888");
        System.setProperty("proxyHost", "127.0.0.1");
        return this;
    }

    //========================================== ↓↓↓↓↓↓ main method ↓↓↓↓↓↓ ==========================================

    public Response doRequest(Request request) throws HttpRequestException {
        try {
            checkParams(request);
            //确定contentType
            String contentType = determineContentType(request);
            //准备最终url
            URL url;
            if (request.getUrl() == null) {
                String urlPath = prepareUrl(request);
                url = new URL(urlPath);
                request.setUrl(url);
            } else {
                url = request.getUrl();
            }
            //打开连接
            HttpURLConnection conn = (HttpURLConnection) (proxy == null ? url.openConnection() : url.openConnection(proxy));
            //公共的请求头设置
            setCommonConnOptions(conn, contentType, request);
            //如果是https必要的处理
            if (HttpUtils.isHttpsProtocol(url)) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
                httpsConn.setSSLSocketFactory(createSSLSocketFactory(request));//设置SSL
            }
            //开始输出请求体
            if (requestBodyHandler != null) {
                requestBodyHandler.handleOutput(conn, request);
            }
            //接收响应
            int responseCode = conn.getResponseCode();//获取返回的状态码，此处会阻塞直到有响应或超时
            Response response = new Response();
            response.setRequestUrl(url);
            response.setHttpMethod(request.getHttpMethod());
            response.setResponseCode(responseCode);
            response.setAcceptCharset(request.getAcceptCharset());
            response.setContentEncoding(conn.getContentEncoding());
            response.setContentLength(conn.getContentLengthLong());
            response.setContentType(conn.getContentType());
            response.setHeaders(HttpUtils.transformURLConnHeaders(conn.getHeaderFields()));
            response.setConnObj(conn);
            boolean responseSuccess;
            if (responseCode >= 200 && responseCode < 300) {
                responseSuccess = true;
                //成功后的响应回调
                if (request.getResponseSuccessListener() != null) {
                    request.getResponseSuccessListener().handle(response, request);
                }
            } else {
                responseSuccess = false;
                //失败后的响应回调
                if (request.getResponseFailureListener() != null) {
                    request.getResponseFailureListener().handle(response, request);
                }
            }
            //处理响应体
            if (responseBodyHandler != null) {
                responseBodyHandler.handleInput(responseSuccess, conn, response, request);
            }
            return response;
        } catch (Exception e) {
            if (executeErrorHandler != null) {
                executeErrorHandler.handle(request, e);
            }
            throw new HttpRequestException(e);
        }
    }

    //========================================== ↓↓↓↓↓↓ private methods ↓↓↓↓↓↓ ==========================================

    protected void checkParams(Request request) throws IllegalArgumentException {
        if (request == null) {
            throw new IllegalArgumentException("argument request can not be null");
        }
        if (StringUtils.isBlank(request.getUrlPath())) {
            throw new IllegalArgumentException("urlPath can not be blank");
        }
        if (request.getRequestDataMode() == null) {
            request.setRequestDataMode(RequestDataMode.NONE);
        }
        if (request.getResponseDataMode() == null) {
            request.setResponseDataMode(ResponseDataMode.DEFAULT);
        }
    }

    /**
     * 明确请求的contentType
     *
     * @return 如果没用明确指定，则默认按{@link RequestDataMode}来
     */
    protected String determineContentType(Request request) {
        String contentType;
        if (StringUtils.isNotBlank(request.getContentType())) {
            contentType = request.getContentType();
        } else {
            contentType = request.getRequestDataMode().contentType;
            if (request.getRequestDataMode() == RequestDataMode.UPLOAD_FILE) {
                contentType += ("; boundary=" + HttpFileUtils.BOUNDARY);
            }
        }
        return contentType;
    }

    /**
     * 准备最终的url
     */
    protected String prepareUrl(Request request) throws UnsupportedEncodingException {
        String currentUrl = request.getUrlPath();
        if (request.isContentEmpty() || (request.getHttpMethod() != HttpMethod.GET && request.getHttpMethod() != HttpMethod.HEAD)) {
            return currentUrl;
        }
        //为get方法拼接参数
        if (request.getRequestDataMode() == RequestDataMode.FORM) {
            currentUrl = HttpUtils.getUrlWithParams(request.getUrlPath(), request.getFormData(), request.getRequestCharset());
        } else if (request.getRequestDataMode() == RequestDataMode.TEXT_PLAIN) {
            String regex = "((\\w+=?)(\\w*)&?)+";
            if (!Pattern.matches(regex, request.getStrData())) {
                throw new IllegalArgumentException("Invalid request params when using GET method");
            }
            currentUrl += ("?" + request.getStrData());
        } else {
            throw new IllegalArgumentException("Invalid requestDataMode when using GET method! requestDataMode: [" + request.getRequestDataMode().name() + "]");
        }
        return currentUrl;
    }

    /**
     * 设置共通连接参数
     */
    protected void setCommonConnOptions(HttpURLConnection conn, String contentType, Request request) throws ProtocolException {
        //设置连接方法
        conn.setRequestMethod(request.getHttpMethod().value);
        //是否关闭重定向以获取跳转后的真实地址
        conn.setInstanceFollowRedirects(request.isFollowRedirects());
        //设置连接超时的时间(毫秒)
        conn.setConnectTimeout(request.getConnectTimeout());
        conn.setReadTimeout(request.getReadTimeout());
        //设置连接打开输入流
        conn.setDoInput(true);
        //设置连接打开输出
        if (request.getHttpMethod() == HttpMethod.POST) {
            conn.setDoOutput(true);
        }
        conn.setRequestProperty("Accept-Encoding", "gzip,deflate");//告诉服务器支持gzip压缩
        conn.setRequestProperty("Accept-Charset", request.getAcceptCharset());
        conn.setRequestProperty("Charset", request.getRequestCharset());
        //设置content-type
        if (contentType != null) {
            conn.setRequestProperty("Content-Type", contentType);//可被后面的headerParam覆盖
        }
        //设置user-agent
        if (StringUtils.isNotBlank(request.getUserAgent())) {
            conn.setRequestProperty("User-Agent", request.getUserAgent());
        }
        //设置自定义请求头参数
        if (request.getHeaders() != null && request.getHeaders().size() > 0) {
            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 创建SSLSocket工厂以用于HTTPS连接
     */
    protected SSLSocketFactory createSSLSocketFactory(Request request)
            throws Exception {
        if (sslContextInitializer == null) {
            sslContextInitializer = SSLContextInitializer.DEFAULT;
        }
        SSLContext sslContext = sslContextInitializer.build(trustManagers, request.getTlsProtocol());
        //从上述SSLContext对象中得到SSLSocketFactory对象
        return sslContext.getSocketFactory();
    }

}

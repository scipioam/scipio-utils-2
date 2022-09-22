package com.github.scipioutils.core.net.http;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.core.io.stream.InputStreamWrapper;
import com.github.scipioutils.core.io.stream.StreamParser;
import com.github.scipioutils.core.net.http.def.*;
import com.github.scipioutils.core.net.http.listener.SSLContextInitializer;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * HTTP client based on original Java
 *
 * @author Alan Scipio
 * create date: 2022/9/22
 */
@Data
@Accessors(chain = true)
public class JavaHttpRequester implements HttpRequester {

    //========================================== ↓↓↓↓↓↓ API ↓↓↓↓↓↓ ==========================================

    //========================================== ↓↓↓↓↓↓ 主要方法 ↓↓↓↓↓↓ ==========================================

    public Response doRequest(Request request) throws Exception {
        //确定contentType
        String contentType = determineContentType(request);
        //准备最终url
        String urlPath = prepareUrl(request);
        URL url = new URL(urlPath);
        //打开连接
        HttpURLConnection conn = (HttpURLConnection) (request.getProxy() == null ? url.openConnection() : url.openConnection(request.getProxy()));
        //公共的请求头设置
        setCommonConnOptions(conn, contentType, request);
        //如果是https必要的处理
        if (HttpUtils.isHttpsProtocol(url)) {
            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            httpsConn.setSSLSocketFactory(createSSLSocketFactory(request));//设置SSL
        }
        //开始输出请求内容
        outputRequestContent(conn, request);
        //接收响应
        int responseCode = conn.getResponseCode();//获取返回的状态码，此处会阻塞直到有响应或超时
        Response response = new Response();
        response.setRequestUrl(urlPath);
        response.setHttpMethod(request.getHttpMethod());
        response.setResponseCode(responseCode);
        response.setAcceptCharset(request.getAcceptCharset());
        response.setContentEncoding(conn.getContentEncoding());
        response.setContentLength(conn.getContentLengthLong());
        response.setContentType(conn.getContentType());
        response.setHeaders(HttpUtils.transformURLConnHeaders(conn.getHeaderFields()));
        response.setConnObj(conn);
        if (responseCode >= 200 && responseCode < 300) {
            handleResponseContent(conn, response, request);
            //成功后的响应回调
            if (request.getResponseSuccessHandler() != null) {
                request.getResponseSuccessHandler().handle(response, request);
            }
        } else {
            //失败后的响应回调
            if (request.getResponseFailureHandler() != null) {
                request.getResponseFailureHandler().handle(response, request);
            }
        }
        return response;
    }

    //========================================== ↓↓↓↓↓↓ 内部方法 ↓↓↓↓↓↓ ==========================================

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
        if (request.isContentEmpty() || request.getHttpMethod() != HttpMethod.GET || request.getHttpMethod() != HttpMethod.HEAD) {
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
        SSLContextInitializer sslContextInitializer = request.getSslContextInitializer();
        if (sslContextInitializer == null) {
            sslContextInitializer = SSLContextInitializer.DEFAULT;
        }
        SSLContext sslContext = sslContextInitializer.build(request.getTrustManagers(), request.getTlsProtocol());
        //从上述SSLContext对象中得到SSLSocketFactory对象
        return sslContext.getSocketFactory();
    }

    /**
     * 输出请求内容（请求体）
     */
    protected void outputRequestContent(HttpURLConnection conn, Request request) throws IOException {
        if (request.isContentEmpty() || request.getHttpMethod() == HttpMethod.GET || request.getHttpMethod() == HttpMethod.HEAD) {
            return;
        }
        try (OutputStream out = conn.getOutputStream()) {
            if (request.getRequestDataMode() == RequestDataMode.UPLOAD_FILE) {
                //上传文件
                HttpFileUtils.build().uploadFiles(
                        conn,
                        request.getFormData(),
                        request.getUploadFiles(),
                        request.getRequestCharset(),
                        request.getFileBufferSize(),
                        request.getUploadListener()
                );
            } else if (request.getRequestDataMode() != RequestDataMode.NONE) {
                //其他上传方式
                byte[] requestContent = request.getData();
                if (requestContent != null) {
                    //将数据输出
                    out.write(requestContent);
                }
            }
        }
    }

    /**
     * 处理响应内容
     */
    protected void handleResponseContent(HttpURLConnection conn, Response response, Request request) throws IOException {
        if (request.getResponseDataMode() == null) {
            request.setResponseDataMode(ResponseDataMode.DEFAULT);
        }
        response.setResponseDataMode(request.getResponseDataMode());
        //处理响应数据
        if (request.getResponseDataMode() == ResponseDataMode.DEFAULT) {
            //从input流读取字节数据
            try (InputStream in = conn.getInputStream()) {
                InputStreamWrapper wrapper = null;
                if (StringUtils.isNotBlank(response.getContentEncoding()) && response.getContentEncoding().equalsIgnoreCase("gzip")) {
                    //响应体为gzip压缩
                    wrapper = InputStreamWrapper.GZIP;
                }
                byte[] data = StreamParser.build().read(in, wrapper);
                response.setData(data);
            }
        } else if (request.getResponseDataMode() == ResponseDataMode.STREAM_ONLY) {
            //直接返回input流
            InputStream in = null;
            try {
                in = conn.getInputStream();
                response.setDataStream(conn.getInputStream());
            } catch (IOException e) {
                if (in != null) {
                    in.close();
                }
                throw new IOException(e);
            }
        } else if (request.getResponseDataMode() == ResponseDataMode.DOWNLOAD) {
            //下载
            File downloadFile = HttpFileUtils.build().downloadFile(
                    request.getDownloadFilePath(),
                    conn.getInputStream(),
                    response.getContentLength(),
                    request.getFileBufferSize(),
                    request.isDownloadAutoSuffix(),
                    response.getContentType(),
                    request.getDownloadListener()
            );
            response.setDownloadFile(downloadFile);
        }
        //responseDataMode是NONE则无作为直接返回
    }

}

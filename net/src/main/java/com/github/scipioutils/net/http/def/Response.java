package com.github.scipioutils.net.http.def;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.net.mime.ApplicationType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应的数据
 *
 * @author Alan Scipio
 * Create Date: 2020/9/24
 */
@Data
@Accessors(chain = true)
public class Response {

    /**
     * 执行代码出现异常时的响应码
     */
    public static final int EXECUTE_ERR_CODE = -1;

    //========================================== ↓↓↓↓↓↓ 基础设定 ↓↓↓↓↓↓ ==========================================

    /**
     * 请求url
     */
    private URL requestUrl;

    /**
     * HTTP请求方法
     */
    private HttpMethod httpMethod;

    /**
     * 响应码
     */
    private Integer responseCode;

    /**
     * 接收的字符集
     */
    private String acceptCharset;

    /**
     * 响应头
     */
    private Map<String, String> headers;

    /**
     * 响应的压缩编码
     */
    private String contentEncoding;

    /**
     * 响应的数据长度
     */
    private Long contentLength;

    /**
     * 响应数据类型
     */
    private String contentType;

    /**
     * 响应的连接对象
     */
    private URLConnection connObj;

    /**
     * 异常信息
     */
    private String errorMsg;

    //========================================== ↓↓↓↓↓↓ 数据设定 ↓↓↓↓↓↓ ==========================================

    /**
     * 响应数据
     */
    private byte[] data;

    /**
     * 下载的文件
     */
    private File downloadFile;

    /**
     * 接收响应数据的模式
     */
    private ResponseDataMode responseDataMode;

    /**
     * 响应的输入流（只当responseDataMode为直接返回输入流时才set）
     */
    private InputStream dataStream;

    //========================================== ↓↓↓↓↓↓ 方法 ↓↓↓↓↓↓ ==========================================

    public Response() {
    }

    public Response(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getHeader(String key) {
        if (headers == null) {
            return null;
        }
        return headers.get(key);
    }

    public void addHeader(String key, String value) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
    }

    /**
     * HTTP请求是否成功（响应码为2xx）
     * @return true：成功
     */
    public boolean isSuccess() {
        if (responseCode == null) {
            return false;
        } else {
            return responseCode >= 200 && responseCode < 300;
        }
    }

    /**
     * 猜测响应体是否不为空
     * @return true：不为空
     */
    public boolean guessBodyIsNotEmpty() {
        if (contentLength != null && contentLength > -1) {
            return contentLength > 0L;
        } else if (StringUtils.isNotBlank(contentEncoding)) {
            return true;
        } else if (StringUtils.isNotBlank(contentType)) {
            return contentType.startsWith(ApplicationType.JSON.getTemplate())
                    || contentType.startsWith(ApplicationType.XML.getTemplate())
                    || contentType.startsWith("text");
        } else {
            return false;
        }
    }

    public String getStrData() {
        if (StringUtils.isNotBlank(contentType)) {
            String lowerContentType = contentType.toLowerCase();
            if (!lowerContentType.startsWith(ApplicationType.JSON.getTemplate())
                    && !lowerContentType.startsWith(ApplicationType.XML.getTemplate())
                    && !lowerContentType.startsWith("text")) {
                throw new IllegalStateException("response contentType is not a string type: " + contentType);
            }
        }
        try {
            return new String(data, 0, data.length, acceptCharset);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("unsupported accept charset: " + acceptCharset, e);
        }
    }

}

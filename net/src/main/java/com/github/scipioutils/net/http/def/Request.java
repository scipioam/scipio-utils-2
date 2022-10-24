package com.github.scipioutils.net.http.def;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.net.http.HttpUtils;
import com.github.scipioutils.net.http.listener.DownloadListener;
import com.github.scipioutils.net.http.listener.ResponseFailureListener;
import com.github.scipioutils.net.http.listener.ResponseSuccessListener;
import com.github.scipioutils.net.http.listener.UploadListener;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 请求参数
 *
 * @author Alan Scipio
 * create date: 2022/9/22
 */
@Data
@Accessors(chain = true)
public class Request {

    //========================================== ↓↓↓↓↓↓ 基础设定 ↓↓↓↓↓↓ ==========================================

    /**
     * 接受内容的编码
     */
    private String acceptCharset = "utf-8";

    /**
     * 发送内容的编码
     */
    private String requestCharset = "utf-8";

    /**
     * 请求的URL
     */
    private String urlPath;

    /**
     * 请求的URL(urlPath不为空，自身为空时，将从urlPath里构建得到)
     */
    private URL url;

    /**
     * HTTP请求方法
     */
    private HttpMethod httpMethod;

    /**
     * Content-Type
     */
    private String contentType;

    /**
     * 自定义请求头
     */
    private Map<String, String> headers;

    /**
     * 连接超时，单位毫秒
     */
    private int connectTimeout = 60000;

    /**
     * 读取输入流超时，单位毫秒
     */
    private int readTimeout = 60000;

    /**
     * 是否关闭重定向以获取跳转后的真实地址,默认false
     */
    private boolean isFollowRedirects = false;

    /**
     * User-Agent
     */
    private String userAgent;

    /**
     * TLS协议
     * <p>
     * 注：Java11以下底层只支持到TLSv1.2，需要加入OpenJSSE库以扩展对TLSv1.3的支持
     * </p>
     *
     * @see <a href="https://github.com/openjsse/openjsse">OpenJSSE</a>
     */
    private String tlsProtocol;

    //========================================== ↓↓↓↓↓↓ 数据设定 ↓↓↓↓↓↓ ==========================================

    /**
     * 请求数据的模式（获取对应的content）
     */
    private RequestDataMode requestDataMode;

    /**
     * 接收响应数据的模式
     */
    private ResponseDataMode responseDataMode;

    /**
     * 请求数据
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private byte[] data;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.NONE)
    private Map<String, String> formData;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.NONE)
    private String strData;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.NONE)
    private Map<String, File> uploadFiles;

    //========================================== ↓↓↓↓↓↓ 上传/下载相关 ↓↓↓↓↓↓ ==========================================

    /**
     * 上传\下载的缓冲池大小（单位字节）
     */
    private int fileBufferSize = 4096;

    /**
     * 下载文件的全路径
     */
    private String downloadFilePath;

    /**
     * 下载时自动生成文件后缀（根据contentType进行截取）
     */
    private boolean downloadAutoSuffix = false;

    //========================================== ↓↓↓↓↓↓ 回调相关 ↓↓↓↓↓↓ ==========================================

    /**
     * 连接上服务器并返回2xx响应码时的回调
     */
    private ResponseSuccessListener responseSuccessListener;

    /**
     * 连接上服务器并返回非2xx响应码时的回调
     */
    private ResponseFailureListener responseFailureListener;

    /**
     * 下载监听器
     */
    private DownloadListener downloadListener;

    /**
     * 上传监听器
     */
    private UploadListener uploadListener;

    //========================================== ↓↓↓↓↓↓ 部分getter/setter方法 ↓↓↓↓↓↓ ==========================================

    public Request setDefaultUserAgent() {
        this.userAgent = PresetUserAgent.getDefault().val;
        return this;
    }

    public Request setData(Map<String, String> formData) {
        this.formData = formData;
        if (requestDataMode == null) {
            requestDataMode = RequestDataMode.FORM;
        }
        return this;
    }

    public Request setData(String strData) {
        this.strData = strData;
        return this;
    }

    public Request setXmlData(String xmlData) {
        this.strData = xmlData;
        this.requestDataMode = RequestDataMode.XML;
        return this;
    }

    public Request setJsonData(String jsonData) {
        this.strData = jsonData;
        this.requestDataMode = RequestDataMode.JSON;
        return this;
    }

    public Request addFormData(String key, String data) {
        if (requestDataMode == null) {
            requestDataMode = RequestDataMode.FORM;
        }
        if (formData == null) {
            formData = new HashMap<>();
        }
        formData.put(key, data);
        return this;
    }

    public Request setUploadFiles(Map<String, File> uploadFiles) {
        this.uploadFiles = uploadFiles;
        this.requestDataMode = RequestDataMode.UPLOAD_FILE;
        return this;
    }

    public Request addUploadFile(String fileKey, File file) {
        if (this.uploadFiles == null) {
            this.uploadFiles = new HashMap<>();
        }
        uploadFiles.put(fileKey, file);
        this.requestDataMode = RequestDataMode.UPLOAD_FILE;
        return this;
    }

    public Request addUploadFile(File file) {
        return addUploadFile(file.getName(), file);
    }

    public boolean isContentEmpty() {
        return (StringUtils.isBlank(strData) && (formData == null || formData.size() == 0) && (uploadFiles == null || uploadFiles.size() == 0));
    }

    public Request setAllCharsets(String charset) {
        return setRequestCharset(charset).setAcceptCharset(charset);
    }

    public byte[] getData() {
        try {
            switch (requestDataMode) {
                case FORM:
                    String strForm = HttpUtils.getParams(formData, requestCharset);
                    return strForm.getBytes(requestCharset);
                case XML:
                case JSON:
                case TEXT_PLAIN:
                    if (StringUtils.isNotBlank(strData)) {
                        return strData.getBytes(requestCharset);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Request addHeader(String key, String value) {
        if (StringUtils.isBlank(key)) {
            return this;
        }
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return this;
    }

    public String getHeader(String key) {
        if (headers == null) {
            return null;
        }
        return headers.get(key);
    }

    /**
     * 准备最终的url
     */
    public URL getFinalUrl() throws UnsupportedEncodingException, MalformedURLException {
        if (url != null) {
            return url;
        }

        String currentUrl = urlPath;
        if (isContentEmpty() || (getHttpMethod() != HttpMethod.GET && getHttpMethod() != HttpMethod.HEAD)) {
            url = new URL(currentUrl);
            return url;
        }
        //为get方法拼接参数
        if (getRequestDataMode() == RequestDataMode.FORM) {
            currentUrl = HttpUtils.getUrlWithParams(getUrlPath(), getFormData(), getRequestCharset());
        } else if (getRequestDataMode() == RequestDataMode.TEXT_PLAIN) {
            String regex = "((\\w+=?)(\\w*)&?)+";
            if (!Pattern.matches(regex, getStrData())) {
                throw new IllegalArgumentException("Invalid request params when using GET method");
            }
            currentUrl += ("?" + getStrData());
        } else {
            throw new IllegalArgumentException("Invalid requestDataMode when using GET method! requestDataMode: [" + getRequestDataMode().name() + "]");
        }
        url = new URL(currentUrl);
        return url;
    }

}

package com.github.scipioutils.core.net.http.def;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.core.net.http.AllTrustX509TrustManager;
import com.github.scipioutils.core.net.http.HttpUtils;
import com.github.scipioutils.core.net.http.listener.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.net.ssl.TrustManager;
import java.io.File;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

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
     * User-Agent
     */
    private String urlPath;

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
     * 代理
     */
    private Proxy proxy;

    /**
     * TLS协议，JDK11以下默认为TLSv1.2
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
     * 下载文件的全路径，如果不为空则代表需要下载
     */
    private String downloadFilePath;

    /**
     * 下载时自动生成文件后缀（根据contentType进行截取）
     */
    private boolean downloadAutoSuffix = false;

    //========================================== ↓↓↓↓↓↓ 回调相关 ↓↓↓↓↓↓ ==========================================

    protected ResponseSuccessHandler responseSuccessHandler;

    protected ResponseFailureHandler responseFailureHandler;

    protected DownloadListener downloadListener;

    protected UploadListener uploadListener;

    /**
     * SSLContext初始化者
     */
    protected SSLContextInitializer sslContextInitializer;

    /**
     * 信任管理器（决定了信任哪些SSL证书）
     */
    protected TrustManager[] trustManagers = new TrustManager[]{new AllTrustX509TrustManager()};

    //========================================== ↓↓↓↓↓↓ 方法 ↓↓↓↓↓↓ ==========================================

    public Request setData(Map<String, String> formData) {
        this.formData = formData;
        return this;
    }

    public Request setData(String strData) {
        this.strData = strData;
        return this;
    }

    public Request setUploadFiles(Map<String, File> uploadFiles) {
        this.uploadFiles = uploadFiles;
        return this;
    }

    public Request addUploadFile(String fileKey, File file) {
        if (this.uploadFiles == null) {
            this.uploadFiles = new HashMap<>();
        }
        uploadFiles.put(fileKey, file);
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

}

package com.github.scipioutils.core.net.http;

import com.github.scipioutils.core.net.http.listener.ExecuteErrorHandler;
import com.github.scipioutils.core.net.http.listener.RequestBodyHandler;
import com.github.scipioutils.core.net.http.listener.ResponseBodyHandler;
import com.github.scipioutils.core.net.http.listener.SSLContextInitializer;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.net.ssl.TrustManager;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Scipio
 * create date: 2022/9/22
 */
@Setter
@Accessors(chain = true)
public abstract class HttpRequesterFactory {

    public abstract HttpRequester getRequester();

    //========================================== ↓↓↓↓↓↓ fields ↓↓↓↓↓↓ ==========================================

    protected Proxy proxy;

    /**
     * 发生异常时的回调
     */
    protected ExecuteErrorHandler executeErrorHandler;

    /**
     * 输出请求体的实现
     */
    protected RequestBodyHandler requestBodyHandler;

    /**
     * 接收响应体的实现
     */
    protected ResponseBodyHandler responseBodyHandler;

    /**
     * SSLContext初始化
     */
    protected SSLContextInitializer sslContextInitializer;

    /**
     * 信任管理器（决定了信任哪些SSL证书）
     * <p>注：默认信任全部SSL证书</p>
     */
    protected List<TrustManager> trustManagers;

    //========================================== ↓↓↓↓↓↓ methods ↓↓↓↓↓↓ ==========================================

    public HttpRequesterFactory addTrustManager(TrustManager trustManager) {
        if (trustManagers == null) {
            trustManagers = new ArrayList<>();
        }
        trustManagers.add(trustManager);
        return this;
    }

}

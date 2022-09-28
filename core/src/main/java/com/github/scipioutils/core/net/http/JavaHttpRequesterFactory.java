package com.github.scipioutils.core.net.http;

import com.github.scipioutils.core.net.http.listener.ExecuteErrorHandler;
import com.github.scipioutils.core.net.http.listener.RequestBodyHandler;
import com.github.scipioutils.core.net.http.listener.ResponseBodyHandler;
import com.github.scipioutils.core.net.http.listener.SSLContextInitializer;

import javax.net.ssl.TrustManager;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.List;

/**
 * @author Alan Scipio
 * create date: 2022/9/27
 */
public class JavaHttpRequesterFactory extends HttpRequesterFactory {

    @Override
    public HttpRequester getRequester() {
        JavaHttpRequester requester = new JavaHttpRequester();
        if (proxy != null) {
            requester.setProxy(proxy);
        }
        if (executeErrorHandler != null) {
            requester.setExecuteErrorHandler(executeErrorHandler);
        }
        if (requestBodyHandler != null) {
            requester.setRequestBodyHandler(requestBodyHandler);
        }
        if (responseBodyHandler != null) {
            requester.setResponseBodyHandler(responseBodyHandler);
        }
        if (sslContextInitializer != null) {
            requester.setSslContextInitializer(sslContextInitializer);
        }
        if (trustManagers != null && trustManagers.size() > 0) {
            requester.setTrustManagers(trustManagers.toArray(new TrustManager[0]));
        }
        return requester;
    }

    //========================================== ↓↓↓↓↓↓ methods ↓↓↓↓↓↓ ==========================================

    @Override
    public JavaHttpRequesterFactory setProxy(Proxy proxy) {
        return (JavaHttpRequesterFactory) super.setProxy(proxy);
    }

    public JavaHttpRequesterFactory setProxy(String host, int port) {
        SocketAddress sa = new InetSocketAddress(host, port);
        return (JavaHttpRequesterFactory) super.setProxy(new Proxy(Proxy.Type.HTTP, sa));
    }

    @Override
    public JavaHttpRequesterFactory setExecuteErrorHandler(ExecuteErrorHandler executeErrorHandler) {
        return (JavaHttpRequesterFactory) super.setExecuteErrorHandler(executeErrorHandler);
    }

    @Override
    public JavaHttpRequesterFactory setRequestBodyHandler(RequestBodyHandler requestBodyHandler) {
        return (JavaHttpRequesterFactory) super.setRequestBodyHandler(requestBodyHandler);
    }

    @Override
    public JavaHttpRequesterFactory setResponseBodyHandler(ResponseBodyHandler responseBodyHandler) {
        return (JavaHttpRequesterFactory) super.setResponseBodyHandler(responseBodyHandler);
    }

    @Override
    public JavaHttpRequesterFactory setSslContextInitializer(SSLContextInitializer sslContextInitializer) {
        return (JavaHttpRequesterFactory) super.setSslContextInitializer(sslContextInitializer);
    }

    @Override
    public JavaHttpRequesterFactory setTrustManagers(List<TrustManager> trustManagers) {
        return (JavaHttpRequesterFactory) super.setTrustManagers(trustManagers);
    }

    @Override
    public JavaHttpRequesterFactory addTrustManager(TrustManager trustManager) {
        return (JavaHttpRequesterFactory) super.addTrustManager(trustManager);
    }

}

package com.github.scipioutils.core.net.http.listener;

import com.github.scipioutils.core.net.http.def.HttpMethod;

/**
 * 执行异常处理器
 *
 * @author Alan Scipio
 * @since 1.0.1-p1
 */
@FunctionalInterface
public interface ExecuteErrorHandler {

    //TODO 待实装

    /**
     * 执行期间抛异常时的处理（最外层的catch）
     *
     * @param urlPath    请求的url
     * @param httpMethod HTTP请求方法（GET或POST）
     * @param ex         抛出的异常
     */
    void handle(String urlPath, HttpMethod httpMethod, Exception ex);

}

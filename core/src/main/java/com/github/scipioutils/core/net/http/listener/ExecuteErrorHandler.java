package com.github.scipioutils.core.net.http.listener;

import com.github.scipioutils.core.net.http.HttpRequestException;
import com.github.scipioutils.core.net.http.def.Request;
import com.github.scipioutils.core.net.http.def.Response;

/**
 * 执行异常处理器
 *
 * @author Alan Scipio
 * @since 1.0.1-p1
 */
@FunctionalInterface
public interface ExecuteErrorHandler {

    /**
     * 执行期间抛异常时的处理（最外层的catch）
     *
     * @param request 各项请求参数
     * @param ex      抛出的异常
     */
    void handle(Request request, Throwable ex) throws HttpRequestException;

}

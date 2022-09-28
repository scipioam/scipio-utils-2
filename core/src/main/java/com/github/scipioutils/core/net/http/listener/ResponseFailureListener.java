package com.github.scipioutils.core.net.http.listener;

import com.github.scipioutils.core.net.http.def.Request;
import com.github.scipioutils.core.net.http.def.Response;

/**
 * 失败响应后的处理器
 *
 * @author Alan Scipio
 * @since 1.0.1-p1
 */
@FunctionalInterface
public interface ResponseFailureListener {

    /**
     * 失败响应后的处理（非2xx响应码）
     *
     * @param request  请求参数
     * @param response 响应结果
     */
    void handle(Response response, Request request);

}

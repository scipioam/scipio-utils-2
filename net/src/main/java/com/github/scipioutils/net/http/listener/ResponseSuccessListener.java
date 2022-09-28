package com.github.scipioutils.net.http.listener;

import com.github.scipioutils.net.http.def.Request;
import com.github.scipioutils.net.http.def.Response;

/**
 * 成功响应后的处理器
 *
 * @author Alan Scipio
 * @since 1.0.1-p1
 */
@FunctionalInterface
public interface ResponseSuccessListener {

    /**
     * 成功响应后的处理（2xx响应码）
     *
     * @param request  请求的参数
     * @param response 响应结果
     */
    void handle(Response response, Request request);

}

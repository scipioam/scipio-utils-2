package com.github.scipioutils.net.api;

import com.github.scipioutils.net.http.def.Request;
import com.github.scipioutils.net.http.def.Response;

/**
 * 自定义API请求后的返回结果
 *
 * @author Alan Scipio
 * create date: 2022/10/11
 */
public interface ApiResponseBuilder {

    <T extends ApiResponse> T build(Request request, Response origResponse, Class<T> responseType);

}

package com.github.scipioutils.net.api;

import java.io.InputStream;
import java.util.Map;

/**
 * API响应结果
 *
 * @author Alan Scipio
 * create date: 2022/9/28
 */
public interface ApiResponse {

    void setHttpResponseCode(int httpResponseCode);

    default void setResponseHeaders(Map<String, String> headers) {
    }

    default void setResponseStream(InputStream in) {
    }

}

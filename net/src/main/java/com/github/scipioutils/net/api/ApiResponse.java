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

    int getHttpResponseCode();

    void setHttpResponseCode(int httpResponseCode);

    default Map<String, String> getResponseHeaders() {
        return null;
    }

    default void setResponseHeaders(Map<String, String> headers) {
    }

    default void setResponseStream(InputStream in) {
    }

}

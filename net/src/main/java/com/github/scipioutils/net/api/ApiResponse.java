package com.github.scipioutils.net.api;

import java.io.InputStream;

/**
 * API响应结果
 *
 * @author Alan Scipio
 * create date: 2022/9/28
 */
public interface ApiResponse {

    int getHttpResponseCode();

    void setHttpResponseCode(int httpResponseCode);

    default void setResponseStream(InputStream in) {
    }

}

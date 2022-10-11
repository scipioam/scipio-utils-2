package com.github.scipioutils.net.api;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.LinkedHashMap;

/**
 * API响应结果 - 通用版
 *
 * @author Alan Scipio
 * create date: 2022/9/28
 */
public class ApiComResponse extends LinkedHashMap<String, Object> implements ApiResponse{

    @Getter @Setter
    private int httpResponseCode;

    @Getter
    private InputStream responseStream;

    @Override
    public void setResponseStream(InputStream in) {
        this.responseStream = in;
    }

}

package com.github.scipioutils.net.api;

import com.github.scipioutils.core.data.structure.IterateHandler;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * API响应结果 - 通用版
 *
 * @author Alan Scipio
 * create date: 2022/9/28
 */
public class ApiMapResponse extends LinkedHashMap<String, Object> implements ApiResponse {

    @Getter
    @Setter
    private int httpResponseCode;

    @Getter
    private InputStream responseStream;

    @Getter
    @Setter
    private Map<String, String> responseHeaders;

    /**
     * 原始数据（仅当返回的是json或xml时才有值）
     */
    @Getter
    @Setter
    private String origStrData;

    @Override
    public void setResponseStream(InputStream in) {
        this.responseStream = in;
    }

    /**
     * HTTP请求是否成功（响应码为2xx）
     *
     * @return true：成功
     */
    public boolean isHttpSuccess() {
        return httpResponseCode >= 200 && httpResponseCode < 300;
    }


    public void each(IterateHandler<String, Object> handler) {
        each(this, handler, 0);
    }

    @SuppressWarnings("unchecked")
    private void each(Map<String, Object> node, IterateHandler<String, Object> handler, int index) {
        for (Map.Entry<String, Object> entry : node.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            handler.handle(index, key, val);
            index++;
            if (val instanceof Map) {
                each((Map<String, Object>) val, handler, index);
            }
        }
    }

}

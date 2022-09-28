package com.github.scipioutils.net.http.listener;

import com.github.scipioutils.net.http.HttpFileUtils;
import com.github.scipioutils.net.http.def.HttpMethod;
import com.github.scipioutils.net.http.def.Request;
import com.github.scipioutils.net.http.def.RequestDataMode;

import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * 输出请求体的实现
 *
 * @author Alan Scipio
 * create date: 2022/9/26
 */
public interface RequestBodyHandler {

    /**
     * 输出请求体
     *
     * @param conn    连接对象
     * @param request 请求参数
     */
    void handleOutput(HttpURLConnection conn, Request request) throws Exception;

    /**
     * 默认实现
     */
    RequestBodyHandler DEFAULT = (conn, request) -> {
        if (request.isContentEmpty() || request.getHttpMethod() == HttpMethod.GET || request.getHttpMethod() == HttpMethod.HEAD) {
            //没有请求体
            return;
        }
        if (request.getRequestDataMode() == null) {
            request.setRequestDataMode(RequestDataMode.NONE);
        }
        try (OutputStream out = conn.getOutputStream()) {
            if (request.getRequestDataMode() == RequestDataMode.UPLOAD_FILE) {
                //上传文件
                HttpFileUtils.build().uploadFiles(
                        conn,
                        request.getFormData(),
                        request.getUploadFiles(),
                        request.getRequestCharset(),
                        request.getFileBufferSize(),
                        request.getUploadListener()
                );
            } else if (request.getRequestDataMode() != RequestDataMode.NONE) {
                //其他输入方式
                byte[] requestContent = request.getData();
                if (requestContent != null) {
                    //将数据输出
                    out.write(requestContent);
                }
            }
        }
    };

}

package com.github.scipioutils.net.http.listener;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.core.io.stream.InputStreamWrapper;
import com.github.scipioutils.core.io.stream.StreamParser;
import com.github.scipioutils.net.http.HttpFileUtils;
import com.github.scipioutils.net.http.def.Request;
import com.github.scipioutils.net.http.def.Response;
import com.github.scipioutils.net.http.def.ResponseDataMode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * 接收响应体的实现
 *
 * @author Alan Scipio
 * create date: 2022/9/26
 */
public interface ResponseBodyHandler {

    /**
     * 接收响应体数据
     *
     * @param responseSuccess 服务器响应为：成功（2xx的响应码）
     * @param conn            连接对象
     * @param response        响应数据
     * @param request         请求参数
     */
    void handleInput(boolean responseSuccess, HttpURLConnection conn, Response response, Request request) throws Exception;

    /**
     * 默认实现
     */
    ResponseBodyHandler DEFAULT = (responseSuccess, conn, response, request) -> {
        if (!response.guessBodyIsNotEmpty()) {
            //判断响应体为空，直接结束处理
            return;
        }
        response.setResponseDataMode(request.getResponseDataMode());
        //处理响应数据
        if (!responseSuccess || request.getResponseDataMode() == ResponseDataMode.DEFAULT) {
            //从input流读取字节数据
            try (InputStream in = conn.getInputStream()) {
                InputStreamWrapper wrapper = null;
                if (StringUtils.isNotBlank(response.getContentEncoding()) && response.getContentEncoding().equalsIgnoreCase("gzip")) {
                    //响应体为gzip压缩
                    wrapper = InputStreamWrapper.GZIP;
                }
                byte[] data = StreamParser.build().read(in, wrapper);
                response.setData(data);
            }
        } else if (request.getResponseDataMode() == ResponseDataMode.STREAM) {
            //直接返回input流
            InputStream in = null;
            try {
                in = conn.getInputStream();
                response.setDataStream(conn.getInputStream());
            } catch (IOException e) {
                if (in != null) {
                    in.close();
                }
                throw new IOException(e);
            }
        } else if (request.getResponseDataMode() == ResponseDataMode.DOWNLOAD) {
            //下载
            File downloadFile = HttpFileUtils.build().downloadFile(
                    request.getDownloadFilePath(),
                    conn.getInputStream(),
                    response.getContentLength(),
                    request.getFileBufferSize(),
                    request.isDownloadAutoSuffix(),
                    response.getContentType(),
                    request.getDownloadListener()
            );
            response.setDownloadFile(downloadFile);
        }
        //responseDataMode是NONE则无作为直接返回
    };

}

package com.github.scipioutils.net.http;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.net.http.def.*;
import com.github.scipioutils.net.http.listener.DownloadListener;

import java.io.File;

/**
 * HTTP client interface
 *
 * @author Alan Scipio
 * create date: 2022/9/22
 */
public interface HttpRequester {

    Response doRequest(Request request) throws HttpRequestException;

    /**
     * get请求
     */
    default Response get(Request request) throws HttpRequestException {
        request.setHttpMethod(HttpMethod.GET);
        return doRequest(request);
    }

    default Response get(String url) throws HttpRequestException {
        Request request = new Request()
                .setUrlPath(url);
        return get(request);
    }

    /**
     * post请求
     */
    default Response post(Request request) throws HttpRequestException {
        request.setHttpMethod(HttpMethod.POST);
        return doRequest(request);
    }

    default Response postJson(String url, String jsonData) throws HttpRequestException {
        if (StringUtils.isBlank(jsonData)) {
            throw new IllegalArgumentException("not offering json data for request body");
        }
        Request request = new Request()
                .setUrlPath(url)
                .setRequestDataMode(RequestDataMode.JSON)
                .setData(jsonData);
        return post(request);
    }

    /**
     * 上传文件
     */
    default Response upload(Request request, File... files) {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("not offering upload files");
        }
        request.setHttpMethod(HttpMethod.POST);
        request.setRequestDataMode(RequestDataMode.UPLOAD_FILE);
        for (File file : files) {
            request.addUploadFile(file);
        }
        return doRequest(request);
    }

    default Response upload(String url, File... files) {
        Request request = new Request()
                .setUrlPath(url);
        return upload(request, files);
    }

    /**
     * 下载文件
     */
    default Response download(String url, String dir, String fileName, DownloadListener downloadListener) {
        if (StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException("argument \"fileName\" is null");
        }
        String downloadFilePath = dir + File.separator + fileName;
        Request request = new Request()
                .setUrlPath(url)
                .setRequestDataMode(RequestDataMode.NONE)
                .setResponseDataMode(ResponseDataMode.DOWNLOAD)
                .setDownloadAutoSuffix(true)
                .setDownloadFilePath(downloadFilePath)
                .setDownloadListener(downloadListener);
        return doRequest(request);
    }

    default Response download(String url, String dir, String fileName) {
        return download(url, dir, fileName, null);
    }

}

package com.github.scipioutils.net.api;

import com.github.scipioutils.net.http.HttpRequestException;
import com.github.scipioutils.net.http.HttpRequester;
import com.github.scipioutils.net.http.HttpRequesterFactory;
import com.github.scipioutils.net.http.JavaHttpRequesterFactory;
import com.github.scipioutils.net.http.def.HttpMethod;
import com.github.scipioutils.net.http.def.Request;
import com.github.scipioutils.net.http.def.Response;
import com.github.scipioutils.net.http.def.ResponseDataMode;
import com.github.scipioutils.net.json.JacksonUtils;
import com.github.scipioutils.net.mime.ApplicationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * API请求客户端
 *
 * @author Alan Scipio
 * create date: 2022/9/28
 */
public class ApiClient {

    protected HttpRequesterFactory requesterFactory = new JavaHttpRequesterFactory();

    @Getter @Setter
    protected ApiResponseBuilder apiResponseBuilder;

    public <T extends ApiResponse> T doRequest(ApiRequest apiRequest, Class<T> responseType) throws HttpRequestException {
        if (apiRequest == null) {
            throw new IllegalArgumentException("apiRequest can not be null");
        }
        if (responseType == null) {
            apiRequest.setResponseDataMode(ResponseDataMode.NONE);
        }
        if (requesterFactory == null) {
            requesterFactory = new JavaHttpRequesterFactory();
        }
        try {
            HttpRequester requester = requesterFactory.getRequester();
            //构建最终的请求数据
            Request request = apiRequest.buildRequestData();
            //发起请求
            Response origResponse = requester.doRequest(request);
            //解析响应数据
            if (apiRequest.getResponseDataMode() == ResponseDataMode.DEFAULT) {
                if (apiResponseBuilder != null) {
                    //自定义返回结果
                    return apiResponseBuilder.build(request, origResponse, responseType);
                } else {
                    if (origResponse.getContentType().toLowerCase().contains(ApplicationType.JSON.getTemplate())) {
                        //解析返回的json数据
                        String strData = origResponse.getStrData();
                        T apiResponse = JacksonUtils.fromJson(strData, responseType);
                        apiResponse.setHttpResponseCode(origResponse.getResponseCode());
                        apiResponse.setResponseHeaders(origResponse.getHeaders());
                        if (apiResponse instanceof ApiMapResponse) {
                            ApiMapResponse acr = (ApiMapResponse) apiResponse;
                            acr.setOrigStrData(strData);
                        }
                        return apiResponse;
                    } else if (origResponse.getContentType().toLowerCase().contains(ApplicationType.XML.getTemplate())) {
                        //TODO 解析返回的xml数据
                        throw new IllegalStateException("xml data analysing is still developing...");
                    } else {
                        throw new HttpRequestException("unsupported response content type: [" + origResponse.getContentType() + "]");
                    }
                }
            } else if (apiRequest.getResponseDataMode() == ResponseDataMode.STREAM) {
                T apiResponse = Objects.requireNonNull(responseType).getDeclaredConstructor().newInstance();
                apiResponse.setHttpResponseCode(origResponse.getResponseCode());
                apiResponse.setResponseStream(origResponse.getDataStream());
                apiResponse.setResponseHeaders(origResponse.getHeaders());
                return apiResponse;
            } else {
                return null;
            }
        } catch (Exception e) {
            if (e instanceof HttpRequestException) {
                throw (HttpRequestException) e;
            } else {
                throw new HttpRequestException(e);
            }
        }
    }

    public ApiMapResponse doRequest(ApiRequest apiRequest) throws HttpRequestException {
        return doRequest(apiRequest, ApiMapResponse.class);
    }

    public <T extends ApiResponse> T get(ApiRequest apiRequest, Class<T> responseType) {
        apiRequest.setHttpMethod(HttpMethod.GET);
        return doRequest(apiRequest, responseType);
    }

    public ApiMapResponse get(ApiRequest apiRequest) throws HttpRequestException {
        return get(apiRequest, ApiMapResponse.class);
    }

    public <T extends ApiResponse> T post(ApiRequest apiRequest, Class<T> responseType) {
        apiRequest.setHttpMethod(HttpMethod.POST);
        return doRequest(apiRequest, responseType);
    }

    public ApiMapResponse post(ApiRequest apiRequest) throws HttpRequestException {
        return post(apiRequest, ApiMapResponse.class);
    }

    //TODO 上传文件的功能待开发

}

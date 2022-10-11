package com.github.scipioutils.net.api;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.net.http.def.HttpMethod;
import com.github.scipioutils.net.http.def.Request;
import com.github.scipioutils.net.http.def.RequestDataMode;
import com.github.scipioutils.net.http.def.ResponseDataMode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alan Scipio
 * create date: 2022/10/11
 */
@Data
@Accessors(chain = true)
public class ApiRequest {

    private HttpMethod httpMethod = HttpMethod.GET;

    private String urlPath;

    private RequestDataMode requestDataMode;

    private ResponseDataMode responseDataMode = ResponseDataMode.DEFAULT;

    /**
     * 安全验证的原始信息，例如APPCODE，或SecretId、SecretKey之类的
     */
    private Map<String, String> authorizationInfos;

    /**
     * API请求的安全验证的签名计算器
     */
    private Signature signature;

    @Setter(AccessLevel.NONE)
    private String strData;

    @Setter(AccessLevel.NONE)
    private Map<String, String> formData;

    /**
     * 构造最终的请求数据
     */
    Request buildRequestData() throws Exception {
        Request request = new Request();
        if (StringUtils.isBlank(urlPath)) {
            throw new IllegalArgumentException("urlPath can not be blank");
        }
        if (requestDataMode == null) {
            requestDataMode = RequestDataMode.NONE;
        }
        if (httpMethod == null) {
            httpMethod = HttpMethod.GET;
        }
        request.setUrlPath(urlPath)
                .setHttpMethod(httpMethod)
                .setRequestDataMode(requestDataMode)
                .setResponseDataMode(responseDataMode);
        //请求数据
        if (requestDataMode == RequestDataMode.FORM) {
            request.setData(formData);
        } else if (requestDataMode == RequestDataMode.JSON || requestDataMode == RequestDataMode.XML) {
            request.setData(strData);
        }
        //安全验证
        if (signature != null) {
            String authorizationValue = signature.calc(this, authorizationInfos, request);
            String authorizationKey = signature.getAuthorizationKey();
            request.addHeader(authorizationKey, authorizationValue);
        }
        return request;
    }

    public ApiRequest setJsonData(String jsonData) {
        requestDataMode = RequestDataMode.JSON;
        this.strData = jsonData;
        return this;
    }

    public ApiRequest setXmlData(String xmlData) {
        requestDataMode = RequestDataMode.XML;
        this.strData = xmlData;
        return this;
    }

    public ApiRequest setFormData(Map<String, String> formData) {
        requestDataMode = RequestDataMode.FORM;
        this.formData = formData;
        return this;
    }

    public ApiRequest addAuthorizationInfo(String infoKey, String info) {
        if (authorizationInfos == null) {
            authorizationInfos = new HashMap<>();
        }
        authorizationInfos.put(infoKey, info);
        return this;
    }

    public String getAuthorizationInfo(String infoKey) {
        return authorizationInfos == null ? null : authorizationInfos.get(infoKey);
    }

    public void clearAuthorizationInfos() {
        if (authorizationInfos != null) {
            authorizationInfos.clear();
        }
    }

}

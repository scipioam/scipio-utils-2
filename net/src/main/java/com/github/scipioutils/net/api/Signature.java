package com.github.scipioutils.net.api;

import com.github.scipioutils.net.http.def.Request;

import java.util.Map;

/**
 * API请求的安全验证的签名计算器
 *
 * @author Alan Scipio
 * create date: 2022/10/11
 */
public interface Signature {

    /**
     * 计算签名
     *
     * @param apiRequest         请求参数
     * @param authorizationInfos 原始的安全信息，据此进行签名计算
     * @param request            最终请求参数
     * @return 最终提交的鉴权签名
     */
    String calc(ApiRequest apiRequest, Map<String, String> authorizationInfos, Request request) throws Exception;

    default String getAuthorizationKey() {
        return "Authorization";
    }

}

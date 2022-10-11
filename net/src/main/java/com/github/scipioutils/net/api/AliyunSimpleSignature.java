package com.github.scipioutils.net.api;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.net.http.def.Request;

import java.util.Map;

/**
 * 阿里云简单安全验证（APPCODE）
 *
 * @author Alan Scipio
 * create date: 2022/10/11
 * @see <a href="https://help.aliyun.com/document_detail/157953.html?spm=5176.product-detail.detail.2.71421dbdhesA5h">Aliyun APPCODE</a>
 */
public class AliyunSimpleSignature extends AliyunSignature{

    @Override
    public String calc(ApiRequest apiRequest, Map<String, String> authorizationInfos, Request request) {
        if (authorizationInfos == null || authorizationInfos.size() == 0) {
            throw new IllegalArgumentException("aliyun simple signature (APPCODE) is null");
        }
        String appCode = authorizationInfos.get(APPCODE);
        if (StringUtils.isBlank(appCode)) {
            throw new IllegalArgumentException("aliyun simple signature (APPCODE) is blank");
        }
        return "APPCODE " + appCode;
    }

}

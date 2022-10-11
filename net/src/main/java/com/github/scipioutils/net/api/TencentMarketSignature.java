package com.github.scipioutils.net.api;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.net.http.def.Request;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * 腾讯云安全验证（secretId+secretKey，简单版）
 *
 * @author Alan Scipio
 * create date: 2022/10/11
 * @see <a href="https://cloud.tencent.com/document/product/306/57450">Signture ver.1</a>
 */
public class TencentMarketSignature extends TencentSignature {

    //TODO 待测试

    @Override
    public String calc(ApiRequest apiRequest, Map<String, String> authorizationInfos, Request request) throws Exception {
        if (authorizationInfos == null || authorizationInfos.size() == 0) {
            throw new IllegalArgumentException("aliyun simple signature (APPCODE) is null");
        }
        String secretKey = authorizationInfos.get(SECRET_KEY);
        if (StringUtils.isBlank(secretKey)) {
            throw new IllegalArgumentException("tencent market signature (secretKey) is blank");
        }
        String secretId = authorizationInfos.get(SECRET_KEY);
        if (StringUtils.isBlank(secretId)) {
            throw new IllegalArgumentException("tencent market signature (secretId) is blank");
        }

        //生成签名
        String datetime = getDateTime();
        String content = getStringToSign(datetime);
        String signature = sign(content, secretKey, "HmacSHA1");
        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + signature + "\"";

        //补充header字段
        request.addHeader("X-Source", SOURCE_MARKET);
        request.addHeader("X-date", datetime);

        return auth;
    }

    private String getStringToSign(String datetime) {
        return "x-date: " + datetime + "\n" + "x-source: " + SOURCE_MARKET;
    }

    private String getDateTime() {
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(cd.getTime());
    }

}

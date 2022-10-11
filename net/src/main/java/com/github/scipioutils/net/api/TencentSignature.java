package com.github.scipioutils.net.api;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;

/**
 * @author Alan Scipio
 * create date: 2022/10/11
 */
public abstract class TencentSignature implements Signature {

    public static final String SECRET_ID = "secretId";
    public static final String SECRET_KEY = "secretKey";

    /**
     * 腾讯云API市场
     */
    public static final String SOURCE_MARKET = "market";

    /**
     * 签名的生成
     *
     * @param content   明文
     * @param secretKey 腾讯云给出的secretKey
     * @param method    采用的算法
     * @return 签名成品(未经URL转码)
     */
    protected String sign(String content, String secretKey, String method) throws Exception {
        Mac mac = Mac.getInstance(method);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printBase64Binary(hash);
    }

}

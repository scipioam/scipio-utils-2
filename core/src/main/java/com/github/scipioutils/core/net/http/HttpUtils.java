package com.github.scipioutils.core.net.http;

import com.github.scipioutils.core.AssertUtils;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alan Scipio
 * create date: 2022/9/22
 */
public class HttpUtils {

    /**
     * 检测连接协议，返回true代表是https协议
     */
    public static boolean isHttpsProtocol(URL url) throws IllegalArgumentException {
        AssertUtils.notNull(url);
        String protocol = url.getProtocol().toLowerCase();
        if (protocol.equals("https")) {
            return true;
        } else if (protocol.equals("http")) {
            return false;
        } else {
            throw new IllegalArgumentException("Url protocol is not http or https");
        }
    }

    /**
     * 准备好带参的url地址
     *
     * @param address url地址
     * @param params  参数
     * @return 拼接好的地址字符串
     */
    public static String getUrlWithParams(String address, Map<String, String> params, String charset) throws UnsupportedEncodingException {
        if (params == null) {
            return address;
        }
        return address + "?" + getParams(params, charset);
    }

    public static String getParams(Map<String, String> params, String charset) throws UnsupportedEncodingException {
        StringBuilder temp = new StringBuilder();
        for (Map.Entry<String, String> p : params.entrySet()) {
            temp.append(URLEncoder.encode(p.getKey(), charset));
            temp.append("=");
            temp.append(URLEncoder.encode(p.getValue(), charset));
            temp.append("&");
        }
        return temp.substring(0, (temp.length() - 1));
    }

    /**
     * {@link java.net.HttpURLConnection}里获取的请求头/响应头的转换
     */
    public static Map<String, String> transformURLConnHeaders(Map<String, List<String>> originalHeaders) {
        Map<String, String> finalHeaders = new HashMap<>();
        for (Map.Entry<String, List<String>> header : originalHeaders.entrySet()) {
            StringBuilder value = new StringBuilder();
            List<String> headerValueList = header.getValue();
            if (headerValueList != null && headerValueList.size() > 0) {
                for (String f : headerValueList) {
                    value.append(f).append(",");
                }
                value.deleteCharAt(value.length() - 1);
            }
            finalHeaders.put(header.getKey(), value.toString());
        }
        return finalHeaders;
    }

}

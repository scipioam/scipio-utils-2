package com.github.scipioutils.net.http.def;

/**
 * HTTP 请求方法
 *
 * @author Alan Scipio
 * @since 1.0.0
 */
public enum HttpMethod {

    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    CONNECT("CONNECT"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    PATCH("PATCH")
    ;

    public final String value;

    HttpMethod(String value) {
        this.value = value;
    }

}

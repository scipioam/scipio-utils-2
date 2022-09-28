package com.github.scipioutils.net.http;

import com.github.scipioutils.core.exception.ScipioUtilException;

/**
 * @author Alan Scipio
 * create date: 2022/9/26
 */
public class HttpRequestException extends ScipioUtilException {
    public HttpRequestException(String message) {
        super(message);
    }

    public HttpRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestException(Throwable cause) {
        super(cause);
    }

    public HttpRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.github.scipioutils.core.exception;

/**
 * @author Alan Scipio
 * create date: 2022/9/26
 */
public class ScipioUtilException extends RuntimeException{

    public ScipioUtilException() {
    }

    public ScipioUtilException(String message) {
        super(message);
    }

    public ScipioUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScipioUtilException(Throwable cause) {
        super(cause);
    }

    public ScipioUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

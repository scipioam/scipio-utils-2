package com.github.scipioutils.core.exception;

/**
 * @since 2022/9/21
 */
public class TestException extends ScipioUtilException{

    public TestException(String message) {
        super(message);
    }

    public TestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

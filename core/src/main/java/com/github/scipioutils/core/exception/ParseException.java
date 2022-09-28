package com.github.scipioutils.core.exception;

/**
 * @author Alan Scipio
 * create date: 2022/9/28
 */
public class ParseException extends ScipioUtilException{
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    public ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

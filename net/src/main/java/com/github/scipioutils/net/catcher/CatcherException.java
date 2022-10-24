package com.github.scipioutils.net.catcher;

import com.github.scipioutils.core.exception.ScipioUtilException;

/**
 * @author Alan Scipio
 * create date: 2022/10/24
 */
public class CatcherException extends ScipioUtilException {
    public CatcherException(String message) {
        super(message);
    }

    public CatcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public CatcherException(Throwable cause) {
        super(cause);
    }
}

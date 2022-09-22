package com.github.scipioutils.core.data.cache;

/**
 * @author Alan Scipio
 * @since 1.0.2
 */
public class CacheNotFoundException extends RuntimeException {
    public CacheNotFoundException() {
    }

    public CacheNotFoundException(String message) {
        super(message);
    }

    public CacheNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheNotFoundException(Throwable cause) {
        super(cause);
    }
}

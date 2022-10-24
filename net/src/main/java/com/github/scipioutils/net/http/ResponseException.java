package com.github.scipioutils.net.http;

import com.github.scipioutils.core.exception.ScipioUtilException;
import com.github.scipioutils.net.http.def.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 响应异常
 *
 * @author Alan Scipio
 * create date: 2022/10/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseException extends ScipioUtilException {

    /**
     * HTTP响应码
     */
    private int responseCode;

    private Response content;

    public ResponseException(String message, Response content) {
        super(message);
        setContent(content);
    }

    public ResponseException(String message, Throwable cause, Response content) {
        super(message, cause);
        this.content = content;
    }

    public ResponseException(Throwable cause, Response content) {
        super(cause);
        this.content = content;
    }

}

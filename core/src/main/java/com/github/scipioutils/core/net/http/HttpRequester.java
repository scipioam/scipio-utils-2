package com.github.scipioutils.core.net.http;

import com.github.scipioutils.core.net.http.def.Request;
import com.github.scipioutils.core.net.http.def.Response;

import java.io.IOException;

/**
 * HTTP client interface
 *
 * @author Alan Scipio
 * create date: 2022/9/22
 */
public interface HttpRequester {

    Response doRequest(Request request) throws Exception;

}

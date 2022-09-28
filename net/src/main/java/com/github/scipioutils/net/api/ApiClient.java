package com.github.scipioutils.net.api;

import com.github.scipioutils.net.http.HttpRequester;
import com.github.scipioutils.net.http.HttpRequesterFactory;
import com.github.scipioutils.net.http.JavaHttpRequesterFactory;
import com.github.scipioutils.net.http.def.RequestDataMode;
import com.github.scipioutils.net.http.def.ResponseDataMode;

/**
 * @author Alan Scipio
 * create date: 2022/9/28
 */
public class ApiClient {

    protected HttpRequesterFactory requesterFactory = new JavaHttpRequesterFactory();

    protected RequestDataMode requestDataMode = RequestDataMode.FORM;

    protected ResponseDataMode responseDataMode = ResponseDataMode.DEFAULT;

    protected HttpRequester requester;



}

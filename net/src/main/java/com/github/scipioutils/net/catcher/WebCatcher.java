package com.github.scipioutils.net.catcher;

import com.github.scipioutils.net.http.*;
import com.github.scipioutils.net.http.def.HttpMethod;
import com.github.scipioutils.net.http.def.Request;
import com.github.scipioutils.net.http.def.Response;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;

/**
 * 通用便捷小爬虫
 *
 * @author Alan Scipio
 * create date: 2022/10/12
 */
@Data
@Accessors(chain = true)
public class WebCatcher {

    protected HttpMethod httpMethod = HttpMethod.GET;

    protected HttpRequesterFactory requesterFactory = new JavaHttpRequesterFactory();

    protected CatchHandler catchHandler;

    protected IOHandler ioHandler;

    //========================================== ↓↓↓↓↓↓ Catcher APIs ↓↓↓↓↓↓ ==========================================

    /**
     * 快速抓取
     *
     * @param request 请求参数
     * @return 抓取结果
     */
    public WebInfo quickCatch(Request request) {
        try {
            request.setHttpMethod(httpMethod);
            URL url = request.getFinalUrl();
            WebInfo webInfo = new WebInfo(url);
            //发起HTTP请求
            Response response = doRequest(request);
            webInfo.setResponseCode(response.getResponseCode());
            String responseBody = response.getStrData();
            webInfo.setOriginalResponse(responseBody);
            //解析HTML
            Document document = Jsoup.parse(responseBody);
            //执行抓取逻辑
            if (catchHandler != null) {
                catchHandler.handle(webInfo, document);
            }
            //执行IO逻辑(保存数据)
            if (ioHandler != null) {
                ioHandler.handle(webInfo, document);
            }
            return webInfo;
        } catch (Exception e) {
            throw new CatcherException(e);
        }
    }

    /**
     * 快速抓取
     *
     * @param url 请求的url（GET请求）
     * @return 抓取结果
     */
    public WebInfo quickCatch(String url) {
        Request request = new Request()
                .setUrlPath(url);
        return quickCatch(request);
    }

    //========================================== ↓↓↓↓↓↓ 内部方法 ↓↓↓↓↓↓ ==========================================

    private Response doRequest(Request request) {
        //准备HTTP请求工具（客户端工具）
        HttpRequester httpRequester = requesterFactory.getRequester();
//        System.out.println("httpRequester has been built: " + httpRequester);
        //发起HTTP请求
        Response response;
        System.out.println("start send http request to [" + request.getUrl().getPath() + "] by " + request.getHttpMethod().value + " method");
        response = (httpMethod == HttpMethod.GET) ? httpRequester.get(request) : httpRequester.post(request);
        if (!response.isSuccess()) {
            throw new ResponseException("response error code:" + response.getResponseCode(), response);
        }
        System.out.println("request successfully, http response code:" + response.getResponseCode());
        return response;
    }

}

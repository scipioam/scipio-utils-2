package test.net;

import com.github.scipioutils.net.http.HttpRequester;
import com.github.scipioutils.net.http.HttpRequesterFactory;
import com.github.scipioutils.net.http.JavaHttpRequesterFactory;
import com.github.scipioutils.net.http.def.Request;
import com.github.scipioutils.net.http.def.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author Alan Scipio
 * create date: 2022/9/22
 */
public class HttpTest {

    private HttpRequester requester;
    private Request request;
    private Response response;

    @BeforeEach
    public void beforeEach() {
        response = null;
        //请求对象
        HttpRequesterFactory requesterFactory = new JavaHttpRequesterFactory();
        requester = requesterFactory.getRequester();
        //请求参数
        request = new Request();
    }

    @AfterEach
    public void afterEach() {
        if (response == null) {
            return;
        }
        System.out.println("最终请求URL: [" + request.getUrl() + "]");
        //响应码
        System.out.println("响应码：" + response.getResponseCode());
        if (response.isSuccess()) {
            System.out.println("请求成功");
        } else {
            System.out.println("请求失败");
        }
        //响应头
        System.out.println("------------------------------------------------------");
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
            System.out.println("[" + header.getKey() + "] : " + header.getValue());
        }
        System.out.println("------------------------------------------------------");
        //响应体
        if (response.guessBodyIsNotEmpty()) {
            System.out.println("响应体：\n" + response.getStrData());
        } else {
            System.out.println("响应体为空");
        }
    }

    @Test
    public void testSimpleReq() {
        String url = "https://blog.csdn.net/fengdijiang/article/details/101348901";
        request.setUrlPath(url)
                .setDefaultUserAgent();
        //发起请求
        response = requester.get(request);
    }

    @Test
    public void testApi() {
        String url = "https://weather01.market.alicloudapi.com/weatherhistory";
        request.setUrlPath(url)
                .addHeader("Authorization", "APPCODE xxx")
                .addFormData("areaCode", "320100") //南京
                .addFormData("month", "202208");
        //发起请求
        response = requester.get(request);
    }

}

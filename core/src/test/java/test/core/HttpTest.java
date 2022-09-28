package test.core;

import com.github.scipioutils.core.net.http.HttpRequester;
import com.github.scipioutils.core.net.http.HttpRequesterFactory;
import com.github.scipioutils.core.net.http.JavaHttpRequesterFactory;
import com.github.scipioutils.core.net.http.def.Request;
import com.github.scipioutils.core.net.http.def.Response;
import com.github.scipioutils.core.net.mime.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alan Scipio
 * create date: 2022/9/22
 */
public class HttpTest {

    @Test
    public void test0() {
        Map<String, String> contentType = new HashMap<>();

        int foundCount = 0;
        for (VideoType type : VideoType.values()) {
            boolean notFound = true;
            for (Map.Entry<String, String> entry : contentType.entrySet()) {
                String ext = entry.getKey();
                String ct = entry.getValue();
                if (ct.equals(type.getTemplate())) {
                    notFound = false;
                    foundCount++;
                    System.out.println(type.name() + "(\"" + type.getTemplate() + "\", \"" + ext + "\"),");
                    break;
                }
            }
            if (notFound) {
                System.out.println(type.name() + "(\"" + type.getTemplate() + "\", \"\"),");
            }
        }
        System.out.println("\n\ntotal found: " + foundCount);
    }

    @Test
    public void testSimpleReq() {
        String url = "https://blog.csdn.net/fengdijiang/article/details/101348901";
        Request request = new Request()
                .setUrlPath(url)
                .setDefaultUserAgent();

        HttpRequesterFactory requesterFactory = new JavaHttpRequesterFactory();
        HttpRequester requester = requesterFactory.getRequester();
        try {
            //发起请求
            Response response = requester.get(request);
            System.out.println("响应码："+response.getResponseCode());
            if (response.getResponseCode() > 0) {
                System.out.println("响应体：\n" + response.getStrData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

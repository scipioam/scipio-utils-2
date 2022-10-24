package test.net;

import com.github.scipioutils.net.catcher.CatchHandler;
import com.github.scipioutils.net.catcher.FileIOHandler;
import com.github.scipioutils.net.catcher.WebCatcher;
import com.github.scipioutils.net.catcher.WebInfo;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Scipio
 * create date: 2022/10/24
 */
public class CatcherTest {

    @Test
    public void test0() {
        String url = "https://blog.csdn.net/nibonnn/article/details/105331405";

        WebCatcher catcher = new WebCatcher()
                .setCatchHandler(CATCH_IMPL_0)
                .setIoHandler(new FileIOHandler("D:\\temp", "test"));
        WebInfo webInfo = catcher.quickCatch(url);
        System.out.println("\n\nURL: " + webInfo.getUrl());
        System.out.println("web title: " + webInfo.getWebTitle());
        System.out.println("response code: " + webInfo.getResponseCode());
    }

    /**
     * 抓取实现0：
     * @see <a href="">目标网址</a>
     */
    private final CatchHandler CATCH_IMPL_0 = (webInfo, document) -> {
        //抓取文本容器
        Elements tables = document.getElementsByTag("table");
        System.out.println("tables.size: " + tables.size());

        //抓取主内容
        List<String> contentList = new ArrayList<>();
        contentList.add(tables.get(5).toString());
//        for (Element table : tables) {
//            Element tbody = table.getElementsByTag("tbody").first();
//            assert tbody != null : "tbody is null";
//            Elements trList = tbody.getElementsByTag("tr");
//            for (Element tr : trList) {
//                System.out.println(tr);
//                contentList.add(tr.toString());
//            }
//            contentList.add("---------------------------------------------\n\n");
//        }

        //保存结果
        webInfo.setResultStr(contentList);
    };

}

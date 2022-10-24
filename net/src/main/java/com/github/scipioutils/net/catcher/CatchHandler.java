package com.github.scipioutils.net.catcher;

import org.jsoup.nodes.Document;

/**
 * 抓取的具体实现
 *
 * @author Alan Scipio
 * create date: 2022/10/12
 */
@FunctionalInterface
public interface CatchHandler {

    /**
     * 抓取的回调
     *
     * @param webInfo  目标网页信息
     * @param document 解析过的html文档对象
     */
    void handle(WebInfo webInfo, Document document);

}

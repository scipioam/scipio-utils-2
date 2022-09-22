package com.github.scipioutils.core.net.mime;

/**
 * MIME类型
 *
 * @author Alan Scipio
 * @since 1.0.1-p1
 */
public interface MimeType {

    /**
     * 获取Mime类型全名
     */
    String getTemplate();

    /**
     * TODO 获取MIME对应的文件扩展名 (计划用爬虫抓取对照表并处理)
     */
    default String getFileExtension() {
        return null;
    }

    /**
     * 获取Mime类型主类型
     */
    MimeMainType getMimeMainType();

    /**
     * 该类型是否被包含
     *
     * @param mimeTypeStr 要查询的媒体类型String
     * @return 如果mimeTypeStr包含该媒体类型返回true, 或者返回false
     */
    boolean contains(String mimeTypeStr);

    String getContentType(String charSet);

    default String getContentType() {
        return getContentType("utf-8");
    }

}

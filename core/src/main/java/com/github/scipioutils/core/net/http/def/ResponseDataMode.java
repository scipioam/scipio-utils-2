package com.github.scipioutils.core.net.http.def;

/**
 * 接收数据方式
 *
 * @author Alan Scipio
 * Create Date: 2020/9/24
 */
public enum ResponseDataMode {

    /**
     * 默认按
     */
    DEFAULT,

    /**
     * 不需要响应体数据
     */
    NONE,

    /**
     * 下载文件
     */
    DOWNLOAD,

    /**
     * 直接返回InputStream对象
     */
    STREAM_ONLY

}

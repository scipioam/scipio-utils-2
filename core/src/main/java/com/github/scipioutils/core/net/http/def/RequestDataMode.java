package com.github.scipioutils.core.net.http.def;

import com.github.scipioutils.core.net.mime.ApplicationType;
import com.github.scipioutils.core.net.mime.MultipartType;
import com.github.scipioutils.core.net.mime.TextType;

/**
 * 请求数据的模式
 *
 * @author Alan Scipio
 * @since 1.0.0
 */
public enum RequestDataMode {

    /**
     * 没有要提交的数据
     */
    NONE(null),

    /**
     * form表单提交方式(比如x=1&y=2...)
     */
    FORM(ApplicationType.X_WWW_FORM_URLENCODED.getTemplate()),

    /**
     * 提交JSON数据
     */
    JSON(ApplicationType.JSON.getTemplate()),

    /**
     * 提交XML数据
     */
    XML(TextType.XML.getTemplate()),

    /**
     * 直接提交字符串
     */
    TEXT_PLAIN(TextType.PLAIN.getTemplate()),

    /**
     * 上传文件
     */
    UPLOAD_FILE(MultipartType.FORM_DATA.getTemplate()),
    ;

    public final String contentType;

    RequestDataMode(String contentType) {
        this.contentType = contentType;
    }

    public static RequestDataMode getDefaultMode() {
        return FORM;
    }

}

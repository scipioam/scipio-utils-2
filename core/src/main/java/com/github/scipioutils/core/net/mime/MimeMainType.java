package com.github.scipioutils.core.net.mime;

/**
 * @since 2022/9/21
 */
public enum MimeMainType {

    APPLICATION("application"),
    AUDIO("audio"),
    FONT("font"),
    IMAGE("image"),
    MESSAGE("message"),
    MODEL("model"),
    MULTIPART("multipart"),
    TEXT("text"),
    OTHER("other"),
    VIDEO("video");

    public final String template;

    MimeMainType(String template) {
        this.template = template;
    }


}

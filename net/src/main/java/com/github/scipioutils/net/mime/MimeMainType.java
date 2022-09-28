package com.github.scipioutils.net.mime;

import java.util.*;

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

    /**
     * 获取所有MIME类型
     */
    public static List<MimeType> getAllMimeTypes() {
        List<MimeType> mimeTypes = new ArrayList<>();
        mimeTypes.addAll(Arrays.asList(ApplicationType.values()));
        mimeTypes.addAll(Arrays.asList(AudioType.values()));
        mimeTypes.addAll(Arrays.asList(FontType.values()));
        mimeTypes.addAll(Arrays.asList(ImageType.values()));
        mimeTypes.addAll(Arrays.asList(MessageType.values()));
        mimeTypes.addAll(Arrays.asList(ModelType.values()));
        mimeTypes.addAll(Arrays.asList(MultipartType.values()));
        mimeTypes.addAll(Arrays.asList(TextType.values()));
        mimeTypes.addAll(Arrays.asList(VideoType.values()));
        return mimeTypes;
    }

}

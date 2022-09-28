package com.github.scipioutils.net.mime;

import com.github.scipioutils.core.StringUtils;

import java.util.List;

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
     * 获取MIME对应的文件扩展名
     */
    String getFileExtension();

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

    /**
     * 根据文件扩展名获取对应的ContentType
     */
    static String getContentTypeByFileExtension(String fileExtension) {
        if (StringUtils.isBlank(fileExtension)) {
            throw new IllegalArgumentException("fileExtension can not be blank");
        }
        if (!fileExtension.startsWith("\\.")) {
            fileExtension = ("." + fileExtension);
        }
        List<MimeType> mimeTypes = MimeMainType.getAllMimeTypes();
        for (MimeType mimeType : mimeTypes) {
            if (fileExtension.equals(mimeType.getFileExtension())) {
                return mimeType.getContentType();
            }
        }
        return "application/octet-stream";
    }

    /**
     * 根据ContentType获取对应的文件扩展名
     */
    static String getFileExtensionByContentType(String contentType) {
        if (StringUtils.isBlank(contentType)) {
            throw new IllegalArgumentException("contentType can not be blank");
        }
        List<MimeType> mimeTypes = MimeMainType.getAllMimeTypes();
        for (MimeType mimeType : mimeTypes) {
            if (contentType.equals(mimeType.getContentType())) {
                return mimeType.getFileExtension();
            }
        }
        return null;
    }

}

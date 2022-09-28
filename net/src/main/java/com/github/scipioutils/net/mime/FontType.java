package com.github.scipioutils.net.mime;

import com.github.scipioutils.core.AssertUtils;

/**
 * @since 2022/9/21
 */
public enum FontType implements MimeType{

    COLLECTION("font/collection",  ""),
    OTF("font/otf",  ""),
    SFNT("font/sfnt",  ""),
    TTF("font/ttf",  ""),
    WOFF("font/woff",  ""),
    WOFF2("font/woff2",  "");

    private final String template;
    private final String fileExtension;

    FontType(String template, String fileExtension) {
        this.template = template;
        this.fileExtension = fileExtension;
    }

    @Override
    public String getTemplate() {
        return this.template;
    }

    @Override
    public String getFileExtension() {
        return fileExtension;
    }

    @Override
    public MimeMainType getMimeMainType() {
        return MimeMainType.FONT;
    }

    @Override
    public boolean contains(String mimeTypeStr) {
        AssertUtils.notNull(mimeTypeStr);
        return mimeTypeStr.contains(this.template);
    }

    @Override
    public String getContentType(String charSet) {
        return this.template + ";charset=" + charSet;
    }

}

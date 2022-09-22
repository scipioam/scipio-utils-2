package com.github.scipioutils.core.net.mime;

import com.github.scipioutils.core.AssertUtils;

/**
 * @since 2022/9/21
 */
public enum FontType implements MimeType{

    COLLECTION("font/collection"),
    OTF("font/otf"),
    SFNT("font/sfnt"),
    TTF("font/ttf"),
    WOFF("font/woff"),
    WOFF2("font/woff2");

    private final String template;

    FontType(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return this.template;
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

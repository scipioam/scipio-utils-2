package com.github.scipioutils.core.net.mime;

import com.github.scipioutils.core.AssertUtils;

/**
 * @since 2022/9/21
 */
public enum MultipartType implements MimeType{

    ALTERNATIVE("",  ""),
    APPLEDOUBLE("multipart/appledouble",  ""),
    BYTERANGES("multipart/byteranges",  ""),
    DIGEST("",  ""),
    ENCRYPTED("multipart/encrypted",  ""),
    EXAMPLE("multipart/example",  ""),
    FORM_DATA("multipart/form-data",  ""),
    HEADER_SET("multipart/header-set",  ""),
    MIXED("",  ""),
    MULTILINGUAL("multipart/multilingual",  ""),
    PARALLEL("",  ""),
    RELATED("multipart/related",  ""),
    REPORT("multipart/report",  ""),
    SIGNED("multipart/signed",  ""),
    VND$BINT$MED_PLUS("multipart/vnd.bint.med-plus",  ""),
    VOICE_MESSAGE("multipart/voice-message",  ""),
    X_MIXED_REPLACE("multipart/x-mixed-replace",  "");

    private final String template;
    private final String fileExtension;

    MultipartType(String template, String fileExtension) {
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

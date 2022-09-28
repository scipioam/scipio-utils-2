package com.github.scipioutils.core.net.mime;

import com.github.scipioutils.core.AssertUtils;

/**
 * @since 2022/9/21
 */
public enum MessageType implements MimeType{

    CPIM("message/CPIM",  ""),
    DELIVERY_STATUS("message/delivery-status",  ""),
    DISPOSITION_NOTIFICATION("message/disposition-notification",  ""),
    EXAMPLE("message/example",  ""),
    EXTERNAL_BODY("",  ""),
    FEEDBACK_REPORT("message/feedback-report",  ""),
    GLOBAL("message/global",  ""),
    GLOBAL_DELIVERY_STATUS("message/global-delivery-status",  ""),
    GLOBAL_DISPOSITION_NOTIFICATION("message/global-disposition-notification",  ""),
    GLOBAL_HEADERS("message/global-headers",  ""),
    HTTP("message/http",  ""),
    IMDN_WITH_XML("message/imdn+xml",  ""),
    NEWS("message/news",  ""),
    PARTIAL("",  ""),
    RFC822("",  ""),
    S_HTTP("message/s-http",  ""),
    SIP("message/sip",  ""),
    SIPFRAG("message/sipfrag",  ""),
    TRACKING_STATUS("message/tracking-status",  ""),
    VND$SI$SIMP("message/vnd.si.simp",  ""),
    VND$WFA$WSC("message/vnd.wfa.wsc",  "");

    private final String template;
    private final String fileExtension;

    MessageType(String template, String fileExtension) {
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

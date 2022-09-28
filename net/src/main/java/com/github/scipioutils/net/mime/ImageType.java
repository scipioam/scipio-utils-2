package com.github.scipioutils.net.mime;

import com.github.scipioutils.core.AssertUtils;

/**
 * @since 2022/9/21
 */
public enum ImageType implements MimeType {

    ACES("image/aces", ""),
    AVCI("image/avci", ""),
    AVCS("image/avcs", ""),
    AVIF("image/avif", ""),
    BMP("image/bmp", ".bmp"),
    CGM("image/cgm", ".cgm"),
    DICOM_RLE("image/dicom-rle", ""),
    EMF("image/emf", ""),
    EXAMPLE("image/example", ""),
    FITS("image/fits", ".fits"),
    G3FAX("image/g3fax", ""),
    GIF("", ""),
    HEIC("image/heic", ""),
    HEIC_SEQUENCE("image/heic-sequence", ""),
    HEIF("image/heif", ""),
    HEIF_SEQUENCE("image/heif-sequence", ""),
    HEJ2K("image/hej2k", ""),
    HSJ2("image/hsj2", ""),
    IEF("", ""),
    JLS("image/jls", ""),
    JP2("image/jp2", ".jp2"),
    JPEG("", ""),
    JPH("image/jph", ""),
    JPHC("image/jphc", ""),
    JPM("image/jpm", ""),
    JPX("image/jpx", ""),
    JXR("image/jxr", ""),
    JXRA("image/jxrA", ""),
    JXRS("image/jxrS", ""),
    JXS("image/jxs", ""),
    JXSC("image/jxsc", ""),
    JXSI("image/jxsi", ""),
    JXSS("image/jxss", ""),
    KTX("image/ktx", ""),
    KTX2("image/ktx2", ""),
    NAPLPS("image/naplps", ""),
    PNG("image/png", ".png"),
    PRS$BTIF("image/prs.btif", ""),
    PRS$PTI("image/prs.pti", ""),
    PWG_RASTER("image/pwg-raster", ""),
    SVG_WITH_XML("image/svg+xml", ".svg"),
    T38("image/t38", ""),
    TIFF("image/tiff", ".tiff"),
    TIFF_FX("image/tiff-fx", ""),
    VND$ADOBE$PHOTOSHOP("image/vnd.adobe.photoshop", ".psd"),
    VND$AIRZIP$ACCELERATOR$AZV("image/vnd.airzip.accelerator.azv", ""),
    VND$CNS$INF2("image/vnd.cns.inf2", ""),
    VND$DECE$GRAPHIC("image/vnd.dece.graphic", ""),
    VND$DJVU("image/vnd.djvu", ".djvu"),
    VND$DWG("image/vnd.dwg", ".dwg"),
    VND$DXF("image/vnd.dxf", ".dxf"),
    VND$DVB$SUBTITLE("image/vnd.dvb.subtitle", ""),
    VND$FASTBIDSHEET("image/vnd.fastbidsheet", ""),
    VND$FPX("image/vnd.fpx", ""),
    VND$FST("image/vnd.fst", ""),
    VND$FUJIXEROX$EDMICS_MMR("image/vnd.fujixerox.edmics-mmr", ""),
    VND$FUJIXEROX$EDMICS_RLC("image/vnd.fujixerox.edmics-rlc", ""),
    VND$GLOBALGRAPHICS$PGB("image/vnd.globalgraphics.pgb", ""),
    VND$MICROSOFT$ICON("image/vnd.microsoft.icon", ".ico"),
    VND$MIX("image/vnd.mix", ""),
    VND$MS_MODI("image/vnd.ms-modi", ".mdi"),
    VND$MOZILLA$APNG("image/vnd.mozilla.apng", ""),
    VND$NET_FPX("image/vnd.net-fpx", ""),
    VND$PCO$B16("image/vnd.pco.b16", ""),
    VND$RADIANCE("image/vnd.radiance", ""),
    VND$SEALED$PNG("image/vnd.sealed.png", ""),
    VND$SEALEDMEDIA$SOFTSEAL$GIF("image/vnd.sealedmedia.softseal.gif", ""),
    VND$SEALEDMEDIA$SOFTSEAL$JPG("image/vnd.sealedmedia.softseal.jpg", ""),
    VND$SVF("image/vnd.svf", ""),
    VND$TENCENT$TAP("image/vnd.tencent.tap", ""),
    VND$VALVE$SOURCE$TEXTURE("image/vnd.valve.source.texture", ""),
    VND$WAP$WBMP("image/vnd.wap.wbmp", ".wbmp"),
    VND$XIFF("image/vnd.xiff", ""),
    VND$ZBRUSH$PCX("image/vnd.zbrush.pcx", ""),
    WMF("image/wmf", ""),
    X_EMF("image/emf", ""),
    X_WMF("image/wmf", ""),
    ;

    private final String template;
    private final String fileExtension;

    ImageType(String template, String fileExtension) {
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

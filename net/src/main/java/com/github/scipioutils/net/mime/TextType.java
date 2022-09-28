package com.github.scipioutils.net.mime;

import com.github.scipioutils.core.AssertUtils;

/**
 * @since 2022/9/21
 */
public enum TextType implements MimeType{

    TEXT_1D_INTERLEAVED_PARITYFEC("text/1d-interleaved-parityfec", ""),
    CACHE_MANIFEST("text/cache-manifest", ""),
    CALENDAR("text/calendar", ".vcs"),
    CQL("text/cql", ""),
    CQL_EXPRESSION("text/cql-expression", ""),
    CQL_IDENTIFIER("text/cql-identifier", ""),
    CSS("text/css", ".css"),
    CSV("text/csv", ".csv"),
    CSV_SCHEMA("text/csv-schema", ""),
    DIRECTORY("text/directory", ".vct"),
    DNS("text/dns", ""),
    ECMASCRIPT("text/ecmascript", ""),
    ENCAPRTP("text/encaprtp", ""),
    ENRICHED("", ""),
    EXAMPLE("text/example", ""),
    FHIRPATH("text/fhirpath", ""),
    FLEXFEC("text/flexfec", ""),
    FWDRED("text/fwdred", ""),
    GFF3("text/gff3", ""),
    GRAMMAR_REF_LIST("text/grammar-ref-list", ""),
    HTML("text/html", ".html"),
    JAVASCRIPT("text/javascript", ".js"),
    JCR_CND("text/jcr-cnd", ""),
    MARKDOWN("text/markdown", ".md"),
    MIZAR("text/mizar", ""),
    N3("text/n3", ""),
    PARAMETERS("text/parameters", ""),
    PARITYFEC("text/parityfec", ""),
    PLAIN("text/plain", ".txt"),
    PROVENANCE_NOTATION("text/provenance-notation", ""),
    PRS$FALLENSTEIN$RST("text/prs.fallenstein.rst", ""),
    PRS$LINES$TAG("text/prs.lines.tag", ""),
    PRS$PROP$LOGIC("text/prs.prop.logic", ""),
    RAPTORFEC("text/raptorfec", ""),
    RED("text/RED", ""),
    RFC822_HEADERS("text/rfc822-headers", ""),
    RICHTEXT("", ""),
    RTF("text/rtf", ""),
    RTP_ENC_AESCM128("text/rtp-enc-aescm128", ""),
    RTPLOOPBACK("text/rtploopback", ""),
    RTX("text/rtx", ""),
    SGML("text/SGML", ""),
    SHACLC("text/shaclc", ""),
    SPDX("text/spdx", ""),
    STRINGS("text/strings", ""),
    T140("text/t140", ""),
    TAB_SEPARATED_VALUES("text/tab-separated-values", ".tsv"),
    TROFF("text/troff", ".t"),
    TURTLE("text/turtle", ""),
    ULPFEC("text/ulpfec", ""),
    URI_LIST("text/uri-list", ""),
    VCARD("text/vcard", ""),
    VND$A("text/vnd.a", ""),
    VND$ABC("text/vnd.abc", ""),
    VND$ASCII_ART("text/vnd.ascii-art", ""),
    VND$CURL("text/vnd.curl", ""),
    VND$DEBIAN$COPYRIGHT("text/vnd.debian.copyright", ""),
    VND$DMCLIENTSCRIPT("text/vnd.DMClientScript", ""),
    VND$DVB$SUBTITLE("text/vnd.dvb.subtitle", ""),
    VND$ESMERTEC$THEME_DESCRIPTOR("text/vnd.esmertec.theme-descriptor", ""),
    VND$FICLAB$FLT("text/vnd.ficlab.flt", ""),
    VND$FLY("text/vnd.fly", ""),
    VND$FMI$FLEXSTOR("text/vnd.fmi.flexstor", ""),
    VND$GML("text/vnd.gml", ""),
    VND$GRAPHVIZ("text/vnd.graphviz", ".gv"),
    VND$HANS("text/vnd.hans", ""),
    VND$HGL("text/vnd.hgl", ""),
    VND$IN3D$3DML("text/vnd.in3d.3dml", ""),
    VND$IN3D$SPOT("text/vnd.in3d.spot", ""),
    VND$IPTC$NEWSML("text/vnd.IPTC.NewsML", ""),
    VND$IPTC$NITF("text/vnd.IPTC.NITF", ""),
    VND$LATEX_Z("text/vnd.latex-z", ""),
    VND$MOTOROLA$REFLEX("text/vnd.motorola.reflex", ""),
    VND$MS_MEDIAPACKAGE("text/vnd.ms-mediapackage", ""),
    VND$NET2PHONE$COMMCENTER$COMMAND("text/vnd.net2phone.commcenter.command", ""),
    VND$RADISYS$MSML_BASIC_LAYOUT("text/vnd.radisys.msml-basic-layout", ""),
    VND$SENX$WARPSCRIPT("text/vnd.senx.warpscript", ""),
    VND$SI$URICATALOGUE("text/vnd.si.uricatalogue", ""),
    VND$SUN$J2ME$APP_DESCRIPTOR("text/vnd.sun.j2me.app-descriptor", ".jad"),
    VND$SOSI("text/vnd.sosi", ""),
    VND$TROLLTECH$LINGUIST("text/vnd.trolltech.linguist", ""),
    VND$WAP$SI("text/vnd.wap.si", ""),
    VND$WAP$SL("text/vnd.wap.sl", ""),
    VND$WAP$WML("text/vnd.wap.wml", ".wml"),
    VND$WAP$WMLSCRIPT("text/vnd.wap.wmlscript", ".wmls"),
    VTT("text/vtt", ".vtt"),
    XML("text/xml", ".xml"),
    XML_EXTERNAL_PARSED_ENTITY("text/xml-external-parsed-entity", ""),
    ;

    private final String template;
    private final String fileExtension;

    TextType(String template, String fileExtension) {
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

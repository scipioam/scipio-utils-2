package com.github.scipioutils.net.mime;

import com.github.scipioutils.core.AssertUtils;

/**
 * @since 2022/9/21
 */
public enum AudioType implements MimeType{

    AUDIO_1D_INTERLEAVED_PARITYFEC("audio/1d-interleaved-parityfec", ""),
    AUDIO_32KADPCM("audio/32kadpcm", ""),
    AUDIO_3GPP("audio/3gpp", ""),
    AUDIO_3GPP2("audio/3gpp2", ""),
    AAC("audio/aac", ".aac"),
    AC3("audio/ac3", ".ac3"),
    AMR("audio/AMR", ""),
    AMR_WB("audio/AMR-WB", ""),
    AMR_WB_UP("audio/amr-wb+", ""),
    APTX("audio/aptx", ""),
    ASC("audio/asc", ""),
    ATRAC_ADVANCED_LOSSLESS("audio/ATRAC-ADVANCED-LOSSLESS", ""),
    ATRAC_X("audio/ATRAC-X", ""),
    ATRAC3("audio/ATRAC3", ""),
    BASIC("audio/basic", ".au"),
    BV16("audio/BV16", ""),
    BV32("audio/BV32", ""),
    CLEARMODE("audio/clearmode", ""),
    CN("audio/CN", ""),
    DAT12("audio/DAT12", ""),
    DLS("audio/dls", ""),
    DSR_ES201108("audio/dsr-es201108", ""),
    DSR_ES202050("audio/dsr-es202050", ""),
    DSR_ES202211("audio/dsr-es202211", ""),
    DSR_ES202212("audio/dsr-es202212", ""),
    DV("audio/DV", ""),
    DVI4("audio/DVI4", ""),
    EAC3("audio/eac3", ""),
    ENCAPRTP("audio/encaprtp", ""),
    EVRC("audio/EVRC", ""),
    EVRC_QCP("audio/EVRC-QCP", ""),
    EVRC0("audio/EVRC0", ""),
    EVRC1("audio/EVRC1", ""),
    EVRCB("audio/EVRCB", ""),
    EVRCB0("audio/EVRCB0", ""),
    EVRCB1("audio/EVRCB1", ""),
    EVRCNW("audio/EVRCNW", ""),
    EVRCNW0("audio/EVRCNW0", ""),
    EVRCNW1("audio/EVRCNW1", ""),
    EVRCWB("audio/EVRCWB", ""),
    EVRCWB0("audio/EVRCWB0", ""),
    EVRCWB1("audio/EVRCWB1", ""),
    EVS("audio/EVS", ""),
    EXAMPLE("audio/example", ""),
    FLEXFEC("audio/flexfec", ""),
    FWDRED("audio/fwdred", ""),
    G711_0("audio/G711-0", ""),
    G719("audio/G719", ""),
    G7221("audio/G7221", ""),
    G722("audio/G722", ""),
    G723("audio/G723", ""),
    G726_16("audio/G726-16", ""),
    G726_24("audio/G726-24", ""),
    G726_32("audio/G726-32", ""),
    G726_40("audio/G726-40", ""),
    G728("audio/G728", ""),
    G729("audio/G729", ""),
    G7291("audio/G7291", ""),
    G729D("audio/G729D", ""),
    G729E("audio/G729E", ""),
    GSM("audio/GSM", ""),
    GSM_EFR("audio/GSM-EFR", ""),
    GSM_HR_08("audio/GSM-HR-08", ""),
    ILBC("audio/iLBC", ""),
    IP_MR_V2$5("audio/ip-mr_v2.5", ""),
    L8("audio/L8", ""),
    L16("audio/L16", ""),
    L20("audio/L20", ""),
    L24("audio/L24", ""),
    LPC("audio/LPC", ""),
    MELP("audio/MELP", ""),
    MELP600("audio/MELP600", ""),
    MELP1200("audio/MELP1200", ""),
    MELP2400("audio/MELP2400", ""),
    MHAS("audio/mhas", ""),
    MOBILE_XMF("audio/mobile-xmf", ""),
    MPA("audio/MPA", ""),
    MP4("audio/mp4", ".m4a"),
    MP4A_LATM("audio/MP4A-LATM", ""),
    MPA_ROBUST("audio/mpa-robust", ""),
    MPEG("audio/mpeg", ".mp3"),
    MPEG4_GENERIC("audio/mpeg4-generic", ""),
    OGG("audio/ogg", ".oga"),
    OPUS("audio/opus", ""),
    PARITYFEC("audio/parityfec", ""),
    PCMA("audio/PCMA", ""),
    PCMA_WB("audio/PCMA-WB", ""),
    PCMU("audio/PCMU", ""),
    PCMU_WB("audio/PCMU-WB", ""),
    PRS$SID("audio/prs.sid", ".psid"),
    QCELP("", ""),
    RAPTORFEC("audio/raptorfec", ""),
    RED("audio/RED", ""),
    RTP_ENC_AESCM128("audio/rtp-enc-aescm128", ""),
    RTPLOOPBACK("audio/rtploopback", ""),
    RTP_MIDI("audio/rtp-midi", ""),
    RTX("audio/rtx", ""),
    SCIP("audio/scip", ""),
    SMV("audio/SMV", ""),
    SMV0("audio/SMV0", ""),
    SMV_QCP("audio/SMV-QCP", ""),
    SOFA("audio/sofa", ""),
    SP_MIDI("audio/sp-midi", ""),
    SPEEX("audio/speex", ""),
    T140C("audio/t140c", ""),
    T38("audio/t38", ""),
    TELEPHONE_EVENT("audio/telephone-event", ""),
    TETRA_ACELP("audio/TETRA_ACELP", ""),
    TETRA_ACELP_BB("audio/TETRA_ACELP_BB", ""),
    TONE("audio/tone", ""),
    TSVCIS("audio/TSVCIS", ""),
    UEMCLIP("audio/UEMCLIP", ""),
    ULPFEC("audio/ulpfec", ""),
    USAC("audio/usac", ""),
    VDVI("audio/VDVI", ""),
    VMR_WB("audio/VMR-WB", ""),
    VND$3GPP$IUFP("audio/vnd.3gpp.iufp", ""),
    VND$4SB("audio/vnd.4SB", ""),
    VND$AUDIOKOZ("audio/vnd.audiokoz", ""),
    VND$CELP("audio/vnd.CELP", ""),
    VND$CISCO$NSE("audio/vnd.cisco.nse", ""),
    VND$CMLES$RADIO_EVENTS("audio/vnd.cmles.radio-events", ""),
    VND$CNS$ANP1("audio/vnd.cns.anp1", ""),
    VND$CNS$INF1("audio/vnd.cns.inf1", ""),
    VND$DECE$AUDIO("audio/vnd.dece.audio", ""),
    VND$DIGITAL_WINDS("audio/vnd.digital-winds", ""),
    VND$DLNA$ADTS("audio/vnd.dlna.adts", ""),
    VND$DOLBY$HEAAC$1("audio/vnd.dolby.heaac.1", ""),
    VND$DOLBY$HEAAC$2("audio/vnd.dolby.heaac.2", ""),
    VND$DOLBY$MLP("audio/vnd.dolby.mlp", ""),
    VND$DOLBY$MPS("audio/vnd.dolby.mps", ""),
    VND$DOLBY$PL2("audio/vnd.dolby.pl2", ""),
    VND$DOLBY$PL2X("audio/vnd.dolby.pl2x", ""),
    VND$DOLBY$PL2Z("audio/vnd.dolby.pl2z", ""),
    VND$DOLBY$PULSE$1("audio/vnd.dolby.pulse.1", ""),
    VND$DRA("audio/vnd.dra", ""),
    VND$DTS("audio/vnd.dts", ""),
    VND$DTS$HD("audio/vnd.dts.hd", ""),
    VND$DTS$UHD("audio/vnd.dts.uhd", ""),
    VND$DVB$FILE("audio/vnd.dvb.file", ""),
    VND$EVERAD$PLJ("audio/vnd.everad.plj", ""),
    VND$HNS$AUDIO("audio/vnd.hns.audio", ""),
    VND$LUCENT$VOICE("audio/vnd.lucent.voice", ""),
    VND$MS_PLAYREADY$MEDIA$PYA("audio/vnd.ms-playready.media.pya", ""),
    VND$NOKIA$MOBILE_XMF("audio/vnd.nokia.mobile-xmf", ""),
    VND$NORTEL$VBK("audio/vnd.nortel.vbk", ""),
    VND$NUERA$ECELP4800("audio/vnd.nuera.ecelp4800", ""),
    VND$NUERA$ECELP7470("audio/vnd.nuera.ecelp7470", ""),
    VND$NUERA$ECELP9600("audio/vnd.nuera.ecelp9600", ""),
    VND$OCTEL$SBC("audio/vnd.octel.sbc", ""),
    VND$PRESONUS$MULTITRACK("audio/vnd.presonus.multitrack", ""),
    VND$QCELP("audio/vnd.qcelp", ""),
    VND$RHETOREX$32KADPCM("audio/vnd.rhetorex.32kadpcm", ""),
    VND$RIP("audio/vnd.rip", ""),
    VND$SEALEDMEDIA$SOFTSEAL$MPEG("audio/vnd.sealedmedia.softseal.mpeg", ""),
    VND$VMX$CVSD("audio/vnd.vmx.cvsd", ""),
    VORBIS("audio/vorbis", ""),
    VORBIS_CONFIG("audio/vorbis-config", ""),
    ;

    private final String template;
    private final String fileExtension;

    AudioType(String template, String fileExtension) {
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
        return MimeMainType.AUDIO;
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

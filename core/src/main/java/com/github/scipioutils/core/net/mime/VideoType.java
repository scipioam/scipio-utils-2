package com.github.scipioutils.core.net.mime;

import com.github.scipioutils.core.AssertUtils;

/**
 * @since 2022/9/21
 */
public enum VideoType implements MimeType{

    VIDEO_1D_INTERLEAVED_PARITYFEC("video/1d-interleaved-parityfec"),
    VIDEO_3GPP("video/3gpp"),
    VIDEO_3GPP2("video/3gpp2"),
    VIDEO_3GPP_TT("video/3gpp-tt"),
    AV1("video/AV1"),
    BMPEG("video/BMPEG"),
    BT656("video/BT656"),
    CELB("video/CelB"),
    DV("video/DV"),
    ENCAPRTP("video/encaprtp"),
    EXAMPLE("video/example"),
    FLEXFEC("video/flexfec"),
    H261("video/H261"),
    H263("video/H263"),
    H263_1998("video/H263-1998"),
    H263_2000("video/H263-2000"),
    H264("video/H264"),
    H264_RCDO("video/H264-RCDO"),
    H264_SVC("video/H264-SVC"),
    H265("video/H265"),
    ISO$SEGMENT("video/iso.segment"),
    JPEG("video/JPEG"),
    JPEG2000("video/jpeg2000"),
    MJ2("video/mj2"),
    MP1S("video/MP1S"),
    MP2P("video/MP2P"),
    MP2T("video/MP2T"),
    MP4("video/mp4"),
    MP4V_ES("video/MP4V-ES"),
    MPV("video/MPV"),
    MPEG(""),
    MPEG4_GENERIC("video/mpeg4-generic"),
    NV("video/nv"),
    OGG("video/ogg"),
    PARITYFEC("video/parityfec"),
    POINTER("video/pointer"),
    QUICKTIME("video/quicktime"),
    RAPTORFEC("video/raptorfec"),
    RAW("video/raw"),
    RTP_ENC_AESCM128("video/rtp-enc-aescm128"),
    RTPLOOPBACK("video/rtploopback"),
    RTX("video/rtx"),
    SCIP("video/scip"),
    SMPTE291("video/smpte291"),
    SMPTE292M("video/SMPTE292M"),
    ULPFEC("video/ulpfec"),
    VC1("video/vc1"),
    VC2("video/vc2"),
    VND$CCTV("video/vnd.CCTV"),
    VND$DECE$HD("video/vnd.dece.hd"),
    VND$DECE$MOBILE("video/vnd.dece.mobile"),
    VND$DECE$MP4("video/vnd.dece.mp4"),
    VND$DECE$PD("video/vnd.dece.pd"),
    VND$DECE$SD("video/vnd.dece.sd"),
    VND$DECE$VIDEO("video/vnd.dece.video"),
    VND$DIRECTV$MPEG("video/vnd.directv.mpeg"),
    VND$DIRECTV$MPEG_TTS("video/vnd.directv.mpeg-tts"),
    VND$DLNA$MPEG_TTS("video/vnd.dlna.mpeg-tts"),
    VND$DVB$FILE("video/vnd.dvb.file"),
    VND$FVT("video/vnd.fvt"),
    VND$HNS$VIDEO("video/vnd.hns.video"),
    VND$IPTVFORUM$1DPARITYFEC_1010("video/vnd.iptvforum.1dparityfec-1010"),
    VND$IPTVFORUM$1DPARITYFEC_2005("video/vnd.iptvforum.1dparityfec-2005"),
    VND$IPTVFORUM$2DPARITYFEC_1010("video/vnd.iptvforum.2dparityfec-1010"),
    VND$IPTVFORUM$2DPARITYFEC_2005("video/vnd.iptvforum.2dparityfec-2005"),
    VND$IPTVFORUM$TTSAVC("video/vnd.iptvforum.ttsavc"),
    VND$IPTVFORUM$TTSMPEG2("video/vnd.iptvforum.ttsmpeg2"),
    VND$MOTOROLA$VIDEO("video/vnd.motorola.video"),
    VND$MOTOROLA$VIDEOP("video/vnd.motorola.videop"),
    VND$MPEGURL("video/vnd.mpegurl"),
    VND$MS_PLAYREADY$MEDIA$PYV("video/vnd.ms-playready.media.pyv"),
    VND$NOKIA$INTERLEAVED_MULTIMEDIA("video/vnd.nokia.interleaved-multimedia"),
    VND$NOKIA$MP4VR("video/vnd.nokia.mp4vr"),
    VND$NOKIA$VIDEOVOIP("video/vnd.nokia.videovoip"),
    VND$OBJECTVIDEO("video/vnd.objectvideo"),
    VND$RADGAMETTOOLS$BINK("video/vnd.radgamettools.bink"),
    VND$RADGAMETTOOLS$SMACKER("video/vnd.radgamettools.smacker"),
    VND$SEALED$MPEG1("video/vnd.sealed.mpeg1"),
    VND$SEALED$MPEG4("video/vnd.sealed.mpeg4"),
    VND$SEALED$SWF("video/vnd.sealed.swf"),
    VND$SEALEDMEDIA$SOFTSEAL$MOV("video/vnd.sealedmedia.softseal.mov"),
    VND$UVVU$MP4("video/vnd.uvvu.mp4"),
    VND$YOUTUBE$YT("video/vnd.youtube.yt"),
    VND$VIVO("video/vnd.vivo"),
    VP8("video/VP8");

    private final String template;

    VideoType(String template) {
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

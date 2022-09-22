package com.github.scipioutils.core.net.mime;

import com.github.scipioutils.core.AssertUtils;

/**
 * @since 2022/9/21
 */
public enum ModelType implements MimeType{

    MODEL_3MF("model/3mf"),
    E57("model/e57"),
    EXAMPLE("model/example"),
    GLTF_BINARY("model/gltf_binary"),
    GLTF_WITH_JSON("model/gltf_with_json"),
    IGES("model/iges"),
    MESH(""),
    MTL("model/mtl"),
    OBJ("model/obj"),
    STL("model/stl"),
    VND$COLLADA_WITH_XML("model/vnd.collada_with_xml"),
    VND$DWF("model/vnd.dwf"),
    VND$FLATLAND$3DML("model/vnd.flatland.3dml"),
    VND$GDL("model/vnd.gdl"),
    VND$GS_GDL("model/vnd.gs_gdl"),
    VND$GTW("model/vnd.gtw"),
    VND$MOML_WITH_XML("model/vnd.moml_with_xml"),
    VND$MTS("model/vnd.mts"),
    VND$OPENGEX("model/vnd.opengex"),
    VND$PARASOLID$TRANSMIT$BINARY("model/vnd.parasolid.transmit.binary"),
    VND$PARASOLID$TRANSMIT$TEXT("model/vnd.parasolid.transmit.text"),
    VND$ROSETTE$ANNOTATED_DATA_MODEL("model/vnd.rosette.annotated_data_model"),
    VND$USDZ_WITH_ZIP("model/vnd.usdz_with_zip"),
    VND$VALVE$SOURCE$COMPILED_MAP("model/vnd.valve.source.compiled_map"),
    VND$VTU("model/vnd.vtu"),
    VRML(""),
    X3D_VRML("model/x3d_vrml"),
    X3D_WITH_FASTINFOSET("model/x3d_with_fastinfoset"),
    X3D_WITH_XML("model/x3d_with_xml");

    private final String template;

    ModelType(String template) {
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

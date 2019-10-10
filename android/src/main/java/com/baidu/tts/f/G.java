/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum G {
    a(null, "state", null),
    b(null, "code", null),
    c(null, "data", null),
    d(null, "iversion", null),
    e("url", "url", null),
    f(null, "md5", null),
    g(null, "length", null),
    h(null, "absPath", null),
    i(null, "id", null),
    j(null, "gender", null),
    k(null, "domain", null),
    l(null, "quality", null),
    m(null, "data_count", null),
    n(null, "data_list", null),
    o(null, "name", null),
    p(null, "version_min", null),
    q(null, "version_max", null),
    r(null, "text_data_id", null),
    s(null, "speech_data_id", null),
    t("func", "function", ""),
    u("modelsinfo", "modelsinfo", ""),
    v("err_no", "errorNumber", ""),
    w("err_msg", "errorMessage", ""),
    x(null, "mixMode", null),
    y("ncps", "notificationCountPerSecond", ""),
    z("pct", "percent", ""),
    A("ac", "appCode", ""),
    B("pn", "packageName", "app_name"),
    C("", "platform", ""),
    D("spd", "speed", ""),
    E("vol", "volume", ""),
    F("pit", "pitch", ""),
    G("lan", "language", ""),
    H("cod", "textEncode", ""),
    I("st", "streamType", ""),
    J("aue", "audioEncode", ""),
    K("rate", "audioRate", ""),
    L("per", "speaker", ""),
    M("sty", "style", ""),
    N("bcg", "background", ""),
    O("pdt", "productId", ""),
    P("tdp", "textDatPath", ""),
    Q("sdp", "speechDatPath", ""),
    R("tlfp", "ttsLicenseFilePath", ""),
    S("cs", "custom_synth", ""),
    T("xml", "open_xml", ""),
    U("tvo", "ttsVocoderOptimzation", ""),
    V("sr", "sampleRate", ""),
    W("sn", "serialNumber", ""),
    X("idx", "index", ""),
    Y("tex", "text", ""),
    Z("ctp", "clientPath", ""),
    aa("cuid", "deviceId", "wise_cuid"),
    ab("ver", "version", "sdk_version"),
    ac("num", "number", ""),
    ad("en", "engine", ""),
    ae("ter", "territory", ""),
    af("puc", "punctuation", ""),
    ag("ctx", "context", ""),
    ah("", "apiKey", ""),
    ai("", "secretKey", ""),
    aj("tok", "token", ""),
    ak("spec", "spec", ""),
    al("key", "key", ""),
    am("ph", "proxyHost", ""),
    an("pp", "proxyPort", ""),
    ao("rp", "requestProtocol", ""),
    ap("dns", "dns", "");
    
    private final String aq;
    private final String ar;
    private final String as;

    private G(String string2, String string3, String string4) {
        this.aq = string2;
        this.ar = string3;
        this.as = string4;
    }

    public String a() {
        return this.aq;
    }

    public String b() {
        return this.ar;
    }

    public static String a(G g2) {
        return g2 == null ? null : g2.name();
    }
}


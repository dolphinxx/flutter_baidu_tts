/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum C {
    a("BV_16K", "0", 8.0),
    b("AMR_6K6", "0", 15.6),
    c("AMR_8K85", "1", 15.6),
    d("AMR_12K65", "2", 15.6),
    e("AMR_14K25", "3", 15.6),
    f("AMR_15K85", "4", 15.6),
    g("AMR_18K25", "5", 15.6),
    h("AMR_19K85", "6", 15.6),
    i("AMR_23K05", "7", 15.6),
    j("AMR_23K85", "8", 15.6),
    k("OPUS_8K", "0", 15.0),
    l("OPUS_16K", "1", 15.0),
    m("OPUS_18K", "2", 15.0),
    n("OPUS_20K", "3", 15.0),
    o("OPUS_24K", "4", 15.0),
    p("OPUS_32K", "5", 15.0),
    q("MP3_8K", "0", 10.0),
    r("MP3_11K", "1", 10.0),
    s("MP3_16K", "2", 10.0),
    t("MP3_24K", "3", 10.0),
    u("MP3_32K", "4", 10.0),
    v("PCM", "0", 10.0);
    
    private final String w;
    private final String x;
    private final double y;

    private C(String string2, String string3, double d2) {
        this.w = string2;
        this.x = string3;
        this.y = d2;
    }

    public String a() {
        return this.x;
    }

    public double b() {
        return this.y;
    }

    public static C a(String string) {
        C[] arrc;
        for (C c2 : arrc = C.values()) {
            if (!c2.a().equals(string)) continue;
            return c2;
        }
        return null;
    }

    public static C[] c() {
        return new C[]{a};
    }

    public static C[] d() {
        return new C[]{b, c, d, e, f, g, h, i, j};
    }

    public static C[] e() {
        return new C[]{k, l, m, n, o, p};
    }

    public static C[] f() {
        return new C[]{q, r, s, t, u};
    }

    public static C[] g() {
        return new C[]{v};
    }
}


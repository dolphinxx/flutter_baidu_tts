/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum D {
    a("gb18030", "0"),
    b("big5", "1"),
    c("utf-8", "2"),
    d("gbk", "4"),
    e("unicode", "5");
    
    private final String f;
    private final String g;

    private D(String string2, String string3) {
        this.f = string2;
        this.g = string3;
    }

    public String a() {
        return this.f;
    }

    public String b() {
        return this.g;
    }

    public static D a(String string) {
        for (D d2 : D.values()) {
            if (!d2.b().equals(string)) continue;
            return d2;
        }
        return null;
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum H {
    a("chinese", "ZH"),
    b("english", "EN");
    
    private final String c;
    private final String d;

    private H(String string2, String string3) {
        this.c = string2;
        this.d = string3;
    }

    public String a() {
        return this.d;
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum K {
    a(8000, "8k"),
    b(16000, "16k"),
    c(24000, "24k"),
    d(48000, "48k");
    
    private final int e;
    private final String f;

    private K(int n3, String string2) {
        this.e = n3;
        this.f = string2;
    }

    public int a() {
        return this.e;
    }
}


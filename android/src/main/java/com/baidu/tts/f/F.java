/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum F {
    a(0, "online engine"),
    b(1, "offline engine"),
    c(2, "online and offline mix engine");
    
    private final int d;
    private final String e;

    private F(int n3, String string2) {
        this.d = n3;
        this.e = string2;
    }

    public int a() {
        return this.d;
    }
}


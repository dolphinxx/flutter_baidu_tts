/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum M {
    a(0, "just online"),
    b(1, "just offline"),
    c(2, "if online cannot use switch from online to offline");
    
    private final int d;
    private final String e;

    private M(int n3, String string2) {
        this.d = n3;
        this.e = string2;
    }

    public int a() {
        return this.d;
    }

    public String b() {
        return this.e;
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum L {
    a(6L, 6000L),
    b(1L, 1200L),
    c(4L, 4000L);
    
    private final long d;
    private final long e;

    private L(long l2, long l3) {
        this.d = l2;
        this.e = l3;
    }

    public long a() {
        return this.e;
    }

    public int b() {
        return (int)this.a();
    }
}


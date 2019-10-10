/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.b.b;

import com.baidu.tts.b.b.b.C;

public class B {
    private static volatile B a = null;

    private B() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static B a() {
        if (a != null) return a;
        Class<B> class_ = B.class;
        synchronized (B.class) {
            if (a != null) return a;
            {
                a = new B();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return a;
        }
    }

    public C b() {
        return this.c();
    }

    private com.baidu.tts.b.b.b.B c() {
        return new com.baidu.tts.b.b.b.B();
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.d.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.d.b.B;

public class C {
    private B a;

    public void a(B b2) {
        this.a = b2;
    }

    public String a() {
        return this.a != null ? this.a.c() : null;
    }

    public String b() {
        return this.a != null ? this.a.b() : null;
    }

    public void c() {
        this.a.g();
    }

    public void d() {
        this.a.h();
    }

    public void a(long l2, long l3) {
        this.a.a(l2, l3);
    }

    public void e() {
        this.a.i();
    }

    public void a(TtsError ttsError) {
        this.a.a(ttsError);
    }
}


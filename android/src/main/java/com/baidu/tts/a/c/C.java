/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.a.c;

import com.baidu.tts.a.c.A;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.aop.ttslistener.TtsListener;
import com.baidu.tts.m.E;
import com.baidu.tts.m.F;
import com.baidu.tts.m.G;
import com.baidu.tts.m.I;

public class C {
    private A a;

    public TtsError a() {
        return this.a.b();
    }

    public void b() {
        this.a.c();
    }

    public void c() {
        this.a.d();
    }

    public void d() {
        this.a.e();
    }

    public void e() {
        this.a.f();
    }

    public void a(TtsListener ttsListener) {
        this.a.a(ttsListener);
    }

    public void a(I i2) {
        this.a.b(i2);
    }

    public void b(I i2) {
        this.a.a(i2);
    }

    public A f() {
        return this.a;
    }

    public void a(A a2) {
        this.a = a2;
    }

    public int a(E e2) {
        return this.a.a(e2);
    }

    public int b(E e2) {
        return this.a.b(e2);
    }

    public int a(G g2) {
        return this.a.a(g2);
    }

    public int a(F f2) {
        return this.a.a(f2);
    }

    public int a(float f2, float f3) {
        return this.a.a(f2, f3);
    }
}


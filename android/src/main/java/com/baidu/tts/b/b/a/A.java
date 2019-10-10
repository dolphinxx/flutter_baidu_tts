/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.b.b.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.b.b.a.B;
import com.baidu.tts.b.b.a.C;
import com.baidu.tts.m.H;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class A
extends com.baidu.tts.j.A
implements C {
    protected com.baidu.tts.b.b.A a;
    protected volatile B b;

    public B a() {
        return this.b;
    }

    public void a(B b2) {
        this.b = b2;
    }

    @Override
    protected TtsError g() {
        return this.b.b();
    }

    @Override
    protected void h() {
        this.b.a();
    }

    @Override
    protected void i() {
        this.b.c();
    }

    @Override
    protected void j() {
        this.b.d();
    }

    @Override
    protected void k() {
        this.b.e();
    }

    @Override
    protected void l() {
        this.b.f();
    }

    @Override
    public <T> void a(T t) {
        this.b.a(t);
    }

    @Override
    public void a(com.baidu.tts.b.b.A a2) {
        this.b.a(a2);
    }

    @Override
    public void o() {
        this.b.o();
    }

    @Override
    public void a(H h2) {
        this.b.a(h2);
    }

    @Override
    public int a(float f2, float f3) {
        return this.b.a(f2, f3);
    }

    @Override
    public int a(int n2) {
        return this.b.a(n2);
    }

    @Override
    public int b(int n2) {
        return this.b.b(n2);
    }

    protected void b(H h2) {
        if (this.C() && this.a != null) {
            this.a.a(h2);
        }
    }

    protected void c(H h2) {
        if (this.C() && this.a != null) {
            this.a.b(h2);
        }
    }

    protected void d(H h2) {
        if (this.C() && this.a != null) {
            this.a.c(h2);
        }
    }
}


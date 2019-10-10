/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.b.a.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.b.a.a.C;
import com.baidu.tts.b.a.a.D;
import com.baidu.tts.m.E;
import com.baidu.tts.m.F;
import com.baidu.tts.m.G;
import com.baidu.tts.m.I;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class B
implements D {
    protected C a;

    public B(C c2) {
        this.a = c2;
    }

    public void a(B b2) {
        this.a.a(b2);
    }

    @Override
    public TtsError b() {
        return null;
    }

    public void a() {
    }

    @Override
    public void c() {
    }

    @Override
    public void d() {
    }

    @Override
    public void e() {
    }

    @Override
    public void f() {
    }

    @Override
    public void a(com.baidu.tts.b.a.b.B b2) {
        this.a.b(b2);
    }

    @Override
    public void a(com.baidu.tts.b.a.B b2) {
        this.a.b(b2);
    }

    @Override
    public <T> void a(T t) {
        this.a.b(t);
    }

    @Override
    public void a(I i2) {
    }

    @Override
    public int a(E e2) {
        return this.a.c(e2);
    }

    @Override
    public int b(E e2) {
        return this.a.d(e2);
    }

    @Override
    public int a(G g2) {
        return this.a.b(g2);
    }

    @Override
    public int a(F f2) {
        return this.a.b(f2);
    }
}


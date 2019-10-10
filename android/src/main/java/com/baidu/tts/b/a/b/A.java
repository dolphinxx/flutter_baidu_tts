/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.b.a.b;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.m.E;
import com.baidu.tts.m.G;
import com.baidu.tts.m.H;
import com.baidu.tts.m.I;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class A
implements B {
    protected com.baidu.tts.b.a.B a;

    @Override
    public <T> void a(T t) {
    }

    @Override
    public TtsError a() {
        return null;
    }

    @Override
    public TtsError b() {
        return null;
    }

    @Override
    public void a(com.baidu.tts.b.a.B b2) {
        this.a = b2;
    }

    @Override
    public TtsError a(I i2) throws InterruptedException {
        return null;
    }

    @Override
    public int a(E e2) {
        return 0;
    }

    @Override
    public int b(E e2) {
        return 0;
    }

    @Override
    public int a(G g2) {
        return 0;
    }

    protected void a(H h2) {
        if (this.a != null) {
            this.a.onSynthesizeDataArrived(h2);
        }
    }
}


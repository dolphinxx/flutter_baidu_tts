/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.b.b.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.b.b.a.B;
import com.baidu.tts.b.b.a.D;
import com.baidu.tts.b.b.a.F;

public class H
extends B {
    public H(F f2) {
        super(f2);
    }

    public TtsError b() {
        TtsError ttsError = this.a.t();
        this.a(this.a.q());
        return ttsError;
    }

    public void a(com.baidu.tts.m.H h2) {
        this.b();
        if (this.a.a() != this) {
            this.a.a(h2);
        }
    }

    public void o() {
        this.b();
        if (this.a.a() != this) {
            this.a.o();
        }
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.o.a;

import android.content.Context;
import com.baidu.tts.aop.tts.ITts;
import com.baidu.tts.aop.ttslistener.TtsListener;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.f.G;
import com.baidu.tts.f.M;
import com.baidu.tts.m.J;
import com.baidu.tts.o.a.C;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class A
implements ITts {
    protected C a;

    public A(C c2) {
        this.a = c2;
    }

    @Override
    public void setTtsListener(TtsListener ttsListener) {
        this.a.a(ttsListener);
    }

    @Override
    public TtsListener getTtsListener() {
        return this.a.m();
    }

    @Override
    public void setContext(Context context) {
        this.a.a(context);
    }

    @Override
    public void setMode(M mode) {
        this.a.a(mode);
    }

    @Override
    public M getMode() {
        return this.a.n();
    }

    @Override
    public AuthInfo auth(M ttsEnum) {
        return this.a.b(ttsEnum);
    }

    @Override
    public int setParam(G key, String value) {
        return this.a.a(key, value);
    }

    @Override
    public J getTtsParams() {
        return this.a.o();
    }

    public void a(A a2) {
        this.a.a(a2);
    }
}


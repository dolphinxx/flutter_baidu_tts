/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.o.a;

import android.content.Context;
import com.baidu.tts.aop.tts.ITts;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.aop.ttslistener.TtsListener;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.b.a.b.E;
import com.baidu.tts.b.a.b.F;
import com.baidu.tts.f.M;
import com.baidu.tts.f.N;
//import com.baidu.tts.m.E;
//import com.baidu.tts.m.F;
import com.baidu.tts.m.G;
import com.baidu.tts.m.H;
import com.baidu.tts.m.I;
import com.baidu.tts.m.J;
//import com.baidu.tts.o.a.A;
//import com.baidu.tts.o.a.B;
//import com.baidu.tts.o.a.D;

public class C
implements ITts {
    private J b;
    private TtsListener c;
    private M d;
    private com.baidu.tts.a.c.C e;
    private volatile A f;
    private D g;
    private B h;

    public C() {
        b = new J();
        g = new D(this);
        h = new B(this);
        f = g;
    }

    public TtsError b() {
        return this.f.b();
    }

    public void c() {
        this.f.c();
    }

    public void d() {
        this.f.d();
    }

    public void e() {
        this.f.e();
    }

    public void f() {
        this.f.f();
    }

    public void setTtsListener(TtsListener ttsListener) {
        this.f.setTtsListener(ttsListener);
    }

    public TtsListener getTtsListener() {
        return this.f.getTtsListener();
    }

    public void setContext(Context context) {
        this.f.setContext(context);
    }

    public void setMode(M mode) {
        this.f.setMode(mode);
    }

    public M getMode() {
        return this.f.getMode();
    }

    public AuthInfo auth(M ttsEnum) {
        return this.f.auth(ttsEnum);
    }

    public int setParam(com.baidu.tts.f.G key, String value) {
        return this.f.setParam(key, value);
    }

    public void speak(I textParams) {
        this.f.speak(textParams);
    }

    public void synthesize(I textParams) {
        this.f.synthesize(textParams);
    }

    public int loadCustomResource(com.baidu.tts.m.E params) {
        return this.f.loadCustomResource(params);
    }

    public int freeCustomResource(com.baidu.tts.m.E params) {
        return this.f.freeCustomResource(params);
    }

    public int loadModel(G params) {
        return this.f.loadModel(params);
    }

    public int loadEnglishModel(com.baidu.tts.m.F params) {
        return this.f.loadEnglishModel(params);
    }

    public int setStereoVolume(float leftVolume, float rightVolume) {
        return this.f.setStereoVolume(leftVolume, rightVolume);
    }

    public int setAudioStreamType(int streamType) {
        return this.f.setAudioStreamType(streamType);
    }

    public int setAudioSampleRate(int sampleRate) {
        return this.f.setAudioSampleRate(sampleRate);
    }

    public J getTtsParams() {
        return this.f.getTtsParams();
    }

    void a(A a2) {
        this.f = a2;
    }

    public D a() {
        return this.g;
    }

    public B g() {
        return this.h;
    }

    TtsError h() {
        if (this.d == null) {
            this.d = M.a;
        }
        if (this.b == null) {
            this.b = new J();
        }
        TtsError ttsError = com.baidu.tts.h.b.B.a().b();
        assert (ttsError == null);
        com.baidu.tts.b.a.a.D d2 = null;
        Object a2 = null;
        switch (this.d) {
            case a: {
                d2 = com.baidu.tts.b.a.A.a().a(com.baidu.tts.f.F.a);
                a2 = this.b.c();
                break;
            }
            case b: {
                d2 = com.baidu.tts.b.a.A.a().a(com.baidu.tts.f.F.b);
                a2 = this.b.d();
                break;
            }
            case c: {
                d2 = com.baidu.tts.b.a.A.a().a(com.baidu.tts.f.F.c);
                a2 = this.b.a();
                break;
            }
        }
        if (d2 != null && a2 != null) {
            d2.a(a2);
            com.baidu.tts.b.b.a.F f2 = new com.baidu.tts.b.b.a.F();
            com.baidu.tts.m.A a3 = this.b.b();
            f2.a(a3);
            this.e = new com.baidu.tts.a.c.C();
            com.baidu.tts.a.c.B b2 = new com.baidu.tts.a.c.B(d2, f2, this.b);
            this.e.a(b2);
            if (this.c != null) {
                this.e.a(this.c);
            }
            return this.e.a();
        }
        return com.baidu.tts.h.a.C.a().b(N.S);
    }

    void i() {
        this.e.b();
    }

    void j() {
        this.e.c();
    }

    void k() {
        this.e.d();
    }

    void l() {
        this.e.e();
        com.baidu.tts.auth.A.a().b();
        com.baidu.tts.h.b.B.a().f();
        this.b = new J();
    }

    void a(TtsListener ttsListener) {
        if (ttsListener != null && ttsListener != this.c) {
            this.c = ttsListener;
            if (this.e != null) {
                this.e.a(this.c);
            }
        }
    }

    TtsListener m() {
        return this.c;
    }

    void a(Context context) {
        com.baidu.tts.h.b.B.a().a(context);
    }

    void a(M m2) {
        this.d = m2;
    }

    M n() {
        return this.d;
    }

    AuthInfo b(M m2) {
        return com.baidu.tts.auth.A.a().a(m2, this.b);
    }

    int a(com.baidu.tts.f.G g2, String string) {
        if (this.b != null) {
            return this.b.a(g2, string);
        }
        return 0;
    }

    void a(I i2) {
        this.e.a(i2);
    }

    void b(I i2) {
        this.e.b(i2);
    }

    int a(com.baidu.tts.m.E e2) {
        return this.e.a(e2);
    }

    int b(com.baidu.tts.m.E e2) {
        return this.e.b(e2);
    }

    int a(G g2) {
        return this.e.a(g2);
    }

    int a(com.baidu.tts.m.F f2) {
        return this.e.a(f2);
    }

    J o() {
        return this.b;
    }

    int a(float f2, float f3) {
        return this.e.a(f2, f3);
    }

    int a(int n2) {
        try {
            com.baidu.tts.b.b.a.C c2 = this.e.f().a();
            return c2.a(n2);
        }
        catch (Exception exception) {
            return -1;
        }
    }

    int b(int n2) {
        try {
            com.baidu.tts.b.b.a.C c2 = this.e.f().a();
            return c2.b(n2);
        }
        catch (Exception exception) {
            return -1;
        }
    }

    public int p() {
        if (this.c == null) {
            throw new IllegalStateException(N.S.c());
        }
        TtsError ttsError = com.baidu.tts.h.a.C.a().b(N.S);
        this.a(ttsError);
        return -1;
    }

    public void a(TtsError ttsError) {
        this.a(H.b(ttsError));
    }

    public void a(H h2) {
        if (this.c != null) {
            this.c.onError(h2);
        }
    }

    public boolean q() {
        return this.h == this.f;
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.b.a.b;

import android.util.Log;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.b.a.B;
import com.baidu.tts.b.a.b.A;
import com.baidu.tts.b.a.b.C;
import com.baidu.tts.b.a.b.E;
import com.baidu.tts.b.a.b.F;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.N;
import com.baidu.tts.m.G;
import com.baidu.tts.m.H;
import com.baidu.tts.m.I;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class D
extends A {
    private com.baidu.tts.m.B b;
    private F c = new F();
    private E d = new E();
    private C e = new C();
    private TtsError f;
    private int g = 0;
    private int h = 0;

    @Override
    public TtsError a() {
        this.c.a(new B(){

            public void onSynthesizeStart(H h2) {
            }

            public void onSynthesizeDataArrived(H h2) {
                D.this.h = h2.b();
                D.this.g = h2.c();
                D.this.a(h2);
            }

            public void onSynthesizeFinished(H h2) {
            }

            public void e(H h2) {
            }

            public void onError(H h2) {
            }
        });
        this.d.a(new B(){

            public void onSynthesizeStart(H h2) {
            }

            public void onSynthesizeDataArrived(H h2) {
                D.this.a(D.this.b(h2));
            }

            public void onSynthesizeFinished(H h2) {
            }

            public void e(H h2) {
            }

            public void onError(H h2) {
            }
        });
        this.c.a();
        this.f = this.d.a();
        TtsError ttsError = null;
        if (this.f != null) {
            ttsError = com.baidu.tts.h.a.C.a().b(N.N);
        }
        return ttsError;
    }

    @Override
    public TtsError b() {
        this.c.b();
        this.d.b();
        this.e.a(null);
        return null;
    }

    public <AllSynthesizerParams> void a(AllSynthesizerParams AllSynthesizerParams) {
        Log.d("-----", "ab.a.b.D.a(AllSynthesizerParams AllSynthesizerParams) -> " + AllSynthesizerParams.getClass().getName());
        this.b = (com.baidu.tts.m.B)AllSynthesizerParams;
        F.b b2 = this.b.a();
        b2.b(3);
        b2.c(500);
        this.c.a(b2);
        E.b b3 = this.b.b();
        this.d.a(b3);
        this.e.a(this.b);
    }

    @Override
    public TtsError a(I i2) throws InterruptedException {
        this.h = 0;
        this.g = 0;
        TtsError ttsError = null;
        boolean bl = this.e.a();
        if (bl) {
            ttsError = this.c.a(i2);
            if (ttsError != null) {
                LoggerProxy.d("MixSynthesizer", "online synthesize ttserror=" + ttsError.getDetailMessage());
                String string = i2.c();
                String string2 = string.substring(this.g);
                i2.b(string2);
                ttsError = this.d.a(i2);
            }
        } else {
            ttsError = this.d.a(i2);
        }
        return ttsError;
    }

    private H b(H h2) {
        int n2;
        int n3 = n2 = h2.b();
        n3 = n2 >= 0 ? n2 + this.h : n2 - this.h;
        h2.b(n3);
        int n4 = h2.c();
        int n5 = n4 + this.g;
        h2.d(n5);
        return h2;
    }

    @Override
    public int a(com.baidu.tts.m.E e2) {
        return this.d.a(e2);
    }

    @Override
    public int b(com.baidu.tts.m.E e2) {
        return this.d.b(e2);
    }

    @Override
    public int a(G g2) {
        if (this.f == null) {
            return this.d.a(g2);
        }
        String string = g2.a();
        String string2 = g2.b();
        E.b b2 = this.b.b();
        b2.d(string);
        b2.e(string2);
        this.f = this.d.a();
        if (this.f == null) {
            return 0;
        }
        return this.f.getDetailCode();
    }

    @Override
    public int a(com.baidu.tts.m.F f2) {
        return this.d.a(f2);
    }

}


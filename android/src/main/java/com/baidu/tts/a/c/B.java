/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.baidu.tts.a.c;

import android.util.Log;
import com.baidu.tts.a.c.A;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.aop.ttslistener.TtsListener;
import com.baidu.tts.b.a.a.D;
import com.baidu.tts.b.b.a.C;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.m.E;
import com.baidu.tts.m.F;
import com.baidu.tts.m.G;
import com.baidu.tts.m.H;
import com.baidu.tts.m.I;
import com.baidu.tts.m.J;

public class B
implements A {
    private D a;
    private C b;
    private TtsListener c;
    private com.baidu.tts.b.a.B d;
    private com.baidu.tts.b.b.A e;
    private com.baidu.tts.b.a.B f;

    public B(D d2, C c2, J j2) {
        this.a = d2;
        this.b = c2;
    }

    public TtsError b() {
        TtsError ttsError = this.a.b();
        TtsError ttsError2 = this.b.b();
        this.g();
        return ttsError;
    }

    public void c() {
        this.a.c();
        this.b.c();
    }

    public void d() {
        this.a.d();
        this.b.d();
    }

    public void e() {
        LoggerProxy.d("TtsAdapter", "before engine stop");
        this.a.e();
        LoggerProxy.d("TtsAdapter", "after engine stop");
        this.b.e();
        LoggerProxy.d("TtsAdapter", "after play stop");
    }

    public void f() {
        LoggerProxy.d("TtsAdapter", "before engine destroy");
        this.a.f();
        LoggerProxy.d("TtsAdapter", "after engine destroy");
        this.b.f();
        LoggerProxy.d("TtsAdapter", "after player destroy");
    }

    public void a(TtsListener ttsListener) {
        this.c = ttsListener;
        this.a(this.a);
        this.a(this.b);
    }

    public void a(I i2) {
        this.a.a(i2);
    }

    public void b(I i2) {
        this.b.o();
        this.a.a(i2);
    }

    protected void a(D d2) {
        if (this.d == null) {
            this.d = new com.baidu.tts.b.a.B(){

                public void onSynthesizeStart(H h2) {
                    if (B.this.c != null) {
                        B.this.c.onSynthesizeStart(h2);
                    }
                }

                public void onSynthesizeFinished(H h2) {
                    if (B.this.c != null) {
                        B.this.c.onSynthesizeFinished(h2);
                    }
                }

                public void onSynthesizeDataArrived(H h2) {
                    if (B.this.c != null) {
                        B.this.c.onSynthesizeDataArrived(h2);
                    }
                }

                public void onError(H h2) {
                    if (B.this.c != null) {
                        B.this.c.onError(h2);
                    }
                }

                public void e(H h2) {
                    LoggerProxy.d("TtsAdapter", "onSynthesizeStop");
                }
            };
        }
        d2.a(this.d);
    }

    protected void a(C c2) {
        if (this.e == null) {
            this.e = new com.baidu.tts.b.b.A(){

                public void a(H h2) {
                    if (B.this.c != null) {
                        B.this.c.onPlayStart(h2);
                    }
                }

                public void b(H h2) {
                    if (B.this.c != null) {
                        B.this.c.onPlayProgressUpdate(h2);
                    }
                }

                public void c(H h2) {
                    if (B.this.c != null) {
                        try {
                            B.this.c.onPlayFinished(h2);
                        }
                        catch (Exception exception) {
                            Log.e((String)"TtsAdapter", (String)("onPlayFinished exception e=" + exception.toString()));
                        }
                    }
                }
            };
        }
        c2.a(this.e);
    }

    protected void g() {
        this.f = new com.baidu.tts.b.a.B(){

            public void onSynthesizeStart(H h2) {
                if (B.this.a(h2)) {
                    B.this.b.a(h2);
                }
            }

            public void onSynthesizeFinished(H h2) {
                if (B.this.a(h2)) {
                    B.this.b.a(h2);
                }
            }

            public void onSynthesizeDataArrived(H h2) {
                if (B.this.a(h2)) {
                    B.this.b.a(h2);
                }
            }

            public void onError(H h2) {
            }

            public void e(H h2) {
            }
        };
        this.a.a(this.f);
    }

    private boolean a(H h2) {
        I i2 = h2.e();
        if (i2 == null) {
            return false;
        }
        com.baidu.tts.f.I i3 = i2.g();
        return com.baidu.tts.f.I.a(i3);
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
        return this.b.a(f2, f3);
    }

    public C a() {
        return this.b;
    }

}


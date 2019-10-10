/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.b.a.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.b.a.B;
import com.baidu.tts.m.E;
import com.baidu.tts.m.F;
import com.baidu.tts.m.G;
import com.baidu.tts.m.H;
import com.baidu.tts.m.I;
import java.util.ArrayList;
import java.util.List;

public abstract class A
extends com.baidu.tts.j.A
implements D {
    protected com.baidu.tts.b.a.b.B a;
    protected List<B> b = new ArrayList<B>();
    protected volatile com.baidu.tts.b.a.a.B c;

    public com.baidu.tts.b.a.a.B a() {
        return this.c;
    }

    public void a(com.baidu.tts.b.a.a.B b2) {
        this.c = b2;
    }

    protected TtsError g() {
        return this.c.b();
    }

    protected void h() {
        this.c.a();
    }

    protected void i() {
        this.c.c();
    }

    protected void j() {
        this.c.d();
    }

    protected void k() {
        this.c.e();
    }

    protected void l() {
        this.c.f();
    }

    public void a(com.baidu.tts.b.a.b.B b2) {
        this.c.a(b2);
    }

    public void a(B b2) {
        this.c.a(b2);
    }

    public void a(Object object) {
        this.c.a(object);
    }

    public void a(I i2) {
        this.c.a(i2);
    }

    public int a(E e2) {
        return this.c.a(e2);
    }

    public int b(E e2) {
        return this.c.b(e2);
    }

    public int a(G g2) {
        return this.c.a(g2);
    }

    public int a(F f2) {
        return this.c.a(f2);
    }

    void a(H h2) {
        if (this.C()) {
            if (h2 == null) {
                h2 = new H();
            }
            h2.a(com.baidu.tts.f.E.a);
            if (this.b != null) {
                for (B b2 : this.b) {
                    if (b2 == null) continue;
                    b2.onSynthesizeStart(h2);
                }
            }
        }
    }

    void b(H h2) {
        if (this.C()) {
            if (h2 == null) {
                h2 = new H();
            }
            h2.a(com.baidu.tts.f.E.c);
            if (this.b != null) {
                for (B b2 : this.b) {
                    if (b2 == null) continue;
                    b2.onSynthesizeDataArrived(h2);
                }
            }
        }
    }

    void c(H h2) {
        if (this.C()) {
            if (h2 == null) {
                h2 = new H();
            }
            h2.a(com.baidu.tts.f.E.b);
            if (this.b != null) {
                for (B b2 : this.b) {
                    if (b2 == null) continue;
                    b2.onSynthesizeFinished(h2);
                }
            }
        }
    }

    void d(H h2) {
        if (h2 == null) {
            h2 = new H();
        }
        h2.a(com.baidu.tts.f.E.d);
        if (this.b != null) {
            for (B b2 : this.b) {
                if (b2 == null) continue;
                b2.e(h2);
            }
        }
    }

    void e(H h2) {
        if (h2 == null) {
            h2 = new H();
        }
        h2.a(com.baidu.tts.f.E.g);
        if (this.b != null) {
            for (B b2 : this.b) {
                if (b2 == null) continue;
                b2.onError(h2);
            }
        }
    }
}


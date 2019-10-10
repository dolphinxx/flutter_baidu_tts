/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.b.b.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.b.b.a.B;
import com.baidu.tts.b.b.a.D;
import com.baidu.tts.b.b.a.E;
import com.baidu.tts.b.b.a.G;
import com.baidu.tts.b.b.a.H;
//import com.baidu.tts.b.b.b.B;
import com.baidu.tts.b.b.b.C;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.L;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class F
extends com.baidu.tts.b.b.a.A {
    private ThreadPoolExecutor c;
    private C f;
    private H g = new H(this);
    private D h = new D(this);
    private G i = new G(this);
    private E j = new E(this);

    public F() {
        this.b = this.g;
        this.f = com.baidu.tts.b.b.B.a().b();
    }

    @Override
    public boolean m() {
        return this.b == this.j;
    }

    @Override
    public boolean n() {
        return Thread.currentThread().isInterrupted() || this.b == this.h;
    }

    public H p() {
        return this.g;
    }

    public D q() {
        return this.h;
    }

    public G r() {
        return this.i;
    }

    public E s() {
        return this.j;
    }

    TtsError t() {
        this.f.a(new com.baidu.tts.b.b.A(){

            public void a(com.baidu.tts.m.H h2) {
                F.this.b(h2);
            }

            public void b(com.baidu.tts.m.H h2) {
                F.this.c(h2);
            }

            public void c(com.baidu.tts.m.H h2) {
                F.this.d(h2);
            }
        });
        return this.f.a();
    }

    void u() {
        this.c = new com.baidu.tts.c.A(200, "PlayExecutorPoolThread");
    }

    void v() {
        this.f.d();
    }

    void w() {
        this.f.c();
    }

    void x() {
        this.f.e();
        if (this.c != null) {
            if (!this.c.isShutdown()) {
                this.c.shutdownNow();
            }
            try {
                LoggerProxy.d("PlayQueueMachine", "before await");
                boolean bl = this.c.awaitTermination(L.a.a(), TimeUnit.MILLISECONDS);
                LoggerProxy.d("PlayQueueMachine", "after await isTer=" + bl);
            }
            catch (InterruptedException interruptedException) {
                LoggerProxy.d("PlayQueueMachine", "InterruptedException");
            }
            this.c = null;
        }
    }

    void y() {
        this.f.f();
    }

    void e(com.baidu.tts.m.H h2) {
        this.c.execute(new a(h2));
    }

    void z() {
        this.f.b();
    }

    <T> void b(T t) {
        com.baidu.tts.m.A a2 = (com.baidu.tts.m.A)t;
        com.baidu.tts.b.b.b.B.a a3 = a2.a();
        this.f.a(a3);
    }

    void b(com.baidu.tts.b.b.A a2) {
        this.a = a2;
    }

    int b(float f2, float f3) {
        return this.f.a(f2, f3);
    }

    int c(int n2) {
        return this.f.a(n2);
    }

    int d(int n2) {
        return this.f.b(n2);
    }

    private class a
    implements Runnable {
        private com.baidu.tts.m.H b;

        public a(com.baidu.tts.m.H h2) {
            this.b = h2;
        }

        public void run() {
            LoggerProxy.d("PlayQueueMachine", "enter run");
            F.this.f.a(this.b);
            LoggerProxy.d("PlayQueueMachine", "end run");
        }
    }

}


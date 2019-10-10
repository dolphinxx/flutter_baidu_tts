/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.b.a.a;

import android.util.Log;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.b.a.a.B;
import com.baidu.tts.b.a.a.E;
import com.baidu.tts.b.a.a.F;
import com.baidu.tts.b.a.a.G;
import com.baidu.tts.b.a.a.H;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.N;
import com.baidu.tts.m.I;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class C
extends com.baidu.tts.b.a.a.A {
    private ExecutorService f;
    private H g = new H(this);
    private E h = new E(this);
    private G i = new G(this);
    private F j = new F(this);

    public C() {
        this.c = this.g;
    }

    @Override
    public boolean m() {
        return this.c == this.j;
    }

    @Override
    public boolean n() {
        return Thread.currentThread().isInterrupted() || this.c == this.h;
    }

    public H o() {
        return this.g;
    }

    public E p() {
        return this.h;
    }

    public G q() {
        return this.i;
    }

    public F r() {
        return this.j;
    }

    TtsError s() {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        a.a(new com.baidu.tts.b.a.B(){

            public void e(com.baidu.tts.m.H h2) {
            }

            public void onSynthesizeStart(com.baidu.tts.m.H h2) {
            }

            public void onSynthesizeFinished(com.baidu.tts.m.H h2) {
            }

            public void onSynthesizeDataArrived(com.baidu.tts.m.H h2) {
                C.this.b(h2);
            }

            public void onError(com.baidu.tts.m.H h2) {
            }
        });
        return a.a();
    }

    void t() {
        this.f = Executors.newSingleThreadExecutor(new com.baidu.tts.g.a.A("EngineExecutorPoolThread"));
    }

    void u() {
    }

    void v() {
    }

    void w() {
        if (this.f != null) {
            if (!this.f.isShutdown()) {
                this.f.shutdownNow();
            }
            try {
                LoggerProxy.d("EngineExecutor", "before awaitTermination");
                boolean bl = this.f.awaitTermination(1000L, TimeUnit.MILLISECONDS);
                LoggerProxy.d("EngineExecutor", "after awaitTermination isTermination=" + bl);
                this.a(bl);
            }
            catch (InterruptedException interruptedException) {
                this.a(false);
            }
            this.f = null;
        }
    }

    void x() {
        a.b();
        this.b = null;
    }

    int b(com.baidu.tts.m.G g2) {
        return a.a(g2);
    }

    int b(com.baidu.tts.m.F f2) {
        return a.a(f2);
    }

    int c(com.baidu.tts.m.E e2) {
        return a.a(e2);
    }

    int d(com.baidu.tts.m.E e2) {
        return a.b(e2);
    }

    void b(com.baidu.tts.b.a.b.B b2) {
        this.a = b2;
    }

    void b(com.baidu.tts.b.a.B b2) {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        if (!this.b.contains(b2)) {
            this.b.add(b2);
        }
    }

    <T> void b(T t) {
        a.a(t);
    }

    void b(I i2) {
        this.f.submit(new a(i2));
    }

    private void a(boolean bl) {
        if (bl) {
            // FIXME: really com.baidu.tts.m.E?
            this.d((com.baidu.tts.m.E)null);
        } else {
            com.baidu.tts.m.H h2 = new com.baidu.tts.m.H();
            TtsError ttsError = com.baidu.tts.h.a.C.a().b(N.W);
            h2.a(ttsError);
            this.d(h2);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class a
    implements Callable<Void> {
        private I b;

        public a(I i2) {
            this.b = i2;
        }

        public Void a() throws Exception {
            try {
                C.this.a(com.baidu.tts.m.H.b(this.b));
                TtsError ttsError = a.a(this.b);
                if (ttsError == null) {
                    C.this.c(com.baidu.tts.m.H.b(this.b));
                } else {
                    C.this.e(com.baidu.tts.m.H.a(this.b, ttsError));
                }
                return null;
            }
            catch (InterruptedException interruptedException) {
                Log.e("---->", "synthesise interrupted", interruptedException);
                return null;
            } catch(Exception e) {
                Log.e("----", "b.a.a.C.a.a()", e);
                throw new RuntimeException(e);
            }
        }

        @Override
        public /* synthetic */ Void call() throws Exception {
            return this.a();
        }
    }

}


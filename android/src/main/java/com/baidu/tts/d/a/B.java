/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 */
package com.baidu.tts.d.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.model.BasicHandler;
import com.baidu.tts.client.model.ModelFileBags;
import com.baidu.tts.d.a.C;
import com.baidu.tts.d.a.D;
import com.baidu.tts.d.a.E;
import com.baidu.tts.d.a.F;
import com.baidu.tts.d.a.G;
import com.baidu.tts.d.a.H;
import com.baidu.tts.d.a.I;
import com.baidu.tts.f.L;
import com.baidu.tts.f.N;
import com.baidu.tts.loopj.RequestHandle;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.SyncHttpClient;
import com.baidu.tts.tools.FileTools;
import com.baidu.tts.tools.StringTool;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.http.Header;

public class B
extends com.baidu.tts.j.A {
    private volatile com.baidu.tts.d.a.A a = this.b;
    private I b = new I(this);
    private F c = new F(this);
    private D f = new D(this);
    private H g = new H(this);
    private ThreadPoolExecutor h;
    private com.baidu.tts.l.A i;

    public B() {
        this.b();
    }

    public void a(com.baidu.tts.l.A a2) {
        this.i = a2;
    }

    public com.baidu.tts.d.a.A a() {
        return this.a;
    }

    public void a(com.baidu.tts.d.a.A a2) {
        this.a = a2;
    }

    public I o() {
        return this.b;
    }

    public F p() {
        return this.c;
    }

    public D q() {
        return this.f;
    }

    public H r() {
        return this.g;
    }

    protected TtsError g() {
        return this.a.b();
    }

    protected void h() {
        this.a.a();
    }

    protected void i() {
        this.a.c();
    }

    protected void j() {
        this.a.d();
    }

    protected void k() {
        this.a.e();
    }

    protected void l() {
        this.a.f();
    }

    public boolean m() {
        return this.a == this.g;
    }

    public boolean n() {
        return Thread.currentThread().isInterrupted() || this.a == this.c;
    }

    public E a(C c2) {
        return this.a.a(c2);
    }

    void s() {
        this.h = (ThreadPoolExecutor)Executors.newFixedThreadPool(5, new com.baidu.tts.g.a.A("downloadPoolThread"));
    }

    void t() {
        LoggerProxy.d("DownloadEngine", "enter stop");
        if (this.h != null) {
            if (!this.h.isShutdown()) {
                this.h.shutdownNow();
            }
            try {
                LoggerProxy.d("DownloadEngine", "before awaitTermination");
                boolean bl = this.h.awaitTermination(L.a.a(), TimeUnit.MILLISECONDS);
                LoggerProxy.d("DownloadEngine", "after awaitTermination isTermination=" + bl);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            this.h = null;
        }
        LoggerProxy.d("DownloadEngine", "end stop");
    }

    E b(C c2) {
        a a2 = new a(c2);
        c2.c();
        LoggerProxy.d("DownloadEngine", "before submit");
        Future<Void> future = null;
        try {
            future = this.h.submit(a2);
        }
        catch (Exception exception) {
            LoggerProxy.d("DownloadEngine", "submit exception");
            TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.ak, exception);
            c2.a(ttsError);
        }
        E e2 = new E();
        e2.a(future);
        e2.a(a2);
        return e2;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public class a
    implements Callable<Void> {
        private C b;
        private SyncHttpClient c;

        public a(C c2) {
            this.b = c2;
        }

        public Void a() throws Exception {
            this.b.d();
            final String string = this.b.a();
            LoggerProxy.d("DownloadEngine", "DownloadWork start fileId=" + string);
            if (StringTool.isEmpty(string)) {
                TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.ac, "fileId is null");
                this.b.a(ttsError);
            } else {
                HashSet<String> hashSet = new HashSet<String>();
                hashSet.add(string);
                BasicHandler<ModelFileBags> basicHandler = B.this.i.a(hashSet);
                ModelFileBags modelFileBags = basicHandler.get();
                if (modelFileBags != null) {
                    String string2 = modelFileBags.getUrl(0);
                    if (string2 != null) {
                        this.c = string2.startsWith("https") ? new SyncHttpClient(true, 80, 443) : new SyncHttpClient();
                        this.c.setURLEncodingEnabled(false);
                        this.c.setTimeout(L.a.b());
                        this.c.setMaxRetriesAndTimeout(5, 1500);
                        String string3 = this.b.b();
                        File file = FileTools.getFile(string3);
                        G g2 = new G(file, this.b){

                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                                LoggerProxy.d("DownloadEngine", "1isInterrupted=" + Thread.currentThread().isInterrupted());
                                if (B.this.C()) {
                                    super.onFailure(statusCode, headers, throwable, file);
                                }
                            }

                            public void onSuccess(int statusCode, Header[] headers, File file) {
                                LoggerProxy.d("DownloadEngine", "2isInterrupted=" + Thread.currentThread().isInterrupted() + "--fileId=" + string);
                                if (B.this.C()) {
                                    super.onSuccess(statusCode, headers, file);
                                }
                            }

                            public void onProgress(long bytesWritten, long totalSize) {
                                if (B.this.C()) {
                                    super.onProgress(bytesWritten, totalSize);
                                }
                            }
                        };
                        g2.setUseSynchronousMode(true);
                        LoggerProxy.d("DownloadEngine", "before get fileId=" + string);
                        this.c.get(string2, g2);
                    } else {
                        TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.ac, "url is null");
                        this.b.a(ttsError);
                    }
                } else {
                    TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.ac, "urlbags is null");
                    this.b.a(ttsError);
                }
            }
            LoggerProxy.d("DownloadEngine", "DownloadWork end");
            return null;
        }

        public void b() {
            if (this.c != null) {
                this.c.stop();
            }
        }

        public C c() {
            return this.b;
        }

        @Override
        public /* synthetic */ Void call() throws Exception {
            return this.a();
        }

    }

}


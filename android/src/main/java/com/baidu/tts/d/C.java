/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.d;

import android.content.Context;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.model.BasicHandler;
import com.baidu.tts.client.model.Conditions;
import com.baidu.tts.client.model.DownloadHandler;
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.client.model.ModelFileBags;
import com.baidu.tts.d.a.E;
import com.baidu.tts.d.b.B;
import com.baidu.tts.d.b.D;
import com.baidu.tts.f.L;
import com.baidu.tts.f.N;
import com.baidu.tts.tools.DataTool;
import com.baidu.tts.tools.StringTool;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class C
implements com.baidu.tts.j.B {
    private static volatile C a = null;
    private com.baidu.tts.l.A b;
    private com.baidu.tts.d.b.A c = com.baidu.tts.d.b.A.a();
    private com.baidu.tts.d.a.B d = new com.baidu.tts.d.a.B();
    private ExecutorService e;

    private C() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static C a() {
        if (a != null) return a;
        Class<C> class_ = C.class;
        synchronized (C.class) {
            if (a != null) return a;
            {
                a = new C();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return a;
        }
    }

    private synchronized ExecutorService h() {
        if (this.e == null) {
            this.e = Executors.newSingleThreadExecutor();
        }
        return this.e;
    }

    public synchronized TtsError b() {
        return null;
    }

    public synchronized void g() {
        this.h();
        this.d.A();
    }

    public synchronized void c() {
        this.d.c();
    }

    public synchronized void d() {
        this.d.d();
    }

    public synchronized void e() {
        LoggerProxy.d("Downloader", "enter stop");
        this.c.c();
        if (this.e != null) {
            if (!this.e.isShutdown()) {
                this.e.shutdownNow();
                this.d.e();
                LoggerProxy.d("Downloader", "after engine stop");
            }
            try {
                LoggerProxy.d("Downloader", "before awaitTermination");
                boolean bl = this.e.awaitTermination(L.a.a(), TimeUnit.MILLISECONDS);
                LoggerProxy.d("Downloader", "after awaitTermination isTermination=" + bl);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            this.e = null;
        }
        LoggerProxy.d("Downloader", "end stop");
    }

    public synchronized void f() {
    }

    public void a(com.baidu.tts.l.A a2) {
        this.b = a2;
        this.c.a(this.b.e());
        this.d.a(this.b);
    }

    public synchronized DownloadHandler a(DownloadHandler downloadHandler) {
        LoggerProxy.d("Downloader", "download handler=" + downloadHandler);
        a a2 = new a(downloadHandler);
        Future<A> future = this.h().submit(a2);
        downloadHandler.setCheckFuture(future);
        return downloadHandler;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public class a
    implements Callable<A> {
        private DownloadHandler b;

        public a(DownloadHandler downloadHandler) {
            this.b = downloadHandler;
        }

        public A a() throws Exception {
            A a2 = new A();
            com.baidu.tts.database.A a3 = C.this.b.e();
            String string = this.b.getModelId();
            D d2 = C.this.c.b(string);
            try {
                Object object;
                Conditions object2;
                Object object3;
                Object object4;
                Object object5;
                d2.c(this.b);
                boolean bl = d2.a(a3);
                if (!bl) {
                    object2 = new Conditions();
                    object2.appendId(string);
                    object3 = C.this.b.a(object2);
                    ModelBags set = (ModelBags)((BasicHandler)object3).get();
                    if (set == null || ((ModelBags)((Object)set)).isEmpty()) {
                        TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.ah, "modelId=" + string);
                        d2.a(this.b, ttsError);
                        return a2;
                    }
                    d2.a((ModelBags)((Object)set), a3);
                }
                Set<String> object22 = d2.f();
                if (DataTool.isSetEmpty(object22)) {
                    object3 = com.baidu.tts.h.a.C.a().a(N.ae, "modelId=" + string);
                    d2.a(this.b, (TtsError)object3);
                    return a2;
                }
                Iterator<String> object33 = object22.iterator();
                while (object33.hasNext()) {
                    String ss = object33.next();
                    object4 = C.this.c.c(ss);
                    boolean bl2 = ((com.baidu.tts.d.b.C)object4).a(a3);
                    if (bl2) continue;
                    Set<String> object55 = new HashSet<>();
                    object55.add(ss);
                    BasicHandler<ModelFileBags> basicHandler = C.this.b.a(object55);
                    object = basicHandler.get();
                    if (object == null || ((ModelFileBags)object).isEmpty()) {
                        TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.ai, "fileId=" + ss);
                        d2.a(this.b, ttsError);
                        return a2;
                    }
                    ((ModelFileBags)object).generateAbsPath(C.this.b.d());
                    ((com.baidu.tts.d.b.C)object4).a((ModelFileBags)object, a3);
                }
                d2.d();
                Set<String> set = d2.b();
                if (DataTool.isSetEmpty(set)) {
                    object4 = com.baidu.tts.h.a.C.a().a(N.af, "modelId=" + string);
                    d2.a(this.b, (TtsError)object4);
                    return a2;
                }
                for (String string2 : set) {
                    if (StringTool.isEmpty(string2)) continue;
                    object5 = C.this.c.a(string2);
                    ((B)object5).a(string);
                    boolean bl3 = ((B)object5).a(a3);
                    object = ((B)object5).c();
                    LoggerProxy.d("Downloader", "isNeedDownload=" + bl3 + "--fileId=" + (String)object);
                    if (bl3) {
                        boolean bl4 = ((B)object5).e();
                        if (bl4) {
                            ((B)object5).f();
                        }
                        com.baidu.tts.d.a.C c2 = new com.baidu.tts.d.a.C();
                        c2.a((B)object5);
                        if (Thread.currentThread().isInterrupted()) {
                            return null;
                        }
                        LoggerProxy.d("Downloader", "before download fileId=" + (String)object);
                        E e2 = C.this.d.a(c2);
                        ((B)object5).a(e2);
                        a2.a(true);
                        continue;
                    }
                    a2.a(string2, ((B)object5).d());
                }
                if (!a2.a() && a2.b()) {
                    this.b.updateProgress(d2);
                    TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.ag, "modelId=" + string);
                    d2.a(this.b, ttsError);
                }
                return a2;
            }
            catch (Exception exception) {
                LoggerProxy.d("Downloader", "exception=" + exception.toString());
                TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.aj, "modelId=" + string);
                d2.a(this.b, ttsError);
                return a2;
            }
        }

        @Override
        public /* synthetic */ A call() throws Exception {
            return this.a();
        }
    }

}


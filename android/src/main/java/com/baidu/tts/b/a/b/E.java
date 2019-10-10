/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package com.baidu.tts.b.a.b;

import android.content.Context;
import android.text.TextUtils;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.auth.B;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.D;
import com.baidu.tts.f.N;
import com.baidu.tts.jni.EmbeddedSynthesizerEngine;
import com.baidu.tts.m.F;
import com.baidu.tts.m.G;
import com.baidu.tts.m.H;
import com.baidu.tts.m.I;
import com.baidu.tts.tools.DataTool;
import com.baidu.tts.tools.ResourceTools;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class E
extends com.baidu.tts.b.a.b.A {
    private b b;
    private long[] c = new long[1];
    private com.baidu.tts.e.C d;

    public <OfflineSynthesizerParams> void a(OfflineSynthesizerParams OfflineSynthesizerParams) {
        this.b = (b)OfflineSynthesizerParams;
    }

    @Override
    public TtsError a() {
        B.a a2;
        try {
            this.d = com.baidu.tts.e.C.a(com.baidu.tts.h.b.B.a().h());
        }
        catch (Exception exception) {
            LoggerProxy.d("OfflineSynthesizer", "embedded statistics open exception=" + exception.toString());
        }
        if (this.b == null) {
            this.b = new b();
        }
        if ((a2 = com.baidu.tts.auth.A.a().a(this.b)).g()) {
            String string = this.b.d();
            String string2 = this.b.e();
            byte[] arrby = ResourceTools.stringToByteArrayAddNull(string);
            byte[] arrby2 = ResourceTools.stringToByteArrayAddNull(string2);
            LoggerProxy.d("OfflineSynthesizer", "before bdTTSEngineInit");
            int n2 = EmbeddedSynthesizerEngine.bdTTSEngineInit(arrby, arrby2, this.c);
            LoggerProxy.d("OfflineSynthesizer", "engine init ret = " + n2);
            if (n2 == 0) {
                return null;
            }
            return com.baidu.tts.h.a.C.a().a(N.y, n2, "bdTTSEngineInit result not 0");
        }
        return a2.b();
    }

    @Override
    public TtsError b() {
        EmbeddedSynthesizerEngine.bdTTSEngineUninit(this.c[0]);
        return null;
    }

    @Override
    public TtsError a(I i2) throws InterruptedException {
        try {
            c c2 = new c(i2);
            return c2.a();
        }
        catch (InterruptedException interruptedException) {
            throw interruptedException;
        }
        catch (Exception exception) {
            return com.baidu.tts.h.a.C.a().a(N.A, exception);
        }
    }

    @Override
    public int a(com.baidu.tts.m.E e2) {
        byte[] arrby = ResourceTools.stringToByteArrayAddNull(e2.a());
        return EmbeddedSynthesizerEngine.bdTTSDomainDataInit(arrby, this.c[0]);
    }

    @Override
    public int b(com.baidu.tts.m.E e2) {
        return EmbeddedSynthesizerEngine.bdTTSDomainDataUninit(this.c[0]);
    }

    @Override
    public int a(G g2) {
        String string = g2.b();
        String string2 = g2.a();
        boolean bl = TextUtils.isEmpty((CharSequence)string);
        boolean bl2 = TextUtils.isEmpty((CharSequence)string2);
        if (bl && bl2) {
            return N.Y.b();
        }
        int n2 = 0;
        if (!bl2) {
            byte[] arrby = ResourceTools.stringToByteArrayAddNull(string2);
            n2 = EmbeddedSynthesizerEngine.bdTTSReInitData(arrby, this.c[0]);
        }
        int n3 = 0;
        if (!bl) {
            byte[] arrby = ResourceTools.stringToByteArrayAddNull(string);
            n3 = EmbeddedSynthesizerEngine.bdTTSReInitData(arrby, this.c[0]);
        }
        return n2 + n3;
    }

    @Override
    public int a(F f2) {
        String string = f2.b();
        String string2 = f2.a();
        boolean bl = TextUtils.isEmpty((CharSequence)string);
        boolean bl2 = TextUtils.isEmpty((CharSequence)string2);
        if (bl2 || bl) {
            return N.Y.b();
        }
        byte[] arrby = ResourceTools.stringToByteArrayAddNull(string2);
        byte[] arrby2 = ResourceTools.stringToByteArrayAddNull(string);
        int n2 = EmbeddedSynthesizerEngine.loadEnglishEngine(arrby, arrby2, this.c[0]);
        LoggerProxy.d("OfflineSynthesizer", "loadEnglishModel ret=" + n2);
        return n2;
    }

    private class a
    implements Runnable {
        private int b;

        public a(int n2) {
            this.b = n2;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void run() {
            try {
                com.baidu.tts.e.C c2 = E.this.d;
                synchronized (c2) {
                    if (E.this.d != null) {
                        E.this.d.a(System.currentTimeMillis(), this.b, 0, 0, "");
                    }
                }
            }
            catch (Exception exception) {
                LoggerProxy.d("OfflineSynthesizer", "AddPVResultsToDB exception=" + exception.toString());
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class c
    implements EmbeddedSynthesizerEngine.OnNewDataListener,
    Callable<TtsError> {
        private I c;
        private int d = 0;
        ExecutorService a = Executors.newCachedThreadPool();

        public c(I i2) {
            this.c = i2;
        }

        @Override
        public int onNewData(byte[] audioData, int progress) {
            H h2 = H.b(this.c);
            h2.e(com.baidu.tts.f.F.b.a());
            h2.a(com.baidu.tts.f.A.a);
            h2.a(audioData);
            h2.d(progress);
            ++this.d;
            h2.b(this.d);
            E.this.a(h2);
            if (Thread.currentThread().isInterrupted()) {
                LoggerProxy.d("OfflineSynthesizer", "interrupted to interrupt syn");
                return -1;
            }
            return 0;
        }

        public TtsError a() throws Exception {
            B.a a2 = com.baidu.tts.auth.A.a().a(E.this.b);
            if (a2 != null) {
                if (a2.g()) {
                    String string = E.this.b.D();
                    float f2 = 0.0f;
                    long l2 = 0L;
                    if (string.matches("\\d+(.\\d+)?")) {
                        if (string.indexOf(".") > 0) {
                            f2 = Float.parseFloat(string);
                            EmbeddedSynthesizerEngine.bdTTSSetParamFloat(E.this.c[0], 5, f2);
                        } else {
                            l2 = Long.parseLong(string);
                            EmbeddedSynthesizerEngine.bdTTSSetParam(E.this.c[0], 5, l2);
                        }
                    }
                    String string2 = E.this.b.B();
                    float f3 = 0.0f;
                    long l3 = 0L;
                    if (string2.matches("\\d+(.\\d+)?")) {
                        if (string2.indexOf(".") > 0) {
                            f3 = Float.parseFloat(string2);
                            EmbeddedSynthesizerEngine.bdTTSSetParamFloat(E.this.c[0], 6, f3);
                        } else {
                            l3 = Long.parseLong(string2);
                            EmbeddedSynthesizerEngine.bdTTSSetParam(E.this.c[0], 6, l3);
                        }
                    }
                    String string3 = E.this.b.C();
                    float f4 = 0.0f;
                    long l4 = 0L;
                    if (string3.matches("\\d+(.\\d+)?")) {
                        if (string3.indexOf(".") > 0) {
                            f4 = Float.parseFloat(string3);
                            EmbeddedSynthesizerEngine.bdTTSSetParamFloat(E.this.c[0], 7, f4);
                        } else {
                            l4 = Long.parseLong(string3);
                            EmbeddedSynthesizerEngine.bdTTSSetParam(E.this.c[0], 7, l4);
                        }
                    }
                    String string4 = E.this.b.h();
                    float f5 = 0.0f;
                    long l5 = 0L;
                    if (string4.matches("\\d+(.\\d+)?")) {
                        if (string4.indexOf(".") > 0) {
                            f5 = Float.parseFloat(string4);
                            EmbeddedSynthesizerEngine.bdTTSSetParamFloat(E.this.c[0], 8, f5);
                        } else {
                            l5 = Long.parseLong(string4);
                            EmbeddedSynthesizerEngine.bdTTSSetParam(E.this.c[0], 8, l5);
                        }
                    }
                    int n2 = EmbeddedSynthesizerEngine.bdTTSSetParam(E.this.c[0], 0, 0L);
                    LoggerProxy.d("OfflineSynthesizer", "engineResult = " + n2);
                    EmbeddedSynthesizerEngine.bdTTSSetParam(E.this.c[0], 18, E.this.b.a());
                    EmbeddedSynthesizerEngine.bdTTSSetParam(E.this.c[0], 19, E.this.b.b());
                    EmbeddedSynthesizerEngine.bdTTSSetParam(E.this.c[0], 20, E.this.b.c());
                    EmbeddedSynthesizerEngine.bdTTSSetParam(E.this.c[0], 10, E.this.b.A());
                    EmbeddedSynthesizerEngine.setOnNewDataListener(this);
                    this.c.c(D.d.a());
                    byte[] arrby = this.c.e();
                    LoggerProxy.d("OfflineSynthesizer", "before bdttssynthesis");
                    int n3 = EmbeddedSynthesizerEngine.bdTTSSynthesis(E.this.c[0], arrby, arrby.length);
                    LoggerProxy.d("OfflineSynthesizer", "after bdttssynthesis result = " + n3);
                    try {
                        this.a.execute(new a(n3));
                    }
                    catch (Exception exception) {
                        LoggerProxy.d("OfflineSynthesizer", "AddPVResultsToDB start exception=" + exception.toString());
                    }
                    if (n3 == 0) {
                        return null;
                    }
                    return com.baidu.tts.h.a.C.a().a(N.B, n3);
                }
                return a2.b();
            }
            return com.baidu.tts.h.a.C.a().b(N.u);
        }

        @Override
        public /* synthetic */ TtsError call() throws Exception {
            return this.a();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class b
    extends com.baidu.tts.m.D<b> {
        private String a = "0";
        private String b = "0";
        private String c = "0";
        private String d;
        private String e;
        private String f;
        private String g;
        private String h = "5";

        public int a(String string) {
            if (DataTool.isLong(string)) {
                this.a = string;
                return 0;
            }
            return N.Y.b();
        }

        public void b(String string) {
            this.b = string;
        }

        public int c(String string) {
            if (DataTool.isLong(string)) {
                this.c = string;
                return 0;
            }
            return N.Y.b();
        }

        public long a() {
            long l2 = 0L;
            try {
                l2 = Long.parseLong(this.a);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return l2;
        }

        public long b() {
            long l2 = 0L;
            try {
                l2 = Long.parseLong(this.b);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return l2;
        }

        public long c() {
            long l2 = 0L;
            try {
                l2 = Long.parseLong(this.c);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return l2;
        }

        public String d() {
            return this.d;
        }

        public void d(String string) {
            this.d = string;
        }

        public String e() {
            return this.e;
        }

        public void e(String string) {
            this.e = string;
        }

        public String f() {
            return this.f;
        }

        public void f(String string) {
            this.f = string;
        }

        public String g() {
            return this.g;
        }

        public void g(String string) {
            this.g = string;
        }

        public void h(String string) {
            this.h = string;
        }

        public String h() {
            return this.h;
        }
    }

}


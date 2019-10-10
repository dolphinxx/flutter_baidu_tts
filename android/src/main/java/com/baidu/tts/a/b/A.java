/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.baidu.tts.a.b;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.tts.aop.tts.ITts;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.aop.tts.TtsFactory;
import com.baidu.tts.aop.ttslistener.TtsListener;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.baidu.tts.f.G;
import com.baidu.tts.f.I;
import com.baidu.tts.f.L;
import com.baidu.tts.f.M;
import com.baidu.tts.f.N;
import com.baidu.tts.m.E;
import com.baidu.tts.m.F;
import com.baidu.tts.m.H;
import com.baidu.tts.o.a.C;
import com.baidu.tts.tools.ResourceTools;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class A {
    /**
     * {@link C}
     */
    private ITts a = this.g();
    private SpeechSynthesizerListener b;
    private ThreadPoolExecutor c;
    private TtsListener d = new TtsListener(){

        public void onSynthesizeStart(H responseBag) {
            if (A.this.b != null) {
                String string = A.this.a(responseBag);
                A.this.b.onSynthesizeStart(string);
            }
        }

        public void onSynthesizeDataArrived(H responseBag) {
            if (A.this.b != null) {
                String string = A.this.a(responseBag);
                byte[] arrby = responseBag.d();
                int n2 = responseBag.c();
                A.this.b.onSynthesizeDataArrived(string, arrby, n2);
            }
        }

        public void onSynthesizeFinished(H responseBag) {
            if (A.this.b != null) {
                String string = A.this.a(responseBag);
                A.this.b.onSynthesizeFinish(string);
            }
        }

        public void onPlayStart(H responseBag) {
            if (A.this.b != null) {
                String string = A.this.a(responseBag);
                A.this.b.onSpeechStart(string);
            }
        }

        public void onPlayProgressUpdate(H responseBag) {
            if (A.this.b != null) {
                String string = A.this.a(responseBag);
                int n2 = responseBag.c();
                A.this.b.onSpeechProgressChanged(string, n2);
            }
        }

        public void onPlayFinished(H responseBag) {
            if (A.this.b != null) {
                String string = A.this.a(responseBag);
                A.this.b.onSpeechFinish(string);
            }
        }

        public void onError(H responseBag) {
            if (A.this.b != null && !this.a(responseBag)) {
                String string = A.this.a(responseBag);
                SpeechError speechError = A.this.b(responseBag);
                A.this.b.onError(string, speechError);
            }
        }

        private boolean a(H h2) {
            boolean bl = false;
            try {
                TtsError ttsError = h2.f();
                N n2 = ttsError.getTtsErrorFlyweight().a();
                switch (n2) {
                    case K: 
                    case v: 
                    case d: {
                        bl = true;
                        break;
                    }
                }
            }
            catch (Exception exception) {
                LoggerProxy.d("SpeechSynthesizerAdapter", "isStopped exception=" + exception.toString());
            }
            return bl;
        }
    };

    public A() {
        this.a.setTtsListener(this.d);
    }

    private ITts g() {
//        return new C();
        TtsFactory ttsFactory = new TtsFactory();
        return ttsFactory.makeProxy();
    }

    public void a(SpeechSynthesizerListener speechSynthesizerListener) {
        if (this.b != speechSynthesizerListener) {
            this.b = speechSynthesizerListener;
        }
    }

    public void a(Context context) {
        this.a.setContext(context);
    }

    public TtsError a(TtsMode ttsMode) {
        this.a.setMode(ttsMode.getTtsEnum());
        return this.a.b();
    }

    public String a() {
        return com.baidu.tts.h.b.B.a().j();
    }

    public int a(String string, String string2) {
        try {
            G g2 = G.valueOf(string);
            return this.a.setParam(g2, string2);
        }
        catch (Exception exception) {
            return N.Y.b();
        }
    }

    public int b() {
        if (this.a != null) {
            this.a.d();
        }
        return 0;
    }

    public int c() {
        if (this.a != null) {
            this.a.c();
        }
        return 0;
    }

    public int d() {
        this.i();
        if (this.a != null) {
            this.a.e();
        }
        return 0;
    }

    public int e() {
        this.i();
        try {
            if (this.a != null) {
                this.a.f();
                this.a = null;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public int a(String string) {
        E e2 = new E();
        e2.a(string);
        return this.a.loadCustomResource(e2);
    }

    public int f() {
        return this.a.freeCustomResource(null);
    }

    public int b(String string, String string2) {
        com.baidu.tts.m.G g2 = new com.baidu.tts.m.G();
        g2.b(string);
        g2.a(string2);
        return this.a.loadModel(g2);
    }

    public int c(String string, String string2) {
        F f2 = new F();
        f2.a(string);
        f2.b(string2);
        return this.a.loadEnglishModel(f2);
    }

    public int a(String string, String string2, Bundle bundle) {
        return this.a(string, new b(string, string2));
    }

    public int b(String string, String string2, Bundle bundle) {
        return this.a(string, new c(string, string2));
    }

    public int a(List<SpeechSynthesizeBag> list) {
        int n2 = list.size();
        if (n2 <= 100) {
            return this.a(new a(list));
        }
        return SpeechSynthesizer.ERROR_LIST_IS_TOO_LONG;
    }

    public AuthInfo b(TtsMode ttsMode) {
        return this.a.auth(ttsMode.getTtsEnum());
    }

    public int a(float f2, float f3) {
        return this.a.setStereoVolume(f2, f3);
    }

    public int a(int n2) {
        return this.a.setAudioStreamType(n2);
    }

    public int b(int n2) {
        return this.a.setAudioSampleRate(n2);
    }

    private synchronized ExecutorService h() {
        if (this.c == null) {
            this.c = new com.baidu.tts.c.A(15000, "SpeechSynthesizerPoolThread", (RejectedExecutionHandler)new ThreadPoolExecutor.AbortPolicy());
        }
        return this.c;
    }

    private int a(String string, Callable<Void> callable) {
        N n2 = ResourceTools.isTextValid(string);
        if (n2 == null) {
            return this.a(callable);
        }
        return n2.b();
    }

    private int a(Callable<Void> callable) {
        try {
            ExecutorService executorService = this.h();
            executorService.submit(callable);
            return 0;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            ExecutorService executorService = this.h();
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)executorService;
            int n2 = threadPoolExecutor.getQueue().size();
            Log.e((String)"bdtts-Queue", (String)(" count=" + n2));
            return SpeechSynthesizer.ERROR_QUEUE_IS_FULL;
        }
    }

    private void i() {
        if (this.c != null) {
            if (!this.c.isShutdown()) {
                this.c.shutdownNow();
            }
            try {
                boolean bl = this.c.awaitTermination(L.a.a(), TimeUnit.MILLISECONDS);
                LoggerProxy.d("SpeechSynthesizerAdapter", "isTerminated=" + bl);
            }
            catch (InterruptedException interruptedException) {
                LoggerProxy.d("SpeechSynthesizerAdapter", "InterruptedException");
            }
            this.c = null;
        }
    }

    private String a(H h2) {
        com.baidu.tts.m.I i2;
        if (h2 != null && (i2 = h2.e()) != null) {
            String string = i2.f();
            return string;
        }
        LoggerProxy.d("SpeechSynthesizerAdapter", "getUtteranceId null");
        return null;
    }

    private SpeechError b(H h2) {
        Object object;
        if (h2 != null) {
            object = h2.f();
            if (object != null) {
                int n2 = ((TtsError)object).getDetailCode();
                String string = ((TtsError)object).getDetailMessage();
                SpeechError speechError = new SpeechError();
                speechError.code = n2;
                speechError.description = string;
                return speechError;
            }
            LoggerProxy.d("SpeechSynthesizerAdapter", "ttsError is null");
        }
        object = new SpeechError();
        ((SpeechError)object).code = N.al.b();
        ((SpeechError)object).description = N.al.c();
        return (SpeechError)object;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class a
    implements Callable<Void> {
        List<SpeechSynthesizeBag> a;

        public a(List<SpeechSynthesizeBag> list) {
            this.a = list;
        }

        public Void a() throws Exception {
            int n2;
            if (this.a != null && (n2 = this.a.size()) > 0) {
                for (int i2 = 0; i2 < n2; ++i2) {
                    SpeechSynthesizeBag speechSynthesizeBag = this.a.get(i2);
                    if (speechSynthesizeBag == null) continue;
                    String string = speechSynthesizeBag.getText();
                    String string2 = speechSynthesizeBag.getUtteranceId();
                    if (TextUtils.isEmpty((CharSequence)string2)) {
                        string2 = String.valueOf(i2);
                        speechSynthesizeBag.setUtteranceId(string2);
                    }
                    if (!Thread.currentThread().isInterrupted()) {
                        com.baidu.tts.m.I i3 = new com.baidu.tts.m.I(string, string2);
                        i3.a(I.b);
                        A.this.a.speak(i3);
                        continue;
                    }
                    return null;
                }
            }
            return null;
        }

        @Override
        public /* synthetic */ Void call() throws Exception {
            return this.a();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class c
    implements Callable<Void> {
        private String b;
        private String c;

        public c(String string, String string2) {
            this.b = string;
            this.c = string2;
        }

        public Void a() throws Exception {
            com.baidu.tts.m.I i2 = new com.baidu.tts.m.I(this.b, this.c);
            i2.a(I.a);
            A.this.a.synthesize(i2);
            return null;
        }

        @Override
        public /* synthetic */ Void call() throws Exception {
            return this.a();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class b
    implements Callable<Void> {
        private String b;
        private String c;

        public b(String string, String string2) {
            this.b = string;
            this.c = string2;
        }

        public Void a() throws Exception {
            com.baidu.tts.m.I i2 = new com.baidu.tts.m.I(this.b, this.c);
            i2.a(I.b);
            A.this.a.speak(i2);
            return null;
        }

        @Override
        public /* synthetic */ Void call() throws Exception {
            return this.a();
        }
    }

}


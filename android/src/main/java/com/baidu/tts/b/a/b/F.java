/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.apache.http.HttpEntity
 *  org.apache.http.NameValuePair
 *  org.apache.http.client.entity.UrlEncodedFormEntity
 *  org.apache.http.message.BasicNameValuePair
 */
package com.baidu.tts.b.a.b;

import android.content.Context;
import com.baidu.speechsynthesizer.utility.SpeechDecoder;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.auth.C;
import com.baidu.tts.b.a.b.H;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.G;
import com.baidu.tts.f.L;
import com.baidu.tts.f.N;
import com.baidu.tts.f.O;
import com.baidu.tts.loopj.RequestHandle;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.SyncHttpClient;
import com.baidu.tts.m.E;
import com.baidu.tts.m.I;
import com.baidu.tts.tools.CommonUtility;
import com.baidu.tts.tools.StringTool;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class F
extends com.baidu.tts.b.a.b.A {
    private b b;
    private double c;

    public <OnlineSynthesizerParams> void a(OnlineSynthesizerParams OnlineSynthesizerParams) {
        this.b = (b)OnlineSynthesizerParams;
    }

    @Override
    public TtsError a(I i2) throws InterruptedException {
        try {
            d d2 = new d(i2);
            return d2.a();
        }
        catch (InterruptedException interruptedException) {
            throw interruptedException;
        }
        catch (Exception exception) {
            return com.baidu.tts.h.a.C.a().a(N.j, exception);
        }
    }

    private com.baidu.tts.m.H a(int n2, c c2, I i2) throws InterruptedException {
        com.baidu.tts.m.H h2 = com.baidu.tts.m.H.b(i2);
        b b2 = (b)this.b.E();
        a a2 = new a(n2, c2, i2, b2, h2);
        FutureTask<com.baidu.tts.m.H> futureTask = new FutureTask<com.baidu.tts.m.H>(a2);
        Thread thread = new Thread(futureTask);
        thread.start();
        long l2 = b2.u();
        try {
            h2 = futureTask.get(l2, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            futureTask.cancel(true);
            a2.b();
            throw interruptedException;
        }
        catch (ExecutionException executionException) {
            TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.n, executionException.getCause());
            h2.a(ttsError);
        }
        catch (TimeoutException timeoutException) {
            LoggerProxy.d("OnlineSynthesizer", "startOnceHttpRequest timeout");
            futureTask.cancel(true);
            a2.b();
            TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.o, timeoutException);
            h2.a(ttsError);
        }
        return h2;
    }

    private HttpEntity a(int n2, String string, I i2, b b2) throws com.baidu.tts.q.A {
        CharSequence charSequence;
        String string3;
        String string2;
        if (b2 == null) {
            return null;
        }
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair(G.X.a(), String.valueOf(n2)));
        arrayList.add(new BasicNameValuePair(G.W.a(), string));
        arrayList.add(new BasicNameValuePair(G.C.b(), "Android"));
        com.baidu.tts.h.b.B b3 = com.baidu.tts.h.b.B.a();
        String string4 = b3.j();
        arrayList.add(new BasicNameValuePair(G.ab.a(), string4));
        String string5 = b2.i();
        if (!StringTool.isEmpty(string5)) {
            arrayList.add(new BasicNameValuePair(G.O.a(), string5));
        }
        if (!StringTool.isEmpty(string2 = b2.j())) {
            arrayList.add(new BasicNameValuePair(G.al.a(), string2));
        }
        i2.c(b2.w());
        String string6 = i2.d();
        if (n2 == 1) {
            string3 = null;
            try {
                charSequence = i2.c();
                string3 = URLEncoder.encode((String)charSequence, string6);
                arrayList.add(new BasicNameValuePair(G.Y.a(), string3));
                String string7 = b3.a(G.Z.a());
                arrayList.add(new BasicNameValuePair(G.Z.a(), string7));
                String object = b3.i();
                if (object != null) {
                    arrayList.add(new BasicNameValuePair(G.aa.a(), object));
                }
                if (StringTool.isEmpty(string5)) {
                    LoggerProxy.d("OnlineSynthesizer", "before online auth");
                    C.a arrcc = com.baidu.tts.auth.A.a().a(b2);
                    LoggerProxy.d("OnlineSynthesizer", "after online auth");
                    if (arrcc.g()) {
                        String string8 = arrcc.a();
                        arrayList.add(new BasicNameValuePair(G.aj.a(), string8));
                    } else {
                        throw new com.baidu.tts.q.A();
                    }
                }
                arrayList.add(new BasicNameValuePair(G.H.a(), b2.x()));
                arrayList.add(new BasicNameValuePair(G.J.a(), b2.h()));
                arrayList.add(new BasicNameValuePair(G.K.a(), b2.k()));
                com.baidu.tts.f.C[] arrc = b2.g().b();
                int n3 = Integer.parseInt(b2.k());
                this.c = arrc[n3].b();
                arrayList.add(new BasicNameValuePair(G.L.a(), b2.l()));
                arrayList.add(new BasicNameValuePair(G.ac.a(), b2.m()));
                arrayList.add(new BasicNameValuePair(G.ad.a(), b2.n()));
                arrayList.add(new BasicNameValuePair(G.M.a(), b2.o()));
                arrayList.add(new BasicNameValuePair(G.N.a(), b2.p()));
                arrayList.add(new BasicNameValuePair(G.ae.a(), b2.q()));
                arrayList.add(new BasicNameValuePair(G.af.a(), b2.r()));
                arrayList.add(new BasicNameValuePair(G.G.a(), b2.y()));
                arrayList.add(new BasicNameValuePair(G.D.a(), b2.B()));
                arrayList.add(new BasicNameValuePair(G.F.a(), b2.C()));
                arrayList.add(new BasicNameValuePair(G.E.a(), b2.D()));
                arrayList.add(new BasicNameValuePair(G.T.a(), b2.z()));
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                unsupportedEncodingException.printStackTrace();
            }
        }
        HttpEntity entity = null;
        try {
            charSequence = new StringBuffer();
            for (NameValuePair nameValuePair : arrayList) {
                ((StringBuffer)charSequence).append(nameValuePair.getName());
                ((StringBuffer)charSequence).append("=");
                ((StringBuffer)charSequence).append(nameValuePair.getValue());
                ((StringBuffer)charSequence).append(",");
            }
            LoggerProxy.d("OnlineSynthesizer", "request params: " + charSequence);
            entity = new UrlEncodedFormEntity(arrayList, string6);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }
        return entity;
    }

    @Override
    public int a(E e2) {
        return N.k.b();
    }

    @Override
    public int b(E e2) {
        return N.k.b();
    }

    @Override
    public int a(com.baidu.tts.m.G g2) {
        return N.k.b();
    }

    @Override
    public int a(com.baidu.tts.m.F f2) {
        return N.k.b();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class b
    extends com.baidu.tts.m.D<b> {
        private String a;
        private com.baidu.tts.f.B b = com.baidu.tts.f.B.b;
        private com.baidu.tts.f.C c = com.baidu.tts.f.C.f;
        private String d = "0";
        private String e;
        private String f;
        private String g;
        private String h;
        private String i;
        private String j;
        private String k;
        private String l;
        private String m;
        private int n = 5;
        private int o = 1000;
        private int p = L.a.b();
        private String q;
        private int r = -1;
        private String s = "https";
        private String t = "1";
        private String u;
        private static Set<String> v = new HashSet<String>();

        public String a() {
            return this.t;
        }

        public void a(String string) {
            this.t = string;
        }

        public String b() {
            return this.s;
        }

        public void b(String string) {
            this.s = string;
        }

        public int c() {
            return this.r;
        }

        public void a(int n2) {
            this.r = n2;
        }

        public String d() {
            return this.q;
        }

        public void c(String string) {
            this.q = string;
        }

        public String e() {
            return this.k;
        }

        public void d(String string) {
            this.k = string;
        }

        public String f() {
            return this.l;
        }

        public void e(String string) {
            this.l = string;
        }

        public com.baidu.tts.f.B g() {
            return this.b;
        }

        public String h() {
            return this.b.a();
        }

        public int a(com.baidu.tts.f.B b2) {
            if (b2 != null) {
                this.b = b2;
                return 0;
            }
            return N.Y.b();
        }

        public String i() {
            return this.a;
        }

        public void f(String string) {
            this.a = string;
        }

        public String j() {
            return this.m;
        }

        public void g(String string) {
            this.m = string;
        }

        public String k() {
            return this.c.a();
        }

        public void a(com.baidu.tts.f.C c2) {
            this.c = c2;
        }

        public String l() {
            return this.d;
        }

        public void h(String string) {
            this.d = string;
        }

        public String m() {
            return this.e;
        }

        public String n() {
            return this.f;
        }

        public String o() {
            return this.g;
        }

        public void i(String string) {
            this.g = string;
        }

        public String p() {
            return this.h;
        }

        public void j(String string) {
            this.h = string;
        }

        public String q() {
            return this.i;
        }

        public void k(String string) {
            this.i = string;
        }

        public String r() {
            return this.j;
        }

        public void l(String string) {
            this.j = string;
        }

        public int s() {
            return this.n;
        }

        public void b(int n2) {
            this.n = n2;
        }

        public int t() {
            return this.o;
        }

        public void c(int n2) {
            this.o = n2;
        }

        public int u() {
            return this.p;
        }

        public void d(int n2) {
            this.p = n2;
        }

        public void m(String string) {
            this.u = string;
        }

        public String v() {
            return this.u;
        }

        static {
            v.add(G.D.b());
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class a
    implements Callable<com.baidu.tts.m.H> {
        private int b;
        private c c;
        private I d;
        private b e;
        private com.baidu.tts.m.H f;
        private SyncHttpClient g;

        public a(int n2, c c2, I i2, b b2, com.baidu.tts.m.H h2) {
            this.b = n2;
            this.c = c2;
            this.d = i2;
            this.e = b2;
            this.f = h2;
        }

        public com.baidu.tts.m.H a() throws Exception {
            HttpEntity httpEntity = null;
            try {
                httpEntity = F.this.a(this.b, this.c.a, this.d, this.e);
            }
            catch (com.baidu.tts.q.A a2) {
                TtsError ttsError = com.baidu.tts.h.a.C.a().b(N.h);
                this.f.a(ttsError);
                return this.f;
            }
            String string = this.e.b();
            if (this.b == 1) {
                String string2 = this.e.a();
                this.c.b = this.e.v() == null ? ("1".equals(string2) ? O.a.a(string) : O.a.b(string)) : this.e.v();
                LoggerProxy.d("OnlineSynthesizer", "serverIp=" + this.c.b);
            }
            if (this.c.b.startsWith("http://")) {
                this.g = new SyncHttpClient();
            } else if (this.c.b.startsWith("https://")) {
                this.g = new SyncHttpClient(true, 80, 443);
            }
            int n2 = this.e.s();
            int n3 = this.e.t();
            this.g.setMaxRetriesAndTimeout(n2, n3);
            int n4 = this.e.u();
            LoggerProxy.d("OnlineSynthesizer", "timeout=" + n4);
            this.g.setTimeout(n4);
            H h2 = new H(this.f);
            h2.a(this.e);
            String string3 = this.e.d();
            if (string3 != null) {
                int n5 = this.e.c();
                LoggerProxy.d("OnlineSynthesizer", "--> proxy host=" + string3 + "--port=" + n5);
                this.g.setProxy(string3, n5);
            }
            if (this.c.b == null) {
                TtsError ttsError = com.baidu.tts.h.a.C.a().b(N.q);
                this.f.a(ttsError);
            } else if (!Thread.currentThread().isInterrupted()) {
                LoggerProxy.d("OnlineSynthesizer", "before post");
                this.g.post(null, this.c.b, httpEntity, null, h2);
                LoggerProxy.d("OnlineSynthesizer", "after post");
            }
            return this.f;
        }

        public void b() {
            if (this.g != null) {
                this.g.stop();
            }
        }

        @Override
        public /* synthetic */ com.baidu.tts.m.H call() throws Exception {
            return this.a();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class d
    implements SpeechDecoder.OnDecodedDataListener,
    Callable<TtsError> {
        private I c;
        private c d;
        private SpeechDecoder e;
        private com.baidu.tts.m.H f;
        private int g = 0;
        private int h = 1;
        byte[] a = new byte[0];

        public d(I i2) {
            this.c = i2;
            this.d = new c();
            this.e = new SpeechDecoder();
        }

        public TtsError a() throws Exception {
            Object object;
            int n2 = 0;
            com.baidu.tts.m.H h2 = null;
            SpeechDecoder.setOnDecodedDataListener(this);
            do {
                LoggerProxy.d("OnlineSynthesizer", "count=" + ++n2);
                h2 = F.this.a(n2, this.d, this.c);
                if (!this.a(h2)) continue;
                this.f = h2;
                object = h2.d();
                if (F.this.b.g() == com.baidu.tts.f.B.e) {
                    F.this.a(h2);
                    continue;
                }
                int n3 = this.e.decodeWithCallback((byte[])object);
                LoggerProxy.d("OnlineSynthesizer", "Decoder ret : " + n3);
            } while (!this.b(h2));
            TtsError object2 = h2 == null ? com.baidu.tts.h.a.C.a().b(N.j) : h2.f();
            return object2;
        }

        private boolean a(com.baidu.tts.m.H h2) {
            if (h2 == null) {
                return false;
            }
            TtsError ttsError = h2.f();
            if (ttsError == null) {
                int n2 = h2.a();
                return n2 == 0;
            }
            return false;
        }

        private boolean b(com.baidu.tts.m.H h2) {
            if (this.a(h2)) {
                int n2 = h2.b();
                return n2 < 0;
            }
            return true;
        }

        @Override
        public void onDecodedData(byte[] audioData) {
            int n2;
            this.a = this.a(this.a, audioData);
            int n3 = this.a.length;
            if (n3 >= 3200) {
                n2 = this.f.d().length;
                int n4 = this.f.c();
                double d2 = (double)n3 / ((double)n2 * F.this.c);
                int n5 = (int)((double)this.g + d2 * (double)(n4 - this.g) * (double)this.h);
                this.a(n5);
            }
            if (audioData.length == 0) {
                if (n3 < 3200) {
                    n2 = this.f.c();
                    this.a(n2);
                }
                this.g = this.f.c();
                this.h = 1;
                if (this.f.b() < 0) {
                    this.g = 0;
                }
            }
        }

        private void a(int n2) {
            LoggerProxy.d("OnlineSynthesizer", "mindex=" + this.h + " progress=" + n2);
            com.baidu.tts.m.H h2 = (com.baidu.tts.m.H)this.f.E();
            h2.a(this.a);
            h2.a(com.baidu.tts.f.A.a);
            h2.c(this.h);
            h2.d(n2);
            F.this.a(h2);
            ++this.h;
            this.a = new byte[0];
        }

        private byte[] a(byte[] arrby, byte[] arrby2) {
            byte[] arrby3 = new byte[arrby.length + arrby2.length];
            System.arraycopy(arrby, 0, arrby3, 0, arrby.length);
            System.arraycopy(arrby2, 0, arrby3, arrby.length, arrby2.length);
            return arrby3;
        }

        @Override
        public /* synthetic */ TtsError call() throws Exception {
            return this.a();
        }
    }

    private class c {
        String a = CommonUtility.generateSerialNumber();
        String b;
    }

}


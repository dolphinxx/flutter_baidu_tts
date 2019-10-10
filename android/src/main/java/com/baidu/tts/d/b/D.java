/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.d.b;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.model.DownloadHandler;
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.d.b.A;
import com.baidu.tts.d.b.B;
import com.baidu.tts.d.b.C;
import com.baidu.tts.d.b.E;
import com.baidu.tts.f.G;
import com.baidu.tts.tools.DataTool;
import com.baidu.tts.tools.StringTool;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class D {
    private String a;
    private String b;
    private String c;
    private long d = 0L;
    private A e = A.a();
    private CopyOnWriteArraySet<DownloadHandler> f = new CopyOnWriteArraySet();

    public D(String string) {
        this.a = string;
    }

    public void a(DownloadHandler downloadHandler) {
        if (this.f != null) {
            this.f.add(downloadHandler);
        }
    }

    public void b(DownloadHandler downloadHandler) {
        boolean bl = DataTool.isSetEmpty(this.f);
        LoggerProxy.d("ModelFlyweight", "unregisterListener 1isEmpty=" + bl);
        if (!bl) {
            this.f.remove(downloadHandler);
            bl = DataTool.isSetEmpty(this.f);
            LoggerProxy.d("ModelFlyweight", "unregisterListener 2isEmpty=" + bl);
            if (bl) {
                this.j();
            } else {
                for (DownloadHandler downloadHandler2 : this.f) {
                    LoggerProxy.d("ModelFlyweight", "unregisterListener item=" + downloadHandler2);
                }
            }
        }
    }

    private void j() {
        this.e.a(this.b, this.a);
        this.e.a(this.c, this.a);
    }

    public void a() {
        this.f.clear();
        this.j();
    }

    public Set<String> b() {
        HashSet<String> hashSet = new HashSet<String>();
        E e2 = E.a();
        C c2 = e2.b(this.b);
        C c3 = e2.b(this.c);
        String string = c2.a();
        String string2 = c3.a();
        hashSet.add(string);
        hashSet.add(string2);
        return hashSet;
    }

    public long c() {
        this.d();
        return this.d;
    }

    public void d() {
        if (this.d == 0L) {
            this.e();
        }
    }

    public void e() {
        E e2 = E.a();
        C c2 = e2.b(this.b);
        String string = c2.b();
        C c3 = e2.b(this.c);
        String string2 = c3.b();
        Long l2 = Long.parseLong(string);
        Long l3 = Long.parseLong(string2);
        this.d = l2 + l3;
    }

    public boolean a(com.baidu.tts.database.A a2) {
        Map<String, String> map = a2.e(this.a);
        if (map == null || map.isEmpty()) {
            return false;
        }
        this.b = DataTool.getMapValue(map, G.r.b());
        this.c = DataTool.getMapValue(map, G.s.b());
        boolean bl = StringTool.isEmpty(this.b);
        boolean bl2 = StringTool.isEmpty(this.c);
        if (!bl && !bl2) {
            return true;
        }
        a2.a(this.a);
        return false;
    }

    public void a(ModelBags modelBags, com.baidu.tts.database.A a2) {
        a2.a(modelBags);
        this.a(a2);
    }

    public Set<String> f() {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add(this.b);
        hashSet.add(this.c);
        return hashSet;
    }

    public String g() {
        return this.a;
    }

    public long h() {
        long l2 = this.e.d(this.b);
        long l3 = this.e.d(this.c);
        return l2 + l3;
    }

    public void c(DownloadHandler downloadHandler) {
        this.a(downloadHandler);
        downloadHandler.updateStart(this);
    }

    public void a(DownloadHandler downloadHandler, TtsError ttsError) {
        downloadHandler.updateFinish(this, ttsError);
        this.b(downloadHandler);
    }

    public void a(B b2) {
        if (this.f != null) {
            for (DownloadHandler downloadHandler : this.f) {
                downloadHandler.updateProgress(this);
            }
        }
    }

    public boolean i() {
        int n2 = this.e.e(this.b);
        int n3 = this.e.e(this.c);
        return n2 == 7 && n3 == 7;
    }

    public void b(B b2) {
        boolean bl = this.i();
        LoggerProxy.d("ModelFlyweight", "onFileDownloadSuccess isAllFileDownloadSuccess=" + bl);
        if (bl && this.f != null) {
            for (DownloadHandler downloadHandler : this.f) {
                this.a(downloadHandler, null);
            }
        }
    }

    public void a(B b2, TtsError ttsError) {
        LoggerProxy.d("ModelFlyweight", "onFileDownloadFailure");
        if (this.f != null) {
            for (DownloadHandler downloadHandler : this.f) {
                this.a(downloadHandler, ttsError);
            }
        }
    }
}


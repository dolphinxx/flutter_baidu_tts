/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.d.b;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.d.b.A;
import com.baidu.tts.d.b.C;
import com.baidu.tts.d.b.D;
import com.baidu.tts.d.b.E;
import com.baidu.tts.f.G;
import com.baidu.tts.tools.DataTool;
import com.baidu.tts.tools.MD5;
import com.baidu.tts.tools.StringTool;
import java.io.File;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

public class B {
    private String a;
    private long b;
    private String c;
    private volatile int d = 0;
    private volatile int e = 9;
    private String f;
    private com.baidu.tts.d.a.E g;
    private CopyOnWriteArraySet<String> h = new CopyOnWriteArraySet();

    public B(String string) {
        this.a = string;
    }

    public void a(String string) {
        if (this.h != null) {
            this.h.add(string);
        }
    }

    public void b(String string) {
        boolean bl = DataTool.isSetEmpty(this.h);
        LoggerProxy.d("FsFileInfoFlyweight", "unregisterObserver 1isEmpty=" + bl);
        if (!bl) {
            this.h.remove(string);
            bl = DataTool.isSetEmpty(this.h);
            LoggerProxy.d("FsFileInfoFlyweight", "unregisterObserver 2isEmpty=" + bl);
            if (bl) {
                this.j();
            }
        }
    }

    private void j() {
        if (this.g != null) {
            LoggerProxy.d("FsFileInfoFlyweight", "unregisterObserver stop");
            this.g.a();
            this.g = null;
            if (this.d == 4 || this.d == 5) {
                this.d = 8;
                this.e = 8;
                A a2 = A.a();
                com.baidu.tts.database.A a3 = a2.b();
                a3.a(this.a, this.e);
            }
        }
    }

    public long a() {
        return this.b;
    }

    public void a(com.baidu.tts.d.a.E e2) {
        this.g = e2;
    }

    public String b() {
        return this.a;
    }

    public String c() {
        return this.f;
    }

    public void c(String string) {
        this.f = string;
    }

    public int d() {
        return this.d;
    }

    public boolean a(com.baidu.tts.database.A a2) {
        C c2 = E.a().b(this.f);
        if (this.d == 0) {
            this.a(c2);
            this.b(a2);
        } else if (this.d == 8 || this.d == 7) {
            this.a(c2);
        }
        if (this.d == 7 && this.e != 7) {
            this.e = 7;
            a2.a(this.a, this.e);
        }
        LoggerProxy.d("FsFileInfoFlyweight", "fileId=" + this.f + "--filestate=" + this.d + "--dbstate=" + this.e);
        return this.d != 4 && this.d != 5 && this.d != 7;
    }

    public boolean e() {
        if (this.d == 7) {
            return false;
        }
        if (this.d == 4 || this.d == 5) {
            return false;
        }
        C c2 = E.a().b(this.f);
        String string = c2.b();
        long l2 = Long.parseLong(string);
        return this.b >= l2;
    }

    public boolean f() {
        File file = new File(this.a);
        return file.delete();
    }

    public int b(com.baidu.tts.database.A a2) {
        Map<String, String> map = a2.c(this.a);
        String string = DataTool.getMapValue(map, G.a.b());
        this.e = StringTool.isEmpty(string) ? 9 : Integer.parseInt(string);
        return this.e;
    }

    public int a(C c2) {
        File file = new File(this.a);
        if (file.exists()) {
            this.b = file.length();
            String string = c2.b();
            long l2 = Long.parseLong(string);
            if (this.b == l2) {
                this.c = MD5.getInstance().getBigFileMd5(file);
                String string2 = c2.c();
                this.d = string2.equalsIgnoreCase(this.c) ? 7 : 3;
            } else {
                this.d = 2;
            }
        } else {
            this.d = 1;
        }
        return this.d;
    }

    public void g() {
        LoggerProxy.d("FsFileInfoFlyweight", "queueForDownload fileId=" + this.f + "--filestate=" + this.d);
        this.d = 4;
    }

    public void h() {
        this.d = 5;
        this.e = 6;
        A a2 = A.a();
        com.baidu.tts.database.A a3 = a2.b();
        a3.a(this.a, this.e);
    }

    public void a(long l2, long l3) {
        A a2 = A.a();
        this.b = l2;
        if (this.h != null) {
            for (String string : this.h) {
                D d2 = a2.b(string);
                d2.a(this);
            }
        }
    }

    public void i() {
        LoggerProxy.d("FsFileInfoFlyweight", "onDownloadSuccess");
        this.d = 7;
        this.e = 7;
        A a2 = A.a();
        com.baidu.tts.database.A a3 = a2.b();
        a3.a(this.a, this.e);
        if (this.h != null) {
            for (String string : this.h) {
                D d2 = a2.b(string);
                d2.b(this);
                this.h.remove(string);
            }
        }
    }

    public void a(TtsError ttsError) {
        LoggerProxy.d("FsFileInfoFlyweight", "onDownloadFailure");
        this.d = 8;
        this.e = 8;
        A a2 = A.a();
        com.baidu.tts.database.A a3 = a2.b();
        a3.a(this.a, this.e);
        if (this.h != null) {
            for (String string : this.h) {
                D d2 = a2.b(string);
                d2.a(this, ttsError);
                this.h.remove(string);
            }
        }
    }
}


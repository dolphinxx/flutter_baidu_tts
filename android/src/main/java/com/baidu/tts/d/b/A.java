/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.d.b;

import com.baidu.tts.client.model.DownloadHandler;
import com.baidu.tts.d.b.B;
import com.baidu.tts.d.b.C;
import com.baidu.tts.d.b.D;
import com.baidu.tts.d.b.E;

public class A {
    private static volatile A a = null;
    private E b = E.a();
    private com.baidu.tts.database.A c;

    private A() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static A a() {
        if (a != null) return a;
        Class<A> class_ = A.class;
        synchronized (A.class) {
            if (a != null) return a;
            {
                a = new A();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return a;
        }
    }

    public com.baidu.tts.database.A b() {
        return this.c;
    }

    public void a(com.baidu.tts.database.A a2) {
        this.c = a2;
    }

    public B a(String string) {
        return this.b.c(string);
    }

    public D b(String string) {
        return this.b.a(string);
    }

    public C c(String string) {
        return this.b.b(string);
    }

    public long d(String string) {
        return this.b.e(string);
    }

    public int e(String string) {
        return this.b.f(string);
    }

    public void a(DownloadHandler downloadHandler) {
        this.b.a(downloadHandler);
    }

    public void a(String string, String string2) {
        this.b.a(string, string2);
    }

    public void c() {
        this.b.b();
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.d.b;

import com.baidu.tts.client.model.DownloadHandler;
import com.baidu.tts.d.b.B;
import com.baidu.tts.d.b.C;
import com.baidu.tts.d.b.D;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class E {
    private static volatile E a = null;
    private ConcurrentHashMap<String, D> b = new ConcurrentHashMap();
    private ConcurrentHashMap<String, C> c = new ConcurrentHashMap();
    private ConcurrentHashMap<String, B> d = new ConcurrentHashMap();

    private E() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static E a() {
        if (a != null) return a;
        Class<E> class_ = E.class;
        synchronized (E.class) {
            if (a != null) return a;
            {
                a = new E();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return a;
        }
    }

    public D a(String string) {
        try {
            D d2 = new D(string);
            D d3 = this.b.putIfAbsent(string, d2);
            if (d3 == null) {
                d3 = d2;
            }
            return d3;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public C b(String string) {
        try {
            C c2 = new C(string);
            C c3 = this.c.putIfAbsent(string, c2);
            if (c3 == null) {
                c3 = c2;
            }
            return c3;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public B c(String string) {
        try {
            B b2 = new B(string);
            B b3 = this.d.putIfAbsent(string, b2);
            if (b3 == null) {
                b3 = b2;
            }
            return b3;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public B d(String string) {
        C c2 = this.b(string);
        if (c2 != null) {
            String string2 = c2.a();
            B b2 = this.c(string2);
            return b2;
        }
        return null;
    }

    public long e(String string) {
        B b2 = this.d(string);
        return b2.a();
    }

    public int f(String string) {
        B b2 = this.d(string);
        return b2.d();
    }

    public void a(DownloadHandler downloadHandler) {
        String string = downloadHandler.getModelId();
        D d2 = this.a(string);
        d2.b(downloadHandler);
    }

    public void a(String string, String string2) {
        B b2 = this.d(string);
        if (b2 != null) {
            b2.b(string2);
        }
    }

    public void b() {
        Collection<D> collection = this.b.values();
        for (D d2 : collection) {
            d2.a();
        }
    }
}


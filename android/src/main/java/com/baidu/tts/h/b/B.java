/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.h.b;

import android.content.Context;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.f.G;
import com.baidu.tts.h.b.A;
import java.lang.ref.WeakReference;
import java.util.Hashtable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class B
implements com.baidu.tts.j.B {
    private static volatile B a = null;
    private Hashtable<WeakReference<Context>, A> b = new Hashtable();
    private WeakReference<Context> c;
    private Hashtable<String, Object> d = new Hashtable();

    private B() {
        this.d.put(G.Z.a(), "10");
        this.d.put(G.ab.a(), "V2.3.2");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static B a() {
        if (a != null) return a;
        Class<B> class_ = B.class;
        synchronized (B.class) {
            if (a != null) return a;
            {
                a = new B();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return a;
        }
    }

    @Override
    public TtsError b() {
        return null;
    }

    @Override
    public void c() {
    }

    @Override
    public void d() {
    }

    @Override
    public void e() {
    }

    @Override
    public void f() {
        if (this.b != null) {
            this.b.clear();
        }
        this.c = null;
    }

    public A a(WeakReference<Context> weakReference) {
        if (weakReference == null) {
            return null;
        }
        A a2 = this.b.get(weakReference);
        if (a2 == null) {
            a2 = new A(weakReference);
            this.b.put(weakReference, a2);
        }
        return a2;
    }

    public A g() {
        return this.a(this.c);
    }

    public Context h() {
        return (Context)this.c.get();
    }

    public void a(Context context) {
        this.c = new WeakReference<Context>(context);
    }

    public String a(String string) {
        try {
            return (String)this.d.get(string);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public String i() {
        try {
            A a2 = this.g();
            return a2 == null ? null : a2.a();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public String j() {
        return this.a(G.ab.a());
    }
}


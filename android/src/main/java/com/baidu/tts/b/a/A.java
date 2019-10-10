/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.b.a;

import com.baidu.tts.b.a.a.C;
import com.baidu.tts.b.a.b.B;
import com.baidu.tts.b.a.b.D;
import com.baidu.tts.b.a.b.E;
import com.baidu.tts.b.a.b.F;

public class A {
    private static volatile A a = null;

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

    public com.baidu.tts.b.a.a.D a(com.baidu.tts.f.F f2) {
        com.baidu.tts.b.a.a.D d2 = null;
        switch (f2) {
            case a: {
                d2 = this.b();
                break;
            }
            case b: {
                d2 = this.c();
                break;
            }
            case c: {
                d2 = this.d();
                break;
            }
        }
        return d2;
    }

    private com.baidu.tts.b.a.a.D b() {
        F f2 = new F();
        return this.a(f2);
    }

    private com.baidu.tts.b.a.a.D c() {
        E e2 = new E();
        return this.a(e2);
    }

    private com.baidu.tts.b.a.a.D d() {
        D d2 = new D();
        return this.a(d2);
    }

    private com.baidu.tts.b.a.a.D a(B b2) {
        C c2 = new C();
        c2.a(b2);
        return c2;
    }

}


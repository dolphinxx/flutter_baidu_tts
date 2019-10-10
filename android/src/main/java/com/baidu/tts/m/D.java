/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.m;

import com.baidu.tts.f.H;
import com.baidu.tts.n.A;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class D<T>
extends A<T> {
    private String a = "5";
    private String b = "5";
    private String c = "5";
    private String d = H.a.a();
    private com.baidu.tts.f.D e = com.baidu.tts.f.D.c;
    private String f = "0";

    public String w() {
        return this.e.a();
    }

    public String x() {
        return this.e.b();
    }

    public void a(com.baidu.tts.f.D d2) {
        this.e = d2;
    }

    public String y() {
        return this.d;
    }

    public void n(String string) {
        this.d = string;
    }

    public void o(String string) {
        this.f = string;
    }

    public String z() {
        return this.f;
    }

    public long A() {
        long l2 = 0L;
        try {
            l2 = Long.parseLong(this.f);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return l2;
    }

    public String B() {
        return this.a;
    }

    public void p(String string) {
        this.a = string;
    }

    public String C() {
        return this.b;
    }

    public void q(String string) {
        this.b = string;
    }

    public String D() {
        return this.c;
    }

    public void r(String string) {
        this.c = string;
    }
}


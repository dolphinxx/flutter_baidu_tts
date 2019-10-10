/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.m;

import com.baidu.tts.b.a.b.E;
import com.baidu.tts.b.a.b.F;
import com.baidu.tts.f.J;
import com.baidu.tts.f.N;
import com.baidu.tts.n.A;
import com.baidu.tts.tools.DataTool;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class B
extends A<B> {
    private F.b a = new F.b();
    private E.b b = new E.b();
    private J c;

    public F.b a() {
        return this.a;
    }

    public E.b b() {
        return this.b;
    }

    public void a(String string) {
        this.a.p(string);
        this.b.p(string);
    }

    public void b(String string) {
        this.a.r(string);
        this.b.r(string);
    }

    public void c(String string) {
        this.a.q(string);
        this.b.q(string);
    }

    public int d(String string) {
        if (DataTool.isLong(string)) {
            this.a.o(string);
            this.b.o(string);
            return 0;
        }
        return N.Y.b();
    }

    public J c() {
        return this.c;
    }

    public void a(J j2) {
        this.c = j2;
    }
}


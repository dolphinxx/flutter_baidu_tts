/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.i.a;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.i.a.A;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class B
implements Iterator<A> {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;

    public void a(int n2) {
        this.c = n2;
    }

    public void a() {
        this.a = 0;
        this.b = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
    }

    public void b() {
    }

    public void b(int n2) {
        this.b += n2;
        this.f = 0;
    }

    private int e() {
        return this.a + this.b - 1;
    }

    private int f() {
        return this.d + this.c - 1;
    }

    @Override
    public boolean hasNext() {
        return this.e < this.e();
    }

    public A c() {
        int n2;
        A a2 = new A();
        int n3 = this.f();
        if (n3 <= (n2 = this.e())) {
            int n4 = n3 - this.e + 1;
            a2.a(this.f);
            a2.b(n4);
            this.d = this.e = n3 + 1;
            this.f += n4;
            float f2 = (float)this.d / (float)this.b;
            LoggerProxy.d("UtteranceSubpackager", "mCurrentProgressStartIndex=" + this.d + "--mCurrentAllUtteranceLenght=" + this.b + "--percent=" + f2);
            a2.a(f2);
            a2.a(true);
        } else {
            int n5 = this.b - this.e;
            a2.a(this.f);
            a2.b(n5);
            this.e += n5;
            this.f += n5;
        }
        return a2;
    }

    @Override
    public void remove() {
    }

    public int d() {
        return this.g;
    }

    public void c(int n2) {
        this.g = n2;
    }

    @Override
    public /* synthetic */ A next() {
        return this.c();
    }
}


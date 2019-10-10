/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.m;

import com.baidu.tts.n.A;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class C<T>
extends A<T> {
    private int a = 3;
    private int b = 5;

    public int g() {
        return this.a;
    }

    public void b(int n2) {
        this.a = n2;
    }

    public int h() {
        return this.b;
    }

    public void a(String string) {
        this.b = Integer.parseInt(string);
    }
}


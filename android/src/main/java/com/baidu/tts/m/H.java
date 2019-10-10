/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.m;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.f.A;
import com.baidu.tts.f.E;
import com.baidu.tts.m.I;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class H
extends com.baidu.tts.n.A<H> {
    private E b;
    private int c;
    private int d;
    private String e;
    private int f;
    private int g;
    private byte[] h;
    private A i;
    private I j;
    private TtsError k;
    public int a;

    public int a() {
        return this.d;
    }

    public void a(int n2) {
        this.d = n2;
    }

    public void a(String string) {
        this.e = string;
    }

    public int b() {
        return this.f;
    }

    public void b(int n2) {
        this.f = n2;
    }

    public void c(int n2) {
        this.a = n2;
    }

    public int c() {
        return this.g;
    }

    public void d(int n2) {
        this.g = n2;
    }

    public byte[] d() {
        return this.h;
    }

    public void a(byte[] arrby) {
        this.h = arrby;
    }

    public void e(int n2) {
        this.c = n2;
    }

    public void a(A a2) {
        this.i = a2;
    }

    public I e() {
        return this.j;
    }

    public void a(I i2) {
        this.j = i2;
    }

    public TtsError f() {
        return this.k;
    }

    public void a(TtsError ttsError) {
        this.k = ttsError;
    }

    public E g() {
        return this.b;
    }

    public void a(E e2) {
        this.b = e2;
    }

    public static H b(I i2) {
        H h2 = new H();
        h2.a(i2);
        return h2;
    }

    public static H b(TtsError ttsError) {
        H h2 = new H();
        h2.a(ttsError);
        return h2;
    }

    public static H a(I i2, TtsError ttsError) {
        H h2 = H.b(i2);
        h2.a(ttsError);
        return h2;
    }
}


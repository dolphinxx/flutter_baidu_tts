/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.baidu.tts.m;

import android.text.TextUtils;
import com.baidu.tts.n.A;
import java.io.UnsupportedEncodingException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class I// TextParams
extends A<I> {
    private String a;
    private String b;
    private String c = "0";
    private com.baidu.tts.f.I d;
    private String e;

    public I(String string, String string2) {
        this.b(string);
        this.d(string2);
    }

    public void a() {
        if (!TextUtils.isEmpty((CharSequence)this.e)) {
            this.a = this.e + this.a;
        }
    }

    public String b() {
        return this.e;
    }

    public void a(String string) {
        this.e = string;
    }

    public String c() {
        return this.a;
    }

    public void b(String string) {
        this.a = string;
    }

    public String d() {
        return this.b;
    }

    public void c(String string) {
        this.b = string;
    }

    public byte[] e() {
        byte[] arrby = null;
        try {
            arrby = this.a.getBytes(this.b);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }
        return arrby;
    }

    public String f() {
        return this.c;
    }

    public void d(String string) {
        if (string == null) {
            string = "0";
        }
        this.c = string;
    }

    public com.baidu.tts.f.I g() {
        return this.d;
    }

    public void a(com.baidu.tts.f.I i2) {
        this.d = i2;
    }
}


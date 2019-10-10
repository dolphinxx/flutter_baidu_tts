/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.h.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.f.N;
import com.baidu.tts.h.a.A;

public class B
extends A {
    public B(N n2) {
        super(n2);
    }

    public int a(TtsError ttsError) {
        int n2 = this.a.b();
        return n2;
    }

    public String b(TtsError ttsError) {
        int n2 = ttsError.getCode();
        String string = ttsError.getMessage();
        Throwable throwable = ttsError.getThrowable();
        int n3 = this.a.b();
        String string2 = this.a.c();
        string2 = "(" + n3 + ")" + string2;
        if (string != null) {
            string2 = string2 + "[(" + n2 + ")" + string + "]";
        } else if (n2 != 0) {
            string2 = string2 + "[(" + n2 + ")]";
        }
        if (throwable != null) {
            String string3 = throwable.toString();
            string2 = string2 + "[(cause)" + string3 + "]";
        }
        return string2;
    }
}


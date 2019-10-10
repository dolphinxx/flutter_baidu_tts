/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop.tts;

import com.baidu.tts.f.N;
import com.baidu.tts.h.a.B;

public class TtsError {
    private Throwable a;
    private int b;
    private String c;
    private B d;

    public B getTtsErrorFlyweight() {
        return this.d;
    }

    public void setTtsErrorFlyweight(B ttsErrorFlyweight) {
        this.d = ttsErrorFlyweight;
    }

    public N getErrorEnum() {
        return this.d == null ? null : this.d.a();
    }

    public Throwable getThrowable() {
        return this.a;
    }

    public void setThrowable(Throwable throwable) {
        this.a = throwable;
    }

    public int getCode() {
        return this.b;
    }

    public void setCode(int code) {
        this.b = code;
    }

    public String getMessage() {
        return this.c;
    }

    public void setMessage(String message) {
        this.c = message;
    }

    public int getDetailCode() {
        return this.d != null ? this.d.a(this) : this.b;
    }

    public String getDetailMessage() {
        return this.d != null ? this.d.b(this) : (this.c != null ? this.c : "TtsErrorFlyweight is null");
    }
}


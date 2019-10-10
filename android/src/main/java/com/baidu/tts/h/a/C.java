/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.h.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.f.N;
import com.baidu.tts.h.a.B;
import java.util.Hashtable;

public class C {
    private static volatile C a = null;
    private Hashtable<N, B> b = new Hashtable();

    private C() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static C a() {
        if (a != null) return a;
        Class<C> class_ = C.class;
        synchronized (C.class) {
            if (a != null) return a;
            {
                a = new C();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return a;
        }
    }

    public B a(N n2) {
        B b2 = this.b.get((Object)n2);
        if (b2 == null) {
            b2 = new B(n2);
            this.b.put(n2, b2);
        }
        return b2;
    }

    public TtsError b(N n2) {
        B b2 = this.a(n2);
        TtsError ttsError = new TtsError();
        ttsError.setTtsErrorFlyweight(b2);
        return ttsError;
    }

    public TtsError a(N n2, Throwable throwable) {
        TtsError ttsError = this.b(n2);
        ttsError.setThrowable(throwable);
        return ttsError;
    }

    public TtsError a(N n2, int n3) {
        return this.a(n2, n3, null);
    }

    public TtsError a(N n2, String string) {
        return this.a(n2, 0, string);
    }

    public TtsError a(N n2, int n3, String string) {
        return this.a(n2, n3, string, null);
    }

    public TtsError a(N n2, int n3, String string, Throwable throwable) {
        TtsError ttsError = this.b(n2);
        ttsError.setCode(n3);
        ttsError.setMessage(string);
        ttsError.setThrowable(throwable);
        return ttsError;
    }
}


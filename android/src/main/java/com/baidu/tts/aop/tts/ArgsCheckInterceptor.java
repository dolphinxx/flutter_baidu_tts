/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop.tts;

import com.baidu.tts.aop.AInterceptor;
import com.baidu.tts.aop.AInterceptorHandler;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.aop.ttslistener.TtsListener;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.N;
import com.baidu.tts.h.a.C;
import com.baidu.tts.m.H;
import com.baidu.tts.m.I;
import com.baidu.tts.tools.ResourceTools;
import java.lang.reflect.Method;
import java.util.List;

public class ArgsCheckInterceptor
extends AInterceptor {
    protected void a() {
        this.a.add("speak");
        this.a.add("synthesize");
    }

    protected Object a(Object object, Method method, Object[] arrobject) {
        I i2 = (I)arrobject[0];
        String string = i2.c();
        LoggerProxy.d("ArgsCheckInterceptor", "text=" + string);
        N n2 = ResourceTools.isTextValid(string);
        if (n2 == null) {
            return AInterceptorHandler.DEFAULT;
        }
        return this.a(object, i2, N.Q);
    }

    private Object a(Object object, I i2, N n2) {
        H h2 = H.b(i2);
        TtsError ttsError = C.a().b(n2);
        h2.a(ttsError);
        this.a(object, h2);
        return AInterceptorHandler.END;
    }

    private void a(Object object, H h2) {
        com.baidu.tts.o.a.C c2 = (com.baidu.tts.o.a.C)object;
        TtsListener ttsListener = c2.getTtsListener();
        if (ttsListener != null) {
            ttsListener.onError(h2);
        }
    }
}


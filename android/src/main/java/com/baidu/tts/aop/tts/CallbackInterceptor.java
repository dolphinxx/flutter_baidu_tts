/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop.tts;

import com.baidu.tts.aop.AInterceptor;
import com.baidu.tts.aop.AInterceptorHandler;
import com.baidu.tts.aop.ttslistener.TtsListener;
import com.baidu.tts.aop.ttslistener.TtsListenerFactory;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import java.lang.reflect.Method;
import java.util.List;

public class CallbackInterceptor
extends AInterceptor {
    protected void a() {
        this.a.add("setTtsListener");
    }

    protected Object a(Object object, Method method, Object[] arrobject) {
        LoggerProxy.d("CallbackInterceptor", "method=" + method.getName());
        TtsListener ttsListener = (TtsListener)arrobject[0];
        TtsListenerFactory ttsListenerFactory = new TtsListenerFactory(ttsListener);
        TtsListener ttsListener2 = (TtsListener)ttsListenerFactory.makeProxy();
        arrobject[0] = ttsListener2;
        return AInterceptorHandler.DEFAULT;
    }
}


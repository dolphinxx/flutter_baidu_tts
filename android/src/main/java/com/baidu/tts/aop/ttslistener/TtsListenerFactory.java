/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop.ttslistener;

import com.baidu.tts.aop.AProxyFactory;
import com.baidu.tts.aop.IInterceptor;
import com.baidu.tts.aop.IInterceptorHandler;
import com.baidu.tts.aop.ttslistener.ProgressCorrectInterceptor;
import com.baidu.tts.aop.ttslistener.TtsListener;
import com.baidu.tts.aop.ttslistener.TtsListenerInterceptorHandler;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TtsListenerFactory
extends AProxyFactory<TtsListener> {
    private TtsListener a;

    public TtsListenerFactory(TtsListener ttsListener) {
        this.a = ttsListener;
    }

    @Override
    public TtsListener createProxied() {
        return this.a;
    }

    @Override
    public IInterceptorHandler createInterceptorHandler() {
        TtsListenerInterceptorHandler ttsListenerInterceptorHandler = new TtsListenerInterceptorHandler();
        ttsListenerInterceptorHandler.registerMethods();
        return ttsListenerInterceptorHandler;
    }

    @Override
    public List<IInterceptor> createInterceptors() {
        ArrayList<IInterceptor> arrayList = new ArrayList<IInterceptor>();
        arrayList.add(new ProgressCorrectInterceptor());
        return arrayList;
    }
}


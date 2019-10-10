/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop.tts;

import com.baidu.tts.aop.AProxyFactory;
import com.baidu.tts.aop.IInterceptor;
import com.baidu.tts.aop.IInterceptorHandler;
import com.baidu.tts.aop.tts.ArgsCheckInterceptor;
import com.baidu.tts.aop.tts.CallbackInterceptor;
import com.baidu.tts.aop.tts.ITts;
import com.baidu.tts.aop.tts.OfflineAuthNotificationInterceptor;
import com.baidu.tts.aop.tts.StatisticsInterceptor;
import com.baidu.tts.aop.tts.TtsInterceptorHandler;
import com.baidu.tts.o.a.C;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TtsFactory
extends AProxyFactory<ITts> {
    @Override
    public ITts createProxied() {
        return new C();
    }

    @Override
    public IInterceptorHandler createInterceptorHandler() {
        TtsInterceptorHandler ttsInterceptorHandler = new TtsInterceptorHandler();
        ttsInterceptorHandler.registerMethods();
        return ttsInterceptorHandler;
    }

    @Override
    public List<IInterceptor> createInterceptors() {
        ArrayList<IInterceptor> arrayList = new ArrayList<IInterceptor>();
        arrayList.add(new ArgsCheckInterceptor());
        arrayList.add(new StatisticsInterceptor());
        arrayList.add(new OfflineAuthNotificationInterceptor());
        arrayList.add(new CallbackInterceptor());
        return arrayList;
    }
}


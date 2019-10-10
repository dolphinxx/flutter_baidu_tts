/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop.tts;

import com.baidu.tts.aop.AInterceptor;
import com.baidu.tts.aop.AInterceptorHandler;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import java.lang.reflect.Method;
import java.util.List;

public class StatisticsInterceptor
extends AInterceptor {
    protected void a() {
        this.a.add("speak");
        this.a.add("synthesize");
    }

    protected Object a(Object object, Method method, Object[] arrobject) {
        LoggerProxy.d("StatisticsInterceptor", "statistics");
        return AInterceptorHandler.DEFAULT;
    }
}


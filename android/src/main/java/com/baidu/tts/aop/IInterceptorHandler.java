/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop;

import com.baidu.tts.aop.IInterceptor;
import java.lang.reflect.InvocationHandler;
import java.util.List;

public interface IInterceptorHandler
extends InvocationHandler {
    public void registerMethods();

    public void registerMethod(String var1);

    public void unregisterMethod(String var1);

    public boolean canIntercept(String var1);

    public Object bind(Object var1, List<IInterceptor> var2);
}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop;

import com.baidu.tts.aop.IInterceptor;
import com.baidu.tts.aop.IInterceptorHandler;
import java.util.List;

public interface IProxyFactory<T> {
    public T createProxied();

    public IInterceptorHandler createInterceptorHandler();

    public List<IInterceptor> createInterceptors();

    public T makeProxy();
}


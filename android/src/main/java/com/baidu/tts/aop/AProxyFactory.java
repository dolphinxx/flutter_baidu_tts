/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop;

import com.baidu.tts.aop.IInterceptor;
import com.baidu.tts.aop.IInterceptorHandler;
import com.baidu.tts.aop.IProxyFactory;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AProxyFactory<T>
implements IProxyFactory<T> {
    @Override
    public T makeProxy() {
        T t = this.createProxied();
        IInterceptorHandler iInterceptorHandler = this.createInterceptorHandler();
        List<IInterceptor> list = this.createInterceptors();
        if (iInterceptorHandler != null && list != null) {
            return (T)iInterceptorHandler.bind(t, list);
        }
        return t;
    }
}


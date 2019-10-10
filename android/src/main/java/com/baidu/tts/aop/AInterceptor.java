/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop;

import com.baidu.tts.aop.AInterceptorHandler;
import com.baidu.tts.aop.IInterceptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class AInterceptor
implements IInterceptor {
    protected List<String> a = new ArrayList<String>();

    public AInterceptor() {
        this.a();
    }

    public Object before(Object proxied, Method method, Object[] args) {
        return this.a(method.getName()) ? this.a(proxied, method, args) : AInterceptorHandler.DEFAULT;
    }

    public Object after(Object proxied, Method method, Object[] args, Object methodReturn) {
        return AInterceptorHandler.DEFAULT;
    }

    private boolean a(String string) {
        return this.a.contains(string);
    }

    protected abstract void a();

    protected abstract Object a(Object var1, Method var2, Object[] var3);
}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop;

import java.lang.reflect.Method;

public interface IInterceptor {
    public Object before(Object var1, Method var2, Object[] var3);

    public Object after(Object var1, Method var2, Object[] var3, Object var4);
}


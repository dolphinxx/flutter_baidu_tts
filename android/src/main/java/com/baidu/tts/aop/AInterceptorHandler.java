/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop;

import com.baidu.tts.aop.IInterceptor;
import com.baidu.tts.aop.IInterceptorHandler;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AInterceptorHandler
implements IInterceptorHandler {
    public static final Object DEFAULT = 0;
    public static final Object END = 1;
    protected List<IInterceptor> a;
    protected Object b;
    protected List<String> c = new ArrayList<String>();

    @Override
    public Object bind(Object proxied, List<IInterceptor> interceptors) {
        this.b = proxied;
        this.a = interceptors;
        Class<?> class_ = this.b.getClass();
        Object object = Proxy.newProxyInstance(class_.getClassLoader(), class_.getInterfaces(), (InvocationHandler)this);
        LoggerProxy.d("AInterceptorHandler", "proxy=" + object);
        return object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String string = method.getName();
        boolean bl = this.canIntercept(string);
        if (bl) {
            Object object2 = this.a(this.b, method, args);
            if (object2.equals(END)) {
                return null;
            }
            Object object = method.invoke(this.b, args);
            Object object3 = this.a(this.b, method, args, object);
            LoggerProxy.d("AInterceptorHandler", "afterResult=" + object3);
            return object;
        }
        return method.invoke(this.b, args);
    }

    @Override
    public void registerMethod(String methodName) {
        if (methodName != null) {
            this.c.add(methodName);
        }
    }

    @Override
    public void unregisterMethod(String methodName) {
        if (methodName != null) {
            this.c.remove(methodName);
        }
    }

    @Override
    public boolean canIntercept(String methodName) {
        return this.c.contains(methodName);
    }

    protected Object a(Object object, Method method, Object[] arrobject) {
        Object object2 = DEFAULT;
        int n2 = this.a.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            object2 = this.a.get(i2).before(object, method, arrobject);
            if (!object2.equals(END)) continue;
            return object2;
        }
        return object2;
    }

    protected Object a(Object object, Method method, Object[] arrobject, Object object2) {
        Object object3 = DEFAULT;
        int n2 = this.a.size();
        for (int i2 = n2 - 1; i2 >= 0; --i2) {
            object3 = this.a.get(i2).after(object, method, arrobject, object2);
        }
        return object3;
    }
}


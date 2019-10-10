/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.k;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.k.A;
import com.baidu.tts.k.B;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class C<A extends B<A, R>, R extends com.baidu.tts.k.A> {
    private final ConcurrentMap<A, Future<R>> a = new ConcurrentHashMap<A, Future<R>>();

    public R a(A a2) throws Exception {
        com.baidu.tts.k.A object;
        Future<R> object2 = null;
        A a3 = this.b(a2);
        if (a3 != null) {
            object2 = this.a.get(a3);
        }
        if (object2 != null) {
            LoggerProxy.d("Memorizer", "+ get f=" + object2);
            object = (com.baidu.tts.k.A)object2.get();
            LoggerProxy.d("Memorizer", "- get f=" + object2);
            if (!object.g()) {
                LoggerProxy.d("Memorizer", "arg invalid r=" + object);
                this.a.remove(a3);
                object2 = null;
            }
        }
        Future<R> object3;
        if (object2 == null && (object2 = (Future)this.a.putIfAbsent(a2, (Future<R>)(object3 = new FutureTask(a2)))) == null) {
            object2 = object3;
            LoggerProxy.d("Memorizer", "+ run f=" + object2);
            ((FutureTask)object3).run();
            LoggerProxy.d("Memorizer", "- run f=" + object2);
        }
        try {
            return object2.get();
        }
        catch (ExecutionException executionException) {
            this.a.remove(a2, object2);
            throw (Exception)executionException.getCause();
        }
        catch (Exception exception) {
            this.a.remove(a2, object2);
            throw exception;
        }
    }

    private A b(A a2) {
        Set<A> set = this.a.keySet();
        for (A b2 : set) {
            int n2 = a2.compareTo(b2);
            if (n2 != 0) continue;
            return b2;
        }
        return null;
    }

    public void a() {
        if (this.a != null) {
            this.a.clear();
        }
    }
}


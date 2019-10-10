/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.client.model;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.L;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BasicHandler<T> {
    private FutureTask<T> a;

    public BasicHandler(FutureTask<T> futureTask) {
        this.a = futureTask;
    }

    public void start() {
        Thread thread = new Thread(this.a);
        thread.start();
    }

    public boolean cancel() {
        return this.a.cancel(true);
    }

    public T get() {
        T t = null;
        try {
            LoggerProxy.d("BasicHandler", "before get");
            t = this.a.get(L.a.a(), TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException interruptedException) {
            LoggerProxy.d("BasicHandler", interruptedException.toString());
        }
        catch (ExecutionException executionException) {
            LoggerProxy.d("BasicHandler", executionException.getCause().toString());
        }
        catch (TimeoutException timeoutException) {
            LoggerProxy.d("BasicHandler", timeoutException.toString());
        }
        return t;
    }
}


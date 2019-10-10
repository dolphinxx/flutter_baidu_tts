/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.c;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class A
extends ThreadPoolExecutor {
    public A(int n2, String string) {
        this(n2, string, (RejectedExecutionHandler)new a());
    }

    public A(int n2, String string, RejectedExecutionHandler rejectedExecutionHandler) {
        this(n2, new com.baidu.tts.g.a.A(string), rejectedExecutionHandler);
    }

    public A(int n2, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        this(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(n2), threadFactory, rejectedExecutionHandler);
    }

    public A(int n2, int n3, long l2, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(n2, n3, l2, timeUnit, blockingQueue, threadFactory, rejectedExecutionHandler);
    }

    public static class a
    implements RejectedExecutionHandler {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e2) {
            LoggerProxy.d("LimitQueueThreadPoolExecutor", "rejectedExecution");
            if (!e2.isShutdown()) {
                try {
                    e2.getQueue().put(r);
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}


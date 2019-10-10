/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.g.a;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import java.util.concurrent.ThreadFactory;

public class A
implements ThreadFactory {
    private String a;
    private int b;

    public A(String string) {
        this.a = string;
    }

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        ++this.b;
        String string = this.a + "(" + this.b + ")";
        thread.setName(string);
        LoggerProxy.d("NameThreadFactory", "threadName=" + string);
        return thread;
    }
}


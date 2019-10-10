/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.p;

import android.content.Context;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.p.C;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class B {
    private C a;
    private Context b;
    private FutureTask<Integer> c;

    public B(Context context) {
        this.b = context;
        this.a = new C(context);
    }

    public void a() {
        this.c = this.a.a();
        int n2 = -1;
        try {
            n2 = this.c.get();
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        catch (ExecutionException executionException) {
            executionException.printStackTrace();
        }
        LoggerProxy.d("StatisticsClient", "Statistics uploade result=" + n2);
    }

    public void b() {
        if (this.c != null) {
            this.a.b();
        }
    }
}


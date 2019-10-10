/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.d.a;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.d.a.B;
import com.baidu.tts.d.a.C;
import java.util.concurrent.Future;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class E {
    private Future<Void> a;
    private B.a b;

    public void a(Future<Void> future) {
        this.a = future;
    }

    public void a(B.a a2) {
        this.b = a2;
    }

    public void a() {
        LoggerProxy.d("EngineDownloadHandler", "before stop");
        try {
            LoggerProxy.d("EngineDownloadHandler", "stop fileId=" + this.b.c().a());
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (this.a != null) {
            boolean bl = this.a.cancel(true);
            LoggerProxy.d("EngineDownloadHandler", "unDone = " + bl);
        }
        if (this.b != null) {
            this.b.b();
        }
        LoggerProxy.d("EngineDownloadHandler", "after stop");
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.d;

import com.baidu.tts.client.model.DownloadHandler;
import com.baidu.tts.d.B;
import com.baidu.tts.d.C;
import com.baidu.tts.l.A;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class D {
    private C a = C.a();
    private ConcurrentMap<String, DownloadHandler> b = new ConcurrentHashMap<String, DownloadHandler>();
    private A c;

    public void a() {
        this.a.g();
    }

    public void b() {
        this.c();
        this.a.e();
    }

    private void c() {
        Collection<DownloadHandler> collection = this.b.values();
        for (DownloadHandler downloadHandler : collection) {
            downloadHandler.stop();
        }
    }

    public void a(A a2) {
        this.c = a2;
        this.a.a(a2);
    }

    public DownloadHandler a(B b2) {
        String string;
        DownloadHandler downloadHandler;
        if (b2 != null && b2.b() && (downloadHandler = this.a(string = b2.a())) != null) {
            downloadHandler.reset(b2);
            return this.a.a(downloadHandler);
        }
        return null;
    }

    public synchronized DownloadHandler a(String string) {
        try {
            DownloadHandler downloadHandler = (DownloadHandler)this.b.get(string);
            if (downloadHandler == null) {
                downloadHandler = new DownloadHandler(this.c);
                this.b.put(string, downloadHandler);
            }
            return downloadHandler;
        }
        catch (Exception exception) {
            return null;
        }
    }
}


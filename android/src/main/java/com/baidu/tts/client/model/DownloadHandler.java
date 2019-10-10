/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.client.model;

import android.content.Context;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.model.OnDownloadListener;
import com.baidu.tts.client.model.RecordData;
import com.baidu.tts.client.model.Statistics;
import com.baidu.tts.d.A;
import com.baidu.tts.d.B;
import com.baidu.tts.d.b.D;
import java.util.UUID;
import java.util.concurrent.Future;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DownloadHandler {
    public static final int DOWNLOAD_SUCCESS = 0;
    private B a;
    private Future<A> b;
    private TtsError c;
    private com.baidu.tts.d.b.A d = com.baidu.tts.d.b.A.a();
    private volatile boolean e = false;
    private com.baidu.tts.l.A f;
    private RecordData g = null;
    private String h = UUID.randomUUID().toString();

    public DownloadHandler(com.baidu.tts.l.A modelMediator) {
        this.f = modelMediator;
    }

    public String getModelId() {
        return this.a.a();
    }

    private OnDownloadListener a() {
        return this.a.c();
    }

    public void setCheckFuture(Future<A> checkFuture) {
        this.b = checkFuture;
    }

    public void setTtsError(TtsError ttsError) {
        this.c = ttsError;
    }

    public TtsError getTtsError() {
        return this.c;
    }

    public int getErrorCode() {
        return this.getErrorCode(this.c);
    }

    public int getErrorCode(TtsError ttsError) {
        return ttsError != null ? ttsError.getDetailCode() : 0;
    }

    public String getErrorMessage() {
        return this.getErrorMessage(this.c);
    }

    public String getErrorMessage(TtsError ttsError) {
        return ttsError != null ? ttsError.getDetailMessage() : null;
    }

    public B getDownloadParams() {
        return this.a;
    }

    public void setDownloadParams(B downloadParams) {
        this.a = downloadParams;
    }

    public void reset(B params) {
        this.setDownloadParams(params);
        this.reset();
    }

    public synchronized void reset() {
        LoggerProxy.d("DownloadHandler", "reset");
        this.e = false;
    }

    public synchronized void stop() {
        LoggerProxy.d("DownloadHandler", "stop");
        this.e = true;
        if (this.b != null) {
            this.b.cancel(true);
            this.b = null;
        }
        this.d.a(this);
        this.a.a((OnDownloadListener)null);
    }

    public void updateStart(D model) {
        this.a(model.g());
    }

    public void updateProgress(D model) {
        long l2 = model.h();
        long l3 = model.c();
        this.a(model.g(), l2, l3);
    }

    public void updateFinish(D model, TtsError ttsError) {
        this.updateFinish(model.g(), ttsError);
    }

    public void updateFinish(String modelId, TtsError ttsError) {
        this.setTtsError(ttsError);
        this.a(modelId, this.getErrorCode());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(String string) {
        Object object;
        this.g = new RecordData(this.f);
        synchronized (this) {
            if (Statistics.isStatistics) {
                object = System.currentTimeMillis() + "";
                this.g.setStartInfo(this.h, string, (String)object);
            }
        }
        OnDownloadListener object2 = this.a();
        if (object2 != null) {
            object = this;
            synchronized (object) {
                if (!this.e) {
                    object2.onStart(string);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(String string, long l2, long l3) {
        OnDownloadListener onDownloadListener = this.a();
        if (onDownloadListener != null) {
            DownloadHandler downloadHandler = this;
            synchronized (downloadHandler) {
                if (!this.e) {
                    onDownloadListener.onProgress(string, l2, l3);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(String string, int n2) {
        DownloadHandler downloadHandler;
        OnDownloadListener object;
        if (Statistics.isStatistics) {
            this.g.setEndInfo(this.h, string, n2, System.currentTimeMillis() + "");
        }
        if ((object = this.a()) != null) {
            downloadHandler = this;
            synchronized (downloadHandler) {
                if (!this.e) {
                    object.onFinish(string, n2);
                    this.a.a((OnDownloadListener)null);
                }
            }
        }
        downloadHandler = this;
        synchronized (downloadHandler) {
            Object object2;
            if (Statistics.isStatistics) {
                object2 = System.currentTimeMillis() + "";
                this.g.setEndInfo(this.h, string, n2, (String)object2);
            }
            if (Statistics.isStatistics) {
                object2 = new Statistics(this.f.d());
                int n3 = ((Statistics)object2).start();
                LoggerProxy.d("DownloadHandler", " statistics ret=" + n3);
            }
        }
    }
}


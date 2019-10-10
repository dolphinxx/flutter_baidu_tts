/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 */
package com.baidu.tts.d.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.d.a.C;
import com.baidu.tts.f.N;
import com.baidu.tts.loopj.RangeFileAsyncHttpResponseHandler;
import java.io.File;
import org.apache.http.Header;

public class G
extends RangeFileAsyncHttpResponseHandler {
    private C a;

    public G(File file, C c2) {
        super(file);
        this.a = c2;
    }

    public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
        Object object;
        String string = null;
        if (throwable != null) {
            object = throwable.getCause();
            string = object == null ? throwable.getMessage() : ((Throwable)object).getMessage();
        }
        LoggerProxy.d("ModelFileResponseHandler", "onFailure statuscode=" + statusCode + "--msg=" + string);
        object = com.baidu.tts.h.a.C.a().a(N.ac, statusCode, "download failure", throwable);
        this.a.a((TtsError)object);
    }

    public void onSuccess(int statusCode, Header[] headers, File file) {
        LoggerProxy.d("ModelFileResponseHandler", "onSuccess");
        this.a.e();
    }

    public void onProgress(long bytesWritten, long totalSize) {
        this.a.a(bytesWritten, totalSize);
    }
}


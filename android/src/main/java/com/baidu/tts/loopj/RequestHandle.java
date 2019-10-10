/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.baidu.tts.loopj;

import android.os.Looper;
import com.baidu.tts.loopj.AsyncHttpRequest;
import java.lang.ref.WeakReference;

public class RequestHandle {
    private final WeakReference<AsyncHttpRequest> request;

    public RequestHandle(AsyncHttpRequest request) {
        this.request = new WeakReference<AsyncHttpRequest>(request);
    }

    public boolean cancel(final boolean mayInterruptIfRunning) {
        final AsyncHttpRequest asyncHttpRequest = (AsyncHttpRequest)this.request.get();
        if (asyncHttpRequest != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable(){

                    public void run() {
                        asyncHttpRequest.cancel(mayInterruptIfRunning);
                    }
                }).start();
                return true;
            }
            return asyncHttpRequest.cancel(mayInterruptIfRunning);
        }
        return false;
    }

    public boolean isFinished() {
        AsyncHttpRequest asyncHttpRequest = (AsyncHttpRequest)this.request.get();
        return asyncHttpRequest == null || asyncHttpRequest.isDone();
    }

    public boolean isCancelled() {
        AsyncHttpRequest asyncHttpRequest = (AsyncHttpRequest)this.request.get();
        return asyncHttpRequest == null || asyncHttpRequest.isCancelled();
    }

    public boolean shouldBeGarbageCollected() {
        boolean bl;
        boolean bl2 = bl = this.isCancelled() || this.isFinished();
        if (bl) {
            this.request.clear();
        }
        return bl;
    }

    public RequestHandle setTag(Object tag) {
        AsyncHttpRequest asyncHttpRequest = (AsyncHttpRequest)this.request.get();
        if (asyncHttpRequest != null) {
            asyncHttpRequest.setRequestTag(tag);
        }
        return this;
    }

    public Object getTag() {
        AsyncHttpRequest asyncHttpRequest = (AsyncHttpRequest)this.request.get();
        return asyncHttpRequest == null ? null : asyncHttpRequest.getTag();
    }

}


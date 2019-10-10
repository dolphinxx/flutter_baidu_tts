/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.conn.scheme.SchemeRegistry
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.protocol.HttpContext
 */
package com.baidu.tts.loopj;

import android.content.Context;
import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.AsyncHttpRequest;
import com.baidu.tts.loopj.RequestHandle;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

public class SyncHttpClient
extends AsyncHttpClient {
    private RequestHandle mRequestHandle;

    public SyncHttpClient() {
        super(false, 80, 443);
    }

    public SyncHttpClient(int httpPort) {
        super(false, httpPort, 443);
    }

    public SyncHttpClient(int httpPort, int httpsPort) {
        super(false, httpPort, httpsPort);
    }

    public SyncHttpClient(boolean fixNoHttpResponseException, int httpPort, int httpsPort) {
        super(fixNoHttpResponseException, httpPort, httpsPort);
    }

    public SyncHttpClient(SchemeRegistry schemeRegistry) {
        super(schemeRegistry);
    }

    protected RequestHandle sendRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
        if (contentType != null) {
            uriRequest.addHeader("Content-Type", contentType);
        }
        responseHandler.setUseSynchronousMode(true);
        AsyncHttpRequest asyncHttpRequest = this.newAsyncHttpRequest(client, httpContext, uriRequest, contentType, responseHandler, context);
        this.mRequestHandle = new RequestHandle(asyncHttpRequest);
        asyncHttpRequest.run();
        return null;
    }

    public void stop() {
        if (this.mRequestHandle != null) {
            this.mRequestHandle.cancel(true);
            this.mRequestHandle = null;
        }
    }
}


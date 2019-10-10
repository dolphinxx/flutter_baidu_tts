/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpResponse
 */
package com.baidu.tts.loopj;

import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class BlackholeHttpResponseHandler
extends AsyncHttpResponseHandler {
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
    }

    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
    }

    public void onProgress(long bytesWritten, long totalSize) {
    }

    public void onCancel() {
    }

    public void onFinish() {
    }

    public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
    }

    public void onPreProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
    }

    public void onRetry(int retryNo) {
    }

    public void onStart() {
    }

    public void onUserException(Throwable error) {
    }
}


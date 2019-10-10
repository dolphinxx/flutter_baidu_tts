/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpResponseException
 */
package com.baidu.tts.b.a.b;

import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;

public abstract class G
extends AsyncHttpResponseHandler {
    private String a;
    private HttpEntity b;

    public void sendResponseMessage(HttpResponse response) throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            StatusLine statusLine = response.getStatusLine();
            this.b = response.getEntity();
            this.a = this.a(this.b);
            if (!Thread.currentThread().isInterrupted()) {
                if (statusLine.getStatusCode() >= 300) {
                    this.sendFailureMessage(statusLine.getStatusCode(), response.getAllHeaders(), null, (Throwable)new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase()));
                } else {
                    this.sendSuccessMessage(statusLine.getStatusCode(), response.getAllHeaders(), null);
                }
            }
        }
    }

    String a(HttpEntity httpEntity) {
        String string;
        Header header = httpEntity.getContentType();
        if (header != null && "application/json".equals(string = header.getValue())) {
            return "application/json";
        }
        return null;
    }

    public void onSuccess(int statusCode, Header[] headers, byte[] flag) {
        this.a(statusCode, headers, this.a, this.b);
    }

    public abstract void a(int var1, Header[] var2, String var3, HttpEntity var4);

    public void onFailure(int statusCode, Header[] headers, byte[] flag, Throwable error) {
        this.a(statusCode, headers, this.a, this.b, error);
    }

    public abstract void a(int var1, Header[] var2, String var3, HttpEntity var4, Throwable var5);
}


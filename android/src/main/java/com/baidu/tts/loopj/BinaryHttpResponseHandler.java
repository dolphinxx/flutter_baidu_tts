/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  org.apache.http.Header
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpResponseException
 */
package com.baidu.tts.loopj;

import android.os.Looper;
import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import com.baidu.tts.loopj.LogInterface;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;

public abstract class BinaryHttpResponseHandler
extends AsyncHttpResponseHandler {
    private static final String LOG_TAG = "BinaryHttpRH";
    private String[] mAllowedContentTypes = new String[]{"application/octet-stream", "image/jpeg", "image/png", "image/gif"};

    public String[] getAllowedContentTypes() {
        return this.mAllowedContentTypes;
    }

    public BinaryHttpResponseHandler() {
    }

    public BinaryHttpResponseHandler(String[] allowedContentTypes) {
        if (allowedContentTypes != null) {
            this.mAllowedContentTypes = allowedContentTypes;
        } else {
            AsyncHttpClient.log.e(LOG_TAG, "Constructor passed allowedContentTypes was null !");
        }
    }

    public BinaryHttpResponseHandler(String[] allowedContentTypes, Looper looper) {
        super(looper);
        if (allowedContentTypes != null) {
            this.mAllowedContentTypes = allowedContentTypes;
        } else {
            AsyncHttpClient.log.e(LOG_TAG, "Constructor passed allowedContentTypes was null !");
        }
    }

    public abstract void onSuccess(int var1, Header[] var2, byte[] var3);

    public abstract void onFailure(int var1, Header[] var2, byte[] var3, Throwable var4);

    public final void sendResponseMessage(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        Header[] arrheader = response.getHeaders("Content-Type");
        if (arrheader.length != 1) {
            this.sendFailureMessage(statusLine.getStatusCode(), response.getAllHeaders(), null, (Throwable)new HttpResponseException(statusLine.getStatusCode(), "None, or more than one, Content-Type Header found!"));
            return;
        }
        Header header = arrheader[0];
        boolean bl = false;
        for (String string : this.getAllowedContentTypes()) {
            try {
                if (!Pattern.matches(string, header.getValue())) continue;
                bl = true;
            }
            catch (PatternSyntaxException patternSyntaxException) {
                AsyncHttpClient.log.e("BinaryHttpRH", "Given pattern is not valid: " + string, patternSyntaxException);
            }
        }
        if (!bl) {
            this.sendFailureMessage(statusLine.getStatusCode(), response.getAllHeaders(), null, (Throwable)new HttpResponseException(statusLine.getStatusCode(), "Content-Type (" + header.getValue() + ") not allowed!"));
            return;
        }
        super.sendResponseMessage(response);
    }
}


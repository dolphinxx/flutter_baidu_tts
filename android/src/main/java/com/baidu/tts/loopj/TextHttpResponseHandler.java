/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 */
package com.baidu.tts.loopj;

import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import com.baidu.tts.loopj.LogInterface;
import java.io.UnsupportedEncodingException;
import org.apache.http.Header;

public abstract class TextHttpResponseHandler
extends AsyncHttpResponseHandler {
    private static final String LOG_TAG = "TextHttpRH";

    public TextHttpResponseHandler() {
        this("UTF-8");
    }

    public TextHttpResponseHandler(String encoding) {
        this.setCharset(encoding);
    }

    public abstract void onFailure(int var1, Header[] var2, String var3, Throwable var4);

    public abstract void onSuccess(int var1, Header[] var2, String var3);

    public void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
        this.onSuccess(statusCode, headers, TextHttpResponseHandler.getResponseString(responseBytes, this.getCharset()));
    }

    public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
        this.onFailure(statusCode, headers, TextHttpResponseHandler.getResponseString(responseBytes, this.getCharset()), throwable);
    }

    public static String getResponseString(byte[] stringBytes, String charset) {
        try {
            String string;
            String string2 = string = stringBytes == null ? null : new String(stringBytes, charset);
            if (string != null && string.startsWith("\ufeff")) {
                return string.substring(1);
            }
            return string;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            AsyncHttpClient.log.e("TextHttpRH", "Encoding response into string failed", unsupportedEncodingException);
            return null;
        }
    }
}


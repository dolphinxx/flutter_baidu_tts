/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.json.JSONObject
 */
package com.baidu.tts.p;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.f.G;
import com.baidu.tts.f.N;
import com.baidu.tts.h.a.C;
import com.baidu.tts.loopj.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONObject;

public class D
extends JsonHttpResponseHandler {
    private TtsError a;
    private int b = -1;

    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        String string = null;
        if (errorResponse != null) {
            string = errorResponse.toString();
        }
        this.a = C.a().a(N.ac, statusCode, string, throwable);
    }

    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        int n2 = response.optInt(G.v.a());
        String string = response.optString(G.w.a());
        if (n2 == 0) {
            this.b = n2;
        } else {
            this.a = C.a().a(N.ad, n2, string);
        }
    }

    public int a() {
        return this.b;
    }
}


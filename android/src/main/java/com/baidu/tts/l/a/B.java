/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.baidu.tts.l.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.f.G;
import com.baidu.tts.f.N;
import com.baidu.tts.h.a.C;
import com.baidu.tts.loopj.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class B
extends JsonHttpResponseHandler {
    private TtsError a;
    private ModelBags b;

    public ModelBags a() {
        return this.b;
    }

    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        LoggerProxy.d("GetListHttpHandler", "onFailure1");
        this.a = C.a().a(N.ac, statusCode, responseString, throwable);
    }

    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        LoggerProxy.d("GetListHttpHandler", "onFailure2");
        String string = null;
        if (errorResponse != null) {
            string = errorResponse.toString();
        }
        this.a = C.a().a(N.ac, statusCode, string, throwable);
    }

    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        LoggerProxy.d("GetListHttpHandler", "onSuccess response=" + (Object)response);
        int n2 = response.optInt(G.v.a());
        String string = response.optString(G.w.a());
        if (n2 == 0 || n2 == -1004) {
            JSONArray jSONArray = response.optJSONArray(G.n.b());
            this.b = new ModelBags();
            this.b.parseJson(jSONArray);
        } else {
            this.a = C.a().a(N.ad, n2, string);
        }
    }
}


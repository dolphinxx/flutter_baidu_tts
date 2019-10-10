/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.entity.StringEntity
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.l.a;

import android.content.Context;
import android.text.TextUtils;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.G;
import com.baidu.tts.f.O;
import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import com.baidu.tts.loopj.RequestHandle;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.SyncHttpClient;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class A
implements Callable<String> {
    private RequestHandle a;
    private JSONArray b;

    public A(JSONArray jSONArray) {
        this.b = jSONArray;
    }

    public String a() throws Exception {
        SyncHttpClient syncHttpClient = new SyncHttpClient(true, 80, 443);
        String string = O.b.a();
        StringEntity stringEntity = this.b();
        final String[] arrstring = new String[]{""};
        this.a = syncHttpClient.post(null, string, (HttpEntity)stringEntity, "application/json", new AsyncHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String string = new String(responseBody);
                LoggerProxy.d("GetServerModelsWork", "statusCode: " + statusCode + " response: " + new String(responseBody));
                if (!TextUtils.isEmpty((CharSequence)string)) {
                    try {
                        JSONObject jSONObject = new JSONObject(string);
                        String string2 = jSONObject.optString("err_no");
                        if ("0".equals(string2)) {
                            arrstring[0] = jSONObject.optString("info");
                        }
                    }
                    catch (Exception exception) {
                        LoggerProxy.d("GetServerModelsWork", "parse:" + exception.toString());
                    }
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                LoggerProxy.d("GetServerModelsWork", "onFileure statusCode:" + statusCode);
            }
        });
        return arrstring[0];
    }

    private StringEntity b() throws UnsupportedEncodingException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(G.t.a(), (Object)"checkUpdate");
            jSONObject.put(G.u.a(), (Object)this.b);
            StringEntity stringEntity = new StringEntity(jSONObject.toString());
            return stringEntity;
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    @Override
    public /* synthetic */ String call() throws Exception {
        return this.a();
    }

}


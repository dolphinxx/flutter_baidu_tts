/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.apache.http.HttpEntity
 *  org.apache.http.entity.StringEntity
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.l.a;

import android.content.Context;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.model.Conditions;
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.f.G;
import com.baidu.tts.f.O;
import com.baidu.tts.l.a.B;
import com.baidu.tts.loopj.RequestHandle;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.SyncHttpClient;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class H
implements Callable<ModelBags> {
    private Conditions a;
    private RequestHandle b;

    public H(Conditions conditions) {
        this.a = conditions;
    }

    public ModelBags a() throws Exception {
        SyncHttpClient syncHttpClient = new SyncHttpClient(true, 80, 443);
        String string = O.b.a();
        StringEntity stringEntity = this.b();
        B b2 = new B();
        this.b = syncHttpClient.post(null, string, (HttpEntity)stringEntity, "application/json", b2);
        ModelBags modelBags = b2.a();
        return modelBags;
    }

    private StringEntity b() throws UnsupportedEncodingException {
        JSONObject jSONObject = this.a.getJSONConditions();
        try {
            jSONObject.put(G.d.b(), (Object)"1");
            jSONObject.put(G.t.a(), (Object)"getList");
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
        String string = jSONObject.toString();
        LoggerProxy.d("GetServerModelsWork", "getlist params=" + string);
        StringEntity stringEntity = new StringEntity(string);
        return stringEntity;
    }

    @Override
    public /* synthetic */ ModelBags call() throws Exception {
        return this.a();
    }
}


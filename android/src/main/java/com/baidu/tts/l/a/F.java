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
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.f.G;
import com.baidu.tts.f.O;
import com.baidu.tts.jni.EmbeddedSynthesizerEngine;
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
public class F
implements Callable<ModelBags> {
    private RequestHandle a;

    public ModelBags a() throws Exception {
        SyncHttpClient syncHttpClient = new SyncHttpClient(true, 80, 443);
        String string = O.b.a();
        StringEntity stringEntity = this.b();
        B b2 = new B();
        this.a = syncHttpClient.post(null, string, (HttpEntity)stringEntity, "application/json", b2);
        ModelBags modelBags = b2.a();
        return modelBags;
    }

    private StringEntity b() throws UnsupportedEncodingException {
        String string = EmbeddedSynthesizerEngine.bdTTSGetEngineParam();
        try {
            JSONObject jSONObject = new JSONObject(string);
            jSONObject.put(G.t.a(), (Object)"getDefaultList");
            StringEntity stringEntity = new StringEntity(jSONObject.toString());
            return stringEntity;
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    @Override
    public /* synthetic */ ModelBags call() throws Exception {
        return this.a();
    }
}


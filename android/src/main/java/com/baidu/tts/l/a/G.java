/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.apache.http.HttpEntity
 *  org.apache.http.entity.StringEntity
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.l.a;

import android.content.Context;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.model.ModelFileBags;
import com.baidu.tts.f.O;
import com.baidu.tts.l.a.E;
import com.baidu.tts.loopj.RequestHandle;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.SyncHttpClient;
import com.baidu.tts.tools.JsonTool;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.concurrent.Callable;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class G
implements Callable<ModelFileBags> {
    private RequestHandle a;
    private Set<String> b;

    public G(Set<String> set) {
        this.b = set;
    }

    public ModelFileBags a() throws Exception {
        SyncHttpClient syncHttpClient = new SyncHttpClient(true, 80, 443);
        String string = O.b.a() + "https=1";
        StringEntity stringEntity = this.b();
        E e2 = new E();
        this.a = syncHttpClient.post(null, string, (HttpEntity)stringEntity, "application/json", e2);
        ModelFileBags modelFileBags = e2.a();
        return modelFileBags;
    }

    private StringEntity b() throws UnsupportedEncodingException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.baidu.tts.f.G.t.a(), (Object)"getURL");
            jSONObject.put(com.baidu.tts.f.G.d.b(), (Object)"1");
            JSONArray jSONArray = JsonTool.fromSetToJson(this.b);
            jSONObject.put(com.baidu.tts.f.G.i.b(), (Object)jSONArray);
            String string = jSONObject.toString();
            LoggerProxy.d("GetServerModelFileInfosWork", "geturl params=" + string);
            StringEntity stringEntity = new StringEntity(string);
            return stringEntity;
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    @Override
    public /* synthetic */ ModelFileBags call() throws Exception {
        return this.a();
    }
}


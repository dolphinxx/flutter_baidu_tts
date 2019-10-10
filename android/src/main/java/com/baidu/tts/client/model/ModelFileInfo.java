/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.client.model;

import android.content.Context;
import com.baidu.tts.f.G;
import com.baidu.tts.tools.ResourceTools;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ModelFileInfo {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;

    public String getServerid() {
        return this.a;
    }

    public void setServerid(String serverid) {
        this.a = serverid;
    }

    public String getLength() {
        return this.b;
    }

    public void setLength(String length) {
        this.b = length;
    }

    public String getMd5() {
        return this.c;
    }

    public void setMd5(String md5) {
        this.c = md5;
    }

    public String getName() {
        return this.d;
    }

    public void setName(String name) {
        this.d = name;
    }

    public String getAbsPath() {
        return this.e;
    }

    public void setAbsPath(String absPath) {
        this.e = absPath;
    }

    public String getUrl() {
        return this.f;
    }

    public void setUrl(String url) {
        this.f = url;
    }

    public void generateAbsPath(Context context) {
        this.e = ResourceTools.getModelFileAbsName(context, this.d);
    }

    public void setMap(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            this.a = map.get(G.i.b());
            this.b = map.get(G.g.b());
            this.c = map.get(G.f.b());
            this.d = map.get(G.o.b());
            this.e = map.get(G.h.b());
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt(G.i.b(), (Object)this.a);
            jSONObject.putOpt(G.g.b(), (Object)this.b);
            jSONObject.putOpt(G.f.b(), (Object)this.c);
            jSONObject.putOpt(G.o.b(), (Object)this.d);
            jSONObject.putOpt(G.h.b(), (Object)this.e);
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
        return jSONObject;
    }

    public void parseJson(JSONObject jo) {
        this.a = jo.optString(G.i.b());
        this.b = jo.optString(G.g.b());
        this.c = jo.optString(G.f.b());
        this.d = jo.optString(G.o.b());
        this.f = jo.optString(G.e.b());
    }
}


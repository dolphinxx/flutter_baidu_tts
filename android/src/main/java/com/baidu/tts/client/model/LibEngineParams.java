/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.client.model;

import com.baidu.tts.f.G;
import com.baidu.tts.tools.JsonTool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LibEngineParams {
    private String a;
    private String b;
    private String[] c;
    private String[] d;
    private String[] e;

    public LibEngineParams(String params) {
        this.a = params;
        try {
            JSONObject jSONObject = new JSONObject(params);
            this.b = jSONObject.optString(G.ab.b());
            JSONArray jSONArray = jSONObject.optJSONArray(G.k.b());
            this.c = JsonTool.getStringarray(jSONArray);
            JSONArray jSONArray2 = jSONObject.optJSONArray(G.G.b());
            this.d = JsonTool.getStringarray(jSONArray2);
            JSONArray jSONArray3 = jSONObject.optJSONArray(G.l.b());
            this.e = JsonTool.getStringarray(jSONArray3);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public String getVersion() {
        return this.b;
    }

    public void setVersion(String version) {
        this.b = version;
    }

    public String[] getDomain() {
        return this.c;
    }

    public void setDomain(String[] domain) {
        this.c = domain;
    }

    public String[] getLanguage() {
        return this.d;
    }

    public void setLanguage(String[] language) {
        this.d = language;
    }

    public String[] getQuality() {
        return this.e;
    }

    public void setQuality(String[] quality) {
        this.e = quality;
    }

    public String getResult() {
        return this.a;
    }

    public JSONObject getJsonResult() {
        try {
            return new JSONObject(this.a);
        }
        catch (JSONException jSONException) {
            return null;
        }
    }
}


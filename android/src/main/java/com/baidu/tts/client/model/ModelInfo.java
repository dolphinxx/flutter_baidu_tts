/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.client.model;

import com.baidu.tts.f.G;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ModelInfo {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;

    public String getServerId() {
        return this.a;
    }

    public void setServerId(String serverId) {
        this.a = serverId;
    }

    public String getName() {
        return this.b;
    }

    public void setName(String name) {
        this.b = name;
    }

    public String getVersionMin() {
        return this.c;
    }

    public void setVersionMin(String versionMin) {
        this.c = versionMin;
    }

    public String getVersionMax() {
        return this.d;
    }

    public void setVersionMax(String versionMax) {
        this.d = versionMax;
    }

    public String getLanguage() {
        return this.e;
    }

    public void setLanguage(String language) {
        this.e = language;
    }

    public String getGender() {
        return this.f;
    }

    public void setGender(String gender) {
        this.f = gender;
    }

    public String getSpeaker() {
        return this.g;
    }

    public void setSpeaker(String speaker) {
        this.g = speaker;
    }

    public String getDomain() {
        return this.h;
    }

    public void setDomain(String domain) {
        this.h = domain;
    }

    public String getQuality() {
        return this.i;
    }

    public void setQuality(String quality) {
        this.i = quality;
    }

    public String getTextDataId() {
        return this.j;
    }

    public void setTextDataId(String textDataId) {
        this.j = textDataId;
    }

    public String getSpeechDataId() {
        return this.k;
    }

    public void setSpeechDataId(String speechDataId) {
        this.k = speechDataId;
    }

    public void setMap(Map<String, String> map) {
        if (map != null) {
            this.a = map.get(G.i.b());
            this.b = map.get(G.o.b());
            this.c = map.get(G.p.b());
            this.d = map.get(G.q.b());
            this.e = map.get(G.G.b());
            this.f = map.get(G.j.b());
            this.g = map.get(G.L.b());
            this.h = map.get(G.k.b());
            this.i = map.get(G.l.b());
            this.j = map.get(G.r.b());
            this.k = map.get(G.s.b());
        }
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt(G.i.b(), (Object)this.a);
            jSONObject.putOpt(G.o.b(), (Object)this.b);
            jSONObject.putOpt(G.p.b(), (Object)this.c);
            jSONObject.putOpt(G.q.b(), (Object)this.d);
            jSONObject.putOpt(G.G.b(), (Object)this.e);
            jSONObject.putOpt(G.j.b(), (Object)this.f);
            jSONObject.putOpt(G.L.b(), (Object)this.g);
            jSONObject.putOpt(G.k.b(), (Object)this.h);
            jSONObject.putOpt(G.l.b(), (Object)this.i);
            jSONObject.putOpt(G.r.b(), (Object)this.j);
            jSONObject.putOpt(G.s.b(), (Object)this.k);
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
        return jSONObject;
    }

    public void parseJson(JSONObject jo) {
        this.a = jo.optString(G.i.b());
        this.b = jo.optString(G.o.b());
        this.c = jo.optString(G.p.b());
        this.d = jo.optString(G.q.b());
        this.e = jo.optString(G.G.b());
        this.f = jo.optString(G.j.b());
        this.g = jo.optString(G.L.b());
        this.h = jo.optString(G.k.b());
        this.i = jo.optString(G.l.b());
        this.j = jo.optString(G.r.b());
        this.k = jo.optString(G.s.b());
    }
}


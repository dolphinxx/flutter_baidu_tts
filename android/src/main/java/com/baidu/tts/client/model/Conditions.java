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
import com.baidu.tts.tools.DataTool;
import com.baidu.tts.tools.JsonTool;
import com.baidu.tts.tools.StringTool;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Conditions {
    private Set<String> a;
    private String b;
    private Set<String> c;
    private Set<String> d;
    private Set<String> e;
    private Set<String> f;
    private Set<String> g;

    public Set<String> getModelIds() {
        return this.a;
    }

    public void setModelIds(Set<String> modelIds) {
        this.a = modelIds;
    }

    public Set<String> getLanguages() {
        return this.c;
    }

    public void setLanguages(Set<String> languages) {
        this.c = languages;
    }

    public Set<String> getGenders() {
        return this.d;
    }

    public void setGenders(Set<String> genders) {
        this.d = genders;
    }

    public Set<String> getSpeakers() {
        return this.e;
    }

    public void setSpeakers(Set<String> speakers) {
        this.e = speakers;
    }

    public Set<String> getDomains() {
        return this.f;
    }

    public void setDomains(Set<String> domains) {
        this.f = domains;
    }

    public Set<String> getQualitys() {
        return this.g;
    }

    public void setQualitys(Set<String> qualitys) {
        this.g = qualitys;
    }

    public void appendId(String id) {
        if (StringTool.isEmpty(id)) {
            return;
        }
        if (this.a == null) {
            this.a = new HashSet<String>();
        }
        this.a.add(id);
    }

    public void appendLanguage(String language) {
        if (StringTool.isEmpty(language)) {
            return;
        }
        if (this.c == null) {
            this.c = new HashSet<String>();
        }
        this.c.add(language);
    }

    public void appendGender(String gender) {
        if (StringTool.isEmpty(gender)) {
            return;
        }
        if (this.d == null) {
            this.d = new HashSet<String>();
        }
        this.d.add(gender);
    }

    public void appendSpeaker(String speaker) {
        if (StringTool.isEmpty(speaker)) {
            return;
        }
        if (this.e == null) {
            this.e = new HashSet<String>();
        }
        this.e.add(speaker);
    }

    public void appendDomain(String domain) {
        if (StringTool.isEmpty(domain)) {
            return;
        }
        if (this.f == null) {
            this.f = new HashSet<String>();
        }
        this.f.add(domain);
    }

    public void appendQuality(String quality) {
        if (StringTool.isEmpty(quality)) {
            return;
        }
        if (this.g == null) {
            this.g = new HashSet<String>();
        }
        this.g.add(quality);
    }

    public JSONArray getSpeakerJA() {
        return JsonTool.fromSetToJson(this.e);
    }

    public JSONArray getGenderJA() {
        return JsonTool.fromSetToJson(this.d);
    }

    public void setDomains(String[] domains) {
        this.f = DataTool.fromArrayToSet(domains);
    }

    public void setLanguages(String[] languages) {
        this.c = DataTool.fromArrayToSet(languages);
    }

    public void setQualitys(String[] qualities) {
        this.g = DataTool.fromArrayToSet(qualities);
    }

    public String[] getModelIdsArray() {
        return DataTool.fromSetToArray(this.a);
    }

    public String[] getDomainArray() {
        return DataTool.fromSetToArray(this.f);
    }

    public String[] getLanguageArray() {
        return DataTool.fromSetToArray(this.c);
    }

    public String[] getQualityArray() {
        return DataTool.fromSetToArray(this.g);
    }

    public String[] getGenderArray() {
        return DataTool.fromSetToArray(this.d);
    }

    public String[] getSpeakerArray() {
        return DataTool.fromSetToArray(this.e);
    }

    public String getVersion() {
        return this.b;
    }

    public void setVersion(String version) {
        this.b = version;
    }

    public JSONObject getJSONConditions() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(G.i.b(), (Object)JsonTool.fromSetToJson(this.a));
            jSONObject.put(G.ab.b(), (Object)this.b);
            jSONObject.put(G.G.b(), (Object)JsonTool.fromSetToJson(this.c));
            jSONObject.put(G.j.b(), (Object)JsonTool.fromSetToJson(this.d));
            jSONObject.put(G.L.b(), (Object)JsonTool.fromSetToJson(this.e));
            jSONObject.put(G.k.b(), (Object)JsonTool.fromSetToJson(this.f));
            jSONObject.put(G.l.b(), (Object)JsonTool.fromSetToJson(this.g));
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
        return jSONObject;
    }
}


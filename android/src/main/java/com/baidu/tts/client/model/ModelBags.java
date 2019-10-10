/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.baidu.tts.client.model;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.client.model.ModelInfo;
import com.baidu.tts.tools.DataTool;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ModelBags {
    private TtsError a;
    private List<ModelInfo> b;

    public TtsError getTtsError() {
        return this.a;
    }

    public void setTtsError(TtsError ttsError) {
        this.a = ttsError;
    }

    public List<ModelInfo> getModelInfos() {
        return this.b;
    }

    public void setModelInfos(List<ModelInfo> modelInfos) {
        this.b = modelInfos;
    }

    public void addModelInfo(ModelInfo modelInfo) {
        if (this.b == null) {
            this.b = new ArrayList<ModelInfo>();
        }
        this.b.add(modelInfo);
    }

    public void setList(List<Map<String, String>> list) {
        if (list != null) {
            ArrayList<ModelInfo> arrayList = new ArrayList<ModelInfo>();
            for (Map<String, String> map : list) {
                ModelInfo modelInfo = new ModelInfo();
                modelInfo.setMap(map);
                arrayList.add(modelInfo);
            }
            this.b = arrayList;
        }
    }

    public boolean isEmpty() {
        return DataTool.isListEmpty(this.b);
    }

    public JSONArray toJson() {
        JSONArray jSONArray = new JSONArray();
        if (!this.isEmpty()) {
            for (ModelInfo modelInfo : this.b) {
                JSONObject jSONObject = modelInfo.toJson();
                jSONArray.put((Object)jSONObject);
            }
        }
        return jSONArray;
    }

    public void parseJson(JSONArray ja) {
        int n2 = ja.length();
        for (int i2 = 0; i2 < n2; ++i2) {
            JSONObject jSONObject = ja.optJSONObject(i2);
            ModelInfo modelInfo = new ModelInfo();
            modelInfo.parseJson(jSONObject);
            this.addModelInfo(modelInfo);
        }
    }
}


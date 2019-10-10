/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.baidu.tts.client.model;

import android.content.Context;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.client.model.ModelFileInfo;
import com.baidu.tts.tools.DataTool;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ModelFileBags {
    private TtsError a;
    private List<ModelFileInfo> b;

    public List<ModelFileInfo> getModelFileInfos() {
        return this.b;
    }

    public TtsError getTtsError() {
        return this.a;
    }

    public void setTtsError(TtsError ttsError) {
        this.a = ttsError;
    }

    public void setModelFileInfos(List<ModelFileInfo> modelFileInfos) {
        this.b = modelFileInfos;
    }

    public void addFileInfo(ModelFileInfo fileInfo) {
        if (this.b == null) {
            this.b = new ArrayList<ModelFileInfo>();
        }
        this.b.add(fileInfo);
    }

    public void setList(List<Map<String, String>> list) {
        if (list != null && !list.isEmpty()) {
            ArrayList<ModelFileInfo> arrayList = new ArrayList<ModelFileInfo>();
            for (Map<String, String> map : list) {
                ModelFileInfo modelFileInfo = new ModelFileInfo();
                modelFileInfo.setMap(map);
                arrayList.add(modelFileInfo);
            }
            this.b = arrayList;
        }
    }

    public void generateAbsPath(Context context) {
        if (this.b != null) {
            for (ModelFileInfo modelFileInfo : this.b) {
                modelFileInfo.generateAbsPath(context);
            }
        }
    }

    public ModelFileInfo getModelFileInfo(int index) {
        if (this.b != null) {
            return this.b.get(index);
        }
        return null;
    }

    public String getUrl(int index) {
        ModelFileInfo modelFileInfo = this.getModelFileInfo(index);
        if (modelFileInfo != null) {
            return modelFileInfo.getUrl();
        }
        return null;
    }

    public boolean isEmpty() {
        return DataTool.isListEmpty(this.b);
    }

    public JSONArray toJson() {
        JSONArray jSONArray = new JSONArray();
        if (!this.isEmpty()) {
            for (ModelFileInfo modelFileInfo : this.b) {
                JSONObject jSONObject = modelFileInfo.toJson();
                jSONArray.put((Object)jSONObject);
            }
        }
        return jSONArray;
    }

    public void parseJson(JSONArray ja) {
        int n2 = ja.length();
        for (int i2 = 0; i2 < n2; ++i2) {
            JSONObject jSONObject = ja.optJSONObject(i2);
            ModelFileInfo modelFileInfo = new ModelFileInfo();
            modelFileInfo.parseJson(jSONObject);
            this.addFileInfo(modelFileInfo);
        }
    }
}


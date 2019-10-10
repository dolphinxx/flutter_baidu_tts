/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.client.model;

import android.content.Context;
import com.baidu.tts.client.model.AvailableConditions;
import com.baidu.tts.client.model.BasicHandler;
import com.baidu.tts.client.model.Conditions;
import com.baidu.tts.client.model.DownloadHandler;
import com.baidu.tts.client.model.LibEngineParams;
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.client.model.ModelFileBags;
import com.baidu.tts.client.model.OnDownloadListener;
import com.baidu.tts.d.B;
import com.baidu.tts.f.G;
import com.baidu.tts.l.A;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ModelManager {
    private A a;
    public Context mContext;

    public ModelManager(Context context) {
        this.a = new A(context);
        this.mContext = context;
    }

    public BasicHandler<ModelBags> getServerModels(Conditions conditions) {
        return this.a.a(conditions);
    }

    public BasicHandler<ModelBags> getLocalModels(Conditions conditions) {
        return this.a.a(conditions, false);
    }

    public LibEngineParams getEngineParams() {
        return this.a.a();
    }

    public BasicHandler<ModelBags> getServerModelsAvailable(AvailableConditions conditions) {
        Conditions conditions2 = this.a(conditions);
        return this.getServerModels(conditions2);
    }

    public BasicHandler<ModelBags> getLocalModelsAvailable(AvailableConditions conditions) {
        Conditions conditions2 = this.a(conditions);
        return this.a.a(conditions2, true);
    }

    private Conditions a(AvailableConditions availableConditions) {
        Conditions conditions = this.a();
        if (conditions != null && availableConditions != null) {
            conditions.setSpeakers(availableConditions.getSpeakers());
            conditions.setGenders(availableConditions.getGenders());
        }
        return conditions;
    }

    private Conditions a() {
        Conditions conditions = new Conditions();
        LibEngineParams libEngineParams = this.getEngineParams();
        String string = libEngineParams.getVersion();
        conditions.setVersion(string);
        conditions.setDomains(libEngineParams.getDomain());
        conditions.setLanguages(libEngineParams.getLanguage());
        conditions.setQualitys(libEngineParams.getQuality());
        return conditions;
    }

    public boolean isModelFileValid(String fileId) {
        return this.a.a(fileId);
    }

    public boolean isModelValid(String modelId) {
        return this.a.b(modelId);
    }

    public String getTextModelFileAbsPath(String modelId) {
        return this.a.a(G.r.b(), modelId);
    }

    public String getSpeechModelFileAbsPath(String modelId) {
        return this.a.a(G.s.b(), modelId);
    }

    public BasicHandler<ModelFileBags> getServerModelFileInfos(Set<String> fileIds) {
        return this.a.a(fileIds);
    }

    public BasicHandler<ModelFileBags> getLocalModelFileInfos(Set<String> fileIds) {
        return this.a.b(fileIds);
    }

    public BasicHandler<ModelBags> getServerDefaultModels() {
        return this.a.b();
    }

    public DownloadHandler download(String modelId, OnDownloadListener listener) {
        B b2 = new B();
        b2.a(modelId);
        b2.a(listener);
        return this.a.a(b2);
    }

    public int stop() {
        this.a.c();
        return 0;
    }

    public String checkModelsUpdate(Set<String> ModelIds) {
        return this.a.c(ModelIds);
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 */
package com.baidu.tts.l.a;

import com.baidu.tts.client.model.BasicHandler;
import com.baidu.tts.client.model.Conditions;
import com.baidu.tts.client.model.LibEngineParams;
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.client.model.ModelFileBags;
import com.baidu.tts.jni.EmbeddedSynthesizerEngine;
import com.baidu.tts.l.a.A;
import com.baidu.tts.l.a.C;
import com.baidu.tts.l.a.D;
import com.baidu.tts.l.a.F;
import com.baidu.tts.l.a.G;
import com.baidu.tts.l.a.H;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import org.json.JSONArray;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class I {
    private com.baidu.tts.l.A a;

    public I(com.baidu.tts.l.A a2) {
        this.a = a2;
    }

    public BasicHandler<ModelBags> a(Conditions conditions) {
        FutureTask<ModelBags> futureTask = new FutureTask<ModelBags>(new H(conditions));
        BasicHandler<ModelBags> basicHandler = new BasicHandler<ModelBags>(futureTask);
        basicHandler.start();
        return basicHandler;
    }

    public BasicHandler<ModelBags> a(Conditions conditions, boolean bl) {
        com.baidu.tts.database.A a2 = this.a.e();
        FutureTask<ModelBags> futureTask = new FutureTask<ModelBags>(new D(a2, conditions, this.a, bl));
        BasicHandler<ModelBags> basicHandler = new BasicHandler<ModelBags>(futureTask);
        basicHandler.start();
        return basicHandler;
    }

    public LibEngineParams a() {
        String string = EmbeddedSynthesizerEngine.bdTTSGetEngineParam();
        return new LibEngineParams(string);
    }

    public BasicHandler<ModelFileBags> a(Set<String> set) {
        FutureTask<ModelFileBags> futureTask = new FutureTask<ModelFileBags>(new G(set));
        BasicHandler<ModelFileBags> basicHandler = new BasicHandler<ModelFileBags>(futureTask);
        basicHandler.start();
        return basicHandler;
    }

    public BasicHandler<ModelFileBags> b(Set<String> set) {
        com.baidu.tts.database.A a2 = this.a.e();
        FutureTask<ModelFileBags> futureTask = new FutureTask<ModelFileBags>(new C(a2, set));
        BasicHandler<ModelFileBags> basicHandler = new BasicHandler<ModelFileBags>(futureTask);
        basicHandler.start();
        return basicHandler;
    }

    public BasicHandler<ModelBags> b() {
        FutureTask<ModelBags> futureTask = new FutureTask<ModelBags>(new F());
        BasicHandler<ModelBags> basicHandler = new BasicHandler<ModelBags>(futureTask);
        basicHandler.start();
        return basicHandler;
    }

    public BasicHandler<String> a(JSONArray jSONArray) {
        FutureTask<String> futureTask = new FutureTask<String>(new A(jSONArray));
        BasicHandler<String> basicHandler = new BasicHandler<String>(futureTask);
        basicHandler.start();
        return basicHandler;
    }
}


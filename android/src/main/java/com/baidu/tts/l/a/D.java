/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.l.a;

import com.baidu.tts.client.model.Conditions;
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.client.model.ModelInfo;
import com.baidu.tts.l.A;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class D
implements Callable<ModelBags> {
    private Conditions a;
    private com.baidu.tts.database.A b;
    private A c;
    private boolean d;

    public D(com.baidu.tts.database.A a2, Conditions conditions, A a3, boolean bl) {
        this.a = conditions;
        this.b = a2;
        this.c = a3;
        this.d = bl;
    }

    public ModelBags a() throws Exception {
        List<Map<String, String>> list = this.b.a(this.a);
        ModelBags modelBags = new ModelBags();
        modelBags.setList(list);
        if (modelBags != null && this.d) {
            List<ModelInfo> list2 = modelBags.getModelInfos();
            Iterator<ModelInfo> iterator = list2.iterator();
            while (iterator.hasNext()) {
                ModelInfo modelInfo = iterator.next();
                String string = modelInfo.getServerId();
                boolean bl = this.c.b(string);
                if (bl) continue;
                iterator.remove();
            }
        }
        return modelBags;
    }

    @Override
    public /* synthetic */ ModelBags call() throws Exception {
        return this.a();
    }
}


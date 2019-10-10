/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.l.a;

import com.baidu.tts.client.model.ModelFileBags;
import com.baidu.tts.database.A;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class C
implements Callable<ModelFileBags> {
    private Set<String> a;
    private A b;

    public C(A a2, Set<String> set) {
        this.a = set;
        this.b = a2;
    }

    public ModelFileBags a() throws Exception {
        List<Map<String, String>> list = this.b.a(this.a);
        ModelFileBags modelFileBags = new ModelFileBags();
        modelFileBags.setList(list);
        return modelFileBags;
    }

    @Override
    public /* synthetic */ ModelFileBags call() throws Exception {
        return this.a();
    }
}


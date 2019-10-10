/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.d.b;

import com.baidu.tts.client.model.ModelFileBags;
import com.baidu.tts.d.b.B;
import com.baidu.tts.d.b.E;
import com.baidu.tts.database.A;
import com.baidu.tts.f.G;
import com.baidu.tts.tools.DataTool;
import com.baidu.tts.tools.StringTool;
import java.util.HashMap;
import java.util.Map;

public class C {
    private String a;
    private Map<String, String> b;

    public C(String string) {
        this.a = string;
        this.b = new HashMap<String, String>();
    }

    public boolean a(A a2) {
        this.b = a2.d(this.a);
        if (DataTool.isMapEmpty(this.b)) {
            return false;
        }
        String string = this.b.get(G.h.b());
        if (StringTool.isEmpty(string)) {
            a2.b(this.a);
            return false;
        }
        B b2 = E.a().c(string);
        b2.c(this.a);
        return true;
    }

    public void a(ModelFileBags modelFileBags, A a2) {
        a2.a(modelFileBags);
        this.a(a2);
    }

    public String a() {
        return DataTool.getMapValue(this.b, G.h.b());
    }

    public String b() {
        return DataTool.getMapValue(this.b, G.g.b());
    }

    public String c() {
        return DataTool.getMapValue(this.b, G.f.b());
    }
}


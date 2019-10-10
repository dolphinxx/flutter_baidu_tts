/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.d;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class A {
    private boolean a = false;
    private Map<String, Integer> b = new HashMap<String, Integer>();

    public boolean a() {
        return this.a;
    }

    public void a(boolean bl) {
        this.a = bl;
    }

    public void a(String string, int n2) {
        this.b.put(string, n2);
    }

    public boolean b() {
        boolean bl = true;
        Collection<Integer> collection = this.b.values();
        for (int n2 : collection) {
            if (n2 == 7) continue;
            bl = false;
            break;
        }
        return bl;
    }
}


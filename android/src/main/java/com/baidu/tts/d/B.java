/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.d;

import com.baidu.tts.client.model.OnDownloadListener;
import com.baidu.tts.tools.StringTool;

public class B {
    private String a;
    private OnDownloadListener b;

    public String a() {
        return this.a;
    }

    public void a(String string) {
        this.a = string;
    }

    public boolean b() {
        return !StringTool.isEmpty(this.a);
    }

    public OnDownloadListener c() {
        return this.b;
    }

    public void a(OnDownloadListener onDownloadListener) {
        this.b = onDownloadListener;
    }
}


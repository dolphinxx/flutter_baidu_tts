/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.e;

import android.content.Context;
import com.baidu.tts.e.A;

public class B
extends Thread {
    private Context a;
    private String b;

    public B(Context context, String string) {
        this.a = context;
        this.b = string;
    }

    public void run() {
        A.a(this.a, this.b);
        this.a = null;
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.client.model;

import android.content.Context;
import com.baidu.tts.p.B;

public class Statistics {
    public static boolean isStatistics = true;
    private B a;

    public Statistics(Context context) {
        this.a = new B(context);
    }

    public static void setEnable(boolean mIsStatistics) {
        isStatistics = mIsStatistics;
    }

    public int start() {
        this.a.a();
        return 0;
    }

    public int stop() {
        this.a.b();
        return 0;
    }
}


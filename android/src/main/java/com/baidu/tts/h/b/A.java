/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.h.b;

import android.content.Context;
import com.baidu.tts.tools.FileTools;
import com.baidu.tts.tools.GetCUID;
import com.baidu.tts.tools.ResourceTools;
import java.lang.ref.WeakReference;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class A {
    private WeakReference<Context> a;
    private String b;
    private String c;

    public A(WeakReference<Context> weakReference) {
        this.a = weakReference;
    }

    public String a() {
        if (this.b == null) {
            this.b = GetCUID.getCUID(this.c());
        }
        return this.b;
    }

    public String b() {
        if (this.c == null) {
            this.c = FileTools.jointPathAndName(ResourceTools.getAppFilesDirPath(this.c()), "baidu_tts_license");
        }
        return this.c;
    }

    private Context c() {
        return this.a == null ? null : (Context)this.a.get();
    }
}


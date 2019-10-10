/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.baidu.tts.aop.ttslistener;

import android.text.TextUtils;
import com.baidu.tts.aop.AInterceptor;
import com.baidu.tts.aop.AInterceptorHandler;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.m.H;
import com.baidu.tts.m.I;
import java.lang.reflect.Method;
import java.util.List;

public class ProgressCorrectInterceptor
extends AInterceptor {
    protected void a() {
        this.a.add("onSynthesizeDataArrived");
        this.a.add("onPlayProgressUpdate");
    }

    protected Object a(Object object, Method method, Object[] arrobject) {
        String string;
        H h2 = (H)arrobject[0];
        if (h2 != null && !TextUtils.isEmpty((CharSequence)(string = h2.e().b()))) {
            int n2 = string.length();
            int n3 = h2.c();
            int n4 = 0;
            if (n3 > n2) {
                n4 = n3 - n2;
            }
            LoggerProxy.d("ProgressCorrectInterceptor", "prefixLength=" + n2 + "--progress=" + n3);
            H h3 = (H)h2.E();
            h3.d(n4);
            arrobject[0] = h3;
        }
        return AInterceptorHandler.DEFAULT;
    }
}


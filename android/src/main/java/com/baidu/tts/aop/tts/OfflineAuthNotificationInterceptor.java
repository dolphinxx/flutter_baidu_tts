/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop.tts;

import com.baidu.tts.aop.AInterceptor;
import com.baidu.tts.aop.AInterceptorHandler;
import com.baidu.tts.auth.A;
import com.baidu.tts.auth.B;
import com.baidu.tts.b.a.b.E;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.M;
import com.baidu.tts.m.I;
import com.baidu.tts.m.J;
import com.baidu.tts.o.a.C;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class OfflineAuthNotificationInterceptor
extends AInterceptor {
    private AtomicInteger b = new AtomicInteger(-1);

    protected void a() {
        this.a.add("speak");
    }

    protected Object a(Object object, Method method, Object[] arrobject) {
        C c2 = (C)object;
        if (c2.q()) {
            M m2 = c2.getMode();
            if (m2 == null) {
                c2.p();
                return AInterceptorHandler.END;
            }
            switch (m2) {
                case c: 
                case b: {
                    int n2 = this.b.incrementAndGet();
                    LoggerProxy.d("OfflineAuthNotificationInterceptor", "currentCount=" + n2);
                    if (n2 % 20 != 0) break;
                    I i2 = (I)arrobject[0];
                    J j2 = c2.getTtsParams();
                    if (j2 != null) {
                        return this.a(c2, j2, i2);
                    }
                    c2.p();
                    return AInterceptorHandler.END;
                }
            }
            return AInterceptorHandler.DEFAULT;
        }
        c2.p();
        return AInterceptorHandler.END;
    }

    private Object a(C c2, J j2, I i2) {
        B.a a2 = A.a().a(j2.d());
        this.a(a2, i2);
        return AInterceptorHandler.DEFAULT;
    }

    private void a(B.a a2, I i2) {
        if (a2.d()) {
            int n2 = a2.a();
            String string = String.format(Locale.US, "\u767e\u5ea6\u8bed\u97f3\u8bd5\u7528\u670d\u52a1%d\u5929\u540e\u5230\u671f\uff0c", n2);
            i2.a(string);
        }
        if (a2.f()) {
            i2.a("\u767e\u5ea6\u8bed\u97f3\u8bd5\u7528\u670d\u52a1\u5df2\u7ecf\u5230\u671f\uff0c\u8bf7\u53ca\u65f6\u66f4\u65b0\u6388\u6743\uff0c");
        }
        i2.a();
    }

}


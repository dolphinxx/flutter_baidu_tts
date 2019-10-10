/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

import com.baidu.tts.f.C;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum B {
    a("0"){

        public C[] b() {
            return C.c();
        }
    }
    ,
    b("1"){

        public C[] b() {
            return C.d();
        }
    }
    ,
    c("2"){

        public C[] b() {
            return C.e();
        }
    }
    ,
    d("3"){

        public C[] b() {
            return C.f();
        }
    }
    ,
    e("4"){

        public C[] b() {
            return C.g();
        }
    };
    
    private final String f;

    private B(String string2) {
        this.f = string2;
    }

    public String a() {
        return this.f;
    }

    public static B a(String string) {
        B[] arrb;
        for (B b2 : arrb = B.values()) {
            if (!b2.a().equals(string)) continue;
            return b2;
        }
        return null;
    }

    public abstract C[] b();

}


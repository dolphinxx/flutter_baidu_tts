/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 */
package com.baidu.tts.b.a.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.baidu.tts.b.a.b.F;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.J;
import com.baidu.tts.f.L;
import com.baidu.tts.h.b.B;

public class C {
    private com.baidu.tts.m.B a;
    private J b;

    public void a(com.baidu.tts.m.B b2) {
        this.a = b2;
    }

    public boolean a() {
        boolean bl = false;
        try {
            Context context = B.a().h();
            if (context != null) {
                NetworkInfo networkInfo;
                F.b b2;
                boolean bl2 = this.b();
                if (bl2) {
                    b2 = this.a.a();
                    if (this.b.equals((Object)J.c) || this.b.equals((Object)J.d)) {
                        b2.d(L.b.b());
                    } else {
                        b2.d(L.c.b());
                    }
                }
                if ((networkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()) != null && networkInfo.isConnected()) {
                    int n2 = networkInfo.getType();
                    int n3 = networkInfo.getSubtype();
                    switch (this.b) {
                        case a: 
                        case d: {
                            if (!this.c(n2) && n2 != 9) break;
                            bl = true;
                            break;
                        }
                        case b: 
                        case c: {
                            if (!this.c(n2) && n2 != 9 && !this.a(n3)) break;
                            bl = true;
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception exception) {
            LoggerProxy.d("MixStrategy", exception.toString());
        }
        return bl;
    }

    private boolean b() {
        boolean bl = false;
        J j2 = null;
        try {
            j2 = this.a.c();
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (this.b == null) {
            if (j2 == null) {
                this.b = J.a;
                bl = true;
            } else {
                this.b = j2;
                bl = true;
            }
        } else if (j2 == null) {
            bl = false;
        } else if (this.b.equals((Object)j2)) {
            bl = false;
        } else {
            this.b = j2;
            bl = true;
        }
        return bl;
    }

    private boolean a(int n2) {
        int n3 = this.b(n2);
        return n3 >= 2;
    }

    private int b(int n2) {
        switch (n2) {
            case 1: 
            case 2: 
            case 4: 
            case 7: 
            case 11: {
                return 1;
            }
            case 3: 
            case 5: 
            case 6: 
            case 8: 
            case 9: 
            case 10: 
            case 12: 
            case 14: 
            case 15: {
                return 2;
            }
            case 13: {
                return 3;
            }
        }
        return 0;
    }

    private boolean c(int n2) {
        switch (n2) {
            case 1: {
                return true;
            }
        }
        return false;
    }

}


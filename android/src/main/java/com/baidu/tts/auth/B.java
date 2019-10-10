/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.auth;

import android.content.Context;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.N;
import com.baidu.tts.h.a.C;
import com.baidu.tts.jni.EmbeddedSynthesizerEngine;
import com.baidu.tts.tools.StringTool;
import java.io.File;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class B
implements com.baidu.tts.k.B<B, B.a> {
    private String a;
    private String b;

    public String a() {
        return this.a;
    }

    public void a(String string) {
        this.a = string;
    }

    public String b() {
        return this.b;
    }

    public void b(String string) {
        this.b = string;
    }

    public int a(B b2) {
        String string = b2.a();
        String string2 = b2.b();
        boolean bl = StringTool.isEqual(this.a, string);
        boolean bl2 = StringTool.isEqual(this.b, string2);
        return bl && bl2 ? 0 : 1;
    }

    public a c() throws Exception {
        a a2 = new a();
        a2.a(this.b);
        a2.b(this.a);
        boolean bl = a2.g();
        if (!bl) {
            com.baidu.tts.h.b.B b2 = com.baidu.tts.h.b.B.a();
            Context context = b2.h();
            String string = b2.i();
            LoggerProxy.d("OfflineAuth", "+ downloadLicense");
            int n2 = EmbeddedSynthesizerEngine.bdTTSGetLicense(context, this.a, string, "0", "", this.b);
            LoggerProxy.d("OfflineAuth", "- downloadLicense ret = " + n2);
            a2.a(n2);
            if (n2 < 0) {
                TtsError ttsError = C.a().a(N.t, n2, "appCode=" + this.a + "--licensePath=" + this.b);
                a2.a(ttsError);
            } else {
                a2.g();
            }
        }
        return a2;
    }

    @Override
    public /* synthetic */ int compareTo(B x0) {
        return this.a((B)x0);
    }

    @Override
    public /* synthetic */ B.a call() throws Exception {
        return this.c();
    }

    public static class a
    implements com.baidu.tts.k.A {
        private int a;
        private int b = -1;
        private String c;
        private String d;
        private TtsError e;

        public int a() {
            return this.a >= 1000 ? this.a - 1000 : 0;
        }

        public void a(int n2) {
            this.b = n2;
        }

        public void a(String string) {
            this.c = string;
        }

        public void b(String string) {
            this.d = string;
        }

        public TtsError b() {
            return this.e;
        }

        public void a(TtsError ttsError) {
            if (ttsError != null) {
                LoggerProxy.d("OfflineAuth", "this=" + this + "--error=" + ttsError.getDetailMessage());
            }
            this.e = ttsError;
        }

        public String c() {
            if (this.e()) {
                return "valid official";
            }
            if (this.d()) {
                return "valid temp";
            }
            switch (this.a) {
                case -2: {
                    return "package name unmatched";
                }
                case -3: {
                    return "sign or appcode unmatched";
                }
                case -4: {
                    return "cuid unmatched";
                }
                case -5: {
                    return "official license expired";
                }
                case -6: {
                    return "will expire after a month";
                }
                case -7: {
                    return "platform unmatched";
                }
                case -8: {
                    return "license not exist or invalid license";
                }
                case -10: {
                    return "temp license expired";
                }
            }
            return "not a valid result";
        }

        public boolean d() {
            return this.a >= 1000;
        }

        public boolean e() {
            return this.a >= 0 && this.a < 1000;
        }

        public boolean f() {
            return this.a == -5 || this.a == -6;
        }

        public boolean g() {
            if (StringTool.isEmpty(this.c)) {
                return false;
            }
            File file = new File(this.c);
            if (file.exists()) {
                com.baidu.tts.h.b.B b2 = com.baidu.tts.h.b.B.a();
                Context context = b2.h();
                String string = b2.i();
                byte[] arrby = new byte[32];
                this.a = EmbeddedSynthesizerEngine.bdTTSVerifyLicense(context, this.d, string, this.c, arrby);
                LoggerProxy.d("OfflineAuth", "verify result=" + this.a);
                if (arrby != null) {
                    String string2 = new String(arrby);
                    LoggerProxy.d("OfflineAuth", "get appIdStr=" + string2);
                    try {
                        int n2 = string2.indexOf("end");
                        if (n2 != -1) {
                            String string3 = string2.substring(0, n2);
                            com.baidu.tts.e.B b3 = new com.baidu.tts.e.B(context, string3);
                            b3.start();
                        }
                    }
                    catch (Exception exception) {
                        LoggerProxy.d("OfflineAuth", "embedded statistics start exception=" + exception.toString());
                    }
                }
                if (this.a < 0) {
                    boolean bl = file.delete();
                    LoggerProxy.d("OfflineAuth", "isDelete=" + bl);
                    return false;
                }
                return true;
            }
            return false;
        }
    }

}


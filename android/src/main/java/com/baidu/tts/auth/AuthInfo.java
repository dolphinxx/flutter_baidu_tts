/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.auth;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.auth.B;
import com.baidu.tts.auth.C;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.M;
import com.baidu.tts.f.N;

public class AuthInfo {
    private M a;
    private C.a b;
    private B.a c;
    private TtsError d;

    public M getTtsEnum() {
        return this.a;
    }

    public void setTtsEnum(M ttsEnum) {
        this.a = ttsEnum;
    }

    public C.a getOnlineResult() {
        return this.b;
    }

    public void setOnlineResult(C.a onlineResult) {
        this.b = onlineResult;
    }

    public B.a getOfflineResult() {
        return this.c;
    }

    public void setOfflineResult(B.a offlineResult) {
        this.c = offlineResult;
    }

    public TtsError getTtsError() {
        if (this.d == null) {
            TtsError ttsError = null;
            switch (this.a) {
                case a: {
                    ttsError = this.b.b();
                    break;
                }
                case b: {
                    ttsError = this.c.b();
                    break;
                }
                case c: {
                    ttsError = this.getMixTtsError();
                    break;
                }
            }
            return ttsError;
        }
        return this.d;
    }

    public void setTtsError(TtsError ttsError) {
        this.d = ttsError;
    }

    public String getNotifyMessage() {
        return this.c.c();
    }

    public int getLeftValidDays() {
        return this.c.a();
    }

    public TtsError getOnlineTtsError() {
        return this.b != null ? this.b.b() : this.d;
    }

    public TtsError getOfflineTtsError() {
        return this.c != null ? this.c.b() : this.d;
    }

    public TtsError getMixTtsError() {
        TtsError ttsError = this.getOnlineTtsError();
        TtsError ttsError2 = this.getOfflineTtsError();
        TtsError ttsError3 = null;
        if (ttsError != null && ttsError2 != null) {
            ttsError3 = com.baidu.tts.h.a.C.a().b(N.J);
        } else if (ttsError == null && ttsError2 != null) {
            ttsError3 = com.baidu.tts.h.a.C.a().b(N.r);
        } else if (ttsError != null && ttsError2 == null) {
            ttsError3 = com.baidu.tts.h.a.C.a().b(N.a);
        }
        return ttsError3 != null ? ttsError3 : this.d;
    }

    public boolean isSuccess() {
        if (this.d == null) {
            if (this.a != null) {
                switch (this.a) {
                    case a: {
                        return this.isOnlineSuccess();
                    }
                    case b: {
                        return this.isOfflineSuccess();
                    }
                    case c: {
                        return this.isMixSuccess();
                    }
                }
                return false;
            }
            return false;
        }
        LoggerProxy.d("AuthInfo", "cause=" + this.d.getThrowable().getMessage());
        return false;
    }

    public boolean isOnlineSuccess() {
        return this.b != null ? this.b.g() : false;
    }

    public boolean isOfflineSuccess() {
        return this.c != null ? this.c.g() : false;
    }

    public boolean isMixSuccess() {
        return this.isOnlineSuccess() || this.isOfflineSuccess();
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.client;

import com.baidu.tts.f.M;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum TtsMode {
    ONLINE(M.a),
    /**
     * android only
     */
    OFFLINE(M.b),
    MIX(M.c);
    
    private final M a;

    private TtsMode(M ttsEnum) {
        this.a = ttsEnum;
    }

    public M getTtsEnum() {
        return this.a;
    }

    public int getMode() {
        return this.a.a();
    }

    public String getDescription() {
        return this.a.b();
    }
}


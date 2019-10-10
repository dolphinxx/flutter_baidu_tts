/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.client;

import com.baidu.tts.f.N;
import com.baidu.tts.tools.ResourceTools;

public class SpeechSynthesizeBag {
    private String a;
    private String b;

    public String getText() {
        return this.a;
    }

    public int setText(String text) {
        N n2 = ResourceTools.isTextValid(text);
        if (n2 == null) {
            this.a = text;
            return 0;
        }
        return n2.b();
    }

    public String getUtteranceId() {
        return this.b;
    }

    public void setUtteranceId(String utteranceId) {
        this.b = utteranceId;
    }
}


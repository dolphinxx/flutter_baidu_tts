/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.o.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.b.b.b.B;
import com.baidu.tts.f.N;
import com.baidu.tts.m.E;
import com.baidu.tts.m.F;
import com.baidu.tts.m.G;
import com.baidu.tts.m.I;
import com.baidu.tts.m.J;
//import com.baidu.tts.o.a.B;


public class D
extends A {
    public D(C c2) {
        super(c2);
    }

    public TtsError b() {
        TtsError ttsError = this.a.h();
        if (ttsError != null) {
            N n2 = ttsError.getErrorEnum();
            if (n2 != null && N.Na.a.equals(n2.a())) {
                this.a(this.a.g());
            }
        } else {
            this.a(this.a.g());
        }
        return ttsError;
    }

    public void c() {
        this.a.p();
    }

    public void d() {
        this.a.p();
    }

    public void e() {
        this.a.p();
    }

    public void f() {
    }

    public void speak(I textParams) {
        this.a.p();
    }

    public void synthesize(I textParams) {
        this.a.p();
    }

    public int loadCustomResource(E params) {
        return this.a.p();
    }

    public int freeCustomResource(E params) {
        return this.a.p();
    }

    public int loadModel(G params) {
        return this.a.p();
    }

    public int loadEnglishModel(F params) {
        return this.a.p();
    }

    public int setStereoVolume(float leftVolume, float rightVolume) {
        J j2 = this.a.getTtsParams();
        B.a a2 = j2.e();
        a2.a(leftVolume);
        a2.b(rightVolume);
        return 0;
    }

    public int setAudioStreamType(int streamType) {
        J j2 = this.a.getTtsParams();
        B.a a2 = j2.e();
        a2.b(streamType);
        return 0;
    }

    public int setAudioSampleRate(int sampleRate) {
        J j2 = this.a.getTtsParams();
        B.a a2 = j2.e();
        a2.a(sampleRate);
        return 0;
    }
}


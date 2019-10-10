/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.o.a;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.m.E;
import com.baidu.tts.m.F;
import com.baidu.tts.m.G;
import com.baidu.tts.m.I;
import com.baidu.tts.o.a.A;
import com.baidu.tts.o.a.C;
import com.baidu.tts.o.a.D;

public class B
extends A {
    public B(C c2) {
        super(c2);
    }

    public TtsError b() {
        return null;
    }

    public void c() {
        this.a.i();
    }

    public void d() {
        this.a.j();
    }

    public void e() {
        this.a.k();
    }

    public void f() {
        this.a.l();
        this.a(this.a.a());
    }

    public void speak(I textParams) {
        this.a.a(textParams);
    }

    public void synthesize(I textParams) {
        this.a.b(textParams);
    }

    public int loadCustomResource(E params) {
        return this.a.a(params);
    }

    public int freeCustomResource(E params) {
        return this.a.b(params);
    }

    public int loadModel(G params) {
        return this.a.a(params);
    }

    public int loadEnglishModel(F params) {
        return this.a.a(params);
    }

    public int setStereoVolume(float leftVolume, float rightVolume) {
        return this.a.a(leftVolume, rightVolume);
    }

    public int setAudioStreamType(int streamType) {
        return this.a.a(streamType);
    }

    public int setAudioSampleRate(int sampleRate) {
        return this.a.b(sampleRate);
    }
}


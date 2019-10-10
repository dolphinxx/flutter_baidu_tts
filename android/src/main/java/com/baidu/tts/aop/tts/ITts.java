/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.aop.tts;

import android.content.Context;
import com.baidu.tts.aop.ttslistener.TtsListener;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.f.M;
import com.baidu.tts.j.B;
import com.baidu.tts.m.E;
import com.baidu.tts.m.F;
import com.baidu.tts.m.G;
import com.baidu.tts.m.I;
import com.baidu.tts.m.J;

public interface ITts
extends B {
    public void setTtsListener(TtsListener var1);

    public TtsListener getTtsListener();

    public void setContext(Context var1);

    public void setMode(M var1);

    public M getMode();

    public AuthInfo auth(M var1);

    public int setParam(com.baidu.tts.f.G var1, String var2);

    public void speak(I var1);

    public void synthesize(I var1);

    public J getTtsParams();

    public int loadCustomResource(E var1);

    public int freeCustomResource(E var1);

    public int loadModel(G var1);

    public int loadEnglishModel(F var1);

    public int setStereoVolume(float var1, float var2);

    public int setAudioStreamType(int var1);

    public int setAudioSampleRate(int var1);
}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop.ttslistener;

import com.baidu.tts.m.H;

public interface TtsListener {
    public void onSynthesizeStart(H var1);

    public void onSynthesizeDataArrived(H var1);

    public void onSynthesizeFinished(H var1);

    public void onPlayStart(H var1);

    public void onPlayProgressUpdate(H var1);

    public void onPlayFinished(H var1);

    public void onError(H var1);
}


/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.client;

import com.baidu.tts.client.SpeechError;

public interface SpeechSynthesizerListener {
    public void onSynthesizeStart(String var1);

    public void onSynthesizeDataArrived(String var1, byte[] var2, int var3);

    public void onSynthesizeFinish(String var1);

    public void onSpeechStart(String var1);

    public void onSpeechProgressChanged(String var1, int var2);

    public void onSpeechFinish(String var1);

    public void onError(String var1, SpeechError var2);
}


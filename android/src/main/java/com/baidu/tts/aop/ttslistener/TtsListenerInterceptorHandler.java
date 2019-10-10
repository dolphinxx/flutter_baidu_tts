/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop.ttslistener;

import com.baidu.tts.aop.AInterceptorHandler;

public class TtsListenerInterceptorHandler
extends AInterceptorHandler {
    public void registerMethods() {
        this.registerMethod("onSynthesizeDataArrived");
        this.registerMethod("onPlayProgressUpdate");
    }
}


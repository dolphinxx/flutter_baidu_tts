/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.aop.tts;

import com.baidu.tts.aop.AInterceptorHandler;

public class TtsInterceptorHandler
extends AInterceptorHandler {
    public void registerMethods() {
        this.registerMethod("speak");
        this.registerMethod("synthesize");
        this.registerMethod("setTtsListener");
    }
}


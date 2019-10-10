/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.client.model;

public interface OnDownloadListener {
    public void onStart(String var1);

    public void onProgress(String var1, long var2, long var4);

    public void onFinish(String var1, int var2);
}


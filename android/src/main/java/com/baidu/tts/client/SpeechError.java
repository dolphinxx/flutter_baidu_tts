/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.client;

public class SpeechError {
    public int code;
    public String description;

    public String toString() {
        return "(" + this.code + ")" + this.description;
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.baidu.tts.jni;

import android.content.Context;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;

public class EmbeddedSynthesizerEngine {
    private static final String TAG = "EmbeddedSynthesizerEngine";
    private static OnNewDataListener mNewDataListener = null;

    public static void setOnNewDataListener(OnNewDataListener listener) {
        mNewDataListener = listener;
    }

    public static int newAudioDataCallback(byte[] audioData, int progress) {
        return mNewDataListener.onNewData(audioData, progress);
    }

    public static native int bdTTSEngineInit(byte[] var0, byte[] var1, long[] var2);

    public static native int bdTTSReInitData(byte[] var0, long var1);

    public static native int bdTTSDomainDataInit(byte[] var0, long var1);

    public static native int bdTTSDomainDataUninit(long var0);

    public static native int bdTTSSetText(long var0, byte[] var2, int var3);

    public static native int bdTTSSetParam(long var0, int var2, long var3);

    public static native int bdTTSSetParamFloat(long var0, int var2, float var3);

    public static synchronized native int bdTTSSynthesis(long var0, byte[] var2, int var3);

    public static native int bdTTSEngineUninit(long var0);

    public static native int bdTTSGetLicense(Context var0, String var1, String var2, String var3, String var4, String var5);

    public static synchronized native int bdTTSVerifyLicense(Context var0, String var1, String var2, String var3, byte[] var4);

    public static native int bdTTSGetTestAuthorize();

    public static native int bdTTSVerifyDataFile(byte[] var0);

    public static native int bdTTSGetDataFileParam(byte[] var0, int var1, byte[] var2);

    public static synchronized native String bdTTSGetEngineParam();

    public static native String bdTTSGetDatParam(String var0);

    public static native int loadEnglishEngine(byte[] var0, byte[] var1, long var2);

    public static native int getEngineMinVersion();

    static {
        try {
            LoggerProxy.d("EmbeddedSynthesizerEngine", "before load gnustl_shared");
            System.loadLibrary("gnustl_shared");
        }
        catch (Throwable throwable) {
            LoggerProxy.e("EmbeddedSynthesizerEngine", "so file gnustl_shared load fail");
        }
        try {
            LoggerProxy.d("EmbeddedSynthesizerEngine", "before load BDSpeechDecoder_V1");
            System.loadLibrary("BDSpeechDecoder_V1");
        }
        catch (Throwable throwable) {
            LoggerProxy.e("EmbeddedSynthesizerEngine", "so file BDSpeechDecoder_V1 load fail");
        }
        try {
            LoggerProxy.d("EmbeddedSynthesizerEngine", "before load bd_etts");
            System.loadLibrary("bd_etts");
        }
        catch (Throwable throwable) {
            LoggerProxy.e("EmbeddedSynthesizerEngine", "so file bd_etts load fail");
        }
        try {
            LoggerProxy.d("EmbeddedSynthesizerEngine", "before load bdtts");
            System.loadLibrary("bdtts");
            LoggerProxy.d("EmbeddedSynthesizerEngine", "after load bdtts");
        }
        catch (Throwable throwable) {
            LoggerProxy.e("EmbeddedSynthesizerEngine", "so file bdtts load fail");
        }
    }

    public static interface OnNewDataListener {
        public int onNewData(byte[] var1, int var2);
    }

}


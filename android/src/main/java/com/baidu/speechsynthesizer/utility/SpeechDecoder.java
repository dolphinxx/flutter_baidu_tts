/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.speechsynthesizer.utility;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;

public class SpeechDecoder {
    private static final String TAG = "SpeechDecoder";
    private static OnDecodedDataListener mDecodedDataListener = null;

    public native int decode(byte[] var1, int var2, short[] var3, int[] var4, int var5, int var6);

    public static native int decodeWithCallback(byte[] var0, Object var1);

    public int decodeWithCallback(byte[] intputData) {
        return SpeechDecoder.decodeWithCallback(intputData, this);
    }

    public void decode_audio_callback(byte[] audio) {
        mDecodedDataListener.onDecodedData(audio);
    }

    public static void setOnDecodedDataListener(OnDecodedDataListener listener) {
        mDecodedDataListener = listener;
    }

    static {
        try {
            LoggerProxy.d("SpeechDecoder", "before load gnustl_shared");
            System.loadLibrary("gnustl_shared");
        }
        catch (Throwable throwable) {
            LoggerProxy.e("SpeechDecoder", "so file gnustl_shared load fail");
        }
        try {
            LoggerProxy.d("SpeechDecoder", "before load BDSpeechDecoder_V1");
            System.loadLibrary("BDSpeechDecoder_V1");
            LoggerProxy.d("SpeechDecoder", "after load BDSpeechDecoder_V1");
        }
        catch (Throwable throwable) {
            LoggerProxy.e("SpeechDecoder", "so file BDSpeechDecoder_V1 load fail");
        }
    }

    public static interface OnDecodedDataListener {
        public void onDecodedData(byte[] var1);
    }

}


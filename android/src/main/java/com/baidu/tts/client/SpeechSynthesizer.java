/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.baidu.tts.client;

import android.content.Context;
import android.os.Bundle;
import com.baidu.tts.a.b.A;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.baidu.tts.f.B;
import com.baidu.tts.f.C;
import com.baidu.tts.f.D;
import com.baidu.tts.f.G;
import com.baidu.tts.f.H;
import com.baidu.tts.f.J;
import com.baidu.tts.f.K;
import com.baidu.tts.f.N;
import com.baidu.tts.tools.DataTool;
import com.baidu.tts.tools.StringTool;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SpeechSynthesizer {
    public static final int ERROR_QUEUE_IS_FULL = N.U.b();
    public static final int ERROR_LIST_IS_TOO_LONG = N.V.b();
    public static final int ERROR_TEXT_IS_EMPTY = N.P.b();
    public static final int ERROR_TEXT_IS_TOO_LONG = N.Q.b();
    public static final int ERROR_TEXT_ENCODE_IS_WRONG = N.R.b();
    public static final int ERROR_APP_ID_IS_INVALID = N.X.b();
    public static final int MAX_QUEUE_SIZE = 15000;
    public static final int MAX_LIST_SIZE = 100;
    public static final String PARAM_REQUEST_PROTOCOL = G.a(G.ao);
    public static final String PARAM_REQUEST_ENABLE_DNS = G.a(G.ap);
    public static final String REQUEST_DNS_OFF = "0";
    public static final String REQUEST_DNS_ON = "1";
    public static final String REQUEST_PROTOCOL_HTTP = "http";
    public static final String REQUEST_PROTOCOL_HTTPS = "https";
    public static final String PARAM_PROXY_HOST = G.a(G.am);
    public static final String PARAM_PROXY_PORT = G.a(G.an);
    public static final String PARAM_URL = G.a(G.e);
    public static final String PARAM_SPEED = G.a(G.D);
    public static final String PARAM_PITCH = G.a(G.F);
    public static final String PARAM_VOLUME = G.a(G.E);
    public static final String PARAM_SPEC = G.a(G.ak);
    public static final String PARAM_TTS_TEXT_MODEL_FILE = G.a(G.P);
    public static final String PARAM_TTS_SPEECH_MODEL_FILE = G.a(G.Q);
    public static final String PARAM_TTS_LICENCE_FILE = G.a(G.R);
    public static final String PARAM_VOCODER_OPTIM_LEVEL = G.a(G.U);
    public static final String PARAM_CUSTOM_SYNTH = G.a(G.S);
    public static final String PARAM_OPEN_XML = G.a(G.T);
    public static final String PARAM_PRODUCT_ID = G.a(G.O);
    public static final String PARAM_KEY = G.a(G.al);
    public static final String PARAM_LANGUAGE = G.a(G.G);
    public static final String PARAM_AUDIO_ENCODE = G.a(G.J);
    public static final String PARAM_AUDIO_RATE = G.a(G.K);
    public static final String PARAM_SPEAKER = G.a(G.L);
    public static final String PARAM_MIX_MODE = G.a(G.x);
    public static final String MIX_MODE_DEFAULT = J.a.name();
    public static final String MIX_MODE_HIGH_SPEED_NETWORK = J.b.name();
    public static final String MIX_MODE_HIGH_SPEED_SYNTHESIZE = J.c.name();
    public static final String MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI = J.d.name();
    public static final String LANGUAGE_ZH = H.a.a();
    public static final String LANGUAGE_EN = H.b.a();
    public static final String TEXT_ENCODE_GBK = D.a.b();
    public static final String TEXT_ENCODE_BIG5 = D.b.b();
    public static final String TEXT_ENCODE_UTF8 = D.c.b();
    public static final String AUDIO_ENCODE_BV = B.a.a();
    public static final String AUDIO_ENCODE_AMR = B.b.a();
    public static final String AUDIO_ENCODE_OPUS = B.c.a();
    public static final String AUDIO_ENCODE_MP3 = B.d.a();
    public static final String AUDIO_ENCODE_PCM = B.e.a();
    public static final int AUDIO_SAMPLERATE_8K = K.a.a();
    public static final int AUDIO_SAMPLERATE_16K = K.b.a();
    public static final int AUDIO_SAMPLERATE_24K = K.c.a();
    public static final int AUDIO_SAMPLERATE_48K = K.d.a();
    public static final String AUDIO_BITRATE_BV_16K = C.a.a();
    public static final String AUDIO_BITRATE_AMR_6K6 = C.b.a();
    public static final String AUDIO_BITRATE_AMR_8K85 = C.c.a();
    public static final String AUDIO_BITRATE_AMR_12K65 = C.d.a();
    public static final String AUDIO_BITRATE_AMR_14K25 = C.e.a();
    public static final String AUDIO_BITRATE_AMR_15K85 = C.f.a();
    public static final String AUDIO_BITRATE_AMR_18K25 = C.g.a();
    public static final String AUDIO_BITRATE_AMR_19K85 = C.h.a();
    public static final String AUDIO_BITRATE_AMR_23K05 = C.i.a();
    public static final String AUDIO_BITRATE_AMR_23K85 = C.j.a();
    public static final String AUDIO_BITRATE_OPUS_8K = C.k.a();
    public static final String AUDIO_BITRATE_OPUS_16K = C.l.a();
    public static final String AUDIO_BITRATE_OPUS_18K = C.m.a();
    public static final String AUDIO_BITRATE_OPUS_20K = C.n.a();
    public static final String AUDIO_BITRATE_OPUS_24K = C.o.a();
    public static final String AUDIO_BITRATE_OPUS_32K = C.p.a();
    public static final String AUDIO_BITRATE_PCM = C.v.a();
    private static volatile SpeechSynthesizer a = null;
    private A b = new A();

    private SpeechSynthesizer() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static SpeechSynthesizer getInstance() {
        if (a != null) return a;
        Class<SpeechSynthesizer> class_ = SpeechSynthesizer.class;
        synchronized (SpeechSynthesizer.class) {
            if (a != null) return a;
            {
                a = new SpeechSynthesizer();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return a;
        }
    }

    public void setSpeechSynthesizerListener(SpeechSynthesizerListener speechSynthesizerListener) {
        this.b.a(speechSynthesizerListener);
    }

    public void setContext(Context context) {
        this.b.a(context);
    }

    public synchronized int initTts(TtsMode ttsMode) {
        TtsError ttsError = this.b.a(ttsMode);
        return ttsError == null ? 0 : ttsError.getDetailCode();
    }

    public String libVersion() {
        return this.b.a();
    }

    public int setApiKey(String apiKey, String secretKey) {
        this.setParam(G.ah.name(), apiKey);
        this.setParam(G.ai.name(), secretKey);
        return 0;
    }

    public int setAppId(String appId) {
        if (StringTool.isAllNumber(appId)) {
            this.setParam(G.A.name(), appId);
            return 0;
        }
        return ERROR_APP_ID_IS_INVALID;
    }

    public int setParam(String key, String value) {
        return this.b.a(key, value);
    }

    public synchronized int pause() {
        return this.b.b();
    }

    public synchronized int resume() {
        return this.b.c();
    }

    public synchronized int stop() {
        return this.b.d();
    }

    public synchronized int release() {
        this.b.e();
        a = null;
        return 0;
    }

    public int loadCustomResource(String customModelPath) {
        return this.b.a(customModelPath);
    }

    public int freeCustomResource() {
        return this.b.f();
    }

    public int loadModel(String speechModelPath, String textModelPath) {
        return this.b.b(speechModelPath, textModelPath);
    }

    public int loadEnglishModel(String englishTextModelPath, String englishSpeechModelPath) {
        return this.b.c(englishTextModelPath, englishSpeechModelPath);
    }

    public int speak(String text) {
        return this.speak(text, null);
    }

    public int speak(SpeechSynthesizeBag speechSynthesizeBag) {
        try {
            String string = speechSynthesizeBag.getText();
            String string2 = speechSynthesizeBag.getUtteranceId();
            return this.speak(string, string2);
        }
        catch (Exception exception) {
            return N.Y.b();
        }
    }

    public int speak(String text, String utteranceId) {
        return this.speak(text, utteranceId, null);
    }

    public int speak(String text, String utteranceId, Bundle params) {
        return this.b.a(text, utteranceId, params);
    }

    public int synthesize(String text) {
        return this.synthesize(text, null);
    }

    public int synthesize(SpeechSynthesizeBag speechSynthesizeBag) {
        try {
            String string = speechSynthesizeBag.getText();
            String string2 = speechSynthesizeBag.getUtteranceId();
            return this.synthesize(string, string2);
        }
        catch (Exception exception) {
            return N.Y.b();
        }
    }

    public int synthesize(String text, String utteranceId) {
        return this.synthesize(text, utteranceId, null);
    }

    public int synthesize(String text, String utteranceId, Bundle params) {
        return this.b.b(text, utteranceId, params);
    }

    public int batchSpeak(List<SpeechSynthesizeBag> speechSynthesizeBags) {
        if (DataTool.isListEmpty(speechSynthesizeBags)) {
            return N.Y.b();
        }
        return this.b.a(speechSynthesizeBags);
    }

    public AuthInfo auth(TtsMode ttsMode) {
        return this.b.b(ttsMode);
    }

    public int setStereoVolume(float leftVolume, float rightVolume) {
        return this.b.a(leftVolume, rightVolume);
    }

    public int setAudioStreamType(int streamType) {
        return this.b.a(streamType);
    }

    public int setAudioSampleRate(int sampleRate) {
        return this.b.b(sampleRate);
    }
}


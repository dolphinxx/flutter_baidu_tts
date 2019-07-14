package com.whaleread.flutter_baidu_tts;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class Tts implements MethodChannel.MethodCallHandler, SpeechSynthesizerListener {
    private static final String TAG = "TTS";
    private SpeechSynthesizer mSpeechSynthesizer;

//    private static final String TEXT_FILENAME = "bd_etts_text.dat";
//    private static final Map<String, String> MODEL_FILENAMES = new HashMap<>();
//    static {
//        MODEL_FILENAMES.put("0", "bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat");
//        MODEL_FILENAMES.put("1", "bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat");
//        MODEL_FILENAMES.put("3", "bd_etts_common_speech_yyjw_mand_eng_high_am-mix_v3.0.0_20170512.dat");
//        MODEL_FILENAMES.put("4", "bd_etts_common_speech_as_mand_eng_high_am_v3.0.0_20170516.dat");
//    }

    private String textFile;
    private List<String> speechModelFiles;
    private TtsMode ttsMode;
    private PluginRegistry.Registrar registrar;
    private MethodChannel channel;
    private boolean notifyProgress;

    private boolean audioFocus;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;
    private AudioAttributes audioAttributes;
    private boolean autoResume = false;
    private boolean hasAudioFocus = false;

    Tts(PluginRegistry.Registrar registrar, MethodChannel channel, TtsMode ttsMode, String textFile, List<String> speechModelFiles, boolean notifyProgress, boolean audioFocus) {
        this.registrar = registrar;
        this.channel = channel;
        this.ttsMode = ttsMode;
        this.notifyProgress = notifyProgress;
        this.textFile = textFile;
        this.speechModelFiles = speechModelFiles;
        this.audioFocus = audioFocus;
        if (audioFocus) {
            audioManager = (AudioManager) registrar.context().getSystemService(Context.AUDIO_SERVICE);
            onAudioFocusChangeListener = focusChange -> {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        if (autoResume) {
                            _resume();
                            autoResume = false;
                        }
                        hasAudioFocus = true;
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        _pause();
                        autoResume = true;
                        hasAudioFocus = false;
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        _pause();
                        autoResume = true;
                        hasAudioFocus = false;
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        _pause();
                        autoResume = true;
                        hasAudioFocus = false;
                        break;
                }
            };
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
            }
        }
        LoggerProxy.printable(false);
    }

    /**
     * 注意该方法需要在新线程中调用。且该线程不能结束。详细请参见NonBlockSyntherizer的实现
     */
    void init(String appId, String appKey, String secretKey) {

        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(registrar.context());
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);


        // 请替换为语音开发者平台上注册应用得到的App ID ,AppKey ，Secret Key ，填写在SynthActivity的开始位置
        int result = mSpeechSynthesizer.setAppId(appId);
        if (result != 0) {
            Log.e(TAG, "set AppId result: " + result);
            channel.invokeMethod("onInitFailed", result);
            return;
        }
        result = mSpeechSynthesizer.setApiKey(appKey, secretKey);
        if (result != 0) {
            Log.e(TAG, "set appKey result: " + result);
            channel.invokeMethod("onInitFailed", result);
            return;
        }

        if (ttsMode == TtsMode.MIX) {
            if (!checkAuth()) {
                channel.invokeMethod("onInitFailed", "checkAuth failed");
                return;
            }

//            String textFile = new File(modelPath, TEXT_FILENAME).getAbsolutePath();
//            String modelFile = new File(modelPath, MODEL_FILENAMES.get(model)).getAbsolutePath();

            if (!checkOfflineResources(textFile, speechModelFiles.get(0))) {
                channel.invokeMethod("onInitFailed", "checkOfflineResources failed");
                return;
            }
            // 文本模型文件路径 (离线引擎使用)， 注意TEXT_FILENAME必须存在并且可读
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, textFile);
            // 声学模型文件路径 (离线引擎使用)， 注意TEXT_FILENAME必须存在并且可读
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, speechModelFiles.get(0));
        }

        // 5. 以下setParam 参数选填。不填写则默认值生效
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-15 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置合成的语速，0-15 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-15 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        mSpeechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // 6. 初始化
        result = mSpeechSynthesizer.initTts(ttsMode);
        if (result != 0) {
            Log.e(TAG, "initTts result: " + result);
            channel.invokeMethod("onInitFailed", result);
            return;
        }
        channel.invokeMethod("onInitSuccess", null);
    }

    private void setParams(Map<String, String> params) {
        if (params != null) {
            if (params.containsKey("onlineSpeaker")) {
                mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, params.get("onlineSpeaker"));
            }
            for (Map.Entry<String, String> e : params.entrySet()) {
                switch (e.getKey()) {
                    case "onlineSpeaker":
                        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, params.get("onlineSpeaker"));
                        break;
                    case "offlineSpeaker":
                        //noinspection ConstantConditions
                        loadModel(Integer.parseInt(params.get("offlineSpeaker")));
                        break;
                    case "volume":
                        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, params.get("volume"));
                        break;
                    case "speed":
                        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, params.get("speed"));
                        break;
                    case "pitch":
                        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, params.get("pitch"));
                        break;
                    case "mixMode":
                        String mixMode;
                        //noinspection ConstantConditions
                        switch (params.get("mixMode")) {
                            case "MIX_MODE_HIGH_SPEED_NETWORK":
                                mixMode = SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK;
                                break;
                            case "MIX_MODE_HIGH_SPEED_SYNTHESIZE":
                                mixMode = SpeechSynthesizer.MIX_MODE_HIGH_SPEED_SYNTHESIZE;
                                break;
                            case "MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI":
                                mixMode = SpeechSynthesizer.MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI;
                                break;
                            default:
                                mixMode = SpeechSynthesizer.MIX_MODE_DEFAULT;
                        }
                        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, mixMode);
                }
            }
        }
    }

    /**
     * 检查appId ak sk 是否填写正确，另外检查官网应用内设置的包名是否与运行时的包名一致。本demo的包名定义在build.gradle文件中
     */
    private boolean checkAuth() {
        AuthInfo authInfo = mSpeechSynthesizer.auth(ttsMode);
        if (!authInfo.isSuccess()) {
            // 离线授权需要网站上的应用填写包名。
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.e(TAG, "【error】鉴权失败 errorMsg=" + errorMsg);
            return false;
        } else {
            Log.d(TAG, "验证通过，离线正式授权文件存在。");
            return true;
        }
    }

    /**
     * 检查 TEXT_FILENAME, MODEL_FILENAME 这2个文件是否存在，不存在请自行从assets目录里手动复制
     */
    private boolean checkOfflineResources(String textModel, String modelFile) {
        String[] filenames = {textModel, modelFile};
        for (String path : filenames) {
            File f = new File(path);
            if (!f.canRead()) {
                Log.e(TAG, "[ERROR] 文件不存在或者不可读取，请从assets目录复制同名文件到：" + path);
                return false;
            }
        }
        return true;
    }

    /**
     * 切换离线发音。注意需要添加额外的判断：引擎在合成时该方法不能调用
     */
    private void loadModel(int model) {
        Log.d(TAG, "切换离线语音：" + model);
        int result = mSpeechSynthesizer.loadModel(speechModelFiles.get(model), textFile);
        if (result != 0) {
            Log.e(TAG, "load model result: " + result);
        }
    }

    private boolean requestAudioFocus() {
        if (audioFocus) {
            int result;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                result = audioManager.requestAudioFocus(new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                        .setAudioAttributes(audioAttributes)
                        .setAcceptsDelayedFocusGain(false)
                        .setWillPauseWhenDucked(true)
                        .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                        .build());
            } else {
                result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);
            }
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                this.hasAudioFocus = true;
                return true;
            } else {
                Log.w(TAG, "requestAudioFocus failed " + result);
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 合成并播放
     *
     * @param texts 要合成的文本，不得大于1024个GBK字节，即512个汉字
     * @return 合成结果
     */
    private int speak(String texts) {
        if(!hasAudioFocus && !requestAudioFocus()) {
            return -1;
        }
        /* 以下参数每次合成时都可以修改
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
         *  设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5"); 设置合成的音量，0-9 ，默认 5
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5"); 设置合成的语速，0-9 ，默认 5
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5"); 设置合成的语调，0-9 ，默认 5
         *
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
         *  MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
         *  MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
         *  MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
         *  MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
         */

        if (mSpeechSynthesizer == null) {
            Log.e(TAG, "[ERROR], 初始化失败");
            return -1;
        }
        return mSpeechSynthesizer.speak(texts);
    }

    /**
     * 批量合成并播放，等同于多次调用speak
     *
     * @param texts 要合成的文本，每个文本不能大于1024个GBK字节，即512个汉字
     * @return 合成结果
     */
    private List<Integer> batchSpeak(List<String> texts) {
        if(!hasAudioFocus && !requestAudioFocus()) {
            return new ArrayList<>();
        }
        List<SpeechSynthesizeBag> bags = new ArrayList<>();
        List<Integer> result = new ArrayList<>(texts.size());
        for (int i = 0;i < texts.size();i++) {
            SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
            speechSynthesizeBag.setText(texts.get(i));
            speechSynthesizeBag.setUtteranceId(String.valueOf(i));
            bags.add(speechSynthesizeBag);
            result.add(i);
        }
        mSpeechSynthesizer.batchSpeak(bags);
        return result;
    }

    private void _pause() {
        pause();
        channel.invokeMethod("onSpeechPause", null);
    }

    private int pause() {
        if (mSpeechSynthesizer != null) {
            return mSpeechSynthesizer.pause();
        }
        return 0;
    }

    private int resume() {
        autoResume = false;
        if(!requestAudioFocus()) {
            return 0;
        }
        return doResume();
    }

    private void _resume() {
        resume();
        channel.invokeMethod("onSpeechResume", null);
    }

    private int doResume() {
        if (mSpeechSynthesizer != null) {
            return mSpeechSynthesizer.resume();
        }
        return 0;
    }

    private int stop() {
        if (mSpeechSynthesizer != null) {
            return mSpeechSynthesizer.stop();
        }
        return 0;
    }

    void destroy() {
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.stop();
            mSpeechSynthesizer.release();
            mSpeechSynthesizer = null;
        }
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method) {
            case "speak":
                result.success(speak((String) methodCall.arguments));
                break;
            case "batchSpeak":
                //noinspection unchecked
                result.success(batchSpeak((List<String>) methodCall.arguments));
                break;
            case "stop":
                result.success(stop());
                break;
            case "pause":
                result.success(pause());
                break;
            case "resume":
                result.success(resume());
                break;
            case "destroy":
                destroy();
                result.success(1);
                break;
            case "setParams":
                //noinspection unchecked
                setParams((Map<String, String>) methodCall.arguments);
                result.success(1);
                break;
            default:
                result.notImplemented();
        }
    }

    @Override
    public void onSynthesizeStart(String utteranceId) {
        channel.invokeMethod("onSynthesizeStart", utteranceId == null ? 0 : Integer.parseInt(utteranceId));
    }

    @Override
    public void onSynthesizeDataArrived(String utteranceId, byte[] bytes, int progress) {
        if (notifyProgress) {
            channel.invokeMethod("onSynthesizeDataArrived", new int[]{utteranceId == null ? 0 : Integer.parseInt(utteranceId), progress});
        }
    }

    @Override
    public void onSynthesizeFinish(String utteranceId) {
        channel.invokeMethod("onSynthesizeFinish", utteranceId == null ? 0 : Integer.parseInt(utteranceId));
    }

    @Override
    public void onSpeechStart(String utteranceId) {
        channel.invokeMethod("onSpeechStart", utteranceId == null ? 0 : Integer.parseInt(utteranceId));
    }

    @Override
    public void onSpeechProgressChanged(String utteranceId, int progress) {
        if (notifyProgress) {
            channel.invokeMethod("onSpeechProgressChanged", new int[]{utteranceId == null ? 0 : Integer.parseInt(utteranceId), progress});
        }
    }

    @Override
    public void onSpeechFinish(String utteranceId) {
        channel.invokeMethod("onSpeechFinish", utteranceId == null ? 0 : Integer.parseInt(utteranceId));
    }

    @Override
    public void onError(String utteranceId, SpeechError speechError) {
        Log.e(TAG, "TTS error " + speechError);
        channel.invokeMethod("onError", new int[]{utteranceId == null ? 0 : Integer.parseInt(utteranceId), speechError.code});
    }

//    private void prepareModels() {
//        copyFromAssets(TEXT_FILENAME);
//        for(String file : MODEL_FILENAMES.values()) {
//            copyFromAssets(file);
//        }
//    }

//    private void copyFromAssets(String sourceFilename) {
//
//        File file = new File(modelPath, sourceFilename);
//        if (!file.exists()) {
//            InputStream is = null;
//            FileOutputStream fos = null;
//            try {
//                is = registrar.context().getApplicationContext().getAssets().open(sourceFilename);
//                fos = new FileOutputStream(file);
//                byte[] buffer = new byte[1024];
//                int size = 0;
//                while ((size = is.read(buffer, 0, 1024)) >= 0) {
//                    fos.write(buffer, 0, size);
//                }
//            } catch(IOException e) {
//                throw new RuntimeException(e);
//            } finally {
//                if (fos != null) {
//                    try {
//                        fos.close();
//                    } catch(IOException ignore) {
//                    }
//                }
//                if (is != null) {
//                    try {
//                        is.close();
//                    } catch (IOException ignore) {
//                    }
//                }
//            }
//        }
//        Log.i(TAG, "文件复制成功：" + file.getAbsolutePath());
//    }
}

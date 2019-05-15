import 'dart:async';

import 'package:flutter/services.dart';

enum TtsEvent {
  /// arguments: init result
  onInitFailed,

  /// arguments: null
  onInitSuccess,

  /// arguments: utteranceId
  onSynthesizeStart,

  /// arguments: [utteranceId, progress]
  onSynthesizeDataArrived,

  /// arguments: utteranceId
  onSynthesizeFinish,

  /// arguments: utteranceId
  onSpeechStart,

  /// arguments: [utteranceId, progress]
  onSpeechProgressChanged,

  /// arguments: utteranceId
  onSpeechFinish,

  /// paused when loosing audio focus, arguments: null
  onSpeechPause,

  /// resumed when gained audio focus, arguments: null
  onSpeechResume,

  /// arguments: [utteranceId, error]
  onError,
}

typedef TtsEventHandler = Function(TtsEvent event, dynamic arguments);

class FlutterBaiduTts {
  static const int minVolume = 0;
  static const int maxVolume = 15;
  static const int minPitch = 0;
  static const int maxPitch = 15;
  static const int minSpeed = 0;
  static const int maxSpeed = 15;
  /// WIFI 使用在线合成，非WIFI使用离线合成 ，6s超时
  static const String mixModeDefault = 'MIX_MODE_DEFAULT';
  /// WIFI,4G,3G 使用在线合成，其他使用离线合成 ，6s超时
  static const String mixModeHighSpeedNetwork = 'MIX_MODE_HIGH_SPEED_NETWORK';
  /// 同MIX_MODE_HIGH_SPEED_NETWORK。但是连接百度服务器超时1.2s后，自动切换离线合成引擎
  static const String mixModeHighSpeedSynthesize = 'MIX_MODE_HIGH_SPEED_SYNTHESIZE';
  /// 同 MIX_MODE_DEFAULT。 但是连接百度服务器超时1.2s后，自动切换离线合成引擎
  static const String mixModeHighSpeedSynthesizeWifi = 'MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI';
  static MethodChannel _channel = const MethodChannel('com.whaleread.flutter_baidu_tts')..setMethodCallHandler(_handleMethodCall);
  static TtsEventHandler _ttsEventHandler;
  static set ttsEventHandler(handler) => _ttsEventHandler = handler;

  static Future<void> _handleMethodCall(MethodCall call) async {
    if (_ttsEventHandler == null) {
      return;
    }
    TtsEvent event;
    switch (call.method) {
      case 'onInitFailed':
        event = TtsEvent.onInitFailed;
        break;
      case 'onInitSuccess':
        event = TtsEvent.onInitSuccess;
        break;
      case 'onSynthesizeStart':
        event = TtsEvent.onSynthesizeStart;
        break;
      case 'onSynthesizeDataArrived':
        event = TtsEvent.onSynthesizeDataArrived;
        break;
      case 'onSynthesizeFinish':
        event = TtsEvent.onSynthesizeFinish;
        break;
      case 'onSpeechStart':
        event = TtsEvent.onSpeechStart;
        break;
      case 'onSpeechProgressChanged':
        event = TtsEvent.onSpeechProgressChanged;
        break;
      case 'onSpeechFinish':
        event = TtsEvent.onSpeechFinish;
        break;
      case 'onSpeechPause':
        event = TtsEvent.onSpeechPause;
        break;
      case 'onSpeechResume':
        event = TtsEvent.onSpeechResume;
        break;
      case 'onError':
        event = TtsEvent.onError;
        break;
    }
    _ttsEventHandler(event, call.arguments);
  }

  ///
  /// [textModelPath] text model(bd_etts_text.dat) 目录
  ///
  /// [speechModelPath] speech model 目录
  ///
  ///
  ///   - 普通女声 bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat
  ///
  ///   - 普通男声 bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat
  ///
  ///   - 度逍遥/情感男声 bd_etts_common_speech_yyjw_mand_eng_high_am-mix_v3.0.0_20170512.dat
  ///
  ///   - 度丫丫/情感童声 bd_etts_common_speech_as_mand_eng_high_am_v3.0.0_20170516.dat
  ///
  /// [engineType] 合成引擎，`mix` or online, default 'mix'
  ///
  /// [notifyProgress] 是否通知合成及播放进度, default `true`
  ///
  /// [audioFocus] 是否请求安卓Audio Focus, default `true`
  static Future<dynamic> init(String appId, String appKey, String secretKey,
      String textModelPath, List<String> speechModelPath,
      {String engineType: 'mix', bool notifyProgress: true, bool audioFocus: true,}) async {
    return await _channel.invokeMethod('init', {
      "appId": appId,
      "appKey": appKey,
      "secretKey": secretKey,
      "engineType": engineType,
      "textModelPath": textModelPath,
      "speechModelPath": speechModelPath,
      "notifyProgress": notifyProgress,
      "audioFocus": audioFocus,
    });
  }

  ///
  /// [onlineSpeaker] 在线合成发音人, 0: 普通女声, 1: 普通男声, 2: 特别男声, 3: 情感男声, 4: 情感童声, default `0`.
  ///
  /// [offlineSpeaker] 离线合成发音人, 0: 普通女声, 1: 普通男声, 2: 情感男声, 3: 情感童声, default `0`.
  ///
  /// [speed] 合成语速, 0-15, default `5`.
  ///
  /// [pitch] 合成音调, 0-15, default `5`.
  ///
  /// [volume] 合成音量, 0-15, default `5`.
  ///
  /// [mixMode] 控制何种网络状况切换到离线。 SDK没有纯离线功能，强制在线优先。
  ///
  static Future<dynamic> setParams({
    String onlineSpeaker,
    String offlineSpeaker,
    int speed,
    int pitch,
    int volume,
    String mixMode,
  }) async {
    Map<String, String> params = Map();
    if (onlineSpeaker != null) {
      params['onlineSpeaker'] = onlineSpeaker;
    }
    if (offlineSpeaker != null) {
      params['offlineSpeaker'] = offlineSpeaker;
    }
    if (speed != null) {
      params['speed'] = speed.toString();
    }
    if (pitch != null) {
      params['pitch'] = pitch.toString();
    }
    if (volume != null) {
      params['volume'] = volume.toString();
    }
    if(mixMode != null) {
      params['mixMode'] = mixMode;
    }
    return await _channel.invokeMethod('setParams', params);
  }

  static Future<dynamic> clearParams() async {
    return await _channel.invokeMethod('clearParams');
  }

  static Future<dynamic> speak(String texts) async {
    return await _channel.invokeMethod('speak', texts);
  }

  static Future<dynamic> batchSpeak(List<String> texts) async {
    return await _channel.invokeMethod('batchSpeak', texts);
  }

  static Future<dynamic> pause() async {
    return await _channel.invokeMethod('pause');
  }

  static Future<dynamic> stop() async {
    return await _channel.invokeMethod('stop');
  }

  static Future<dynamic> resume() async {
    return await _channel.invokeMethod('resume');
  }

  static Future<dynamic> destroy() async {
    return await _channel.invokeMethod('destroy');
  }
}

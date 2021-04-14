import 'package:flutter/material.dart';
import 'dart:async';
import 'dart:io';
import 'dart:typed_data';

import 'package:path_provider/path_provider.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'package:flutter_baidu_tts/flutter_baidu_tts.dart';

/// normal speak
void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: SpeakingWidget(),
      ),
    );
  }
}

class SpeakingWidget extends StatefulWidget {
  @override
  SpeakingWidgetState createState() => SpeakingWidgetState();
}

class SpeakingWidgetState extends State<SpeakingWidget> {
  late TextEditingController _appIdController;
  late TextEditingController _appKeyController;
  late TextEditingController _secretKeyController;
  late TextEditingController _textController;

  static const Map<String, String> offlineSpeakers = {
    '0': '普通女声',
    '1': '普通男声',
    '2': '情感男声',
    '3': '情感童声',
  };

  static const Map<String, String> onlineSpeakers = {
    '0': '普通女声',
    '1': '普通男声',
    '2': '特别男声',
    '3': '情感男声',
    '4': '情感童声',
  };

  String engineType = 'mix';
  String onlineSpeaker = '0';
  String offlineSpeaker = '0';
  int speed = 5;
  int volume = 5;
  int pitch = 5;

  List<String>? texts;
  int? textIndex;
  int? textsLength;
  int? beginPos;
  int? endPos;

  int speakingState = 0;
  double? bufferPercent;
  double? speakPercent;
  bool? appIdValid;
  bool? appKeyValid;
  bool? secretKeyValid;

  String? textModelPath;
  List<String>? speechModelPath;

  @override
  void initState() {
    super.initState();
    _appIdController = TextEditingController()..text = '11005757';
    _appKeyController = TextEditingController()
      ..text = 'Ovcz19MGzIKoDDb3IsFFncG1';
    _secretKeyController = TextEditingController()
      ..text = 'e72ebb6d43387fc7f85205ca7e6706e2';
    _textController = TextEditingController(
        text:
            '''道可道，非常道；名可名，非常名。无名天地之始，有名万物之母。故常无欲，以观其妙；常有欲，以观其徼。此两者同出而异名，同谓之玄，玄之又玄，众妙之门。天下皆知美之为美，斯恶已；皆知善之为善，斯不善已。故有无相生，难易相成，长短相较，高下相倾，音声相和，前后相随。是以圣人处无为之事，行不言之教，万物作焉而不辞，生而不有，为而不恃，功成而弗居。夫唯弗居，是以不去。''');
  }

  @override
  void dispose() {
    FlutterBaiduTts.destroy();
    super.dispose();
  }

  Future<void> prepareModels() async {
    Directory directory = await getApplicationDocumentsDirectory();
    String basePath = '${directory.path}/baidu_tts';
    textModelPath = await copyAssetFile(basePath, 'bd_etts_text.dat');
    speechModelPath = [];
    speechModelPath!.add(await copyAssetFile(basePath,
        'bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat'));
    speechModelPath!.add(await copyAssetFile(basePath,
        'bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat'));
    speechModelPath!.add(await copyAssetFile(basePath,
        'bd_etts_common_speech_yyjw_mand_eng_high_am-mix_v3.0.0_20170512.dat'));
    speechModelPath!.add(await copyAssetFile(basePath,
        'bd_etts_common_speech_as_mand_eng_high_am_v3.0.0_20170516.dat'));
  }

  Future<String> copyAssetFile(String basePath, String filename) async {
    ByteData data = await rootBundle.load('assets/$filename');
    File dest = File('$basePath/$filename');
    if (dest.existsSync()) {
      await dest.delete();
    }
    await dest.create(recursive: true);
    await dest.writeAsBytes(
        data.buffer.asUint8List(data.offsetInBytes, data.lengthInBytes));
    return dest.path;
  }

  Future<dynamic> initTts(
      String appId, String appKey, String secretKey, String engineType) async {
    if (engineType == 'mix') {
      await prepareModels();
    }
    FlutterBaiduTts.ttsEventHandler = _handleTtsEvent;
    return FlutterBaiduTts.init(appId, appKey, secretKey, textModelPath!, speechModelPath!,
        engineType: engineType);
  }

  void _handleTtsEvent(TtsEvent event, dynamic arguments) async {
    print('Tts event $event $arguments');
    switch (event) {
      case TtsEvent.onInitFailed:
        break;
      case TtsEvent.onInitSuccess:
        setState(() {
          speakingState = 0;
        });
        break;
      case TtsEvent.onSynthesizeDataArrived:
        setState(() {
          bufferPercent = arguments[1] / textsLength;
        });
        break;
//      case TtsEvent.onSynthesizeFinish:
//        setState(() {
//          bufferPercent = 1;
//        });
//        break;
      case TtsEvent.onSpeechProgressChanged:
        setState(() {
          speakPercent = arguments[1] / textsLength;
        });
        break;
      case TtsEvent.onSpeechStart:
        setState(() {
          speakingState = 1;
        });
        break;
      case TtsEvent.onSpeechFinish:
        this.textIndex = this.textIndex! + 1;
        if (this.textIndex == this.texts!.length) {
          _resetProgress();
        } else {
          setState(() {
            this.speakPercent = 0;
            this.bufferPercent = 0;
            this.beginPos = this.endPos;
            this.endPos = this.endPos! + this.texts![this.textIndex!].length;
          });
          speak(this.texts![this.textIndex!]);
        }
        break;
      case TtsEvent.onError:
        _resetProgress();
        break;
      default:
        break;
    }
  }

  void _resetProgress() {
    setState(() {
      speakingState = 0;
      bufferPercent = 0;
      speakPercent = 0;
      beginPos = 0;
      endPos = 0;
    });
    _textController.selection = TextSelection(baseOffset: 0, extentOffset: 0);
  }

  void _setOfflineSpeaker(String value) async {
    await FlutterBaiduTts.setParams(offlineSpeaker: value);
    setState(() {
      offlineSpeaker = value;
    });
  }

  void _setOnlineSpeaker(String value) async {
    await FlutterBaiduTts.setParams(onlineSpeaker: value);
    setState(() {
      onlineSpeaker = value;
    });
  }

  void _changeEngineType(String? value) async {
    setState(() {
      engineType = value!;
    });
  }

  void pauseSpeaking() async {
    await FlutterBaiduTts.pause();
    setState(() {
      speakingState = 2;
    });
  }

  void resumeSpeaking() async {
    await FlutterBaiduTts.resume();
    setState(() {
      speakingState = 1;
    });
  }

  void stopSpeaking() async {
    await FlutterBaiduTts.stop();
  }

  void startSpeaking() async {
    String texts = _textController.text;
    if (texts.isNotEmpty) {
      this.texts = splitTexts(texts);
      this.textIndex = 0;
      this.beginPos = 0;
      this.endPos = this.texts!.first.length;
      speak(this.texts![this.textIndex!]);
    }
  }

  void speak(String text) async {
    setState(() {
      textsLength = text.length;
    });
    _textController.selection =
        TextSelection(baseOffset: beginPos!, extentOffset: endPos!);
    await FlutterBaiduTts.speak(text);
  }

  List<String> breakPunches = ['。', '！', '…', '？', '.', '?', '!', '\n'];

  List<String> splitTexts(String texts) {
    List<String> result = [];
    int startIndex = 0;
    bool breaking = false;
    for (int i = 0; i < texts.length; i++) {
      if (breakPunches.contains(texts[i])) {
        breaking = true;
      } else if (breaking) {
        breaking = false;
        result.add(texts.substring(startIndex, i));
        startIndex = i;
      }
    }
    if (startIndex < texts.length) {
      result.add(texts.substring(startIndex));
    }
    return result;
  }

  void clearParams() {
    FlutterBaiduTts.clearParams();
  }

  Widget speakingBtn() {
    switch (speakingState) {
      case 0:
        return IconButton(
            icon: Icon(Icons.play_arrow), onPressed: () => startSpeaking());
      case 1:
        return IconButton(
            icon: Icon(Icons.pause), onPressed: () => pauseSpeaking());
      case 2:
        return IconButton(
            icon: Icon(Icons.play_arrow), onPressed: () => resumeSpeaking());
      default:
        return IconButton(
            icon: Icon(Icons.play_arrow), onPressed: () => startSpeaking());
    }
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: <Widget>[
          Row(
            children: <Widget>[
              Text('AppId：'),
              Expanded(
                child: TextFormField(
                  decoration: InputDecoration(
                    hintText: 'AppId',
                    errorText:
                        appIdValid == true ? null : 'AppId must not be empty!',
                  ),
                  controller: _appIdController,
                ),
              )
            ],
          ),
          Row(
            children: <Widget>[
              Text('AppKey：'),
              Expanded(
                child: TextFormField(
                  decoration: InputDecoration(
                    hintText: 'AppKey',
                    errorText: appKeyValid == true
                        ? null
                        : 'AppKey must not be empty!',
                  ),
                  controller: _appKeyController,
                ),
              )
            ],
          ),
          Row(
            children: <Widget>[
              Text('SecretKey：'),
              Expanded(
                child: TextFormField(
                  decoration: InputDecoration(
                    hintText: 'SecretKey',
                    errorText: secretKeyValid == true
                        ? null
                        : 'SecretKey must not be empty!',
                  ),
                  controller: _secretKeyController,
                ),
              )
            ],
          ),
          Row(
            children: <Widget>[
              Text('Mode：'),
              Expanded(
                child: DropdownButton(
                  value: engineType,
                  items: ['online', 'mix']
                      .map((k) => DropdownMenuItem(value: k, child: Text(k)))
                      .toList(),
                  onChanged: _changeEngineType,
                ),
              ),
            ],
          ),
          ElevatedButton(
            onPressed: () {
              if (_appIdController.text.isEmpty) {
                setState(() {
                  appIdValid = false;
                });
                return;
              }
              if (_appKeyController.text.isEmpty) {
                setState(() {
                  appKeyValid = false;
                });
                return;
              }
              if (_secretKeyController.text.isEmpty) {
                setState(() {
                  secretKeyValid = false;
                });
                return;
              }
              initTts(_appIdController.text, _appKeyController.text,
                  _secretKeyController.text, engineType);
              setState(() {
                appIdValid = true;
                appKeyValid = true;
                secretKeyValid = true;
              });
            },
            child: Text('Init'),
          ),
          TextField(
            maxLines: 10,
            controller: _textController,
          ),
          Container(
            alignment: Alignment.centerLeft,
            child: Row(
              children: <Widget>[
                Text('离线发音：'),
                Expanded(
                  child: SingleChildScrollView(
                    scrollDirection: Axis.horizontal,
                    child: Row(
                        children: offlineSpeakers.keys
                            .map((k) => Container(
                                  decoration: BoxDecoration(
                                    border: Border.all(
                                        color: k == offlineSpeaker
                                            ? Colors.deepOrange
                                            : Colors.grey),
                                  ),
                                  child: ElevatedButton(
                                    onPressed: () => _setOfflineSpeaker(k),
                                    child: Text(offlineSpeakers[k]!),
                                  ),
                                ))
                            .toList()),
                  ),
                ),
              ],
            ),
          ),
          Container(
            alignment: Alignment.centerLeft,
            child: Row(
              children: <Widget>[
                Text('在线发音：'),
                Expanded(
                  child: SingleChildScrollView(
                    scrollDirection: Axis.horizontal,
                    child: Row(
                        children: onlineSpeakers.keys
                            .map((k) => Container(
                                  decoration: BoxDecoration(
                                    border: Border.all(
                                        color: k == onlineSpeaker
                                            ? Colors.deepOrange
                                            : Colors.grey),
                                  ),
                                  child: ElevatedButton(
                                    onPressed: () => _setOnlineSpeaker(k),
                                    child: Text(onlineSpeakers[k]!),
                                  ),
                                ))
                            .toList()),
                  ),
                ),
              ],
            ),
          ),
          Container(
            alignment: Alignment.centerLeft,
            child: Row(
              children: <Widget>[
                Text('语速：'),
                Expanded(
                  child: Slider(
                      value: speed.toDouble(),
                      min: FlutterBaiduTts.minSpeed.toDouble(),
                      max: FlutterBaiduTts.maxSpeed.toDouble(),
                      onChanged: (value) {
                        setState(() {
                          speed = value.toInt();
                        });
                        FlutterBaiduTts.setParams(speed: speed);
                      }),
                ),
              ],
            ),
          ),
          Container(
            alignment: Alignment.centerLeft,
            child: Row(
              children: <Widget>[
                Text('语调：'),
                Expanded(
                  child: Slider(
                      value: pitch.toDouble(),
                      min: FlutterBaiduTts.minPitch.toDouble(),
                      max: FlutterBaiduTts.maxPitch.toDouble(),
                      onChanged: (value) {
                        setState(() {
                          pitch = value.toInt();
                        });
                        FlutterBaiduTts.setParams(pitch: pitch);
                      }),
                ),
              ],
            ),
          ),
          Container(
            alignment: Alignment.centerLeft,
            child: Row(
              children: <Widget>[
                Text('音量：'),
                Expanded(
                  child: Slider(
                      value: volume.toDouble(),
                      min: FlutterBaiduTts.minVolume.toDouble(),
                      max: FlutterBaiduTts.maxVolume.toDouble(),
                      onChanged: (value) {
                        setState(() {
                          volume = value.toInt();
                        });
                        FlutterBaiduTts.setParams(volume: value.toInt());
                      }),
                ),
              ],
            ),
          ),
          Container(
            child: Row(
              children: <Widget>[
                speakingState == 0
                    ? IconButton(icon: Icon(Icons.stop), onPressed: null)
                    : IconButton(
                        icon: Icon(Icons.stop),
                        onPressed: () {
                          FlutterBaiduTts.stop().then((_) => _resetProgress());
                        },
                      ),
                speakingBtn(),
                Expanded(
                  child: Container(
                    height: 20.0,
                    color: Colors.grey,
                    alignment: Alignment.centerLeft,
                    child: FractionallySizedBox(
                      heightFactor: 1,
                      widthFactor: (bufferPercent ?? 0),
                      alignment: Alignment.centerLeft,
                      child: Container(
                        color: Colors.blueAccent,
                        alignment: Alignment.centerLeft,
                        child: FractionallySizedBox(
                          heightFactor: 1,
                          widthFactor: (speakPercent ?? 0),
                          alignment: Alignment.centerLeft,
                          child: Container(
                            color: Colors.deepOrangeAccent,
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
                Container(
                  width: 20,
                  height: 20,
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}

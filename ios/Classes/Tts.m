//
//  Tts.m
//  flutter_baidu_tts
//
//  Created by Dolphin on 2019/7/11.
//

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>
#import "Tts.h"
#import "BDSSpeechSynthesizer.h"
#import <AVFoundation/AVFoundation.h>

BOOL enableLog = NO;

@implementation Tts{
    NSString* _appId;
    FlutterMethodChannel* _channel;
    BOOL _notifyProgress;
    NSString* _textFile;
    NSArray<NSString*>* _speechModelFiles;
    NSString* documentsDirectory;
}

- (instancetype)initWithFlutter:(FlutterMethodChannel*)methodChannel textFile:(NSString*)textFile speechModelFiles:(NSArray<NSString*>*)speechModelFiles notifyProgress:(BOOL)notifyProgress {
    _channel = methodChannel;
    _textFile = textFile;
    _speechModelFiles = speechModelFiles;
    _notifyProgress = notifyProgress;
    documentsDirectory = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
    return self;
}

- (void)initTts:(NSString*)appId appKey:(NSString*)appKey secretKey:(NSString*)secretKey{
    _appId = appId;
    // configure Online TTS
    [[BDSSpeechSynthesizer sharedInstance] setApiKey:appId withSecretKey:secretKey];
    [[AVAudioSession sharedInstance]setCategory:AVAudioSessionCategoryPlayback error:nil];
    
    
    // configure Offline TTS
    NSError *err = nil;
//    NSString* offlineEngineSpeechData = [documentsDirectory stringByAppendingPathComponent:_speechModelFiles[0]];
//    NSString* offlineChineseAndEnglishTextData = [documentsDirectory stringByAppendingPathComponent:_textFile];
    NSString* offlineEngineSpeechData =_speechModelFiles[0];
    NSString* offlineChineseAndEnglishTextData = _textFile;

    [[BDSSpeechSynthesizer sharedInstance] verifyDataFile:offlineEngineSpeechData error:&err];
    if(err) {
        NSLog(@"offlineTextDat invalid %@", err);
        [_channel invokeMethod:@"onInitFailed" arguments:[err localizedDescription]];
        return;
    }
    [[BDSSpeechSynthesizer sharedInstance] verifyDataFile:offlineChineseAndEnglishTextData error:&err];
    if(err) {
        NSLog(@"offlineSpeechDat invalid %@", err);
        [_channel invokeMethod:@"onInitFailed" arguments:[err localizedDescription]];
        return;
    }
    err = [[BDSSpeechSynthesizer sharedInstance] loadOfflineEngine:offlineChineseAndEnglishTextData speechDataPath:offlineEngineSpeechData licenseFilePath:nil withAppCode:appId];
    if(err) {
        NSLog(@"loadOfflineEngine failed %@", err);
        [_channel invokeMethod:@"onInitFailed" arguments:[err localizedDescription]];
        return;
    }
    [[BDSSpeechSynthesizer sharedInstance] setSynthesizerDelegate:self];
    [[BDSSpeechSynthesizer sharedInstance] setSynthParam:@(BDS_SYNTHESIZER_SPEAKER_FEMALE) forKey:BDS_SYNTHESIZER_PARAM_SPEAKER];
    [[BDSSpeechSynthesizer sharedInstance] setSynthParam:[NSNumber numberWithInt:9] forKey:BDS_SYNTHESIZER_PARAM_VOLUME];
    [[BDSSpeechSynthesizer sharedInstance] setSynthParam:[NSNumber numberWithInt:5] forKey:BDS_SYNTHESIZER_PARAM_SPEED];
    [[BDSSpeechSynthesizer sharedInstance] setSynthParam:[NSNumber numberWithInt:5] forKey:BDS_SYNTHESIZER_PARAM_PITCH];
    [_channel invokeMethod:@"onInitSuccess" arguments:nil];
}

- (void)onMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result{
    if([@"speak" isEqualToString:call.method]) {
        result([NSNumber numberWithInteger:[self speak:call.arguments]]);
    } else if([@"batchSpeak" isEqualToString:call.method]) {
        result([self batchSpeak:call.arguments]);
    } else if([@"stop" isEqualToString:call.method]) {
        result([NSNumber numberWithInteger:[self stop]]);
    } else if([@"pause" isEqualToString:call.method]) {
        result([NSNumber numberWithInteger:[self pause]]);
    } else if([@"resume" isEqualToString:call.method]) {
        result([NSNumber numberWithInteger:[self resume]]);
    } else if([@"destroy" isEqualToString:call.method]) {
        [self destroy];
        result([NSNumber numberWithInteger:1]);
    } else if([@"setParams" isEqualToString:call.method]) {
        [self setParams:call.arguments];
        result([NSNumber numberWithInteger:1]);
    }
    else {
        result(FlutterMethodNotImplemented);
    }
}

- (NSInteger)speak:(NSString*)texts {
    NSError* err = nil;
    NSInteger result = [[BDSSpeechSynthesizer sharedInstance] speakSentence:texts withError:&err];
    if(result == -1) {
        NSLog(@"failed to speak %@", err);
    }
    return result;
}

/**
 * 返回每个Sentence的编号
 **/
- (NSMutableArray*)batchSpeak:(NSArray*)texts{
    NSMutableArray *results = [[NSMutableArray alloc] initWithCapacity:texts.count];
    NSInteger result = 0;
    for(NSString* text in texts) {
        result = [self speak:text];
        [results addObject:[NSNumber numberWithInteger:result]];
    }
    return results;
}

- (NSInteger)pause{
    [[BDSSpeechSynthesizer sharedInstance] pause];
    return 1;
}

- (NSInteger)resume{
    [[BDSSpeechSynthesizer sharedInstance] resume];
    return 1;
}

- (NSInteger)stop{
    [[BDSSpeechSynthesizer sharedInstance] cancel];
    return 1;
}

- (void) destroy{
    [BDSSpeechSynthesizer releaseInstance];
}

- (void)setParams:(NSDictionary*)params {
    if(params[@"onlineSpeaker"]) {
        [[BDSSpeechSynthesizer sharedInstance] setSynthParam:[NSNumber numberWithInteger:[params[@"onlineSpeaker"] integerValue]] forKey:BDS_SYNTHESIZER_PARAM_SPEAKER];
    }
    if(params[@"offlineSpeaker"]) {
        [self loadModel:[params[@"offlineSpeaker"] integerValue]];
    }
    if(params[@"volume"]) {
        [[BDSSpeechSynthesizer sharedInstance] setSynthParam:[NSNumber numberWithInteger:[params[@"volume"] integerValue]] forKey:BDS_SYNTHESIZER_PARAM_VOLUME];
    }
    if(params[@"speed"]) {
        [[BDSSpeechSynthesizer sharedInstance] setSynthParam:[NSNumber numberWithInteger:[params[@"speed"] integerValue]] forKey:BDS_SYNTHESIZER_PARAM_SPEED];
    }
    if(params[@"pitch"]) {
        [[BDSSpeechSynthesizer sharedInstance] setSynthParam:[NSNumber numberWithInteger:[params[@"pitch"] integerValue]] forKey:BDS_SYNTHESIZER_PARAM_PITCH];
    }
    
}

/**
 * 切换离线发音人
 *
 **/
- (void)loadModel:(NSInteger)model{
    NSString* textFile = [documentsDirectory stringByAppendingPathComponent:_textFile];
    NSString* speechFile = [documentsDirectory stringByAppendingPathComponent:_speechModelFiles[model]];
    NSError* result = [[BDSSpeechSynthesizer sharedInstance] loadOfflineEngine:speechFile speechDataPath:textFile licenseFilePath:nil withAppCode:_appId];
    if(result) {
        NSLog(@"loadModel result: %@", result);
    }
}


/**
 * @brief Began synthesizing new sentence.
 *        Will receive one call for each queued sentence when SDK starts synthesizing them
 *
 * @param SynthesizeSentence Sentence ID generated BY SDK and returned by
 *        speakSentence- and synthesizeSentence- interface.
 *
 *        SynthesizeSentence may be any value from NSInteger's value
 *        range, including negative values but excluding -1, which is reserved to
 *        indicate none/error.
 */
- (void)synthesizerStartWorkingSentence:(NSInteger)SynthesizeSentence{
    if(enableLog) {
        NSLog(@"synthesizerStartWorkingSentence SynthesizeSentence=%ld", SynthesizeSentence);
    }
    [_channel invokeMethod:@"onSynthesizeStart" arguments:[NSNumber numberWithInteger:SynthesizeSentence]];
}

/**
 * @brief Finished synthesizing a sentence.
 *        Will receive one call for each queued sentence when SDK finishes synthesizing them
 *
 * @param SynthesizeSentence Sentence ID generated BY SDK and returned by
 *        speakSentence- and synthesizeSentence- interface.
 *
 *         SynthesizeSentence may be any value from NSInteger's value
 *        range, including negative values but excluding -1, which is reserved to
 *        indicate none/error.
 */
- (void)synthesizerFinishWorkingSentence:(NSInteger)SynthesizeSentence{
    if(enableLog) {
        NSLog(@"synthesizerFinishWorkingSentence SynthesizeSentence=%ld", SynthesizeSentence);
    }
    [_channel invokeMethod:@"onSynthesizeFinish" arguments:[NSNumber numberWithInteger:SynthesizeSentence]];
}

/**
 * @brief Began speak a sentence.
 *        Will receive one call for each queued sentence when SDK begans playback on them
 *        Not called if only synthesizing
 *
 * @param SpeakSentence Sentence ID generated BY SDK and returned by
 *        speakSentence- interface.
 *
 *        SpeakSentence may be any value from NSInteger's value
 *        range, including negative values but excluding -1, which is reserved to
 *        indicate none/error.
 */
- (void)synthesizerSpeechStartSentence:(NSInteger)SpeakSentence{
    if(enableLog) {
        NSLog(@"synthesizerSpeechStartSentence SpeakSentence=%ld", SpeakSentence);
    }
    [_channel invokeMethod:@"onSpeechStart" arguments:[NSNumber numberWithInteger:SpeakSentence]];
}
/**
 * @brief 朗读完成
 *        Will receive one call for each queued sentence when SDK finishes playback on them
 *        Not called if only synthesizing
 *
 * @param SpeakSentence Sentence ID generated BY SDK and returned by
 *        speakSentence- interface.
 *
 *        SpeakSentence may be any value from NSInteger's value
 *        range, including negative values but excluding -1, which is reserved to
 *        indicate none/error.
 */
- (void)synthesizerSpeechEndSentence:(NSInteger)SpeakSentence{
    if(enableLog) {
        NSLog(@"synthesizerSpeechEndSentence SpeakSentence=%ld", SpeakSentence);
    }
    [_channel invokeMethod:@"onSpeechFinish" arguments:[NSNumber numberWithInteger:SpeakSentence]];
}

/**
 * @brief 新的语音数据已经合成
 *
 * @param newData 语音数据
 * @param fmt Audio format in passed buffer.
 * @param newLength Currently synthesized character count of current sentence.
 *
 * @param SynthesizeSentence Sentence ID generated BY SDK and returned by
 *        speakSentence- and synthesizeSentence- interface.
 *
 *        SynthesizeSentence may be any value from NSInteger's value
 *        range, including negative values but excluding -1, which is reserved to
 *        indicate none/error.
 */
- (void)synthesizerNewDataArrived:(NSData *)newData
                       DataFormat:(BDSAudioFormat)fmt
                   characterCount:(int)newLength
                   sentenceNumber:(NSInteger)SynthesizeSentence{
    if(enableLog) {
        NSLog(@"synthesizerNewDataArrived newLength=%d SynthesizeSentence=%ld", newLength, SynthesizeSentence);
    }
    if(_notifyProgress) {
        [_channel invokeMethod:@"onSynthesizeDataArrived" arguments:[NSArray arrayWithObjects:[NSNumber numberWithInteger:SynthesizeSentence], [NSNumber numberWithInt:newLength], nil]];
    }
}


/**
 * @brief Gives an estimation about how many characters have been spoken so far.
 *        Not called if only synthesizing
 *
 * @param newLength Currently finished speaking character count of current sentence.
 *
 * @param SpeakSentence Sentence ID generated BY SDK and returned by
 *        speakSentence- interface.
 *
 *        SpeakSentence may be any value from NSInteger's value
 *        range, including negative values but excluding -1, which is reserved to
 *        indicate none/error.
 */
- (void)synthesizerTextSpeakLengthChanged:(int)newLength
                           sentenceNumber:(NSInteger)SpeakSentence{
    if(enableLog) {
        NSLog(@"synthesizerTextSpeakLengthChanged newLenght=%d SpeakSentence=%ld", newLength, (long)SpeakSentence);
    }
    if(_notifyProgress) {
        [_channel invokeMethod:@"onSpeechProgressChanged" arguments:[NSArray arrayWithObjects:[NSNumber numberWithInteger:SpeakSentence], [NSNumber numberWithInt:newLength], nil]];
    }
}

/**
 * @brief invoked when player gets paused
 */
//- (void)synthesizerdidPause;

//- (void)synthesizerPaused:(BDSAudioPlayerPauseSources)src __attribute__((deprecated("src parameter will be removed from paused callback. Current implementation of synthesizerPaused is kept in for backward compatibility for now, but will always pass BDS_AUDIO_PLAYER_PAUSE_SOURCE_USER as pause source. Start using - (void)synthesizerdidPause; instead.")));

/**
 * @brief invoked when player is resumed from pause
 */
//- (void)synthesizerResumed;

/**
 * @brief 合成器发生错误
 *
 * @param error 错误对象
 * @param SpeakSentence Sentence ID generated BY SDK and returned by
 *        speakSentence- interface.
 *
 * @param SynthesizeSentence Sentence ID generated BY SDK and returned by
 *        speakSentence- and synthesizeSentence- interface.
 *
 *        SpeakSentence may be any value from NSInteger's value
 *        range, including negative values, also -1 may be encountered in this callback
 *        if error happened while only synthesizing or playback haven't started yet.
 *
 *        SynthesizeSentence may be any value from NSInteger's value
 *        range, including negative values, also -1 may be encountered in this callback
 *        if error happened in player and synthesizer had already finished it's work.
 */
- (void)synthesizerErrorOccurred:(NSError *)error
                        speaking:(NSInteger)SpeakSentence
                    synthesizing:(NSInteger)SynthesizeSentence{
    NSLog(@"onError %@", error);
    [_channel invokeMethod:@"onError" arguments:[NSArray arrayWithObjects: [NSNumber numberWithInteger:SpeakSentence], error.code, nil]];
}

@end

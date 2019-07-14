//
//  Tts.h
//  Pods
//
//  Created by Dolphin on 2019/7/11.
//

#ifndef Tts_h
#define Tts_h
#import <Flutter/Flutter.h>
#import "BDSSpeechSynthesizer.h"

@interface Tts:NSObject<BDSSpeechSynthesizerDelegate>
@property FlutterMethodChannel* channel;
- (instancetype)initWithFlutter: (FlutterMethodChannel*)methodChannel
                       textFile:(NSString*)textFile speechModelFiles:(NSArray<NSString*>*)speechModelFiles notifyProgress:(BOOL)notifyProgress;
- (void)initTts:(NSString*)appId appKey:(NSString*)appKey secretKey:(NSString*)secretKey;
- (NSInteger)stop;
- (void)onMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result;
@end

#endif /* Tts_h */

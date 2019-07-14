#import "FlutterBaiduTtsPlugin.h"
#import "Tts.h"

static Tts* tts;
static NSString* appId;
static NSString* appKey;
static NSString* secretKey;
static NSString* engineType;
static NSString* textModelPath;
static NSArray* speechModelPath;
static BOOL notifyProgress;

static FlutterMethodChannel* _channel;

@implementation FlutterBaiduTtsPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  _channel = [FlutterMethodChannel
      methodChannelWithName:@"com.whaleread.flutter_baidu_tts"
            binaryMessenger:[registrar messenger]];
  FlutterBaiduTtsPlugin* instance = [[FlutterBaiduTtsPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:_channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"init" isEqualToString:call.method]) {
      if(tts) {
          [tts stop];
      }
      NSDictionary* arguments = call.arguments;
      appId = arguments[@"appId"];
      appKey = arguments[@"appKey"];
      secretKey = arguments[@"secretKey"];
      engineType = arguments[@"engineType"];
      textModelPath = arguments[@"textModelPath"];
      speechModelPath = arguments[@"speechModelPath"];
      notifyProgress = [[arguments objectForKey:@"notifyProgress"] boolValue];
      [self initTts];
      result(@(YES));
  } else {
      if([@"destroy" isEqualToString:call.method]) {
          if(tts != nil) {
              [tts stop];
              tts = nil;
              result(@(YES));
          }
          return;
      }
      [tts onMethodCall:call result:result];
  }
}

- (void)initTts{
    tts = [[Tts alloc] initWithFlutter:_channel textFile:textModelPath speechModelFiles:speechModelPath notifyProgress:notifyProgress];
    [tts initTts:appId appKey:appKey secretKey:secretKey];
}

@end

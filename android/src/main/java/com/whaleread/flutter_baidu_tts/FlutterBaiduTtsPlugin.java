package com.whaleread.flutter_baidu_tts;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.baidu.tts.client.TtsMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterBaiduTtsPlugin
 */
public class FlutterBaiduTtsPlugin implements MethodCallHandler, PluginRegistry.RequestPermissionsResultListener {
    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        FlutterBaiduTtsPlugin instance = new FlutterBaiduTtsPlugin();
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "com.whaleread.flutter_baidu_tts");
        channel.setMethodCallHandler(instance);
        FlutterBaiduTtsPlugin.methodChannel = channel;
        FlutterBaiduTtsPlugin.registrar = registrar;
        registrar.addRequestPermissionsResultListener(instance);
    }

    private static Registrar registrar;
    private static Tts tts;
    private static MethodChannel methodChannel;
    private static String appId;
    private static String appKey;
    private static String secretKey;
    /**
     * online, mix
     */
    private static String engineType;
    private static String textModelPath;
    private static List<String> speechModelPath;
    private static boolean notifyProgress;
    private static boolean audioFocus;
    private static boolean enableLog;

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("init")) {
            if (tts != null) {
                tts.destroy();
            }
            if(call.hasArgument("enableLog")) {
                enableLog = call.argument("enableLog");
            }
            appId = call.argument("appId");
            appKey = call.argument("appKey");
            secretKey = call.argument("secretKey");
            engineType = call.argument("engineType");
            textModelPath = call.argument("textModelPath");
            speechModelPath = call.argument("speechModelPath");
            //noinspection ConstantConditions
            notifyProgress = call.argument("notifyProgress");
            //noinspection ConstantConditions
            audioFocus = call.argument("audioFocus");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermission();
            } else {
                initTts();
            }
            result.success(1);
        } else {
            if(call.method.equals("destroy")) {
                if(tts != null) {
                    tts.destroy();
                    tts = null;
                    result.success(1);
                }
                return;
            }
            if (tts == null) {
                throw new IllegalStateException("TTS not initialized!");
            }
            tts.onMethodCall(call, result);
        }
    }

    private static TtsMode parseTtsMode(String mode) {
        switch(mode) {
            case "online":
                return TtsMode.ONLINE;
            case "offline":
                return TtsMode.OFFLINE;
            default:
                return TtsMode.MIX;
        }
    }

    private void initTts() {
        tts = new Tts(registrar, methodChannel, parseTtsMode(engineType), textModelPath, speechModelPath, notifyProgress, audioFocus, enableLog);
        tts.init(appId, appKey, secretKey);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
//              Manifest.permission.WRITE_EXTERNAL_STORAGE,
//              Manifest.permission.WRITE_SETTINGS,
//              Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
//              Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != registrar.context().checkSelfPermission(perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String[] lackPermissions = new String[toApplyList.size()];
        if (toApplyList.isEmpty()) {
            initTts();
        } else {
            _requestCode = Integer.parseInt(new SimpleDateFormat("MMddHHmmss", Locale.CHINA).format(new Date()));
            registrar.activity().requestPermissions(toApplyList.toArray(lackPermissions), _requestCode);
        }
    }

    private int _requestCode;

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		Log.d("FlutterBaiduTtsPlugin", "onRequestPermissionsResult " + requestCode);
        if (requestCode == _requestCode) {
            if(hasAllPermissionsGranted(grantResults)) {
                initTts();
            } else {
//                initTts();
                methodChannel.invokeMethod("onRequestPermissionsFailed", null);
            }
            return true;
        }
        return false;
    }
}

package com.whaleread.flutter_baidu_tts;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import com.baidu.tts.client.TtsMode;

import java.util.ArrayList;
import java.util.List;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterBaiduTtsPlugin
 */
public class FlutterBaiduTtsPlugin implements MethodCallHandler, PluginRegistry.RequestPermissionsResultListener, FlutterPlugin, ActivityAware, ActivityProvider {
    private Tts tts;
    private MethodChannel methodChannel;
    private String appId;
    private String appKey;
    private String secretKey;
    /**
     * online, mix
     */
    private String engineType;
    private String textModelPath;
    private List<String> speechModelPath;
    private boolean notifyProgress;
    private boolean audioFocus;
    private boolean enableLog;
    private int permRequestCode;

    private Registrar registrar;
    private ActivityPluginBinding activityPluginBinding;

    /**
     * Plugin registration. For only pre 1.12
     */
    @Deprecated
    public static void registerWith(Registrar registrar) {
        FlutterBaiduTtsPlugin instance = new FlutterBaiduTtsPlugin();
        instance.init(registrar.messenger());
        instance.registrar = registrar;
        registrar.addRequestPermissionsResultListener(instance);
    }

    private void init(BinaryMessenger messenger) {
        final MethodChannel channel = new MethodChannel(messenger, "com.whaleread.flutter_baidu_tts");
        channel.setMethodCallHandler(this);
        this.methodChannel = channel;
    }

    private void attachToActivity(ActivityPluginBinding activityPluginBinding) {
        this.activityPluginBinding = activityPluginBinding;
        activityPluginBinding.addRequestPermissionsResultListener(this);
    }

    private void detachToActivity() {
        if(activityPluginBinding != null) {
            activityPluginBinding.removeRequestPermissionsResultListener(this);
        }
        this.activityPluginBinding = null;
    }

    @Override
    public Activity getActivity() {
        if(registrar != null) {
            return registrar.activity();
        }
        return activityPluginBinding == null ? null : activityPluginBinding.getActivity();
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        init(binding.getBinaryMessenger());
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        methodChannel.setMethodCallHandler(null);
        methodChannel = null;
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        attachToActivity(binding);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        detachToActivity();
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        attachToActivity(binding);
    }

    @Override
    public void onDetachedFromActivity() {
        detachToActivity();
    }

    @Override
    public void onMethodCall(MethodCall call, @NonNull Result result) {
        if (call.method.equals("init")) {
            if (tts != null) {
                tts.destroy();
            }
            if(call.hasArgument("enableLog")) {
                enableLog = Boolean.TRUE.equals(call.argument("enableLog"));
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
            permRequestCode = call.hasArgument("permRequestCode") ? call.argument("permRequestCode") : 9527001;
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
                result.error("failed", "TTS not initialized!", null);
                return;
            }
            tts.onMethodCall(call, result);
        }
    }

    private static TtsMode parseTtsMode(String mode) {
        if ("online".equals(mode)) {
            return TtsMode.ONLINE;
        }
        return TtsMode.MIX;
    }

    private void initTts() {
        tts = new Tts(this, methodChannel, parseTtsMode(engineType), textModelPath, speechModelPath, notifyProgress, audioFocus, enableLog);
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
            if (PackageManager.PERMISSION_GRANTED != getActivity().checkSelfPermission(perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String[] lackPermissions = new String[toApplyList.size()];
        if (toApplyList.isEmpty()) {
            initTts();
        } else {
            getActivity().requestPermissions(toApplyList.toArray(lackPermissions), permRequestCode);
        }
    }

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
        if (requestCode == permRequestCode) {
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

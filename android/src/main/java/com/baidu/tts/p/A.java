/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.p;

import android.content.Context;
import android.os.Build;
import com.baidu.tts.h.b.B;
import com.baidu.tts.jni.EmbeddedSynthesizerEngine;
import com.baidu.tts.tools.CommonUtility;
import com.baidu.tts.tools.GetCUID;
import org.json.JSONException;
import org.json.JSONObject;

public class A {
    public static String a(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("System", (Object)Build.VERSION.RELEASE);
            jSONObject.put("SystemVersion", (Object)(Build.VERSION.SDK + ""));
            jSONObject.put("PhoneModel", (Object)Build.MODEL);
            jSONObject.put("CPU", (Object)Build.CPU_ABI);
            jSONObject.put("NetworkType", (Object)CommonUtility.getNetworkInfo(context));
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static String b(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("Cuid", (Object)GetCUID.getCUID(context));
            jSONObject.put("AppPackageName", (Object)context.getPackageName());
            jSONObject.put("SDKVersion", (Object)B.a().j());
            jSONObject.put("soInfo", (Object)EmbeddedSynthesizerEngine.bdTTSGetEngineParam());
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
        return jSONObject.toString();
    }
}


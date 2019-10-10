/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package com.baidu.tts.tools;

import android.content.Context;
import android.text.TextUtils;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.tools.CommonParam;
import com.baidu.tts.tools.SharedPreferencesUtils;

public class GetCUID {
    private GetCUID() {
    }

    public static String getCUID(Context context) {
        String string = SharedPreferencesUtils.getString(context, "CUID", "");
        if (TextUtils.isEmpty((CharSequence)string)) {
            string = CommonParam.getCUID(context);
            SharedPreferencesUtils.putString(context, "CUID", string);
        } else {
            LoggerProxy.d("Device", "read deviceID:" + string);
        }
        return string;
    }
}


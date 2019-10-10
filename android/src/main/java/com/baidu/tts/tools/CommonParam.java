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
import com.baidu.tts.tools.DeviceId;

public class CommonParam {
    private static final String a = CommonParam.class.getSimpleName();

    public static String getCUID(Context paramContext) {
        String string = CommonParam.a(paramContext);
        String string2 = DeviceId.getIMEI(paramContext);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string2 = "0";
        }
        StringBuffer stringBuffer = new StringBuffer(string2);
        string2 = stringBuffer.reverse().toString();
        String string3 = string + "|" + string2;
        return string3;
    }

    private static String a(Context context) {
        return DeviceId.getDeviceID(context);
    }
}


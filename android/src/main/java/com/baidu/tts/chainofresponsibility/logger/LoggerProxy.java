/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.baidu.tts.chainofresponsibility.logger;

import android.util.Log;

public class LoggerProxy {
    private static boolean a = false;

    public static void v(String tag, String message) {
        if (a || Log.isLoggable((String)"TTSLOG", (int)2)) {
            Log.v((String)("bdtts-" + tag), (String)message);
        }
    }

    public static void i(String tag, String message) {
        if (a || Log.isLoggable((String)"TTSLOG", (int)4)) {
            Log.i((String)("bdtts-" + tag), (String)message);
        }
    }

    public static void d(String tag, String message) {
        if (a || Log.isLoggable((String)"TTSLOG", (int)3)) {
            Log.d((String)("bdtts-" + tag), (String)message);
        }
    }

    public static void w(String tag, String message) {
        if (a || Log.isLoggable((String)"TTSLOG", (int)5)) {
            Log.w((String)("bdtts-" + tag), (String)message);
        }
    }

    public static void e(String tag, String message) {
        if (a || Log.isLoggable((String)"TTSLOG", (int)6)) {
            Log.e((String)("bdtts-" + tag), (String)message);
        }
    }

    public static void printable(boolean isPrint) {
        a = isPrint;
    }
}


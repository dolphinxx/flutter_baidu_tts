/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package com.baidu.tts.loopj;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import com.baidu.tts.loopj.LogInterface;

public class LogHandler
implements LogInterface {
    boolean mLoggingEnabled = true;
    int mLoggingLevel = 2;

    public boolean isLoggingEnabled() {
        return this.mLoggingEnabled;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.mLoggingEnabled = loggingEnabled;
    }

    public int getLoggingLevel() {
        return this.mLoggingLevel;
    }

    public void setLoggingLevel(int loggingLevel) {
        this.mLoggingLevel = loggingLevel;
    }

    public boolean shouldLog(int logLevel) {
        return logLevel >= this.mLoggingLevel;
    }

    public void log(int logLevel, String tag, String msg) {
        this.logWithThrowable(logLevel, tag, msg, null);
    }

    public void logWithThrowable(int logLevel, String tag, String msg, Throwable t) {
        if (this.isLoggingEnabled() && this.shouldLog(logLevel)) {
            switch (logLevel) {
                case 2: {
                    Log.v((String)tag, (String)msg, (Throwable)t);
                    break;
                }
                case 5: {
                    Log.w((String)tag, (String)msg, (Throwable)t);
                    break;
                }
                case 6: {
                    Log.e((String)tag, (String)msg, (Throwable)t);
                    break;
                }
                case 3: {
                    Log.d((String)tag, (String)msg, (Throwable)t);
                    break;
                }
                case 8: {
                    if (Integer.valueOf(Build.VERSION.SDK) > 8) {
                        this.checkedWtf(tag, msg, t);
                        break;
                    }
                    Log.e((String)tag, (String)msg, (Throwable)t);
                    break;
                }
                case 4: {
                    Log.i((String)tag, (String)msg, (Throwable)t);
                }
            }
        }
    }

    @TargetApi(value=8)
    private void checkedWtf(String tag, String msg, Throwable t) {
        Log.wtf((String)tag, (String)msg, (Throwable)t);
    }

    public void v(String tag, String msg) {
        this.log(2, tag, msg);
    }

    public void v(String tag, String msg, Throwable t) {
        this.logWithThrowable(2, tag, msg, t);
    }

    public void d(String tag, String msg) {
        this.log(2, tag, msg);
    }

    public void d(String tag, String msg, Throwable t) {
        this.logWithThrowable(3, tag, msg, t);
    }

    public void i(String tag, String msg) {
        this.log(4, tag, msg);
    }

    public void i(String tag, String msg, Throwable t) {
        this.logWithThrowable(4, tag, msg, t);
    }

    public void w(String tag, String msg) {
        this.log(5, tag, msg);
    }

    public void w(String tag, String msg, Throwable t) {
        this.logWithThrowable(5, tag, msg, t);
    }

    public void e(String tag, String msg) {
        this.log(6, tag, msg);
    }

    public void e(String tag, String msg, Throwable t) {
        this.logWithThrowable(6, tag, msg, t);
    }

    public void wtf(String tag, String msg) {
        this.log(8, tag, msg);
    }

    public void wtf(String tag, String msg, Throwable t) {
        this.logWithThrowable(8, tag, msg, t);
    }
}


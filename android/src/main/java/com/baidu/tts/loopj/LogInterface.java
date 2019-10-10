/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.loopj;

public interface LogInterface {
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int WTF = 8;

    public boolean isLoggingEnabled();

    public void setLoggingEnabled(boolean var1);

    public int getLoggingLevel();

    public void setLoggingLevel(int var1);

    public boolean shouldLog(int var1);

    public void v(String var1, String var2);

    public void v(String var1, String var2, Throwable var3);

    public void d(String var1, String var2);

    public void d(String var1, String var2, Throwable var3);

    public void i(String var1, String var2);

    public void i(String var1, String var2, Throwable var3);

    public void w(String var1, String var2);

    public void w(String var1, String var2, Throwable var3);

    public void e(String var1, String var2);

    public void e(String var1, String var2, Throwable var3);

    public void wtf(String var1, String var2);

    public void wtf(String var1, String var2, Throwable var3);
}


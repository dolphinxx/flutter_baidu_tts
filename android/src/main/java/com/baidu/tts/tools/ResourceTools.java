/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.text.TextUtils
 */
package com.baidu.tts.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.baidu.tts.f.D;
import com.baidu.tts.f.N;
import com.baidu.tts.m.I;
import com.baidu.tts.tools.FileTools;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class ResourceTools {
    public static final int TEXT_LENGTH_LIMIT = 1024;

    public static String getAppFilesPath(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String string = packageInfo.applicationInfo.dataDir + "/files/";
            return string;
        }
        catch (Exception exception) {
            String string = context.getFilesDir().getAbsolutePath() + "/";
            return string;
        }
    }

    public static String getModelFileAbsName(Context context, String name) {
        String string = ResourceTools.getAppFilesPath(context) + "modelDir/";
        String string2 = FileTools.jointPathAndName(string, name);
        return string2;
    }

    public static String getByteMapAbsName(Context context, String datName) {
        String string = datName + ".bm";
        return ResourceTools.getModelFileAbsName(context, string);
    }

    public static String getAppFilesDirPath(Context context) {
        return context.getFilesDir().getAbsolutePath() + "/";
    }

    @SuppressLint(value={"SdCardPath"})
    public static String getSdcardFilesDirPath(Context context) {
        return "/sdcard/tts/";
    }

    public static String getDefaultResourcePath(Context context, String fileName) {
        String string = ResourceTools.getSdcardFilesDirPath(context) + fileName;
        return string;
    }

    public static I format(String oldFormat, String newFormat, I textParams) {
        try {
            byte[] arrby = textParams.c().getBytes(oldFormat);
            String string = new String(arrby, newFormat);
            textParams.b(string);
            textParams.c(newFormat);
            return textParams;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return null;
        }
    }

    public static N isTextValid(String text) {
        if (TextUtils.isEmpty((CharSequence)text)) {
            return N.P;
        }
        try {
            int n2 = text.getBytes(D.d.a()).length;
            if (n2 > 1024) {
                return N.Q;
            }
            return null;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return N.R;
        }
    }

    public static byte[] stringToByteArrayAddNull(String string) {
        if (string == null) {
            string = "";
        }
        byte[] arrby = string.getBytes();
        byte[] arrby2 = new byte[]{0};
        byte[] arrby3 = new byte[arrby.length + 1];
        System.arraycopy(arrby, 0, arrby3, 0, arrby.length);
        System.arraycopy(arrby2, 0, arrby3, arrby.length, arrby2.length);
        return arrby3;
    }
}


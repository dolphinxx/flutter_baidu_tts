/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.baidu.tts.tools;

import android.annotation.SuppressLint;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressLint(value={"DefaultLocale"})
public class MD5Util {
    public static String toMd5(byte[] paramArrayOfByte, boolean paramBoolean) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(paramArrayOfByte);
            return MD5Util.toHexString(messageDigest.digest(), "", paramBoolean);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    public static String toHexString(byte[] paramArrayOfByte, String paramString, boolean paramBoolean) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte by : paramArrayOfByte) {
            String string = Integer.toHexString(255 & by);
            if (paramBoolean) {
                string = string.toUpperCase();
            }
            if (string.length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(string).append(paramString);
        }
        return stringBuilder.toString();
    }
}


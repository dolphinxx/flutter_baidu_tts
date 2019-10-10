/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.baidu.tts.tools;

import android.annotation.SuppressLint;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class AESUtil {
    @SuppressLint(value={"TrulyRandom"})
    public static byte[] encrypt(String paramString1, String paramString2, byte[] paramArrayOfByte) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(paramString2.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(paramString1.getBytes());
        cipher.init(1, (Key)secretKeySpec, ivParameterSpec);
        byte[] arrby = cipher.doFinal(paramArrayOfByte);
        return arrby;
    }

    public static byte[] decrypt(String paramString1, String paramString2, byte[] paramArrayOfByte) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(paramString2.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(paramString1.getBytes());
        cipher.init(2, (Key)secretKeySpec, ivParameterSpec);
        byte[] arrby = cipher.doFinal(paramArrayOfByte);
        return arrby;
    }
}


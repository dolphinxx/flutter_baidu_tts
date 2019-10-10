/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 */
package com.baidu.tts.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class CommonUtility {
    public static String generateSerialNumber() {
        String string = UUID.randomUUID().toString();
        return string;
    }

    public static byte[] shortArrayToByteArray(short[] shortArray) {
        int n2 = shortArray.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(shortArray.length * 2);
        byteBuffer.clear();
        byteBuffer.order(ByteOrder.nativeOrder());
        for (int i2 = 0; i2 < n2; ++i2) {
            byteBuffer.putShort(i2 * 2, shortArray[i2]);
        }
        return byteBuffer.array();
    }

    public static int indexOf(byte[] data, byte[] pattern, int start) {
        try {
            int[] arrn = CommonUtility.a(pattern);
            int n2 = 0;
            if (data.length == 0) {
                return -1;
            }
            if (start >= data.length) {
                return -1;
            }
            for (int i2 = start; i2 < data.length; ++i2) {
                while (n2 > 0 && pattern[n2] != data[i2]) {
                    n2 = arrn[n2 - 1];
                }
                if (pattern[n2] == data[i2]) {
                    ++n2;
                }
                if (n2 != pattern.length) continue;
                return i2 - pattern.length + 1;
            }
            return -1;
        }
        catch (Exception exception) {
            return -1;
        }
    }

    private static int[] a(byte[] arrby) {
        int[] arrn = new int[arrby.length];
        int n2 = 0;
        for (int i2 = 1; i2 < arrby.length; ++i2) {
            while (n2 > 0 && arrby[n2] != arrby[i2]) {
                n2 = arrn[n2 - 1];
            }
            if (arrby[n2] == arrby[i2]) {
                // empty if block
            }
            arrn[i2] = ++n2;
        }
        return arrn;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo();
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkInfo = CommonUtility.getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo networkInfo = CommonUtility.getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == 1;
    }

    public static byte[] copyBytesOfRange(byte[] source, int start, int end) {
        if (start > end || start < 0 || end < 0 || end > source.length) {
            return null;
        }
        byte[] arrby = new byte[end - start];
        for (int i2 = start; i2 < end; ++i2) {
            arrby[i2 - start] = source[i2];
        }
        return arrby;
    }

    public static byte[] addCAFHeaderForPCMData(byte[] pcmData) {
        if (pcmData == null || pcmData.length == 0) {
            return null;
        }
        long l2 = pcmData.length;
        long l3 = l2 + 44L;
        long l4 = 16000L;
        int n2 = 1;
        long l5 = 16L * l3 * (long)n2 / 8L;
        byte[] arrby = new byte[]{82, 73, 70, 70, (byte)(l3 & 255L), (byte)(l3 >> 8 & 255L), (byte)(l3 >> 16 & 255L), (byte)(l3 >> 24 & 255L), 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, (byte)n2, 0, (byte)(l4 & 255L), (byte)(l4 >> 8 & 255L), (byte)(l4 >> 16 & 255L), (byte)(l4 >> 24 & 255L), (byte)(l5 & 255L), (byte)(l5 >> 8 & 255L), (byte)(l5 >> 16 & 255L), (byte)(l5 >> 24 & 255L), 2, 0, 16, 0, 100, 97, 116, 97, (byte)(l2 & 255L), (byte)(l2 >> 8 & 255L), (byte)(l2 >> 16 & 255L), (byte)(l2 >> 24 & 255L)};
        byte[] arrby2 = new byte[arrby.length + pcmData.length];
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        System.arraycopy(pcmData, 0, arrby2, arrby.length, pcmData.length);
        return arrby2;
    }
}


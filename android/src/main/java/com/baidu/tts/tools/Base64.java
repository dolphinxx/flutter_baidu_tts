/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.tools;

import java.io.UnsupportedEncodingException;

public final class Base64 {
    private static final byte[] a = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

    public static byte[] decode(byte[] paramArrayOfByte) {
        return Base64.decode(paramArrayOfByte, paramArrayOfByte.length);
    }

    public static byte[] decode(byte[] paramArrayOfByte, int paramInt) {
        byte by;
        int n2 = paramInt / 4 * 3;
        if (n2 == 0) {
            return new byte[0];
        }
        byte[] arrby = new byte[n2];
        int n3 = 0;
        do {
            if ((by = paramArrayOfByte[paramInt - 1]) != 10 && by != 13 && by != 32 && by != 9) {
                if (by != 61) break;
                ++n3;
            }
            --paramInt;
        } while (true);
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        for (int i2 = 0; i2 < paramInt; ++i2) {
            by = paramArrayOfByte[i2];
            if (by == 10 || by == 13 || by == 32 || by == 9) continue;
            if (by >= 65 && by <= 90) {
                n6 = by - 65;
            } else if (by >= 97 && by <= 122) {
                n6 = by - 71;
            } else if (by >= 48 && by <= 57) {
                n6 = by + 4;
            } else if (by == 43) {
                n6 = 62;
            } else if (by == 47) {
                n6 = 63;
            } else {
                return null;
            }
            n7 = n7 << 6 | (byte)n6;
            if (n5 % 4 == 3) {
                arrby[n4++] = (byte)((n7 & 16711680) >> 16);
                arrby[n4++] = (byte)((n7 & 65280) >> 8);
                arrby[n4++] = (byte)(n7 & 255);
            }
            ++n5;
        }
        if (n3 > 0) {
            arrby[n4++] = (byte)(((n7 <<= 6 * n3) & 16711680) >> 16);
            if (n3 == 1) {
                arrby[n4++] = (byte)((n7 & 65280) >> 8);
            }
        }
        byte[] arrby2 = new byte[n4];
        System.arraycopy(arrby, 0, arrby2, 0, n4);
        return arrby2;
    }

    public static String encode(byte[] paramArrayOfByte, String paramString) throws UnsupportedEncodingException {
        int n2 = paramArrayOfByte.length * 4 / 3;
        n2 += n2 / 76 + 3;
        byte[] arrby = new byte[n2];
        int n3 = 0;
        int n4 = 0;
        int n5 = paramArrayOfByte.length - paramArrayOfByte.length % 3;
        for (int i2 = 0; i2 < n5; i2 += 3) {
            arrby[n3++] = a[(paramArrayOfByte[i2] & 255) >> 2];
            arrby[n3++] = a[(paramArrayOfByte[i2] & 3) << 4 | (paramArrayOfByte[i2 + 1] & 255) >> 4];
            arrby[n3++] = a[(paramArrayOfByte[i2 + 1] & 15) << 2 | (paramArrayOfByte[i2 + 2] & 255) >> 6];
            arrby[n3++] = a[paramArrayOfByte[i2 + 2] & 63];
            if ((n3 - n4) % 76 != 0 || n3 == 0) continue;
            arrby[n3++] = 10;
            ++n4;
        }
        switch (paramArrayOfByte.length % 3) {
            case 1: {
                arrby[n3++] = a[(paramArrayOfByte[n5] & 255) >> 2];
                arrby[n3++] = a[(paramArrayOfByte[n5] & 3) << 4];
                arrby[n3++] = 61;
                arrby[n3++] = 61;
                break;
            }
            case 2: {
                arrby[n3++] = a[(paramArrayOfByte[n5] & 255) >> 2];
                arrby[n3++] = a[(paramArrayOfByte[n5] & 3) << 4 | (paramArrayOfByte[n5 + 1] & 255) >> 4];
                arrby[n3++] = a[(paramArrayOfByte[n5 + 1] & 15) << 2];
                arrby[n3++] = 61;
            }
        }
        return new String(arrby, 0, n3, paramString);
    }
}


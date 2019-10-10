/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.tools;

import java.util.Iterator;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class StringTool {
    public static String addDivider(List<String> data, String divider) {
        String string;
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> iterator = data.iterator();
        if (iterator.hasNext()) {
            string = iterator.next();
            stringBuilder.append(string);
        }
        while (iterator.hasNext()) {
            string = iterator.next();
            stringBuilder.append(divider);
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static String addDivider(int[] data, String divider) {
        if (data != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(data[0]);
            for (int i2 = 1; i2 < data.length; ++i2) {
                stringBuilder.append(divider);
                stringBuilder.append(data[i2]);
            }
            return stringBuilder.toString();
        }
        return null;
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        return (str = str.trim()).length() <= 0;
    }

    public static boolean isAllNumber(String str) {
        if (str != null) {
            String string = "^[0-9]{1,20}$";
            return str.matches(string);
        }
        return false;
    }

    public static boolean isEqual(String left, String right) {
        if (left == null) {
            return right == null;
        }
        return left.equals(right);
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 */
package com.baidu.tts.tools;

import java.util.Collection;
import java.util.Set;
import org.json.JSONArray;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JsonTool {
    public static JSONArray fromSetToJson(Set<String> set) {
        if (set != null) {
            return new JSONArray(set);
        }
        return null;
    }

    public static String[] getStringarray(JSONArray ja) {
        if (ja != null) {
            int n2 = ja.length();
            String[] arrstring = new String[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                String string;
                arrstring[i2] = string = ja.optString(i2);
            }
            return arrstring;
        }
        return null;
    }
}


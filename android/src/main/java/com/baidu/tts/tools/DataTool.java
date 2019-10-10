/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DataTool {
    public static Set<String> fromArrayToSet(String[] array) {
        if (array != null) {
            HashSet<String> hashSet = new HashSet<String>();
            int n2 = array.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                hashSet.add(array[i2]);
            }
            return hashSet;
        }
        return null;
    }

    public static String[] fromSetToArray(Set<String> set) {
        if (set != null) {
            int n2 = set.size();
            String[] arrstring = new String[n2];
            set.toArray(arrstring);
            return arrstring;
        }
        return null;
    }

    public static String[] connect(String[] first, String[] ... rest) {
        if (first == null) {
            first = new String[]{};
        }
        int n2 = first.length;
        for (String[] arrstring : rest) {
            try {
                n2 += arrstring.length;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        String[] arrstring = Arrays.copyOf(first, n2);
        int n3 = first.length;
        for (String[] arrstring2 : rest) {
            try {
                System.arraycopy(arrstring2, 0, arrstring, n3, arrstring2.length);
                n3 += arrstring2.length;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return arrstring;
    }

    public static Set<String> copy(Set<String> ori) {
        if (ori == null) {
            return null;
        }
        HashSet<String> hashSet = new HashSet<String>();
        for (String string : ori) {
            hashSet.add(string);
        }
        return hashSet;
    }

    public static <T extends Set<?>> boolean isSetEmpty(T t) {
        return t == null || t.isEmpty();
    }

    public static <T extends Map<?, ?>> boolean isMapEmpty(T t) {
        return t == null || t.isEmpty();
    }

    public static <T extends List<?>> boolean isListEmpty(T t) {
        return t == null || t.isEmpty();
    }

    public static Map<String, Integer> getSuitItem(Map<String, Integer> map, boolean isEqual, int value) {
        if (DataTool.isMapEmpty(map)) {
            return null;
        }
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        Set<String> set = map.keySet();
        for (String string : set) {
            int n2 = map.get(string);
            if (isEqual) {
                if (value != n2) continue;
                hashMap.put(string, n2);
                continue;
            }
            if (value == n2) continue;
            hashMap.put(string, n2);
        }
        return hashMap;
    }

    public static Map<String, Map<String, String>> getSuitItem(Map<String, Map<String, String>> out, String inKey, boolean isEqual, String inValue) {
        if (DataTool.isMapEmpty(out)) {
            return null;
        }
        HashMap<String, Map<String, String>> hashMap = new HashMap<String, Map<String, String>>();
        Set<String> set = out.keySet();
        for (String string : set) {
            Map<String, String> map = out.get(string);
            String string2 = map.get(inKey);
            if (isEqual) {
                if (!inValue.equals(string2)) continue;
                hashMap.put(string, map);
                continue;
            }
            if (inValue.equals(string2)) continue;
            hashMap.put(string, map);
        }
        return hashMap;
    }

    public static void putMapItem(Map<String, Map<String, String>> out, String outKey, Map<String, String> item) {
        Map<String, String> map = out.get(outKey);
        if (map == null) {
            map = item;
            out.put(outKey, map);
        } else {
            map.putAll(item);
        }
    }

    public static Map<String, String> putIfAbsent(Map<String, Map<String, String>> out, String key) {
        Map<String, String> map = out.get(key);
        if (map == null) {
            map = new HashMap<String, String>();
            out.put(key, map);
        }
        return map;
    }

    public static void putMapValue(Map<String, Map<String, String>> out, String outKey, String inKey, String value) {
        Map<String, String> map = DataTool.putIfAbsent(out, outKey);
        map.put(inKey, value);
    }

    public static String getMapInnerValue(Map<String, Map<String, String>> out, String outKey, String inKey) {
        if (out != null) {
            Map<String, String> map = out.get(outKey);
            if (map != null) {
                return map.get(inKey);
            }
            return null;
        }
        return null;
    }

    public static String getMapValue(Map<String, String> map, String key) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        return map.get(key);
    }

    public static boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.baidu.tts.tools;

import android.text.TextUtils;
import com.baidu.tts.tools.ReflectTool;
import com.baidu.tts.tools.StringTool;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class SqlTool {
    public static String sqlDropTable(String tableName) {
        return "drop table if exists " + tableName;
    }

    public static String sqlCreateTable(String tableName, Object[] fields) {
        if (tableName != null && fields != null) {
            Object object = fields[0];
            Class<?> class_ = object.getClass();
            try {
                Method method = ReflectTool.getSupportedMethod(class_, "getColumnName", null);
                Method method2 = ReflectTool.getSupportedMethod(class_, "getDataType", null);
                StringBuilder stringBuilder = new StringBuilder("create Table " + tableName);
                String string = SqlTool.a(method, method2, object);
                if (string != null) {
                    stringBuilder.append(" (" + string);
                    int n2 = fields.length;
                    for (int i2 = 1; i2 < n2; ++i2) {
                        stringBuilder.append(",");
                        object = fields[i2];
                        string = SqlTool.a(method, method2, object);
                        stringBuilder.append(string);
                    }
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private static String a(Method method, Method method2, Object object) {
        String string;
        String string2 = SqlTool.a(method, object);
        if (string2 != null && (string = SqlTool.a(method2, object)) != null) {
            return string2 + " " + string;
        }
        return null;
    }

    private static String a(Method method, Object object) {
        String string = null;
        try {
            string = (String)method.invoke(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
        }
        catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        }
        return string;
    }

    public static String buildConditions(String connector, String ... conditions) {
        String string;
        Iterator iterator;
        if (TextUtils.isEmpty((CharSequence)connector) || conditions == null || conditions.length == 0) {
            return null;
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (int i2 = 0; i2 < conditions.length; ++i2) {
            String s = conditions[i2];
            if (StringTool.isEmpty(s)) continue;
            arrayList.add(s);
        }
        StringBuilder stringBuilder = new StringBuilder();
        iterator = arrayList.iterator();
        if (iterator.hasNext()) {
            string = (String)iterator.next();
            stringBuilder.append(string);
        }
        while (iterator.hasNext()) {
            string = (String)iterator.next();
            stringBuilder.append(" " + connector + " ");
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static String buildInCondition(String field, String[] data) {
        if (data == null || data.length == 0 || TextUtils.isEmpty((CharSequence)field)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(field);
        stringBuilder.append(" in (");
        stringBuilder.append(SqlTool.addPlaceholders(data.length));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String addPlaceholders(int length) {
        if (length < 1) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?");
        for (int i2 = 1; i2 < length; ++i2) {
            stringBuilder.append(",?");
        }
        return stringBuilder.toString();
    }

    public static String[] getSQLformat(String version, String[] domains, String[] languages, String[] qualities) {
        int n2 = 1 + domains.length + languages.length + qualities.length;
        String[] arrstring = new String[n2];
        arrstring[0] = version;
        System.arraycopy(domains, 0, arrstring, 1, domains.length);
        System.arraycopy(languages, 0, arrstring, 1 + domains.length, languages.length);
        System.arraycopy(qualities, 0, arrstring, 1 + domains.length + languages.length, qualities.length);
        return arrstring;
    }
}


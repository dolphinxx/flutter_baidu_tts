/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.tools;

import java.lang.reflect.Method;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ReflectTool {
    public static Method getSupportedMethod(Class<?> cls, String name, Class<?>[] paramTypes) throws NoSuchMethodException {
        if (cls == null) {
            throw new NoSuchMethodException();
        }
        try {
            return cls.getDeclaredMethod(name, paramTypes);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return ReflectTool.getSupportedMethod(cls.getSuperclass(), name, paramTypes);
        }
    }
}


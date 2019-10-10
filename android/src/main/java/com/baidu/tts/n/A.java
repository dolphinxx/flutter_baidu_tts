/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.n;

import com.baidu.tts.n.B;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class A<T>
implements B/*<T>*/ {
    public T E() {
        try {
            return (T)super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            cloneNotSupportedException.printStackTrace();
            return null;
        }
    }
}


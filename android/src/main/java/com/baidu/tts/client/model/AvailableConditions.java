/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.client.model;

import com.baidu.tts.tools.StringTool;
import java.util.HashSet;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AvailableConditions {
    private Set<String> a;
    private Set<String> b;

    public Set<String> getGenders() {
        return this.a;
    }

    public void setGenders(Set<String> genders) {
        this.a = genders;
    }

    public Set<String> getSpeakers() {
        return this.b;
    }

    public void setSpeakers(Set<String> speakers) {
        this.b = speakers;
    }

    public void appendGender(String gender) {
        if (StringTool.isEmpty(gender)) {
            return;
        }
        if (this.a == null) {
            this.a = new HashSet<String>();
        }
        this.a.add(gender);
    }

    public void appendSpeaker(String speaker) {
        if (StringTool.isEmpty(speaker)) {
            return;
        }
        if (this.b == null) {
            this.b = new HashSet<String>();
        }
        this.b.add(speaker);
    }
}


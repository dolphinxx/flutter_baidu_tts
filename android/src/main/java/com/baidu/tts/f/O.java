/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

import java.net.InetAddress;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum O {
    a("https://tsn.baidu.com/text2audio"){

        public String a(String string) {
            if (string == null) {
                return null;
            }
            String string2 = "tsn.baidu.com";
            String string3 = /*1.*/c(string2);
            if (string3 != null) {
                return string + "://" + string3 + "/text2audio";
            }
            return null;
        }

        public String b(String string) {
            if (string == null) {
                return null;
            }
            return string + "://tsn.baidu.com/text2audio";
        }
    }
    ,
    b("https://tts.baidu.com/bos/story.php?"){

        public String a(String string) {
            return null;
        }

        public String b(String string) {
            return null;
        }
    }
    ,
    c("https://upl.baidu.com/ttsdlstats.php"){

        public String a(String string) {
            return null;
        }

        public String b(String string) {
            return null;
        }
    };
    
    private final String d;

    private O(String string2) {
        this.d = string2;
    }

    public String a() {
        return this.d;
    }

    public abstract String a(String var1);

    public abstract String b(String var1);

    public static String c(String string) {
        try {
            InetAddress inetAddress = InetAddress.getByName(string);
            return inetAddress.getHostAddress();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}


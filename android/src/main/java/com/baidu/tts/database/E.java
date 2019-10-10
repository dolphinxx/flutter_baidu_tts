/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.database;

import com.baidu.tts.tools.SqlTool;

public class E {
    public static String a() {
        return "CREATE TABLE StatisticsInfo(id INTEGER PRIMARY KEY AUTOINCREMENT,uuid varchar(256),startInfo varchar(256),endInfo varchar(256))";
    }

    public static String b() {
        return SqlTool.sqlDropTable("StatisticsInfo");
    }
}


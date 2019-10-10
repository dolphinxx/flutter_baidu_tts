/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.database;

import com.baidu.tts.tools.SqlTool;

public class FsFileInfoTable {
    public static String a() {
        return SqlTool.sqlCreateTable("fsFileInfo", (Object[])Field.values());
    }

    public static String b() {
        return SqlTool.sqlDropTable("fsFileInfo");
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Field {
        a("absPath", "varchar primary key"),
        b("state", "integer");
        
        private final String c;
        private final String d;

        private Field(String columnName, String dataType) {
            this.c = columnName;
            this.d = dataType;
        }

        public String getColumnName() {
            return this.c;
        }

        public String getDataType() {
            return this.d;
        }
    }

}


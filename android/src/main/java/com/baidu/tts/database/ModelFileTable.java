/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteStatement
 */
package com.baidu.tts.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.baidu.tts.client.model.ModelFileBags;
import com.baidu.tts.client.model.ModelFileInfo;
import com.baidu.tts.database.F;
import com.baidu.tts.tools.SqlTool;
import java.util.List;

public class ModelFileTable {
    public static String a() {
        return SqlTool.sqlCreateTable("modelFile", (Object[])Field.values());
    }

    public static String b() {
        return SqlTool.sqlDropTable("modelFile");
    }

    public static int a(SQLiteDatabase sQLiteDatabase, String string) {
        return sQLiteDatabase.delete("modelFile", "id=?", new String[]{string});
    }

    public static void a(SQLiteDatabase sQLiteDatabase, final ModelFileBags modelFileBags) {
        F f2 = new F(sQLiteDatabase, new F.a(){

            public boolean a(SQLiteDatabase sQLiteDatabase) {
                try {
                    String string = "insert into modelFile (id, length, md5, name, absPath) values (?, ?, ?, ?, ?)";
                    SQLiteStatement sQLiteStatement = sQLiteDatabase.compileStatement(string);
                    List<ModelFileInfo> list = modelFileBags.getModelFileInfos();
                    for (ModelFileInfo modelFileInfo : list) {
                        String string2 = modelFileInfo.getServerid();
                        String string3 = modelFileInfo.getLength();
                        String string4 = modelFileInfo.getMd5();
                        String string5 = modelFileInfo.getName();
                        String string6 = modelFileInfo.getAbsPath();
                        sQLiteStatement.bindString(1, string2);
                        sQLiteStatement.bindString(2, string3);
                        sQLiteStatement.bindString(3, string4);
                        sQLiteStatement.bindString(4, string5);
                        sQLiteStatement.bindString(5, string6);
                        sQLiteStatement.executeInsert();
                    }
                    return true;
                }
                catch (Exception exception) {
                    return false;
                }
            }
        });
        f2.a();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Field {
        a("id", "integer primary key"),
        b("length", "bigint"),
        c("md5", "varchar(32)"),
        d("name", "varchar(256) not null default unnamed"),
        e("absPath", "varchar");
        
        private final String f;
        private final String g;

        private Field(String columnName, String dataType) {
            this.f = columnName;
            this.g = dataType;
        }

        public String getColumnName() {
            return this.f;
        }

        public String getDataType() {
            return this.g;
        }
    }

}


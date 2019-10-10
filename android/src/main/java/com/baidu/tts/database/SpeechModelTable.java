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
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.client.model.ModelInfo;
import com.baidu.tts.database.F;
import com.baidu.tts.tools.SqlTool;
import java.util.List;

public class SpeechModelTable {
    public static String a() {
        return SqlTool.sqlCreateTable("speechModel", (Object[])Field.values());
    }

    public static String b() {
        return SqlTool.sqlDropTable("speechModel");
    }

    public static int a(SQLiteDatabase sQLiteDatabase, String string) {
        return sQLiteDatabase.delete("speechModel", "id=?", new String[]{string});
    }

    public static void a(SQLiteDatabase sQLiteDatabase, final ModelBags modelBags) {
        F f2 = new F(sQLiteDatabase, new F.a(){

            public boolean a(SQLiteDatabase sQLiteDatabase) {
                try {
                    String string = "insert into speechModel (name, version_min, version_max, language, gender, speaker, domain, quality, text_data_id, speech_data_id, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    SQLiteStatement sQLiteStatement = sQLiteDatabase.compileStatement(string);
                    List<ModelInfo> list = modelBags.getModelInfos();
                    for (ModelInfo modelInfo : list) {
                        String string2 = modelInfo.getName();
                        String string3 = modelInfo.getVersionMin();
                        String string4 = modelInfo.getVersionMax();
                        String string5 = modelInfo.getLanguage();
                        String string6 = modelInfo.getGender();
                        String string7 = modelInfo.getSpeaker();
                        String string8 = modelInfo.getDomain();
                        String string9 = modelInfo.getQuality();
                        String string10 = modelInfo.getTextDataId();
                        String string11 = modelInfo.getSpeechDataId();
                        String string12 = modelInfo.getServerId();
                        sQLiteStatement.bindString(1, string2);
                        sQLiteStatement.bindString(2, string3);
                        sQLiteStatement.bindString(3, string4);
                        sQLiteStatement.bindString(4, string5);
                        sQLiteStatement.bindString(5, string6);
                        sQLiteStatement.bindString(6, string7);
                        sQLiteStatement.bindString(7, string8);
                        sQLiteStatement.bindString(8, string9);
                        sQLiteStatement.bindString(9, string10);
                        sQLiteStatement.bindString(10, string11);
                        sQLiteStatement.bindString(11, string12);
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
        b("text_data_id", "integer"),
        c("speech_data_id", "integer"),
        d("name", "varchar(256) not null default unnamed"),
        e("version_min", "integer"),
        f("version_max", "integer"),
        g("language", "varchar(20)"),
        h("gender", "varchar(20)"),
        i("speaker", "varchar(256)"),
        j("domain", "varchar(50)"),
        k("quality", "varchar(50)");
        
        private final String l;
        private final String m;

        private Field(String columnName, String dataType) {
            this.l = columnName;
            this.m = dataType;
        }

        public String getColumnName() {
            return this.l;
        }

        public String getDataType() {
            return this.m;
        }
    }

}


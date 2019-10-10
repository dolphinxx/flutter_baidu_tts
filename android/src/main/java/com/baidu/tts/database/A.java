/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 */
package com.baidu.tts.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.baidu.tts.client.model.Conditions;
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.client.model.ModelFileBags;
import com.baidu.tts.database.ModelFileTable;
import com.baidu.tts.database.SpeechModelTable;
import com.baidu.tts.database.B;
import com.baidu.tts.f.G;
import com.baidu.tts.tools.DataTool;
import com.baidu.tts.tools.SqlTool;
import com.baidu.tts.tools.StringTool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class A {
    private com.baidu.tts.l.A a;
    private B b;
    private ReadWriteLock c = new ReentrantReadWriteLock();
    private Lock d = this.c.writeLock();
    private Lock e = this.c.readLock();

    public A(com.baidu.tts.l.A a2) {
        this.a = a2;
        this.b = new B(this.a.d());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int a(String string) {
        this.d.lock();
        try {
            int n2;
            SQLiteDatabase sQLiteDatabase = this.a();
            try {
                int n3;
                n2 = n3 = SpeechModelTable.a(sQLiteDatabase, string);
            }
            catch (Exception exception) {
                int n4;
                try {
                    n4 = -1;
                }
                catch (Throwable throwable) {
                    sQLiteDatabase.close();
                    throw throwable;
                }
                sQLiteDatabase.close();
                this.d.unlock();
                return n4;
            }
            sQLiteDatabase.close();
            return n2;
        }
        finally {
            this.d.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int b(String string) {
        this.d.lock();
        try {
            int n2;
            SQLiteDatabase sQLiteDatabase = this.a();
            try {
                int n3;
                n2 = n3 = ModelFileTable.a(sQLiteDatabase, string);
            }
            catch (Exception exception) {
                int n4;
                try {
                    n4 = -1;
                }
                catch (Throwable throwable) {
                    sQLiteDatabase.close();
                    throw throwable;
                }
                sQLiteDatabase.close();
                this.d.unlock();
                return n4;
            }
            sQLiteDatabase.close();
            return n2;
        }
        finally {
            this.d.unlock();
        }
    }

    public String a(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder("select b.absPath from speechModel a left join modelFile b on a.");
        stringBuilder.append(string);
        stringBuilder.append("=b.id where a.id=?");
        String string3 = stringBuilder.toString();
        String[] arrstring = new String[]{string2};
        Map<String, String> map = this.a(string3, arrstring);
        String string4 = null;
        if (map != null) {
            string4 = map.get(G.h.b());
        }
        return string4;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(String string, int n2) {
        this.d.lock();
        try {
            String string2 = "replace into fsFileInfo (absPath,state) values (?, ?)";
            String string3 = String.valueOf(n2);
            Object[] arrobject = new String[]{string, string3};
            SQLiteDatabase sQLiteDatabase = this.a();
            try {
                sQLiteDatabase.execSQL(string2, arrobject);
            }
            finally {
                sQLiteDatabase.close();
            }
        }
        finally {
            this.d.unlock();
        }
    }

    public Map<String, String> c(String string) {
        String string2 = "select * from fsFileInfo where absPath=?";
        String[] arrstring = new String[]{string};
        Map<String, String> map = this.a(string2, arrstring);
        return map;
    }

    public Map<String, String> d(String string) {
        String string2 = "select * from modelFile where id=?";
        String[] arrstring = new String[]{string};
        Map<String, String> map = this.a(string2, arrstring);
        return map;
    }

    public Map<String, String> e(String string) {
        String string2 = "select * from speechModel where id=?";
        String[] arrstring = new String[]{string};
        Map<String, String> map = this.a(string2, arrstring);
        return map;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(ModelFileBags modelFileBags) {
        this.d.lock();
        try {
            SQLiteDatabase sQLiteDatabase = this.a();
            ModelFileTable.a(sQLiteDatabase, modelFileBags);
        }
        finally {
            this.d.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(ModelBags modelBags) {
        this.d.lock();
        try {
            SQLiteDatabase sQLiteDatabase = this.a();
            SpeechModelTable.a(sQLiteDatabase, modelBags);
        }
        finally {
            this.d.unlock();
        }
    }

    public List<Map<String, String>> a(Conditions conditions) {
        String string = conditions.getVersion();
        String string2 = null;
        String[] arrstring = null;
        if (!StringTool.isEmpty(string)) {
            string2 = "version_min <= ? and version_max >= ?";
            arrstring = new String[]{string, string};
        }
        String[] arrstring2 = conditions.getDomainArray();
        String[] arrstring3 = conditions.getLanguageArray();
        String[] arrstring4 = conditions.getQualityArray();
        String[] arrstring5 = conditions.getGenderArray();
        String[] arrstring6 = conditions.getSpeakerArray();
        String[] arrstring7 = conditions.getModelIdsArray();
        String string3 = SqlTool.buildInCondition("domain", arrstring2);
        String string4 = SqlTool.buildInCondition("language", arrstring3);
        String string5 = SqlTool.buildInCondition("quality", arrstring4);
        String string6 = SqlTool.buildInCondition("gender", arrstring5);
        String string7 = SqlTool.buildInCondition("speaker", arrstring6);
        String string8 = SqlTool.buildInCondition("id", arrstring7);
        String string9 = SqlTool.buildConditions("and", string2, string3, string4, string5, string6, string7, string8);
        if (StringTool.isEmpty(string9)) {
            return null;
        }
        String string10 = "select * from speechModel where " + string9;
        String[] arrstring8 = DataTool.connect(arrstring, arrstring2, arrstring3, arrstring4, arrstring5, arrstring6, arrstring7);
        List<Map<String, String>> list = this.b(string10, arrstring8);
        return list;
    }

    public List<Map<String, String>> a(Set<String> set) {
        if (set == null || set.isEmpty()) {
            return null;
        }
        String[] arrstring = DataTool.fromSetToArray(set);
        String string = SqlTool.buildInCondition("id", arrstring);
        String string2 = "select * from modelFile where " + string;
        List<Map<String, String>> list = this.b(string2, arrstring);
        return list;
    }

    private SQLiteDatabase a() {
        return this.b.getWritableDatabase();
    }

    private SQLiteDatabase b() {
        return this.b.getReadableDatabase();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, String> a(String string, String[] arrstring) {
        this.e.lock();
        try {
            Cursor cursor;
            HashMap<String, String> hashMap = null;
            SQLiteDatabase sQLiteDatabase = this.b();
            try {
                cursor = sQLiteDatabase.rawQuery(string, arrstring);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        hashMap = new HashMap<String, String>();
                        String[] arrstring2 = cursor.getColumnNames();
                        int n2 = arrstring2.length;
                        for (int i2 = 0; i2 < n2; ++i2) {
                            hashMap.put(arrstring2[i2], cursor.getString(cursor.getColumnIndex(arrstring2[i2])));
                        }
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            finally {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            }
            return hashMap;
        }
        finally {
            this.e.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Map<String, String>> b(String string, String[] arrstring) {
        this.e.lock();
        try {
            Cursor cursor;
            List list = null;
            SQLiteDatabase sQLiteDatabase = null;
            try {
                list = new ArrayList();
                sQLiteDatabase = this.b();
                cursor = sQLiteDatabase.rawQuery(string, arrstring);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        String[] arrstring2 = cursor.getColumnNames();
                        do {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            int n2 = arrstring2.length;
                            for (int i2 = 0; i2 < n2; ++i2) {
                                hashMap.put(arrstring2[i2], cursor.getString(cursor.getColumnIndex(arrstring2[i2])));
                            }
                            list.add(hashMap);
                        } while (cursor.moveToNext());
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            finally {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            }
            return list;
        }
        finally {
            this.e.unlock();
        }
    }
}


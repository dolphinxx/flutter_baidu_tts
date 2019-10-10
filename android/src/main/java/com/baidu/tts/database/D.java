/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.database.C;
import com.baidu.tts.l.A;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class D {
    private A a;
    private C b;
    private ReadWriteLock c = new ReentrantReadWriteLock();
    private Lock d = this.c.writeLock();
    private Lock e = this.c.readLock();

    public D(A a2) {
        this.a = a2;
        this.b = new C(this.a.d());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long a(String string) {
        long l2 = -1L;
        this.d.lock();
        SQLiteDatabase sQLiteDatabase = this.b();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("uuid", string);
            l2 = sQLiteDatabase.insert("StatisticsInfo", null, contentValues);
        }
        finally {
            sQLiteDatabase.close();
            this.d.unlock();
        }
        return l2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int a(String string, String string2, String string3) {
        int n2 = -1;
        this.d.lock();
        SQLiteDatabase sQLiteDatabase = this.b();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(string2, string3);
            n2 = sQLiteDatabase.update("StatisticsInfo", contentValues, "uuid=?", new String[]{string});
            if (n2 == 0) {
                sQLiteDatabase.insert("StatisticsInfo", null, contentValues);
            }
        }
        finally {
            sQLiteDatabase.close();
            this.d.unlock();
        }
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<String, ArrayList> a() {
        HashMap<String, ArrayList> hashMap = new HashMap<String, ArrayList>();
        ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        this.e.lock();
        SQLiteDatabase sQLiteDatabase = this.c();
        Cursor cursor = sQLiteDatabase.rawQuery("select * from StatisticsInfo limit 0,100", null);
        try {
            while (cursor.moveToNext()) {
                JSONObject jSONObject = new JSONObject();
                try {
                    int n2 = cursor.getInt(cursor.getColumnIndex("id"));
                    jSONObject.put("uuid", (Object)cursor.getString(cursor.getColumnIndex("uuid")));
                    jSONObject.put("startInfo", (Object)cursor.getString(cursor.getColumnIndex("startInfo")));
                    jSONObject.put("endInfo", (Object)cursor.getString(cursor.getColumnIndex("endInfo")));
                    arrayList2.add(n2);
                    arrayList.add(jSONObject);
                }
                catch (JSONException jSONException) {
                    jSONException.printStackTrace();
                }
            }
            hashMap.put("listId", arrayList2);
            hashMap.put("list", arrayList);
            return hashMap;
        }
        finally {
            cursor.close();
            sQLiteDatabase.close();
            this.e.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int a(int n2, int n3) {
        this.d.lock();
        int n4 = -1;
        SQLiteDatabase sQLiteDatabase = this.c();
        try {
            n4 = sQLiteDatabase.delete("StatisticsInfo", "id between ? and ?", new String[]{Integer.toString(n2), Integer.toString(n3)});
            LoggerProxy.d("StatisticsDbManager", "delete database=" + n4 + "=" + n2 + "=" + n3);
        }
        finally {
            sQLiteDatabase.close();
            this.d.unlock();
        }
        return n4;
    }

    private SQLiteDatabase b() {
        return this.b.getWritableDatabase();
    }

    private SQLiteDatabase c() {
        return this.b.getReadableDatabase();
    }
}


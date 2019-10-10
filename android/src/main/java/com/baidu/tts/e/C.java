/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.e;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class C {
    private static C a;
    private a b;
    private ReadWriteLock c = new ReentrantReadWriteLock();
    private Lock d = this.c.writeLock();
    private Lock e = this.c.readLock();
    private Context f;

    private C(Context context) {
        this.f = context;
        this.b = new a(this.f);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static C a(Context context) {
        if (a != null) return a;
        Class<C> class_ = C.class;
        synchronized (C.class) {
            if (a != null) return a;
            {
                a = new C(context);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return a;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(long l2, int n2, int n3, int n4, String string) {
        this.d.lock();
        SQLiteDatabase sQLiteDatabase = this.c();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", Long.valueOf(l2));
            contentValues.put("code", Integer.valueOf(n2));
            contentValues.put("cmd_type", Integer.valueOf(n3));
            contentValues.put("cmd_id", Integer.valueOf(n4));
            contentValues.put("result", string);
            sQLiteDatabase.insert("result", null, contentValues);
        }
        catch (SQLiteException sQLiteException) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + sQLiteException.toString());
        }
        catch (IllegalStateException illegalStateException) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + illegalStateException.toString());
        }
        catch (Exception exception) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + exception.toString());
        }
        finally {
            sQLiteDatabase.close();
            this.d.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int a() {
        int n2 = 0;
        Cursor cursor = null;
        this.e.lock();
        SQLiteDatabase sQLiteDatabase = this.d();
        try {
            if (sQLiteDatabase != null) {
                cursor = sQLiteDatabase.query("result", new String[]{"_id", "time", "code", "cmd_type", "cmd_id", "result"}, null, null, null, null, null);
                n2 = cursor.getCount();
            }
        }
        catch (SQLiteException sQLiteException) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + sQLiteException.toString());
        }
        catch (IllegalStateException illegalStateException) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + illegalStateException.toString());
        }
        catch (Exception exception) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + exception.toString());
        }
        finally {
            cursor.close();
            sQLiteDatabase.close();
            this.e.unlock();
        }
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<String, ArrayList> b() {
        HashMap<String, ArrayList> hashMap = new HashMap<String, ArrayList>();
        ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        this.e.lock();
        SQLiteDatabase sQLiteDatabase = this.d();
        Cursor cursor = sQLiteDatabase.rawQuery("select * from result limit 0,500", null);
        try {
            while (cursor.moveToNext()) {
                JSONObject jSONObject = new JSONObject();
                try {
                    int n2 = cursor.getInt(cursor.getColumnIndex("_id"));
                    String string = cursor.getString(cursor.getColumnIndex("time"));
                    int n3 = cursor.getInt(cursor.getColumnIndex("code"));
                    int n4 = cursor.getInt(cursor.getColumnIndex("cmd_type"));
                    int n5 = cursor.getInt(cursor.getColumnIndex("cmd_id"));
                    String string2 = cursor.getString(cursor.getColumnIndex("result"));
                    jSONObject.put("time", Long.parseLong(string));
                    jSONObject.put("error_code", n3);
                    if (n3 == 0) {
                        jSONObject.put("cmd_type", n4);
                        jSONObject.put("cmd_id", n5);
                        jSONObject.put("voice_to_text_result", (Object)string2);
                    }
                    arrayList2.add(n2);
                    arrayList.add(jSONObject);
                }
                catch (JSONException jSONException) {
                    jSONException.printStackTrace();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            hashMap.put("listId", arrayList2);
            hashMap.put("list", arrayList);
            return hashMap;
        }
        catch (SQLiteException sQLiteException) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + sQLiteException.toString());
            return hashMap;
        }
        catch (IllegalStateException illegalStateException) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + illegalStateException.toString());
            return hashMap;
        }
        catch (Exception exception) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + exception.toString());
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
    public void a(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.d.lock();
        SQLiteDatabase sQLiteDatabase = this.d();
        try {
            String string = "";
            for (int i2 = 0; i2 < list.size(); ++i2) {
                string = string + list.get(i2) + ",";
            }
            if (string.length() > 0) {
                string = string.substring(0, string.length() - 1);
                sQLiteDatabase.delete("result", "_id in (" + string + ")", null);
            }
        }
        catch (SQLiteException sQLiteException) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + sQLiteException.toString());
        }
        catch (IllegalStateException illegalStateException) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + illegalStateException.toString());
        }
        catch (Exception exception) {
            LoggerProxy.d("SynthesizeResultDb", "exception:" + exception.toString());
        }
        finally {
            sQLiteDatabase.close();
            this.d.unlock();
        }
    }

    private SQLiteDatabase c() {
        return this.b.getWritableDatabase();
    }

    private SQLiteDatabase d() {
        return this.b.getReadableDatabase();
    }

    class a
    extends SQLiteOpenHelper {
        public a(Context context) {
            super(context, "ttsdata", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table result (_id integer primary key autoincrement, time text, code integer, cmd_type integer, cmd_id integer, result text);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS result");
            this.onCreate(db);
        }
    }

}


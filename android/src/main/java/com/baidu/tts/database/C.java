/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 */
package com.baidu.tts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.baidu.tts.database.E;

public class C
extends SQLiteOpenHelper {
    public C(Context context) {
        this(context, "Statistics.db", null, 1);
    }

    public C(Context context, String string, SQLiteDatabase.CursorFactory cursorFactory, int n2) {
        super(context, string, cursorFactory, n2);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(E.a());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(E.b());
        this.onCreate(db);
    }
}


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
import com.baidu.tts.database.FsFileInfoTable;
import com.baidu.tts.database.ModelFileTable;
import com.baidu.tts.database.SpeechModelTable;

public class B
extends SQLiteOpenHelper {
    public B(Context context) {
        this(context, "ttsModel.db", null, 1);
    }

    public B(Context context, String string, SQLiteDatabase.CursorFactory cursorFactory, int n2) {
        super(context, string, cursorFactory, n2);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SpeechModelTable.a());
        db.execSQL(ModelFileTable.a());
        db.execSQL(FsFileInfoTable.a());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SpeechModelTable.b());
        db.execSQL(ModelFileTable.b());
        db.execSQL(FsFileInfoTable.b());
        this.onCreate(db);
    }
}


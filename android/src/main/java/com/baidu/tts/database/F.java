/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.database.sqlite.SQLiteDatabase
 */
package com.baidu.tts.database;

import android.database.sqlite.SQLiteDatabase;

public class F {
    private SQLiteDatabase a;
    private a b;

    public F(SQLiteDatabase sQLiteDatabase, a a2) {
        this.a = sQLiteDatabase;
        this.b = a2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean a() {
        boolean bl = false;
        if (this.b != null && this.a != null) {
            try {
                this.a.beginTransaction();
                bl = this.b.a(this.a);
                if (bl) {
                    this.a.setTransactionSuccessful();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            finally {
                if (this.a != null) {
                    this.a.endTransaction();
                    this.a.close();
                }
            }
        }
        return bl;
    }

    public static interface a {
        public boolean a(SQLiteDatabase var1);
    }

}


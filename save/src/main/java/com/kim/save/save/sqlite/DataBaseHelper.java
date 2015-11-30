package com.kim.save.save.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KIM on 2015/8/18.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String name = "mydb.db";
    private static final int version = 1;

    public DataBaseHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table person(" +
                "id integer primary key autoincrement," +
                "name varchar(64)," +
                "age integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

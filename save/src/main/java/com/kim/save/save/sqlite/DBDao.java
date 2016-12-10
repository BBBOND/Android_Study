package com.kim.save.save.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KIM on 2015/8/18.
 */
public class DBDao implements DBService {

    private DataBaseHelper dbHelper;
    private String DBNAME = "";

    public DBDao(Context context, String DBName) {
        dbHelper = new DataBaseHelper(context);
        DBNAME = DBName;
    }

    @Override
    public int addPerson(Object[] params) {
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", params[0].toString());
        cv.put("age", params[1].toString());
        int result = (int) dbWriter.insert(DBNAME, null, cv);
        dbWriter.close();
        if (dbHelper != null) {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public int deletePerson(Object[] params) {
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
        int result = dbWriter.delete(DBNAME, "id=?", (String[]) params);
        dbWriter.close();
        if (dbHelper != null) {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public int updatePerson(Object[] params) {
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", params[1].toString());
        cv.put("age", params[2].toString());
        int result = dbWriter.update(DBNAME, cv, "id=?", new String[]{params[0].toString()});
        dbWriter.close();
        if (dbHelper != null) {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Map<String, String> getPerson(Object[] params) {
        SQLiteDatabase dbReader = dbHelper.getReadableDatabase();
        Map<String, String> map = new HashMap<>();
        Cursor c = dbReader.query(DBNAME, null, "id=?", new String[]{params[0].toString()}, null, null, null);
        int len = c.getColumnCount();
        while (c.moveToNext()) {
            for (int i = 0; i < len; i++) {
                String col_name = c.getColumnName(i);
                String col_value = c.getString(c.getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }
        }
        return map;
    }

    @Override
    public List<Map<String, String>> getPersons() {
        SQLiteDatabase dbReader = dbHelper.getReadableDatabase();
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = null;
        Cursor c = dbReader.query(DBNAME, null, null, null, null, null, null);
        int len = c.getColumnCount();
        while (c.moveToNext()) {
            map = new HashMap<>();
            for (int i = 0; i < len; i++) {
                String col_name = c.getColumnName(i);
                String col_value = c.getString(c.getColumnIndex(col_name));
                if (col_value == null) {
                    col_value = "";
                }
                map.put(col_name, col_value);
            }
            list.add(map);
        }
        return list;
    }
}

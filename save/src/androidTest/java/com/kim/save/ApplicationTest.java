package com.kim.save;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.view.View;

import com.kim.save.save.sqlite.DataBaseHelper;
import com.kim.save.save.FileSave;
import com.kim.save.save.MySharedPreference;

import java.util.Map;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private final String TAG = "ApplicationTest";

    public ApplicationTest() {
        super(Application.class);
    }

    public void testSdCardSave() {
        FileSave fileSave = new FileSave(getContext());
        boolean flag = fileSave.saveToSDCard("save.txt", "你好");
        Log.i(TAG, "----->>>" + flag);
    }

    public void testSdCardRead() {
        FileSave fileSave = new FileSave(getContext());
        String content = fileSave.readFromSDCard("save.txt");
        Log.i(TAG, "----->>>" + content);
    }

    public void testSharedPreferenceSave() {
        MySharedPreference mySharedPreference = new MySharedPreference(getContext());
        boolean flag = mySharedPreference.save("userinfo", "admin", "admin");
        Log.i(TAG, "----->>>" + flag);
    }

    public void testSharedPreferenceRead() {
        MySharedPreference mySharedPreference = new MySharedPreference(getContext());
        Map<String, Object> map = mySharedPreference.read("userinfo");
        Log.i(TAG, "----->>>" + map.toString());
    }

    public void testDBSave() {
        //测试SQLite  创建数据库
        DataBaseHelper dbHelper = new DataBaseHelper(getContext());
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
        // TODO: 2015/8/18
    }

    public void testDBRead() {
        // TODO: 2015/8/18
    }

    public void testContentProviderSave() {
        ContentResolver contentResolver = getContext().getContentResolver();
        //Uri uri = Uri.parse("content://com.kim.save.save.PersonProvider/person/1");
        Uri uri = Uri.parse("content://com.kim.save.save.PersonProvider/persons");
        ContentValues values = new ContentValues();
        values.put("name", "张三");
        values.put("age", 21);
        contentResolver.insert(uri, values);
    }

    public void testContentProviderQuery() {
        ContentResolver contentResolver = getContext().getContentResolver();
        //Uri uri = Uri.parse("content://com.kim.save.save.PersonProvider/person/1");
        Uri uri = Uri.parse("content://com.kim.save.save.PersonProvider/persons");
        Cursor cursor = contentResolver.query(uri, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Log.i(TAG, "------>" + cursor.getColumnIndex("name"));
        }
    }

    public void testContentProviderDelete() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.kim.save.save.PersonProvider/person/1");
        contentResolver.delete(uri, null, null);
    }

    public void testContentProviderUpdate() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.kim.save.save.PersonProvider/person/1");
        ContentValues values = new ContentValues();
        values.put("name", "李四");
        values.put("age", 20);
        contentResolver.update(uri, values, null, null);
    }
}
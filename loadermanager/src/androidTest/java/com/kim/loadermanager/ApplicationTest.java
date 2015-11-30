package com.kim.loadermanager;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ApplicationTestCase;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private final String TAG = "ApplicationTest";

    public ApplicationTest() {
        super(Application.class);
    }

    public void testAdd() {
        ContentResolver contentResolver = getContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name", "hahaha");
        Uri uri = Uri.parse("content://com.kim.loadermanager.PersonContentProvider/person");
        Uri uri1 = contentResolver.insert(uri, values);
        Log.i(TAG, "---->" + uri1);
    }

    public void testDelete() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.kim.loadermanager.PersonContentProvider/person/1");
        int count = contentResolver.delete(uri, null, null);
        Log.i(TAG, "---->" + count);
    }

    public void testUpdate() {
        ContentResolver contentResolver = getContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name", "hehehe");
        Uri uri = Uri.parse("content://com.kim.loadermanager.PersonContentProvider/person/1");
        int count = contentResolver.update(uri, values, null, null);
        Log.i(TAG, "---->" + count);
    }

    public void testQuery() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.kim.loadermanager.PersonContentProvider/person");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        String name = "";
        while (cursor.moveToNext()) {
            name += cursor.getString(1) + "\n";
        }
        Log.i(TAG, "---->" + name);
    }
}
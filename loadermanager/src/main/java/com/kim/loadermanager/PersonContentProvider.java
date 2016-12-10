package com.kim.loadermanager;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.kim.loadermanager.dbhelper.DBHelper;

/**
 * Created by KIM on 2015/8/27.
 */
public class PersonContentProvider extends ContentProvider {

    private final String TAG = "PersonContentProvider";
    private final static UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private final static int PERSON = 1;
    private final static int PERSONS = 2;
    private DBHelper dbHelper;

    static {
        URI_MATCHER.addURI("com.kim.loadermanager.PersonContentProvider", "person", PERSONS);
        URI_MATCHER.addURI("com.kim.loadermanager.PersonContentProvider", "person/#", PERSON);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase dbReader = dbHelper.getReadableDatabase();
        int flag = URI_MATCHER.match(uri);
        switch (flag) {
            case PERSON:
                long id = ContentUris.parseId(uri);
                String where_value = "_id=" + id;
                if (selection != null && !selection.isEmpty()) {
                    where_value += selection;
                }
                cursor = dbReader.query("person", null, where_value, selectionArgs, null, null, null);
                Log.i(TAG, "------->query PERSON ");
                break;
            case PERSONS:
                cursor = dbReader.query("person", null, selection, selectionArgs, null, null, null);
                Log.i(TAG, "------->query PERSONS ");
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        int flag = URI_MATCHER.match(uri);
        switch (flag) {
            case PERSON:
                return "vnd.android.cursor.item/person";
            case PERSONS:
                return "vnd.android.cursor.dir/persons";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int flag = URI_MATCHER.match(uri);
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
        Uri uri1 = null;
        switch (flag) {
            case PERSONS:
                long id = dbWriter.insert("person", null, values);
                uri1 = ContentUris.withAppendedId(uri, id);
                break;
        }
        return uri1;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int flag = URI_MATCHER.match(uri);
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
        int count = 0;
        switch (flag) {
            case PERSON:
                long id = ContentUris.parseId(uri);
                String where_value = "_id=" + id;
                if (selection != null && !selection.isEmpty())
                    where_value += selection;
                count = dbWriter.delete("person", where_value, selectionArgs);
                break;
            case PERSONS:
                count = dbWriter.delete("person", selection, selectionArgs);
                break;
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
        int flag = URI_MATCHER.match(uri);
        switch (flag) {
            case PERSON:
                long id = ContentUris.parseId(uri);
                String where_value = "_id=" + id;
                if (selection != null && !selection.isEmpty())
                    where_value += selection;
                count = dbWriter.update("person", values, where_value, selectionArgs);
                Log.i(TAG, "update PERSON");
                break;
            case PERSONS:
                count = dbWriter.update("person", values, selection, selectionArgs);
                Log.i(TAG, "update PERSONS");
                break;
        }
        return count;
    }
}

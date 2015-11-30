package com.kim.save.save;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.kim.save.save.sqlite.DataBaseHelper;

/**
 * Created by KIM on 2015/8/19.
 */
public class PersonProvider extends ContentProvider {

    private final String TAG = "PersonProvider";
    private DataBaseHelper dbHelper = null;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int PERSON = 1;//操作单条记录
    private static final int PERSONS = 2;//操作多条记录

    static {
        URI_MATCHER.addURI("com.kim.save.save.PersonProvider", "person/#", PERSON);
        URI_MATCHER.addURI("com.kim.save.save.PersonProvider", "person", PERSONS);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DataBaseHelper(getContext());
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
                String where_value = " id=" + id;
                if (selection != null && !selection.equals("")) {
                    where_value += " and " + selection;
                }
                cursor = dbReader.query("person", null, where_value, selectionArgs, null, null, null);
                break;
            case PERSONS:
                cursor = dbReader.query("person", null, selection, selectionArgs, null, null, null);
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
                return "vnd.android.cursor.dir/person";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri resultUri = null;
        int flag = URI_MATCHER.match(uri);
        switch (flag) {
            case PERSON:
                break;
            case PERSONS:
                SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
                long id = dbWriter.insert("person", null, values);
                resultUri = ContentUris.withAppendedId(uri, id);
                Log.i(TAG, "----->" + resultUri.toString());
                break;
        }
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //uri = com.kim.save.save.PersonProvider/person/1
        int count = -1;
        SQLiteDatabase dbWriter = dbHelper.getReadableDatabase();
        int flag = URI_MATCHER.match(uri);
        switch (flag) {
            case PERSON:
                long id = ContentUris.parseId(uri);
                String where_value = "id" + id;
                if (selection != null && !selection.equals("")) {
                    where_value += " and " + selection;
                }
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
        //uri = com.kim.save.save.PersonProvider/person/2
        int count = -1;
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
        long id = ContentUris.parseId(uri);
        int flag = URI_MATCHER.match(uri);
        switch (flag) {
            case PERSON:
                String where_value = " id=" + id;
                if (selection != null && !selection.equals("")) {
                    where_value += " and " + selection;
                }
                count = dbWriter.update("person", values, where_value, selectionArgs);
                break;
        }
        return count;
    }
}

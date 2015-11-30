package com.kim.loadermanager;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LoaderManager manager;
    private EditText name;
    private Button insert;
    private ListView nameLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        insert = (Button) findViewById(R.id.insert);
        nameLV = (ListView) findViewById(R.id.name_LV);

        manager = getLoaderManager();
        manager.initLoader(1000, null, callbacks);

        insert.setOnClickListener(this);
        registerForContextMenu(nameLV);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView id_tv = (TextView) info.targetView.findViewById(R.id.id_TV);
        String id = id_tv.getText().toString();
        switch (item.getItemId()) {
            case R.id.edit:
                TextView name_tv = (TextView) info.targetView.findViewById(R.id.name_TV);
                String name = name_tv.getText().toString().trim();
                editDialog(id, name);
                break;
            case R.id.del:
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://com.kim.loadermanager.PersonContentProvider/person");
                uri = ContentUris.withAppendedId(uri, Long.parseLong(id));
                contentResolver.delete(uri, null, null);
                manager.restartLoader(1000, null, callbacks);
                break;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void editDialog(final String id, final String name) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_context, null);
        final TextView id_dialog = (TextView) layout.findViewById(R.id.id_dialog);
        final EditText name_dialog = (EditText) layout.findViewById(R.id.name_dialog);
        id_dialog.setText(id);
        name_dialog.setHint(name);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("修改");
        builder.setView(layout);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://com.kim.loadermanager.PersonContentProvider/person");
                uri = ContentUris.withAppendedId(uri, Long.parseLong(id));
                String nameStr = name_dialog.getText().toString().trim();
                if (nameStr.isEmpty()) {
                    nameStr = name;
                }
                ContentValues values = new ContentValues();
                values.put("name", nameStr);
                Log.i("------>>", values.getAsString("name"));
                contentResolver.update(uri, values, null, null);
                manager.restartLoader(1000, null, callbacks);
            }
        });
        builder.show();
    }

    private LoaderManager.LoaderCallbacks<Cursor> callbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//            CursorLoader loader = new CursorLoader(MainActivity.this);
            Uri uri = Uri.parse("content://com.kim.loadermanager.PersonContentProvider/person");
//            loader.setUri(uri);
            CursorLoader loader = new CursorLoader(MainActivity.this, uri, null, null, null, null);
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            //对UI的操作
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.listview_item, data, new String[]{"_id", "name"}, new int[]{R.id.id_TV, R.id.name_TV});
            nameLV.setAdapter(adapter);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                String nameStr = name.getText().toString().trim();
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://com.kim.loadermanager.PersonContentProvider/person");
                ContentValues values = new ContentValues();
                values.put("name", nameStr);
                contentResolver.insert(uri, values);
                manager.restartLoader(1000, null, callbacks);
                break;
        }
    }
}

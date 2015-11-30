package com.jwy.usingsqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private EditText name;
    private EditText sex;
    private Button save;
    private Button read;
    private ListView readlist;
    private Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Db(this);

        name = (EditText) findViewById(R.id.name);
        sex = (EditText) findViewById(R.id.sex);
        save = (Button) findViewById(R.id.save);
        read = (Button) findViewById(R.id.read);
        readlist = (ListView) findViewById(R.id.readlist);

        save.setOnClickListener(new saveAction());

        read.setOnClickListener(new readAction());
    }

    class saveAction implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String n = name.getText().toString().trim();
            String s = sex.getText().toString().trim();

            //写入
            SQLiteDatabase dbWrite = db.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("name", n);
            cv.put("sex", s);
            dbWrite.insert("user", null, cv);

            dbWrite.close();
        }
    }

    class readAction implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //读取
            SQLiteDatabase dbRead = db.getReadableDatabase();
            List<String> list = new ArrayList<>();
            Cursor c = dbRead.query("user", null, null, null, null, null, null);

            while (c.moveToNext()){
                String name = c.getString(c.getColumnIndex("name"));
                String sex = c.getString(c.getColumnIndex("sex"));
                list.add(String.format("name=%s,sex=%s", name, sex));
                Log.i("MainActivity", String.format("name=%s,sex=%s", name, sex));
            }

            c.close();
            dbRead.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_single_choice, list);
            readlist.setAdapter(adapter);
            readlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }
}

package com.kim.json.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kim.json.R;
import com.kim.json.http.GsonHttpUtils;
import com.kim.json.http.JsonHttpUtils;
import com.kim.json.model.Person;
import com.kim.json.utils.FastjsonUtils;
import com.kim.json.utils.GsonUtils;
import com.kim.json.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {

    private Spinner spinner;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        findViewById(R.id.json_btn).setOnClickListener(this);
        findViewById(R.id.gson_btn).setOnClickListener(this);
        findViewById(R.id.fastjson_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.json_btn:
                if (getSpinnerSelect() == 0) {
                    Toast.makeText(MainActivity.this, "请选择！", Toast.LENGTH_SHORT).show();
                } else {
                    json(getSpinnerSelect());
                }
                break;
            case R.id.gson_btn:
                if (getSpinnerSelect() == 0) {
                    Toast.makeText(MainActivity.this, "请选择！", Toast.LENGTH_SHORT).show();
                } else {
                    gson(getSpinnerSelect());
                }
                break;
            case R.id.fastjson_btn:
                if (getSpinnerSelect() == 0) {
                    Toast.makeText(MainActivity.this, "请选择！", Toast.LENGTH_SHORT).show();
                } else {
                    fastjson(getSpinnerSelect());
                }
                break;
        }
    }

    private void json(final int key) {
        new AsyncTask<Integer, Void, String>() {

            ProgressDialog dialog = new ProgressDialog(MainActivity.this);

            @Override
            protected String doInBackground(Integer... params) {
                return JsonHttpUtils.changeInputStreamToJsonString(params[0]);
            }

            @Override
            protected void onPreExecute() {
                dialog.setTitle("读取中。。。");
                dialog.setMessage("loading...");
                dialog.setCancelable(true);
                dialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null || s.equals("")) {
                    Toast.makeText(MainActivity.this, "无法读取数据！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("---------------->", s);
                    Person person;
                    List<Person> persons;
                    ArrayAdapter<Person> adapter;
                    switch (key) {
                        case 1:
                            person = JsonUtils.getPerson("person", s);
                            persons = new ArrayList<Person>();
                            persons.add(person);
                            adapter = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, persons);
                            listView.setAdapter(adapter);
                            break;
                        case 2:
                            persons = JsonUtils.getPersons("persons", s);
                            adapter = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, persons);
                            listView.setAdapter(adapter);
                            break;
                        case 3:
                            List<String> list = JsonUtils.getStringList("stringList", s);
                            ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                            listView.setAdapter(stringAdapter);
                            break;
                        case 4:
                            List<Map<String, Object>> mapList = JsonUtils.getMapList("mapList", s);
                            ArrayAdapter<Map<String, Object>> mapListadapter = new ArrayAdapter<Map<String, Object>>(MainActivity.this, android.R.layout.simple_list_item_1, mapList);
                            listView.setAdapter(mapListadapter);
                            break;
                        default:
                            break;
                    }
                }
                dialog.dismiss();
                super.onPostExecute(s);
            }
        }.execute(key);
    }

    private void gson(final int key) {
        new AsyncTask<Integer, Void, String>() {

            ProgressDialog dialog = new ProgressDialog(MainActivity.this);

            @Override
            protected String doInBackground(Integer... params) {
                return GsonHttpUtils.changeInputStreamToJsonString(params[0]);
            }

            @Override
            protected void onPreExecute() {
                dialog.setTitle("读取中。。。");
                dialog.setMessage("loading...");
                dialog.setCancelable(true);
                dialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null || s.equals("")) {
                    Toast.makeText(MainActivity.this, "无法读取数据！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("---------------->", s);
                    Person person;
                    List<Person> persons;
                    ArrayAdapter<Person> adapter;
                    switch (key) {
                        case 1:
                            person = GsonUtils.getPerson(s, Person.class);
                            persons = new ArrayList<Person>();
                            persons.add(person);
                            adapter = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, persons);
                            listView.setAdapter(adapter);
                            break;
                        case 2:
                            persons = GsonUtils.getPersons(s, Person.class);
                            adapter = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, persons);
                            listView.setAdapter(adapter);
                            break;
                        case 3:
                            List<String> list = GsonUtils.getStringList(s);
                            ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                            listView.setAdapter(stringAdapter);
                            break;
                        case 4:
                            List<Map<String, Object>> mapList = GsonUtils.getMapList(s);
                            ArrayAdapter<Map<String, Object>> mapListadapter = new ArrayAdapter<Map<String, Object>>(MainActivity.this, android.R.layout.simple_list_item_1, mapList);
                            listView.setAdapter(mapListadapter);
                            break;
                        default:
                            break;
                    }
                }
                dialog.dismiss();
                super.onPostExecute(s);
            }
        }.execute(key);
    }

    private void fastjson(final int key) {
        new AsyncTask<Integer, Void, String>() {

            ProgressDialog dialog = new ProgressDialog(MainActivity.this);

            @Override
            protected String doInBackground(Integer... params) {
                return GsonHttpUtils.changeInputStreamToJsonString(params[0]);
            }

            @Override
            protected void onPreExecute() {
                dialog.setTitle("读取中。。。");
                dialog.setMessage("loading...");
                dialog.setCancelable(true);
                dialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null || s.equals("")) {
                    Toast.makeText(MainActivity.this, "无法读取数据！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("---------------->", s);
                    Person person;
                    List<Person> persons;
                    ArrayAdapter<Person> adapter;
                    switch (key) {
                        case 1:
                            person = FastjsonUtils.getPerson(s, Person.class);
                            Log.i("+++++++++>", person.toString());
                            persons = new ArrayList<Person>();
                            persons.add(person);
                            adapter = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, persons);
                            listView.setAdapter(adapter);
                            break;
                        case 2:
                            persons = FastjsonUtils.getPersons(s, Person.class);
                            Log.i("+++++++++>", persons.toString());
                            adapter = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, persons);
                            listView.setAdapter(adapter);
                            break;
                        case 3:
                            List<String> list = FastjsonUtils.getStringList(s);
                            Log.i("+++++++++>", list.toString());
                            ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                            listView.setAdapter(stringAdapter);
                            break;
                        case 4:
                            List<Map<String, Object>> mapList = FastjsonUtils.getMapList(s);
                            Log.i("+++++++++>", mapList.toString());
                            ArrayAdapter<Map<String, Object>> mapListadapter = new ArrayAdapter<Map<String, Object>>(MainActivity.this, android.R.layout.simple_list_item_1, mapList);
                            listView.setAdapter(mapListadapter);
                            break;
                        default:
                            break;
                    }
                }
                dialog.dismiss();
                super.onPostExecute(s);
            }
        }.execute(key);
    }

    public int getSpinnerSelect() {
        return (int) spinner.getSelectedItemId();
    }

    public String getSpinnerSelectText() {
        return spinner.getSelectedItem().toString();
    }

    private void init() {
        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.listview);

        List<String> list = new ArrayList<>();
        list.add("-请选择-");
        list.add("person");
        list.add("persons");
        list.add("StringList");
        list.add("MapList");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
        spinner.setAdapter(adapter);
    }
}

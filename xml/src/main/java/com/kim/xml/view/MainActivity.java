package com.kim.xml.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.kim.xml.R;
import com.kim.xml.handler.PullTools;
import com.kim.xml.http.HttpUtils;
import com.kim.xml.model.Person;
import com.kim.xml.service.DomService;
import com.kim.xml.service.SaxService;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String PATH = "http://192.168.1.105:8888/AndroidHttpTest/001.xml";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.xml_lv);
        findViewById(R.id.sax_btn).setOnClickListener(this);
        findViewById(R.id.pull_btn).setOnClickListener(this);
        findViewById(R.id.dom_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sax_btn:
                showXMLBySax(PATH, "person");
                break;
            case R.id.pull_btn:
                showXMLByPull(PATH, "UTF-8");
                break;
            case R.id.dom_btn:
                showXMLByDom(PATH);
                break;
        }
    }

    private void showXMLBySax(String path, String nodeName) {
        new AsyncTask<String, Void, List<HashMap<String, String>>>() {

            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

            @Override
            protected List<HashMap<String, String>> doInBackground(String... params) {
                InputStream inputStream = HttpUtils.getXML(params[0]);
                return inputStream == null ? null : SaxService.readXML(inputStream, params[1]);
            }

            @Override
            protected void onPreExecute() {
                progressDialog.setTitle("读取中...");
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
                if (hashMaps == null) {
                    Toast.makeText(MainActivity.this, "无数据！", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayAdapter<HashMap<String, String>> adapter = new ArrayAdapter<HashMap<String, String>>(MainActivity.this, android.R.layout.simple_list_item_1, hashMaps);
                    listView.setAdapter(adapter);
                }
                progressDialog.dismiss();
                super.onPostExecute(hashMaps);
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(List<HashMap<String, String>> hashMaps) {
                super.onCancelled(hashMaps);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(path, nodeName);
    }

    private void showXMLByPull(String path, String encode) {
        new AsyncTask<String, Void, List<Person>>() {

            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

            @Override
            protected List<Person> doInBackground(String... params) {
                try {
                    InputStream inputStream = HttpUtils.getXML(params[0]);
                    return PullTools.parseXML(inputStream, params[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                progressDialog.setTitle("读取中...");
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<Person> persons) {
                if (persons == null) {
                    Toast.makeText(MainActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, persons);
                    listView.setAdapter(adapter);
                }
                progressDialog.dismiss();
                super.onPostExecute(persons);
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(List<Person> persons) {
                super.onCancelled(persons);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(path, encode);
    }

    private void showXMLByDom(final String path) {
        new AsyncTask<String, Void, List<Person>>() {

            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

            @Override
            protected List<Person> doInBackground(String... params) {
                try {
                    InputStream inputStream = HttpUtils.getXML(params[0]);
                    return DomService.getPersons(inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                progressDialog.setTitle("读取中...");
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(List<Person> persons) {
                if (persons == null) {
                    Toast.makeText(MainActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, persons);
                    listView.setAdapter(adapter);
                }
                progressDialog.dismiss();
                super.onPostExecute(persons);
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(List<Person> persons) {
                super.onCancelled(persons);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(path);
    }
}

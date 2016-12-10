package com.kim.http.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kim.http.R;
import com.kim.http.utils.HttpClientGet;
import com.kim.http.utils.MyHttpGet;

public class HttpGetActivity extends Activity implements View.OnClickListener {

    private EditText urlEt;
    private Button showBtn;
    private ImageView imgIV;
    private Button toPost;
    private Button showBtnClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_get);

        urlEt = (EditText) findViewById(R.id.url_et);
        showBtn = (Button) findViewById(R.id.show_btn);
        imgIV = (ImageView) findViewById(R.id.img_iv);
        toPost = (Button) findViewById(R.id.go_to_post);
        showBtnClient = (Button) findViewById(R.id.show_btn_httpclient);

        showBtn.setOnClickListener(this);
        toPost.setOnClickListener(this);
        showBtnClient.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(urlEt.getWindowToken(), 0);
        String url = urlEt.getText().toString().trim();
        switch (v.getId()) {
            case R.id.show_btn:
                readUrlImg(url);
                break;
            case R.id.show_btn_httpclient:
                readUrlImgClient(url);
                break;
            case R.id.go_to_post:
                Intent intent = new Intent(HttpGetActivity.this, HttpPostActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void readUrlImg(String url) {
        //android自带轻量级线程
        new AsyncTask<String, Void, byte[]>() {

            ProgressDialog progressDialog = new ProgressDialog(HttpGetActivity.this);

            @Override
            protected byte[] doInBackground(String... params) {
                MyHttpGet myHttpGet = new MyHttpGet(params[0]);
                return myHttpGet.readInputStream();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setTitle("读取中请等待。。。");
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(byte[] bytes) {
                super.onPostExecute(bytes);
                if (bytes != null) {
                    Bitmap bit = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imgIV.setImageBitmap(bit);
                } else {
                    Toast.makeText(getApplicationContext(), "无法读取照片", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(byte[] bytes) {
                super.onCancelled(bytes);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(url);
    }

    public void readUrlImgClient(String url) {
        new AsyncTask<String, Void, byte[]>() {

            ProgressDialog progressDialog = new ProgressDialog(HttpGetActivity.this);

            @Override
            protected byte[] doInBackground(String... params) {
                HttpClientGet httpClientGet = new HttpClientGet(params[0]);
                return httpClientGet.readInputStream();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setTitle("读取中请等待。。。");
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(byte[] bytes) {
                super.onPostExecute(bytes);
                if (bytes != null) {
                    Bitmap bit = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imgIV.setImageBitmap(bit);
                } else {
                    Toast.makeText(getApplicationContext(), "无法读取照片", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(byte[] bytes) {
                super.onCancelled(bytes);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(url);
    }
}

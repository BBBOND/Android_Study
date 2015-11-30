package com.kim.http.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kim.http.R;
import com.kim.http.utils.HttpClientGet;
import com.kim.http.utils.HttpClientPost;
import com.kim.http.utils.MyHttpPost;
import com.kim.http.utils.Userinfo;

/**
 * Created by KIM on 2015/8/3.
 */
public class HttpPostActivity extends Activity implements View.OnClickListener {

    private EditText usernameEt;
    private EditText passwordEt;
    private Button loginBtn;
    //    private final String URL_PATH = "http://192.168.1.109:8888/AndroidHttpTest/loginServlet";
    private final String URL_PATH = "http://10.0.2.2:8888/AndroidHttpTest/loginServlet";
    private Button loginBtnClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_post);

        usernameEt = (EditText) findViewById(R.id.username_et);
        passwordEt = (EditText) findViewById(R.id.password_et);
        loginBtn = (Button) findViewById(R.id.login);
        loginBtnClient = (Button) findViewById(R.id.login_client);

        loginBtn.setOnClickListener(this);
        loginBtnClient.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Userinfo userinfo = new Userinfo();
        String username = usernameEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        userinfo.setUserName(username);
        userinfo.setUserPassword(password);
        switch (v.getId()) {
            case R.id.login:
                login(userinfo);
                break;
            case R.id.login_client:
                loginClient(userinfo);
                break;
        }
    }

    public void login(Userinfo userinfo) {
        new AsyncTask<Userinfo, Void, Boolean>() {

            ProgressDialog progressDialog = new ProgressDialog(HttpPostActivity.this);

            @Override
            protected Boolean doInBackground(Userinfo... params) {
                MyHttpPost myHttpPost = new MyHttpPost(URL_PATH, params[0]);
                return myHttpPost.checkLogin();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setTitle("登录中。。。");
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean)
                    Toast.makeText(HttpPostActivity.this, "登录成功!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(HttpPostActivity.this, "登录失败!", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(Boolean aBoolean) {
                super.onCancelled(aBoolean);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(userinfo);
    }


    public void loginClient(Userinfo userinfo) {
        new AsyncTask<Userinfo, Void, Boolean>() {

            ProgressDialog progressDialog = new ProgressDialog(HttpPostActivity.this);

            @Override
            protected Boolean doInBackground(Userinfo... params) {
                HttpClientPost httpClientPost = new HttpClientPost(URL_PATH, params[0]);
                return httpClientPost.checkLogin();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setTitle("登录中。。。");
                progressDialog.setMessage("loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean)
                    Toast.makeText(HttpPostActivity.this, "登录成功!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(HttpPostActivity.this, "登录失败!", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(Boolean aBoolean) {
                super.onCancelled(aBoolean);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(userinfo);
    }

}

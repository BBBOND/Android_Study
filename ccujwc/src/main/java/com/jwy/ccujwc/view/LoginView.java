package com.jwy.ccujwc.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import com.jwy.ccujwc.R;
import com.jwy.ccujwc.service.LoginService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginView extends Activity {


    private EditText etusername;
    private EditText etpassword;
//    private CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        etusername = (EditText) findViewById(R.id.username);
        etpassword = (EditText) findViewById(R.id.password);
//        cb = (CheckBox) findViewById(R.id.checkbox);
        login();
    }

    /**
     * 登录操作
     */
    public void login() {
        String username = etusername.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //登录 发送消息到服务器，服务器返回验证信息
            if ("zhangsan".equals(username) && "123456".equals(password)) {
//                //判断是否保存密码
//                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
//                if (cb.isChecked()) {
//                    //保存
//                    boolean result = LoginService.saveUserInfo(this, username, password);
//                    if (!(result)) {
//                        Toast.makeText(this, "保存信息失败", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                setContentView(R.layout.main_view);
                CreateMainView();
            } else {
                Toast.makeText(this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                LoginService.saveUserInfo(this, username, " ");
            }
        }
    }


    public void CreateMainView() {
        Intent intent = new Intent();
        intent.setClass(LoginView.this, MainView.class);
        startActivity(intent);
    }
}
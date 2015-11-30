package com.jwy.dail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示界面
        setContentView(R.layout.activity_main);
        //找到按钮
        Button call = (Button)findViewById(R.id.call);
        //创建事件
        call.setOnClickListener(this);
    }

    //重写接口方法
    public void onClick(View v){
        switch (v.getId()){
            case R.id.call:callPhone();break;
            default:break;
        }
    }

    //拨打电话方法
    public void callPhone(){
        EditText numberText = (EditText)findViewById(R.id.numberText);
        String number = numberText.getText().toString().trim();
        if(number.isEmpty()){
            Toast.makeText(MainActivity.this,"号码不能为空",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
        }
    }
}

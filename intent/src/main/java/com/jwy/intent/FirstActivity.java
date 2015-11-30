package com.jwy.intent;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class FirstActivity extends Activity {

    private Button intentBtn;
    private Button staticsBtn;
    private Button clipboardBtn;
    private Button globalBtn;
    private TextView nameTv;
    private TextView ageTv;
    private String name;
    private String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first);

        intentBtn = (Button) findViewById(R.id.intent);
        staticsBtn = (Button) findViewById(R.id.statics);
        clipboardBtn = (Button) findViewById(R.id.clipboard);
        globalBtn = (Button) findViewById(R.id.global_variable);
        nameTv = (TextView) findViewById(R.id.name);
        ageTv = (TextView) findViewById(R.id.age);

        /**
         * 使用普通方法传值
         */
        intentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameTv.getText().toString();
                age = ageTv.getText().toString();

                Intent intent = new Intent(getApplicationContext(), IntentActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                startActivityForResult(intent, 1);
            }
        });

        /**
         * 使用静态变量
         */
        staticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameTv.getText().toString();
                age = ageTv.getText().toString();

                Intent intent = new Intent(getApplicationContext(), StaticsActivity.class);
                StaticsActivity.name = name;
                StaticsActivity.age = age;
                startActivity(intent);
            }
        });

        /**
         * 使用剪切板
         */
        clipboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameTv.getText().toString();
                age = ageTv.getText().toString();

                Intent intent = new Intent(getApplicationContext(), ClipboardActivity.class);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String text = "name:" + name + "\nage:" + age;
                clipboardManager.setText(text);
                startActivity(intent);
            }
        });

        /**
         * 使用全局变量
         */
        globalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameTv.getText().toString();
                age = ageTv.getText().toString();

                Intent intent = new Intent(getApplicationContext(), GlobalVariableActivity.class);
                Myapp myapp = (Myapp) getApplication();
                myapp.setName(name);
                myapp.setAge(age);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text = item.getTitle().toString();
        Toast.makeText(getApplicationContext(), "你点击了" + text, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 接收回传的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                nameTv.setText(data.getStringExtra("name"));
                ageTv.setText(data.getStringExtra("age"));
            }
        }
    }
}

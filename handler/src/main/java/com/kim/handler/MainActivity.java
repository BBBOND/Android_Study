package com.kim.handler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 入门级别的Handler的用法
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    private EditText imgURL;
    private Button imgBtn;
    private ImageView imgIV;
    private String URL_PATH;
    private final int IS_FINISH = 1;
    private ProgressDialog dialog = null;
    private Button sendMsg;
    private Button toSecond;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            byte[] data = (byte[]) msg.obj;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            imgIV.setImageBitmap(bitmap);
            if (msg.what == IS_FINISH) {
                dialog.dismiss();
            }
        }
    };

    private Handler sendMsgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int arg1 = msg.arg1;
            int arg2 = msg.arg2;
            int what = msg.what;
            Object result = msg.obj;
            String str = arg1 + "\n" + arg2 + "\n" + what + "\n" + result + "\n";

            Bundle bundle = msg.getData();
            if (bundle != null && bundle.getStringArray("str") != null) {
                String[] data = bundle.getStringArray("str");
                for (String s : data) {
                    str += s;
                }
            }
            imgURL.setText(str);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgURL = (EditText) findViewById(R.id.img_url);
        imgBtn = (Button) findViewById(R.id.img_show_btn);
        imgIV = (ImageView) findViewById(R.id.img_IV);
        sendMsg = (Button) findViewById(R.id.send_message);
        toSecond = (Button) findViewById(R.id.toSecond);

        dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("下载中...");
        dialog.setCancelable(false);

        imgBtn.setOnClickListener(this);
        sendMsg.setOnClickListener(this);
        toSecond.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_show_btn:
                String imgUrl = imgURL.getText().toString().trim();
                if (imgUrl.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入图片地址！", Toast.LENGTH_SHORT).show();
                } else {
                    URL_PATH = imgUrl;
                    new Thread(new MyThread()).start();
                    dialog.show();
                }
                break;
            case R.id.send_message:
                new Thread(new SendMsgThread()).start();
                break;
            case R.id.toSecond:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                break;
        }
    }

    public class MyThread implements Runnable {

        @Override
        public void run() {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(URL_PATH);
            HttpResponse response = null;
            try {
                response = client.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    byte[] data = EntityUtils.toByteArray(response.getEntity());
                    Message message = Message.obtain();
                    message.obj = data;
                    message.what = IS_FINISH;
                    handler.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class SendMsgThread implements Runnable {

        @Override
        public void run() {
//            -----------第一种方式---------
//            Message message = Message.obtain();
//            message.what = 1;
//            message.arg1 = 2;
//            message.arg2 = 3;
//            message.obj = "BBBOND";
//            sendMsgHandler.sendMessage(message);

//            -----------第二种方式---------
//            Message message = Message.obtain(sendMsgHandler);
//            message.what = 1;
//            message.arg1 = 2;
//            message.arg2 = 3;
//            message.obj = "BBBOND";
//            message.sendToTarget();

//            -----------第三种方式---------
//            Message message = Message.obtain(sendMsgHandler, 1, 2, 3, "BBBOND");
//            message.sendToTarget();

//            -----------第四种方式---------
            Message message = Message.obtain(sendMsgHandler, 1, 2, 3, "BBBOND");
            Bundle bundle = new Bundle();
            bundle.putStringArray("str", new String[]{"j", "w", "y"});
            message.setData(bundle);
            message.sendToTarget();
        }
    }
}

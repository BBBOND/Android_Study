package com.kim.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.widget.ToggleButton;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.TOP;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ToggleButton.OnCheckedChangeListener {

    private Button toast, addStatus;
    private ToggleButton status;
    private NotificationManager manager;
    private Notification.Builder builder;
    private static int id = 0;
    private static int id2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toast = (Button) findViewById(R.id.toast);
        addStatus = (Button) findViewById(R.id.add_status);
        status = (ToggleButton) findViewById(R.id.status);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        toast.setOnClickListener(this);
        addStatus.setOnClickListener(this);
        status.setOnCheckedChangeListener(this);

        setStatus();
    }

    public void setStatus() {
        if (status.isChecked()) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
            builder = new Notification.Builder(MainActivity.this);
            builder.setContentIntent(contentIntent);
            builder.setContentTitle("Notification Test");
            builder.setContentText("Hello world!");
            builder.setSmallIcon(R.drawable.abc_ic_clear_mtrl_alpha);
            builder.setOngoing(true);
            Notification notification = builder.build();
            manager.notify(id, notification);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toast:
                String str = "<font color='red'>Show Toast</font>";
                CharSequence text = Html.fromHtml(str);
                Toast t = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
                t.show();
                Toast t2 = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
                t2.setGravity(CENTER, 0, 0);
                t2.show();
                Toast t3 = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
                t3.setGravity(TOP, 0, 0);
                t3.show();
                break;
            case R.id.add_status:
//                可以自定义通知,通过
//                RemoteViews contentViews = new RemoteViews(getPackageName(), R.layout.xxx);
//                contentViews.setImageViewResource(R.id.xxx, R.drawable.xxx);
//                contentViews.setTextViewText(R.id.xxx, "自定义通知的标题");
//                contentViews.setTextViewText(R.id.xxx, "自定义通知的内容");
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
//                builder.setContentIntent(contentIntent);
//                builder.setContent(contentViews);
//                builder.setTicker("通知来了");
//                builder.setDefaults(Notification.DEFAULT_ALL);
//                Notification notification = builder.build();
//                manager.notify(id++, notification);
//                来实现

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                builder = new Notification.Builder(MainActivity.this);
                builder.setContentIntent(contentIntent);
                builder.setContentTitle("Notification Test");
                builder.setContentText("Hello world!");
                builder.setSmallIcon(R.drawable.abc_ic_clear_mtrl_alpha);
                builder.setTicker("通知来了");
                //所有提示都是默认
                builder.setDefaults(Notification.DEFAULT_ALL);
                Notification notification = builder.build();
                manager.notify(id2++, notification);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.status:
                if (isChecked) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                    builder = new Notification.Builder(MainActivity.this);
                    builder.setContentIntent(contentIntent);
                    builder.setContentTitle("Notification Test");
                    builder.setContentText("Hello world!");
                    builder.setSmallIcon(R.drawable.abc_ic_clear_mtrl_alpha);
                    builder.setOngoing(true);
                    Notification notification = builder.build();
                    manager.notify(id, notification);
                } else {
                    manager.cancel(id);
                }
                break;
        }
    }
}

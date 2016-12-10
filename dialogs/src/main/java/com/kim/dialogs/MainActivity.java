package com.kim.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private Button alert, alertItems, alertC, alertR, progress, custom, datePicker;
    private final CharSequence[] items = {"11", "22", "33", "44", "55"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alert = (Button) findViewById(R.id.alertDialogBtn);
        alertItems = (Button) findViewById(R.id.alertDialogItemsBtn);
        progress = (Button) findViewById(R.id.progressDialogBtn);
        datePicker = (Button) findViewById(R.id.datePickerDialogBtn);
        alertC = (Button) findViewById(R.id.alertDialogCheckboxesBtn);
        alertR = (Button) findViewById(R.id.alertDialogRadioBtn);
        alert.setOnClickListener(this);
        alertItems.setOnClickListener(this);
        alertC.setOnClickListener(this);
        alertR.setOnClickListener(this);
        progress.setOnClickListener(this);
        datePicker.setOnClickListener(this);
        datePicker.setOnLongClickListener(this);

        custom = (Button) findViewById(R.id.customDialogBtn);
        custom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alertDialogBtn:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定删除？");
                builder.setIcon(android.R.drawable.ic_dialog_dialer);
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.alertDialogItemsBtn:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setTitle("请选择");
                builder1.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You Click " + items[which], Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();
                break;
            case R.id.alertDialogCheckboxesBtn:
                final List<Integer> list = new ArrayList<>();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("请选择");
                builder2.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        list.add(which);
                    }
                });
                builder2.setNegativeButton("取消", null);
                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Collections.sort(list);
                        String str = "已选择：\n";
                        for (int i : list) {
                            str += items[i] + "\n";
                        }
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog2 = builder2.create();
                alertDialog2.show();
                break;
            case R.id.alertDialogRadioBtn:
                final Map<String, Integer> map = new HashMap<>();
                AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
                builder3.setTitle("请选择");
                builder3.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        map.put("select", which);
                    }
                });
                builder3.setNegativeButton("取消", null);
                builder3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int select = map.get("select");
                        Toast.makeText(MainActivity.this, "You select " + items[select], Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog3 = builder3.create();
                alertDialog3.show();
                break;
            case R.id.progressDialogBtn:
                final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("加载中");
                dialog.setCancelable(true);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        int i = 0;
                        while (i < 100) {
                            dialog.setProgress(i);
                            i += 5;
                            try {
                                sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        dialog.dismiss();
                        super.run();
                    }
                }.start();
                break;
            case R.id.customDialogBtn:
                //自定义dialog 通过编写一个类在类中通过layoutInflater实现对自定义界面的加载
                CustomDialog dialog1 = new CustomDialog(MainActivity.this);
                dialog1.show();
                break;
            case R.id.datePickerDialogBtn:
                int year, monthOfYear, dayOfMonth;
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                monthOfYear = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = year + "/" + monthOfYear + "" + dayOfMonth;
                        Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();
                    }
                }, year, monthOfYear, dayOfMonth);
                datePickerDialog.show();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.datePickerDialogBtn:
                int hour, minute;
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();
                    }
                }, hour, minute, true);
                timePickerDialog.show();
                return true;
        }
        return false;
    }
}

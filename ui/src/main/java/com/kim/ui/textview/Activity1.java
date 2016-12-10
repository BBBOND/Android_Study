package com.kim.ui.textview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by KIM on 2015/8/9.
 */
public class Activity1 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity1");
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity1.this);
        builder.setTitle("Activity1").setMessage("Activity1").setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }
}

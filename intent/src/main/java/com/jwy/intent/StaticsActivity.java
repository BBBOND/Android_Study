package com.jwy.intent;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by KIM on 2015/7/29.
 */
public class StaticsActivity extends Activity {

    public static String name;
    public static String age;
    private TextView otherTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.others);

        otherTv = (TextView) findViewById(R.id.others_tv);

        String text = "name:" + name + "\nage:" + age;

        otherTv.setText(text);
    }
}

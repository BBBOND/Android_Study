package com.jwy.intent;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by KIM on 2015/7/29.
 */
public class GlobalVariableActivity extends Activity {

    private TextView otherTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.others);

        otherTv = (TextView) findViewById(R.id.others_tv);

        Myapp myapp = (Myapp) getApplication();

        String text = "name:" + myapp.getName() + "\nage:" + myapp.getAge();

        otherTv.setText(text);
    }
}

package com.jwy.intent;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by KIM on 2015/7/29.
 */
public class ClipboardActivity extends Activity {

    private TextView otherTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.others);

        otherTv = (TextView) findViewById(R.id.others_tv);

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String text = clipboardManager.getText().toString();

        otherTv.setText(text);
    }
}

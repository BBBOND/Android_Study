package com.jwy.sms;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    EditText myNumberText;
    EditText myTextText;
    EditText mySuffix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myNumberText = (EditText) findViewById(R.id.numberText);
        myTextText = (EditText) findViewById(R.id.inputText);
        mySuffix = (EditText) findViewById(R.id.suffix);

        Button sent = (Button) findViewById(R.id.sent);
        sent.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sent:
                sentText();
                break;
        }
    }

    public void sentText() {
        String number = myNumberText.getText().toString().trim();
        String text = myTextText.getText().toString();
        String suffix = "\n" + mySuffix.getText().toString();
        if (!suffix.equals("————输入小尾巴")) {
            text += suffix;
        }
        if (number.isEmpty() || text.isEmpty()) {
            Toast.makeText(this, "号码或短信内容不能为空", Toast.LENGTH_LONG).show();
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, text, null, null);
        }
    }
}

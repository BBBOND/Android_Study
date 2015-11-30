package com.jwy.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by KIM on 2015/7/29.
 */
public class IntentActivity extends Activity {

    private EditText nameEt;
    private EditText ageEt;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.intent);

        nameEt = (EditText) findViewById(R.id.name_Et);
        ageEt = (EditText) findViewById(R.id.age_Et);
        back = (Button) findViewById(R.id.back);

        Intent intent = getIntent();

        nameEt.setText(intent.getStringExtra("name"));
        ageEt.setText(intent.getStringExtra("age"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEt.getText().toString();
                String age = ageEt.getText().toString();

                Intent data = new Intent(getApplicationContext(), FirstActivity.class);
                data.putExtra("name", name);
                data.putExtra("age", age);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}

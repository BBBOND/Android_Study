package com.kim.ui.edittext;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.kim.ui.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by KIM on 2015/8/10.
 */
public class EditTextActivity extends Activity implements View.OnClickListener {

    private EditText editText;
    private EditText num;
    private Button etButton;
    private Button button;
    private AutoCompleteTextView autotext;
    private MultiAutoCompleteTextView multitext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittext_main);

        editText = (EditText) findViewById(R.id.editText1);
        etButton = (Button) findViewById(R.id.Et_button);
        button = (Button) findViewById(R.id.btn);
        num = (EditText) findViewById(R.id.num);
        autotext = (AutoCompleteTextView) findViewById(R.id.autotext);
        multitext = (MultiAutoCompleteTextView) findViewById(R.id.multitext);

        List<String> list = new ArrayList<>();
        list.add("百度");
        list.add("谷歌");
        list.add("bing");
        list.add("雅虎");
        list.add("搜狗");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditTextActivity.this, android.R.layout.simple_dropdown_item_1line, list);
        autotext.setAdapter(adapter);

        multitext.setAdapter(adapter);
        multitext.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        etButton.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Et_button:
                //随机插入图片
                addImage();
                break;
            case R.id.btn:
                //检查输入
                check();
                break;
        }
    }

    private void addImage() {
        int randomId = 1 + new Random().nextInt(5);
        try {
            Field field = R.drawable.class.getDeclaredField("emo_" + randomId);
            int resourceId = Integer.parseInt(field.get(null).toString());
            //android中显示图片信息，要使用bitmap位图的对象来装载
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
            ImageSpan imageSpan = new ImageSpan(EditTextActivity.this, bitmap);
            SpannableString spannableString = new SpannableString("emo_" + randomId);
            spannableString.setSpan(imageSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            editText.append(spannableString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void check() {
        if (num.getText().toString().trim().equals("")) {
            num.setError("不能为空！");
        }
    }
}

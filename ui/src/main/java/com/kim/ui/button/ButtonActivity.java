package com.kim.ui.button;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kim.ui.R;

/**
 * Created by KIM on 2015/8/10.
 */
public class ButtonActivity extends Activity implements View.OnClickListener,
        View.OnTouchListener, View.OnFocusChangeListener, View.OnKeyListener, CompoundButton.OnCheckedChangeListener {

    private Button commonbutton;
    private Button imagebutton;
    private Button button1;
    private RadioGroup group;
    private Button sexBtn;
    private ToggleButton tBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_main);

        commonbutton = (Button) findViewById(R.id.commonbutton);
        imagebutton = (Button) findViewById(R.id.imagebutton);
        button1 = (Button) findViewById(R.id.button1);
        group = (RadioGroup) findViewById(R.id.sex_RG);
        sexBtn = (Button) findViewById(R.id.sex_Btn);
        tBtn = (ToggleButton) findViewById(R.id.TBtn);

        commonbutton.setOnClickListener(this);
        imagebutton.setOnTouchListener(this);
        imagebutton.setOnFocusChangeListener(this);
        imagebutton.setOnKeyListener(this);
        tBtn.setOnCheckedChangeListener(this);

        SpannableString spannableStringLeft = new SpannableString("left");
        Bitmap bitmapLeft = BitmapFactory.decodeResource(getResources(), R.drawable.emo_1);
        ImageSpan imageSpanLeft = new ImageSpan(bitmapLeft, DynamicDrawableSpan.ALIGN_BOTTOM);
        spannableStringLeft.setSpan(imageSpanLeft, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString spannableStringRight = new SpannableString("right");
        Bitmap bitmapRight = BitmapFactory.decodeResource(getResources(), R.drawable.emo_2);
        ImageSpan imageSpanRight = new ImageSpan(bitmapRight, DynamicDrawableSpan.ALIGN_BOTTOM);
        spannableStringRight.setSpan(imageSpanRight, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        button1.append(spannableStringLeft);
        button1.append("我的按钮");
        button1.append(spannableStringRight);

        sexBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commonbutton:
                //点击事件
                Button button = (Button) v;
                if (button.getWidth() > 300) {
                    button.setWidth((int) (v.getWidth() * 0.9));
                } else {
                    button.setWidth(getWindowManager().getDefaultDisplay().getWidth());
                }
                break;
            case R.id.sex_Btn:
                int len = group.getChildCount();//获得选项个数
                String msg = "";
                for (int i = 0; i < len; i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if (radioButton.isChecked()) {
                        msg = radioButton.getText().toString();
                        break;
                    }
                }
                Toast.makeText(ButtonActivity.this, msg, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (KeyEvent.ACTION_DOWN == event.getAction()) {
            //下方向键点击事件
            v.setBackgroundResource(R.drawable.emo_2);
        } else if (KeyEvent.ACTION_UP == event.getAction()) {
            //上方向键点击事件
            v.setBackgroundResource(R.drawable.emo_1);
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            //获取焦点事件
            v.setBackgroundResource(R.drawable.emo_2);
        } else {
            //失去焦点事件
            v.setBackgroundResource(R.drawable.emo_1);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //触摸，手指离开事件
            v.setBackgroundResource(R.drawable.emo_1);
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //触摸，手机放下事件
            v.setBackgroundResource(R.drawable.emo_2);
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(ButtonActivity.this, "打开", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ButtonActivity.this, "关闭", Toast.LENGTH_SHORT).show();
        }
    }
}

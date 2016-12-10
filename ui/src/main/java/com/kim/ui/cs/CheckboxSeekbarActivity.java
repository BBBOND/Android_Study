package com.kim.ui.cs;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.kim.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KIM on 2015/8/12.
 */
public class CheckboxSeekbarActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    List<CheckBox> checkBoxes = new ArrayList<>();
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] checkboxText = new String[]{"学生？", "男生？", "会编程吗？"};

        //动态加载布局
        LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.checkbox_seekbar, null);

        for (int i = 0; i < checkboxText.length; i++) {
            CheckBox checkBox = (CheckBox) getLayoutInflater().inflate(R.layout.checkbox, null);
            checkBoxes.add(checkBox);
            checkBoxes.get(i).setText(checkboxText[i]);
            linearLayout.addView(checkBox, i);
        }
        SeekBar seekBar = (SeekBar) getLayoutInflater().inflate(R.layout.seekbar, null);
        linearLayout.addView(seekBar);
        setContentView(linearLayout);
        findViewById(R.id.checkboxBtn).setOnClickListener(this);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkboxBtn:
                String s = "";
                for (CheckBox checkBox : checkBoxes) {
                    if (checkBox.isChecked()) {
                        s += checkBox.getText().toString() + "\n";
                    }
                }
                if (s.equals("")) {
                    s = "无选中项！";
                }
                new AlertDialog.Builder(this).setMessage(s).setNegativeButton("关闭", null).show();
                break;
        }
    }

    //滑动进度条
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.seekbar) {
            if (seekBar.getSecondaryProgress() <= 100) {
                seekBar.setSecondaryProgress(progress + 30);
            } else {
                seekBar.setSecondaryProgress(100);
            }
        }

    }

    //从哪开始滑动进度条
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == R.id.seekbar) {
            Toast.makeText(CheckboxSeekbarActivity.this, "开始拖动！", Toast.LENGTH_SHORT).show();
        }
    }

    //从哪开始结束拖动
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == R.id.seekbar) {
            Toast.makeText(CheckboxSeekbarActivity.this, "结束拖动！", Toast.LENGTH_SHORT).show();
        }
    }
}

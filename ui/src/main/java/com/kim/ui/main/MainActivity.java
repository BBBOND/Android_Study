package com.kim.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kim.ui.R;
import com.kim.ui.button.ButtonActivity;
import com.kim.ui.cs.CheckboxSeekbarActivity;
import com.kim.ui.dateandother.DateActivity;
import com.kim.ui.edittext.EditTextActivity;
import com.kim.ui.gallery.GalleryActivity;
import com.kim.ui.imageview.ImageViewActivity;
import com.kim.ui.textview.TextViewActivity;

/**
 * Created by KIM on 2015/8/9.
 */
public class MainActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.textViewBtn).setOnClickListener(this);
        findViewById(R.id.editTextBtn).setOnClickListener(this);
        findViewById(R.id.buttonBtn).setOnClickListener(this);
        findViewById(R.id.CSBtn).setOnClickListener(this);
        findViewById(R.id.imageviewBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.textViewBtn:
                intent.setClass(MainActivity.this, TextViewActivity.class);
                break;
            case R.id.editTextBtn:
                intent.setClass(MainActivity.this, EditTextActivity.class);
                break;
            case R.id.buttonBtn:
                intent.setClass(MainActivity.this, ButtonActivity.class);
                break;
            case R.id.CSBtn:
                intent.setClass(MainActivity.this, CheckboxSeekbarActivity.class);
                break;
            case R.id.imageviewBtn:
                intent.setClass(MainActivity.this, ImageViewActivity.class);
                break;
            case R.id.date:
                intent.setClass(MainActivity.this, DateActivity.class);
                break;
            case R.id.galleryBtn:
                intent.setClass(MainActivity.this, GalleryActivity.class);
                break;
        }
        startActivity(intent);
    }
}
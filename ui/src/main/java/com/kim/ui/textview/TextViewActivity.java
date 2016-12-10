package com.kim.ui.textview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.kim.ui.R;

import java.lang.reflect.Field;

public class TextViewActivity extends Activity {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);

        /*------TextView中可以添加html标签,包括超链接(tv1,tv2)------*/
        textView_One();

        /*------TextView中显示表情和图片(tv3)------*/
        textView_Two();

        /*------TextView单击链接弹出Activity(tv4)------*/
        textView_Three();

        /*------TextView实现跑马灯------*/
        textView_Four();
    }

    public void textView_One() {
        String html = "<font color='red'>I Love You</font><br>";
        html += "<font color='#0000ff'><big><i>I Love You</i></big></font><br>";
        html += "<big><a href='http://www.baidu.com'>百度</a></big>";
        CharSequence charSequence = Html.fromHtml(html);
        tv1.setText(charSequence);
        //点击产生超链接
        tv1.setMovementMethod(LinkMovementMethod.getInstance());

        // 使用autolink自动检测
        String text = "我的URL:http://www.baidu.com\n";
        text += "我的email:jwy8645@163.com\n";
        text += "我的电话:+86 13003789494";
        tv2.setText(text);
        tv2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void textView_Two() {
        tv3.setTextColor(Color.BLACK);
        tv3.setBackgroundColor(Color.WHITE);
        tv3.setTextSize(20);
        String html = "图像1<img src='emo_1'/>图像2<img src='emo_2'/>图像3<img src='emo_3'/><p>";
        html += "图像4<a href='http://www.baidu.com'><img src='emo_4'/></a>图像5<img src='emo_5'/>";
        CharSequence charSequence = Html.fromHtml(html, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                //getResources()获取资源信息
                //getDrawable()获取Drawable资源
                Drawable drawable = getResources().getDrawable(getResoureceId(source));
                if (drawable != null) {
                    //处理第三张图片文件，压缩成10%的比例，其余的压缩成20%
                    if (source.equals("emo_3")) {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 10, drawable.getIntrinsicHeight() / 10);
                    } else {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 5, drawable.getIntrinsicHeight() / 5);
                    }
                    return drawable;
                } else
                    return null;
            }
        }, null);
        tv3.setText(charSequence);
        tv3.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public int getResoureceId(String name) {
        try {
            //根据资源的ID的变量名获取Field的对象，使用反射机制
            Field field = R.drawable.class.getField(name);
            //取得并返回资源的ID的字段（静态变量）的值，使用反射机制
            return Integer.parseInt(field.get(null).toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void textView_Three() {
        String text1 = "跳转到Activity1";
        String text2 = "跳转到Activity2";
        SpannableString spannableString1 = new SpannableString(text1);
        SpannableString spannableString2 = new SpannableString(text2);
        spannableString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(TextViewActivity.this, Activity1.class);
                startActivity(intent);
            }
        }, 3, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(TextViewActivity.this, Activity2.class);
                startActivity(intent);
            }
        }, 3, text2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv4.setText(spannableString1);
        tv5.setText(spannableString2);
        tv4.setMovementMethod(LinkMovementMethod.getInstance());
        tv5.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void textView_Four() {
        String html = "哈嘻哈嘻哈嘻哈哈嘻哈哈嘻嘻哈嘻哈嘻哈嘻哈嘻哈哈哈哈哈哈哈哈哈哈哈哈哈哈嘻哈";
        CharSequence charSequence = Html.fromHtml(html);
        tv6.setText(charSequence);
    }
}

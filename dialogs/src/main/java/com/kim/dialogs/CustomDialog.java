package com.kim.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by KIM on 2015/8/27.
 */
public class CustomDialog {
    private Context context;
    private Dialog dialog;

    public CustomDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
    }

    public void show() {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        dialog.setContentView(view);
        dialog.setTitle("自定义");
        TextView textView = (TextView) view.findViewById(R.id.dialogMess);
        ImageView imageView = (ImageView) view.findViewById(R.id.dialogIc);
        imageView.setImageResource(android.R.drawable.btn_star);
        textView.setText("loading...");
        textView.setTextColor(Color.BLACK);
        dialog.show();
    }
}

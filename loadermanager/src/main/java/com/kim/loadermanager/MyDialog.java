package com.kim.loadermanager;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by KIM on 2015/8/29.
 */
public class MyDialog {

    private Context context;
    private Dialog dialog;
    private String id, name;


    public MyDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void show() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_context, null);
        dialog.setContentView(view);
        dialog.setTitle("修改");
        TextView id_dialog = (TextView) view.findViewById(R.id.id_dialog);
        final EditText name_dialog = (EditText) view.findViewById(R.id.name_dialog);
        id_dialog.setText(id);
        name_dialog.setHint(name);
        dialog.show();
    }
}

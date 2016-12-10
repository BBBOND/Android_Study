package com.kim.handler;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.URL;

/**
 * Created by KIM on 2015/8/31.
 */
public class DownLoadImage {

    private String image_path;

    public DownLoadImage(String image_path) {
        this.image_path = image_path;
    }

    public void loagImage(final ImageCallBack callBack) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                callBack.getDrawable((Drawable) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Drawable drawable = Drawable.createFromStream(new URL(image_path).openStream(), "");
                    Message message = Message.obtain();
                    message.obj = drawable;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //接口的回调
    public interface ImageCallBack {
        void getDrawable(Drawable drawable);
    }
}

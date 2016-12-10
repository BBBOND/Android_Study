package com.kim.save.save;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件流方式保存
 * Created by KIM on 2015/8/17.
 */
public class FileSave {

    private Context context;

    public FileSave(Context context) {
        this.context = context;
    }

    public String readFromSDCard(String fileName) {
        FileInputStream fileInputStream = null;
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        //缓冲区的流，与硬盘无关，无需关闭
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            try {
                fileInputStream = new FileInputStream(file);
                int len = 0;
                byte[] bytes = new byte[1024];
                while ((len = fileInputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new String(outputStream.toByteArray());
    }

    /**
     * 保存在SdCard中  理论上永久有效
     *
     * @param fileName 文件名
     * @param content  文件内容
     * @return 是否保存成功
     */
    public boolean saveToSDCard(String fileName, String content) {
        boolean flag = false;

        FileOutputStream fileOutputStream = null;
        //获得SD所在路径
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        //判断SD卡是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            try {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(content.getBytes());
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return flag;
    }
}

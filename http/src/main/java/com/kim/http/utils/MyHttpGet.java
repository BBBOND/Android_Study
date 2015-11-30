package com.kim.http.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KIM on 2015/8/2.
 */
public class MyHttpGet {

    private String URL_PATH = "";

    public MyHttpGet(String urlpath) {
        URL_PATH = urlpath;
    }


    public InputStream getInputStream() {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        URL url = null;
        try {
            url = new URL(URL_PATH);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置连接网络的超时时间
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            //设置为GET方式请求
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            //从服务器获取输入流
            if (responseCode == 200)
                inputStream = httpURLConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public byte[] readInputStream() {
        InputStream inputStream = getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] data = new byte[1024];
        int len = 0;
        try {
            if (inputStream != null) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return byteArrayOutputStream != null ? byteArrayOutputStream.toByteArray() : null;
    }
}

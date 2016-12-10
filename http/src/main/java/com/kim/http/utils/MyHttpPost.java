package com.kim.http.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KIM on 2015/8/3.
 */
public class MyHttpPost {
    private String URL_PATH = "";
    private Userinfo userinfo;

    public MyHttpPost(String URL_PATH, Userinfo userinfo) {
        this.URL_PATH = URL_PATH;
        this.userinfo = userinfo;
    }

    private InputStream getInputStream() {
        InputStream inputStream = null;
        try {
            URL url = new URL(URL_PATH);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("POST");

            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("username=" + userinfo.getUserName() + "&password=" + userinfo.getUserPassword());
            bw.flush();

            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public boolean checkLogin() {
        InputStream inputStream = getInputStream();
        InputStreamReader isReader;
        StringBuffer stringBuffer = null;
        BufferedReader bReader = null;
        try {
            isReader = new InputStreamReader(inputStream, "utf-8");
            bReader = new BufferedReader(isReader);
            stringBuffer = new StringBuffer();
            String len;
            while ((len = bReader.readLine()) != null) {
                stringBuffer.append(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (stringBuffer != null && stringBuffer.toString().equals("success"));
    }
}

package com.kim.json.http;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KIM on 2015/8/7.
 */
public class GsonHttpUtils {
    private static String URL_PATH = "http://192.168.1.105:8888/AndroidHttpTest/gsonServlet";

    private static InputStream getInputStream(int key) {
        if (key <= 0 || key > 4)
            return null;
        InputStream inputStream = null;
        try {
            URL url = new URL(URL_PATH);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.getDoOutput();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(3000);

            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            BufferedWriter writer = new BufferedWriter(osw);
            writer.write("key=" + key);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public static String changeInputStreamToJsonString(int key) {
        InputStream inputStream = getInputStream(key);
        if (inputStream == null) return null;
        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        try {
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            jsonString = new String(outputStream.toByteArray());
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}

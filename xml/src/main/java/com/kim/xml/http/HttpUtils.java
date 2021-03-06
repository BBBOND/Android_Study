package com.kim.xml.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KIM on 2015/8/6.
 */
public class HttpUtils {

    public HttpUtils() {
    }

    public static InputStream getXML(String path) {
        InputStream inputStream = null;

        try {
            URL url = new URL(path);
            if (url != null) {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                int code = connection.getResponseCode();
                if (code == 200) {
                    inputStream = connection.getInputStream();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}

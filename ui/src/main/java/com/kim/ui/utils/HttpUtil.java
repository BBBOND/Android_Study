package com.kim.ui.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KIM on 2015/8/14.
 */
public class HttpUtil {

    public static InputStream getInputStream(String urlPath) throws Exception {
        InputStream inputStream = null;
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3000);
        connection.setDoInput(true);
        if (connection.getResponseCode() == 200) {
            inputStream = connection.getInputStream();
        }
        return inputStream;
    }
}

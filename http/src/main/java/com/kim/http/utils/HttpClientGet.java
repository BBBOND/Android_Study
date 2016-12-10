package com.kim.http.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by KIM on 2015/8/3.
 */
public class HttpClientGet {

    private String URL_PATH = "";

    public HttpClientGet(String urlpath) {
        URL_PATH = urlpath;
    }

    public byte[] readInputStream() {

        byte[] data = null;
        HttpClient client = new DefaultHttpClient();

        //httpGet
        HttpGet httpGet = new HttpGet(URL_PATH);

        try {
            HttpResponse response = client.execute(httpGet);
            data = EntityUtils.toByteArray(response.getEntity());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
package com.kim.http.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KIM on 2015/8/3.
 */
public class HttpClientPost {
    private String URL_PATH = "";
    private Userinfo userinfo;

    public HttpClientPost(String URL_PATH, Userinfo userinfo) {
        this.URL_PATH = URL_PATH;
        this.userinfo = userinfo;
    }

    public boolean checkLogin() {
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_PATH);
        String value = "";

        try {
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
            list.add(new BasicNameValuePair("username", userinfo.getUserName()));
            list.add(new BasicNameValuePair("password", userinfo.getUserPassword()));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse response = client.execute(httpPost);
            value = EntityUtils.toString(response.getEntity(), "utf-8").trim();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return value.equals("success");
    }
}

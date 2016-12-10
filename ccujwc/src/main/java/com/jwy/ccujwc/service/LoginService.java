package com.jwy.ccujwc.service;

import android.content.Context;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KIM on 2015/1/3.
 */
public class LoginService {
    public static boolean saveUserInfo(Context context,String username, String password){
        try {
            File file = new File(context.getFilesDir(),"info.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write((username+"&&"+password).getBytes());
            fos.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //获取保存的数据
    public static Map<String,String> getSaveUserInfo(Context context){
        File file = new File(context.getFilesDir(), "info.txt");
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String str = br.readLine();
            String[] info = str.split("&&");
            Map<String, String> map = new HashMap<String,String>();
            map.put("username",info[0]);
            map.put("password",info[1]);
            return map;
        }catch (Exception e){
            return null;
        }
    }
}
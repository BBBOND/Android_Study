package com.kim.save.save;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * 常用于保存登陆信息等
 * Created by KIM on 2015/8/18.
 */
public class MySharedPreference {

    private Context context;

    public MySharedPreference(Context context) {
        this.context = context;
    }

    /**
     * 保存在/data/data/包名/
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否保存成功
     */
    public boolean save(String name, String username, String password) {
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        //对数据进行编辑
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        flag = editor.commit();//将数据持久化到存储介质中
        return flag;
    }

    public Map<String, Object> read(String name) {
        Map<String, Object> map = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        map.put("username", username);
        map.put("password", password);
        return map;
    }
}

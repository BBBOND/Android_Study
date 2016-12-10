package com.kim.json.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by KIM on 2015/8/6.
 */
public class GsonUtils {

    public static <T> T getPerson(String jsonString, Class<T> cla) {
        T t = null;

        Gson gson = new Gson();
        t = gson.fromJson(jsonString, cla);

        return t;
    }

    public static <T> List<T> getPersons(String jsonString, Class<T> cla) {
        List<T> list = new ArrayList<T>();

        Gson gson = new Gson();
        //用gson解析List<T>中的T的类型
        list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
        }.getType());

        return list;
    }

    public static List<String> getStringList(String jsonString) {
        List<String> list = new ArrayList<>();
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
        }.getType());
        return list;
    }

    public static List<Map<String, Object>> getMapList(String jsonString) {
        List<Map<String, Object>> mapList = new ArrayList<>();

        Gson gson = new Gson();
        mapList = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
        }.getType());

        return mapList;
    }
}
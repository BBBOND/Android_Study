package com.kim.json.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 读取的是GSON格式的
 * Created by KIM on 2015/8/6.
 */
public class FastjsonUtils {

    public static <T> T getPerson(String jsonString, Class<T> cla) {
        T t = null;

        t = JSON.parseObject(jsonString, cla);

        return t;
    }

    public static <T> List<T> getPersons(String jsonString, Class<T> cla) {
        List<T> list = new ArrayList<T>();

        list = JSON.parseArray(jsonString, cla);

        return list;
    }

    public static List<String> getStringList(String jsonString) {
        List<String> list = new ArrayList<>();

        list = JSON.parseObject(jsonString, new TypeReference<List<String>>() {
        }.getType());

        return list;
    }

    public static List<Map<String, Object>> getMapList(String jsonString) {
        List<Map<String, Object>> mapList = new ArrayList<>();

        mapList = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {
        }.getType());

        return mapList;
    }
}

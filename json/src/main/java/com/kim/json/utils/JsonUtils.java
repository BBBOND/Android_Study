package com.kim.json.utils;

import com.kim.json.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by KIM on 2015/8/6.
 */
public class JsonUtils {

    public static Person getPerson(String key, String jsonString) {
        Person person = new Person();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject personObject = jsonObject.getJSONObject(key);
            person = parseJsonToPerson(personObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }

    public static List<Person> getPersons(String key, String jsonString) {
        List<Person> persons = new ArrayList<>();
        try {
            JSONObject jsonList = new JSONObject(jsonString);
            JSONArray personArray = jsonList.getJSONArray(key);
            for (int i = 0; i < personArray.length(); i++) {
                persons.add(parseJsonToPerson((JSONObject) personArray.get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public static List<String> getStringList(String key, String jsonString) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray stringArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < stringArray.length(); i++) {
                list.add(stringArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Map<String, Object>> getMapList(String key, String jsonString) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Map<String, Object> map = new HashMap<>();
                Iterator<String> iterator = object.keys();
                while (iterator.hasNext()) {
                    String json_key = iterator.next();
                    Object json_value = object.get(json_key);
                    if (json_value == null) {
                        json_value = "";
                    }
                    map.put(json_key, json_value);
                }
                mapList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mapList;
    }

    public static Person parseJsonToPerson(JSONObject personObject) {
        Person person = new Person();
        try {
            person.setId(personObject.getInt("id"));
            person.setName(personObject.getString("name"));
            person.setAge(personObject.getInt("age"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }
}

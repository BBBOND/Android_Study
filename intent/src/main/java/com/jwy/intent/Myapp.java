package com.jwy.intent;

import android.app.Application;

/**
 * Created by KIM on 2015/7/29.
 */
public class Myapp extends Application {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Myapp{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

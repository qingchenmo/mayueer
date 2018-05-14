package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class AppServerBean {


    /**
     * id : 2
     * phone : 0755-8512345
     * area : 0
     * times : 1512762448000
     * onlinetime : 10:00-18:00
     * onlinecycle : 周一至周五
     */

    private int id;
    private String phone;
    private int area;
    private long times;
    private String onlinetime;
    private String onlinecycle;

    public static List<AppServerBean> arrayAppServerBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<AppServerBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public String getOnlinetime() {
        return onlinetime;
    }

    public void setOnlinetime(String onlinetime) {
        this.onlinetime = onlinetime;
    }

    public String getOnlinecycle() {
        return onlinecycle;
    }

    public void setOnlinecycle(String onlinecycle) {
        this.onlinecycle = onlinecycle;
    }
}

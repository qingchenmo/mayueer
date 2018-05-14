package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29 0029.
 */

public class CityBean {

    /**
     * provincecode : 2
     * countrycode : 10001
     * countryname : 中国
     * provincename : 北京市
     */

    private String provincecode;
    private String countrycode;
    private String countryname;
    private String provincename;

    public static List<CityBean> arrayCityBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<CityBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }
}

package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29 0029.
 */

public class CountrBean {


    /**
     * provincecode : null
     * countrycode : 10002
     * countryname : ANDORRA
     * provincename : null
     */

    private Object provincecode;
    private String countrycode;
    private String countryname;
    private Object provincename;

    public static List<CountrBean> arrayCountrBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<CountrBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public Object getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(Object provincecode) {
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

    public Object getProvincename() {
        return provincename;
    }

    public void setProvincename(Object provincename) {
        this.provincename = provincename;
    }
}

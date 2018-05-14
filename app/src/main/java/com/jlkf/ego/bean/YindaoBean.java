package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1 0001.
 */

public class YindaoBean {


    /**
     * pId : null
     * pName : 2
     * pLogo : http://120.25.237.198:8380//images//adal//201708301679654.png
     * pTime : null
     * pType : null
     */

    private Object pId;
    private String pName;
    private String pLogo;
    private Object pTime;
    private Object pType;

    public static List<YindaoBean> arrayYindaoBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<YindaoBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public Object getPId() {
        return pId;
    }

    public void setPId(Object pId) {
        this.pId = pId;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPLogo() {
        return pLogo;
    }

    public void setPLogo(String pLogo) {
        this.pLogo = pLogo;
    }

    public Object getPTime() {
        return pTime;
    }

    public void setPTime(Object pTime) {
        this.pTime = pTime;
    }

    public Object getPType() {
        return pType;
    }

    public void setPType(Object pType) {
        this.pType = pType;
    }
}

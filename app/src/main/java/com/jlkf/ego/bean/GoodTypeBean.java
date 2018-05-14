package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品类型实体类
 * Created by super on 2017-07-07.
 */
public class GoodTypeBean {

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int drawable;


    /**
     * itemgroupId : null
     * area : null
     * itmsgrpcod : 209
     * itmsgrpnam : 包材类
     */

    private int itemgroupId;
    private String area;
    private String itmsgrpcod;
    private String itmsgrpnam;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    private String picture;

    public static List<GoodTypeBean> arrayGoodTypeBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<GoodTypeBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getItemgroupId() {
        return itemgroupId;
    }

    public void setItemgroupId(int itemgroupId) {
        this.itemgroupId = itemgroupId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getItmsgrpcod() {
        return itmsgrpcod;
    }

    public void setItmsgrpcod(String itmsgrpcod) {
        this.itmsgrpcod = itmsgrpcod;
    }

    public String getItmsgrpnam() {
        return itmsgrpnam;
    }

    public void setItmsgrpnam(String itmsgrpnam) {
        this.itmsgrpnam = itmsgrpnam;
    }
}

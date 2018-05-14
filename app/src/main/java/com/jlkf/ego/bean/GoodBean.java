package com.jlkf.ego.bean;

/**
 * 商品实体类
 * Created by Administrator on 2017-07-07.
 */
public class GoodBean {
    public String name;
    public String picUrl;
    public int goodId;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }
}

package com.jlkf.ego.bean;

import java.util.List;

/**
 * 品牌实体类
 * Created by super on 2017-07-07.
 */
public class BrandBean {
    public int id;
    public String name;
    public String imageUrl;
    public String firstWord;
    public List<BrandBean> list;

    public List<BrandBean> getList() {
        return list;
    }

    public void setList(List<BrandBean> list) {
        this.list = list;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

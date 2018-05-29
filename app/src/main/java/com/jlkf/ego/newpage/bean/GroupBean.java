package com.jlkf.ego.newpage.bean;

/**
 * Created by zcs on 2018/5/23.
 */

public class GroupBean {

    /**
     * itemGroup_id : 1
     * ItmsGrpCod : 101
     * ItmsGrpNam : 充电器2
     * picture : http://files.hi-ego.com/img/chongdianqi3.png
     */

    private int itemGroup_id;
    private String ItmsGrpCod;
    private String ItmsGrpNam;
    private String picture;
    private String mark;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getItemGroup_id() {
        return itemGroup_id;
    }

    public void setItemGroup_id(int itemGroup_id) {
        this.itemGroup_id = itemGroup_id;
    }

    public String getItmsGrpCod() {
        return ItmsGrpCod;
    }

    public void setItmsGrpCod(String ItmsGrpCod) {
        this.ItmsGrpCod = ItmsGrpCod;
    }

    public String getItmsGrpNam() {
        return ItmsGrpNam;
    }

    public void setItmsGrpNam(String ItmsGrpNam) {
        this.ItmsGrpNam = ItmsGrpNam;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

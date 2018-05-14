package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class BrandGoodsBean {


    /**
     * oitmId : null
     * area : null
     * itemcode : 1010004
     * codebars : null
     * itemname : 车载充电器-VIP-CC03-1A MICRO USB-黑色
     * itmsgrpcod : null
     * spec : null
     * minlevel : null
     * onhand : 12469
     * uColors : null
     * uXyms : null
     * uUB : 12
     * uUX : 120
     * uPp : null
     * picturname : http://52.57.221.27:8898/8433772130040-1.jpg
     * uIssale : null
     * createdate : null
     * updatedate : null
     * price : 1.83
     * currency : null
     * monthSale : null
     * isColler : null
     * areaId : null
     */

    private Object oitmId;
    private Object area;
    private String itemcode;
    private Object codebars;
    private String itemname;
    private Object itmsgrpcod;
    private Object spec;
    private Object minlevel;
    private String onhand;
    private Object uColors;
    private Object uXyms;
    private String uUB;
    private String uUX;
    private Object uPp;
    private String picturname;
    private Object uIssale;
    private Object createdate;
    private Object updatedate;
    private String price;
    private Object currency;
    private Object monthSale;
    private Object isColler;
    private Object areaId;

    public static List<BrandGoodsBean> arrayBrandGoodsBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<BrandGoodsBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public Object getOitmId() {
        return oitmId;
    }

    public void setOitmId(Object oitmId) {
        this.oitmId = oitmId;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public Object getCodebars() {
        return codebars;
    }

    public void setCodebars(Object codebars) {
        this.codebars = codebars;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Object getItmsgrpcod() {
        return itmsgrpcod;
    }

    public void setItmsgrpcod(Object itmsgrpcod) {
        this.itmsgrpcod = itmsgrpcod;
    }

    public Object getSpec() {
        return spec;
    }

    public void setSpec(Object spec) {
        this.spec = spec;
    }

    public Object getMinlevel() {
        return minlevel;
    }

    public void setMinlevel(Object minlevel) {
        this.minlevel = minlevel;
    }

    public String getOnhand() {
        return onhand;
    }

    public void setOnhand(String onhand) {
        this.onhand = onhand;
    }

    public Object getUColors() {
        return uColors;
    }

    public void setUColors(Object uColors) {
        this.uColors = uColors;
    }

    public Object getUXyms() {
        return uXyms;
    }

    public void setUXyms(Object uXyms) {
        this.uXyms = uXyms;
    }

    public String getUUB() {
        return uUB;
    }

    public void setUUB(String uUB) {
        this.uUB = uUB;
    }

    public String getUUX() {
        return uUX;
    }

    public void setUUX(String uUX) {
        this.uUX = uUX;
    }

    public Object getUPp() {
        return uPp;
    }

    public void setUPp(Object uPp) {
        this.uPp = uPp;
    }

    public String getPicturname() {
        return picturname;
    }

    public void setPicturname(String picturname) {
        this.picturname = picturname;
    }

    public Object getUIssale() {
        return uIssale;
    }

    public void setUIssale(Object uIssale) {
        this.uIssale = uIssale;
    }

    public Object getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Object createdate) {
        this.createdate = createdate;
    }

    public Object getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Object updatedate) {
        this.updatedate = updatedate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    public Object getMonthSale() {
        return monthSale;
    }

    public void setMonthSale(Object monthSale) {
        this.monthSale = monthSale;
    }

    public Object getIsColler() {
        return isColler;
    }

    public void setIsColler(Object isColler) {
        this.isColler = isColler;
    }

    public Object getAreaId() {
        return areaId;
    }

    public void setAreaId(Object areaId) {
        this.areaId = areaId;
    }
}

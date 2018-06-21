package com.jlkf.ego.bean;

/**
 * @autor zcs
 * @time 2017/8/31
 * @describe
 */

public class ShopCarGoodsBean {
    private String goodsCode;
    private int num;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

package com.jlkf.ego.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class YunfeiBean {


    /**
     * msg : 还需3.904即可免费配送
     * finallyTotal : 6.98128
     * code : 200
     * freight : 10.0
     */

    private String msg;
    private double finallyTotal;
    private int code;
    private double freight;

    public static YunfeiBean objectFromData(String str) {

        return new Gson().fromJson(str, YunfeiBean.class);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getFinallyTotal() {
        return finallyTotal;
    }

    public void setFinallyTotal(double finallyTotal) {
        this.finallyTotal = finallyTotal;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }
}

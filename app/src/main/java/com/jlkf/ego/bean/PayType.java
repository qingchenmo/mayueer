package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class PayType {


    /**
     * uPayId : 1
     * uPayName : 现金支付
     * uPayType : 0
     * uPayNo : 10002
     * uPayPayee : 无
     */

    private int uPayId;
    private String uPayName;
    private String uPayType;
    private String uPayNo;
    private String uPayPayee;
    private String uPayName1;
    private String uPayName2;
    private String uPayName3;
    private String uPayName4;
    private String uPayName5;

    public String getuPayName1() {
        return uPayName1;
    }

    public void setuPayName1(String uPayName1) {
        this.uPayName1 = uPayName1;
    }

    public String getuPayName2() {
        return uPayName2;
    }

    public void setuPayName2(String uPayName2) {
        this.uPayName2 = uPayName2;
    }

    public String getuPayName3() {
        return uPayName3;
    }

    public void setuPayName3(String uPayName3) {
        this.uPayName3 = uPayName3;
    }

    public String getuPayName4() {
        return uPayName4;
    }

    public void setuPayName4(String uPayName4) {
        this.uPayName4 = uPayName4;
    }

    public String getuPayName5() {
        return uPayName5;
    }

    public void setuPayName5(String uPayName5) {
        this.uPayName5 = uPayName5;
    }

    public static List<PayType> arrayPayTypeFromData(String str) {

        Type listType = new TypeToken<ArrayList<PayType>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getUPayId() {
        return uPayId;
    }

    public void setUPayId(int uPayId) {
        this.uPayId = uPayId;
    }

    public String getUPayName() {
        return uPayName;
    }

    public void setUPayName(String uPayName) {
        this.uPayName = uPayName;
    }

    public String getUPayType() {
        return uPayType;
    }

    public void setUPayType(String uPayType) {
        this.uPayType = uPayType;
    }

    public String getUPayNo() {
        return uPayNo;
    }

    public void setUPayNo(String uPayNo) {
        this.uPayNo = uPayNo;
    }

    public String getUPayPayee() {
        return uPayPayee;
    }

    public void setUPayPayee(String uPayPayee) {
        this.uPayPayee = uPayPayee;
    }
}

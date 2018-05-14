package com.jlkf.ego.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/8/21 0021.
 * <p>
 * 登录后返回的userbean
 */

public class UserBean {


    /**
     * uId : 4
     * uName : 8613071010127
     * uLogo : null
     * uSex : null
     * uBirthday : null
     * uMobile : 8613071010127
     * uPhone : null
     * uSignature : null
     * uPassword : null
     * uWechat : null
     * uWechatName : null
     * uTwitter : null
     * uTwitterName : null
     * uFacebook : null
     * uFacebookName : null
     * uCreationtime : 1503283597000
     * uType : null
     * code : null
     * bindType : null
     * uUserdiscount : null
     * uUserdiscountname : null
     * uaLocationName : null
     * uaLocationId : null
     * cmId : null
     * sapNo : null
     * cmName : null
     * promissoryPay : null
     * msRemarks : null
     * clerk : null
     */

    private int uId;
    private String uName;
    private String uLogo;
    private Object uSex;
    private Object uBirthday;
    private String uMobile;
    private String uPhone;
    private String uSignature;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String country;
    private String uPassword;
    private Object uWechat;
    private Object uWechatName;
    private Object uTwitter;
    private Object uTwitterName;
    private Object uFacebook;
    private Object uFacebookName;
    private long uCreationtime;
    private int uType;

    public boolean isCookie() {
        return cookie;
    }

    public void setCookie(boolean cookie) {
        this.cookie = cookie;
    }

    private boolean cookie;

    public String getuTypeName() {
        return uTypeName;
    }

    public void setuTypeName(String uTypeName) {
        this.uTypeName = uTypeName;
    }

    private String uTypeName;
    private Object code;
    private Object bindType;
    private Object uUserdiscount;
    private Object uUserdiscountname;
    private Object uaLocationName;
    private Object uaLocationId;
    private Object cmId;
    private Object sapNo;
    private Object cmName;
    private Object promissoryPay;
    private Object msRemarks;
    private Object clerk;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    private String areaCode;   // 区号

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private String area;   // 区域

    public static UserBean objectFromData(String str) {

        return new Gson().fromJson(str, UserBean.class);
    }

    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getULogo() {
        return uLogo;
    }

    public void setULogo(String uLogo) {
        this.uLogo = uLogo;
    }

    public Object getUSex() {
        return uSex;
    }

    public void setUSex(Object uSex) {
        this.uSex = uSex;
    }

    public Object getUBirthday() {
        return uBirthday;
    }

    public void setUBirthday(Object uBirthday) {
        this.uBirthday = uBirthday;
    }

    public String getUMobile() {
        return uMobile;
    }

    public void setUMobile(String uMobile) {
        this.uMobile = uMobile;
    }

    public String getUPhone() {
        return uPhone;
    }

    public void setUPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getUSignature() {
        return uSignature;
    }

    public void setUSignature(String uSignature) {
        this.uSignature = uSignature;
    }

    public String getUPassword() {
        return uPassword;
    }

    public void setUPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public Object getUWechat() {
        return uWechat;
    }

    public void setUWechat(Object uWechat) {
        this.uWechat = uWechat;
    }

    public Object getUWechatName() {
        return uWechatName;
    }

    public void setUWechatName(Object uWechatName) {
        this.uWechatName = uWechatName;
    }

    public Object getUTwitter() {
        return uTwitter;
    }

    public void setUTwitter(Object uTwitter) {
        this.uTwitter = uTwitter;
    }

    public Object getUTwitterName() {
        return uTwitterName;
    }

    public void setUTwitterName(Object uTwitterName) {
        this.uTwitterName = uTwitterName;
    }

    public Object getUFacebook() {
        return uFacebook;
    }

    public void setUFacebook(Object uFacebook) {
        this.uFacebook = uFacebook;
    }

    public Object getUFacebookName() {
        return uFacebookName;
    }

    public void setUFacebookName(Object uFacebookName) {
        this.uFacebookName = uFacebookName;
    }

    public long getUCreationtime() {
        return uCreationtime;
    }

    public void setUCreationtime(long uCreationtime) {
        this.uCreationtime = uCreationtime;
    }

    public int getUType() {
        return uType;
    }

    public void setUType(int uType) {
        this.uType = uType;
        if (uType == 0) {
            setArea("spain");
        } else {
            setArea("Italy");
        }
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getBindType() {
        return bindType;
    }

    public void setBindType(Object bindType) {
        this.bindType = bindType;
    }

    public Object getUUserdiscount() {
        return uUserdiscount;
    }

    public void setUUserdiscount(Object uUserdiscount) {
        this.uUserdiscount = uUserdiscount;
    }

    public Object getUUserdiscountname() {
        return uUserdiscountname;
    }

    public void setUUserdiscountname(Object uUserdiscountname) {
        this.uUserdiscountname = uUserdiscountname;
    }

    public Object getUaLocationName() {
        return uaLocationName;
    }

    public void setUaLocationName(Object uaLocationName) {
        this.uaLocationName = uaLocationName;
    }

    public Object getUaLocationId() {
        return uaLocationId;
    }

    public void setUaLocationId(Object uaLocationId) {
        this.uaLocationId = uaLocationId;
    }

    public Object getCmId() {
        return cmId;
    }

    public void setCmId(Object cmId) {
        this.cmId = cmId;
    }

    public Object getSapNo() {
        return sapNo;
    }

    public void setSapNo(Object sapNo) {
        this.sapNo = sapNo;
    }

    public Object getCmName() {
        return cmName;
    }

    public void setCmName(Object cmName) {
        this.cmName = cmName;
    }

    public Object getPromissoryPay() {
        return promissoryPay;
    }

    public void setPromissoryPay(Object promissoryPay) {
        this.promissoryPay = promissoryPay;
    }

    public Object getMsRemarks() {
        return msRemarks;
    }

    public void setMsRemarks(Object msRemarks) {
        this.msRemarks = msRemarks;
    }

    public Object getClerk() {
        return clerk;
    }

    public void setClerk(Object clerk) {
        this.clerk = clerk;
    }

}

package com.jlkf.ego.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class UserInfo {


    /**
     * uId : 4
     * uName : 8613071010127
     * uLogo : null
     * uSex : null
     * uBirthday : null
     * uMobile : 8613071010127
     * uPhone : null
     * uSignature : null
     * uPassword : 25f9e794323b453885f5181f1b624d0b
     * uWechat : null
     * uWechatName : null
     * uTwitter : null
     * uTwitterName : null
     * uFacebook : null
     * uFacebookName : null
     * uCreationtime : null
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
    private int uSex;
    private String uBirthday;
    private String uMobile;
    private String uPhone;
    private String uSignature;
    private String uPassword;
    private String uWechat;
    private String uWechatName;
    private String uTwitter;
    private String uTwitterName;
    private String uFacebook;
    private String uFacebookName;
    private String uCreationtime;
    private String uType;
    private String code;
    private String bindType;
    private String uUserdiscount;
    private String uUserdiscountname;
    private String uaLocationName;
    private String uaLocationId;
    private String cmId;
    private String sapNo;
    private String cmName;
    private String promissoryPay;
    private String msRemarks;
    private String clerk;

    public static UserInfo objectFromData(String str) {

        return new Gson().fromJson(str, UserInfo.class);
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

    public int getUSex() {
        return uSex;
    }

    public void setUSex(int uSex) {
        this.uSex = uSex;
    }

    public String getUBirthday() {
        return uBirthday;
    }

    public void setUBirthday(String uBirthday) {
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

    public String getUWechat() {
        return uWechat;
    }

    public void setUWechat(String uWechat) {
        this.uWechat = uWechat;
    }

    public String getUWechatName() {
        return uWechatName;
    }

    public void setUWechatName(String uWechatName) {
        this.uWechatName = uWechatName;
    }

    public String getUTwitter() {
        return uTwitter;
    }

    public void setUTwitter(String uTwitter) {
        this.uTwitter = uTwitter;
    }

    public String getUTwitterName() {
        return uTwitterName;
    }

    public void setUTwitterName(String uTwitterName) {
        this.uTwitterName = uTwitterName;
    }

    public String getUFacebook() {
        return uFacebook;
    }

    public void setUFacebook(String uFacebook) {
        this.uFacebook = uFacebook;
    }

    public String getUFacebookName() {
        return uFacebookName;
    }

    public void setUFacebookName(String uFacebookName) {
        this.uFacebookName = uFacebookName;
    }

    public String getUCreationtime() {
        return uCreationtime;
    }

    public void setUCreationtime(String uCreationtime) {
        this.uCreationtime = uCreationtime;
    }

    public String getUType() {
        return uType;
    }

    public void setUType(String uType) {
        this.uType = uType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public String getUUserdiscount() {
        return uUserdiscount;
    }

    public void setUUserdiscount(String uUserdiscount) {
        this.uUserdiscount = uUserdiscount;
    }

    public String getUUserdiscountname() {
        return uUserdiscountname;
    }

    public void setUUserdiscountname(String uUserdiscountname) {
        this.uUserdiscountname = uUserdiscountname;
    }

    public String getUaLocationName() {
        return uaLocationName;
    }

    public void setUaLocationName(String uaLocationName) {
        this.uaLocationName = uaLocationName;
    }

    public String getUaLocationId() {
        return uaLocationId;
    }

    public void setUaLocationId(String uaLocationId) {
        this.uaLocationId = uaLocationId;
    }

    public String getCmId() {
        return cmId;
    }

    public void setCmId(String cmId) {
        this.cmId = cmId;
    }

    public String getSapNo() {
        return sapNo;
    }

    public void setSapNo(String sapNo) {
        this.sapNo = sapNo;
    }

    public String getCmName() {
        return cmName;
    }

    public void setCmName(String cmName) {
        this.cmName = cmName;
    }

    public String getPromissoryPay() {
        return promissoryPay;
    }

    public void setPromissoryPay(String promissoryPay) {
        this.promissoryPay = promissoryPay;
    }

    public String getMsRemarks() {
        return msRemarks;
    }

    public void setMsRemarks(String msRemarks) {
        this.msRemarks = msRemarks;
    }

    public String getClerk() {
        return clerk;
    }

    public void setClerk(String clerk) {
        this.clerk = clerk;
    }
}

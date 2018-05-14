package com.jlkf.ego.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class AdressBean implements Serializable {

    public String getUaId() {
        return uaId;
    }

    public void setUaId(String uaId) {
        this.uaId = uaId;
    }

    private String uaId;
    private String uaSurname;
    private String uaName;
    private int uaSex;
    private String uaContactphone;
    private String uaLandline;
    private String uaCompanyname;
    private String uaShopname;
    private String uaCountriesName;
    private String uaCountries;
    private String uaProvincialName;
    private String uaProvincial;
    private String uaCityName;
    private String uaCity;
    private String uaDelivery;
    private int uaUinvoice;
    private String uaInvoice;
    private String uaZipcode;
    private String uaEin;
    private String uaBusinesstime;
    private String uaDeliverytime;
    private int uaPaytype;
    private int upShoptype;
    private String upShoparea;
    private int upTargetuser;
    private String upSwanseason;
    private String upLightseason;
    private String upMainproducts;
    private String upDescr;

    public int getUaLocationId() {
        return uaLocationId;
    }

    public void setUaLocationId(int uaLocationId) {
        this.uaLocationId = uaLocationId;
    }

    private int uaLocationId ;

    @Override
    public String toString() {
        return "AdressBean{" +
                "uaId='" + uaId + '\'' +
                ", uaSurname='" + uaSurname + '\'' +
                ", uaName='" + uaName + '\'' +
                ", uaSex=" + uaSex +
                ", uaContactphone='" + uaContactphone + '\'' +
                ", uaLandline='" + uaLandline + '\'' +
                ", uaCompanyname='" + uaCompanyname + '\'' +
                ", uaShopname='" + uaShopname + '\'' +
                ", uaCountriesName='" + uaCountriesName + '\'' +
                ", uaCountries='" + uaCountries + '\'' +
                ", uaProvincialName='" + uaProvincialName + '\'' +
                ", uaProvincial='" + uaProvincial + '\'' +
                ", uaCityName='" + uaCityName + '\'' +
                ", uaCity='" + uaCity + '\'' +
                ", uaDelivery='" + uaDelivery + '\'' +
                ", uaUinvoice='" + uaUinvoice + '\'' +
                ", uaInvoice='" + uaInvoice + '\'' +
                ", uaZipcode='" + uaZipcode + '\'' +
                ", uaEin='" + uaEin + '\'' +
                ", uaBusinesstime=" + uaBusinesstime +
                ", uaDeliverytime=" + uaDeliverytime +
                ", uaPaytype=" + uaPaytype +
                ", upShoptype=" + upShoptype +
                ", upShoparea='" + upShoparea + '\'' +
                ", upTargetuser=" + upTargetuser +
                ", upSwanseason=" + upSwanseason +
                ", upLightseason=" + upLightseason +
                ", upMainproducts='" + upMainproducts + '\'' +
                ", upDescr='" + upDescr + '\'' +
                ", upLogo='" + upLogo + '\'' +
                ", upDefault=" + upDefault +
                ", uId='" + uId + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    private String upLogo;
    private int upDefault = 0; // 默认设置为不是默认地址
    private String uId;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    private String position;


    public String getUaSurname() {
        return uaSurname;
    }

    public void setUaSurname(String uaSurname) {
        this.uaSurname = uaSurname;
    }

    public String getUaName() {
        return uaName;
    }

    public void setUaName(String uaName) {
        this.uaName = uaName;
    }

    public int getUaSex() {
        return uaSex;
    }

    public void setUaSex(int uaSex) {
        this.uaSex = uaSex;
    }

    public String getUaContactphone() {
        return uaContactphone;
    }

    public void setUaContactphone(String uaContactphone) {
        this.uaContactphone = uaContactphone;
    }

    public String getUaLandline() {
        return uaLandline;
    }

    public void setUaLandline(String uaLandline) {
        this.uaLandline = uaLandline;
    }

    public String getUaCompanyname() {
        return uaCompanyname;
    }

    public void setUaCompanyname(String uaCompanyname) {
        this.uaCompanyname = uaCompanyname;
    }

    public String getUaShopname() {
        return uaShopname;
    }

    public void setUaShopname(String uaShopname) {
        this.uaShopname = uaShopname;
    }

    public String getUaCountriesName() {
        return uaCountriesName;
    }

    public void setUaCountriesName(String uaCountriesName) {
        this.uaCountriesName = uaCountriesName;
    }

    public String getUaCountries() {
        return uaCountries;
    }

    public void setUaCountries(String uaCountries) {
        this.uaCountries = uaCountries;
    }

    public String getUaProvincialName() {
        return uaProvincialName;
    }

    public void setUaProvincialName(String uaProvincialName) {
        this.uaProvincialName = uaProvincialName;
    }

    public String getUaProvincial() {
        return uaProvincial;
    }

    public void setUaProvincial(String uaProvincial) {
        this.uaProvincial = uaProvincial;
    }

    public String getUaCityName() {
        return uaCityName;
    }

    public void setUaCityName(String uaCityName) {
        this.uaCityName = uaCityName;
    }

    public String getUaCity() {
        return uaCity;
    }

    public void setUaCity(String uaCity) {
        this.uaCity = uaCity;
    }

    public String getUaDelivery() {
        return uaDelivery;
    }

    public void setUaDelivery(String uaDelivery) {
        this.uaDelivery = uaDelivery;
    }

    public int getUaUinvoice() {
        return uaUinvoice;
    }

    public void setUaUinvoice(int uaUinvoice) {
        this.uaUinvoice = uaUinvoice;
    }

    public String getUaInvoice() {
        return uaInvoice;
    }

    public void setUaInvoice(String uaInvoice) {
        this.uaInvoice = uaInvoice;
    }

    public String getUaZipcode() {
        return uaZipcode;
    }

    public void setUaZipcode(String uaZipcode) {
        this.uaZipcode = uaZipcode;
    }

    public String getUaEin() {
        return uaEin;
    }

    public void setUaEin(String uaEin) {
        this.uaEin = uaEin;
    }

    public String getUaBusinesstime() {
        return uaBusinesstime;
    }

    public void setUaBusinesstime(String uaBusinesstime) {
        this.uaBusinesstime = uaBusinesstime;
    }

    public String getUaDeliverytime() {
        return uaDeliverytime;
    }

    public void setUaDeliverytime(String uaDeliverytime) {
        this.uaDeliverytime = uaDeliverytime;
    }

    public int getUaPaytype() {
        return uaPaytype;
    }

    public void setUaPaytype(int uaPaytype) {
        this.uaPaytype = uaPaytype;
    }

    public int getUpShoptype() {
        return upShoptype;
    }

    public void setUpShoptype(int upShoptype) {
        this.upShoptype = upShoptype;
    }

    public String getUpShoparea() {
        return upShoparea;
    }

    public void setUpShoparea(String upShoparea) {
        this.upShoparea = upShoparea;
    }

    public int getUpTargetuser() {
        return upTargetuser;
    }

    public void setUpTargetuser(int upTargetuser) {
        this.upTargetuser = upTargetuser;
    }

    public String getUpSwanseason() {
        return upSwanseason;
    }

    public void setUpSwanseason(String upSwanseason) {
        this.upSwanseason = upSwanseason;
    }

    public String getUpLightseason() {
        return upLightseason;
    }

    public void setUpLightseason(String upLightseason) {
        this.upLightseason = upLightseason;
    }

    public String getUpMainproducts() {
        return upMainproducts;
    }

    public void setUpMainproducts(String upMainproducts) {
        this.upMainproducts = upMainproducts;
    }

    public String getUpDescr() {
        return upDescr;
    }

    public void setUpDescr(String upDescr) {
        this.upDescr = upDescr;
    }

    public String getUpLogo() {
        return upLogo;
    }

    public void setUpLogo(String upLogo) {
        this.upLogo = upLogo;
    }

    public int getUpDefault() {
        return upDefault;
    }

    public void setUpDefault(int upDefault) {
        this.upDefault = upDefault;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}

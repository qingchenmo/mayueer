package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1 0001.
 */

public class Connection2 {


    /**
     * rId : 29
     * relyid : null
     * type : null
     * areatype : null
     * area1 : null
     * itemcode1 : null
     * time : null
     * oitmId : null
     * area : spain
     * itemcode : 1109901
     * codebars : 8433772651583
     * itemname : MHL Tech PACK
     * itmsgrpcod : null
     * spec : null
     * minlevel : null
     * onhand : 0
     * uColors : null
     * uXyms : MHL Tech PACK
     * uUB : 10
     * uUX : 100
     * uPp : 002
     * picturname : http://52.29.220.117:8898/8433772651583.jpg
     * uIssale : null
     * createdate : null
     * updatedate : null
     * price : null
     * currency : null
     * attachment1 : null
     * attachment2 : null
     * attachment3 : null
     * attachment4 : null
     * attachment5 : null
     * attachment6 : null
     * userText : null
     * salesVolume : null
     * price2 : null
     * price3 : null
     * price4 : null
     * price5 : null
     * price6 : null
     * price7 : null
     * price8 : null
     * price9 : null
     * price10 : null
     * uCPZ : null
     */

    private int rId;
    private Object relyid;
    private Object type;
    private Object areatype;
    private Object area1;
    private Object itemcode1;
    private Object time;
    private Object oitmId;
    private String area;
    private String itemcode;
    private String codebars;
    private String itemname;
    private Object itmsgrpcod;
    private Object spec;
    private Object minlevel;
    private int onhand;
    private Object uColors;
    private String uXyms;
    private String uUB;
    private String uUX;
    private String uPp;
    private String picturname;
    private Object uIssale;
    private Object createdate;
    private Object updatedate;
    private double price;
    private Object currency;
    private Object attachment1;
    private Object attachment2;
    private Object attachment3;
    private Object attachment4;
    private Object attachment5;
    private Object attachment6;
    private Object userText;
    private Object salesVolume;
    private Object price2;
    private Object price3;
    private Object price4;
    private Object price5;
    private Object price6;
    private Object price7;
    private Object price8;
    private Object price9;
    private Object price10;
    private Object uCPZ = 0;

    public int getIsBig() {
        return isBig;
    }

    public void setIsBig(int isBig) {
        this.isBig = isBig;
    }

    private int isBig;

    public static List<Connection2> arrayConnection2FromData(String str) {

        Type listType = new TypeToken<ArrayList<Connection2>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getRId() {
        return rId;
    }

    public void setRId(int rId) {
        this.rId = rId;
    }

    public Object getRelyid() {
        return relyid;
    }

    public void setRelyid(Object relyid) {
        this.relyid = relyid;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getAreatype() {
        return areatype;
    }

    public void setAreatype(Object areatype) {
        this.areatype = areatype;
    }

    public Object getArea1() {
        return area1;
    }

    public void setArea1(Object area1) {
        this.area1 = area1;
    }

    public Object getItemcode1() {
        return itemcode1;
    }

    public void setItemcode1(Object itemcode1) {
        this.itemcode1 = itemcode1;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public Object getOitmId() {
        return oitmId;
    }

    public void setOitmId(Object oitmId) {
        this.oitmId = oitmId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getCodebars() {
        return codebars;
    }

    public void setCodebars(String codebars) {
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

    public int getOnhand() {
        return onhand;
    }

    public void setOnhand(int onhand) {
        this.onhand = onhand;
    }

    public Object getUColors() {
        return uColors;
    }

    public void setUColors(Object uColors) {
        this.uColors = uColors;
    }

    public String getUXyms() {
        return uXyms;
    }

    public void setUXyms(String uXyms) {
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

    public String getUPp() {
        return uPp;
    }

    public void setUPp(String uPp) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    public Object getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(Object attachment1) {
        this.attachment1 = attachment1;
    }

    public Object getAttachment2() {
        return attachment2;
    }

    public void setAttachment2(Object attachment2) {
        this.attachment2 = attachment2;
    }

    public Object getAttachment3() {
        return attachment3;
    }

    public void setAttachment3(Object attachment3) {
        this.attachment3 = attachment3;
    }

    public Object getAttachment4() {
        return attachment4;
    }

    public void setAttachment4(Object attachment4) {
        this.attachment4 = attachment4;
    }

    public Object getAttachment5() {
        return attachment5;
    }

    public void setAttachment5(Object attachment5) {
        this.attachment5 = attachment5;
    }

    public Object getAttachment6() {
        return attachment6;
    }

    public void setAttachment6(Object attachment6) {
        this.attachment6 = attachment6;
    }

    public Object getUserText() {
        return userText;
    }

    public void setUserText(Object userText) {
        this.userText = userText;
    }

    public Object getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Object salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Object getPrice2() {
        return price2;
    }

    public void setPrice2(Object price2) {
        this.price2 = price2;
    }

    public Object getPrice3() {
        return price3;
    }

    public void setPrice3(Object price3) {
        this.price3 = price3;
    }

    public Object getPrice4() {
        return price4;
    }

    public void setPrice4(Object price4) {
        this.price4 = price4;
    }

    public Object getPrice5() {
        return price5;
    }

    public void setPrice5(Object price5) {
        this.price5 = price5;
    }

    public Object getPrice6() {
        return price6;
    }

    public void setPrice6(Object price6) {
        this.price6 = price6;
    }

    public Object getPrice7() {
        return price7;
    }

    public void setPrice7(Object price7) {
        this.price7 = price7;
    }

    public Object getPrice8() {
        return price8;
    }

    public void setPrice8(Object price8) {
        this.price8 = price8;
    }

    public Object getPrice9() {
        return price9;
    }

    public void setPrice9(Object price9) {
        this.price9 = price9;
    }

    public Object getPrice10() {
        return price10;
    }

    public void setPrice10(Object price10) {
        this.price10 = price10;
    }

    public Object getUCPZ() {
        return uCPZ;
    }

    public void setUCPZ(Object uCPZ) {
        this.uCPZ = uCPZ;
    }
}

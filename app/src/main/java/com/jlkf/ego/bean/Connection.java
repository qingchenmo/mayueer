package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1 0001.
 */

public class Connection {


    /**
     * id : 20
     * itemcode : 1100001
     * uId : null
     * optime : null
     * areaId : null
     * oitmView : {"oitmId":null,"area":null,"itemcode":"1100001","codebars":null,"itemname":"普通线-MICRO （17*0.1*4C 注塑)-YELLOW","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"106","uColors":null,"uXyms":null,"uUB":"12","uUX":"120","uPp":null,"picturname":"http://52.57.221.27:8898/8433772144146-1.jpg","uIssale":"N","createdate":null,"updatedate":null,"price":"0.75","currency":null,"attachment1":null,"attachment2":null,"attachment3":null,"attachment4":null,"attachment5":null,"attachment6":null,"pName":null,"monthSale":null,"isColler":null,"areaId":null}
     */

    private int id;
    private String itemcode;
    private Object uId;
    private Object optime;
    private Object areaId;
    private OitmViewBean oitmView;

    public int getIsBig() {
        return isBig;
    }

    public void setIsBig(int isBig) {
        this.isBig = isBig;
    }

    private int isBig = 0;


    public static List<Connection> arrayTestBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<Connection>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public Object getUId() {
        return uId;
    }

    public void setUId(Object uId) {
        this.uId = uId;
    }

    public Object getOptime() {
        return optime;
    }

    public void setOptime(Object optime) {
        this.optime = optime;
    }

    public Object getAreaId() {
        return areaId;
    }

    public void setAreaId(Object areaId) {
        this.areaId = areaId;
    }

    public OitmViewBean getOitmView() {
        return oitmView;
    }

    public void setOitmView(OitmViewBean oitmView) {
        this.oitmView = oitmView;
    }

    public static class OitmViewBean {
        /**
         * oitmId : null
         * area : null
         * itemcode : 1100001
         * codebars : null
         * itemname : 普通线-MICRO （17*0.1*4C 注塑)-YELLOW
         * itmsgrpcod : null
         * spec : null
         * minlevel : null
         * onhand : 106
         * uColors : null
         * uXyms : null
         * uUB : 12
         * uUX : 120
         * uPp : null
         * picturname : http://52.57.221.27:8898/8433772144146-1.jpg
         * uIssale : N
         * createdate : null
         * updatedate : null
         * price : 0.75
         * currency : null
         * attachment1 : null
         * attachment2 : null
         * attachment3 : null
         * attachment4 : null
         * attachment5 : null
         * attachment6 : null
         * pName : null
         * monthSale : null
         * isColler : null
         * areaId : null
         */

        private Object oitmId;
        private Object area;
        private String itemcode;
        private String codebars;
        private String itemname;
        private Object itmsgrpcod;
        private Object spec;
        private Object minlevel;
        private int onhand = 0;
        private Object uColors;
        private Object uXyms;
        private String uUB;
        private String uUX;
        private String uPp;
        private String picturname;
        private String uIssale;
        private Object createdate;
        private Object updatedate;
        private String price;
        private String currency;

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        private int isShow;
        private Object attachment1;
        private Object attachment2;
        private Object attachment3;
        private Object attachment4;
        private Object attachment5;
        private Object attachment6;


        private Object userText;
        private int salesVolume;
        private Object price2;
        private Object price3;
        private Object price4;
        private Object price5;
        private Object price6;
        private Object price7;
        private Object price8;
        private Object price9;
        private Object price10;
        private Object uCPZ;
        private String pName;
        private Object monthSale;
        private Object isColler;
        private Object areaId;


        public int getShopCount() {
            return shopCount;
        }

        public void setShopCount(int shopCount) {
            this.shopCount = shopCount;
        }

        private int shopCount;

        public static List<OitmViewBean> arrayOitmViewBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<OitmViewBean>>() {
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

        public String getUIssale() {
            return uIssale;
        }

        public void setUIssale(String uIssale) {
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
            try {
                Double.valueOf(price);
            } catch (Exception e) {
                price = "0";
            }
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
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

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
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
}

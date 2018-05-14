package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22 0022.
 * <p>
 * 商品
 */

public class GoodsBean  implements Serializable {


    /**
     * shopcart : [{"sId":1,"name":"123","price":123,"logo":"123123","itemcode":"123","quantity":123,"codebars":"123","brandname":"环境保护不","brandId":"1223","uId":6}]
     * brandData : {"brand_id":"1223"}
     */

    private BrandDataBean brandData;
    private List<ShopcartBean> shopcart;
    private boolean isChecked;
    private int largePackage, smallPackage;


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private double total;

    public int getLargePackage() {
        return largePackage;
    }

    public void setLargePackage(int largePackage) {
        this.largePackage = largePackage;
    }

    public int getSmallPackage() {
        return smallPackage;
    }

    public void setSmallPackage(int smallPackage) {
        this.smallPackage = smallPackage;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public static List<GoodsBean> arrayGoodsBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<GoodsBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public BrandDataBean getBrandData() {
        return brandData;
    }

    public void setBrandData(BrandDataBean brandData) {
        this.brandData = brandData;
    }

    public List<ShopcartBean> getShopcart() {
        return shopcart;
    }

    public void setShopcart(List<ShopcartBean> shopcart) {
        this.shopcart = shopcart;
    }

    public static class BrandDataBean implements Serializable {
        /**
         * brand_id : 1223
         */

        private String brand_id;
        private String Name;
        private String pp_minlogo;

        public String getPp_context() {
            return pp_context;
        }

        public void setPp_context(String pp_context) {
            this.pp_context = pp_context;
        }

        private String pp_context;

        public String getPp_maxlogo() {
            return pp_maxlogo;
        }

        public void setPp_maxlogo(String pp_maxlogo) {
            this.pp_maxlogo = pp_maxlogo;
        }

        public String getPp_minlogo() {
            return pp_minlogo;
        }

        public void setPp_minlogo(String pp_minlogo) {
            this.pp_minlogo = pp_minlogo;
        }

        private String pp_maxlogo;


        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String area) {
            Area = area;
        }

        private String Area;

        public static List<BrandDataBean> arrayBrandDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<BrandDataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }
    }

    public static class ShopcartBean implements Serializable{
        /**
         * sId : 1
         * name : 123
         * price : 123
         * logo : 123123
         * itemcode : 123
         * quantity : 123
         * codebars : 123
         * brandname : 环境保护不
         * brandId : 1223
         * uId : 6
         */

        private int sId;
        private String name;
        private double price;
        private String logo;
        private String itemcode;
        private int quantity;
        private String codebars;
        private String brandname;
        private String brandId;
        private int uId;

        public int getBaseposition() {
            return baseposition;
        }

        public void setBaseposition(int baseposition) {
            this.baseposition = baseposition;
        }

        private int baseposition;

        public int getVulae() {
            return vulae;
        }

        public void setVulae(int vulae) {
            this.vulae = vulae;
        }

        private int vulae;

        public List<OitmViewBean> getOitmView() {
            return oitmView;
        }

        public void setOitmView(List<OitmViewBean> oitmView) {
            this.oitmView = oitmView;
        }

        private List<OitmViewBean> oitmView;



        public int getIsSamll() {
            return isSamll;
        }

        public void setIsSamll(int isSamll) {
            this.isSamll = isSamll;
        }

        private int isSamll = 1 ;


        private boolean isChecked = false;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }




        public boolean isEditType() {
            return isEditType;
        }

        public void setEditType(boolean editType) {
            isEditType = editType;
        }

        private boolean isEditType =false ;

        public static List<ShopcartBean> arrayShopcartBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ShopcartBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public int getSId() {
            return sId;
        }

        public void setSId(int sId) {
            this.sId = sId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getItemcode() {
            return itemcode;
        }

        public void setItemcode(String itemcode) {
            this.itemcode = itemcode;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getCodebars() {
            return codebars;
        }

        public void setCodebars(String codebars) {
            this.codebars = codebars;
        }

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public int getUId() {
            return uId;
        }

        public void setUId(int uId) {
            this.uId = uId;
        }
    }

    public static class OitmViewBean implements Serializable {
        /**
         * oitmId : 49208
         * area : italy
         * itemcode : 1100237
         * codebars : 7081269764189
         * itemname : 一分二3.5MMJACK音频转接线
         * itmsgrpcod : 111
         * spec : null
         * minlevel : 0
         * onhand : 454
         * uColors : null
         * uXyms : null
         * uUB : 12.000000
         * uUX : 120.000000
         * uPp : 3
         * picturname : http://52.57.221.27:8898/764189.jpg
         * uIssale : N
         * createdate : 1488902400000
         * updatedate : 1494950400000
         * price : 0.52
         * currency : null
         * attachment1 : null
         * attachment2 : null
         * attachment3 : null
         * attachment4 : null
         * attachment5 : null
         * attachment6 : null
         * userText : null
         * pName : null
         * monthSale : null
         * isColler : null
         * areaId : null
         * shopCount : null
         */

        private int oitmId;
        private String area;
        private String itemcode;
        private String codebars;
        private String itemname;
        private String itmsgrpcod;
        private Object spec;
        private String minlevel;
        private int onhand;
        private Object uColors;
        private Object uXyms;
        private String uUB = "0";
        private String uUX = "0";
        private String uPp;
        private String picturname;
        private String uIssale;
        private long createdate;
        private long updatedate;
        private String price;
        private Object currency;
        private Object attachment1;
        private Object attachment2;
        private Object attachment3;
        private Object attachment4;
        private Object attachment5;
        private Object attachment6;
        private Object userText;
        private Object pName;
        private Object monthSale;
        private Object isColler;
        private Object areaId;
        private Object shopCount;

        public static List<OitmViewBean> arrayOitmViewBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<OitmViewBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public int getOitmId() {
            return oitmId;
        }

        public void setOitmId(int oitmId) {
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

        public String getItmsgrpcod() {
            return itmsgrpcod;
        }

        public void setItmsgrpcod(String itmsgrpcod) {
            this.itmsgrpcod = itmsgrpcod;
        }

        public Object getSpec() {
            return spec;
        }

        public void setSpec(Object spec) {
            this.spec = spec;
        }

        public String getMinlevel() {
            return minlevel;
        }

        public void setMinlevel(String minlevel) {
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

        public long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(long createdate) {
            this.createdate = createdate;
        }

        public long getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(long updatedate) {
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

        public Object getPName() {
            return pName;
        }

        public void setPName(Object pName) {
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

        public Object getShopCount() {
            return shopCount;
        }

        public void setShopCount(Object shopCount) {
            this.shopCount = shopCount;
        }
    }
}


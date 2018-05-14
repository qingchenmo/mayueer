package com.jlkf.ego.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @autor zcs
 * @time 2017/8/19
 * @describe
 */

public class ProductListBean extends MyBaseBean {

    /**
     * mData : [{"oitmId":null,"area":null,"itemcode":"1030162","codebars":null,"itemname":"荔枝纹-7寸-黑色（OME）","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"0","uColors":null,"uXyms":null,"uUB":"1","uUX":"40","uPp":null,"picturname":"http://52.57.221.27:8898/110066.jpg","uIssale":null,"createdate":null,"updatedate":null,"price":"4.85","currency":null,"monthSale":null,"isColler":null,"areaId":null},{"oitmId":null,"area":null,"itemcode":"1030163","codebars":null,"itemname":"荔枝纹-7寸-红色（OME）","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"173","uColors":null,"uXyms":null,"uUB":"1","uUX":"40","uPp":null,"picturname":"http://52.57.221.27:8898/110073.jpg","uIssale":null,"createdate":null,"updatedate":null,"price":"4.85","currency":null,"monthSale":null,"isColler":null,"areaId":null},{"oitmId":null,"area":null,"itemcode":"1030164","codebars":null,"itemname":"荔枝纹-7寸-蓝色（OME）","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"134","uColors":null,"uXyms":null,"uUB":"1","uUX":"40","uPp":null,"picturname":"http://52.57.221.27:8898/110080.jpg","uIssale":null,"createdate":null,"updatedate":null,"price":"4.85","currency":null,"monthSale":null,"isColler":null,"areaId":null},{"oitmId":null,"area":null,"itemcode":"1030165","codebars":null,"itemname":"荔枝纹-7寸-玫红（OME）","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"257","uColors":null,"uXyms":null,"uUB":"1","uUX":"40","uPp":null,"picturname":"http://52.57.221.27:8898/110097.jpg","uIssale":null,"createdate":null,"updatedate":null,"price":"4.85","currency":null,"monthSale":null,"isColler":null,"areaId":null},{"oitmId":null,"area":null,"itemcode":"1030166","codebars":null,"itemname":"荔枝纹-8寸-黑色（OME）","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"0","uColors":null,"uXyms":null,"uUB":"1","uUX":"40","uPp":null,"picturname":"http://52.57.221.27:8898/110103.jpg","uIssale":null,"createdate":null,"updatedate":null,"price":"5.04","currency":null,"monthSale":null,"isColler":null,"areaId":null},{"oitmId":null,"area":null,"itemcode":"1030167","codebars":null,"itemname":"荔枝纹-8寸-红色（OME）","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"5","uColors":null,"uXyms":null,"uUB":"1","uUX":"40","uPp":null,"picturname":"http://52.57.221.27:8898/110110.jpg","uIssale":null,"createdate":null,"updatedate":null,"price":"5.04","currency":null,"monthSale":null,"isColler":null,"areaId":null},{"oitmId":null,"area":null,"itemcode":"1030168","codebars":null,"itemname":"荔枝纹-8寸-蓝色（OME）","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"7","uColors":null,"uXyms":null,"uUB":"1","uUX":"40","uPp":null,"picturname":"http://52.57.221.27:8898/1101271.jpg","uIssale":null,"createdate":null,"updatedate":null,"price":"5.04","currency":null,"monthSale":null,"isColler":null,"areaId":null},{"oitmId":null,"area":null,"itemcode":"1030169","codebars":null,"itemname":"荔枝纹-8寸-玫红（OME）","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"9","uColors":null,"uXyms":null,"uUB":"1","uUX":"40","uPp":null,"picturname":"http://52.57.221.27:8898/1101341.jpg","uIssale":null,"createdate":null,"updatedate":null,"price":"5.04","currency":null,"monthSale":null,"isColler":null,"areaId":null},{"oitmId":null,"area":null,"itemcode":"1030170","codebars":null,"itemname":"荔枝纹-9寸-黑色（OME）","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"3","uColors":null,"uXyms":null,"uUB":"1","uUX":"40","uPp":null,"picturname":"http://52.57.221.27:8898/110141.jpg","uIssale":null,"createdate":null,"updatedate":null,"price":"5.24","currency":null,"monthSale":null,"isColler":null,"areaId":null},{"oitmId":null,"area":null,"itemcode":"1030171","codebars":null,"itemname":"荔枝纹-9寸-红色（OME）","itmsgrpcod":null,"spec":null,"minlevel":null,"onhand":"12","uColors":null,"uXyms":null,"uUB":"1","uUX":"40","uPp":null,"picturname":"http://52.57.221.27:8898/110158.jpg","uIssale":null,"createdate":null,"updatedate":null,"price":"5.24","currency":null,"monthSale":null,"isColler":null,"areaId":null}]
     * totalPage : 4
     * totalRecord : 40
     * total : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    private String totalShop;

    public String getTotalShop() {
        return totalShop;
    }

    public void setTotalShop(String totalShop) {
        this.totalShop = totalShop;
    }

    public static class DataBean implements Parcelable {
        /**
         * oitmId : null
         * area : null
         * itemcode : 1030162
         * codebars : null
         * itemname : 荔枝纹-7寸-黑色（OME）
         * itmsgrpcod : null
         * spec : null
         * minlevel : null
         * onhand : 0
         * uColors : null
         * uXyms : null
         * uUB : 1
         * uUX : 40
         * uPp : null
         * picturname : http://52.57.221.27:8898/110066.jpg
         * uIssale : null
         * createdate : null
         * updatedate : null
         * price : 4.85
         * currency : null
         * monthSale : null
         * isColler : null
         * areaId : null
         */
        private String id;
        private String oitmId;
        private String area;
        private String itemcode;
        private String codebars;
        private String itemname;
        private String itmsgrpcod;
        private String spec;
        private String minlevel;
        private String onhand;
        private String uColors;
        private String uXyms;
        private String uUB;
        private String uUX;
        private String uPp;
        private String picturname;
        private String uIssale;
        private String createdate;
        private String updatedate;
        private String price;
        private String currency;
        private String monthSale;
        private String isColler;
        private String uId;
        private String areaId;
        private String pName;
        private String attachment1;
        private String attachment2;
        private String attachment3;
        private String attachment4;
        private String attachment5;
        private String attachment6;
        private String userText;
        private String shopCount;

        public String getShopCount() {
            return shopCount;
        }

        public void setShopCount(String shopCount) {
            this.shopCount = shopCount;
        }

        public String getUserText() {
            return userText;
        }

        public void setUserText(String userText) {
            this.userText = userText;
        }

        public String getAttachment1() {
            return attachment1;
        }

        public void setAttachment1(String attachment1) {
            this.attachment1 = attachment1;
        }

        public String getAttachment2() {
            return attachment2;
        }

        public void setAttachment2(String attachment2) {
            this.attachment2 = attachment2;
        }

        public String getAttachment3() {
            return attachment3;
        }

        public void setAttachment3(String attachment3) {
            this.attachment3 = attachment3;
        }

        public String getAttachment4() {
            return attachment4;
        }

        public void setAttachment4(String attachment4) {
            this.attachment4 = attachment4;
        }

        public String getAttachment5() {
            return attachment5;
        }

        public void setAttachment5(String attachment5) {
            this.attachment5 = attachment5;
        }

        public String getAttachment6() {
            return attachment6;
        }

        public void setAttachment6(String attachment6) {
            this.attachment6 = attachment6;
        }

        public String getpName() {
            return pName;
        }

        public void setpName(String pName) {
            this.pName = pName;
        }

        private boolean isChecked;
        private int selectNum;


        private String optime;
        private String oitmView;


        private boolean isHavaPackage;
        private boolean isLargePackage;
        private boolean havaPackage;
        private boolean isSmallPackage;

        public boolean isSmallPackage() {
            return isSmallPackage;
        }

        public void setSmallPackage(boolean smallPackage) {
            isSmallPackage = smallPackage;
        }

        public int getSelectNum() {
            return selectNum;
        }

        public void setSelectNum(int selectNum) {
            this.selectNum = selectNum;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public boolean isLargePackage() {
            return isLargePackage;
        }

        public void setLargePackage(boolean largePackage) {
            isLargePackage = largePackage;
        }


        public boolean isHavaPackage() {
            return havaPackage;
        }

        public void setHavaPackage(boolean havaPackage) {
            this.havaPackage = havaPackage;
        }

        public String getOitmId() {
            return oitmId;
        }

        public void setOitmId(String oitmId) {
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

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getMinlevel() {
            return minlevel;
        }

        public void setMinlevel(String minlevel) {
            this.minlevel = minlevel;
        }

        public String getOnhand() {
            return onhand;
        }

        public void setOnhand(String onhand) {
            this.onhand = onhand;
        }

        public String getUColors() {
            return uColors;
        }

        public void setUColors(String uColors) {
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

        public String getUIssale() {
            return uIssale;
        }

        public void setUIssale(String uIssale) {
            this.uIssale = uIssale;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(String updatedate) {
            this.updatedate = updatedate;
        }

        public String getPrice() {
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

        public String getMonthSale() {
            return monthSale;
        }

        public void setMonthSale(String monthSale) {
            this.monthSale = monthSale;
        }

        public String getIsColler() {
            return isColler;
        }

        public void setIsColler(String isColler) {
            this.isColler = isColler;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.oitmId);
            dest.writeString(this.area);
            dest.writeString(this.itemcode);
            dest.writeString(this.codebars);
            dest.writeString(this.itemname);
            dest.writeString(this.itmsgrpcod);
            dest.writeString(this.spec);
            dest.writeString(this.minlevel);
            dest.writeString(this.onhand);
            dest.writeString(this.uColors);
            dest.writeString(this.uXyms);
            dest.writeString(this.uUB);
            dest.writeString(this.uUX);
            dest.writeString(this.uPp);
            dest.writeString(this.picturname);
            dest.writeString(this.uIssale);
            dest.writeString(this.createdate);
            dest.writeString(this.updatedate);
            dest.writeString(this.price);
            dest.writeString(this.currency);
            dest.writeString(this.monthSale);
            dest.writeString(this.isColler);
            dest.writeString(this.uId);
            dest.writeString(this.areaId);
            dest.writeString(this.pName);
            dest.writeString(this.attachment1);
            dest.writeString(this.attachment2);
            dest.writeString(this.attachment3);
            dest.writeString(this.attachment4);
            dest.writeString(this.attachment5);
            dest.writeString(this.attachment6);
            dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
            dest.writeInt(this.selectNum);
            dest.writeString(this.optime);
            dest.writeString(this.oitmView);
            dest.writeByte(this.isHavaPackage ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isLargePackage ? (byte) 1 : (byte) 0);
            dest.writeByte(this.havaPackage ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isSmallPackage ? (byte) 1 : (byte) 0);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.oitmId = in.readString();
            this.area = in.readString();
            this.itemcode = in.readString();
            this.codebars = in.readString();
            this.itemname = in.readString();
            this.itmsgrpcod = in.readString();
            this.spec = in.readString();
            this.minlevel = in.readString();
            this.onhand = in.readString();
            this.uColors = in.readString();
            this.uXyms = in.readString();
            this.uUB = in.readString();
            this.uUX = in.readString();
            this.uPp = in.readString();
            this.picturname = in.readString();
            this.uIssale = in.readString();
            this.createdate = in.readString();
            this.updatedate = in.readString();
            this.price = in.readString();
            this.currency = in.readString();
            this.monthSale = in.readString();
            this.isColler = in.readString();
            this.uId = in.readString();
            this.areaId = in.readString();
            this.pName = in.readString();
            this.attachment1 = in.readString();
            this.attachment2 = in.readString();
            this.attachment3 = in.readString();
            this.attachment4 = in.readString();
            this.attachment5 = in.readString();
            this.attachment6 = in.readString();
            this.isChecked = in.readByte() != 0;
            this.selectNum = in.readInt();
            this.optime = in.readString();
            this.oitmView = in.readString();
            this.isHavaPackage = in.readByte() != 0;
            this.isLargePackage = in.readByte() != 0;
            this.havaPackage = in.readByte() != 0;
            this.isSmallPackage = in.readByte() != 0;
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}

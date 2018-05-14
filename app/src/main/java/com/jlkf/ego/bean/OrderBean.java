package com.jlkf.ego.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcs on 2017/7/14.
 * 订单信息实体类
 */

public class OrderBean extends MyBaseBean {

    /**
     * mData : [{"id":17,"appdocnumber":"201708041979795","uFkfs":"1","uZk1":0.6,"uZk2":0.7,"uZk3":0.8,"uZk4":null,"docdisc":null,"totalexpns":20,"comments":"来一斤!!!","shipaddress":"7","docstatus":"3","doctotal":800,"docdate":1501845863000,"docduedate":1502159053000,"uId":4,"payType":null,"detailId":null,"name":null,"area":null,"anchorType":null,"oorderDetail":[{"id":13,"appdocnumber":"201708041979795","itemcode":"1010001","quantity":1,"codebars":"DSA","price":32,"name":"HELLO","logo":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3605439283,2740432204&fm=26&gp=0.jpg","brandname":null,"brandId":null}],"uaddress":{"uaId":7,"uaSurname":"哦哦","uaName":"刚刚","uaSex":"0","uaContactphone":"13245496464","uaLandline":"","uaCompanyname":"刚刚","uaShopname":"厉害","uaCountriesName":"中国","uaCountries":9,"uaProvincialName":"进入","uaProvincial":10001,"uaCityName":"山东省","uaCity":0,"uaDelivery":"去送你","uaUinvoice":"6464","uaStatus":1,"uaInvoice":"抱歉","uaZipcode":"","uaEin":"3434","uaBusinesstime":"0","uaDeliverytime":"0","uaPaytype":0,"upShoptype":0,"upShoparea":null,"upTargetuser":0,"upSwanseason":"0","upLightseason":"0","upMainproducts":"","upDescr":"","upLogo":"1","upDefault":1,"uId":4,"uaCreationtime":null,"uaLocationName":null,"uaLocationId":null,"cmId":null,"sapNo":"","cmName":null,"promissoryPay":0,"msZipcode":null,"customerdiscount":null,"msRemarks":null},"upayMethod":{"uPayId":1,"uPayName":"现金支付","uPayType":"0","uPayNo":"10002","uPayPayee":"无"}}]
     * totalPage : 1
     * totalRecord : 1
     * total : null
     */


    private String total;
    private List<DataBean> data;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 17
         * appdocnumber : 201708041979795
         * uFkfs : 1
         * uZk1 : 0.6
         * uZk2 : 0.7
         * uZk3 : 0.8
         * uZk4 : null
         * docdisc : null
         * totalexpns : 20
         * comments : 来一斤!!!
         * shipaddress : 7
         * docstatus : 3
         * doctotal : 800
         * docdate : 1501845863000
         * docduedate : 1502159053000
         * uId : 4
         * payType : null
         * detailId : null
         * name : null
         * area : null
         * anchorType : null
         * oorderDetail : [{"id":13,"appdocnumber":"201708041979795","itemcode":"1010001","quantity":1,"codebars":"DSA","price":32,"name":"HELLO","logo":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3605439283,2740432204&fm=26&gp=0.jpg","brandname":null,"brandId":null}]
         * uaddress : {"uaId":7,"uaSurname":"哦哦","uaName":"刚刚","uaSex":"0","uaContactphone":"13245496464","uaLandline":"","uaCompanyname":"刚刚","uaShopname":"厉害","uaCountriesName":"中国","uaCountries":9,"uaProvincialName":"进入","uaProvincial":10001,"uaCityName":"山东省","uaCity":0,"uaDelivery":"去送你","uaUinvoice":"6464","uaStatus":1,"uaInvoice":"抱歉","uaZipcode":"","uaEin":"3434","uaBusinesstime":"0","uaDeliverytime":"0","uaPaytype":0,"upShoptype":0,"upShoparea":null,"upTargetuser":0,"upSwanseason":"0","upLightseason":"0","upMainproducts":"","upDescr":"","upLogo":"1","upDefault":1,"uId":4,"uaCreationtime":null,"uaLocationName":null,"uaLocationId":null,"cmId":null,"sapNo":"","cmName":null,"promissoryPay":0,"msZipcode":null,"customerdiscount":null,"msRemarks":null}
         * upayMethod : {"uPayId":1,"uPayName":"现金支付","uPayType":"0","uPayNo":"10002","uPayPayee":"无"}
         */

        private int id;
        private String appdocnumber;
        private String uFkfs;
        private double uZk1;
        private double uZk2;
        private double uZk3;
        private double uZk4;
        private String docdisc;
        private String totalexpns;
        private String comments;
        private String shipaddress;
        private String docstatus;
        private String doctotal;
        private long docdate;
        private long docduedate;
        private int uId;
        private String payType;
        private String detailId;
        private String name;
        private String area;
        private String anchorType;
        private UaddressBean uaddress;
        private UpayMethodBean upayMethod;
        private ArrayList<OorderDetailBean> oorderDetail;

        private boolean isCountDown;

        public boolean isCountDown() {
            return isCountDown;
        }

        public void setCountDown(boolean countDown) {
            isCountDown = countDown;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAppdocnumber() {
            return appdocnumber;
        }

        public void setAppdocnumber(String appdocnumber) {
            this.appdocnumber = appdocnumber;
        }

        public String getUFkfs() {
            return uFkfs;
        }

        public void setUFkfs(String uFkfs) {
            this.uFkfs = uFkfs;
        }

        public double getUZk1() {
            return uZk1;
        }

        public void setUZk1(double uZk1) {
            this.uZk1 = uZk1;
        }

        public double getUZk2() {
            return uZk2;
        }

        public void setUZk2(double uZk2) {
            this.uZk2 = uZk2;
        }

        public double getUZk3() {
            return uZk3;
        }

        public void setUZk3(double uZk3) {
            this.uZk3 = uZk3;
        }

        public double getUZk4() {
            return uZk4;
        }

        public void setUZk4(double uZk4) {
            this.uZk4 = uZk4;
        }

        public String getDocdisc() {
            return docdisc;
        }

        public void setDocdisc(String docdisc) {
            this.docdisc = docdisc;
        }

        public String getTotalexpns() {
            return totalexpns;
        }

        public void setTotalexpns(String totalexpns) {
            this.totalexpns = totalexpns;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getShipaddress() {
            return shipaddress;
        }

        public void setShipaddress(String shipaddress) {
            this.shipaddress = shipaddress;
        }

        public String getDocstatus() {
            return docstatus;
        }

        public void setDocstatus(String docstatus) {
            this.docstatus = docstatus;
        }

        public String getDoctotal() {
            return doctotal;
        }

        public void setDoctotal(String doctotal) {
            this.doctotal = doctotal;
        }

        public long getDocdate() {
            return docdate;
        }

        public void setDocdate(long docdate) {
            this.docdate = docdate;
        }

        public long getDocduedate() {
            return docduedate;
        }

        public void setDocduedate(long docduedate) {
            this.docduedate = docduedate;
        }

        public int getUId() {
            return uId;
        }

        public void setUId(int uId) {
            this.uId = uId;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getDetailId() {
            return detailId;
        }

        public void setDetailId(String detailId) {
            this.detailId = detailId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAnchorType() {
            return anchorType;
        }

        public void setAnchorType(String anchorType) {
            this.anchorType = anchorType;
        }

        public UaddressBean getUaddress() {
            return uaddress;
        }

        public void setUaddress(UaddressBean uaddress) {
            this.uaddress = uaddress;
        }

        public UpayMethodBean getUpayMethod() {
            return upayMethod;
        }

        public void setUpayMethod(UpayMethodBean upayMethod) {
            this.upayMethod = upayMethod;
        }

        public ArrayList<OorderDetailBean> getOorderDetail() {
            return oorderDetail;
        }

        public void setOorderDetail(ArrayList<OorderDetailBean> oorderDetail) {
            this.oorderDetail = oorderDetail;
        }

        public static class UaddressBean {
            /**
             * uaId : 7
             * uaSurname : 哦哦
             * uaName : 刚刚
             * uaSex : 0
             * uaContactphone : 13245496464
             * uaLandline :
             * uaCompanyname : 刚刚
             * uaShopname : 厉害
             * uaCountriesName : 中国
             * uaCountries : 9
             * uaProvincialName : 进入
             * uaProvincial : 10001
             * uaCityName : 山东省
             * uaCity : 0
             * uaDelivery : 去送你
             * uaUinvoice : 6464
             * uaStatus : 1
             * uaInvoice : 抱歉
             * uaZipcode :
             * uaEin : 3434
             * uaBusinesstime : 0
             * uaDeliverytime : 0
             * uaPaytype : 0
             * upShoptype : 0
             * upShoparea : null
             * upTargetuser : 0
             * upSwanseason : 0
             * upLightseason : 0
             * upMainproducts :
             * upDescr :
             * upLogo : 1
             * upDefault : 1
             * uId : 4
             * uaCreationtime : null
             * uaLocationName : null
             * uaLocationId : null
             * cmId : null
             * sapNo :
             * cmName : null
             * promissoryPay : 0
             * msZipcode : null
             * customerdiscount : null
             * msRemarks : null
             */

            private int uaId;
            private String uaSurname;
            private String uaName;
            private String uaSex;
            private String uaContactphone;
            private String uaLandline;
            private String uaCompanyname;
            private String uaShopname;
            private String uaCountriesName;
            private int uaCountries;
            private String uaProvincialName;
            private int uaProvincial;
            private String uaCityName;
            private int uaCity;
            private String uaDelivery;
            private String uaUinvoice;
            private int uaStatus;
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
            private String upLogo;
            private int upDefault;
            private int uId;
            private String uaCreationtime;
            private String uaLocationName;
            private String uaLocationId;
            private String cmId;
            private String sapNo;
            private String cmName;
            private int promissoryPay;
            private String msZipcode;
            private String customerdiscount;
            private String msRemarks;

            public int getUaId() {
                return uaId;
            }

            public void setUaId(int uaId) {
                this.uaId = uaId;
            }

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

            public String getUaSex() {
                return uaSex;
            }

            public void setUaSex(String uaSex) {
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

            public int getUaCountries() {
                return uaCountries;
            }

            public void setUaCountries(int uaCountries) {
                this.uaCountries = uaCountries;
            }

            public String getUaProvincialName() {
                return uaProvincialName;
            }

            public void setUaProvincialName(String uaProvincialName) {
                this.uaProvincialName = uaProvincialName;
            }

            public int getUaProvincial() {
                return uaProvincial;
            }

            public void setUaProvincial(int uaProvincial) {
                this.uaProvincial = uaProvincial;
            }

            public String getUaCityName() {
                return uaCityName;
            }

            public void setUaCityName(String uaCityName) {
                this.uaCityName = uaCityName;
            }

            public int getUaCity() {
                return uaCity;
            }

            public void setUaCity(int uaCity) {
                this.uaCity = uaCity;
            }

            public String getUaDelivery() {
                return uaDelivery;
            }

            public void setUaDelivery(String uaDelivery) {
                this.uaDelivery = uaDelivery;
            }

            public String getUaUinvoice() {
                return uaUinvoice;
            }

            public void setUaUinvoice(String uaUinvoice) {
                this.uaUinvoice = uaUinvoice;
            }

            public int getUaStatus() {
                return uaStatus;
            }

            public void setUaStatus(int uaStatus) {
                this.uaStatus = uaStatus;
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

            public int getUId() {
                return uId;
            }

            public void setUId(int uId) {
                this.uId = uId;
            }

            public String getUaCreationtime() {
                return uaCreationtime;
            }

            public void setUaCreationtime(String uaCreationtime) {
                this.uaCreationtime = uaCreationtime;
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

            public int getPromissoryPay() {
                return promissoryPay;
            }

            public void setPromissoryPay(int promissoryPay) {
                this.promissoryPay = promissoryPay;
            }

            public String getMsZipcode() {
                return msZipcode;
            }

            public void setMsZipcode(String msZipcode) {
                this.msZipcode = msZipcode;
            }

            public String getCustomerdiscount() {
                return customerdiscount;
            }

            public void setCustomerdiscount(String customerdiscount) {
                this.customerdiscount = customerdiscount;
            }

            public String getMsRemarks() {
                return msRemarks;
            }

            public void setMsRemarks(String msRemarks) {
                this.msRemarks = msRemarks;
            }
        }

        public static class UpayMethodBean {
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

        public static class OorderDetailBean implements Parcelable {
            /**
             * id : 13
             * appdocnumber : 201708041979795
             * itemcode : 1010001
             * quantity : 1
             * codebars : DSA
             * price : 32
             * name : HELLO
             * logo : https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3605439283,2740432204&fm=26&gp=0.jpg
             * brandname : null
             * brandId : null
             */

            private int id;
            private String appdocnumber;
            private String itemcode;
            private int quantity;
            private String codebars;
            private String price;
            private String name;
            private String logo;
            private String brandname;
            private String brandId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAppdocnumber() {
                return appdocnumber;
            }

            public void setAppdocnumber(String appdocnumber) {
                this.appdocnumber = appdocnumber;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.appdocnumber);
                dest.writeString(this.itemcode);
                dest.writeInt(this.quantity);
                dest.writeString(this.codebars);
                dest.writeString(this.price);
                dest.writeString(this.name);
                dest.writeString(this.logo);
                dest.writeString(this.brandname);
                dest.writeString(this.brandId);
            }

            public OorderDetailBean() {
            }

            protected OorderDetailBean(Parcel in) {
                this.id = in.readInt();
                this.appdocnumber = in.readString();
                this.itemcode = in.readString();
                this.quantity = in.readInt();
                this.codebars = in.readString();
                this.price = in.readString();
                this.name = in.readString();
                this.logo = in.readString();
                this.brandname = in.readString();
                this.brandId = in.readString();
            }

            public static final Creator<OorderDetailBean> CREATOR = new Creator<OorderDetailBean>() {
                @Override
                public OorderDetailBean createFromParcel(Parcel source) {
                    return new OorderDetailBean(source);
                }

                @Override
                public OorderDetailBean[] newArray(int size) {
                    return new OorderDetailBean[size];
                }
            };
        }
    }
}

package com.jlkf.ego.newpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.jlkf.ego.bean.AdressBean;
import com.jlkf.ego.bean.ConfimOrderBean;

import java.util.List;

/**
 * Created by Dell on 2018/6/20.
 */

public class NewConfimOrderBean implements Parcelable {

    /**
     * brandData : {"pp_id":"3","Area":"spain","Code":"003","Name":"HOME","pp_minlogo":"http://52.57.221.27:8080//images//adal//201803040894102.png","pp_maxlogo":"http://52.57.221.27:8080//images//adal//201803031412818.png","brand_id":"3"}
     * freight : {"freight":20,"Nofreight":300}
     * points : {"discount":"8.8","rankname":"白银","img":"/uploads/2018/06/08/3de4838f0ef271cb2f0ea519979a6f9a.png"}
     * total_num : 7
     * total_sum : 76.23
     * listData : [{"s_id":"6224","name":"S4输入：100-240V/47-63Hz Max 500mA输出：5 V  8Awith IC smart","Price":"10.890000","logo":"http://52.29.220.117:8898/1617850.jpg","ItemCode":"1010261","Quantity":"7","CodeBars":"8433772161785","brandName":null,"brand_id":"3","u_id":"35","discountnum":"0","discount":"0.00","atprice":"0","settlement":"0","overlap":""}]
     * pay : [{"pay_id":"2","customer_discount":"0.0500","pay_discount":"0.0700","settlement_total":101.23396,"settlement_zk":0,"TotalExpns":20,"settlement_zp":0,"Totaltax":5.26416592},{"pay_id":"3","customer_discount":"0.0500","pay_discount":"0.0500","settlement_total":101.23396,"settlement_zk":0,"TotalExpns":20,"settlement_zp":0,"Totaltax":5.26416592},{"pay_id":"4","customer_discount":"0.0500","pay_discount":"0.0000","settlement_total":101.23396,"settlement_zk":0,"TotalExpns":20,"settlement_zp":0,"Totaltax":5.26416592}]
     * address : {"ua_id":"100","ua_surname":"朱","ua_name":"一兵","ua_sex":"1","ua_contactPhone":"18681555504","ua_landline":"","ua_uinvoice":"1","ua_companyname":"Joe的连锁","ua_shopname":"Joe的百货","ua_countriesName":"France","ua_provincialName":"Paris","ua_cityName":"东莞","ua_countries":"33","ua_provincial":"1326","ua_city":"0","ua_delivery":"松山湖","ua_invoice":"松山湖","ua_zipcode":"666665584","ua_ein":"6666","ua_businessTime":"","ua_deliverytime":"","ua_payType":"1","up_shopType":"0","up_shopArea":"","up_targetUser":"0","up_swanSeason":"","up_lightSeason":"","up_mainProducts":"","up_descr":"","up_logo":"1,1,1","up_default":"1","u_id":"35","ua_creationtime":"2017-11-13 03:35:19","ua_status":"1","ua_location_name":null,"ua_location_id":"0","cm_id":null,"SAP_no":"","cm_name":null,"promissory_pay":"0","ms_zipcode":null,"CustomerDiscount":null,"ms_Remarks":null,"is_delete":"0","ua_level":null,"sale_status":"0","type":"0","update_time":"0"}
     */

    private BrandDataBean brandData;
    private FreightBean freight;
    private PointsBean points;
    private int total_num;
    private double total_sum;
    private AddressBean address;
    private List<ConfimOrderBean.DataBean.ListDataBean> listData;
    private List<PayBean> pay;
    private AdressBean mAdressBean;
    private double include_total;
    private double TotalExpns2;

    public double getTotalExpns2() {
        return TotalExpns2;
    }

    public void setTotalExpns2(double totalExpns2) {
        TotalExpns2 = totalExpns2;
    }

    public double getInclude_total() {
        return include_total;
    }

    public void setInclude_total(double include_total) {
        this.include_total = include_total;
    }

    private String uFkfs;
    private double uZk1;
    private double uZk2;
    private double uZk3;
    private double uZk4;
    private double totalexpns = 0.0;
    private String fkfs;

    private String spainTotal;

    private String docdisc;

    public String getDocdisc() {
        return docdisc;
    }

    public void setDocdisc(String docdisc) {
        this.docdisc = docdisc;
    }

    public String getSpainTotal() {
        return spainTotal;
    }

    public void setSpainTotal(String spainTotal) {
        this.spainTotal = spainTotal;
    }

    private int is_gift;

    public int getIs_gift() {
        return is_gift;
    }

    public void setIs_gift(int is_gift) {
        this.is_gift = is_gift;
    }

    public String getFkfs() {
        return fkfs;
    }

    public void setFkfs(String fkfs) {
        this.fkfs = fkfs;
    }

    public double getTotalexpns() {
        return totalexpns;
    }

    public void setTotalexpns(double totalexpns) {
        this.totalexpns = totalexpns;
    }

    public String getuFkfs() {
        return uFkfs;
    }

    public void setuFkfs(String uFkfs) {
        this.uFkfs = uFkfs;
    }

    public double getuZk1() {
        return uZk1;
    }

    public void setuZk1(double uZk1) {
        this.uZk1 = uZk1;
    }

    public double getuZk2() {
        return uZk2;
    }

    public void setuZk2(double uZk2) {
        this.uZk2 = uZk2;
    }

    public double getuZk3() {
        return uZk3;
    }

    public void setuZk3(double uZk3) {
        this.uZk3 = uZk3;
    }

    public double getuZk4() {
        return uZk4;
    }

    public void setuZk4(double uZk4) {
        this.uZk4 = uZk4;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg = "还需0.0即可免费配送";

    public String getLy() {
        return ly;
    }

    public void setLy(String ly) {
        this.ly = ly;
    }

    private String ly;//留言

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;//付款方式
    private double finallyTotal;

    public double getFinallyTotal() {
        return finallyTotal;
    }

    public void setFinallyTotal(double finallyTotal) {
        this.finallyTotal = finallyTotal;
    }

    public AdressBean getmAdressBean() {
        return mAdressBean;
    }

    public void setmAdressBean(AdressBean mAdressBean) {
        this.mAdressBean = mAdressBean;
    }

    public BrandDataBean getBrandData() {
        return brandData;
    }

    public void setBrandData(BrandDataBean brandData) {
        this.brandData = brandData;
    }

    public FreightBean getFreight() {
        return freight;
    }

    public void setFreight(FreightBean freight) {
        this.freight = freight;
    }

    public PointsBean getPoints() {
        return points;
    }

    public void setPoints(PointsBean points) {
        this.points = points;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public double getTotal_sum() {
        return total_sum;
    }

    public void setTotal_sum(double total_sum) {
        this.total_sum = total_sum;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public List<ConfimOrderBean.DataBean.ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ConfimOrderBean.DataBean.ListDataBean> listData) {
        this.listData = listData;
    }

    public List<PayBean> getPay() {
        return pay;
    }

    public void setPay(List<PayBean> pay) {
        this.pay = pay;
    }

    public static class BrandDataBean implements Parcelable {
        /**
         * pp_id : 3
         * Area : spain
         * Code : 003
         * Name : HOME
         * pp_minlogo : http://52.57.221.27:8080//images//adal//201803040894102.png
         * pp_maxlogo : http://52.57.221.27:8080//images//adal//201803031412818.png
         * brand_id : 3
         */

        private String pp_id;
        private String Area;
        private String Code;
        private String Name;
        private String pp_minlogo;
        private String pp_maxlogo;
        private String brand_id;

        public String getPp_id() {
            return pp_id;
        }

        public void setPp_id(String pp_id) {
            this.pp_id = pp_id;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String Area) {
            this.Area = Area;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPp_minlogo() {
            return pp_minlogo;
        }

        public void setPp_minlogo(String pp_minlogo) {
            this.pp_minlogo = pp_minlogo;
        }

        public String getPp_maxlogo() {
            return pp_maxlogo;
        }

        public void setPp_maxlogo(String pp_maxlogo) {
            this.pp_maxlogo = pp_maxlogo;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.pp_id);
            dest.writeString(this.Area);
            dest.writeString(this.Code);
            dest.writeString(this.Name);
            dest.writeString(this.pp_minlogo);
            dest.writeString(this.pp_maxlogo);
            dest.writeString(this.brand_id);
        }

        public BrandDataBean() {
        }

        protected BrandDataBean(Parcel in) {
            this.pp_id = in.readString();
            this.Area = in.readString();
            this.Code = in.readString();
            this.Name = in.readString();
            this.pp_minlogo = in.readString();
            this.pp_maxlogo = in.readString();
            this.brand_id = in.readString();
        }

        public static final Parcelable.Creator<BrandDataBean> CREATOR = new Parcelable.Creator<BrandDataBean>() {
            @Override
            public BrandDataBean createFromParcel(Parcel source) {
                return new BrandDataBean(source);
            }

            @Override
            public BrandDataBean[] newArray(int size) {
                return new BrandDataBean[size];
            }
        };
    }

    public static class FreightBean implements Parcelable {
        /**
         * freight : 20
         * Nofreight : 300
         */

        private int freight;
        private int Nofreight;

        public int getFreight() {
            return freight;
        }

        public void setFreight(int freight) {
            this.freight = freight;
        }

        public int getNofreight() {
            return Nofreight;
        }

        public void setNofreight(int Nofreight) {
            this.Nofreight = Nofreight;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.freight);
            dest.writeInt(this.Nofreight);
        }

        public FreightBean() {
        }

        protected FreightBean(Parcel in) {
            this.freight = in.readInt();
            this.Nofreight = in.readInt();
        }

        public static final Parcelable.Creator<FreightBean> CREATOR = new Parcelable.Creator<FreightBean>() {
            @Override
            public FreightBean createFromParcel(Parcel source) {
                return new FreightBean(source);
            }

            @Override
            public FreightBean[] newArray(int size) {
                return new FreightBean[size];
            }
        };
    }

    public static class PointsBean implements Parcelable {
        /**
         * discount : 8.8
         * rankname : 白银
         * img : /uploads/2018/06/08/3de4838f0ef271cb2f0ea519979a6f9a.png
         */

        private String discount;
        private String rankname;
        private String img;

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getRankname() {
            return rankname;
        }

        public void setRankname(String rankname) {
            this.rankname = rankname;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.discount);
            dest.writeString(this.rankname);
            dest.writeString(this.img);
        }

        public PointsBean() {
        }

        protected PointsBean(Parcel in) {
            this.discount = in.readString();
            this.rankname = in.readString();
            this.img = in.readString();
        }

        public static final Parcelable.Creator<PointsBean> CREATOR = new Parcelable.Creator<PointsBean>() {
            @Override
            public PointsBean createFromParcel(Parcel source) {
                return new PointsBean(source);
            }

            @Override
            public PointsBean[] newArray(int size) {
                return new PointsBean[size];
            }
        };
    }

    public static class AddressBean implements Parcelable {
        /**
         * ua_id : 100
         * ua_surname : 朱
         * ua_name : 一兵
         * ua_sex : 1
         * ua_contactPhone : 18681555504
         * ua_landline :
         * ua_uinvoice : 1
         * ua_companyname : Joe的连锁
         * ua_shopname : Joe的百货
         * ua_countriesName : France
         * ua_provincialName : Paris
         * ua_cityName : 东莞
         * ua_countries : 33
         * ua_provincial : 1326
         * ua_city : 0
         * ua_delivery : 松山湖
         * ua_invoice : 松山湖
         * ua_zipcode : 666665584
         * ua_ein : 6666
         * ua_businessTime :
         * ua_deliverytime :
         * ua_payType : 1
         * up_shopType : 0
         * up_shopArea :
         * up_targetUser : 0
         * up_swanSeason :
         * up_lightSeason :
         * up_mainProducts :
         * up_descr :
         * up_logo : 1,1,1
         * up_default : 1
         * u_id : 35
         * ua_creationtime : 2017-11-13 03:35:19
         * ua_status : 1
         * ua_location_name : null
         * ua_location_id : 0
         * cm_id : null
         * SAP_no :
         * cm_name : null
         * promissory_pay : 0
         * ms_zipcode : null
         * CustomerDiscount : null
         * ms_Remarks : null
         * is_delete : 0
         * ua_level : null
         * sale_status : 0
         * type : 0
         * update_time : 0
         */

        private String ua_id;
        private String ua_surname;
        private String ua_name;
        private String ua_sex;
        private String ua_contactPhone;
        private String ua_landline;
        private String ua_uinvoice;
        private String ua_companyname;
        private String ua_shopname;
        private String ua_countriesName;
        private String ua_provincialName;
        private String ua_cityName;
        private String ua_countries;
        private String ua_provincial;
        private String ua_city;
        private String ua_delivery;
        private String ua_invoice;
        private String ua_zipcode;
        private String ua_ein;
        private String ua_businessTime;
        private String ua_deliverytime;
        private String ua_payType;
        private String up_shopType;
        private String up_shopArea;
        private String up_targetUser;
        private String up_swanSeason;
        private String up_lightSeason;
        private String up_mainProducts;
        private String up_descr;
        private String up_logo;
        private String up_default;
        private String u_id;
        private String ua_creationtime;
        private String ua_status;
        private String ua_location_name;
        private String ua_location_id;
        private String cm_id;
        private String SAP_no;
        private String cm_name;
        private String promissory_pay;
        private String ms_zipcode;
        private String CustomerDiscount;
        private String ms_Remarks;
        private String is_delete;
        private String ua_level;
        private String sale_status;
        private String type;
        private String update_time;

        public String getUa_id() {
            return ua_id;
        }

        public void setUa_id(String ua_id) {
            this.ua_id = ua_id;
        }

        public String getUa_surname() {
            return ua_surname;
        }

        public void setUa_surname(String ua_surname) {
            this.ua_surname = ua_surname;
        }

        public String getUa_name() {
            return ua_name;
        }

        public void setUa_name(String ua_name) {
            this.ua_name = ua_name;
        }

        public String getUa_sex() {
            return ua_sex;
        }

        public void setUa_sex(String ua_sex) {
            this.ua_sex = ua_sex;
        }

        public String getUa_contactPhone() {
            return ua_contactPhone;
        }

        public void setUa_contactPhone(String ua_contactPhone) {
            this.ua_contactPhone = ua_contactPhone;
        }

        public String getUa_landline() {
            return ua_landline;
        }

        public void setUa_landline(String ua_landline) {
            this.ua_landline = ua_landline;
        }

        public String getUa_uinvoice() {
            return ua_uinvoice;
        }

        public void setUa_uinvoice(String ua_uinvoice) {
            this.ua_uinvoice = ua_uinvoice;
        }

        public String getUa_companyname() {
            return ua_companyname;
        }

        public void setUa_companyname(String ua_companyname) {
            this.ua_companyname = ua_companyname;
        }

        public String getUa_shopname() {
            return ua_shopname;
        }

        public void setUa_shopname(String ua_shopname) {
            this.ua_shopname = ua_shopname;
        }

        public String getUa_countriesName() {
            return ua_countriesName;
        }

        public void setUa_countriesName(String ua_countriesName) {
            this.ua_countriesName = ua_countriesName;
        }

        public String getUa_provincialName() {
            return ua_provincialName;
        }

        public void setUa_provincialName(String ua_provincialName) {
            this.ua_provincialName = ua_provincialName;
        }

        public String getUa_cityName() {
            return ua_cityName;
        }

        public void setUa_cityName(String ua_cityName) {
            this.ua_cityName = ua_cityName;
        }

        public String getUa_countries() {
            return ua_countries;
        }

        public void setUa_countries(String ua_countries) {
            this.ua_countries = ua_countries;
        }

        public String getUa_provincial() {
            return ua_provincial;
        }

        public void setUa_provincial(String ua_provincial) {
            this.ua_provincial = ua_provincial;
        }

        public String getUa_city() {
            return ua_city;
        }

        public void setUa_city(String ua_city) {
            this.ua_city = ua_city;
        }

        public String getUa_delivery() {
            return ua_delivery;
        }

        public void setUa_delivery(String ua_delivery) {
            this.ua_delivery = ua_delivery;
        }

        public String getUa_invoice() {
            return ua_invoice;
        }

        public void setUa_invoice(String ua_invoice) {
            this.ua_invoice = ua_invoice;
        }

        public String getUa_zipcode() {
            return ua_zipcode;
        }

        public void setUa_zipcode(String ua_zipcode) {
            this.ua_zipcode = ua_zipcode;
        }

        public String getUa_ein() {
            return ua_ein;
        }

        public void setUa_ein(String ua_ein) {
            this.ua_ein = ua_ein;
        }

        public String getUa_businessTime() {
            return ua_businessTime;
        }

        public void setUa_businessTime(String ua_businessTime) {
            this.ua_businessTime = ua_businessTime;
        }

        public String getUa_deliverytime() {
            return ua_deliverytime;
        }

        public void setUa_deliverytime(String ua_deliverytime) {
            this.ua_deliverytime = ua_deliverytime;
        }

        public String getUa_payType() {
            return ua_payType;
        }

        public void setUa_payType(String ua_payType) {
            this.ua_payType = ua_payType;
        }

        public String getUp_shopType() {
            return up_shopType;
        }

        public void setUp_shopType(String up_shopType) {
            this.up_shopType = up_shopType;
        }

        public String getUp_shopArea() {
            return up_shopArea;
        }

        public void setUp_shopArea(String up_shopArea) {
            this.up_shopArea = up_shopArea;
        }

        public String getUp_targetUser() {
            return up_targetUser;
        }

        public void setUp_targetUser(String up_targetUser) {
            this.up_targetUser = up_targetUser;
        }

        public String getUp_swanSeason() {
            return up_swanSeason;
        }

        public void setUp_swanSeason(String up_swanSeason) {
            this.up_swanSeason = up_swanSeason;
        }

        public String getUp_lightSeason() {
            return up_lightSeason;
        }

        public void setUp_lightSeason(String up_lightSeason) {
            this.up_lightSeason = up_lightSeason;
        }

        public String getUp_mainProducts() {
            return up_mainProducts;
        }

        public void setUp_mainProducts(String up_mainProducts) {
            this.up_mainProducts = up_mainProducts;
        }

        public String getUp_descr() {
            return up_descr;
        }

        public void setUp_descr(String up_descr) {
            this.up_descr = up_descr;
        }

        public String getUp_logo() {
            return up_logo;
        }

        public void setUp_logo(String up_logo) {
            this.up_logo = up_logo;
        }

        public String getUp_default() {
            return up_default;
        }

        public void setUp_default(String up_default) {
            this.up_default = up_default;
        }

        public String getU_id() {
            return u_id;
        }

        public void setU_id(String u_id) {
            this.u_id = u_id;
        }

        public String getUa_creationtime() {
            return ua_creationtime;
        }

        public void setUa_creationtime(String ua_creationtime) {
            this.ua_creationtime = ua_creationtime;
        }

        public String getUa_status() {
            return ua_status;
        }

        public void setUa_status(String ua_status) {
            this.ua_status = ua_status;
        }

        public String getUa_location_name() {
            return ua_location_name;
        }

        public void setUa_location_name(String ua_location_name) {
            this.ua_location_name = ua_location_name;
        }

        public String getUa_location_id() {
            return ua_location_id;
        }

        public void setUa_location_id(String ua_location_id) {
            this.ua_location_id = ua_location_id;
        }

        public String getCm_id() {
            return cm_id;
        }

        public void setCm_id(String cm_id) {
            this.cm_id = cm_id;
        }

        public String getSAP_no() {
            return SAP_no;
        }

        public void setSAP_no(String SAP_no) {
            this.SAP_no = SAP_no;
        }

        public String getCm_name() {
            return cm_name;
        }

        public void setCm_name(String cm_name) {
            this.cm_name = cm_name;
        }

        public String getPromissory_pay() {
            return promissory_pay;
        }

        public void setPromissory_pay(String promissory_pay) {
            this.promissory_pay = promissory_pay;
        }

        public String getMs_zipcode() {
            return ms_zipcode;
        }

        public void setMs_zipcode(String ms_zipcode) {
            this.ms_zipcode = ms_zipcode;
        }

        public String getCustomerDiscount() {
            return CustomerDiscount;
        }

        public void setCustomerDiscount(String CustomerDiscount) {
            this.CustomerDiscount = CustomerDiscount;
        }

        public String getMs_Remarks() {
            return ms_Remarks;
        }

        public void setMs_Remarks(String ms_Remarks) {
            this.ms_Remarks = ms_Remarks;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getUa_level() {
            return ua_level;
        }

        public void setUa_level(String ua_level) {
            this.ua_level = ua_level;
        }

        public String getSale_status() {
            return sale_status;
        }

        public void setSale_status(String sale_status) {
            this.sale_status = sale_status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.ua_id);
            dest.writeString(this.ua_surname);
            dest.writeString(this.ua_name);
            dest.writeString(this.ua_sex);
            dest.writeString(this.ua_contactPhone);
            dest.writeString(this.ua_landline);
            dest.writeString(this.ua_uinvoice);
            dest.writeString(this.ua_companyname);
            dest.writeString(this.ua_shopname);
            dest.writeString(this.ua_countriesName);
            dest.writeString(this.ua_provincialName);
            dest.writeString(this.ua_cityName);
            dest.writeString(this.ua_countries);
            dest.writeString(this.ua_provincial);
            dest.writeString(this.ua_city);
            dest.writeString(this.ua_delivery);
            dest.writeString(this.ua_invoice);
            dest.writeString(this.ua_zipcode);
            dest.writeString(this.ua_ein);
            dest.writeString(this.ua_businessTime);
            dest.writeString(this.ua_deliverytime);
            dest.writeString(this.ua_payType);
            dest.writeString(this.up_shopType);
            dest.writeString(this.up_shopArea);
            dest.writeString(this.up_targetUser);
            dest.writeString(this.up_swanSeason);
            dest.writeString(this.up_lightSeason);
            dest.writeString(this.up_mainProducts);
            dest.writeString(this.up_descr);
            dest.writeString(this.up_logo);
            dest.writeString(this.up_default);
            dest.writeString(this.u_id);
            dest.writeString(this.ua_creationtime);
            dest.writeString(this.ua_status);
            dest.writeString(this.ua_location_name);
            dest.writeString(this.ua_location_id);
            dest.writeString(this.cm_id);
            dest.writeString(this.SAP_no);
            dest.writeString(this.cm_name);
            dest.writeString(this.promissory_pay);
            dest.writeString(this.ms_zipcode);
            dest.writeString(this.CustomerDiscount);
            dest.writeString(this.ms_Remarks);
            dest.writeString(this.is_delete);
            dest.writeString(this.ua_level);
            dest.writeString(this.sale_status);
            dest.writeString(this.type);
            dest.writeString(this.update_time);
        }

        public AddressBean() {
        }

        protected AddressBean(Parcel in) {
            this.ua_id = in.readString();
            this.ua_surname = in.readString();
            this.ua_name = in.readString();
            this.ua_sex = in.readString();
            this.ua_contactPhone = in.readString();
            this.ua_landline = in.readString();
            this.ua_uinvoice = in.readString();
            this.ua_companyname = in.readString();
            this.ua_shopname = in.readString();
            this.ua_countriesName = in.readString();
            this.ua_provincialName = in.readString();
            this.ua_cityName = in.readString();
            this.ua_countries = in.readString();
            this.ua_provincial = in.readString();
            this.ua_city = in.readString();
            this.ua_delivery = in.readString();
            this.ua_invoice = in.readString();
            this.ua_zipcode = in.readString();
            this.ua_ein = in.readString();
            this.ua_businessTime = in.readString();
            this.ua_deliverytime = in.readString();
            this.ua_payType = in.readString();
            this.up_shopType = in.readString();
            this.up_shopArea = in.readString();
            this.up_targetUser = in.readString();
            this.up_swanSeason = in.readString();
            this.up_lightSeason = in.readString();
            this.up_mainProducts = in.readString();
            this.up_descr = in.readString();
            this.up_logo = in.readString();
            this.up_default = in.readString();
            this.u_id = in.readString();
            this.ua_creationtime = in.readString();
            this.ua_status = in.readString();
            this.ua_location_name = in.readString();
            this.ua_location_id = in.readString();
            this.cm_id = in.readString();
            this.SAP_no = in.readString();
            this.cm_name = in.readString();
            this.promissory_pay = in.readString();
            this.ms_zipcode = in.readString();
            this.CustomerDiscount = in.readString();
            this.ms_Remarks = in.readString();
            this.is_delete = in.readString();
            this.ua_level = in.readString();
            this.sale_status = in.readString();
            this.type = in.readString();
            this.update_time = in.readString();
        }

        public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
            @Override
            public AddressBean createFromParcel(Parcel source) {
                return new AddressBean(source);
            }

            @Override
            public AddressBean[] newArray(int size) {
                return new AddressBean[size];
            }
        };
    }

    public static class ListDataBean implements Parcelable {
        /**
         * s_id : 6224
         * name : S4输入：100-240V/47-63Hz Max 500mA输出：5 V  8Awith IC smart
         * Price : 10.890000
         * logo : http://52.29.220.117:8898/1617850.jpg
         * ItemCode : 1010261
         * Quantity : 7
         * CodeBars : 8433772161785
         * brandName : null
         * brand_id : 3
         * u_id : 35
         * discountnum : 0
         * discount : 0.00
         * atprice : 0
         * settlement : 0
         * overlap :
         */

        private String s_id;
        private String name;
        private String Price;
        private String logo;
        private String ItemCode;
        private int Quantity;
        private String CodeBars;
        private String brandName;
        private String brand_id;
        private String u_id;
        private String discountnum;
        private String discount;
        private String atprice;
        private String settlement;
        private String overlap;

        public String getS_id() {
            return s_id;
        }

        public void setS_id(String s_id) {
            this.s_id = s_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String ItemCode) {
            this.ItemCode = ItemCode;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int Quantity) {
            this.Quantity = Quantity;
        }

        public String getCodeBars() {
            return CodeBars;
        }

        public void setCodeBars(String CodeBars) {
            this.CodeBars = CodeBars;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getU_id() {
            return u_id;
        }

        public void setU_id(String u_id) {
            this.u_id = u_id;
        }

        public String getDiscountnum() {
            return discountnum;
        }

        public void setDiscountnum(String discountnum) {
            this.discountnum = discountnum;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getAtprice() {
            return atprice;
        }

        public void setAtprice(String atprice) {
            this.atprice = atprice;
        }

        public String getSettlement() {
            return settlement;
        }

        public void setSettlement(String settlement) {
            this.settlement = settlement;
        }

        public String getOverlap() {
            return overlap;
        }

        public void setOverlap(String overlap) {
            this.overlap = overlap;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.s_id);
            dest.writeString(this.name);
            dest.writeString(this.Price);
            dest.writeString(this.logo);
            dest.writeString(this.ItemCode);
            dest.writeInt(this.Quantity);
            dest.writeString(this.CodeBars);
            dest.writeString(this.brandName);
            dest.writeString(this.brand_id);
            dest.writeString(this.u_id);
            dest.writeString(this.discountnum);
            dest.writeString(this.discount);
            dest.writeString(this.atprice);
            dest.writeString(this.settlement);
            dest.writeString(this.overlap);
        }

        public ListDataBean() {
        }

        protected ListDataBean(Parcel in) {
            this.s_id = in.readString();
            this.name = in.readString();
            this.Price = in.readString();
            this.logo = in.readString();
            this.ItemCode = in.readString();
            this.Quantity = in.readInt();
            this.CodeBars = in.readString();
            this.brandName = in.readString();
            this.brand_id = in.readString();
            this.u_id = in.readString();
            this.discountnum = in.readString();
            this.discount = in.readString();
            this.atprice = in.readString();
            this.settlement = in.readString();
            this.overlap = in.readString();
        }

        public static final Parcelable.Creator<ListDataBean> CREATOR = new Parcelable.Creator<ListDataBean>() {
            @Override
            public ListDataBean createFromParcel(Parcel source) {
                return new ListDataBean(source);
            }

            @Override
            public ListDataBean[] newArray(int size) {
                return new ListDataBean[size];
            }
        };
    }

    public static class PayBean implements Parcelable {
        /**
         * pay_id : 2
         * customer_discount : 0.0500
         * pay_discount : 0.0700
         * settlement_total : 101.23396
         * settlement_zk : 0
         * TotalExpns : 20
         * settlement_zp : 0
         * Totaltax : 5.26416592
         */

        private String pay_id;
        private double customer_discount;
        private double pay_discount;
        private double settlement_total;
        private int settlement_zk;
        private int TotalExpns;
        private double settlement_zp;
        private double Totaltax;
        private double beyond_zp;//超出的赠品金额

        public double getBeyond_zp() {
            return beyond_zp;
        }

        public void setBeyond_zp(double beyond_zp) {
            this.beyond_zp = beyond_zp;
        }

        public String getPay_id() {
            return pay_id;
        }

        public void setPay_id(String pay_id) {
            this.pay_id = pay_id;
        }

        public double getCustomer_discount() {
            return customer_discount;
        }

        public void setCustomer_discount(double customer_discount) {
            this.customer_discount = customer_discount;
        }

        public double getPay_discount() {
            return pay_discount;
        }

        public void setPay_discount(double pay_discount) {
            this.pay_discount = pay_discount;
        }

        public double getSettlement_total() {
            return settlement_total;
        }

        public void setSettlement_total(double settlement_total) {
            this.settlement_total = settlement_total;
        }

        public int getSettlement_zk() {
            return settlement_zk;
        }

        public void setSettlement_zk(int settlement_zk) {
            this.settlement_zk = settlement_zk;
        }

        public int getTotalExpns() {
            return TotalExpns;
        }

        public void setTotalExpns(int TotalExpns) {
            this.TotalExpns = TotalExpns;
        }

        public double getSettlement_zp() {
            return settlement_zp;
        }

        public void setSettlement_zp(double settlement_zp) {
            this.settlement_zp = settlement_zp;
        }

        public double getTotaltax() {
            return Totaltax;
        }

        public void setTotaltax(double Totaltax) {
            this.Totaltax = Totaltax;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.pay_id);
            dest.writeDouble(this.customer_discount);
            dest.writeDouble(this.pay_discount);
            dest.writeDouble(this.settlement_total);
            dest.writeInt(this.settlement_zk);
            dest.writeInt(this.TotalExpns);
            dest.writeDouble(this.settlement_zp);
            dest.writeDouble(this.Totaltax);
        }

        public PayBean() {
        }

        protected PayBean(Parcel in) {
            this.pay_id = in.readString();
            this.customer_discount = in.readDouble();
            this.pay_discount = in.readDouble();
            this.settlement_total = in.readDouble();
            this.settlement_zk = in.readInt();
            this.TotalExpns = in.readInt();
            this.settlement_zp = in.readDouble();
            this.Totaltax = in.readDouble();
        }

        public static final Parcelable.Creator<PayBean> CREATOR = new Parcelable.Creator<PayBean>() {
            @Override
            public PayBean createFromParcel(Parcel source) {
                return new PayBean(source);
            }

            @Override
            public PayBean[] newArray(int size) {
                return new PayBean[size];
            }
        };
    }

    public NewConfimOrderBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.brandData, flags);
        dest.writeParcelable(this.freight, flags);
        dest.writeParcelable(this.points, flags);
        dest.writeInt(this.total_num);
        dest.writeDouble(this.total_sum);
        dest.writeParcelable(this.address, flags);
        dest.writeTypedList(this.listData);
        dest.writeTypedList(this.pay);
        dest.writeSerializable(this.mAdressBean);
        dest.writeDouble(this.include_total);
        dest.writeDouble(this.TotalExpns2);
        dest.writeString(this.uFkfs);
        dest.writeDouble(this.uZk1);
        dest.writeDouble(this.uZk2);
        dest.writeDouble(this.uZk3);
        dest.writeDouble(this.uZk4);
        dest.writeDouble(this.totalexpns);
        dest.writeString(this.fkfs);
        dest.writeString(this.spainTotal);
        dest.writeString(this.docdisc);
        dest.writeInt(this.is_gift);
        dest.writeString(this.msg);
        dest.writeString(this.ly);
        dest.writeInt(this.type);
        dest.writeDouble(this.finallyTotal);
    }

    protected NewConfimOrderBean(Parcel in) {
        this.brandData = in.readParcelable(BrandDataBean.class.getClassLoader());
        this.freight = in.readParcelable(FreightBean.class.getClassLoader());
        this.points = in.readParcelable(PointsBean.class.getClassLoader());
        this.total_num = in.readInt();
        this.total_sum = in.readDouble();
        this.address = in.readParcelable(AddressBean.class.getClassLoader());
        this.listData = in.createTypedArrayList(ConfimOrderBean.DataBean.ListDataBean.CREATOR);
        this.pay = in.createTypedArrayList(PayBean.CREATOR);
        this.mAdressBean = (AdressBean) in.readSerializable();
        this.include_total = in.readDouble();
        this.TotalExpns2 = in.readDouble();
        this.uFkfs = in.readString();
        this.uZk1 = in.readDouble();
        this.uZk2 = in.readDouble();
        this.uZk3 = in.readDouble();
        this.uZk4 = in.readDouble();
        this.totalexpns = in.readDouble();
        this.fkfs = in.readString();
        this.spainTotal = in.readString();
        this.docdisc = in.readString();
        this.is_gift = in.readInt();
        this.msg = in.readString();
        this.ly = in.readString();
        this.type = in.readInt();
        this.finallyTotal = in.readDouble();
    }

    public static final Creator<NewConfimOrderBean> CREATOR = new Creator<NewConfimOrderBean>() {
        @Override
        public NewConfimOrderBean createFromParcel(Parcel source) {
            return new NewConfimOrderBean(source);
        }

        @Override
        public NewConfimOrderBean[] newArray(int size) {
            return new NewConfimOrderBean[size];
        }
    };
}

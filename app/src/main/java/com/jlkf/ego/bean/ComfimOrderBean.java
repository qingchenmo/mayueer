package com.jlkf.ego.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @autor zcs
 * @time 2017/9/1
 * @describe
 */

public class ComfimOrderBean extends MyBaseBean {

    /**
     * mData : [{"name":"465465465465","phone":"465","landline":"4654","orderNo":"201709011060720","orderMoney":"37.435567999999996","payType":"预付款","shippingAddress":"654河北省null465"},{"name":"465465465465","phone":"465","landline":"4654","orderNo":"201709011096942","orderMoney":"42.380564","payType":"现金","shippingAddress":"654河北省null465"}]
     * totalPage : null
     * totalRecord : null
     * total : null
     */

    private String total;
    private ArrayList<DataBean> data;
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * name : 465465465465
         * phone : 465
         * landline : 4654
         * orderNo : 201709011060720
         * orderMoney : 37.435567999999996
         * payType : 预付款
         * shippingAddress : 654河北省null465
         */

        private String name;
        private String phone;
        private String landline;
        private String orderNo;
        private String orderMoney;
        private String payType;
        private String shippingAddress;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLandline() {
            return landline;
        }

        public void setLandline(String landline) {
            this.landline = landline;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderMoney() {
            return orderMoney;
        }

        public void setOrderMoney(String orderMoney) {
            this.orderMoney = orderMoney;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.phone);
            dest.writeString(this.landline);
            dest.writeString(this.orderNo);
            dest.writeString(this.orderMoney);
            dest.writeString(this.payType);
            dest.writeString(this.shippingAddress);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.name = in.readString();
            this.phone = in.readString();
            this.landline = in.readString();
            this.orderNo = in.readString();
            this.orderMoney = in.readString();
            this.payType = in.readString();
            this.shippingAddress = in.readString();
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

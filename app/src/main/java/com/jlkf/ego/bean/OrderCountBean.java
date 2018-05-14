package com.jlkf.ego.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @autor zcs
 * @time 2017/8/31
 * @describe
 */

public class OrderCountBean extends MyBaseBean {

    /**
     * mData : [{"msg":"待受理","num":0,"count":0},{"msg":"代发货","num":1,"count":0},{"msg":"配送中","num":2,"count":0},{"msg":"已完成","num":3,"count":0},{"msg":"已取消","num":4,"count":0}]
     * totalPage : null
     * totalRecord : null
     * total : null
     */


    private Object total;
    private List<DataBean> data;

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
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
         * msg : 待受理
         * num : 0
         * count : 0
         */

        @SerializedName("msg")
        private String msgX;
        private int num;
        private int count;

        public String getMsgX() {
            return msgX;
        }

        public void setMsgX(String msgX) {
            this.msgX = msgX;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}

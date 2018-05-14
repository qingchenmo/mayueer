package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/29 0029.
 */

public class StringBean  extends MyBaseBean{

    /**
     * data : {"purl":"http://52.28.215.17:8080//images//adal//201710180630223.jpg"}
     * totalPage : null
     * totalRecord : null
     * total : null
     * totalCount : 0
     */

    private DataBean data;
    @SerializedName("totalPage")
    private Object totalPageX;
    @SerializedName("totalRecord")
    private Object totalRecordX;
    private Object total;
    private int totalCount;

    public static StringBean objectFromData(String str) {

        return new Gson().fromJson(str, StringBean.class);
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getTotalPageX() {
        return totalPageX;
    }

    public void setTotalPageX(Object totalPageX) {
        this.totalPageX = totalPageX;
    }

    public Object getTotalRecordX() {
        return totalRecordX;
    }

    public void setTotalRecordX(Object totalRecordX) {
        this.totalRecordX = totalRecordX;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public static class DataBean {
        /**
         * purl : http://52.28.215.17:8080//images//adal//201710180630223.jpg
         */

        private String purl;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public String getPurl() {
            return purl;
        }

        public void setPurl(String purl) {
            this.purl = purl;
        }
    }
}

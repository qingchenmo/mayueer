package com.jlkf.ego.bean;

/**
 * @autor zcs
 * @time 2017/8/22
 * @describe
 */

public class MyBaseBean {

    private int code;
    private String msg;
    private String totalPage;
    private String totalRecord;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        this.totalRecord = totalRecord;
    }
}

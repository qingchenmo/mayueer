package com.jlkf.ego.newpage.bean;

/**
 * Created by zcs on 2018/5/23.
 */

public class BaseBean {
    private int status;
    private String data;
    private String message;
    private int code;
    private String msg;

    public static int page;
    public static int totalpage;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

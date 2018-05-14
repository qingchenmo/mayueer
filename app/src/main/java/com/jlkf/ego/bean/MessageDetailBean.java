package com.jlkf.ego.bean;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class MessageDetailBean {


    /**
     * mId : 5
     * mTitle : 订单取消
     * mContext : 商品买错了（颜色、尺寸、数量有错误）
     * mCreationtime : 1503893374000
     * msIsunread : 0
     * msLogo : null
     * msUid : 4
     * msManage : null
     */

    private int mId;
    private String mTitle;
    private String mContext;
    private long mCreationtime;
    private int msIsunread;
    private Object msLogo;
    private int msUid;
    private Object msManage;

    public static MessageDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, MessageDetailBean.class);
    }

    public int getMId() {
        return mId;
    }

    public void setMId(int mId) {
        this.mId = mId;
    }

    public String getMTitle() {
        return mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMContext() {
        return mContext;
    }

    public void setMContext(String mContext) {
        this.mContext = mContext;
    }

    public long getMCreationtime() {
        return mCreationtime;
    }

    public void setMCreationtime(long mCreationtime) {
        this.mCreationtime = mCreationtime;
    }

    public int getMsIsunread() {
        return msIsunread;
    }

    public void setMsIsunread(int msIsunread) {
        this.msIsunread = msIsunread;
    }

    public Object getMsLogo() {
        return msLogo;
    }

    public void setMsLogo(Object msLogo) {
        this.msLogo = msLogo;
    }

    public int getMsUid() {
        return msUid;
    }

    public void setMsUid(int msUid) {
        this.msUid = msUid;
    }

    public Object getMsManage() {
        return msManage;
    }

    public void setMsManage(Object msManage) {
        this.msManage = msManage;
    }
}

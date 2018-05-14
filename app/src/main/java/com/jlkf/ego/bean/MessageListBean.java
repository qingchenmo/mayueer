package com.jlkf.ego.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class MessageListBean {


    /**
     * mId : 6
     * mTitle : 地址审核失败
     * mContext : 图片不清楚
     * mCreationtime : 1503910554000
     * msIsunread : null
     * msLogo : null
     * msUid : null
     * msManage : null
     */

    private int mId;
    private String mTitle;
    private String mContext;
    private long mCreationtime;
    private String msIsunread;
    private String msLogo;
    private String msUid;
    private String msManage;

    public static List<MessageListBean> arrayMessageListBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<MessageListBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
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

    public String getMsIsunread() {
        return msIsunread;
    }

    public void setMsIsunread(String msIsunread) {
        this.msIsunread = msIsunread;
    }

    public String getMsLogo() {
        return msLogo;
    }

    public void setMsLogo(String msLogo) {
        this.msLogo = msLogo;
    }

    public String getMsUid() {
        return msUid;
    }

    public void setMsUid(String msUid) {
        this.msUid = msUid;
    }

    public String getMsManage() {
        return msManage;
    }

    public void setMsManage(String msManage) {
        this.msManage = msManage;
    }
}

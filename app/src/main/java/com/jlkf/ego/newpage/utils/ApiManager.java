package com.jlkf.ego.newpage.utils;

import android.text.TextUtils;

import com.jlkf.ego.application.MyApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcs on 2018/5/23.
 */

public class ApiManager {
    public static final String BaseUrl = "http://180.76.168.177:8889/home/";

    /**
     * banner列表
     *
     * @param back
     */
    public static void getBannerList(Object tag, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/banner", null, tag, back);
    }

    /**
     * 品牌列表
     *
     * @param tag
     * @param back
     */
    public static void getBrandList(Object tag, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/brand", null, tag, back);
    }

    /**
     * 主分类列表
     *
     * @param tag
     * @param back
     */
    public static void getGroupList(String brandId, Object tag, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/group/" + (!TextUtils.isEmpty(brandId) ? brandId : ""), null, tag, back);
    }

    /**
     * 二级分类
     *
     * @param groupId 一级分类Id
     * @param back
     */
    public static void getSubtype(int groupId, String brandId, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/subtype/" + groupId + (!TextUtils.isEmpty(brandId) ? ("/" + brandId) : ""), null, o, back);
    }

    /**
     * 获取区号
     *
     * @param o
     * @param back
     */
    public static void getRegionCode(Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/getregioncode", null, o, back);
    }

    /**
     * 生成审核码
     *
     * @param uid
     */
    public static void upauditCode(int uid) {
        HttpUtils.getInstance().get(BaseUrl + "index/upauditcode/" + uid, null, null, new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {

            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 活动列表
     *
     * @param tag
     * @param back
     */
    public static void activitylist(Object tag, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/activitylist", null, tag, back);
    }

    /**
     * 获取版本信息
     *
     * @param tag
     * @param back
     */
    public static void version(Object tag, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/version", null, tag, back);
    }

    /**
     * 获取活动详情1
     *
     * @param o
     * @param id
     * @param back
     */
    public static void getOperateat(Object o, String id, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/getoperateat/" + id, null, o, back);
    }

    /**
     * 获取活动详情2
     *
     * @param o
     * @param id
     * @param back
     */
    public static void getOperateat2(Object o, String id, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/getoperateat/" + id, null, o, back);
    }

    /**
     * 商品列表
     *
     * @param key   搜索关键字
     * @param stype 二级分类
     * @param pp_id 品牌Id
     * @param minp  最低价格
     * @param maxp  最高价格
     * @param page  页码
     * @param o
     * @param back
     */
    public static void getOitmList(String key, String stype, String pp_id, String minp,
                                   String maxp, String page, String attribute, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/oitmlist/" + (TextUtils.isEmpty(key) ? "0" : key) + "/"
                + (TextUtils.isEmpty(stype) ? "0" : stype) + "/" + (TextUtils.isEmpty(pp_id) ? "0" : pp_id) + "/"
                + (TextUtils.isEmpty(minp) ? "0" : minp) + "/" + (TextUtils.isEmpty(maxp) ? "0" : maxp)
                + "/" + (TextUtils.isEmpty(attribute) ? "0" : attribute) + "/" + MyApplication.getmUserBean().getArea() + "/" + page + "/20", null, o, back);
    }

    /**
     * 产品属性
     *
     * @param group 二级分类Id
     * @param o
     * @param back
     */
    public static void getattribute(String group, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/getattribute/" + group, null, o, back);
    }

    /**
     * 活动商品列表
     *
     * @param at_id
     * @param page
     * @param o
     * @param back
     */
    public static void atoitmlist(String at_id, String page, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/atoitmlist/" + at_id + "/" + page + "/20", null, o, back);
    }

    /**
     * 获取审核码状态
     *
     * @param uid
     * @param o
     * @param back
     */
    public static void getCodeStatus(String uid, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/getcodestatus/" + uid, null, o, back);
    }

    /**
     * 验证审核码
     *
     * @param uid
     * @param code
     * @param o
     * @param back
     */
    public static void checkauditcode(String uid, String code, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/checkauditcode/" + uid + "/" + code, null, o, back);
    }

    /**
     * 赠品专区商品
     *
     * @param pp_id
     * @param page
     * @param o
     * @param back
     */
    public static void giftlist(String pp_id, String page, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/giftlist/" + pp_id + "/" + page + "/20", null, o, back);
    }

    /**
     * 确认订单
     *
     * @param uid
     * @param sid
     * @param area
     * @param o
     * @param back
     */
    public static void goSettlement(String uid, String sid, String area, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/goSettlement/" + uid + "/" + sid + "/" + area, null, o, back);
    }

    /**
     * 获取等级信息
     *
     * @param uid
     * @param o
     * @param back
     */
    public static void getRanks(String uid, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/getranks/" + uid, null, o, back);
    }

    /**
     * 获取商品参加的活动
     *
     * @param oitm_id
     * @param o
     * @param back
     */
    public static void getOiat(String oitm_id, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/getoiat/" + oitm_id + "/" + MyApplication.getmUserBean().getArea(), null, o, back);
    }
}

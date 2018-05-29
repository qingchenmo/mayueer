package com.jlkf.ego.newpage.utils;

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
    public static void getGroupList(int brandId, Object tag, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/group/" + (brandId > 0 ? brandId : ""), null, tag, back);
    }

    /**
     * 二级分类
     *
     * @param groupId 一级分类Id
     * @param back
     */
    public static void getSubtype(int groupId, Object o, HttpUtils.OnCallBack back) {
        HttpUtils.getInstance().get(BaseUrl + "index/subtype/" + groupId, null, o, back);
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
        Map<String, String> map = new HashMap<>();
        map.put("at_id", id);
        HttpUtils.getInstance().get(BaseUrl + "index/getoperateat/" + id, null, o, back);
    }
}

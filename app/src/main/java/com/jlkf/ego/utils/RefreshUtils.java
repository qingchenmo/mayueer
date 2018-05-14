package com.jlkf.ego.utils;

import android.content.Context;

import com.jlkf.ego.R;

/**
 * @autor zcs
 * @time 2017/11/30
 * @describe
 */

public class RefreshUtils {

    public static String REFRESH_HEADER_PULLDOWN = "下拉可以刷新";
    public static String REFRESH_HEADER_REFRESHING = "正在刷新...";
    public static String REFRESH_HEADER_LOADING = "正在加载...";
    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
    public static String REFRESH_HEADER_FINISH = "刷新完成";
    public static String REFRESH_HEADER_FAILED = "刷新失败";
    public static String REFRESH_HEADER_LASTTIME = "上次更新 M-d HH:mm";

    public static String REFRESH_FOOTER_PULLUP = "上拉加载更多";
    public static String REFRESH_FOOTER_RELEASE = "释放立即加载";
    public static String REFRESH_FOOTER_REFRESHING = "正在刷新...";
    public static String REFRESH_FOOTER_LOADING = "正在加载...";
    public static String REFRESH_FOOTER_FINISH = "加载完成";
    public static String REFRESH_FOOTER_FAILED = "加载失败";
    public static String REFRESH_FOOTER_ALLLOADED = "全部加载完成";

    public RefreshUtils(Context context) {
        REFRESH_HEADER_PULLDOWN = context.getResources().getString(R.string.xlkysx);
        REFRESH_HEADER_REFRESHING = context.getResources().getString(R.string.zzjz);
        REFRESH_HEADER_LOADING = context.getResources().getString(R.string.zzjz);
        REFRESH_HEADER_RELEASE = context.getResources().getString(R.string.ljsx);
        REFRESH_HEADER_FINISH = context.getResources().getString(R.string.jzwc);
        REFRESH_HEADER_FAILED = context.getResources().getString(R.string.sxsb);
        REFRESH_HEADER_LASTTIME = /*context.getResources().getString(R.string.scgx) +*/ "M-d HH:mm";

        REFRESH_FOOTER_PULLUP = context.getResources().getString(R.string.load_more);
        REFRESH_FOOTER_RELEASE = context.getResources().getString(R.string.ljsx);
        REFRESH_FOOTER_REFRESHING = context.getResources().getString(R.string.zzsx);
        REFRESH_FOOTER_LOADING = context.getResources().getString(R.string.zzjz);
        REFRESH_FOOTER_FINISH = context.getResources().getString(R.string.jzwc);
        REFRESH_FOOTER_FAILED = context.getResources().getString(R.string.jzsb);
        REFRESH_FOOTER_ALLLOADED = context.getResources().getString(R.string.jzwc);

    }
}

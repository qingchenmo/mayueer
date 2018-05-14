package com.jlkf.ego.utils;

import android.content.Context;

import com.jlkf.ego.application.MyApplication;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class CompanyUtil {

    public static int dip2px( float dipValue){
        final float scale = MyApplication.getmContext().getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dip( float pxValue){
        final float scale = MyApplication.getmContext().getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

}

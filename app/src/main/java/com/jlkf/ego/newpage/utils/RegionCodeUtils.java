package com.jlkf.ego.newpage.utils;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.newpage.bean.RegionCodeBean;
import com.jlkf.ego.widget.spiner.ItemInfo;
import com.jlkf.ego.widget.spiner.SpinerPopWindow;

import java.util.List;

/**
 * Created by zcs on 2018/5/25.
 */

public class RegionCodeUtils {
    public static void getRegionCode(final BaseActivity activity, final OnRegionCodeListener listener) {
        activity.setLoading(true);
        ApiManager.getRegionCode(activity, new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                List<ItemInfo> list = JSON.parseArray(response, ItemInfo.class);
                activity.setLoading(false);
                listener.getPopPhoneCodeWindow(list);
            }

            @Override
            public void onError(String msg) {
                activity.setLoading(false);
                activity.showToast(msg, 0);
            }
        });
    }

    public interface OnRegionCodeListener {
        void getPopPhoneCodeWindow(List<ItemInfo> list);
    }
}

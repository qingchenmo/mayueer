package com.jlkf.ego.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.UserBean;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class ShardeUtil {

    public static void putString(String key, String vule) {
        SharedPreferences config = MyApplication.getmContext().getSharedPreferences("config", MyApplication.getmContext().MODE_PRIVATE);
        config.edit().putString(key, vule).commit();
    }


    public static String getString(String key) {
        SharedPreferences config = MyApplication.getmContext().getSharedPreferences("config", MyApplication.getmContext().MODE_PRIVATE);
        return config.getString(key, "");
    }

    public static void putInt(String key, int vule) {
        SharedPreferences config = MyApplication.getmContext().getSharedPreferences("config",MyApplication.getmContext().MODE_PRIVATE);
        config.edit().putInt(key, vule).commit();
    }


    public static int getInt(String key) {
        SharedPreferences config = MyApplication.getmContext().getSharedPreferences("config", MyApplication.getmContext().MODE_PRIVATE);
        return config.getInt(key, 0);
    }

    public static void putUser(UserBean userBean) {
        Gson gson = new Gson();
        String s = gson.toJson(userBean);
        putString("user", s);
    }


    public static UserBean getUser() {
        String string = getString("user");
        if (!TextUtils.isEmpty(string)) {
            Gson gson = new Gson();
            return gson.fromJson(string, UserBean.class);
        } else {
            return null;
        }
    }
}

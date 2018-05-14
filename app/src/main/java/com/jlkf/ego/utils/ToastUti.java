package com.jlkf.ego.utils;

import android.widget.Toast;

import com.jlkf.ego.application.MyApplication;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class ToastUti {

    public static void show(String msg) {
        Toast.makeText(MyApplication.getmContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

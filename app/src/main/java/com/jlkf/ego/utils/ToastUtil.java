package com.jlkf.ego.utils;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.jlkf.ego.application.MyApplication;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class ToastUtil {
    public static void show(String s) {
        Toast.makeText(MyApplication.getmContext(), s, Toast.LENGTH_SHORT).show();
    }

    public static void show( int id) {
        Toast toast = Toast.makeText(MyApplication.getmContext(), "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(View.inflate(MyApplication.getmContext(), id, null));
        toast.show();
    }
}

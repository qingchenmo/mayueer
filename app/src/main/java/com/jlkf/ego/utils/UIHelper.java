package com.jlkf.ego.utils;

import android.content.Context;
import android.content.Intent;

import com.jlkf.ego.activity.AdressActivity;
import com.jlkf.ego.activity.AppServerCenterActivity;
import com.jlkf.ego.activity.EditAdressActivity;
import com.jlkf.ego.activity.MainActivity;
import com.jlkf.ego.activity.OrderActivity;
import com.jlkf.ego.activity.SearchActivity;

import java.io.Serializable;


/**
 * ui跳转的帮助类
 * <p>
 * Created by Administrator on 2017/7/18 0018.
 */

public class UIHelper {

    public static void startOrer(Context context, String tag) {
        Intent intent = null;
        startActivity(context, tag, intent,0, null);
    }

    public static void startOrer(Context context, String tag, Serializable ser) {
        Intent intent = null;
        startActivity(context, tag, intent, 1,ser);
    }

    private static void startActivity(Context context, String tag, Intent intent, int type, Serializable ser) {


        switch (tag) {
            case "order":
                intent = new Intent(context, OrderActivity.class);
                break;
            case "search":
                intent = new Intent(context, SearchActivity.class);
                break;
            case "adress":
                intent = new Intent(context, AdressActivity.class);
                break;
            case "adress_edit":
                intent = new Intent(context, EditAdressActivity.class);
                intent.putExtra("bean", ser);
                intent.putExtra("type", type);
                break;
            case "home":
                intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case "kefu":
                intent = new Intent(context, AppServerCenterActivity.class);
                break;
        }

        context.startActivity(intent);

    }


}

package com.jlkf.ego.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.AppServerCenterActivity;
import com.jlkf.ego.activity.MainActivity;
import com.jlkf.ego.activity.OrderDetailActivity;
import com.jlkf.ego.activity.OrderListActivity;
import com.jlkf.ego.activity.ShapCarActivity;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.MyBaseBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;

import java.util.HashMap;
import java.util.Map;

/**
 * @autor zcs
 * @time 2017/8/28
 * @describe 订单操作工具类
 */

public class OrderUtils<T> {
    private static OrderUtils sUtils;

    private OrderUtils() {

    }

    public static OrderUtils getIntance() {
        if (sUtils == null) sUtils = new OrderUtils();
        return sUtils;
    }

    /**
     * 再次购买
     */
    public void againBuy(final Activity activity, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpUtil.getInstacne(activity).get2(Urls.buyAgain, MyBaseBean.class, map, new HttpUtil.OnCallBack<MyBaseBean>() {
            @Override
            public void success(MyBaseBean data) {
//                ToastUti.show(data.getMsg());
//                Intent intent = new Intent(activity, MainActivity.class);
                Intent intent = new Intent(activity, ShapCarActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("type", "1");
                intent.putExtra("tag", "againBuy");
                activity.startActivity(intent);
            }

            @Override
            public void onError(String msg, int code) {
                ToastUti.show(msg);
            }
        });
    }

    /**
     * 联系客服
     *
     * @param context
     */
    public void ContactServer(Context context) {
        Intent intent = new Intent(context, AppServerCenterActivity.class);
        context.startActivity(intent);
    }

    /**
     * 确认收货
     *
     * @param activity
     * @param id
     */
    public void confimShouhuo(final Activity activity, final String id) {
        View v = LayoutInflater.from(activity).inflate(R.layout.dia_confim_shouhuo, null);
        View cacelView = v.findViewById(R.id.tv_cancel);
        View commitView = v.findViewById(R.id.tv_commit);
        final Dialog dialog = new AlertDialog.Builder(activity)
                .setView(v)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_radius_white);
        cacelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        commitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                HttpUtil.getInstacne(activity).get2(Urls.confirmReceipt, MyBaseBean.class, map, new HttpUtil.OnCallBack<MyBaseBean>() {
                    @Override
                    public void success(MyBaseBean data) {
//                        ToastUti.show(data.getMsg());
                        if (activity instanceof OrderDetailActivity)
                            activity.finish();
                        OrderListActivity.mTabLayout.getTabAt(4).select();
                    }

                    @Override
                    public void onError(String msg, int code) {
                        ToastUti.show(msg);
                    }
                });
            }
        });
    }

    /**
     * 取消订单
     *
     * @param activity
     * @param id
     */
    public void cacelOrder(final Activity activity, final String id) {
        View v = LayoutInflater.from(activity).inflate(R.layout.cancel_order_dialog, null);
        View cacelView = v.findViewById(R.id.tv_cancel);
        View commitView = v.findViewById(R.id.tv_commit);
        final RadioGroup rg_order_cancel = (RadioGroup) v.findViewById(R.id.rg_order_cancel);
        final Dialog dialog = new AlertDialog.Builder(activity)
                .setView(v)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_radius_white);
        cacelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        commitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String msg = null;
                for (int i = 0; i < rg_order_cancel.getChildCount(); i++) {
                    RadioButton button = (RadioButton) rg_order_cancel.getChildAt(i);
                    if (button.isChecked()) {
                        msg = button.getText().toString();
                        break;
                    }
                }
                Map<String, String> map = new HashMap<>();
                map.put("appdocnumber", id);
                map.put("msg", msg);
                map.put("uId", MyApplication.getmUserBean().getUId() + "");
                HttpUtil.getInstacne(activity).get2(Urls.cancelOrder, MyBaseBean.class, map, new HttpUtil.OnCallBack<MyBaseBean>() {
                    @Override
                    public void success(MyBaseBean data) {
                        ToastUti.show(data.getMsg());
                        if (activity instanceof OrderDetailActivity)
                            activity.finish();
                        OrderListActivity.mTabLayout.getTabAt(5).select();
                    }

                    @Override
                    public void onError(String msg, int code) {
                        ToastUti.show(msg);
                    }
                });
            }
        });
        dialog.show();
    }

    /**
     * 修改订单
     *
     * @param activity
     * @param id
     */
    public void updataOrder(final Activity activity, String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpUtil.getInstacne(activity).get2(Urls.updateOrder, MyBaseBean.class, map, new HttpUtil.OnCallBack<MyBaseBean>() {
            @Override
            public void success(MyBaseBean data) {
                ToastUti.show(data.getMsg());
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("tag", "againBuy");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }

            @Override
            public void onError(String msg, int code) {
                ToastUti.show(msg);
            }
        });
    }
}

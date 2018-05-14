package com.jlkf.ego.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jlkf.ego.R;

/**
 * Created by zcs on 2017/7/17.
 * 定义显示居中的Toast
 */

public class CenterToast {
    private static TextView mTextView;

    public static void showToast(Context context, String message) {
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        //初始化布局控件
        mTextView = (TextView) toastRoot.findViewById(R.id.tv_toast);
        //为控件设置属性
        mTextView.setText(message);
        Toast toastStart = new Toast(context);
        toastStart.setGravity(Gravity.CENTER, 0, 0);
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(toastRoot);
        toastStart.show();
    }

}

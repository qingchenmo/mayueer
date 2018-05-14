package com.jlkf.ego.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.WindowManager;

import com.jlkf.ego.R;

/**
 * Created by zcs on 2017/7/13.
 * 创建自定义样式的Dialog
 */

public class BottomDialog extends Dialog{

    public BottomDialog(@NonNull Context context) {
        super(context,R.style.bottomDialog);
    }
    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);

    }
}

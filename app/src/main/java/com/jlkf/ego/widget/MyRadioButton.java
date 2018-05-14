package com.jlkf.ego.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.jlkf.ego.utils.CompanyUtil;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class MyRadioButton extends RadioButton {


    public MyRadioButton(Context context) {
        this(context, null);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        Drawable[] compoundDrawables = getCompoundDrawables();
        Drawable compoundDrawable = compoundDrawables[0];
        compoundDrawable.setBounds(0,0, CompanyUtil.dip2px(24),CompanyUtil.dip2px(26));
        setCompoundDrawables(compoundDrawable,null,null,null);

    }
}

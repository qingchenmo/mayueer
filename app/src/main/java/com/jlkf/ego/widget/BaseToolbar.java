package com.jlkf.ego.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jlkf.ego.R;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class BaseToolbar extends Toolbar implements View.OnClickListener {


    public final static int WITH_ACTIVITY = 0 ;
    public final static int WITH_FRAGMENT = 1 ;

    private int with = WITH_ACTIVITY;
    private Activity baseActivity;

    private TextView mTitle,mRightText;

    public void setRight(boolean right) {
        isRight = right;
        if (right){
            mRightText.setVisibility(VISIBLE);

            mRightText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRightListener != null){
                        onRightListener.onRight(v);
                    }
                }
            });
        } else {

            mRightText.setOnClickListener(null);

            mRightText.setVisibility(GONE);
        }
    }


    private boolean isRight = false;

    public BaseToolbar(Context context) {
        this(context,null);
    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public void setRightText(String rightts){
        if (isRight && !TextUtils.isEmpty(rightts)){
            mRightText.setText(rightts);
        }
    }


    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.toolbar_my,this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mRightText = (TextView) findViewById(R.id.tv_right);



    }

    public void with(Activity baseActivity){
        this.baseActivity = baseActivity;

    }

    public void setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            mTitle.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                if (baseActivity != null){
                    baseActivity.finish();
                }

                break;
        }
    }

    public void setOnRightListener(OnRightListener onRightListener) {
        this.onRightListener = onRightListener;
    }

    OnRightListener onRightListener;

    public interface OnRightListener{
        void onRight(View v);
    }
}

package com.jlkf.ego.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.utils.ToastUti;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class SelectView extends RelativeLayout implements View.OnClickListener {

    LinearLayout ivDelet;
    TextView etNumber;
    LinearLayout ivAdd;

    public void setValue(int value) {
        this.value = value;
    }

    private int curVaule;

    private int value = 1;

    public void setMax(int max) {
        this.max = max;
    }

    private int max = 6;
    private int min = 0;
    private Context context;

    public SelectView(Context context) {
        this(context, null);
    }

    public SelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_select, this);

        ivDelet = (LinearLayout) findViewById(R.id.iv_delet);
        etNumber = (TextView) findViewById(R.id.et_number);

        ivAdd = (LinearLayout) findViewById(R.id.iv_add);

        ivAdd.setOnClickListener(this);
        ivDelet.setOnClickListener(this);

    }

    /**
     * 选择数量
     */
    public class WriteListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            final View inflate = LayoutInflater.from(context).inflate(R.layout.add_shop_car_dia, null);
            final Dialog dialog = new AlertDialog.Builder(context).setView(inflate).create();
            dialog.getWindow().setBackgroundDrawable(new BitmapDrawable());

            inflate.findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            inflate.findViewById(R.id.tv_commit).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText et = (EditText) inflate.findViewById(R.id.et_num);
                    int number = Integer.parseInt(et.getText().toString().trim());
                    if (number > max) {
                        number = max;
                    }
                    setCurVaule(number);
                    if (onCommitListener != null) {
                        onCommitListener.onCurVaule(number);
                    }
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }


    private void delet() {

        if (curVaule - value < min) {
            ToastUti.show(gS(R.string.bnzx));
            curVaule = min;
        } else {
            curVaule = curVaule - value;
        }
        if (mOnCurVauleListener != null) {
            mOnCurVauleListener.onCurVaule(curVaule + "");
        }
        setCurVaule(curVaule);
    }

    public String gS(int id) {
        return getResources().getString(id);
    }


    public void setCurVaule(int vaule) {
        this.curVaule = vaule;

        if (etNumber != null && !TextUtils.isEmpty(String.valueOf(vaule))) {
            etNumber.setText(vaule + "");
        }
    }

    private void add() {
        if (curVaule + value > max) {
            ToastUti.show(gS(R.string.bnzd));
            curVaule = max;
        } else {
            curVaule = curVaule + value;
        }
        if (mOnCurVauleListener != null) {
            mOnCurVauleListener.onCurVaule(curVaule + "");
        }
        setCurVaule(curVaule);
    }

    public void setNOListener() {
        ivAdd.setOnClickListener(null);
        ivDelet.setOnClickListener(null);
        etNumber.setFocusableInTouchMode(false);
    }

    @Override
    public void onClick(View v) {
        String trim = etNumber.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {

            curVaule = Integer.valueOf("0");
        } else {
            curVaule = Integer.valueOf(trim);

        }
        switch (v.getId()) {
            case R.id.iv_delet:
                delet();
                break;
            case R.id.iv_add:
                add();
                break;
        }
    }

    public void setOnCurVauleListener(OnCurVauleListener onCurVauleListener) {
        mOnCurVauleListener = onCurVauleListener;
    }

    public OnCurVauleListener mOnCurVauleListener;


    public interface OnCurVauleListener {
        void onCurVaule(String vaule);
    }


    public void setOnCommitListener(OnCommitListener onCommitListener) {
        this.onCommitListener = onCommitListener;
    }

    public OnCommitListener onCommitListener;


    public interface OnCommitListener {
        void onCurVaule(int vaule);
    }

}

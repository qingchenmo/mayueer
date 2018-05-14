package com.jlkf.ego.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;

/**
 * Created by zcs on 2017/7/16.
 * 标题View
 */

public class TitleView extends FrameLayout implements View.OnClickListener {
    private ImageView iv_title_left, iv_title_right, iv_title_right_left;
    private TextView tv_title_center, tv_title_right_imgtop, tv_title_right;
    private EditText et_title;
    private View viewLine;
    private Context mContext;
    private RelativeLayout rl_item;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context, attrs);

    }

    public void setOnRightListener(OnRightListener onRightListener) {
        this.onRightListener = onRightListener;
    }

    OnRightListener onRightListener;

    public interface OnRightListener {
        void onRight(View v);
    }

    public void setBg(int id){
        rl_item.setBackgroundColor(getResources().getColor(id));
    }
    /**
     * 初始化布局
     */
    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.title_layout1, this, false);
        tv_title_center = (TextView) view.findViewById(R.id.tv_title_center);
        tv_title_right_imgtop = (TextView) view.findViewById(R.id.tv_title_right_imgtop);
        et_title = (EditText) view.findViewById(R.id.et_title);
        iv_title_left = (ImageView) view.findViewById(R.id.iv_title_left);
        iv_title_right = (ImageView) view.findViewById(R.id.iv_title_right);
        rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);

        iv_title_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRightListener != null) {
                    onRightListener.onRight(v);
                }
            }
        });


        iv_title_right_left = (ImageView) view.findViewById(R.id.iv_title_right_left);
        tv_title_right = (TextView) view.findViewById(R.id.tv_right);
        viewLine = view.findViewById(R.id.view_line);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        if (typedArray != null) {
            switch (typedArray.getInt(R.styleable.TitleView_view_type, 4)) {
                case 0:
                    setView(true, false, true, false, false, false, false, typedArray);
                    iv_title_left.setOnClickListener(this);
                    break;
                case 1:
                    setView(true, false, false, false, false, false, false, typedArray);
                    iv_title_left.setOnClickListener(this);
                    break;
                case 2:
                    setView(true, false, false, true, false, false, false, typedArray);
                    break;
                case 3:
                    setView(true, false, false, true, false, true, false, typedArray);
                    iv_title_left.setOnClickListener(this);
                    break;
                case 4:
                    setView(true, true, true, false, false, false, false, typedArray);
                    iv_title_left.setOnClickListener(this);
                    break;
                case 5:
                    setView(false, false, false, true, false, false, true, typedArray);
                    break;
                case 6:
                    setView(false, true, true, false, true, false, false, typedArray);
                    break;
                case 7:
                    setView(false, false, false, false, false, false, true, typedArray);
                    break;
                case 8:
                    setView(false, false, true, false, false, false, true, typedArray);
                    break;
                case 9:
                    setView(true, false, true, false, false, false, true, typedArray);
                    iv_title_left.setOnClickListener(this);
                    break;
            }

        }
        addView(view);
    }

    /**
     * 设置View的显示和隐藏，并赋值
     *
     * @param iv_left       左侧图片按钮
     * @param iv_right      右侧图片按钮
     * @param tv_title      标题
     * @param et_edit       输入框
     * @param iv_left_right 右侧第二个按钮
     * @param tv_rightImg   右侧复选框
     * @param tv_right      右侧文字按钮
     * @param typedArray    属性
     */
    private void setView(boolean iv_left, boolean iv_right, boolean tv_title
            , boolean et_edit, boolean iv_left_right, boolean tv_rightImg, boolean tv_right, TypedArray typedArray) {
        String titleText = typedArray.getString(R.styleable.TitleView_title);
        int leftImgRes = typedArray.getResourceId(R.styleable.TitleView_left_img, R.drawable.app_title_back);
        String etHintText = typedArray.getString(R.styleable.TitleView_et_hint);
        int rightImgRes = typedArray.getResourceId(R.styleable.TitleView_right_img, R.drawable.icon_sao);
        int leftRightImg = typedArray.getResourceId(R.styleable.TitleView_left_right_img, R.drawable.icon_sao);
        String tv_rightText = typedArray.getString(R.styleable.TitleView_tv_right);
        viewLine.setVisibility(typedArray.getBoolean(R.styleable.TitleView_showLine, true) ? VISIBLE : GONE);
        boolean editAble = typedArray.getBoolean(R.styleable.TitleView_edit_able, true);
        et_title.setFocusable(editAble);
        if (iv_left) {
            iv_title_left.setVisibility(VISIBLE);
            iv_title_left.setImageResource(leftImgRes);
        }
        if (iv_right) {
            iv_title_right.setVisibility(VISIBLE);
            iv_title_right.setImageResource(rightImgRes);
        }
        if (tv_title) {
            tv_title_center.setVisibility(VISIBLE);
            tv_title_center.setText(titleText);
        }
        if (et_edit) {
            et_title.setVisibility(VISIBLE);
            et_title.setHint(etHintText);
        }
        if (iv_left_right) {
            iv_title_right_left.setVisibility(VISIBLE);
            iv_title_right_left.setImageResource(leftRightImg);
        }
        if (tv_rightImg) {
            tv_title_right_imgtop.setVisibility(VISIBLE);
        }
        if (tv_right) {
            tv_title_right.setVisibility(VISIBLE);
            tv_title_right.setText(tv_rightText);
        }
    }

    public void setTitle(String str) {
        tv_title_center.setText(str);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                ((Activity) mContext).finish();
                break;
        }
    }
}

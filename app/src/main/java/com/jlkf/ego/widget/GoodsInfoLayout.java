package com.jlkf.ego.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.ConfimOrderBean;
import com.jlkf.ego.bean.OrderBean;
import com.jlkf.ego.utils.NumberUtil;

import java.util.List;

import static com.jlkf.ego.R.id.iv_left;
import static com.jlkf.ego.application.MyApplication.mContext;

/**
 * Created by zcs on 2017/7/18.
 * 商品清单
 */

public class GoodsInfoLayout extends FrameLayout {
    private ImageView ivLeft, ivMiddle, ivRight, ivMore;
    private TextView tv_product_price, tv_product_num;
    private LinearLayout ll_goods;

    public GoodsInfoLayout(@NonNull Context context) {
        this(context, null);
    }

    public GoodsInfoLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsInfoLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = LayoutInflater.from(context).inflate(R.layout.order_img_layout, null);
        ivLeft = (ImageView) v.findViewById(iv_left);
        ivMiddle = (ImageView) v.findViewById(R.id.iv_middle);
        ivRight = (ImageView) v.findViewById(R.id.iv_right);
        ivMore = (ImageView) v.findViewById(R.id.iv_order_img_more);
        tv_product_price = (TextView) v.findViewById(R.id.tv_product_price);
        tv_product_num = (TextView) v.findViewById(R.id.tv_product_num);
        ll_goods = (LinearLayout) v.findViewById(R.id.ll_goods);
        addView(v);
    }

    public void setBackGrand(int color) {
        ll_goods.setBackgroundColor(getResources().getColor(color));

    }

    /**
     * 设置商品图片
     *
     * @param
     */
    public void setImg(List<ConfimOrderBean.DataBean.ListDataBean> listDataBeen) {
        if (listDataBeen.size() == 1) {
            Glide.with(mContext).load(listDataBeen.get(0).getLogo()).error(R.drawable.icon_img_load).into(ivLeft);
        } else if (listDataBeen.size() == 2) {
            Glide.with(mContext).load(listDataBeen.get(0).getLogo()).error(R.drawable.icon_img_load).into(ivLeft);
            Glide.with(mContext).load(listDataBeen.get(1).getLogo()).error(R.drawable.icon_img_load).into(ivMiddle);
        } else if (listDataBeen.size() >= 3) {
            Glide.with(mContext).load(listDataBeen.get(0).getLogo()).error(R.drawable.icon_img_load).into(ivLeft);
            Glide.with(mContext).load(listDataBeen.get(1).getLogo()).error(R.drawable.icon_img_load).into(ivMiddle);
            Glide.with(mContext).load(listDataBeen.get(2).getLogo()).error(R.drawable.icon_img_load).into(ivRight);
        }

        if (listDataBeen.size() > 3) {
            ivMore.setVisibility(VISIBLE);
        } else {
            ivMore.setVisibility(GONE);
        }
    }

    /**
     * 设置商品数量
     *
     * @param num
     */
    public void setNum(String num) {
        tv_product_num.setText(mContext.getString(R.string.o_gong) + num + mContext.getString(R.string.o_jian));
    }


    /**
     * 设置商品价格
     *
     * @param price
     */
    public void setPrice(String price) {
        tv_product_price.setText(mContext.getString(R.string.money) + price);
    }

    /**
     * 控制商品图片的显示与隐藏
     *
     * @param middleImg 中间的图片
     * @param rightImg  右侧图片
     * @param moreImg   更多
     */
    public void imgGone(boolean middleImg, boolean rightImg, boolean moreImg) {
        if (middleImg) {
            ivMiddle.setVisibility(GONE);
            ivRight.setVisibility(GONE);
            ivMore.setVisibility(GONE);
        }
        if (rightImg) {
            ivRight.setVisibility(GONE);
            ivMore.setVisibility(GONE);
        }
        if (moreImg) {
            ivMore.setVisibility(GONE);
        }
    }

    public void setView(OrderBean.DataBean dataBean) {
        List<OrderBean.DataBean.OorderDetailBean> list = dataBean.getOorderDetail();
        int num = 0;
        double price = 0;
        for (int i = 0; i < list.size(); i++) {
            OrderBean.DataBean.OorderDetailBean oorderDetailBean = list.get(i);
            num = num + list.get(i).getQuantity();
            price = price + Double.valueOf(oorderDetailBean.getPrice()) * oorderDetailBean.getQuantity();
            if (i == 0) {
                Glide.with(mContext).load(oorderDetailBean.getLogo()).into(ivLeft);
            } else if (i == 1) {
                Glide.with(mContext).load(oorderDetailBean.getLogo()).into(ivMiddle);
            } else if (i == 2) {
                Glide.with(mContext).load(oorderDetailBean.getLogo()).into(ivRight);
            }
        }

        ivMiddle.setVisibility(list.size() > 1 ? View.VISIBLE : View.GONE);
        ivRight.setVisibility(list.size() > 2 ? View.VISIBLE : View.GONE);
        ivMore.setVisibility(list.size() > 3 ? View.VISIBLE : View.GONE);
        tv_product_num.setText(mContext.getString(R.string.o_gong) + num + mContext.getString(R.string.o_jian));
        tv_product_price.setText(mContext.getString(R.string.money) + NumberUtil.fomater(price));
    }

    public void setView(List<OrderBean.DataBean.OorderDetailBean> list, String doctotal) {
        int num = 0;
        for (int i = 0; i < list.size(); i++) {
            OrderBean.DataBean.OorderDetailBean oorderDetailBean = list.get(i);
            num = num + list.get(i).getQuantity();
            if (i == 0) {
                Glide.with(mContext).load(oorderDetailBean.getLogo()).into(ivLeft);
            } else if (i == 1) {
                Glide.with(mContext).load(oorderDetailBean.getLogo()).into(ivMiddle);
            } else if (i == 2) {
                Glide.with(mContext).load(oorderDetailBean.getLogo()).into(ivRight);
            }
        }

        ivMiddle.setVisibility(list.size() > 1 ? View.VISIBLE : View.GONE);
        ivRight.setVisibility(list.size() > 2 ? View.VISIBLE : View.GONE);
        ivMore.setVisibility(list.size() > 3 ? View.VISIBLE : View.GONE);
        tv_product_num.setText(mContext.getString(R.string.o_gong) + num + mContext.getString(R.string.o_jian));
        tv_product_price.setText(mContext.getString(R.string.money) + doctotal);
    }
}

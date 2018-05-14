package com.jlkf.ego.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.OrderBean;
import com.jlkf.ego.utils.NumberUtil;

import java.util.List;

/**
 * Created by zcs on 2017/7/14.
 * 订购/实发清单适配器
 */

public class OrderOrIssusAdapter extends RecyclerView.Adapter {
    private List<OrderBean.DataBean.OorderDetailBean> mList;
    private Context mContext;
    private int type;
    public static final int ORDERLIST = 0;
    public static final int ISSUSLIST = 1;

    public OrderOrIssusAdapter(Context context, List<OrderBean.DataBean.OorderDetailBean> list, int type) {
        mContext = context;
        mList = list;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.item_order_or_issus_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        OrderBean.DataBean.OorderDetailBean bean = mList.get(position);
        viewHolder.tv_product_name.setText(bean.getName());
        viewHolder.tv_product_code.setText(bean.getItemcode());
        viewHolder.tv_product_num.setText("x" + bean.getQuantity());
        viewHolder.tv_product_price.setText(mContext.getString(R.string.o_dj) + "  " + mContext.getString(R.string.money) + NumberUtil.fomater(Double.valueOf(bean.getPrice())));
        viewHolder.tv_product_price_all.setText(mContext.getString(R.string.o_xj) + "  " + mContext.getString(R.string.money) + NumberUtil.fomater((Double.valueOf(bean.getPrice()) * bean.getQuantity())));
        Glide.with(mContext).load(bean.getLogo()).placeholder(R.drawable.icon_img_load).error(R.drawable.icon_img_load_failed).into(viewHolder.iv_product_img);
        if (type == ISSUSLIST) {
            viewHolder.tv_product_price.setVisibility(View.GONE);
        } else {
            viewHolder.tv_product_price.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_product_img;
        private TextView tv_product_code, tv_product_name, tv_product_price, tv_product_num, tv_product_price_all;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_product_img = (ImageView) itemView.findViewById(R.id.iv_product_img);
            tv_product_code = (TextView) itemView.findViewById(R.id.tv_product_code);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_product_price = (TextView) itemView.findViewById(R.id.tv_product_price);
            tv_product_num = (TextView) itemView.findViewById(R.id.tv_product_num);
            tv_product_price_all = (TextView) itemView.findViewById(R.id.tv_product_price_all);
        }
    }
}

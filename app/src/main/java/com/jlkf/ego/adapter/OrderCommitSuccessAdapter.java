package com.jlkf.ego.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.MainActivity;
import com.jlkf.ego.activity.OrderListActivity;
import com.jlkf.ego.bean.ComfimOrderBean;
import com.jlkf.ego.utils.NumberUtil;

import java.util.List;

/**
 * Created by zcs on 2017/7/14.
 * 订单提交成功适配器
 */

public class OrderCommitSuccessAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context mContext;
    private List<ComfimOrderBean.DataBean> mList;

    public OrderCommitSuccessAdapter(Context context, List<ComfimOrderBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1)
            return new HeardViewHolder(LayoutInflater.from(mContext).inflate(R.layout.order_commit_success_layout, parent, false));
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_order_commit_success_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == 1) return;
        ViewHolder viewHolder = (ViewHolder) holder;
        ComfimOrderBean.DataBean bean = mList.get(position - 1);
        if (position == mList.size()) {
            viewHolder.lin_order_commit_bottom.setVisibility(View.VISIBLE);
            /*viewHolder.tv_order_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(mContext, OrderDetailActivity.class);
                    intent1.putExtra("AppDocNumber", mList.get(position - 1).getOrderNo());
                    intent1.putExtra("type", 1);
                    mContext.startActivity(intent1);
                }
            });*/
            viewHolder.tv_order_detail.setOnClickListener(this);
            viewHolder.tv_order_out.setOnClickListener(this);
        } else {
            viewHolder.lin_order_commit_bottom.setVisibility(View.GONE);
        }
        viewHolder.tv_goods_person.setText(bean.getName());
        viewHolder.tv_goods_address.setText(bean.getShippingAddress());
        viewHolder.tv_goods_phone.setText(bean.getPhone());
        viewHolder.tv_goods_code.setText(bean.getOrderNo());
        viewHolder.tv_goods_pay_type.setText(bean.getPayType());
        try {
            viewHolder.tv_goods_price.setText(mContext.getString(R.string.money) + NumberUtil.fomater(Double.valueOf(bean.getOrderMoney())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_order_detail:
                Intent intent1 = new Intent(mContext, OrderListActivity.class);
                intent1.putExtra("select", 1);
                intent1.putExtra("type", 1);
                mContext.startActivity(intent1);
                break;
            case R.id.tv_order_out:
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
                break;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_goods_person, tv_goods_phone, tv_goods_address, tv_goods_code,
                tv_goods_pay_type, tv_goods_price, tv_order_detail, tv_order_out;
        private LinearLayout lin_order_commit_bottom;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_goods_person = (TextView) itemView.findViewById(R.id.tv_goods_person);
            tv_goods_phone = (TextView) itemView.findViewById(R.id.tv_goods_phone);
            tv_goods_address = (TextView) itemView.findViewById(R.id.tv_goods_address);
            tv_goods_code = (TextView) itemView.findViewById(R.id.tv_goods_code);
            tv_goods_pay_type = (TextView) itemView.findViewById(R.id.tv_goods_pay_type);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tv_order_detail = (TextView) itemView.findViewById(R.id.tv_order_detail);
            tv_order_out = (TextView) itemView.findViewById(R.id.tv_order_out);
            lin_order_commit_bottom = (LinearLayout) itemView.findViewById(R.id.lin_order_commit_bottom);
        }
    }

    static class HeardViewHolder extends RecyclerView.ViewHolder {

        public HeardViewHolder(View itemView) {
            super(itemView);
        }
    }
}

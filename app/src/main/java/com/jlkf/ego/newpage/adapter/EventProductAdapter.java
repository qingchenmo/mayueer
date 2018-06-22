package com.jlkf.ego.newpage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.newpage.activity.EventProductActivity;
import com.jlkf.ego.newpage.activity.ValidationActivity;
import com.jlkf.ego.newpage.inter.OnItemClickListener;
import com.jlkf.ego.newpage.utils.GiftAddUtils;
import com.jlkf.ego.utils.ProductAddShopCarUtils;

import java.util.List;

/**
 * Created by zcs on 2018/5/18.
 */

public class EventProductAdapter extends RecyclerView.Adapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<ProductListBean.DataBean> mList;
    private OnItemClickListener<ProductListBean.DataBean> mListener;
    private int mType;

    public EventProductAdapter(int type, Context context, List<ProductListBean.DataBean> atpics,
                               OnItemClickListener<ProductListBean.DataBean> listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = atpics;
        mListener = listener;
        mType = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_event_product_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.et_product_select_num.setFocusable(false);
        ClickListener listener = new ClickListener(viewHolder, position);
        viewHolder.et_product_select_num.setOnClickListener(listener);
        viewHolder.iv_product_num_add.setOnClickListener(listener);
        viewHolder.iv_product_num_sub.setOnClickListener(listener);
        viewHolder.lin_product_package_small.setOnClickListener(listener);
        viewHolder.lin_product_package_large.setOnClickListener(listener);
        final ProductListBean.DataBean info = mList.get(position);
        if (!(info.getUUB() == null || info.getUUB().isEmpty()) && !(info.getUUX() == null || info.getUUX().isEmpty())) {
            viewHolder.lin_product_package_small.setVisibility(View.VISIBLE);
            viewHolder.lin_product_package_large.setVisibility(View.VISIBLE);
            info.setHavaPackage(true);
        } else {
            viewHolder.lin_product_package_small.setVisibility(View.INVISIBLE);
            viewHolder.lin_product_package_large.setVisibility(View.INVISIBLE);
        }
        if (mType == EventProductActivity.ZENGPIN)
            info.setSelectNum(GiftAddUtils.getInstance().getGoodsNumInShopCar(info.getItemcode()));
        else
            info.setSelectNum(ProductAddShopCarUtils.getInstance().getGoodsNumInShopCar(info.getItemcode()));
        viewHolder.tv_product_price.setText(mContext.getString(R.string.money) + info.getPrice());
        viewHolder.tv_product_name.setText(info.getItemname());
        viewHolder.tv_product_stock.setText(info.getOnhand());
        viewHolder.et_product_select_num.setText(String.valueOf(info.getSelectNum()));
        viewHolder.tv_product_num_large.setText(info.getUUX());
        viewHolder.tv_product_num_small.setText(info.getUUB());
        Log.e("tag", info.getPicturname());
        Glide.with(mContext).load(info.getPicturname()).placeholder(R.drawable.icon_img_load).error(R.drawable.icon_img_load_failed).into(viewHolder.iv_product_img);
        if (info.isHavaPackage()) {
            viewHolder.lin_product_package_large.setSelected(info.isLargePackage());
            viewHolder.lin_product_package_small.setSelected(info.isSmallPackage());
        }
        if (!MyApplication.mHasComfim) {
            viewHolder.mLinEdit.setVisibility(View.GONE);
            viewHolder.tv_product_price.setText(mContext.getString(R.string.money) + "****");
            viewHolder.tv_product_price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ValidationActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
        viewHolder.iv_product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.itemClickListener(info, position);
            }
        });
        if (mType == EventProductActivity.ZENGPIN) {
            viewHolder.mTvEvent.setVisibility(View.VISIBLE);
        } else {
            if (info.getAttype() == 1) {
                viewHolder.mTvEvent.setVisibility(View.VISIBLE);
                viewHolder.mTvEvent.setText("秒杀");
            } else if (info.getAttype() == 2) {
                viewHolder.mTvEvent.setVisibility(View.VISIBLE);
                viewHolder.mTvEvent.setText("赠品");
            } else if (info.getAttype() == 3) {
                viewHolder.mTvEvent.setVisibility(View.VISIBLE);
                viewHolder.mTvEvent.setText("预定");
            } else {
                viewHolder.mTvEvent.setVisibility(View.GONE);
            }
            if (info.getDiscountnum().equals("0")) {
                viewHolder.mTvActivityContent.setVisibility(View.GONE);
            } else {
                viewHolder.mTvActivityContent.setVisibility(View.VISIBLE);
//                viewHolder.mTvActivityContent.setText("满" + info.getDiscountnum() + "件立减" + ((int) (100 - Double.valueOf(info.getDiscount()) * 10)) + "%");
                viewHolder.mTvActivityContent.setText("满" + info.getDiscountnum() + "件立减" + info.getDiscount() + "%");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ClickListener implements View.OnClickListener {
        private ItemViewHolder holder;
        private int position;

        public ClickListener(ItemViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lin_product_package_large:
                    if (mList.get(position).isLargePackage()) {
                        mList.get(position).setLargePackage(false);
//                        holder.iv_product_package_large.setImageResource(R.mipmap.icon_large_package);
                        holder.lin_product_package_large.setSelected(false);
                        ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                        return;
                    }
                    ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                    holder.lin_product_package_large.setSelected(true);
                    holder.lin_product_package_small.setSelected(false);
                    mList.get(position).setLargePackage(true);
                    mList.get(position).setSmallPackage(false);
                    break;
                case R.id.lin_product_package_small:
                    if (mList.get(position).isSmallPackage()) {
                        mList.get(position).setSmallPackage(false);
                        holder.lin_product_package_small.setSelected(false);
                        ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                        return;
                    }
                    ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                    holder.lin_product_package_small.setSelected(true);
                    holder.lin_product_package_large.setSelected(false);
                    mList.get(position).setLargePackage(false);
                    mList.get(position).setSmallPackage(true);
                    break;
                case R.id.iv_product_num_add:
                    if (mType == EventProductActivity.ZENGPIN)
                        GiftAddUtils.getInstance().EditShopCar(true, mList.get(position), holder.et_product_select_num);
                    else
                        ProductAddShopCarUtils.getInstance().EditShopCar(true, mList.get(position), holder.et_product_select_num, (Activity) mContext, null, holder.iv_product_img);
                    ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                    break;
                case R.id.iv_product_num_sub:
                    if (mType == EventProductActivity.ZENGPIN)
                        GiftAddUtils.getInstance().EditShopCar(false, mList.get(position), holder.et_product_select_num);
                    else
                        ProductAddShopCarUtils.getInstance().EditShopCar(false, mList.get(position), holder.et_product_select_num, (Activity) mContext, null, holder.iv_product_img);
                    ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                    break;
                case R.id.et_product_select_num:
//                    DialogUtil.addShopCarDia(mContext, false, mList.get(position), holder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, holder.iv_product_img);
                    break;
            }
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_product_num_add, iv_product_num_sub, iv_product_img, iv_product_package_large, iv_product_package_small;
        private TextView tv_product_name, tv_product_price, tv_product_stock,
                tv_product_num_large, tv_product_num_small;
        private LinearLayout lin_product_package_large, lin_product_package_small;
        private TextView et_product_select_num;
        private LinearLayout mLinEdit;
        private TextView mTvEvent;
        private TextView mTvActivityContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_product_img = (ImageView) itemView.findViewById(R.id.iv_product_img);
            iv_product_num_add = (ImageView) itemView.findViewById(R.id.iv_product_num_add);
            iv_product_num_sub = (ImageView) itemView.findViewById(R.id.iv_product_num_sub);
            lin_product_package_large = (LinearLayout) itemView.findViewById(R.id.lin_product_package_large);
            lin_product_package_small = (LinearLayout) itemView.findViewById(R.id.lin_product_package_small);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_product_price = (TextView) itemView.findViewById(R.id.tv_product_price);
            tv_product_stock = (TextView) itemView.findViewById(R.id.tv_product_stock);
            et_product_select_num = (TextView) itemView.findViewById(R.id.et_product_select_num);
            tv_product_num_large = (TextView) itemView.findViewById(R.id.tv_product_num_large);
            tv_product_num_small = (TextView) itemView.findViewById(R.id.tv_product_num_small);
            iv_product_package_large = (ImageView) itemView.findViewById(R.id.iv_product_package_large);
            iv_product_package_small = (ImageView) itemView.findViewById(R.id.iv_product_package_small);
            mLinEdit = itemView.findViewById(R.id.lin_edit);
            mTvEvent = itemView.findViewById(R.id.tv_event);
            mTvActivityContent = itemView.findViewById(R.id.tv_activity_content);
        }
    }
}

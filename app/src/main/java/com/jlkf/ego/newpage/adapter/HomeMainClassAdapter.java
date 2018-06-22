package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.newpage.bean.BrandBean;
import com.jlkf.ego.newpage.bean.IconBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcs on 2018/5/14.
 */

public class HomeMainClassAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClickListener<Object> mListener;
    private List<BrandBean> mBrandList;
    private List<IconBean> mIconList;

    public HomeMainClassAdapter(Context context, List<IconBean> iconList, List<BrandBean> brandList, OnItemClickListener<Object> listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListener = listener;
        mBrandList = brandList;
        mIconList = iconList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater
                .inflate(R.layout.item_home_main_class_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        if (position < mIconList.size()) {
            final IconBean bean = mIconList.get(position);
            Glide.with(mContext).load(bean.getMinlogo()).into(viewHolder.ivImg);
            viewHolder.tvName.setText(bean.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.itemClickListener(bean, position);
                }
            });
        } else {
            final BrandBean bean = mBrandList.get(position);
            Glide.with(mContext).load(bean.getPp_minlogo()).into(viewHolder.ivImg);
            viewHolder.tvName.setText(bean.getPp_name());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.itemClickListener(bean, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if ((mIconList.size() + mBrandList.size()) > 8) {
            return 8;
        } else {
            return mIconList.size() + mBrandList.size();
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

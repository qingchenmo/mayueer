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
import com.jlkf.ego.newpage.inter.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcs on 2018/5/23.
 */

public class MoreBrandAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<BrandBean> mList;
    private OnItemClickListener<BrandBean> mListener;

    public MoreBrandAdapter(Context context, List<BrandBean> list, OnItemClickListener<BrandBean> listener) {
        mContext = context;
        mList = list;
        mListener = listener;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_more_brand_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final BrandBean bean = mList.get(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        Glide.with(mContext).load(bean.getPp_minlogo()).fitCenter().into(viewHolder.ivImg);
        viewHolder.tvContent.setText(bean.getPp_context());
        viewHolder.tvTitle.setText(bean.getPp_name());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.itemClickListener(bean, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

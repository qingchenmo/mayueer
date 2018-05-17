package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.newpage.inter.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcs on 2018/5/14.
 */

public class HomeMainClassAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClickListener<Object> mListener;

    public HomeMainClassAdapter(Context context, OnItemClickListener<Object> listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater
                .inflate(R.layout.item_home_main_class_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        if (position == 0) {
            viewHolder.ivImg.setImageResource(R.mipmap.catalog_icon_goods);
            viewHolder.tvName.setText("全部商品");
        } else if (position == 1) {
            viewHolder.ivImg.setImageResource(R.mipmap.catalog_icon_new);
            viewHolder.tvName.setText("本月上新");
        }else if (position == 2) {
            viewHolder.ivImg.setImageResource(R.mipmap.catalog_icon_hot);
            viewHolder.tvName.setText("必点单品");
        }else if (position == 3) {
            viewHolder.ivImg.setImageResource(R.mipmap.catalog_icon_cheap);
            viewHolder.tvName.setText("促销产品");
        }else if (position==4){
            viewHolder.ivImg.setImageResource(R.mipmap.catalog_icon_dem);
            viewHolder.tvName.setText("Digital DEM");
        }else if (position==5){
            viewHolder.ivImg.setImageResource(R.mipmap.catalog_icon_home);
            viewHolder.tvName.setText("HOME");
        }else if (position==6){
            viewHolder.ivImg.setImageResource(R.mipmap.catalog_icon_prolink);
            viewHolder.tvName.setText("Prolink");
        }else if (position==7){
            viewHolder.ivImg.setImageResource(R.mipmap.catalog_icon_asm);
            viewHolder.tvName.setText("ASM");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.itemClickListener("", position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
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

package com.jlkf.ego.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.GoodsBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
public class IImageAdapter extends RecyclerView.Adapter<IImageAdapter.ImageViewHolder> {

    List<GoodsBean.ShopcartBean> shopcart;
    private Context mContext;

    public IImageAdapter(Activity context, List<GoodsBean.ShopcartBean> shopcart) {
        this.shopcart = shopcart;
        this.mContext = context;

    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.holder_image, parent, false);
        return new ImageViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        GoodsBean.ShopcartBean shopcartBean = shopcart.get(position);
        Glide.with(mContext).load(shopcartBean.getLogo()).into(holder.iv);

    }

    @Override
    public int getItemCount() {
        if (shopcart.size() > 0) {
            return shopcart.size();
        }
        return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;

        public ImageViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}

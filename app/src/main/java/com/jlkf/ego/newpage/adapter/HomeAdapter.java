package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.ProductQuickAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcs on 2018/5/14.
 * 首页适配器
 */

public class HomeAdapter extends RecyclerView.Adapter {

    public static final int BANNER = 1;//banner图
    public static final int MAINCLASS = 2; // 主分类
    public static final int SECONDCLASS = 3; //分类列表


    private LayoutInflater mInflater;
    private Context mContext;

    public HomeAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER)
            return new BannerHolder(mInflater.inflate(R.layout.item_home_banner_layout, parent, false));
        else if (viewType == MAINCLASS)
            return new HomeMainClassViewHolder(mInflater.inflate(R.layout.item_home_main_class_layout, parent, false));
        else
            return new HomeBottomListViewHolder(mInflater.inflate(R.layout.item_home_bottom_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == BANNER) {
            BannerHolder bannerHolder = (BannerHolder) holder;
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(R.mipmap.banner);
            }
            bannerHolder.banner.setImageLoader(new ProductQuickAdapter.GlideImageLoader());
            bannerHolder.banner.setImages(list);
            bannerHolder.banner.start();
        } else if (viewType == MAINCLASS) {
            HomeMainClassViewHolder mainClassViewHolder = (HomeMainClassViewHolder) holder;
            mainClassViewHolder.mainClassRecycleView.setAdapter(new HomeMainClassAdapter(mContext));
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return BANNER;
        else if (position == 1) return MAINCLASS;
        else return SECONDCLASS;
    }

    static class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner banner;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class HomeMainClassViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main_class_recycle_view)
        RecyclerView mainClassRecycleView;
        @BindView(R.id.tv_more_brand)
        TextView tvMoreBrand;

        public HomeMainClassViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mainClassRecycleView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 4));
        }
    }

    static class HomeBottomListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_class_img)
        ImageView ivClassImg;
        @BindView(R.id.tv_class_title)
        TextView tvClassTitle;
        @BindView(R.id.tv_class_content)
        TextView tvClassContent;

        public HomeBottomListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.adapter.ProductQuickAdapter;
import com.jlkf.ego.newpage.activity.MoreBrandActivity;
import com.jlkf.ego.newpage.bean.BannerBean;
import com.jlkf.ego.newpage.bean.BrandBean;
import com.jlkf.ego.newpage.bean.GroupBean;
import com.jlkf.ego.newpage.bean.IconBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

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
    private OnItemClickListener<Object> mListener;
    private List<BannerBean> mBannerList;
    private List<BrandBean> mBrandList;
    private List<GroupBean> mGroupList;
    private List<IconBean> mIconList;

    public HomeAdapter(Context context, List<BannerBean> bannerList, List<IconBean> iconList, List<BrandBean> brandList, List<GroupBean> groupList, OnItemClickListener<Object> listener) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mListener = listener;
        mBannerList = bannerList;
        mBrandList = brandList;
        mGroupList = groupList;
        mIconList = iconList;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == BANNER) {
            final BannerHolder bannerHolder = (BannerHolder) holder;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < mBannerList.size(); i++) {
                list.add(mBannerList.get(i).getImgurl());
            }
            bannerHolder.banner.setImageLoader(new ProductQuickAdapter.GlideImageLoader());
            bannerHolder.banner.setImages(list);
            bannerHolder.banner.setIndicatorGravity(BannerConfig.RIGHT);
            bannerHolder.banner.start();
            bannerHolder.banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    mListener.itemClickListener(bannerHolder.banner, position);
                }
            });
        } else if (viewType == MAINCLASS) {
            HomeMainClassViewHolder mainClassViewHolder = (HomeMainClassViewHolder) holder;
            mainClassViewHolder.mainClassRecycleView.setAdapter(new HomeMainClassAdapter(mContext, mIconList, mBrandList, mListener));
            mainClassViewHolder.tvMoreBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MoreBrandActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else {
            HomeBottomListViewHolder bottomListViewHolder = (HomeBottomListViewHolder) holder;
            final GroupBean bean = mGroupList.get(position - 2);
            Glide.with(mContext).load(bean.getPicture()).fitCenter().into(bottomListViewHolder.ivClassImg);
            bottomListViewHolder.tvClassTitle.setText(bean.getItmsGrpNam());
            bottomListViewHolder.tvClassContent.setText(bean.getMark());
            bottomListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.itemClickListener(bean, position - 2);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mGroupList.size() + 2;
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

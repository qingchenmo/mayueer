package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.adapter.ProductQuickAdapter;
import com.jlkf.ego.newpage.activity.EventProductActivity;
import com.jlkf.ego.newpage.bean.EventOneTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcs on 2018/5/29.
 */

public class EventOneTypeAdapter extends RecyclerView.Adapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private EventOneTypeBean mBean;
    private List<String> url = new ArrayList<>();

    public EventOneTypeAdapter(Context context, EventOneTypeBean mOneTypeBean) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBean = mOneTypeBean;
        if (mBean.getBanners() != null) {
            for (int i = 0; i < mBean.getBanners().size(); i++) {
                url.add(mBean.getBanners().get(i).getBanner());
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1)
            return new HomeAdapter.BannerHolder(mInflater.inflate(R.layout.item_activity_banner_layout, parent, false));
        return new EventAdapter.TopViewHolder(mInflater.inflate(R.layout.item_event_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 1) {
            HomeAdapter.BannerHolder bannerHolder = (HomeAdapter.BannerHolder) holder;
            bannerHolder.banner.setImageLoader(new ProductQuickAdapter.GlideImageLoader());
            bannerHolder.banner.setImages(url);
            bannerHolder.banner.start();
        } else {
            EventAdapter.TopViewHolder topViewHolder = (EventAdapter.TopViewHolder) holder;
            Glide.with(mContext).load(mBean.getAtpics().get(position - 1).getAtpic()).into(topViewHolder.mView);
            topViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EventProductActivity.class);
                    intent.putExtra("id", mBean.getAtpics().get(position - 1).getAtpicv());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 1;
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (mBean.getAtpics() == null) return 1;
        return mBean.getAtpics().size() + 1;
    }
}

package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.adapter.ProductQuickAdapter;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.newpage.bean.EventOneTypeBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcs on 2018/5/16.
 *
 * @describe:
 */

public class EventAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private EventOneTypeBean mBean;
    private List<String> url = new ArrayList<>();

    private OnItemClickListener<ProductListBean.DataBean> mListener;

    public EventAdapter(Context context, EventOneTypeBean mOneTypeBean, OnItemClickListener<ProductListBean.DataBean> listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBean = mOneTypeBean;
        mListener = listener;
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
        return new ItemViewHolder(mInflater.inflate(R.layout.item_event_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            HomeAdapter.BannerHolder bannerHolder = (HomeAdapter.BannerHolder) holder;
            bannerHolder.banner.setImageLoader(new ProductQuickAdapter.GlideImageLoader());
            bannerHolder.banner.setImages(url);
            bannerHolder.banner.start();
        } else {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            Glide.with(mContext).load(mBean.getAtpics().get(position - 1).getAtpic()).into(viewHolder.iv);
            viewHolder.recyclerView.setAdapter(new EventProductAdapter(1,mContext, mBean.getAtpics().get(position - 1).getOitmlist(), mListener));
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

    static class TopViewHolder extends RecyclerView.ViewHolder {
        ImageView mView;

        public TopViewHolder(View itemView) {
            super(itemView);
            mView = (ImageView) itemView;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.recycler_view)
        RecyclerView recyclerView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}

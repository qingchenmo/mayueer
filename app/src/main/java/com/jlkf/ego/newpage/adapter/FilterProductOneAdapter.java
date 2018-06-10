package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.newpage.bean.FilterProductBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcs on 2018/5/17.
 *
 * @describe:
 */

public class FilterProductOneAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<FilterProductBean> mList;

    public FilterProductOneAdapter(Context context, List<FilterProductBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_filter_product_one_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mTvTitle.setText(mList.get(position).getName());
        viewHolder.mRecyclerView.setAdapter(new FilterProductTwoAdapter(mContext, mList.get(position)));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.recycler_view)
        RecyclerView mRecyclerView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRecyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 3));
        }
    }
}

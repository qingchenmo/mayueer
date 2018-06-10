package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.newpage.bean.FilterProductBean;

import java.util.ArrayList;

/**
 * Created by zcs on 2018/5/17.
 *
 * @describe:
 */

public class FilterProductTwoAdapter extends RecyclerView.Adapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private FilterProductBean mBean;

    public FilterProductTwoAdapter(Context context, FilterProductBean bean) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBean = bean;
        if (mBean.getValue() == null) mBean.setValue(new ArrayList<String>());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_filter_product_two_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mView.setText(mBean.getValue().get(position));
        viewHolder.mView.setSelected(mBean.getSelectIndex() == position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBean.getSelectIndex() == position) mBean.setSelectIndex(-1);
                else mBean.setSelectIndex(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBean.getValue().size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mView = (TextView) itemView;
        }
    }
}

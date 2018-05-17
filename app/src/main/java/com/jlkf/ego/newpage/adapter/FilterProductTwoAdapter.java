package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.newpage.bean.FilterProductBean;

import java.util.List;

/**
 * Created by zcs on 2018/5/17.
 *
 * @describe:
 */

public class FilterProductTwoAdapter extends RecyclerView.Adapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<FilterProductBean.AttriBean> mList;

    public FilterProductTwoAdapter(Context context, List<FilterProductBean.AttriBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_filter_product_two_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mView.setText(mList.get(position).getName());
        viewHolder.mView.setSelected(mList.get(position).isSelect());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setSelect(false);
                }
                mList.get(position).setSelect(true);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mView = (TextView) itemView;
        }
    }
}

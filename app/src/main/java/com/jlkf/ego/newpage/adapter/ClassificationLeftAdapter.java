package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.newpage.inter.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcs on 2018/5/15.
 *
 * @describe:
 */

public class ClassificationLeftAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mList;
    private int mNowSelectIndex = 0;
    private OnItemClickListener<String> mListener;

    public ClassificationLeftAdapter(Context context, List<String> list, OnItemClickListener<String> listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
        mListener = listener;
        mListener.itemClickListener(mList.get(0), 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_classification_left_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        viewHolder.mTvContent.setText(mList.get(position));
        viewHolder.mTvContent.setSelected(mNowSelectIndex == position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNowSelectIndex = position;
                mListener.itemClickListener(mList.get(position), position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        TextView mTvContent;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jlkf.ego.R;

/**
 * Created by zcs on 2018/5/16.
 *
 * @describe:
 */

public class EventAdapter extends RecyclerView.Adapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public EventAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_event_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        if (position % 2 == 0) viewHolder.mView.setImageResource(R.mipmap.pic4);
        else viewHolder.mView.setImageResource(R.mipmap.pic5);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mView = (ImageView) itemView;
        }
    }
}

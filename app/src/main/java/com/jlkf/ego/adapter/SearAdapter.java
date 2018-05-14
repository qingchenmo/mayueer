package com.jlkf.ego.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jlkf.ego.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class SearAdapter extends RecyclerView.Adapter {

    private List mData;
    private Context context;

    public SearAdapter(Context context ,List mData) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.holder_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mData.size() > 0 && mData != null){
            return mData.size();
        }
        return 0;
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

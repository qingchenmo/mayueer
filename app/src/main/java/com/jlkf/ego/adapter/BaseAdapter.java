package com.jlkf.ego.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {


    public BaseAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public int getColor(int id) {
        return mContext.getResources().getColor(id);
    }

    @Override
    protected void convert(com.chad.library.adapter.base.BaseViewHolder helper, T item) {
        getView(helper, item);
    }

    protected abstract void getView(com.chad.library.adapter.base.BaseViewHolder helper, T item);

    class BaseViewHolder extends com.chad.library.adapter.base.BaseViewHolder {

        public BaseViewHolder(View view) {
            super(view);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(int pos);
    }

    public int gColor(int id) {
        return mContext.getResources().getColor(id);
    }

    public void setOnClick(final com.chad.library.adapter.base.BaseViewHolder helper, View tv) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(helper.getLayoutPosition());
                }
            }
        });
    }

}

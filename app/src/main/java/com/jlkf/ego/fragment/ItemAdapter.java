package com.jlkf.ego.fragment;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jlkf.ego.R;
import com.jlkf.ego.adapter.BaseAdapter;
import com.jlkf.ego.bean.GoodTypeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
public class ItemAdapter extends BaseAdapter<GoodTypeBean> {
    private TextView mTvProductName;

    public ItemAdapter(@LayoutRes int layoutResId, @Nullable List<GoodTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void getView(final BaseViewHolder helper, GoodTypeBean item) {

        mTvProductName = (TextView) helper.getView(R.id.tv_item);
        ImageView rl_item = helper.getView(R.id.rl_item);

        mTvProductName.setText(item.getItmsgrpnam());
        RequestManager with = Glide.with(mContext);
        with.load(item.getPicture()).into(rl_item);

        if (helper.getLayoutPosition() % 4 == 0 || helper.getLayoutPosition() % 4 == 3) {
            rl_item.setBackgroundColor(mContext.getResources().getColor(R.color.color_d8d8d8));
        } else {
            rl_item.setBackgroundColor(mContext.getResources().getColor(R.color.color_808080));
        }

        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(helper.getLayoutPosition());
                }
            }
        });
    }
}

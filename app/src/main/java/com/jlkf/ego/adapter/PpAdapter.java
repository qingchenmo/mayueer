package com.jlkf.ego.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.BrandListBean;
import com.zzhoujay.richtext.RichText;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
public class PpAdapter extends BaseAdapter<BrandListBean> {

    public PpAdapter(@LayoutRes int layoutResId, @Nullable List<BrandListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void getView(final com.chad.library.adapter.base.BaseViewHolder helper, BrandListBean brandListBean) {

        ImageView iv_brand_logo = helper.getView(R.id.iv_brand_logo);
        LinearLayout ll_item = helper.getView(R.id.ll_item);
        View view = helper.getView(R.id.view);
        TextView tv_brand_name = (TextView) helper.getView(R.id.tv_brand_name);
        TextView brand_first_word = (TextView) helper.getView(R.id.brand_first_word);

        tv_brand_name.setText(brandListBean.getName());
        if (!TextUtils.isEmpty(brandListBean.getPpContext())) {
            RichText.fromHtml(brandListBean.getPpContext()).into(brand_first_word);
        }
        Glide.with(mContext).load(brandListBean.getPpMaxlogo()).into(iv_brand_logo);

        brand_first_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(helper.getLayoutPosition());
                }
            }
        });
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(helper.getLayoutPosition());
                }
            }
        });

        if (helper.getLayoutPosition() == getItemCount() - 1) {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

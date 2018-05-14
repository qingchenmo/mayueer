package com.jlkf.ego.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;

import java.util.List;

/**
 * Created by zcs on 2017/7/12.
 * 本类快选商品图片适配器
 */

public class ProductQuickInfoImgAdapter extends PagerAdapter {
    private Context mContext;
    private List<View> mList;
    private List<String> imgList;

    public ProductQuickInfoImgAdapter(Context context, List<View> list, List<String> imgList) {
        this.mList = list;
        this.mContext = context;
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView v = (ImageView) ((LinearLayout) mList.get(position)).getChildAt(0);
        Glide.with(mContext).load(imgList.get(position)).fitCenter().placeholder(R.drawable.icon_img_load).error(R.drawable.icon_img_load_failed).into(v);
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }
}

package com.jlkf.ego.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.BrandListBean;

import java.util.List;

/**
 * Created by zcs on 2017/7/12.
 * 商品列表筛选品牌列表适配器
 */

public class ProductShaiXuanAdapter extends BaseAdapter {
    private Context mContext;
    private List<BrandListBean> mList;
    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public ProductShaiXuanAdapter(Context context, List<BrandListBean> list) {
        this.mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FrameLayout fl_layout;
        ImageView view;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_img_only_layout, null);
            fl_layout = (FrameLayout) convertView.findViewById(R.id.fl_layout);
            view = (ImageView) convertView.findViewById(R.id.iv);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            convertView.setTag(fl_layout);
        } else {
            fl_layout = (FrameLayout) convertView.getTag();
            view = (ImageView) fl_layout.getChildAt(0);
        }
//        view.setAlpha(mList.get(position).isSelect() ? 0.5f : 1f);
        fl_layout.setSelected(mList.get(position).isSelect());
//        Glide.with(mContext).load(mList.get(position).getPpMinlogo())/*.placeholder(R.drawable.icon_img_load).error(R.drawable.icon_img_load_failed)*/.into((ImageView) fl_layout.getChildAt(0));
        Glide.with(mContext).load(mList.get(position).getPpMinlogo()).asBitmap().centerCrop().into(new BitmapImageViewTarget(view) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(8);
                view.setImageDrawable(circularBitmapDrawable);
            }
        });


        return convertView;
    }
}

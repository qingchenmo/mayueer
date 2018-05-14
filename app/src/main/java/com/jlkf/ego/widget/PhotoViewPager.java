package com.jlkf.ego.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * @autor zcs
 * @time 2017/8/11
 * @describe
 */

public class PhotoViewPager extends ViewPager {
    private Context mContext;
    private List<View> mList;

    public PhotoViewPager(Context context) {
        super(context);
    }

    public PhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            //uncomment if you really want to see these errors
            //e.printStackTrace();
            return false;
        }

    }

    public void setData(List<String> list) {
        if (mList == null)
            mList = new ArrayList<>();
        else mList.clear();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            PhotoView view = new PhotoView(mContext);
            Glide.with(mContext).load(list.get(i)).into(view);
            mList.add(view);
        }
        this.setAdapter(new Adapter());
    }

    class Adapter extends PagerAdapter {

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
            container.addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }

}

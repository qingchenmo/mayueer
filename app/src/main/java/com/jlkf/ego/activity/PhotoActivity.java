package com.jlkf.ego.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.widget.BaseViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017/10/24.
 */
public class PhotoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.vp)
    BaseViewPager mVp;
    @BindView(R.id.tv)
    TextView mTv;
    private List<GoodsBean.ShopcartBean> mData;
    private int mPos;

    @Override
    public void initView() {
        setContentView(R.layout.act_photo);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mData = (List<GoodsBean.ShopcartBean>) getIntent().getSerializableExtra("data");

        mPos = getIntent().getIntExtra("pos", 0);
        mVp.setAdapter(new PhotoAdapter());
        mVp.addOnPageChangeListener(this);

        mVp.setCurrentItem(mPos);
        mTv.setText(mPos + 1 + "/" + mData.size());
    }

    @Override
    public void iniActivityAnimation() {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTv.setText(position + 1 + "/" + mData.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class PhotoAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.pager_photo, container, false);
            PhotoView photoView = (PhotoView) inflate.findViewById(R.id.photo_view);

            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            Glide.with(mContext).load(mData.get(position).getLogo()).into(photoView);
            Log.v("图片：", mData.get(position).getLogo());
            container.addView(inflate);
            return inflate;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}

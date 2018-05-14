package com.jlkf.ego.activity;

import com.jlkf.ego.R;
import com.jlkf.ego.widget.PhotoViewPager;

import java.util.ArrayList;

public class BitImgActivity extends BaseActivity {
    private PhotoViewPager mViewPager;
    private int select;

    @Override
    public void initView() {
        setContentView(R.layout.activity_bit_img);
        mViewPager = (PhotoViewPager) findViewById(R.id.viewPager);
        ArrayList<String> imgList = getIntent().getStringArrayListExtra("imgList");
        select = getIntent().getIntExtra("select", 0);
        if (imgList != null) {
            mViewPager.setData(imgList);
            mViewPager.setCurrentItem(select);
        }
    }

    @Override
    public void iniActivityAnimation() {

    }

}

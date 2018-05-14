package com.jlkf.ego.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.ProductQuickAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.ProductAddShopCarUtils;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.widget.TitleView;
import com.jlkf.ego.widget.VerticalViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcs
 *         本类快选界面
 */
public class ProductQuickSelectActivity extends BaseActivity implements View.OnClickListener, ProductAddShopCarUtils.OnAddShopCarListener {
    private TitleView mTitle;
    private VerticalViewPager mVpProductInfo;
    private String codeBars;
    private int mPage = 1;
    private List<ProductListBean.DataBean> mList;
    private RelativeLayout rl_search_no_data;
    private FrameLayout fl_shop_car, fl_parent;
    private TextView tv_car_num;
    private boolean noMore;
    private ProductQuickAdapter mAdapter;
    private int mPosition;
    private ImageButton mImageButton;

    //    private static final float MIN_SCALE = 0.75f;
//    private static final float MIN_ALPHA = 0.75f;
    @Override
    public void initView() {
        setContentView(R.layout.activity_product_quick_select);
        mTitle = (TitleView) findViewById(R.id.title);
        rl_search_no_data = (RelativeLayout) findViewById(R.id.rl_search_no_data);
        mVpProductInfo = (VerticalViewPager) findViewById(R.id.vp_product_quick_select);
        fl_shop_car = (FrameLayout) findViewById(R.id.fl_shop_car);
        tv_car_num = (TextView) findViewById(R.id.tv_car_num);
        fl_parent = (FrameLayout) findViewById(R.id.fl_parent);
        mVpProductInfo.setOnPageChangeListener(new PageChangeListener());
//        mTitle.setTitle(getIntent().getStringExtra("productName"));
        mTitle.setTitle(getResources().getString(R.string.blkx));
        codeBars = getIntent().getStringExtra("codeBars");
        fl_shop_car.setOnClickListener(this);
        mTitle.setOnRightListener(new TitleView.OnRightListener() {
            @Override
            public void onRight(View v) {
                Intent intent = new Intent(ProductQuickSelectActivity.this, SearchProductActivity.class);
                startActivity(intent);
            }
        });
        mVpProductInfo.setAdapter(mAdapter = new ProductQuickAdapter(this, mList = new ArrayList<>(), mVpProductInfo, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition < mList.size() - 1)
                    mVpProductInfo.setCurrentItem(mPosition + 1);
            }
        }));
//        initPage();
        refreshData();
        mImageButton = (ImageButton) findViewById(R.id.ib_first);
        if (TextUtils.isEmpty(ShardeUtil.getString("qiock"))) {
            ShardeUtil.putString("qiock", "true");
            mImageButton.setVisibility(View.VISIBLE);
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mImageButton.setVisibility(View.GONE);
                }
            });
        } else {
            mImageButton.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        ProductAddShopCarUtils.getInstance().setShopCarNum(tv_car_num);
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化VIewPAger
     */
    private void initPage() {
        mVpProductInfo.setAdapter(new ProductQuickAdapter(this, mList, mVpProductInfo, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition < mList.size() - 1)
                    mVpProductInfo.setCurrentItem(mPosition + 1);
            }
        }));
    }

    @Override
    public void iniActivityAnimation() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_shop_car:
//                Intent intent1 = new Intent(this, MainActivity.class);
                Intent intent1 = new Intent(this, ShapCarActivity.class);
                intent1.putExtra("tag", "againBuy");
                intent1.putExtra("type", "1");
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void addShopCarListener(final ImageView productImgView) {
        hideSoftKeyboard();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProductAddShopCarUtils.getInstance().showAnimation(ProductQuickSelectActivity.this, productImgView, fl_shop_car, fl_parent);
                ProductAddShopCarUtils.getInstance().startAlarm(ProductQuickSelectActivity.this);
                ProductAddShopCarUtils.getInstance().setShopCarNum(tv_car_num);
            }
        }, 100);
    }

    /**
     * 本类快选ViewPager滑动监听
     */
    class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mPosition = position;
//            mTitle.setTitle(mList.get(position).getItemname());

            if (mList.size() - 1 == position && !noMore) {
                refreshData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            mList.get(mPosition).setIsColler(data.getStringExtra("coller"));
            Log.e("tag", mList.get(mPosition).getIsColler() + "");
            mVpProductInfo.setAdapter(new ProductQuickAdapter(this, mList, mVpProductInfo, this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPosition < mList.size() - 1)
                        mVpProductInfo.setCurrentItem(mPosition + 1);
                }
            }));
            mVpProductInfo.setCurrentItem(mPosition);
        }

    }

    private void refreshData() {
        Map<String, String> map = new HashMap<>();
        map.put("area", MyApplication.getmUserBean().getArea());
        map.put("codeBars", codeBars);
        map.put("pageNo", mPage + "");
        map.put("pageSize", "10");
        map.put("fast", "0");
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        HttpUtil.getInstacne(this).get2(Urls.getOitmBySecond, ProductListBean.class, map, new HttpUtil.OnCallBack<ProductListBean>() {
            @Override
            public void success(ProductListBean data) {
                if (mPage == 0)
                    mList.clear();
                if (Integer.parseInt(data.getTotalPage()) == /*(mPage - 1)*/mPage) {
                    noMore = true;
                }
                mPage++;
                mList.addAll(data.getData());
                mVpProductInfo.getAdapter().notifyDataSetChanged();
                rl_search_no_data.setVisibility(View.GONE);
            }

            @Override
            public void onError(String msg, int code) {
                rl_search_no_data.setVisibility(View.VISIBLE);
            }
        });
    }
}

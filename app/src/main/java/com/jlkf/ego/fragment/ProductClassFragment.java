package com.jlkf.ego.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.ProductListActivity;
import com.jlkf.ego.adapter.ProductListClassifyOneAdapter;
import com.jlkf.ego.adapter.ProductListClassifyTwoAdaper;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.GoodTypeBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 分类
 */
public class ProductClassFragment extends BaseFragment implements ProductListClassifyOneAdapter.OnItemClickListener, ProductListClassifyTwoAdaper.OnItemClickListener {
    @BindView(R.id.rv_productList_classifyOne)
    RecyclerView rvProductListClassifyOne;
    @BindView(R.id.rv_productList_classifyTwo)
    RecyclerView rvProductListClassifyTwo;
    Unbinder unbinder;
    private String mParam1;
    private String mParam2;
    public static final String VIEWTYPE = "viewType";
    private int viewType = -1;
    private List<GoodTypeBean> mList;
    private ProductListClassifyTwoAdaper mAdapter;
    private List<GoodTypeBean> mGoodTypeBeen;
    private Intent mIntent;
    private int basePosition = 0;

    public ProductClassFragment() {
    }


    public static ProductClassFragment newInstance(int viewType) {
        ProductClassFragment fragment = new ProductClassFragment();
        Bundle args = new Bundle();
        args.putInt("VIEWTYPE", viewType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_product_class, null, false);
    }

    @Override
    public void initView() {
        if (getArguments() != null)
            viewType = getArguments().getInt(VIEWTYPE);
        unbinder = ButterKnife.bind(this, rootView);
        initProductListClassifyOne();
        tag = 0;
        basePosition = 0;
        initProductListClassifyTwo(0);
    }

    private int tag = 0;

    @Override
    public void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("area", MyApplication.getmUserBean().getArea());
        HttpUtil.getInstacne(mActivity).get(Urls.itemgetList, String.class, map, new HttpUtil.OnCallBack<String>() {
            @Override
            public void success(String data) {
                mGoodTypeBeen = GoodTypeBean.arrayGoodTypeBeanFromData(data);

                int[] goodImgs = {R.drawable.good_all_seletor, R.drawable.good_charger_seletor, R.drawable.good_battery_seletor,
                        R.drawable.good_holster_seletor, R.drawable.good_headset_seletor, R.drawable.good_keyboard_seletor,
                        R.drawable.good_shell_seletor, R.drawable.good_sex_seletor, R.drawable.good_handbag_seletor,
                        R.drawable.good_sticker_seletor, R.drawable.good_wire_seletor, R.drawable.good_home_seletor,
                        R.drawable.good_power_seletor, R.drawable.good_stereo_seletor, R.drawable.good_sport_seletor,
                        R.drawable.good_stents_seletor, R.drawable.good_other_seletor};
//                , R.drawable.good_selfie_seletor
//                for (int i = 0; i < goodTypeBeen.size(); i++) {
//                    goodTypeBeen.get(i).setDrawable(goodImgs[i]);
//                }
                GoodTypeBean goodTypeBean = new GoodTypeBean();
                goodTypeBean.setItmsgrpnam("全部");
                goodTypeBean.setDrawable(R.drawable.good_all_seletor);
                goodTypeBean.setItemgroupId(mGoodTypeBeen.size());

                mGoodTypeBeen.add(0, goodTypeBean);
                rvProductListClassifyOne.setAdapter(new ProductListClassifyOneAdapter(mContext, mGoodTypeBeen, ProductClassFragment.this, viewType));


            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    /**
     * 初始化商品列表一级分类
     */
    private void initProductListClassifyOne() {

        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvProductListClassifyOne.setLayoutManager(manager);

    }

    /**
     * 初始化商品列表二级分类
     */
    private void initProductListClassifyTwo(int position) {
        List<GoodTypeBean> list = new ArrayList<>();//模拟数据
        for (int i = 0; i < 20; i++) {
            GoodTypeBean bean = new GoodTypeBean();
            bean.setDrawable(R.mipmap.ic_launcher);
            list.add(bean);
        }


        Map<String, String> map = new HashMap<>();
        map.put("area", MyApplication.getmUserBean().getArea());
        if (position == 0) {

            map.put("itmsGrpCod", "");
        } else {
            map.put("itmsGrpCod", mGoodTypeBeen.get(position).getItmsgrpcod());
        }
        HttpUtil.getInstacne(mActivity).get(Urls.getSecondList, String.class, map, new HttpUtil.OnCallBack<String>() {

            @Override
            public void success(String data) {
                Log.v("-------------", data);
                mList = new ArrayList<GoodTypeBean>();
                String[] strings = new Gson().fromJson(data, String[].class);
                for (int i = 0; i < strings.length; i++) {
                    GoodTypeBean goodTypeBean = new GoodTypeBean();
                    goodTypeBean.setDrawable(0);
                    goodTypeBean.setItmsgrpnam(strings[i]);
                    mList.add(goodTypeBean);
                }
                if (tag == 0) {

                    setRecycler(mList, viewType);
                } else {
                    mAdapter.getList().clear();
                    mAdapter.getList().addAll(mList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override

            public void onError(String msg, int code) {

            }
        });


    }

    private void setRecycler(List<GoodTypeBean> list, int viewType) {
        RecyclerView.LayoutManager manager;
        if (viewType == 0) {
            manager = new GridLayoutManager(mContext, 2);
        } else {
            manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        }
        rvProductListClassifyTwo.setLayoutManager(manager);
        mAdapter = new ProductListClassifyTwoAdaper(mContext, list, this, viewType);
        rvProductListClassifyTwo.setAdapter(mAdapter);
    }



    @Override
    public void clickListener(int position) {
        mIntent = new Intent(mContext, ProductListActivity.class);
        mIntent.putExtra("itmsGrpCod", mGoodTypeBeen.get(position).getItmsgrpcod());
        startActivity(mIntent);
    }

    /**
     * 第一个recycler的点击事件
     *
     * @param position
     */
    @Override
    public void clickLisener(int position) {
        mIntent = new Intent(mContext, ProductListActivity.class);
        mIntent.putExtra("itmsGrpCod", mGoodTypeBeen.get(position).getItmsgrpcod());
        startActivity(mIntent);

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {

    }
}

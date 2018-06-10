package com.jlkf.ego.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.ShopCarItemActivity;
import com.jlkf.ego.adapter.NoShopAdapter;
import com.jlkf.ego.adapter.NoShopAdapter2;
import com.jlkf.ego.adapter.ShopCarAdater3;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.Connection;
import com.jlkf.ego.bean.Connection2;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.newpage.bean.RefreshShopCar;
import com.jlkf.ego.widget.FullyGridLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/23.
 */
public class ShopCarFragment3 extends BaseFragment {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.recycler_no_shop)
    RecyclerView mRecyclerNoShop;
    @BindView(R.id.ll_no_data)
    LinearLayout mLlNoData;
    List<Connection2> mdataBeen = new ArrayList<>();
    List<Connection> mdataBeen2 = new ArrayList<>();

    List<GoodsBean> mGoodsBeanist = new ArrayList<>();
    private ShopCarAdater3 mAdapter;
    private NoShopAdapter mNoShopAdapter;
    private NoShopAdapter2 mNoShopAdapter2;
    private FullyGridLayoutManager mLayoutManager;


    @Override
    public View getContentView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.fragment_shop3, null);

        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void initView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ShopCarAdater3(mActivity, mGoodsBeanist, this);
        mRecycler.setAdapter(mAdapter);

        mLlNoData.setVisibility(View.GONE);

        mLayoutManager = new FullyGridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new FullyGridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        mRecyclerNoShop.setLayoutManager(mLayoutManager);
        mRecyclerNoShop.setNestedScrollingEnabled(false);
        if (getArguments() != null && getArguments().getString("flag").equals("noMain")) {
            mTitle.setVisibility(View.GONE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == ShopCarItemActivity.BACK) {
            initShopCarData();
        }

    }

    @Override
    public void initData() {
        initShopCarData();
    }

    /**
     * 加载购物车
     */
    public void initShopCarData() {
        final Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        HttpUtil.getInstacne((Activity) mContext).get(Urls.list, String.class, map, new HttpUtil.OnCallBack<String>() {
            @Override
            public void success(String data) {
                List<GoodsBean> goodsBeen = GoodsBean.arrayGoodsBeanFromData(data);

                if (goodsBeen.size() == 0) {
                    show(1);
                } else {

                    show(0);
                    mAdapter.getGoodsBeen().clear();
                    mAdapter.getGoodsBeen().addAll(goodsBeen);
                    EventBus.getDefault().post(goodsBeen.get(mAdapter.getmPosition()));
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    public void show(int tag) {
        if (tag == 0) {
            mLlNoData.setVisibility(View.GONE);
            mRecycler.setVisibility(View.VISIBLE);

//            initShopCarData();
        } else {
            mRecycler.setVisibility(View.GONE);
            mLlNoData.setVisibility(View.VISIBLE);

            initNoShopCarData();
        }
    }

    /**
     * 加载没有数据时候的购物车
     */
    public void initNoShopCarData() {

        final Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        map.put("pageNo", "1");
        map.put("pageSize", "4");
        HttpUtil.getInstacne(mActivity).get(Urls.getMyColler, String.class, map, new HttpUtil.OnCallBack<String>() {

            @Override
            public void success(String data) {
                List<Connection> dataBeen = new Gson().fromJson(data, new TypeToken<List<Connection>>() {
                }.getType());
                if (dataBeen.size() == 0) {

                    if (mNoShopAdapter == null) {
                        mNoShopAdapter = new NoShopAdapter(mContext, mdataBeen, ShopCarFragment3.this, mLayoutManager);
                    }
                    mRecyclerNoShop.setAdapter(mNoShopAdapter);

                    Map<String, String> map = new HashMap<>();
                    map.put("area", MyApplication.getmUserBean().getArea());
                    map.put("pageNo", "1");
                    map.put("pageSize", "4");
                    map.put("type", "4");
                    map.put("sapNo", MyApplication.getmUserBean().getSapNo() + "");
                    HttpUtil.getInstacne(mActivity).get(Urls.recommendListTwo, String.class, map, new HttpUtil.OnCallBack<String>() {
                        @Override
                        public void success(String data) {
                            List<Connection2> dataBeen = new Gson().fromJson(data, new TypeToken<List<Connection2>>() {
                            }.getType());
                            Log.v("推荐商品", data);
                            mNoShopAdapter.setTag(0);

                            mNoShopAdapter.getDatas().clear();
                            mNoShopAdapter.getDatas().addAll(dataBeen);
                            mNoShopAdapter.notifyDataSetChanged();
                            EventBus.getDefault().post(new GoodsBean());
                        }

                        @Override
                        public void onError(String msg, int code) {
                        }
                    });
                } else {

                    if (mNoShopAdapter2 == null) {
                        mNoShopAdapter2 = new NoShopAdapter2(mContext, mdataBeen2, ShopCarFragment3.this, mLayoutManager);
                    }
                    mRecyclerNoShop.setAdapter(mNoShopAdapter2);

                    List<Connection> connections = new ArrayList<Connection>();
                    connections.clear();
                    if (dataBeen.size() >= 4) {
                        for (int i = 0; i < 4; i++) {
                            connections.add(dataBeen.get(i));
                        }
                    } else {
                        connections.addAll(dataBeen);
                    }

                    mNoShopAdapter2.setTag(1);
                    mNoShopAdapter2.getDatas().clear();
                    mNoShopAdapter2.getDatas().addAll(dataBeen);
                    mNoShopAdapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String msg, int code) {
            }
        });

    }


    public void load() {
        initShopCarData();
    }

    @Subscribe
    public void refreshShopCar(RefreshShopCar car) {
        initShopCarData();
    }
}

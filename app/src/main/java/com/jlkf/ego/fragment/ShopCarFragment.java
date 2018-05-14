package com.jlkf.ego.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.MainActivity;
import com.jlkf.ego.activity.OrderActivity;
import com.jlkf.ego.adapter.NoShopAdapter;
import com.jlkf.ego.adapter.ShopCarAdater2;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.ConfimOrderBean;
import com.jlkf.ego.bean.Connection;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.bean.ProductInfo;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.utils.ToastUtil;
import com.jlkf.ego.utils.UIHelper;
import com.jlkf.ego.widget.FullyGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class ShopCarFragment extends BaseFragment implements View.OnClickListener {

    RecyclerView recycler;
    RecyclerView recyclerNoShop;

    RelativeLayout rlSettlement;

    RelativeLayout rl_edit;
    LinearLayout ll_no_data;

    RelativeLayout rlEdited, rl_shop, rl_back_top, rl_toolbar, rl_data;


    public final static int state_edit = 0;
    public final static int state_edited = 1;
    public int state = state_edit;

    public final static int STATE_HAVE_DATA = 0;
    public final static int STATE_NO_DATA = 1;
    public int state_data = STATE_HAVE_DATA;


    private List<GoodsBean.ShopcartBean> mData = new ArrayList<>();

    private List<ProductInfo> mData1;

    private List<ProductInfo> mData2;


    private View view, view1;
    private CheckBox checkBox;
    private TextView tv_complet, tv_all_delet;
    private TextView tv_favorites, tv_total_price;
    private ShopCarAdater2 mAdapter;
    private FullyGridLayoutManager mLayout;

    @Override
    public View getContentView(LayoutInflater inflater) {

        view = inflater.inflate(R.layout.fragment_shop2, null);


        recyclerNoShop = (RecyclerView) view.findViewById(R.id.recycler_no_shop);

        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        rlSettlement = (RelativeLayout) view.findViewById(R.id.rl_settlement);
        rl_edit = (RelativeLayout) view.findViewById(R.id.rl_edit);
        rlEdited = (RelativeLayout) view.findViewById(R.id.rl_edited);
        tv_favorites = (TextView) view.findViewById(R.id.tv_favorites);
        tv_all_delet = (TextView) view.findViewById(R.id.tv_all_delet);
        tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
        rl_toolbar = (RelativeLayout) view.findViewById(R.id.rl_toolbar);
        rl_data = (RelativeLayout) view.findViewById(R.id.rl_data);


        rl_shop = (RelativeLayout) view.findViewById(R.id.rl_shop);
        ll_no_data = (LinearLayout) view.findViewById(R.id.ll_no_data);
        tv_complet = (TextView) view.findViewById(R.id.tv_complet);
        rl_back_top = (RelativeLayout) view.findViewById(R.id.rl_back_top);
        tv_complet.setOnClickListener(this);


        view.findViewById(R.id.iv_edit).setOnClickListener(this);
        checkBox = (CheckBox) view.findViewById(R.id.rb_all);
        ((TextView) view.findViewById(R.id.title)).setText(getResources().getString(R.string.shop_title));

        view.findViewById(R.id.tv_favorites).setOnClickListener(this);
        view.findViewById(R.id.rl_settlement).setOnClickListener(this);
        view.findViewById(R.id.rl_back_top).setOnClickListener(this);
        view.findViewById(R.id.iv_kufu).setOnClickListener(this);


        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    if (((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 3) {

                        rl_back_top.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 3) {

                        rl_back_top.setVisibility(View.GONE);
                    }

                }
            }
        });
        return view;
    }

    public void showView() {
        if (state_data == STATE_HAVE_DATA) {
            rl_data.setVisibility(View.VISIBLE);
            ll_no_data.setVisibility(View.GONE);

            final Map<String, String> map = new HashMap<>();
            map.put("uId", MyApplication.getmUserBean().getUId() + "");
            HttpUtil.getInstacne((Activity) mContext).get(Urls.list, String.class, map, new HttpUtil.OnCallBack<String>() {
                @Override
                public void success(String data) {
                    List<GoodsBean> goodsBeen = GoodsBean.arrayGoodsBeanFromData(data);
                    MyApplication.mAppApplication.mGoodsBeen.clear();
                    for (int i = 0; i < goodsBeen.size(); i++) {
                        int size = goodsBeen.get(i).getShopcart().size();
                        for (int j = 0; j < size; j++) {
                            ShopCarGoodsBean bean = new ShopCarGoodsBean();
                            GoodsBean.ShopcartBean shopcartBean = goodsBeen.get(i).getShopcart().get(j);
                            bean.setGoodsCode(shopcartBean.getItemcode());
                            bean.setNum(shopcartBean.getQuantity());
                            MyApplication.mAppApplication.mGoodsBeen.add(bean);
                        }
                    }

                    if (mAdapter == null) {
                        mAdapter = new ShopCarAdater2(mContext, goodsBeen, rlSettlement, tv_favorites, tv_all_delet, checkBox);
                        mAdapter.setOnDeletAllListener(new ShopCarAdater2.OnDeletAllListener() {
                            @Override
                            public void onDeletAll() {
                                state_data = STATE_NO_DATA;
                                showView();
                            }
                        });
                        mAdapter.setOnMoreListener(new ShopCarAdater2.onMoreListener() {
                            @Override
                            public void onMore() {
                                ((MainActivity) getActivity()).select(0);
                            }
                        });
                        recycler.setAdapter(mAdapter);
                    } else {

                        mAdapter.getGoodsBeanList().clear();
                        mAdapter.getGoodsBeanList().addAll(goodsBeen);
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(String msg, int code) {

                }
            });


        } else if (state_data == STATE_NO_DATA) {
            rl_data.setVisibility(View.GONE);
            ll_no_data.setVisibility(View.VISIBLE);
//            mLayout = new FullyGridLayoutManager(mContext, 2);
//            recyclerNoShop.setLayoutManager(mLayout);
            final FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(getActivity(), 2);
            layoutManager.setSpanSizeLookup(new FullyGridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position == 0 ? 2 : 1;
                }
            });
            recyclerNoShop.setLayoutManager(layoutManager);
            recyclerNoShop.setNestedScrollingEnabled(false);
            final Map<String, String> map = new HashMap<>();
            map.put("uId", MyApplication.getmUserBean().getUId() + "");
            HttpUtil.getInstacne(mActivity).get(Urls.getMyColler, String.class, map, new HttpUtil.OnCallBack<String>() {
                private NoShopAdapter adapter;
                @Override
                public void success(String data) {
                    List<Connection> dataBeen = new Gson().fromJson(data, new TypeToken<List<Connection>>() {
                    }.getType());
                    if (dataBeen.size() == 0) {
                        Map<String, String> map = new HashMap<>();
                        map.put("area", MyApplication.getmUserBean().getArea());
                        map.put("pageNo", "1");
                        map.put("pageSize", "4");
                        map.put("type", "4");
                        HttpUtil.getInstacne(mActivity).get(Urls.recommendListTwo, String.class, map, new HttpUtil.OnCallBack<String>() {
                            @Override
                            public void success(String data) {
                                List<Connection> dataBeen = new Gson().fromJson(data, new TypeToken<List<Connection>>() {
                                }.getType());
                                Log.v("推荐商品", data);
                                List<Connection> connections = new ArrayList<Connection>();
                                connections.clear();
                                connections.addAll(dataBeen);
                                if (adapter == null) {
                                    recyclerNoShop.setAdapter(adapter);
                                } else {
                                    adapter.getDatas().clear();
//                                    adapter.getDatas().addAll(connections);
                                }
                            }
                            @Override
                            public void onError(String msg, int code) {
                            }
                        });
                    } else {
                        List<Connection> connections = new ArrayList<Connection>();
                        connections.clear();
                        if (dataBeen.size() >= 4) {
                            for (int i = 0; i < 4; i++) {
                                connections.add(dataBeen.get(i));
                            }
                        } else {
                            connections.addAll(dataBeen);
                        }
                        if (adapter == null) {
                            recyclerNoShop.setAdapter(adapter);
                        } else {
                            adapter.getDatas().clear();
//                            adapter.getDatas().addAll(connections);
                        }
                    }
                }
                @Override
                public void onError(String msg, int code) {
                }
            });
        }
    }


    @Override
    public void initView() {


        if (state_data == STATE_HAVE_DATA) {
            if (recycler != null) {
                recycler.setLayoutManager(new LinearLayoutManager(mContext));
            }
        } else {

        }
    }

    @Override
    public void initListener() {
    }

    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {

            mAdapter.getSelectBean().clear();

        }

        loadData();
    }

    private boolean fisrt = true;

    public void loadData(Context context) {
        mContext = context;
        if (state_data == STATE_HAVE_DATA) {
            loadData();
        } else {

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void loadData() {
        final Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        HttpUtil.getInstacne((Activity) mContext).get(Urls.list, String.class, map, new HttpUtil.OnCallBack<String>() {
            @Override
            public void success(String data) {

                if (TextUtils.isEmpty(data)) {
                    state_data = STATE_NO_DATA;
                } else {
                    List<GoodsBean> goodsBeen = GoodsBean.arrayGoodsBeanFromData(data);
                    MyApplication.mAppApplication.mGoodsBeen.clear();
                    for (int i = 0; i < goodsBeen.size(); i++) {
                        int size = goodsBeen.get(i).getShopcart().size();
                        for (int j = 0; j < size; j++) {
                            ShopCarGoodsBean bean = new ShopCarGoodsBean();
                            GoodsBean.ShopcartBean shopcartBean = goodsBeen.get(i).getShopcart().get(j);
                            bean.setGoodsCode(shopcartBean.getItemcode());
                            bean.setNum(shopcartBean.getQuantity());
                            MyApplication.mAppApplication.mGoodsBeen.add(bean);
                        }
                    }

                    if (getView() == null) return;
                    if (fisrt) {
                        fisrt = false;
                        if (goodsBeen != null && goodsBeen.size() > 0) {
                            state_data = STATE_HAVE_DATA;
                            if (mAdapter == null) {

                                mAdapter = new ShopCarAdater2(mContext, goodsBeen, rlSettlement, tv_favorites, tv_all_delet, checkBox);
                                mAdapter.setOnDeletAllListener(new ShopCarAdater2.OnDeletAllListener() {
                                    @Override
                                    public void onDeletAll() {
                                        state_data = STATE_NO_DATA;
                                        showView();
                                    }
                                });
                                mAdapter.setOnMoreListener(new ShopCarAdater2.onMoreListener() {
                                    @Override
                                    public void onMore() {
                                        ((MainActivity) getActivity()).select(0);
                                    }
                                });
                                recycler.setAdapter(mAdapter);
                            }
                        } else {
                            state_data = STATE_NO_DATA;
                        }
                    } else {

                        if (goodsBeen != null && goodsBeen.size() > 0) {
                            state_data = STATE_HAVE_DATA;
                            if (mAdapter != null) {
                                mAdapter.getGoodsBeanList().clear();
                                mAdapter.getGoodsBeanList().addAll(goodsBeen);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new ShopCarAdater2(mContext, goodsBeen, rlSettlement, tv_favorites, tv_all_delet, checkBox);
                                mAdapter.setOnMoreListener(new ShopCarAdater2.onMoreListener() {
                                    @Override
                                    public void onMore() {
                                        ((MainActivity) getActivity()).select(0);
                                    }
                                });
                                mAdapter.setOnDeletAllListener(new ShopCarAdater2.OnDeletAllListener() {
                                    @Override
                                    public void onDeletAll() {
                                        state_data = STATE_NO_DATA;
                                        showView();
                                    }
                                });
                                recycler.setAdapter(mAdapter);
                            }
                        } else {
                            state_data = STATE_NO_DATA;
                        }

                    }
                }

                showView();
            }

            @Override
            public void onError(String msg, int code) {

                ToastUtil.show(msg);
            }
        });

    }


    boolean isEdit = false;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_edit:
                //  設置編輯
                rootView.findViewById(R.id.rl_edit).setVisibility(View.GONE);
                rootView.findViewById(R.id.rl_edited).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.rl_toolbar).setVisibility(View.GONE);
                rootView.findViewById(R.id.tv_complet).setVisibility(View.VISIBLE);

                //  完成編輯
                break;
            case R.id.rl_settlement:
                goToClear();
                break;
            case R.id.tv_complet:

                rootView.findViewById(R.id.tv_complet).setVisibility(View.GONE);
                rootView.findViewById(R.id.rl_toolbar).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.rl_edit).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.rl_edited).setVisibility(View.GONE);
                break;
            case R.id.iv_kufu:
                UIHelper.startOrer(mContext, "kefu");
                break;
            case R.id.rl_back_top:
                recycler.scrollToPosition(0);
                rl_back_top.setVisibility(View.GONE);
                break;
        }
    }

    private void goToClear() {
        if (state_data == STATE_NO_DATA) {
            ToastUti.show(getResources().getString(R.string.nodata));
            return;
        }
        JSONObject object = new JSONObject();
        int size = mAdapter.getSelectBean().size();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            GoodsBean.ShopcartBean bean = mAdapter.getSelectBean().get(i);
            if (bean.isChecked()) {
                builder.append(bean.getSId()).append(",");
            }
        }
        if (builder.length() < 1) {
            ToastUtil.show(getResources().getString(R.string.xzsp));
            return;
        }
        object.put("sId", builder.substring(0, builder.length() - 1));
        object.put("uId", MyApplication.getmUserBean().getUId() + "");
        HttpUtil.getInstacne(getActivity()).post2(Urls.goSettlement, ConfimOrderBean.class, object.toString(), new HttpUtil.OnCallBack<ConfimOrderBean>() {
            @Override
            public void success(ConfimOrderBean data) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("orderInfo", data);
                startActivity(intent);
//                UIHelper.startOrer(getActivity(), "order");
            }

            @Override
            public void onError(String msg, int code) {
                ToastUti.show(msg);
            }
        });
    }
}

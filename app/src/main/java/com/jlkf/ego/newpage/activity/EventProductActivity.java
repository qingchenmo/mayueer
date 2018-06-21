package com.jlkf.ego.newpage.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.ProductInfoActivity;
import com.jlkf.ego.base.BaseActivity;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.newpage.adapter.EventProductAdapter;
import com.jlkf.ego.newpage.bean.RefreshShopCar;
import com.jlkf.ego.newpage.inter.OnItemClickListener;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.widget.TitleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EventProductActivity extends BaseActivity implements OnRefreshLoadmoreListener, OnItemClickListener<ProductListBean.DataBean> {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    int page = 1;
    String id;
    @BindView(R.id.titleView)
    TitleView titleView;
    private List<ProductListBean.DataBean> mList = new ArrayList<>();
    public static final int EVENT = 1;
    public static final int ZENGPIN = 2;
    private int mType = EVENT;

    @Override
    protected int getlayoutid() {
        return R.layout.activity_event_product;
    }

    @Override
    protected void initView() {
        super.initView();
        refreshLayout.setOnRefreshLoadmoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        id = getIntent().getStringExtra("id");
        mType = getIntent().getIntExtra("type", EVENT);
        if (mType == ZENGPIN) {
            titleView.setTitle("赠品专区");
        }
    }

    @Override
    protected void initData() {
        super.initData();
        recyclerView.setAdapter(new EventProductAdapter(mType, this, mList, this));
        refreshLayout.autoRefresh();
    }

    private void loadData() {
        if (mType == EVENT)
            ApiManager.atoitmlist(id, page + "", this, new HttpUtils.OnCallBack() {
                @Override
                public void success(String response) {
                    List<ProductListBean.DataBean> list = JSON.parseArray(response, ProductListBean.DataBean.class);
                    page = page + 1;
                    mList.addAll(list);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    refreshLayout.finishRefresh(true);
                    refreshLayout.finishLoadmore(true);
                }

                @Override
                public void onError(String msg) {
                    refreshLayout.finishRefresh(false);
                    refreshLayout.finishLoadmore(false);
                    ToastUti.show(msg);
                }
            });
        else {
            ApiManager.giftlist(id, page + "", this, new HttpUtils.OnCallBack() {
                @Override
                public void success(String response) {
                    List<ProductListBean.DataBean> list = JSON.parseArray(response, ProductListBean.DataBean.class);
                    page = page + 1;
                    mList.addAll(list);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    refreshLayout.finishRefresh(true);
                    refreshLayout.finishLoadmore(true);
                }

                @Override
                public void onError(String msg) {
                    refreshLayout.finishRefresh(false);
                    refreshLayout.finishLoadmore(false);
                    ToastUti.show(msg);
                }
            });
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        loadData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        mList.clear();
        loadData();
    }

    @Override
    public void itemClickListener(ProductListBean.DataBean dataBean, int position) {
        Intent intent = new Intent(mContext, ProductInfoActivity.class);
        intent.putExtra("itemCode", dataBean.getItemcode());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mType == ZENGPIN) {
            setResult(RESULT_OK);
//            EventBus.getDefault().post(new RefreshShopCar());
        }
        super.onBackPressed();
    }
}

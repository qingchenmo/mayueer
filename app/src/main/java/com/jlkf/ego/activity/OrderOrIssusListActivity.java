package com.jlkf.ego.activity;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jlkf.ego.R;
import com.jlkf.ego.adapter.OrderOrIssusAdapter;
import com.jlkf.ego.bean.OrderBean;

import java.util.List;

/**
 * @author zcs
 *         订购/实发商品清单页
 */
public class OrderOrIssusListActivity extends BaseActivity implements View.OnClickListener,
        OnRefreshListener, OnLoadMoreListener {
    private ImageView iv_back;
    private TextView tv_title;
    private SwipeToLoadLayout loadLayout;
    Handler handler = new Handler();
    private RecyclerView mRecyclerView;
    private int type;

    @Override
    public void initView() {
        setContentView(R.layout.activity_order_or_issus_list);
        type = getIntent().getIntExtra("type", 0);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);

        iv_back.setOnClickListener(this);
        loadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        loadLayout.setOnLoadMoreListener(this);
        loadLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        //为recyclerView设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<OrderBean.DataBean.OorderDetailBean> list = getIntent().getParcelableArrayListExtra("list");
        int num = 0;
        for (int i = 0; i < list.size(); i++) {
            num =num +list.get(i).getQuantity();
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (type == OrderOrIssusAdapter.ORDERLIST) {
            tv_title.setText(mContext.getString(R.string.o_dgspqd) + "（" + mContext.getString(R.string.o_gong) + num + mContext.getString(R.string.o_jian) + "）");
            mRecyclerView.setAdapter(new OrderOrIssusAdapter(this, list, OrderOrIssusAdapter.ORDERLIST));
        } else if (type == OrderOrIssusAdapter.ISSUSLIST) {
            tv_title.setText(mContext.getString(R.string.o_sfspqd) + "（" + mContext.getString(R.string.o_gong) + num + mContext.getString(R.string.o_jian) + "）");
            mRecyclerView.setAdapter(new OrderOrIssusAdapter(this, list, OrderOrIssusAdapter.ISSUSLIST));
        }
    }

    @Override
    public void iniActivityAnimation() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadLayout.setLoadingMore(false);
                Toast.makeText(mContext, "加载更多", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadLayout.setRefreshing(false);
                Toast.makeText(mContext, "下拉刷新", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }
}

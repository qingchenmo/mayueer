package com.jlkf.ego.newpage.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.activity.ProductInfoActivity;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.newpage.adapter.EventAdapter;
import com.jlkf.ego.newpage.adapter.EventOneTypeAdapter;
import com.jlkf.ego.newpage.bean.EventOneTypeBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.widget.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 活动页
 */
public class EventActyivity extends BaseActivity {

    @BindView(R.id.title)
    TitleView mTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private int mType;
    private String mId;
    private EventOneTypeBean mOneTypeBean;

    @Override
    public void initView() {
        setContentView(R.layout.activity_event_actyivity);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title")))
            mTitle.setTitle(getIntent().getStringExtra("title"));
        mType = getIntent().getIntExtra("type", 0);
        mId = getIntent().getStringExtra("id");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
        super.initData();
        if (mType == 1) {
            getOperateat();
        } else if (mType == 2) {
            getOperateat2();
        }
    }

    private void getOperateat2() {
        setLoading(true);
        ApiManager.getOperateat2(this, mId, new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                mOneTypeBean = JSON.parseArray(response, EventOneTypeBean.class).get(0);
                mTitle.setTitle(mOneTypeBean.getName());
                mRecyclerView.setAdapter(new EventAdapter(EventActyivity.this, mOneTypeBean, new OnItemClickListener<ProductListBean.DataBean>() {
                    @Override
                    public void itemClickListener(ProductListBean.DataBean dataBean, int position) {
                        Intent intent = new Intent(mContext, ProductInfoActivity.class);
                        intent.putExtra("itemCode", dataBean.getItemcode());
                        startActivity(intent);
                    }
                }));
                mRecyclerView.scrollToPosition(0);
                setLoading(false);
            }

            @Override
            public void onError(String msg) {
                setLoading(false);
            }
        });
    }

    private void getOperateat() {
        setLoading(true);
        ApiManager.getOperateat(this, mId, new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                mOneTypeBean = JSON.parseArray(response, EventOneTypeBean.class).get(0);
                mTitle.setTitle(mOneTypeBean.getName());
                mRecyclerView.setAdapter(new EventOneTypeAdapter(EventActyivity.this, mOneTypeBean));
                mRecyclerView.scrollToPosition(0);
                setLoading(false);
            }

            @Override
            public void onError(String msg) {
                showToast(msg, 0);
                setLoading(false);
            }
        });
    }

    @Override
    public void iniActivityAnimation() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
}

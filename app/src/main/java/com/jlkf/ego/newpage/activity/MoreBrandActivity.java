package com.jlkf.ego.newpage.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.newpage.adapter.MoreBrandAdapter;
import com.jlkf.ego.newpage.bean.BrandBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreBrandActivity extends BaseActivity implements OnItemClickListener<BrandBean> {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<BrandBean> mBrandList = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_more_brand);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MoreBrandAdapter(this, mBrandList, this));
    }

    @Override
    public void initData() {
        super.initData();
        setLoading(true);
        ApiManager.getBrandList(this, new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                List<BrandBean> list = JSON.parseArray(response, BrandBean.class);
                mBrandList.clear();
                mBrandList.addAll(list);
                recyclerView.getAdapter().notifyDataSetChanged();
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
    public void itemClickListener(BrandBean brandBean, int position) {
        Intent intent = new Intent(this, ClassificationActivity.class);
        intent.putExtra("brandId", brandBean.getPp_id());
        startActivity(intent);
    }
}

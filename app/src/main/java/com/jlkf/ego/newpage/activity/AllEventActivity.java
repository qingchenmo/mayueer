package com.jlkf.ego.newpage.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.newpage.adapter.PersonActivityAdapter;
import com.jlkf.ego.newpage.bean.PersonActivityListBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllEventActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private List<PersonActivityListBean> activityList = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_all_event);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new PersonActivityAdapter(this, activityList, new OnItemClickListener<PersonActivityListBean>() {
            @Override
            public void itemClickListener(PersonActivityListBean personActivityListBean, int position) {
                Intent intent = new Intent(AllEventActivity.this, EventActyivity.class);
                intent.putExtra("id", personActivityListBean.getAt_id());
                intent.putExtra("type", personActivityListBean.getAttype());
                intent.putExtra("title", personActivityListBean.getName());
                startActivity(intent);
            }
        }));
    }

    @Override
    public void initData() {
        super.initData();
        setLoading(true);
        ApiManager.activitylist(this, new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                setLoading(false);
                List<PersonActivityListBean> list = JSON.parseArray(response, PersonActivityListBean.class);
                activityList.addAll(list);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {
                setLoading(false);
                showToast(msg, 0);
            }
        });
    }

    @Override
    public void iniActivityAnimation() {

    }
}

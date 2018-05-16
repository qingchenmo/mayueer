package com.jlkf.ego.newpage.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.newpage.adapter.EventAdapter;
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

    @Override
    public void initView() {
        setContentView(R.layout.activity_event_actyivity);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new EventAdapter(this));
    }

    @Override
    public void iniActivityAnimation() {

    }
}

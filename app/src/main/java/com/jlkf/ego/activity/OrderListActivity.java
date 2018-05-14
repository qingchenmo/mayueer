package com.jlkf.ego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.OrderListFragmentAdapter;
import com.jlkf.ego.fragment.OrderListFragment;
import com.jlkf.ego.utils.UIHelper;
import com.jlkf.ego.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单页面
 */
public class OrderListActivity extends AppCompatActivity {
    public static TabLayout mTabLayout;
    private ViewPager mVpFragment;
    private FragmentPagerAdapter mAdapter;
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        mTabLayout = (TabLayout) findViewById(R.id.tab_title);
        mVpFragment = (ViewPager) findViewById(R.id.vp_fragment);
        type = getIntent().getIntExtra("type", 0);
        TitleView titleView = (TitleView) findViewById(R.id.tv);
        titleView.setOnRightListener(new TitleView.OnRightListener() {
            @Override
            public void onRight(View v) {
                UIHelper.startOrer(OrderListActivity.this, "search");
            }
        });


        String[] tabArray = getResources().getStringArray(R.array.order_type);
        List<String> tabs = new ArrayList<>();
        List<Fragment> mFragments = new ArrayList<>();
        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabArray.length; i++) {
            tabs.add(tabArray[i]);
            mFragments.add(OrderListFragment.newInstance(i));
            mTabLayout.addTab(mTabLayout.newTab().setText(tabArray[i]));
        }
        mAdapter = new OrderListFragmentAdapter(getSupportFragmentManager(), mFragments, tabs);
        mVpFragment.setAdapter(mAdapter);
        //TabLayout加载viewpager
        mTabLayout.setupWithViewPager(mVpFragment);
        mVpFragment.setOffscreenPageLimit(0);
        selectTab();
        if (type == 1)
            mTabLayout.getTabAt(1).select();
    }

    private void selectTab() {
        mTabLayout.getTabAt(getIntent().getIntExtra("select", 0)).select();
    }

    @Override
    protected void onDestroy() {
        mTabLayout = null;
        super.onDestroy();
    }

    @OnClick(R.id.iv_title_left)
    void back() {
        if (type == 1) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("tag", "user");
            startActivity(intent);
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

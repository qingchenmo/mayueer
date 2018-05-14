package com.jlkf.ego.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.ProductQuickSelectActivity;
import com.jlkf.ego.activity.SearchProductActivity;
import com.jlkf.ego.activity.SystemMessageActivity;
import com.jlkf.ego.adapter.OrderListFragmentAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.zxing.MipcaActivityCapture;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends BaseFragment {
    public static final int MESSAGE_REQUEST = 999;
    @BindView(R.id.iv_system)
    ImageView ivSystem;
    @BindView(R.id.tv_redCircle_system)
    TextView tvRedCircleSystem;
    @BindView(R.id.rl_system)
    RelativeLayout rlSystem;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.rl_search_brand)
    RelativeLayout rlSearchBrand;
    @BindView(R.id.iv_sao)
    ImageView ivSao;
    @BindView(R.id.rl_sao)
    RelativeLayout rlSao;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tab_main)
    TabLayout tabMain;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    Unbinder unbinder;
    private String mParam1;
    private String mParam2;


    public MainFragment() {
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();

        return fragment;
    }

    @Override
    public void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");

        HttpUtil.getInstacne(mActivity).get(Urls.allRead, Integer.class, map, new HttpUtil.OnCallBack<Integer>() {
            @Override
            public void success(Integer data) {

                if (data == 0){
                    tvRedCircleSystem.setVisibility(View.GONE);
                } else {
                    tvRedCircleSystem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String msg, int code) {

            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_main, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, rootView);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new BrandFragment());
        fragments.add(ProductClassFragment.newInstance(0));
        List<String> tabs = new ArrayList<>();
        tabs.add(getResources().getString(R.string.pplb));
        tabs.add(getResources().getString(R.string.ppfl));
        //设置TabLayout的模式
        tabMain.setTabMode(TabLayout.MODE_FIXED);
        tabMain.addTab(tabMain.newTab().setText(tabs.get(0)));
        tabMain.addTab(tabMain.newTab().setText(tabs.get(1)));
        vpMain.setAdapter(new OrderListFragmentAdapter(getChildFragmentManager(), fragments, tabs));

        //TabLayout加载viewpager
        tabMain.setupWithViewPager(vpMain);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 修改TabLayout中tab的间距
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        tabMain.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabMain, 30, 30);
            }
        });
    }

    @OnClick(R.id.iv_sao)
    void scan() {
        Intent intent = new Intent(mContext, MipcaActivityCapture.class);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.rl_system)
    void systemMsg() {
        Intent intent = new Intent(mContext, SystemMessageActivity.class);
        startActivityForResult(intent,MESSAGE_REQUEST);
    }



    @OnClick(R.id.rl_search_brand)
    void search() {
        Intent intent = new Intent(getActivity(), SearchProductActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String str = data.getStringExtra("result");
            Intent intent = new Intent(getActivity(), ProductQuickSelectActivity.class);
            intent.putExtra("productName", str);
            intent.putExtra("codeBars", str);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MESSAGE_REQUEST && resultCode == Activity.RESULT_OK){
            initData();
        }
    }
}

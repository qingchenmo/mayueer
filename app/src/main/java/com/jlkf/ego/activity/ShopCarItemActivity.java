package com.jlkf.ego.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.ShopItemAdatper2;
import com.jlkf.ego.bean.GoodsBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/23.
 */
public class ShopCarItemActivity extends BaseActivity implements ShopItemAdatper2.onMoreListener {


    public static final int BACK = 100;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.iv_kufu)
    ImageView mIvKufu;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    @BindView(R.id.tv_complet)
    TextView mTvComplet;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.view1)
    View mView1;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.rl_settlement)
    RelativeLayout mRlSettlement;
    @BindView(R.id.rl_edit)
    RelativeLayout mRlEdit;
    @BindView(R.id.rb_all)
    CheckBox mRbAll;
    @BindView(R.id.tv_favorites)
    TextView mTvFavorites;
    @BindView(R.id.tv_all_delet)
    TextView mTvAllDelet;
    @BindView(R.id.rl_edited)
    RelativeLayout mRlEdited;
    @BindView(R.id.rl_shop)
    RelativeLayout mRlShop;
    @BindView(R.id.rl_back_top)
    RelativeLayout mRlBackTop;
    @BindView(R.id.rl_data)
    RelativeLayout mRlData;
    private TextView tv_title;
    private GoodsBean mData;
    private ShopItemAdatper2 mAdapter;

    @Override
    public void initView() {
        setContentView(R.layout.act_shop_car_item);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mData = (GoodsBean) getIntent().getSerializableExtra("data");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.iv_edit, R.id.tv_complet, R.id.rl_back, R.id.iv_kufu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_edit:
                //  設置編輯
                findViewById(R.id.rl_edit).setVisibility(View.GONE);
                findViewById(R.id.rl_edited).setVisibility(View.VISIBLE);
                findViewById(R.id.rl_toolbar).setVisibility(View.GONE);
                findViewById(R.id.tv_complet).setVisibility(View.VISIBLE);

                //  完成編輯
                break;
            case R.id.tv_complet:

                findViewById(R.id.tv_complet).setVisibility(View.GONE);
                findViewById(R.id.rl_toolbar).setVisibility(View.VISIBLE);
                findViewById(R.id.rl_edit).setVisibility(View.VISIBLE);
                findViewById(R.id.rl_edited).setVisibility(View.GONE);
                break;
            case R.id.rl_back:
                finishs();
                break;
            case R.id.iv_kufu:
                startActivity(new Intent(this, AppServerCenterActivity.class));
                break;
        }
    }


    @Override
    public void initData() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ShopItemAdatper2(mActivity, mData.getShopcart());
        mAdapter.setOnMoreListener(this);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setView(mTvTotalPrice);
        mAdapter.setTotalView(mRlSettlement);
        mAdapter.setAll(mRbAll);
        mAdapter.setsc(mTvFavorites);
        mAdapter.setshanchu(mTvAllDelet);

        mTitle.setText(mData.getBrandData().getName());
    }

    @Override
    public void onBackPressed() {
        finishs();
    }

    public void finishs() {
        Intent intent = new Intent();
        setResult(BACK, intent);
        finish();
    }


    @Override
    public void iniActivityAnimation() {

    }


    @Override
    public void onMore() {

        startActivity(new Intent(mContext, MainActivity.class));
    }

    @Subscribe
    public void refreshList(GoodsBean bean) {
        mData = bean;
        initData();
    }
}

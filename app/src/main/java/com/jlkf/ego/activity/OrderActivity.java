package com.jlkf.ego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jlkf.ego.R;
import com.jlkf.ego.bean.AdressBean;
import com.jlkf.ego.bean.ConfimOrderBean;
import com.jlkf.ego.fragment.OrderFragment;
import com.jlkf.ego.widget.BaseToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jlkf.ego.adapter.OrderItemAdapter.RESULT_ORDER;

/**
 * Created by Administrator on 2017/7/18 0018.
 * <p>
 * 订单activity
 */

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    private OrderFragment mFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_order);

        ButterKnife.bind(this);

        initToolbar();
        initFragment();
    }

    private void initFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        mFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("orderInfo", (ConfimOrderBean) getIntent().getParcelableExtra("orderInfo"));
        mFragment.setArguments(bundle);
        ft.add(R.id.order_fl, mFragment);
        ft.commit();
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.with(this);
        toolbar.setTitle(getResources().getString(R.string.order_title));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_ORDER && resultCode == RESULT_OK) {
            AdressBean adressBean = (AdressBean) data.getSerializableExtra("order");
            mFragment.setAdressBean(adressBean);
        }
    }
}

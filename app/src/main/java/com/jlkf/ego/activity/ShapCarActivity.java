package com.jlkf.ego.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.jlkf.ego.R;
import com.jlkf.ego.fragment.ShopCarFragment3;
import com.jlkf.ego.widget.TitleView;

import butterknife.BindView;

public class ShapCarActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleView mTitle;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;

    @Override
    public void initView() {
        setContentView(R.layout.activity_shap_car);
        ShopCarFragment3 fragment = new ShopCarFragment3();
        Bundle bundle = new Bundle();
        bundle.putString("flag", "noMain");
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment).commit();
    }

    @Override
    public void iniActivityAnimation() {

    }
}

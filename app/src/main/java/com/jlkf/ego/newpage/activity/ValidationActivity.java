package com.jlkf.ego.newpage.activity;

import android.os.Bundle;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;

import butterknife.ButterKnife;

public class ValidationActivity extends BaseActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_validation);
        ButterKnife.bind(this);
    }

    @Override
    public void iniActivityAnimation() {

    }
}

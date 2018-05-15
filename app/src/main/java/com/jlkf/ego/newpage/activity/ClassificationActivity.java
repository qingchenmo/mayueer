package com.jlkf.ego.newpage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.activity.SearchProductActivity;
import com.jlkf.ego.newpage.fragment.ClassificationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分类页
 */
public class ClassificationActivity extends BaseActivity {
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        ClassificationFragment fragment = new ClassificationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment).commitNowAllowingStateLoss();
    }

    @Override
    public void iniActivityAnimation() {

    }

    @OnClick(R.id.iv_productList_back)
    void back() {
        finish();
    }

    @OnClick(R.id.et_title)
    void search() {
        Intent intent = new Intent(this, SearchProductActivity.class);
        startActivity(intent);
    }
}

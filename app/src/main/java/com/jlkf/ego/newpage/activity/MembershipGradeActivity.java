package com.jlkf.ego.newpage.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MembershipGradeActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;

    @Override
    public void initView() {
        setContentView(R.layout.activity_membership_grade);
        ButterKnife.bind(this);
        webView.loadDataWithBaseURL(null, "这里显示富文本", "text/html", "UTF-8", null);
    }

    @Override
    public void iniActivityAnimation() {

    }
}

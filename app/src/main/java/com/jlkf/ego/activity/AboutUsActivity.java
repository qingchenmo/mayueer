package com.jlkf.ego.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jlkf.ego.R;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.AppUtil;

/**
 * 关于我们
 * @author 邓超桂
 * @date 创建时间：2017年7月11日 下午3:40:13
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {


    private TextView tv_version;

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_about_us);

        tv_version= getView(R.id.tv_version);

        String appName=AppUtil.getAppName(mContext);
        String appVersionName=AppUtil.getVersionName(mContext);

        tv_version.setText(appName+" "+appVersionName);

    }

    @Override
    public void onBackPressed() {
        BaseActivity.finish(AboutUsActivity.this);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.iv_back://返回
               onBackPressed();
               break;
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(mContext, "AboutUs_page");
    }
    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "AboutUs_page");
    }
}

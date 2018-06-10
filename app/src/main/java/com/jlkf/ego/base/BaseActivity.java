package com.jlkf.ego.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.UserBean;
import com.jlkf.ego.utils.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;

/**
 * BaseActivity父类
 * Created by zcs on 2017/7/19.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    public BaseActivity mActivity;
    public RequestManager mRequestManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        mActivity = this;
        setContentView(getlayoutid());
        ButterKnife.bind(this);
        mContext = this;
        mRequestManager = initImageLooder();
        initView();
        initData();
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        View view = getWindow().getDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 判断是否联网的方法
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {

        //当使用测试用例，忽略网络判断
        SharedPreferencesUtil mCsvSPUtil = new SharedPreferencesUtil(context, "csv");
        boolean m_csv = (Boolean) mCsvSPUtil.getSPValue("csv", false);
        if (m_csv) {
            return true;
        }

        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(),this);
        super.onDestroy();
    }

    /**
     * 获取用户
     *
     * @return
     */
    public UserBean getUser() {
        return MyApplication.getmUserBean();
    }

    /**
     * 区域
     *
     * @return
     */
    public String getArea() {
        return getUser().getuTypeName();
    }

    public void init() {

    }

    public RequestManager initImageLooder() {
        return Glide.with(mActivity);

    }

    protected abstract int getlayoutid();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化视图
     */
    protected void initView() {

    }

}

package com.jlkf.ego.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.SharedPreferencesUtil;


/**
 * Fragment 的父类
 *
 * @author 邓超桂
 */
public abstract class BaseFragment extends Fragment {
    public Context mContext;
    public Activity mActivity;
    public View rootView;
    public LayoutInflater mInflater;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        if (rootView == null) {
            mInflater = inflater;
            rootView = getContentView(mInflater);
            //初始化界面
            initView();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity  = getActivity();
    }

    /**
     * 获取View
     *
     * @param inflater
     * @return
     */
    public abstract View getContentView(LayoutInflater inflater);

    /**
     * 初始化view
     */
    public abstract void initView();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }

    public void initListener() {


    }

    public void initData() {

    }

    /**
     * 展示土司
     *
     * @param text   文本内容
     * @param isLong 1 为长时间 0 为短时间
     */
    public void showToast(String text, int isLong) {
        if (isLong == 1) {
            Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 弹出输入键盘
     *
     * @param mContext
     */
    public static void showInput(Activity mContext) {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘
     *
     * @param mContext
     */
    public static void hideInput(Activity mContext) {
        try {
            ((InputMethodManager) mContext
                    .getSystemService(mContext.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(mContext.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (RuntimeException re) {
            AppLog.Loge("dcg", "re==" + re);
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
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }
}

package com.jlkf.ego.activity;

        import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.AppUtil;

/**
 * 易购服务协议
 * @author  邓超桂
 * @date 创建时间：2017年7月7日 下午3:40:13
 */
public class AppServerXieYiActivity extends BaseActivity{

    private WebView m_webview;
    private RelativeLayout errView;
    private String url = "";
    private TextView tv_title;
    private ImageView iv_back;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://显示dialog
                    showProgressDialog();
                    break;
                case 2://隐藏dialog
                    dissmissProgressDialog();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_appserver);
        getData();
        tv_title=(TextView) findViewById(R.id.tv_title);
        iv_back=(ImageView) findViewById(R.id.iv_back);
        errView = (RelativeLayout)this.findViewById(R.id.err_view);

        m_webview = (WebView) findViewById(R.id.m_webview);
        initWebView();

        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        errView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                errView.setVisibility(View.GONE);
                m_webview.loadUrl(url);
            }
        });
    }

    private void getData() {
        url = getIntent().getStringExtra("url");
        AppLog.Loge("dcg", "传过来的网页路径："+url);
        if(AppUtil.IsNullString(url)){
            url="www.baidu.com";
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        m_webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        m_webview.getSettings().setJavaScriptEnabled(true);
        m_webview.getSettings().setLoadWithOverviewMode(true);
        m_webview.getSettings().setUseWideViewPort(true);
        m_webview.setVerticalScrollBarEnabled(false);
        m_webview.setHorizontalScrollBarEnabled(false);
        m_webview.setWebViewClient(client);
        m_webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        m_webview.getSettings().setAppCacheEnabled(false); // 打开浏览器的缓存
//		// 设置可以支持缩放
//		m_webview.getSettings().setSupportZoom(true);
//		// 设置出现缩放工具
//		m_webview.getSettings().setBuiltInZoomControls(true);
//		//扩大比例的缩放
//		m_webview.getSettings().setUseWideViewPort(true);
//		//自适应屏幕
//		m_webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		m_webview.getSettings().setLoadWithOverviewMode(true);
        m_webview.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        BaseActivity.finish(AppServerXieYiActivity.this);
    }


    private WebViewClient client = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            m_webview.getSettings().setLoadsImagesAutomatically(true);
            AppUtil.sendMessage(2, "", mHandler);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            AppUtil.sendMessage(1, "", mHandler);
        }
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            m_webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            AppUtil.sendMessage(2, "", mHandler);
            errView.setVisibility(View.VISIBLE);
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && m_webview.canGoBack()) {
            m_webview.goBack();
            BaseActivity.finish(AppServerXieYiActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(mContext, "AppServerXieYi_page");
    }
    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "AppServerXieYi_page");
    }
}

package com.jlkf.ego.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.YindaoBean;
import com.jlkf.ego.fragment.wellcome.Fragment1;
import com.jlkf.ego.fragment.wellcome.Fragment3;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.AppUtil;
import com.jlkf.ego.utils.SharedPreferencesUtil;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.widget.permission.CheckPermission;
import com.jlkf.ego.widget.permission.PermissionActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 欢迎 引导页 activity
 * Created by dcg on 2017-07-06.
 */
public class WellcomeActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener {

    private boolean m_start;

    // 使用网络获取图片展播组件
    private SharedPreferencesUtil mSharedPreferencesUtil;

    private FrameLayout framelayout;
    private LinearLayout ll_indicator;
    private ImageView iv_logo;

    private ViewPager viewpage;
    private ImageView iv_page_one;
    private ImageView iv_page_two;
    private ImageView iv_page_three;
    private ImageView iv_page_four;

    Fragment fragment ;
    private List<Fragment> mTabs = new ArrayList<Fragment>(); // fragment集合
    private FragmentPagerAdapter mAdapter; // viewpager的adapter

    private int index = 0;


    private TextView skipView;
    private static final String SKIP_TEXT = "跳过";
    public boolean canJump = false;


    //配置需要取的权限
    static final String[] PERMISSION = {Manifest.permission.READ_PHONE_STATE,//拨打客户热线
            Manifest.permission.GET_ACCOUNTS,//Gmail权限。可以不要
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//下载版本更新
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    //请求手机基本信息权限
    private static final int REQUEST_CODE = 0;//请求码
    private CheckPermission checkPermission;//检测权限器

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    AppLog.Loge("dcg", "显示轮播图");
                    dealWithViewFlow();
                    break;
                case 2://进入下一个页面
                    gotoNextActivity();
                    break;
                case 3://Toast处理
                    showToast(String.valueOf(msg.obj), 0);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void initView() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wellcome_first);

        skipView = (TextView) findViewById(R.id.skip_view);

//		AppUtil.sendMessage(1024, "", mHandler);

        checkPermission = new CheckPermission(this, WellcomeActivity.this);

        mSharedPreferencesUtil = new SharedPreferencesUtil(mContext, "start");

        framelayout = getView(R.id.framelayout);
        ll_indicator = getView(R.id.ll_indicator);
        iv_logo = getView(R.id.iv_logo);

        viewpage = getView(R.id.viewpage);
        iv_page_one = getView(R.id.iv_page_one);
        iv_page_two = getView(R.id.iv_page_two);
        iv_page_three = getView(R.id.iv_page_three);
        iv_page_four = getView(R.id.iv_page_four);


        // 判断是否首启动  首次false;再启动 true
        m_start = (boolean) getStart();


        viewpage.setOffscreenPageLimit(2);//设置预加载,当前页面距离超过预设值的页面都会被Destroy掉
        viewpage.setOnPageChangeListener(this);// 设置页面滚动监听获取当前页面的页码


        iv_logo.setVisibility(View.VISIBLE);
        ll_indicator.setVisibility(View.GONE);
        framelayout.setVisibility(View.GONE);

        skipView.setOnClickListener(mainListener);
        if (m_start) {

            initData();
        } else {
            initDatas();

        }
    }

    /**
     * 设置启动页面
     */
    private void initData() {
        final Map<String, String> map = new HashMap<>();
        map.put("pType", "0");
        HttpUtil.getInstacne(WellcomeActivity.this).get(Urls.getPic, String.class, map, new HttpUtil.OnCallBack<String>() {
            @Override
            public void success(String data) {
                if (!TextUtils.isEmpty(data)) {
                    if (!isFinishing()){
                        List<YindaoBean> yindaoBeen = YindaoBean.arrayYindaoBeanFromData(data);
                        Glide.with(mActivity).load(yindaoBeen.get(0).getPLogo()).error(R.mipmap.img_qidongye).into(iv_logo);
                    }
                }

            }

            @Override
            public void onError(String msg, int code) {
                ToastUti.show(msg);

            }
        });
    }


    /*
     * 获取手机基本信息
     */
    private void getAPPDevice() {
        String umDeviceInfo = UMUtils.getDeviceInfo(mContext);
        AppLog.Logi("友盟 设备识别信息：" + umDeviceInfo);


        setIntent();
    }

    private void setIntent() {
        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {

                Message msg = new Message();
                if (!m_start) { //首次启动
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                } else { //再次启动
                    msg.what = 2;
                    mHandler.sendMessage(msg);
                }

            }
        };
        timer.schedule(tt, 2000);//2s

    }

    /**
     * 位fragment的list传值
     **/
    private void initDatas() {

        dealWithViewFlow();
        final Map<String, String> map = new HashMap<>();
        map.put("pType", "1");

        HttpUtil.getInstacne(WellcomeActivity.this).get(Urls.getPic, String.class, map,new HttpUtil.OnCallBack<String>() {

            @Override
            public void success(String data) {
                List<YindaoBean> yindaoBeen = YindaoBean.arrayYindaoBeanFromData(data);

                for (int i=0;i<yindaoBeen.size();i++){
                    if (i != yindaoBeen.size() -1 ){
                        fragment = new Fragment1();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("url", yindaoBeen.get(i).getPLogo());
                        fragment.setArguments(bundle1);

                    } else {
                        fragment = new Fragment3();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("url", yindaoBeen.get(i).getPLogo());
                        fragment.setArguments(bundle1);

                    }
                    mTabs.add(fragment);
                }

                // 初始化适配器
                mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public int getCount() {
                        return mTabs.size();
                    }

                    @Override
                    public Fragment getItem(int arg0) {
                        return mTabs.get(arg0);
                    }
                };

                viewpage.setAdapter(mAdapter);


            }

            @Override
            public void onError(String msg, int code) {

            }
        });


        // 往list加入4个碎片

    }

    //处理商业广告轮播
    private void dealWithViewFlow() {
        iv_logo.setVisibility(View.GONE);
        skipView.setVisibility(View.VISIBLE);
        ll_indicator.setVisibility(View.VISIBLE);
        framelayout.setVisibility(View.VISIBLE);
    }


    private boolean getStart() {
        return (Boolean) mSharedPreferencesUtil.getSPValue("start", false);
    }

    /**
     * 跳转至主页面
     */
    public View.OnClickListener mainListener = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            //记录登录状态
            mSharedPreferencesUtil.saveSPValue("start", true);
            AppUtil.sendMessage(2, null, mHandler);
        }
    };


    /**
     * 进入主页面
     */
    private void gotoNextActivity() {
        if (canJump) {
            //判断用户是否已经登录
            if (MyApplication.getmUserBean() != null && MyApplication.getmUserBean().isCookie()) {
                Intent intent = new Intent(WellcomeActivity.this, com.jlkf.ego.activity.MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(WellcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            finish();
        } else {
            canJump = true;
        }
    }


    //返回键无效
    @Override
    public void onBackPressed() {

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                iv_page_one.setImageResource(R.mipmap.img_star_able);
                iv_page_two.setImageResource(R.mipmap.img_star_enable);
                iv_page_three.setImageResource(R.mipmap.img_star_enable);
                iv_page_four.setImageResource(R.mipmap.img_star_enable);
                iv_logo.setVisibility(View.GONE);

                index = position;//当前位置

                break;
            case 1:
                iv_page_one.setImageResource(R.mipmap.img_star_enable);
                iv_page_two.setImageResource(R.mipmap.img_star_able);
                iv_page_three.setImageResource(R.mipmap.img_star_enable);
                iv_page_four.setImageResource(R.mipmap.img_star_enable);
                iv_logo.setVisibility(View.GONE);

                index = position;//当前位置
                break;
            case 2:
                iv_page_one.setImageResource(R.mipmap.img_star_enable);
                iv_page_two.setImageResource(R.mipmap.img_star_enable);
                iv_page_three.setImageResource(R.mipmap.img_star_able);
                iv_page_four.setImageResource(R.mipmap.img_star_enable);
                iv_logo.setVisibility(View.GONE);

                index = position;//当前位置
                break;
            case 3:
                iv_page_one.setImageResource(R.mipmap.img_star_enable);
                iv_page_two.setImageResource(R.mipmap.img_star_enable);
                iv_page_three.setImageResource(R.mipmap.img_star_enable);
                iv_page_four.setImageResource(R.mipmap.img_star_able);
                iv_logo.setVisibility(View.GONE);

                index = position;//当前位置
                break;

            default:
                break;
        }
    }


    //进入权限设置页面
    private void startPermissionActivity() {
        PermissionActivity.startActivityForResult(this, REQUEST_CODE, PERMISSION);
    }

    //返回结果回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拒绝时，没有获取到主要权限，无法运行，关闭页面
        if (requestCode == REQUEST_CODE && resultCode == PermissionActivity.PERMISSION_DENIEG) {
            Toast.makeText(this, "没有获取到主要权限，无法运行应用", Toast.LENGTH_LONG).show();
            System.exit(0);
        }
    }
/*---------------------------------------------广点通的使用---------------------------------------------------*/


    /**
     * 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //请求手机基本信息权限，6.0以上手机需要申请权限
        if (Build.VERSION.SDK_INT >= 23) {
            //缺少权限时，进入权限设置页面
//      if (checkPermission.permissionSet(PERMISSION)) {
            if (checkPermission.checkPermission(this, PERMISSION)) {
                AppLog.Loge("缺少权限时，进入权限设置页面");
                startPermissionActivity();
            } else {
                AppLog.Loge("打开系统的读写权限成功！！！");
                getAPPDevice();
            }
        } else {
            getAPPDevice();
        }
        if (canJump) {
            Message msg = new Message();
            if (!m_start) { //首次启动
                msg.what = 1;
                mHandler.sendMessage(msg);
            } else { //再次启动
                msg.what = 2;
                mHandler.sendMessage(msg);
            }
        }
        canJump = true;

//        JPushInterface.onResume(mContext);
        UMUtils.onResumeUMActivity(mContext, "Wellcome_page");
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
//        JPushInterface.onPause(mContext);
        UMUtils.onPauseUMActivity(mContext, "Wellcome_page");
    }

}

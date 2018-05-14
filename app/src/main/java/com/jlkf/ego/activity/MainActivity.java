package com.jlkf.ego.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.base.BaseActivity;
import com.jlkf.ego.fragment.MainFragment2;
import com.jlkf.ego.fragment.PersonFragment;
import com.jlkf.ego.fragment.ShopCarFragment3;
import com.jlkf.ego.newpage.HomeFragment;
import com.jlkf.ego.utils.RefreshUtils;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.utils.ToastUtil;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import butterknife.BindView;

// 新的

public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.fl_home)
    FrameLayout flHome;
    @BindView(R.id.tv_red_circle)
    TextView tvRedCircle;
    @BindView(R.id.rg_home_bottom)
    RadioGroup rgHomeBottom;
    @BindView(R.id.rb_index)
    RadioButton rbIndex;
    @BindView(R.id.rb_shop)
    RadioButton rbShop;
    @BindView(R.id.rb_person)
    RadioButton rbPerson;
    @BindView(R.id.tab_home_bottom)
    TabLayout tabHomeBottom;
    @BindView(R.id.line)
    View line;
    private TextView tv_showNum;
    private MainFragmentAdapter adapter;
    private String mTag = "";
    private boolean isFirst = true;
    private RefreshUtils mUtils;

    @BindView(R.id.iv_yindao)
    ImageView iv_yindao;

    @Override
    public void init() {
        super.init();

//        if (getIntent().getStringExtra("tag").equals("setting")){
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }

        mUtils = new RefreshUtils(this);
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = RefreshUtils.REFRESH_HEADER_PULLDOWN;
        ClassicsHeader.REFRESH_HEADER_REFRESHING = RefreshUtils.REFRESH_HEADER_REFRESHING;
        ClassicsHeader.REFRESH_HEADER_LOADING = RefreshUtils.REFRESH_HEADER_LOADING;
        ClassicsHeader.REFRESH_HEADER_RELEASE = RefreshUtils.REFRESH_HEADER_RELEASE;
        ClassicsHeader.REFRESH_HEADER_FINISH = RefreshUtils.REFRESH_HEADER_FINISH;
        ClassicsHeader.REFRESH_HEADER_FAILED = RefreshUtils.REFRESH_HEADER_FAILED;
        ClassicsHeader.REFRESH_HEADER_LASTTIME = RefreshUtils.REFRESH_HEADER_LASTTIME;

        ClassicsFooter.REFRESH_FOOTER_PULLUP = RefreshUtils.REFRESH_FOOTER_PULLUP;
        ClassicsFooter.REFRESH_FOOTER_RELEASE = RefreshUtils.REFRESH_FOOTER_RELEASE;
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = RefreshUtils.REFRESH_FOOTER_REFRESHING;
        ClassicsFooter.REFRESH_FOOTER_LOADING = RefreshUtils.REFRESH_FOOTER_LOADING;
        ClassicsFooter.REFRESH_FOOTER_FINISH = RefreshUtils.REFRESH_FOOTER_FINISH;
        ClassicsFooter.REFRESH_FOOTER_FAILED = RefreshUtils.REFRESH_FOOTER_FAILED;
        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = RefreshUtils.REFRESH_FOOTER_ALLLOADED;
    }

    @Override
    public void onBackPressed() {
        if (TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            exit();
            return;
        }
        super.onBackPressed();
    }

    long exitTime;

    /**
     * 退出app
     */

    private void exit() {
        ToastUtil.show("再次点击退出易购");


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.show(getResources().getString(R.string.tip_out));
//            Toast.makeText(getApplicationContext(), AppUtil.getResString(mContext, R.string.tip_out), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
//				•友盟	如果开发者调用Process.kill或者System.exit之类的方法杀死进程，
//				请务必在此之前调用MobclickAgent.onKillProcess(Context context)方法，
//				用来保存统计数据。
//			MobclickAgent.onKillProcess(mContext);

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);

        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        mTag = intent.getStringExtra("tag");
        if (mTag != null) {
            switch (mTag) {
                case "againBuy":
                    if (tabHomeBottom != null) {

                        tabHomeBottom.getTabAt(1).select();
                    }
                    break;
                case "user":
                    if (tabHomeBottom != null) {
                        tabHomeBottom.getTabAt(2).select();
                    }
                    break;
                case "setting":
                    finish();
                    startActivity(new Intent(this, LoginActivity.class));
                    break;
                case "langage":
                    Intent intent2 = getIntent();
                    finish();
                    startActivity(intent2);
                    break;
            }
        } else {
            if (tabHomeBottom != null) {
                tabHomeBottom.getTabAt(0).select();
            }
        }
        super.onNewIntent(intent);
    }

    @Override
    protected int getlayoutid() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initData() {
        super.initData();
//        new ShopCarFragment().loadData(this);
    }

    @Override
    protected void initView() {
        super.initView();

        flHome = (FrameLayout) findViewById(R.id.fl_home);

        if (ShardeUtil.getInt("main_1") == 2) {
            iv_yindao.setVisibility(View.GONE);
        } else {
            iv_yindao.setVisibility(View.VISIBLE);
        }

        iv_yindao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShardeUtil.getInt("main_1") == 0) {
                    iv_yindao.setImageResource(R.drawable.mian_yindao1);
                    ShardeUtil.putInt("main_1", 1);
                } else {
                    iv_yindao.setVisibility(View.GONE);
                    ShardeUtil.putInt("main_1", 2);
                }

            }
        });

        adapter = new MainFragmentAdapter(getSupportFragmentManager());
        int imgRes[] = new int[]{R.drawable.btn_index_bg_seletor, R.drawable.btn_shop_bg_seletor, R.drawable.btn_me_bg_seletor};
        String strs[] = new String[]{getResources().getString(R.string.sy),
                getResources().getString(R.string.shop_title), getResources().getString(R.string.wode)};
        tabHomeBottom.setOnTabSelectedListener(this);
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.home_tab_layout, null);
            ImageView iv = (ImageView) view.findViewById(R.id.tab_img);
            TextView tv = (TextView) view.findViewById(R.id.tab_text);
            iv.setImageResource(imgRes[i]);
            tv.setText(strs[i]);
            if (i == 1) {
                tv_showNum = (TextView) view.findViewById(R.id.tv_red_circle);
            }
            TabLayout.Tab tab = tabHomeBottom.newTab();
            tab.setCustomView(view);
            tabHomeBottom.addTab(tab, i == 0 ? true : false);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            if (tabHomeBottom != null) {

                tabHomeBottom.getTabAt(1).select();
            }
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();

        Fragment fragment = (Fragment) adapter.instantiateItem(flHome, position);
        adapter.setPrimaryItem(flHome, position, fragment);
        adapter.finishUpdate(flHome);

        if (fragment.getClass().equals(ShopCarFragment3.class)) {
            ((ShopCarFragment3) fragment).load();
        }
//        selectFragment(fragment);
//        tab.getCustomView().findViewById(R.id.tab_img).setSelected(true);
    }

    public void select(int index) {

        tabHomeBottom.getTabAt(0).select();
    }

    private Fragment tempFragment;

    public void selectFragment(Fragment fragment) {

        if (tempFragment != null || tempFragment != fragment) {
            if (!fragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().add(R.id.fl_home, fragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(tempFragment).show(fragment).commit();
            }
        }
        tempFragment = fragment;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.getCustomView().findViewById(R.id.tab_img).setSelected(false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    class MainFragmentAdapter extends FragmentPagerAdapter {

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainFragment2();
//                    return new HomeFragment();
                case 1:
                    return new ShopCarFragment3();
                case 2:
                    return new PersonFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}

package com.jlkf.ego.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.UserBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.newpage.utils.RegionCodeUtils;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.AppUtil;
import com.jlkf.ego.utils.CompanyUtil;
import com.jlkf.ego.utils.LanguageUtils;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.utils.ToastUtil;
import com.jlkf.ego.widget.AutoClearEditText;
import com.jlkf.ego.widget.AutoLookEditText;
import com.jlkf.ego.widget.BrightnessButton;
import com.jlkf.ego.widget.spiner.AbstractSpinerAdapter;
import com.jlkf.ego.widget.spiner.ItemInfo;
import com.jlkf.ego.widget.spiner.SpinerPopWindow;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 登录页面
 *
 * @author 邓超桂
 * @date 创建时间：2017年7月6日 下午3:40:13
 */
public class LoginActivity extends BaseActivity implements OnClickListener, AbstractSpinerAdapter.IOnItemSelectListener {

    private LinearLayout ll_country_item;
    private ImageView iv_country_logo;
    private TextView iv_country_name;
    private List<ItemInfo> countryList = new ArrayList<ItemInfo>();
    private SpinerPopWindow mSpinerPopWindow;
    private ItemInfo selectItem = new ItemInfo();

    private TextView tv_phone_code;
    private List<ItemInfo> phoneCodeList = new ArrayList<ItemInfo>();
    private SpinerPopWindow mSpinerPopPhoneCodeWindow;
    private ItemInfo selectPhoneCodeItem = new ItemInfo();


    private AutoClearEditText et_username;
    private AutoLookEditText et_password;

    private TextView tv_register, tv_forgetPassword;
    private Button btn_login;
    private boolean userNameInputTrue, pwdInputTrue;
    private BrightnessButton iv_wxchat_login, iv_facebook_login, iv_twitter_login;

    private long exitTime = 0;
    private String phoneNum = "";
    private String password = "";

    /**
     * 注册成功返回码
     */
    public static int REGISTER_USER_SUCCESS = 888;
    public static int REGISTER_USER_SUCCESS_CODE = 666;

    /**
     * 忘记密码成功返回码
     */
    public static int FORGET_PASSWORD_SUCCESS = 222;
    public static int FORGET_PASSWORD_SUCCESS_CODE = 333;
    private LinearLayout lin_login_edit, lin_bottom;
    private View v_login_bottom;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://登录成功数据返回
                    AppLog.Loge("登录成功数据返回:" + String.valueOf(msg.obj));
                    getResult(String.valueOf(msg.obj));
                    break;
                case 2://Toast处理
                    showToast(String.valueOf(msg.obj), 0);
                    break;
                default:
                    break;
            }
        }
    };
    private String mName = "34";
    private Intent mIntent;

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(false);
    }


    @Override
    public void initView() {

        setContentView(R.layout.activity_login);

        ll_country_item = getView(R.id.ll_country_item);
        iv_country_logo = getView(R.id.iv_country_logo);
        iv_country_name = getView(R.id.iv_country_name);

        tv_phone_code = getView(R.id.tv_phone_code);
        et_username = getView(R.id.et_username);
        et_password = getView(R.id.et_password);

        tv_register = getView(R.id.tv_register);
        tv_forgetPassword = getView(R.id.tv_forgetPassword);
        btn_login = getView(R.id.btn_login);

        iv_wxchat_login = getView(R.id.iv_wxchat_login);
        iv_facebook_login = getView(R.id.iv_facebook_login);
        iv_twitter_login = getView(R.id.iv_twitter_login);
        lin_login_edit = getView(R.id.lin_login_edit);
        v_login_bottom = getView(R.id.v_login_bottom);
        lin_bottom = getView(R.id.lin_bottom);
        /**
         * 过滤emoj表情
         */
        AppUtil.filterEmjoyEditText2(18, et_password);

        et_username.addTextChangedListener(userNameWatcher);
        et_password.addTextChangedListener(passWordWatcher);

        ll_country_item.setOnClickListener(this);
        tv_phone_code.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        iv_wxchat_login.setOnClickListener(this);
        iv_facebook_login.setOnClickListener(this);
        iv_twitter_login.setOnClickListener(this);

        //初始化
        btn_login.setEnabled(false);
        btn_login.setBackgroundResource(R.drawable.corner_gay);

        setupViews();//设置语言下拉

        setupPhoneCodeViews();//设置电话区号下拉
        et_username.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                LoginActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                //获取屏幕的高度
                int screenHeight = LoginActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - rect.bottom;
                Log.e("tag", "heightDifference:" + heightDifference + "-->screenHeight:"
                        + screenHeight + "-->rect.bottom:" + rect.bottom);
//                if (heightDifference > 200) {
//                    lin_bottom.setVisibility(View.INVISIBLE);
//                } else {
//                    lin_bottom.setVisibility(View.VISIBLE);
//                }
            }
        });


        UserBean user = getUser();
        if (user != null) {
            et_username.setText(user.getUMobile().substring(user.getUMobile().length() - 11, user.getUMobile().length()));
            et_password.setText(user.getUPassword());
        }
    }

    //设置语言下拉内容
    private void setupViews() {
        ItemInfo mItemInfo_china = new ItemInfo();
        mItemInfo_china.setCode("中文");
        mItemInfo_china.setDrawable(R.drawable.china);
        countryList.add(mItemInfo_china);

        ItemInfo mItemInfo_eng = new ItemInfo();//英语
        mItemInfo_eng.setCode("English");
        mItemInfo_eng.setDrawable(R.drawable.eng);
        countryList.add(mItemInfo_eng);

        ItemInfo mItemInfo_esp = new ItemInfo();//西班牙
        mItemInfo_esp.setCode("Español");
        mItemInfo_esp.setDrawable(R.drawable.esp);
        countryList.add(mItemInfo_esp);

        ItemInfo mItemInfo_ita = new ItemInfo();//意大利
        mItemInfo_ita.setCode("Italiano");
        mItemInfo_ita.setDrawable(R.drawable.ita);
        countryList.add(mItemInfo_ita);

        ItemInfo mItemInfo_fra = new ItemInfo();//法国
        mItemInfo_fra.setCode("Français");
        mItemInfo_fra.setDrawable(R.drawable.fra);
        countryList.add(mItemInfo_fra);

        String language = ShardeUtil.getString("language");

        int tag = 0;
        if (!TextUtils.isEmpty(language)) {

            switch (language) {
                case LanguageUtils.CHINAESE:
                    tag = 0;
                    break;
                case LanguageUtils.ENGLISH:
                    tag = 1;
                    break;
                case LanguageUtils.ESPAÑOL:
                    tag = 2;
                    break;
                case LanguageUtils.ITALIANO:
                    tag = 3;
                    break;
                case LanguageUtils.FRANÇAIS:
                    tag = 4;
                    break;
            }
        } else {

        }

        //取第一个默认作为选中
        selectItem = countryList.get(tag);
        updataCountryView(selectItem);


        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.refreshData(countryList, 0);
        mSpinerPopWindow.setItemListener(this);
    }

    //设置电话区号下拉
    private void setupPhoneCodeViews() {
        RegionCodeUtils.getRegionCode(this, new RegionCodeUtils.OnRegionCodeListener() {
            @Override
            public void getPopPhoneCodeWindow(List<ItemInfo> list) {
                phoneCodeList = list;
                if (getUser() != null) {
                    for (int i = 0; i < phoneCodeList.size(); i++) {
                        if (phoneCodeList.get(i).getName().equals(getUser().getAreaCode())) {
                            updataPhoneCodeView(phoneCodeList.get(i));
                        }
                    }
                } else {
                    //取第一个默认作为选中
                    selectPhoneCodeItem = phoneCodeList.get(0);
                    updataPhoneCodeView(selectPhoneCodeItem);
                }

                mSpinerPopPhoneCodeWindow = new SpinerPopWindow(LoginActivity.this);
                mSpinerPopPhoneCodeWindow.refreshData(phoneCodeList, 0);
                mSpinerPopPhoneCodeWindow.setItemListener(phoneClickListener);
            }
        });
       /* *//**
         * 34 西班牙
         * 33 法国
         * 49 德国
         * 351 葡萄牙
         * 39 意大利
         * 86 中文
         *//*

        ItemInfo mItemInfo_34 = new ItemInfo();
        mItemInfo_34.setName("34");
        mItemInfo_34.setCountrName(getString(R.string.es));
        phoneCodeList.add(mItemInfo_34);

        ItemInfo mItemInfo_001 = new ItemInfo();
        mItemInfo_001.setName("33");
        mItemInfo_001.setCountrName(getString(R.string.fr));
        phoneCodeList.add(mItemInfo_001);

        ItemInfo mItemInfo_001809 = new ItemInfo();
        mItemInfo_001809.setName("49");
        mItemInfo_001809.setCountrName(getString(R.string.dg));
        phoneCodeList.add(mItemInfo_001809);

        ItemInfo mItemInfo_351 = new ItemInfo();
        mItemInfo_351.setName("351");
        mItemInfo_351.setCountrName(getString(R.string.pty));
        phoneCodeList.add(mItemInfo_351);

        ItemInfo mItemInfo_39 = new ItemInfo();
        mItemInfo_39.setName("39");
        mItemInfo_39.setCountrName(getString(R.string.it));
        phoneCodeList.add(mItemInfo_39);

        ItemInfo mItemInfo_86 = new ItemInfo();
        mItemInfo_86.setName("86");
        mItemInfo_86.setCountrName(getString(R.string.zh));
        phoneCodeList.add(mItemInfo_86);*/
    }

    AbstractSpinerAdapter.IOnItemSelectListener phoneClickListener = new AbstractSpinerAdapter.IOnItemSelectListener() {
        @Override
        public void onItemClick(int pos) {
            {
                if (pos >= 0 && pos <= phoneCodeList.size()) {
                    ItemInfo value = phoneCodeList.get(pos);
                    selectPhoneCodeItem = value;
                    updataPhoneCodeView(selectPhoneCodeItem);
                }
            }
        }
    };

    @Override
    public void onItemClick(int pos) {
        if (pos >= 0 && pos <= countryList.size()) {
            ItemInfo value = countryList.get(pos);
            selectItem = value;
            updataCountryView(selectItem);
        }
    }

    private String language;

    /**
     * 更新界面的国家选择
     *
     * @param selectItem
     */
    private void updataCountryView(ItemInfo selectItem) {
        if (selectItem != null) {
            language = selectItem.getCode();
            iv_country_name.setText(selectItem.getCode());
            iv_country_logo.setImageResource(selectItem.getDrawable());


            // 登录的时候设置选择的语言
            switch (selectItem.getCode()) {
                case "中文":
                    LanguageUtils.switchLanguage(mContext, LanguageUtils.CHINAESE);
                    break;
                case "English":
                    LanguageUtils.switchLanguage(mContext, LanguageUtils.ENGLISH);
                    break;
                case "Español":
                    LanguageUtils.switchLanguage(mContext, LanguageUtils.ESPAÑOL);
                    break;
                case "Italiano":
                    LanguageUtils.switchLanguage(mContext, LanguageUtils.ITALIANO);
                    break;
                case "Français":
                    LanguageUtils.switchLanguage(mContext, LanguageUtils.FRANÇAIS);
                    break;
            }
            if (aBoolean) {
                finish();
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
    }

    private boolean aBoolean = false;

    /**
     * 更新界面的手机号码区号选择
     *
     * @param selectItem
     */
    private void updataPhoneCodeView(ItemInfo selectItem) {
        if (selectItem != null) {
            mName = selectItem.getCode();
            tv_phone_code.setText(mName);
        }
    }

    //手机号码输入框监听
    TextWatcher userNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            /*if (!AppUtil.IsNullString(s.toString().trim())) {
                String passwordStr = et_password.getText().toString();
                if (!AppUtil.IsNullString(passwordStr.toString().trim())) {
                    btn_login.setEnabled(true);
                    btn_login.setBackgroundResource(R.drawable.selector_btn_commit);
                } else {
                    btn_login.setEnabled(false);
                    btn_login.setBackgroundResource(R.drawable.corner_gay);
                }
            } else {
                btn_login.setEnabled(false);
                btn_login.setBackgroundResource(R.drawable.corner_gay);
            }*/
        }

        @Override
        public void afterTextChanged(Editable editable) {
            userNameInputTrue = (6 <= editable.length() && editable.length() <= 11);
            if (userNameInputTrue && pwdInputTrue) {
                btn_login.setEnabled(true);
                btn_login.setBackgroundResource(R.drawable.selector_btn_commit);
            } else {
                btn_login.setEnabled(false);
                btn_login.setBackgroundResource(R.drawable.corner_gay);
            }
        }
    };

    //密码输入框监听
    TextWatcher passWordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           /* if (!AppUtil.IsNullString(s.toString().trim())) {
                String userNameStr = et_username.getText().toString();
                if (!AppUtil.IsNullString(userNameStr.toString().trim())) {
                    btn_login.setEnabled(true);
                    btn_login.setBackgroundResource(R.drawable.selector_btn_commit);
                } else {
                    btn_login.setEnabled(false);
                    btn_login.setBackgroundResource(R.drawable.corner_gay);
                }
            } else {
                btn_login.setEnabled(false);
                btn_login.setBackgroundResource(R.drawable.corner_gay);
            }*/
        }

        @Override
        public void afterTextChanged(Editable editable) {
            pwdInputTrue = (6 <= editable.length() && editable.length() <= 18);
            if (userNameInputTrue && pwdInputTrue) {
                btn_login.setEnabled(true);
                btn_login.setBackgroundResource(R.drawable.selector_btn_commit);
            } else {
                btn_login.setEnabled(false);
                btn_login.setBackgroundResource(R.drawable.corner_gay);
            }
        }
    };


    /**
     * 登录数据处理
     *
     * @param json
     */
    private void getResult(String json) {

    }

    /**
     * 去登录
     */
    private void gotoLogin() {
        phoneNum = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        if (AppUtil.IsNullString(phoneNum)) {
            AppUtil.sendMessage(2, "请输入手机号~", mHandler);
            return;
        } else if (AppUtil.IsNullString(password)) {
            AppUtil.sendMessage(2, "请输入登录密码~", mHandler);
            return;
        } else if (!isContainAll(password)) {
            ToastUtil.show(getResources().getString(R.string.mmbxbhzmhsz));
        } else {
            AppLog.Loge("手机号码：" + phoneNum + "--密码：" + password);
            if (isNetworkConnected(mContext)) {
//                showProgressDialog();
//              String registrationId = JPushInterface.getRegistrationID(getApplicationContext());//获取极光的registrationId
//                NetworkUtil.setUserLoginAPI(phoneNum, password, mHandler, 1, mContext);

                try {
                    JSONObject map = new JSONObject();
                    map.put("mobile", mName.substring(1) + phoneNum);
                    map.put("passWord", password);
                    if (mName.equals("39")) {


                        map.put("uType", 1);
                    } else {
                        map.put("uType", 0);

                    }

                    HttpUtil.getInstacne(mActivity).post(Urls.login, UserBean.class, map, new LoginCallBack());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {

            }
        }

    }

    class LoginCallBack implements HttpUtil.OnCallBack<UserBean> {


        @Override
        public void success(UserBean data) {
            /**
             * 需要设置国家 和 地区 和密码
             */
            data.setUPassword(password);
            data.setAreaCode(mName);
            data.setCookie(true);
            data.setUType(data.getUType());

            ShardeUtil.putString("quhao", mName);
            // 保存登录状态数据到本地内存
            ShardeUtil.putUser(data);
            MyApplication.setUserBean(data);

            switch (mName) {
                case "34":
                    data.setCountry("ESPAÑA");
                    break;
                case "33":
                    data.setCountry("FRANCIA");
                    break;
                case "49":
                    data.setCountry("GERMANY");
                    break;
                case "351":
                    data.setCountry("PORTUGAL");
                    break;
                case "39":
                    data.setCountry("ITALIA");
                    break;
                case "86":
                    data.setCountry("中国");
                    break;
            }
            LoginMain();
        }


        @Override
        public void onError(String msg, int code) {
            if (code == 10001) {
                ToastUtil.show(getResources().getString(R.string.dhwk));

            } else if (code == 10002) {

            } else if (code == 10004) {

            } else if (code == 10005) {

            }
        }
    }


    /**
     * 退出app
     */
    private void exit() {
        ToastUtil.show("再次点击退出易购");

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast(AppUtil.getResString(mContext, R.string.tip_out), 0);
//            Toast.makeText(getApplicationContext(), AppUtil.getResString(mContext, R.string.tip_out), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
//				•友盟	如果开发者调用Process.kill或者System.exit之类的方法杀死进程，
//				请务必在此之前调用MobclickAgent.onKillProcess(Context context)方法，
//				用来保存统计数据。
//			MobclickAgent.onKillProcess(mContext);

//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            startActivity(intent);
//            System.exit(0);


        }
    }

//    @Override
//    public void onBackPressed() {
//        exit();
//    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_country_item://选择语言
//			mSpinerPopWindow.setWidth(mTView.getWidth());
                if (mSpinerPopPhoneCodeWindow != null && mSpinerPopPhoneCodeWindow.isShowing()) {
                    mSpinerPopPhoneCodeWindow.dismiss();
                }
                mSpinerPopWindow.showAsDropDown(ll_country_item);
                break;
            case R.id.tv_phone_code://选择手机区号
                if (mSpinerPopWindow != null && mSpinerPopWindow.isShowing()) {
                    mSpinerPopWindow.dismiss();
                }
                if (mSpinerPopPhoneCodeWindow == null) {
                    setupPhoneCodeViews();
                    return;
                }
                mSpinerPopPhoneCodeWindow.showAsDropDown(tv_phone_code, -CompanyUtil.dip2px(50), 0);
                break;
            case R.id.tv_register://新用户注册
                intent = new Intent(mContext, RegisterActivity.class);
                BaseActivity.startActivityForResult(intent, LoginActivity.this, REGISTER_USER_SUCCESS);
                break;
            case R.id.tv_forgetPassword://忘记密码？
                intent = new Intent(mContext, ForgetPasswordActivity.class);
                BaseActivity.startActivityForResult(intent, LoginActivity.this, FORGET_PASSWORD_SUCCESS);
                break;
            case R.id.btn_login://点击登录
                AppLog.Loge("登录");
                hideInput(LoginActivity.this);
                gotoLogin();
                break;
            case R.id.iv_wxchat_login://微信第三方登录
//            showToast("点击微信登录",0);
                loginThree(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_facebook_login://facebook第三方登录
//            showToast("点击facebook登录",0);
                loginThree(SHARE_MEDIA.FACEBOOK);
                break;
            case R.id.iv_twitter_login://twitter第三方登录
//            showToast("点击twitter登录",0);
                loginThree(SHARE_MEDIA.TWITTER);
                break;

            default:
                break;
        }
    }


    public void loginThree(SHARE_MEDIA share_media) {

        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.v("第三方授权", "开始" + share_media.toString());

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.v("第三方授权", "信息" + map.toString());
                // 开始绑定
                if (SHARE_MEDIA.WEIXIN == share_media) {
                    JSONObject object = new JSONObject();
                    try {
                        object.put("bindType", 1);
                        object.put("uWechat", map.get("openid").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    HttpUtil.getInstacne(mActivity).post(Urls.thirdLogin, UserBean.class, object, new LoginCallBack());
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.v("第三方授权", "失败" + share_media.toString());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.v("第三方授权", "取消" + share_media.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void LoginMain() {
        Intent intent;
        intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(mContext, "Login_page");
        aBoolean = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "Login_page");
    }


}


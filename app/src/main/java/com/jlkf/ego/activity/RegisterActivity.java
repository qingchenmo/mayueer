package com.jlkf.ego.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jlkf.ego.R;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.UserBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.newpage.utils.RegionCodeUtils;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.AppUtil;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.utils.ToastUtil;
import com.jlkf.ego.widget.AutoClearEditText;
import com.jlkf.ego.widget.AutoLookEditText;
import com.jlkf.ego.widget.TitleView;
import com.jlkf.ego.widget.spiner.AbstractSpinerAdapter;
import com.jlkf.ego.widget.spiner.ItemInfo;
import com.jlkf.ego.widget.spiner.SpinerPopWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注册用户页面
 *
 * @author 邓超桂
 * @date 创建时间：2017年7月7日 下午4:46:00
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {

    private ImageView iv_back;
    private TitleView title;
    private AutoClearEditText et_phone;
    private TextView tv_phone_code;
    private TextView tv_login;
    private List<ItemInfo> phoneCodeList = new ArrayList<ItemInfo>();
    private SpinerPopWindow mSpinerPopPhoneCodeWindow;
    private ItemInfo selectPhoneCodeItem = new ItemInfo();

    private Button btn_code;
    private EditText et_code;
    private AutoLookEditText et_password;
    private FrameLayout fl_bottom;
    //协议
    private boolean isAgreeXieyi = false;
    private ImageView iv_select_xieyi;
    private TextView tv_xieyi;

    private Button btn_register;
    private boolean userPhoneInputTrue, codeInputTrue, pwdInputTrue;

    private TimeCount time;// 构造CountDownTimer对象
    private int countryCode = 86;//国家编号，86中文
    private boolean timeDown;
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://获取验证码数据返回
                    AppLog.Loge("dcg", "获取验证码数据返回:" + String.valueOf(msg.obj));
                    getResult(String.valueOf(msg.obj));
                    break;
                case 2://网络异常
                    break;
                case 3://注册数据返回
                    AppLog.Loge("dcg", "注册数据返回:" + String.valueOf(msg.obj));
                    getResultRegister(String.valueOf(msg.obj));
                    break;
                default:
                    break;
            }
        }
    };
    private String mName;

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_register);

        time = new TimeCount(60000, 1000);

        title = getView(R.id.title);
        title.setBg(R.color.white);
        iv_back = getView(R.id.iv_title_left);
        et_phone = getView(R.id.et_phone);
        tv_phone_code = getView(R.id.tv_phone_code);
        btn_code = getView(R.id.btn_code);
        et_code = getView(R.id.et_code);
        et_password = getView(R.id.et_password);
        iv_select_xieyi = getView(R.id.iv_select_xieyi);
        tv_xieyi = getView(R.id.tv_xieyi);
        btn_register = getView(R.id.btn_register);
        tv_login = getView(R.id.tv_login);
        fl_bottom = getView(R.id.fl_bottom);
        //设置监听
        iv_back.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        iv_select_xieyi.setOnClickListener(this);
        tv_xieyi.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        et_phone.addTextChangedListener(userPhoneTextWatcher);
        et_code.addTextChangedListener(codeTextWatcher);
        et_password.addTextChangedListener(pwdTextWatcher);
        if (getIntent() != null) {
            String phone = getIntent().getStringExtra("phone");
            if (!AppUtil.IsNullString(phone)) {
                et_phone.setText(phone);
                Selection.setSelection(et_phone.getText(), et_phone.getText().length());
            }
        }

        setupPhoneCodeViews();//设置电话区号下拉
        tv_phone_code.setOnClickListener(phoneCodeClick);

        isAgreeXieyi = true;
        selectAgreeXieyi(isAgreeXieyi);
        et_phone.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                RegisterActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                //获取屏幕的高度
                int screenHeight = RegisterActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - rect.bottom;
                if (heightDifference > 0) {
                    fl_bottom.setVisibility(View.INVISIBLE);
                } else {
                    fl_bottom.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    OnClickListener phoneCodeClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mSpinerPopPhoneCodeWindow == null) {
                setupPhoneCodeViews();
                return;
            }
            mSpinerPopPhoneCodeWindow.showAsDropDown(tv_phone_code);
        }
    };
    TextWatcher userPhoneTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            userPhoneInputTrue = (s.length() >= 5);
            btn_code.setEnabled(userPhoneInputTrue);
            btn_code.setSelected(userPhoneInputTrue);
            setBtnBg();
        }
    };
    TextWatcher codeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            codeInputTrue = (4 == s.length());
            setBtnBg();
        }
    };
    TextWatcher pwdTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            pwdInputTrue = (6 <= s.length() && s.length() <= 18);
            setBtnBg();
        }
    };

    private void setBtnBg() {
        if (userPhoneInputTrue && pwdInputTrue && codeInputTrue && isAgreeXieyi) {
            btn_register.setEnabled(true);
            btn_register.setBackgroundResource(R.drawable.selector_btn_commit);
        } else {
            btn_register.setEnabled(false);
            btn_register.setBackgroundResource(R.drawable.corner_gay);
        }
    }

    int codeTime = 100;

    /**
     * 验证码倒计时
     */
    private void timing() {
        codeTime--;
        btn_code.setText(codeTime + "s");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (codeTime > 0) {
                    timing();
                } else {
                    btn_code.setText("重新发送验证码");
                    timeDown = false;
                    codeTime = 100;
                }
            }
        }, 1000);
    }

    private void sendCode(String phone) {

        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);


        HttpUtil.getInstacne(mActivity).get(Urls.sendCode, JSONObject.class, map, new HttpUtil.OnCallBack<JSONObject>() {
            @Override
            public void success(JSONObject data) {
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    /**
     * 验证密码最好包含数字和字母
     */
    private boolean verifyPwd() {
        String pwd = et_password.getText().toString().trim();
        boolean isDigit = false;//用来表示是否包含数字
        boolean isLetter = false;//用来表示是否包含字母
        int length = pwd.length();
        for (int i = 0; i < length; i++) {
            if (Character.isDigit(pwd.charAt(i))) {
                isDigit = true;
            } else if (Character.isLetter(pwd.charAt(i))) {
                isLetter = true;
            }
        }
        return isDigit && isLetter;
    }

    //设置电话区号下拉
    private void setupPhoneCodeViews() {
        /*ItemInfo mItemInfo_34 = new ItemInfo();
        mItemInfo_34.setName("34");
        mItemInfo_34.setCountrName("西班牙");
        phoneCodeList.add(mItemInfo_34);

        ItemInfo mItemInfo_001 = new ItemInfo();
        mItemInfo_001.setName("33");
        mItemInfo_001.setCountrName("法国");
        phoneCodeList.add(mItemInfo_001);

        ItemInfo mItemInfo_001809 = new ItemInfo();
        mItemInfo_001809.setName("49");
        mItemInfo_001809.setCountrName("德国");
        phoneCodeList.add(mItemInfo_001809);

        ItemInfo mItemInfo_351 = new ItemInfo();
        mItemInfo_351.setName("351");
        mItemInfo_351.setCountrName("葡萄牙");
        phoneCodeList.add(mItemInfo_351);

        ItemInfo mItemInfo_39 = new ItemInfo();
        mItemInfo_39.setName("39");
        mItemInfo_39.setCountrName("意大利");
        phoneCodeList.add(mItemInfo_39);

        ItemInfo mItemInfo_86 = new ItemInfo();
        mItemInfo_86.setName("86");
        mItemInfo_86.setCountrName("中文");
        phoneCodeList.add(mItemInfo_86);*/
        RegionCodeUtils.getRegionCode(this, new RegionCodeUtils.OnRegionCodeListener() {
            @Override
            public void getPopPhoneCodeWindow(List<ItemInfo> list) {
                phoneCodeList = list;
                //取第一个默认作为选中
                selectPhoneCodeItem = phoneCodeList.get(0);
                updataPhoneCodeView(selectPhoneCodeItem);

                mSpinerPopPhoneCodeWindow = new SpinerPopWindow(RegisterActivity.this);
                mSpinerPopPhoneCodeWindow.refreshData(phoneCodeList, 0);
                mSpinerPopPhoneCodeWindow.setItemListener(phoneClickListener);
            }
        });
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

    /**
     * 同意协议
     *
     * @param isAgreeXieyi2
     */
    private void selectAgreeXieyi(boolean isAgreeXieyi2) {
        if (isAgreeXieyi2) {//同意
            iv_select_xieyi.setImageResource(R.drawable.img_trick);
        } else {//未同意
            iv_select_xieyi.setImageResource(R.drawable.img_trick_no);
        }
    }

    /**
     * 去注册
     */
    private void gotoRegister() {

        final String phoneNum = et_phone.getText().toString().trim();
        final String passWord = et_password.getText().toString().trim();
        final String code = et_code.getText().toString().trim();
        if (!isContainAll(passWord)) {
            ToastUtil.show(getResources().getString(R.string.mmbxbhzmhsz));
            return;
        }

        if (!TextUtils.isEmpty(phoneNum) || !TextUtils.isEmpty(passWord) || !TextUtils.isEmpty(code)) {
            if (isNetworkConnected(mContext)) {

                try {
                    JSONObject object = new JSONObject();
                    object.put("mobile", mName.substring(1) + phoneNum);
                    object.put("code", code);
                    if (mName.equals("39")) {
                        object.put("uType", 1);
                    } else {
                        object.put("uType", 0);
                    }
                    object.put("passWord", passWord);
                    HttpUtil.getInstacne(mActivity).post(Urls.register, UserBean.class, object, new HttpUtil.OnCallBack<UserBean>() {

                        public Intent mIntent;

                        @Override
                        public void success(UserBean data) {
                            ApiManager.upauditCode(data.getUId());//调用接口生成审核码
                            data.setUPassword(passWord);
                            data.setAreaCode(mName);
                            ShardeUtil.putString("quhao", mName);
                            data.setCookie(true);
                            data.setUType(data.getUType());
                            // 保存登录状态数据到本地内存
                            ShardeUtil.putUser(data);
                            MyApplication.setUserBean(data);
                            // 登录成功
                            mIntent = new Intent(mActivity, MainActivity.class);
                            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mIntent);
                            finish();

                        }

                        @Override
                        public void onError(String msg, int code) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


//        if (AppUtil.IsNullString(phoneNum)) {
//            AppUtil.sendMessage(2, "请输入手机号~", mHandler);
//            return;
//        } else if (!AppUtil.isPhoneNumber(phoneNum)) {
//            AppUtil.sendMessage(2, "手机号格式不正确~", mHandler);
//            return;
//        } else if (AppUtil.IsNullString(passWord)) {
//            AppUtil.sendMessage(2, "请输入登录密码~", mHandler);
//            return;
//        } else if (!AppUtil.isPassword(passWord) || !verifyPwd()) {
//            AppUtil.sendMessage(2, "密码格式为6-18位的数字或字母~", mHandler);
//            return;
//        } else if (AppUtil.IsNullString(code)) {
//            AppUtil.sendMessage(2, "请输入验证码~", mHandler);
//            return;
//        } else if (!isAgreeXieyi) {
//            AppUtil.sendMessage(2, "请先阅读与同意协议~", mHandler);
//            return;
//        } else {
//            AppLog.Loge("dcg", "手机号码：" + phoneNum + "--密码：" + passWord + "--验证码：" + code);
//            if (isNetworkConnected(mContext)) {
//
//                try {
//                    JSONObject object = new JSONObject();
//                    object.put("mobile", phoneNum);
//                    object.put("code", code);
//                    object.put("passWord", passWord);
//                    object.put("uType", 0);
//                    HttpUtil.getInstacne(mActivity).post(Urls.register, object, new StringCallback() {
//                        @Override
//                        public void onSuccess(Response<String> response) {
//
//                        }
//                    });
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            } else {
//                AppUtil.sendMessage(2, AppUtil.networkError(mContext), mHandler);
//            }
//        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        if (timeDown) return;
        String strPhone = et_phone.getText().toString();
        if (AppUtil.IsNullString(strPhone)) {
            AppUtil.sendMessage(2, "请填写手机号码~", mHandler);
            return;
        } else {
            timeDown = true;

            sendCode(mName + et_phone.getText().toString().trim());

            timing();
//            if (isNetworkConnected(this)) {
////                showProgressDialog();
//                timing();
//                NetworkUtil.setPhoneCode(countryCode,strPhone,"resetpass",mHandler, 1, this);
//            } else {
//                AppUtil.sendMessage(2, AppUtil.networkError(mContext), mHandler);
//            }
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btn_code.setText(getResources().getString(R.string.re_send_code));
            btn_code.setTextColor(mContext.getResources().getColor(R.color.app_yellow));
            btn_code.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btn_code.setClickable(false);
            btn_code.setTextColor(mContext.getResources().getColor(R.color.silver));
            btn_code.setText(getResources().getString(R.string.re_send_code) + "(" + millisUntilFinished
                    / 1000 + "s)");
        }
    }

    /**
     * 获取验证码
     *
     * @param json
     */
    private void getResult(String json) {
        try {
            Gson gson = new Gson();
//            RegisterBean mRegisterBean=gson.fromJson(json, RegisterBean.class);
//            if(mRegisterBean!=null){
//                if(mRegisterBean.getReturnCode()==CommonEnum.SUCCESS){
//                    if(mRegisterBean.getData()!=null){
//                        AppUtil.sendMessage(2, "已发送~", mHandler);
//                    }
//                }else if(mRegisterBean.getReturnCode()==CommonEnum.SEND_CODE_FAIL){
//                    AppUtil.sendMessage(2, "发送失败~", mHandler);
//                }else{
//                    AppUtil.sendMessage(2, "获取验证码失败~", mHandler);
//                }
//            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void getResultRegister(String json) {
        try {
            Gson gson = new Gson();
//            BaseBean mBaseBean=gson.fromJson(json, BaseBean.class);
//            if(mBaseBean!=null){
//                if(mBaseBean.getReturnCode()==CommonEnum.SUCCESS){
//
//                    //友盟， 注册账号成功次数统计事件
//                    Map<String, String> map=new HashMap<String, String>();
//                    map.put("mobile", et_phone.getText().toString().trim());
//                    MobclickAgent.onEvent(mContext,UMConstant.mine_registerSuccess_click, UMUtils.getUMEventValue(map));
//
//                    AppUtil.sendMessage(2, "注册成功", mHandler);
//
//                    Intent intent=new Intent();
//                    intent.putExtra("mobile", et_phone.getText().toString().trim());
//                    intent.putExtra("password", et_password.getText().toString());
//                    RegisterActivity.this.setResult(LoginActivity.REGISTER_USER_SUCCESS_CODE, intent);
//
//                    onBackPressed();
//                }else if(mBaseBean.getReturnCode()==CommonEnum.PHONE_IS_EXIT){
//                    AppUtil.sendMessage(2, "手机号已存在~ ", mHandler);
//                }else if(mBaseBean.getReturnCode()==CommonEnum.CODE_IS_ERROR){
//                    AppUtil.sendMessage(2, "验证码错误 ~", mHandler);
//                }else if(mBaseBean.getReturnCode()==CommonEnum.CODE_IS_OUTTIME){
//                    AppUtil.sendMessage(2, "验证码过期~ ", mHandler);
//                }else if(mBaseBean.getReturnCode()==CommonEnum.REGISTER_fAIL){
//                    AppUtil.sendMessage(2, "注册失败 ~", mHandler);
//                }else{
//                    AppUtil.sendMessage(2, mBaseBean.getErrorMsg(), mHandler);
//                }
//            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        BaseActivity.finish(RegisterActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.btn_code://获取验证码
                getCode();
                break;
            case R.id.iv_select_xieyi://选择同意协议
                if (isAgreeXieyi) {
                    isAgreeXieyi = false;
                    selectAgreeXieyi(isAgreeXieyi);
                } else {
                    isAgreeXieyi = true;
                    selectAgreeXieyi(isAgreeXieyi);
                }
                setBtnBg();
                break;
            case R.id.tv_xieyi://浏览协议
//			AppLog.Loge("浏览协议");
                Intent intent1 = new Intent(mContext, AppServerXieYiActivity.class);
                startActivity(intent1, RegisterActivity.this);
                break;
            case R.id.btn_register://立即注册

                gotoRegister();
                break;
            case R.id.tv_login://去登录
                Intent intent = new Intent(mContext, LoginActivity.class);
                BaseActivity.startActivity(intent, RegisterActivity.this);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(mContext, "Register_page");

    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "Register_page");
    }
}


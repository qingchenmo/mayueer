package com.jlkf.ego.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jlkf.ego.R;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.ToastUti;
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

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 忘记密码页面
 *
 * @author 邓超桂
 * @date 创建时间：2017年7月7日 下午4:46:00
 */
public class ForgetPasswordActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.title)
    TitleView mTitle;
    private ImageView iv_back;
    private AutoClearEditText et_phone;
    private TextView tv_phone_code;
    private List<ItemInfo> phoneCodeList = new ArrayList<ItemInfo>();
    private SpinerPopWindow mSpinerPopPhoneCodeWindow;
    private ItemInfo selectPhoneCodeItem = new ItemInfo();
    private LinearLayout lin_pwd;
    private Button btn_code;
    private EditText et_code;

    private AutoLookEditText et_password;

    private Button btn_forgetPassword;
    private boolean userPhoneInputTrue, codeInputTrue, pwdInputTrue;

    private TimeCount time;// 构造CountDownTimer对象
    private int text;//用于接收从个人信息页面传递的数值，用于区分是绑定手机号，还是忘记密码

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://获取验证码数据返回

                    AppLog.Loge("dcg", "获取验证码数据返回:" + String.valueOf(msg.obj));
                    Looper.prepare();
                    getResult(String.valueOf(msg.obj));
                    Looper.loop();
                    break;
                case 2://网络异常
                    showToast(String.valueOf(msg.obj), 0);
                    break;
                case 3://忘记密码数据返回
                    AppLog.Loge("dcg", "/忘记密码数据返回:" + String.valueOf(msg.obj));
                    getResultForGetPassword(String.valueOf(msg.obj));
                    break;
                case 4://去注册用户
                    Looper.prepare();
                    gotoRegisterPhone();
                    Looper.loop();
                    break;
                default:
                    break;
            }
        }
    };
    private String mName;
    private View view;

    private void sendCode() {
        String phone = mName + et_phone.getText().toString();


        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);


        HttpUtil.getInstacne(mActivity).get(Urls.sendCode, JSONObject.class, map, new HttpUtil.OnCallBack<JSONObject>() {
            @Override
            public void success(JSONObject data) {
                showToast(getResources().getString(R.string.send_ok), 0);
            }

            @Override
            public void onError(String msg, int code) {

            }
        });

    }

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_forget_password);

        ButterKnife.bind(this);
        time = new TimeCount(60000, 1000);

        iv_back = getView(R.id.iv_title_left);
        et_phone = getView(R.id.et_phone);
        tv_phone_code = getView(R.id.tv_phone_code);
        btn_code = getView(R.id.btn_code);
        view = getView(R.id.view);
        btn_code.setEnabled(false);
        et_code = getView(R.id.et_code);
        et_password = getView(R.id.et_password);
        btn_forgetPassword = getView(R.id.btn_forgetPassword);
        btn_forgetPassword.setEnabled(false);

        et_phone.addTextChangedListener(userPhoneTextWatcher);
        et_password.addTextChangedListener(pwdTextWatcher);
        et_code.addTextChangedListener(codeTextWatcher);
        //设置监听
        iv_back.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        btn_forgetPassword.setOnClickListener(this);

        setupPhoneCodeViews();//设置电话区号下拉
        tv_phone_code.setOnClickListener(phoneCodeClick);
        text = getIntent().getIntExtra("text", 0);
        lin_pwd = (LinearLayout) findViewById(R.id.lin_pwd);
        if (text == 1) {
            view.setVisibility(View.GONE);
            mTitle.setTitle(getResources().getString(R.string.bdsjh));
            lin_pwd.setVisibility(View.GONE);
            // 设置保存过的用户手机
            et_phone.setText(getUser().getUMobile().substring(getUser().getUMobile().length() - 11, getUser().getUMobile().length()));
            String aercode = getUser().getUMobile().substring(0, getUser().getUMobile().length() - 11);
            // 设置保存过的区号
            for (int i = 0; i < phoneCodeList.size(); i++
                    ) {
                if (phoneCodeList.get(i).getName().equals(aercode)) {
                    updataPhoneCodeView(phoneCodeList.get(i));
                }
            }
            pwdInputTrue = true;
        } else {
        }


    }

    TextWatcher userPhoneTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            userPhoneInputTrue = (6 <= s.length() && s.length() <= 11);
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
            if (text != 1) {
                pwdInputTrue = (6 <= s.length() && s.length() <= 18);
            }
            setBtnBg();
        }
    };

    private void setBtnBg() {
        if (userPhoneInputTrue && pwdInputTrue && codeInputTrue) {
            btn_forgetPassword.setEnabled(true);
            btn_forgetPassword.setBackgroundResource(R.drawable.selector_btn_commit);
        } else {
            btn_forgetPassword.setEnabled(false);
            btn_forgetPassword.setBackgroundResource(R.drawable.corner_gay);
        }
    }

    OnClickListener phoneCodeClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            mSpinerPopPhoneCodeWindow.showAsDropDown(tv_phone_code);
        }
    };


    //设置电话区号下拉
    private void setupPhoneCodeViews() {
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
        phoneCodeList.add(mItemInfo_86);

        //取第一个默认作为选中
        selectPhoneCodeItem = phoneCodeList.get(0);
        updataPhoneCodeView(selectPhoneCodeItem);

        mSpinerPopPhoneCodeWindow = new SpinerPopWindow(this);
        mSpinerPopPhoneCodeWindow.refreshData(phoneCodeList, 0);
        mSpinerPopPhoneCodeWindow.setItemListener(phoneClickListener);
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
            mName = selectItem.getName();
            tv_phone_code.setText("+" + mName);
        }
    }

    /**
     * 去重置密码
     */
    private void gotoForgetPassword() {
        final String phoneNum = et_phone.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        final String cord = et_code.getText().toString().trim();

        if (text == 1) {
            bindMobile(phoneNum, cord);
        } else {
            if (!isContainAll(password)) {
                ToastUtil.show(getResources().getString(R.string.mmbxbhzmhsz));
                return;
            }
            forgetPassWord(phoneNum, password, cord);
        }


    }

    // 绑定手机号
    private void bindMobile(final String phoneNum, String cord) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", mName + phoneNum);
        map.put("uId", getUser().getUId() + "");
        map.put("code", cord);
        HttpUtil.getInstacne(mActivity).get(Urls.boundPhone, String.class, map, new HttpUtil.OnCallBack<String>() {
            @Override
            public void success(String data) {

                getUser().setUMobile(phoneNum);
                MyApplication.setUserBean(getUser());

                ToastUti.show("绑定成功");
                setResult(mActivity.RESULT_OK);
                finish();
            }

            @Override
            public void onError(String msg, int code) {

            }
        });

    }

    private void forgetPassWord(String phoneNum, String password, String cord) {
        try {
            JSONObject object = new JSONObject();
            object.put("uMobile", mName + phoneNum);

            object.put("uPassword", password);
            object.put("code", cord);
            object.put("uType", 0);
            HttpUtil.getInstacne(mActivity).post(Urls.forgetPassWord, String.class, object, new HttpUtil.OnCallBack<String>() {


                @Override
                public void success(String data) {
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

    /**
     * 获取验证码
     */
    private void getCode() {
        if (timeDown) return;
        String strPhone = et_phone.getText().toString();
        sendCode();

        timeDown = true;
        timing();
           /* if (isNetworkConnected(this)) {
                  showProgressDialog();
//                NetworkUtil.setPhoneCode(countryCode,strPhone,"resetpass",mHandler, 1, this);
            } else {
                AppUtil.sendMessage(2, AppUtil.networkError(mContext), mHandler);
            }*/
    }

    int codeTime = 100;
    boolean timeDown = false;

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
                    btn_code.setText(getResources().getString(R.string.re_send_code));
                    timeDown = false;
                    codeTime = 100;
                }
            }
        }, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
//                    AppUtil.sendMessage(2, "已发送~", mHandler);
//                    time.start();
//                }else if(mRegisterBean.getReturnCode()==CommonEnum.SEND_CODE_FAIL){
//                    AppUtil.sendMessage(2, "发送失败~", mHandler);
//                }else if(mRegisterBean.getReturnCode()==CommonEnum.USER_NO_REGISTER){//该用户未注册
//
//                    AppUtil.sendMessage(4, "", mHandler);
//
//                }else{
//                    AppUtil.sendMessage(2, "获取验证码失败~", mHandler);
//                }
//            }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    private void gotoRegisterPhone() {

//        //跳转去注册
//        dialog=MyDialog.showTelPhoneDialog(mContext, "温馨提示", "该手机号码尚未被注册。", "取消", "去注册",
//                new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                }, new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AppLog.Loge("需注册账号");
//                        Intent intent=new Intent(mContext,RegisterActivity.class);
//                        intent.putExtra("phone", et_phone.getText().toString().trim());
//                        BaseActivity.startActivity(intent, ForgetPasswordActivity.this);
////						BaseActivity.startActivityForResult(intent, ForgetPasswordActivity.this, LoginActivity.REGISTER_USER_SUCCESS);
//                        //友盟，点击注册账号次数统计
//                        MobclickAgent.onEvent(mContext,UMConstant.mine_register_click, UMUtils.getUMEventValue(null));
//                        dialog.dismiss();
//                        ForgetPasswordActivity.this.finish();
//                    }
//                });

    }

    private void getResultForGetPassword(String json) {
        try {
            Gson gson = new Gson();

//            BaseBean mBaseBean=gson.fromJson(json, BaseBean.class);
//            if(mBaseBean!=null){
//                if(mBaseBean.getReturnCode()==CommonEnum.SUCCESS){
//
//                    //友盟， 忘记密码成功次数统计事件
//                    Map<String, String> map=new HashMap<String, String>();
//                    map.put("mobile", et_phone.getText().toString().trim());
//                    MobclickAgent.onEvent(mContext,UMConstant.mine_forgetPasswordSuccess_click, UMUtils.getUMEventValue(map));
//
//                    AppUtil.sendMessage(2, "密码重置成功", mHandler);
//
//                    Intent intent=new Intent();
//                    intent.putExtra("mobile", et_phone.getText().toString().trim());
//                    intent.putExtra("password", et_password.getText().toString());
//                    ForgetPasswordActivity.this.setResult(LoginActivity.FORGET_PASSWORD_SUCCESS_CODE, intent);
//
//                    onBackPressed();
//                }else if(mBaseBean.getReturnCode()==CommonEnum.CODE_IS_ERROR){
//                    AppUtil.sendMessage(2, "验证码错误 ~", mHandler);
//                }else if(mBaseBean.getReturnCode()==CommonEnum.CODE_IS_OUTTIME){
//                    AppUtil.sendMessage(2, "验证码过期~ ", mHandler);
//                }else if(mBaseBean.getReturnCode()==CommonEnum.FORGET_PASSWORD_FAIL){
//                    AppUtil.sendMessage(2, "重置密码失败 ~", mHandler);
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
        BaseActivity.finish(ForgetPasswordActivity.this);
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
            case R.id.btn_forgetPassword://确定
                gotoForgetPassword();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(mContext, "ForgetPassword_page");
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "ForgetPassword_page");
    }
}



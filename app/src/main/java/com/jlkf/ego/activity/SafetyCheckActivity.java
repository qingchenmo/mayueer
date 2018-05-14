package com.jlkf.ego.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jlkf.ego.R;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.umanalytics.UMUtils;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.AppUtil;
import com.jlkf.ego.utils.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 安全检测(短信验证手机号码)
 *
 * @author 邓超桂
 * @date 创建时间：2017年7月11日 下午3:40:13
 */
public class SafetyCheckActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    //title
    private TextView tv_title;
    private ImageView iv_back;

    private EditText et_code;
    private Button btn_code;

    private Button btn_next;

    private TimeCount time;// 构造CountDownTimer对象

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://获取验证码数据返回
                    AppLog.Loge("dcg", "获取验证码数据返回:" + String.valueOf(msg.obj));
                    getResult(String.valueOf(msg.obj));
                    break;
                case 2://网络异常
                    showToast(String.valueOf(msg.obj), 0);
                    break;
                default:
                    break;
            }
        }
    };
    private String mUMobile;
    private String mPhone;
    private boolean isok = false;

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_safe_check);

        ButterKnife.bind(this);
        time = new TimeCount(60000, 1000);

        tv_title = getView(R.id.tv_title);
        iv_back = getView(R.id.iv_back);

        tv_title.setText(getResources().getString(R.string.verification));

        et_code = getView(R.id.et_code);
        btn_code = getView(R.id.btn_code);
        btn_next = getView(R.id.btn_next);


        iv_back.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        // 设置手机号     登陆的手机号  前三位加8号加后四位
        mUMobile = getUser().getUMobile();
        String qiansanwei = mUMobile.substring(mUMobile.length() - 11, mUMobile.length() - 8);
        String housiwei = mUMobile.substring(mUMobile.length() - 4, mUMobile.length());
        mTvMobile.setText(qiansanwei + "****" + housiwei);


        mPhone = mUMobile.substring(mUMobile.length() - 11, mUMobile.length());

        setBtnBg();
        et_code.addTextChangedListener(this);
    }

    @Override
    public void onBackPressed() {
        BaseActivity.finish(SafetyCheckActivity.this);
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
                    btn_code.setText(getResources().getString(R.string.cxfsyzm));
                    timeDown = false;
                    codeTime = 100;
                }
            }
        }, 1000);
    }


    private void setBtnBg() {
        if (isok) {
            btn_next.setEnabled(true);
            btn_next.setBackgroundResource(R.drawable.selector_btn_commit);
        } else {
            btn_next.setEnabled(false);
            btn_next.setBackgroundResource(R.drawable.corner_gay);
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        timing();

        if (isNetworkConnected(this)) {
            sendCodeNet();
        } else {
            AppUtil.sendMessage(2, AppUtil.networkError(mContext), mHandler);
        }
    }

    private void sendCodeNet() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", mUMobile);

        HttpUtil.getInstacne(mActivity).get(Urls.sendCode, JSONObject.class, map, new HttpUtil.OnCallBack<JSONObject>() {
            @Override
            public void success(JSONObject data) {
                ToastUtil.show(getResources().getString(R.string.send_ok));
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.length() == 4) {
            isok = true;
        } else {
            isok = false;

        }
        setBtnBg();
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btn_code.setText(getResources().getString(R.string.cxfsyzm));
            btn_code.setTextColor(mContext.getResources().getColor(R.color.app_red));
            btn_code.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btn_code.setClickable(false);
            btn_code.setTextColor(mContext.getResources().getColor(R.color.silver));
            btn_code.setText(getResources().getString(R.string.cxfsyzm)+ "(" + millisUntilFinished
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                onBackPressed();
                break;
            case R.id.btn_code://获取验证码
                getCode();
                break;
            case R.id.btn_next://下一步,修改密码
                yanzhengCode();

                break;
        }
    }

    private void yanzhengCode() {
        Map<String, String> map = new HashMap<>();

        map.put("phone", mUMobile);
        map.put("code", et_code.getText().toString());
        HttpUtil.getInstacne(mActivity).get(Urls.checkCode, String.class, map, new HttpUtil.OnCallBack() {
            @Override
            public void success(Object data) {
                Intent intent = new Intent(mContext, UpdatePasswordActivity.class);
                BaseActivity.startActivity(intent, SafetyCheckActivity.this);

            }

            @Override
            public void onError(String msg, int code) {
                ToastUtil.show(msg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(mContext, "SafetyCheck_page");
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "SafetyCheck_page");
    }
}

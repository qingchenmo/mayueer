package com.jlkf.ego.activity;

import android.content.Intent;
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
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;

/**
 * 修改密码
 *
 * @author 邓超桂
 * @date 创建时间：2017年7月11日 下午3:40:13
 */
public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener {

    //title
    private TextView tv_title;
    private ImageView iv_back;

    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_new_password_comfire;

    private Button btn_sure;


    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://密码修改数据返回
                    AppLog.Loge("dcg", "密码修改数据返回:" + String.valueOf(msg.obj));
                    dissmissProgressDialog();
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

    @Override
    public void iniActivityAnimation() {
        //滑动关闭activity
        addSwipeFinishLayout();
        //设置滑动开启/关闭
        setEnableGesture(true);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_update_password);

        ButterKnife.bind(this);
        tv_title = getView(R.id.tv_title);
        iv_back = getView(R.id.iv_back);


        tv_title.setText(getResources().getString(R.string.change_password));


        et_old_password = getView(R.id.et_old_password);
        et_new_password = getView(R.id.et_new_password);
        et_new_password_comfire = getView(R.id.et_new_password_comfire);
        btn_sure = getView(R.id.btn_sure);

        et_old_password.addTextChangedListener(new BtnListener(0));
        et_new_password.addTextChangedListener(new BtnListener(1));
        et_new_password_comfire.addTextChangedListener(new BtnListener(2));

        iv_back.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    public boolean is1 = false, is2 = false, is3 = false;

    public void setBG() {
        if (is1 && is2 && is3) {
            btn_sure.setEnabled(true);
            btn_sure.setBackgroundResource(R.drawable.selector_btn_commit);
        } else {
            btn_sure.setEnabled(false);
            btn_sure.setBackgroundResource(R.drawable.corner_gay);
        }
    }


    public class BtnListener implements TextWatcher {

        public BtnListener(int tag) {
            this.tag = tag;
        }

        private int tag;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (tag) {
                case 0:
                    if (s.length() >= 6 &&  s.length() <= 18) {
                        is1 = true;
                    } else {
                        is1 = false;
                    }
                    break;
                case 1:
                    if (s.length() >= 6 &&  s.length() <= 18) {
                        is2 = true;
                    } else {
                        is2 = false;
                    }
                    break;
                case 2:
                    if (s.length() >= 6 &&  s.length() <= 18) {
                        is3 = true;
                    } else {
                        is3 = false;
                    }
                    break;
            }
            setBG();
        }
    }

    @Override
    public void onBackPressed() {
        BaseActivity.finish(UpdatePasswordActivity.this);
    }

    /**
     * 确认修改密码
     */
    private void commitUpdatePassword() {
        String oldPassword = et_old_password.getText().toString();
        String newPassword = et_new_password.getText().toString();
        String newPasswordComfire = et_new_password_comfire.getText().toString();
        if (!isContainAll(newPassword) || !isContainAll(newPasswordComfire) ||!isContainAll(oldPassword)  ) {
            ToastUtil.show(getResources().getString(R.string.mmbxbhzmhsz));
            return;
        }
//                NetworkUtil.userRegisterAPI(phoneNum, password,cord,countryCode, mHandler, 1,mContext);
                changPassWord(newPassword, newPasswordComfire);

    }

    private void changPassWord(String newPassword, String newPasswordComfire) {
        String oldPassword = et_old_password.getText().toString();

        JSONObject object = new JSONObject();
        try {
            object.put("uId", getUser().getUId());
            object.put("oldpwd", oldPassword);
            object.put("pwdone", newPassword);
            object.put("pwdtwo", newPasswordComfire);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.getInstacne(mActivity).post(Urls.updatePassWord, String.class, object, new HttpUtil.OnCallBack() {

            private Intent mIntent;

            @Override
            public void success(Object data) {
                getUser().setUPassword("");
                ShardeUtil.putUser(getUser());
                mIntent = new Intent(UpdatePasswordActivity.this, MainActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mIntent.putExtra("tag", "setting");
                startActivity(mIntent);
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtil.show(msg);
            }
        });
    }

    /**
     * 密码修改
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
            case R.id.btn_sure://确认修改密码
                commitUpdatePassword();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.onResumeUMActivity(mContext, "UpdatePassword_page");
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.onPauseUMActivity(mContext, "UpdatePassword_page");
    }
}

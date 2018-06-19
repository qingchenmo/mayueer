package com.jlkf.ego.newpage.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.activity.MainActivity;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.widget.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ValidationActivity extends BaseActivity {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_sure)
    Button btnSure;

    @Override
    public void initView() {
        setContentView(R.layout.activity_validation);
        ButterKnife.bind(this);
        tvName.setText("Hi,\n" + MyApplication.getmUserBean().getUName());
        title.setBg(R.color.transparent);
    }

    @Override
    public void iniActivityAnimation() {

    }

    @OnClick(R.id.btn_sure)
    void commit() {
        if (TextUtils.isEmpty(etContent.getText().toString().trim())) {
            showToast("请先输入审核码", 0);
            return;
        }
        ApiManager.checkauditcode(MyApplication.getmUserBean().getUId() + "",
                etContent.getText().toString().trim(), this, new HttpUtils.OnCallBack() {
                    @Override
                    public void success(String response) {
                        showToast("验证成功", 0);
                        MyApplication.mHasComfim = true;
                        mContext.startActivity(new Intent(mContext, MainActivity.class));
                    }

                    @Override
                    public void onError(String msg) {
                        showToast(msg, 0);
                    }
                });
    }
}

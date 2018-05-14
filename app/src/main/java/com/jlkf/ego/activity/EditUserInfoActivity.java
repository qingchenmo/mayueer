package com.jlkf.ego.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.ShardeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zcs
 *         编辑用户签名与名称页面
 */
public class EditUserInfoActivity extends BaseActivity {
    private TextView tv_textnum, tv_title, tv_commit;
    private EditText et_user_info;
    private ImageView iv_title_back;
    private int maxNum;
    private FrameLayout fl_edit;

    @Override
    public void initView() {
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.bind(this);
        tv_textnum = (TextView) findViewById(R.id.tv_textnum);
        et_user_info = (EditText) findViewById(R.id.et_user_info);
        fl_edit = (FrameLayout) findViewById(R.id.fl_edit);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        maxNum = getIntent().getIntExtra("maxNum", 0);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) fl_edit.getLayoutParams();
        if (maxNum == 30) {
            tv_textnum.setVisibility(View.GONE);
            params.weight = 2;
            et_user_info.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
            tv_title.setText(getResources().getString(R.string.user_name));
        } else if (maxNum == 50) {
            et_user_info.addTextChangedListener(new TextWatcher());
            params.weight = 3;
            et_user_info.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
            tv_title.setText(getResources().getString(R.string.user_signa));
        } else {
            params.weight = 2;
            tv_title.setText(getResources().getString(R.string.call)+"号码");
            tv_textnum.setVisibility(View.GONE);
        }
        fl_edit.setLayoutParams(params);
        et_user_info.setOnEditorActionListener(new EditorActionListener());
    }

    @Override
    public void iniActivityAnimation() {

    }

    private int tag = 0;

    @OnClick({R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:               // 修改完成
                if (maxNum == 30) {
                    tag = 0;
                    change("uName");
                } else if (maxNum == 50) {
                    tag = 1;
                    change("uSignature");
                } else if (maxNum == 20) {
                    tag = 2;
                    change("callNum");
                }
                break;
        }
    }

    private void change(String key) {
        JSONObject object = new JSONObject();
        try {
            object.put("uId", getUser().getUId());
            if (key.equals("uName")) {
                object.put("uName", getUser().getUName());
                if (!TextUtils.isEmpty(getUser().getUPhone())) {

                    object.put("uPhone", getUser().getUPhone());
                } else {
                    object.put("uPhone", "");

                }
                object.put(key, et_user_info.getText().toString().trim());
            } else if (key.equals("uSignature")) {
                object.put("uName", getUser().getUName());
                object.put("uSignature", et_user_info.getText().toString().trim());
                if (!TextUtils.isEmpty(getUser().getUPhone())) {

                    object.put("uPhone", getUser().getUPhone());
                } else {
                    object.put("uPhone", "");

                }

            } else if (key.equals("callNum")) {
                object.put("uName", getUser().getUName());
                object.put("uPhone", et_user_info.getText().toString().trim());
            }
            object.put("uMobile", getUser().getUMobile());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.getInstacne(mActivity).post(Urls.updateUser, String.class, object, new HttpUtil.OnCallBack() {
            @Override
            public void success(Object data) {
                if (tag == 0) {
                    getUser().setUName(et_user_info.getText().toString().trim());
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    getUser().setUName(et_user_info.getText().toString().trim());
                    finish();
                } else if (tag == 2) {
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    getUser().setUPhone(et_user_info.getText().toString().trim());
                    finish();
                } else {
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    getUser().setUSignature(et_user_info.getText().toString().trim());
                }

                ShardeUtil.putUser(getUser());
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    class TextWatcher implements android.text.TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            tv_commit.setEnabled(s.length() > 0);
            tv_textnum.setText(s.length() + "/50");
        }
    }

    class EditorActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
//                    Intent intent = new Intent();
//                    intent.putExtra("text", et_user_info.getText().toString().trim());
//                    setResult(Activity.RESULT_OK, intent);
//                    finish();
                    break;
            }
            return false;
        }
    }
}

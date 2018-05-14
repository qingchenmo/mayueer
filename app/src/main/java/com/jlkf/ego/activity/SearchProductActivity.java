package com.jlkf.ego.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.utils.CompanyUtil;
import com.jlkf.ego.widget.XCFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchProductActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.iv_search_delet)
    ImageView mIvSearchDelet;
    @BindView(R.id.rl_search)
    RelativeLayout mRlSearch;
    @BindView(R.id.tv_cencel)
    TextView mTvCencel;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.xc_layout)
    XCFlowLayout mXcLayout;
    String name[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        ButterKnife.bind(this);
        mEtSearch.setHint(getString(R.string.g_qsrspxx));
        mEtSearch.setOnEditorActionListener(this);
        initHistoryView();
    }

    /**
     * 初始 话历史记录控件
     */
    private void initHistoryView() {
        mXcLayout.removeAllViews();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, CompanyUtil.dip2px(32));
        lp.leftMargin = CompanyUtil.dip2px(10);
        lp.rightMargin = CompanyUtil.dip2px(10);
        lp.topMargin = CompanyUtil.dip2px(20);
        SharedPreferences preferences = getSharedPreferences("history", Context.MODE_PRIVATE);
        String history = preferences.getString("his", "");
        if (history.isEmpty()) {
            return;
        }
        name = history.split(",");
        for (int i = 0; i < name.length; i++) {
            final TextView view = new TextView(this);
            view.setText(name[i]);
            view.setGravity(Gravity.CENTER);
            view.setTextSize(16);
            view.setPadding(CompanyUtil.dip2px(35), 0, CompanyUtil.dip2px(35), 0);
            view.setTextColor(getResources().getColor(R.color.text_title));
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.corner_gay_write));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEtSearch.setText(view.getText());
                    saveHistory(mEtSearch.getText().toString().trim());
                    startSearch();
                }
            });
            mXcLayout.addView(view, lp);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (mEtSearch.getText().toString().trim().isEmpty())
                return true;
            /*隐藏软键盘*/
            InputMethodManager imm = (InputMethodManager) v
                    .getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(
                        v.getApplicationWindowToken(), 0);
            }
            saveHistory(mEtSearch.getText().toString().trim());
            startSearch();
            return true;
        }
        return false;
    }

    private void saveHistory(String str) {
        SharedPreferences preferences = getSharedPreferences("history", Context.MODE_PRIVATE);
        String history = preferences.getString("his", "");
        StringBuilder builder = new StringBuilder();
        String strs[] = history.split(",");
        if (strs != null && !history.isEmpty()) {
            for (int i = 0; i < strs.length; i++) {
                if (i == 9) break;
                if (!strs[i].equals(str)) {
                    builder.append(strs[i]).append(",");
                }
            }
        }
        builder.insert(0, str + ",");

        /*builder.append(str).append(",");
        if (!history.isEmpty() && history.contains(str)) {
            builder.append(history.replace(str + ",", ""));
        } else {
            builder.append(history);
        }
        String strs[] = builder.toString().split(",");
        for (int i = 0; i < strs.length; i++) {
            if (i > 10) {
                builder.replace(builder.indexOf(strs[i] + ","), builder.indexOf(strs[i] + ",") + strs[i].length() + 1, "");
            }
        }*/
        preferences.edit().putString("his", builder.toString()).commit();
    }

    private void startSearch() {
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra("productName", mEtSearch.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.iv_search_delet, R.id.iv_delete, R.id.tv_cencel})
    void click(View v) {
        switch (v.getId()) {
            case R.id.iv_search_delet:
                mEtSearch.setText("");
                break;
            case R.id.iv_delete:
                SharedPreferences preferences = getSharedPreferences("history", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                initHistoryView();
                break;
            case R.id.tv_cencel:
                /*隐藏软键盘*/
                InputMethodManager imm = (InputMethodManager) v
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(
                            v.getApplicationWindowToken(), 0);
                }
                finish();
                break;
        }
    }
}

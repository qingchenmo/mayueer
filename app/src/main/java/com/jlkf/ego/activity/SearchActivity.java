package com.jlkf.ego.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.fragment.OrderListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class SearchActivity extends AppCompatActivity implements TextWatcher, TextView.OnEditorActionListener {

    public final static int STATE_NO_SEARCH_DATA = 0;
    public final static int STATE_SEARCH = 1;
    public final static int STATE_NO = 2;
    public int state = STATE_NO;

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.iv_search_delet)
    ImageView ivSearchDelet;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.tv_cencel)
    TextView tvCencel;
    @BindView(R.id.fl_order_seach)
    FrameLayout flOrderSearch;
    @BindView(R.id.rl_search_no_data)
    RelativeLayout rlSearchNoData;
    @BindView(R.id.et_search)
    EditText etSearch;
    private int orderType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_search);
        ButterKnife.bind(this);
        etSearch.addTextChangedListener(this);
        etSearch.setOnEditorActionListener(this);
    }

    @OnClick({R.id.et_search, R.id.iv_search_delet, R.id.tv_cencel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_search:

                break;
            case R.id.iv_search_delet:
                etSearch.setText("");
                break;
            case R.id.tv_cencel:
                if (tvCencel.getText().equals("取消")) {

                    finish();
                } else {
                    addFragment();
                }
                break;
        }
    }

    public void addFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fl_order_seach, OrderListFragment.newInstance(6, etSearch.getText().toString().trim())).commit();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        /*String i = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(i)) {
            tvCencel.setText("取消");
        } else {
            tvCencel.setText("搜索");
        }*/
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!etSearch.getText().toString().trim().isEmpty())
                addFragment();
        }
        return false;
    }
}

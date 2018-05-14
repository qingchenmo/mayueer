package com.jlkf.ego.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.OrderCommitSuccessAdapter;
import com.jlkf.ego.bean.ComfimOrderBean;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author zcs
 *         订单提交成功页
 */
public class OrderCommitSuccessActivity extends com.jlkf.ego.base.BaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private ImageView iv_title_back;
    private List<ComfimOrderBean.DataBean> mList;

    @Override
    protected int getlayoutid() {
        return R.layout.activity_order_commit_success;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_order);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        mList = getIntent().getParcelableArrayListExtra("info");
        if (mList != null && mList.size() > 0)
            mRecyclerView.setAdapter(new OrderCommitSuccessAdapter(this, mList));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("tag", "againBuy");
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onClick(iv_title_back);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

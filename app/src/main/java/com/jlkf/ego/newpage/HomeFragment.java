package com.jlkf.ego.newpage;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.ProductListActivity;
import com.jlkf.ego.activity.ProductQuickSelectActivity;
import com.jlkf.ego.activity.SearchProductActivity;
import com.jlkf.ego.activity.SystemMessageActivity;
import com.jlkf.ego.fragment.BaseFragment;
import com.jlkf.ego.newpage.activity.ClassificationActivity;
import com.jlkf.ego.newpage.adapter.HomeAdapter;
import com.jlkf.ego.newpage.inter.OnItemClickListener;
import com.jlkf.ego.zxing.MipcaActivityCapture;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @time 2018-05-12 新增首页
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_redCircle_system)
    TextView tvRedCircleSystem;
    @BindView(R.id.home_recycler_view)
    RecyclerView homeRecyclerView;
    Unbinder unbinder;

    @Override
    public View getContentView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initView() {
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRecyclerView.setAdapter(new HomeAdapter(getActivity(), new OnItemClickListener<Object>() {
            @Override
            public void itemClickListener(Object o, int position) {
                if (o instanceof String) {
                    if (position == 0) {
                        startActivity(new Intent(getActivity(), ClassificationActivity.class));
                    } else {
                        Intent intent = new Intent(mContext, ProductListActivity.class);
                        intent.putExtra("code", "003");
                        mContext.startActivity(intent);
                    }
                }else if (o instanceof Integer){
                    Intent intent = new Intent(mContext, ProductListActivity.class);
                    intent.putExtra("code", "003");
                    mContext.startActivity(intent);
                }
            }
        }));
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.iv_sao)
    void scan() {
        Intent intent = new Intent(mContext, MipcaActivityCapture.class);
        startActivityForResult(intent, 1);
    }

    public static final int MESSAGE_REQUEST = 999;

    @OnClick(R.id.rl_system)
    void systemMsg() {
        Intent intent = new Intent(mContext, SystemMessageActivity.class);
        startActivityForResult(intent, MESSAGE_REQUEST);
    }


    @OnClick(R.id.rl_search_brand)
    void search() {
        Intent intent = new Intent(getActivity(), SearchProductActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String str = data.getStringExtra("result");
            Intent intent = new Intent(getActivity(), ProductQuickSelectActivity.class);
            intent.putExtra("productName", str);
            intent.putExtra("codeBars", str);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MESSAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            initData();
        }
    }
}

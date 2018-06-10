package com.jlkf.ego.newpage;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.MyConnectionActivity;
import com.jlkf.ego.activity.ProductInfoActivity;
import com.jlkf.ego.activity.ProductListActivity;
import com.jlkf.ego.activity.ProductQuickSelectActivity;
import com.jlkf.ego.activity.SearchProductActivity;
import com.jlkf.ego.activity.SystemMessageActivity;
import com.jlkf.ego.fragment.BaseFragment;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.newpage.activity.ClassificationActivity;
import com.jlkf.ego.newpage.activity.WebActivity;
import com.jlkf.ego.newpage.adapter.HomeAdapter;
import com.jlkf.ego.newpage.bean.BannerBean;
import com.jlkf.ego.newpage.bean.BrandBean;
import com.jlkf.ego.newpage.bean.GroupBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.zxing.MipcaActivityCapture;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

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
    private List<BannerBean> mBannerList = new ArrayList<>();
    private List<BrandBean> mBrandList = new ArrayList<>();
    private List<GroupBean> mGroupList = new ArrayList<>();

    @Override
    public View getContentView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initView() {
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRecyclerView.setAdapter(new HomeAdapter(getActivity(), mBannerList, mBrandList, mGroupList, new OnItemClickListener<Object>() {
            @Override
            public void itemClickListener(Object o, int position) {
                if (o instanceof BrandBean) {
                    BrandBean bean = (BrandBean) o;
                    Intent intent = new Intent(getActivity(), ClassificationActivity.class);
                    intent.putExtra("brandId", bean.getPp_id());
                    startActivity(intent);
                } else if (o instanceof GroupBean) {
                    GroupBean bean = (GroupBean) o;
                    Intent intent = new Intent(getActivity(), ClassificationActivity.class);
                    intent.putExtra("group_id", bean.getItemGroup_id());
                    startActivity(intent);
                } else if (o instanceof Banner) {
                    if (mBannerList.get(position).getType() == 2) {
                        Intent intent = new Intent(mContext, ProductInfoActivity.class);
                        intent.putExtra("itemCode", mBannerList.get(position).getUrl());
                        startActivity(intent);
                    } else if (mBannerList.get(position).getType() == 3) {
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra("url", mBannerList.get(position).getUrl());
                        startActivity(intent);
                    }
                }
            }
        }));
    }

    @Override
    public void initData() {
        super.initData();
        getBannerList();
        getBrandList();
        getGroupList();
    }

    private void getGroupList() {
        ApiManager.getGroupList("", getActivity(), new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                List<GroupBean> list = JSON.parseArray(response, GroupBean.class);
                mGroupList.clear();
                mGroupList.addAll(list);
                homeRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getBrandList() {
        ApiManager.getBrandList(getActivity(), new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                List<BrandBean> list = JSON.parseArray(response, BrandBean.class);
                mBrandList.clear();
                mBrandList.addAll(list);
                homeRecyclerView.getAdapter().notifyItemChanged(1);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void getBannerList() {
        ApiManager.getBannerList(getActivity(), new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                List<BannerBean> list = JSON.parseArray(response, BannerBean.class);
                mBannerList.clear();
                mBannerList.addAll(list);
                homeRecyclerView.getAdapter().notifyItemChanged(0);
            }

            @Override
            public void onError(String msg) {

            }
        });
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

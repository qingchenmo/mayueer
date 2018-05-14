package com.jlkf.ego.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.ProductListActivity;
import com.jlkf.ego.activity.ProductQuickSelectActivity;
import com.jlkf.ego.activity.SearchProductActivity;
import com.jlkf.ego.activity.SystemMessageActivity;
import com.jlkf.ego.adapter.BaseAdapter;
import com.jlkf.ego.adapter.PpAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.BrandListBean;
import com.jlkf.ego.bean.GoodTypeBean;
import com.jlkf.ego.bean.YindaoBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.widget.FullyGridLayoutManager;
import com.jlkf.ego.zxing.MipcaActivityCapture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/7.
 */
public class MainFragment2 extends BaseFragment {
    @BindView(R.id.rv_pp)
    RecyclerView rv_pp;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.tv_redCircle_system)
    TextView tvRedCircleSystem;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;


    @Override
    public View getContentView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fmt_main_2, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }


    @Override
    public void initView() {


        ll_content.setFocusableInTouchMode(true);
        ll_content.requestFocus();

        rv_pp.setFocusableInTouchMode(false); //设置不需要焦点
        rv_pp.requestFocus(); //设置焦点不需要
        rv_pp.setNestedScrollingEnabled(false);
        rv_pp.setLayoutManager(new LinearLayoutManager(mContext));

        rv_list.setFocusableInTouchMode(false); //设置不需要焦点
        rv_list.requestFocus(); //设置焦点不需要
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setLayoutManager(new FullyGridLayoutManager(mContext, 2));


    }

    @Override
    public void initData() {

        Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");

        HttpUtil.getInstacne(mActivity).get(Urls.allRead, Integer.class, map, new HttpUtil.OnCallBack<Integer>() {
            @Override
            public void success(Integer data) {

                if (data == 0) {
                    tvRedCircleSystem.setVisibility(View.GONE);
                } else {
                    tvRedCircleSystem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String msg, int code) {

            }
        });

        final Map<String, String> ma2 = new HashMap<>();
        ma2.put("pType", "3");
        HttpUtil.getInstacne(getActivity()).get(Urls.getPic, String.class, ma2, new HttpUtil.OnCallBack<String>() {
            @Override
            public void success(String data) {
                if (!TextUtils.isEmpty(data)) {
                    List<YindaoBean> yindaoBeen = YindaoBean.arrayYindaoBeanFromData(data);
                    Glide.with(mActivity).load(yindaoBeen.get(0).getPLogo()).error(R.mipmap.img_qidongye).into(iv_icon);
                }

            }

            @Override
            public void onError(String msg, int code) {
                ToastUti.show(msg);

            }
        });

        Map<String, String> map1 = new HashMap<>();
        map1.put("area", MyApplication.getmUserBean().getArea());
        HttpUtil.getInstacne(getActivity()).get(Urls.recommendListOne, Object.class, map1, new HttpUtil.OnCallBack<Object>() {

            private PpAdapter mAdapter;
            public List<BrandListBean> mBrandListBeen;

            @Override
            public void success(Object data) {
                mBrandListBeen = BrandListBean.arrayBrandListBeanFromData(data.toString());
                Log.e("------------------", mBrandListBeen.size() + "");
                mAdapter = new PpAdapter(R.layout.adataper_pp, mBrandListBeen);
                rv_pp.setAdapter(mAdapter);

                mAdapter.setOnClickListener(new BaseAdapter.OnClickListener() {
                    @Override
                    public void onClick(int pos) {
                        Intent intent = new Intent(mContext, ProductListActivity.class);
                        intent.putExtra("code", mBrandListBeen.get(pos).getCode());
                        mContext.startActivity(intent);
                    }
                });
                Map<String, String> map = new HashMap<>();
                map.put("area", MyApplication.getmUserBean().getArea());
                HttpUtil.getInstacne(mActivity).get(Urls.itemgetList, String.class, map, new HttpUtil.OnCallBack<String>() {
                    private ItemAdapter mAdapter;
                    public List<GoodTypeBean> mGoodTypeBeen;

                    @Override
                    public void success(String data) {
                        mGoodTypeBeen = GoodTypeBean.arrayGoodTypeBeanFromData(data);

                        mAdapter = new ItemAdapter(R.layout.adatper_item, mGoodTypeBeen);
                        rv_list.setAdapter(mAdapter);

                        mAdapter.setOnClickListener(new BaseAdapter.OnClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent intent = new Intent(mContext, ProductListActivity.class);
                                intent.putExtra("itmsGrpCod", mGoodTypeBeen.get(pos).getItmsgrpcod());
                                mContext.startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onError(String msg, int code) {

                    }
                });
            }

            @Override
            public void onError(String msg, int code) {
            }
        });
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

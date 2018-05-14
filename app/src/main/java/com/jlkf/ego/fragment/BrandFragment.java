package com.jlkf.ego.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.BrandListAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.BrandBean;
import com.jlkf.ego.bean.BrandListBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.CharacterParser;
import com.jlkf.ego.widget.SideBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrandFragment extends BaseFragment {

    private String mParam1;
    private String mParam2;

    private RecyclerView mRecycleView;
    private LinearLayout lin_navigation;
    private WindowManager mWindowManager;
    private TextView tv;
    private SideBar sb;
    private List<BrandListBean> mBrandListBeen;

    public BrandFragment() {
    }

    public static BrandFragment newInstance(String param1, String param2) {
        BrandFragment fragment = new BrandFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {

        Map<String, String> map1 = new HashMap<>();
        map1.put("area", MyApplication.getmUserBean().getArea());
        HttpUtil.getInstacne(getActivity()).get(Urls.recommendBrand, Object.class, map1, new HttpUtil.OnCallBack<Object>() {

            @Override
            public void success(Object data) {
                mBrandListBeen = BrandListBean.arrayBrandListBeanFromData(data.toString());
                MyApplication.mAppApplication.setBrandListBeen(mBrandListBeen);


                Map<String, String> map = new HashMap<>();
                map.put("area", MyApplication.getmUserBean().getArea());
                HttpUtil.getInstacne(getActivity()).get(Urls.getBrand, String.class, map, new HttpUtil.OnCallBack<String>() {

                    @Override
                    public void success(String data) {
                        List<BrandListBean> brandListBeen = BrandListBean.arrayBrandListBeanFromData(data);
                        CharacterParser instance = CharacterParser.getInstance();

                        for (int i=0;i<brandListBeen.size();i++){
                            String name = brandListBeen.get(i).getName();
                            String pinyin = instance.getSelling(name);
                            String sortString = pinyin.substring(0, 1).toUpperCase();
                            if (sortString.matches("[A-Z]")) {
//                                brandListBeen.get(i).setSortLetters(sortString.toUpperCase());
                            } else {
//                                brandListBeen.get(i).setSortLetters("#");
                            }
                        }

                        mRecycleView.setAdapter(new BrandListAdapter(mContext, brandListBeen, mBrandListBeen, 0));
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

    @Override
    public View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_brand, null, false);
    }

    @Override
    public void initView() {
        mRecycleView = (RecyclerView) rootView.findViewById(R.id.rv_brand);
        lin_navigation = (LinearLayout) rootView.findViewById(R.id.lin_navigation);
        sb = (SideBar) rootView.findViewById(R.id.sb);
        tv = (TextView) rootView.findViewById(R.id.tv);
        sb.setRecycle(mRecycleView);
        sb.setTextView(tv);

//        initSideBar();
        String[] strs = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (int i = 0; i < 26; i++) {
            TextView textView = new TextView(mContext);
            textView.setText(strs[i]);
            textView.setTextSize(12);
            textView.setTextColor(Color.parseColor("#666666"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            lin_navigation.addView(textView, params);
        }

        List<BrandBean> list = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            BrandBean bean = new BrandBean();
            if (i == 0) {
                bean.setList(new ArrayList<BrandBean>());
                for (int j = 0; j < 6; j++) {
                    BrandBean bean1 = new BrandBean();
                    bean1.setName("ABDC");
                    bean1.setId(1);
                    bean.getList().add(bean1);
                }
                list.add(bean);
            } else {
                bean.setFirstWord(strs[i - 1]);
                list.add(bean);
                for (int j = 0; j < 5; j++) {
                    BrandBean bean1 = new BrandBean();
                    bean1.setName(strs[i - 1] + "ABDC");
                    list.add(bean1);
                }
            }
        }
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));

    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
    }
}
package com.jlkf.ego.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.OrderListAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.OrderBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 订单列表
 */
public class OrderListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.iv_kong)
    ImageView mIvKong;
    @BindView(R.id.tv_kong)
    TextView mTvKong;
    Unbinder unbinder;
    private String mParam1;
    private String mParam2;
    private int orderType;
    public static final int ALLORDEr = 0;//全部订单
    public static final int WAITACCAPT = 1;//待受理
    public static final int WAITSEND = 2;//待发货
    public static final int ONWAY = 3;//配送中
    public static final int COPLETE = 4;//已完成
    public static final int CANCEL = 5;//已取消
    private RecyclerView mRvOrder;
    protected boolean isVisible;
    private boolean isPrepared;
    private List<OrderBean.DataBean> mList;
    private RelativeLayout rl_search_no_data;

    public OrderListFragment() {
        // Required empty public constructor
    }

    public static OrderListFragment newInstance(int orderType) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, orderType);
        fragment.setArguments(args);

        return fragment;
    }

    public static OrderListFragment newInstance(int orderType, String str) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, orderType);
        args.putString(ARG_PARAM2, str);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            orderType = getArguments().getInt(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_list, container, false);
        mRvOrder = (RecyclerView) v.findViewById(R.id.rv_order);
        rl_search_no_data = (RelativeLayout) v.findViewById(R.id.rl_search_no_data);
        isPrepared = true;
        mList = new ArrayList<>();
        mRvOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOrder.setAdapter(new OrderListAdapter(getContext(), mList, orderType, this));
        if (isPrepared && isVisible) {
            lazyLoad();
        }
        if (getArguments().getString(ARG_PARAM2) != null && !getArguments().getString(ARG_PARAM2).isEmpty()) {
            lazyLoad();
        }
        unbinder = ButterKnife.bind(this, v);
        if (orderType == 6) {
            mIvKong.setImageResource(R.drawable.no_search);
            mTvKong.setText(getString(R.string.messdxgjg));
        }
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    public void lazyLoad() {

        if ((!isPrepared || !isVisible) && !(getArguments().getString(ARG_PARAM2) != null && !getArguments().getString(ARG_PARAM2).isEmpty())) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        if (getArguments().getString(ARG_PARAM2) != null && !getArguments().getString(ARG_PARAM2).isEmpty())
            map.put("str", getArguments().getString(ARG_PARAM2));
        if (orderType != 0) {
            map.put("DocStatus", orderType - 1 + "");
        }
//        map.put("pageNo", 0 + "");
        HttpUtil.getInstacne(getActivity()).get2(Urls.orderList, OrderBean.class, map, new HttpUtil.OnCallBack<OrderBean>() {
            @Override
            public void success(OrderBean data) {
                if (data.getData().size() < 1) {
                    rl_search_no_data.setVisibility(View.VISIBLE);
                    mRvOrder.setVisibility(View.GONE);
                }
                mList.clear();
                mList.addAll(data.getData());
                mRvOrder.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, int code) {
                if (getArguments().getString(ARG_PARAM2) != null && !getArguments().getString(ARG_PARAM2).isEmpty()) {
                    rl_search_no_data.setVisibility(View.VISIBLE);
                    mRvOrder.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

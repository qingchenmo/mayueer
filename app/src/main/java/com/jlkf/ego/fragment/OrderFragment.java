package com.jlkf.ego.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.OrderCommitSuccessActivity;
import com.jlkf.ego.adapter.OrderItemAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.AdressBean;
import com.jlkf.ego.bean.ComfimOrderBean;
import com.jlkf.ego.bean.ConfimOrderBean;
import com.jlkf.ego.bean.PayType;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.utils.ToastUti;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/18 0018.
 * <p>
 * 确认订单
 */

public class OrderFragment extends BaseFragment implements OrderItemAdapter.OnClickCommitListener {
    @BindView(R.id.order_recycler)
    RecyclerView orderRecycler;
    @BindView(R.id.iv_yindao)
    ImageView iv_yindao;
    private ConfimOrderBean mBean;
    private List<PayType> mPayTypes;
    private OrderAdapter mAdapter;
    private OrderItemAdapter mOrderItemAdapter;

    @Override
    public View getContentView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fmt_order, null);

        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void initView() {
        orderRecycler.setLayoutManager(new LinearLayoutManager(mContext));

//        orderRecycler.setAdapter(new OrderAdapter(mContext));

        if (ShardeUtil.getInt("order") == 1) {
            iv_yindao.setVisibility(View.GONE);
        } else {
            iv_yindao.setVisibility(View.VISIBLE);
        }
        iv_yindao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShardeUtil.putInt("order", 1);
                iv_yindao.setVisibility(View.GONE);
            }
        });
        mBean = getArguments().getParcelable("orderInfo");
    }

    private AdressBean mAdressBean;

    @Override
    public void initData() {

        HttpUtil.getInstacne(mActivity).get(Urls.payType, String.class, new HttpUtil.OnCallBack<String>() {
            @Override
            public void success(String data) {


                mPayTypes = PayType.arrayPayTypeFromData(data);


                final Map<String, String> map = new HashMap<>();
                map.put("uId", MyApplication.getmUserBean().getUId() + "");
                HttpUtil.getInstacne(mActivity).get(Urls.addrlist, String.class, map, new HttpUtil.OnCallBack<String>() {

                    @Override
                    public void success(String data) {
                        List<AdressBean> adressBeen = new Gson().fromJson(data, new TypeToken<List<AdressBean>>() {
                        }.getType());

                        for (int i = 0; i < adressBeen.size(); i++) {
                            if (adressBeen.get(i).getUpDefault() == 1) {
                                mAdressBean = adressBeen.get(i);
                                for (int j = 0; j < mBean.getData().size(); j++) {
                                    mBean.getData().get(j).setAdressBean(mAdressBean);
                                }
                            }
                        }
                        if (orderRecycler != null) {

                            mAdapter = new OrderAdapter(mPayTypes, mAdressBean);
                            orderRecycler.setAdapter(mAdapter);
                        }
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

    public void setAdressBean(AdressBean adressBean) {
        mAdapter.setAdressBean(adressBean);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void commitListener() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < mOrderItemAdapter.mData.size(); i++) {
            ConfimOrderBean.DataBean bean = mOrderItemAdapter.mData.get(i);
            JSONObject object = new JSONObject();
            object.put("uFkfs", bean.getFkfs() + "");
            object.put("uZk1", bean.getuZk1());
            object.put("uZk2", bean.getuZk2());
            object.put("uZk3", bean.getuZk3());
            object.put("uZk4", bean.getuZk4());
            object.put("docdisc", bean.getDocdisc());
            object.put("totalexpns", bean.getTotalexpns());
            object.put("comments", bean.getLy());
            object.put("shipaddress", bean.getAdressBean().getUaId());
            object.put("doctotal", bean.getSpainTotal());
            object.put("uId", MyApplication.getmUserBean().getUId());
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < bean.getListData().size(); j++) {
                builder.append(bean.getListData().get(j).getSId()).append(",");
            }
            object.put("detailId", builder.substring(0, builder.length() - 1));
            array.add(object);
        }

        HttpUtil.getInstacne(getActivity()).post2(Urls.confirmOrder, ComfimOrderBean.class, array.toString(), new HttpUtil.OnCallBack<ComfimOrderBean>() {
            @Override
            public void success(ComfimOrderBean data) {
//                ToastUti.show(mData.getMsg());
                Intent intent = new Intent(getActivity(), OrderCommitSuccessActivity.class);
                intent.putParcelableArrayListExtra("info", data.getData());
                startActivity(intent);
            }

            @Override
            public void onError(String msg, int code) {
                ToastUti.show(msg);
            }
        });

    }

    class OrderAdapter extends RecyclerView.Adapter {

        List<PayType> mPayTypes;

        public void setTag(int tag) {
            this.tag = tag;
        }

        private int tag;

        public void setAdressBean(AdressBean adressBean) {
            this.adressBean = adressBean;
        }

        public AdressBean getAdressBean() {
            return adressBean;
        }

        AdressBean adressBean;

        public OrderAdapter(List<PayType> payTypes, AdressBean adressBean) {
            this.mPayTypes = payTypes;
            this.adressBean = adressBean;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.holder_order, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mRv.setLayoutManager(new LinearLayoutManager(mContext));
            viewHolder.mRv.setAdapter(mOrderItemAdapter = new OrderItemAdapter(mContext, position, mBean, mPayTypes, adressBean, OrderFragment.this));
        }

        @Override
        public int getItemCount() {
            return mBean.getData().size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rv)
        RecyclerView mRv;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Subscribe
    public void refreshData(ConfimOrderBean bean) {
        mBean = bean;
        mAdapter.notifyDataSetChanged();
    }
}

package com.jlkf.ego.newpage.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.activity.OrderCommitSuccessActivity;
import com.jlkf.ego.adapter.OrderItemAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.AdressBean;
import com.jlkf.ego.bean.PayType;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.newpage.adapter.NewOrderItemAdapter;
import com.jlkf.ego.newpage.bean.NewConfimOrderBean;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.GiftAddUtils;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.widget.BaseToolbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jlkf.ego.adapter.OrderItemAdapter.RESULT_ORDER;

public class NewOrderActivity extends BaseActivity implements OrderItemAdapter.OnClickCommitListener {


    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.order_recycler)
    RecyclerView orderRecycler;
    @BindView(R.id.iv_yindao)
    ImageView ivYindao;
    private NewOrderItemAdapter mOrderItemAdapter;
    private NewConfimOrderBean mOrderBean;
    private List<PayType> mPayTypes;
    private AdressBean mAdressBean;

    public void initData() {

        HttpUtil.getInstacne(this).get(Urls.payType, String.class, new HttpUtil.OnCallBack<String>() {
            @Override
            public void success(String data) {


                mPayTypes = PayType.arrayPayTypeFromData(data);


                final Map<String, String> map = new HashMap<>();
                map.put("uId", MyApplication.getmUserBean().getUId() + "");
                HttpUtil.getInstacne(NewOrderActivity.this).get(Urls.addrlist, String.class, map, new HttpUtil.OnCallBack<String>() {

                    @Override
                    public void success(String data) {
                        List<AdressBean> adressBeen = new Gson().fromJson(data, new TypeToken<List<AdressBean>>() {
                        }.getType());

                        for (int i = 0; i < adressBeen.size(); i++) {
                            if (adressBeen.get(i).getUpDefault() == 1) {
                                mAdressBean = adressBeen.get(i);
                                mOrderBean.setmAdressBean(mAdressBean);
                            }
                        }
                        if (orderRecycler != null) {
                            orderRecycler.setAdapter(mOrderItemAdapter = new NewOrderItemAdapter(NewOrderActivity.this, mOrderBean, mPayTypes, NewOrderActivity.this));
                            LinearLayoutManager mLayoutManager =
                                    (LinearLayoutManager) orderRecycler.getLayoutManager();
                            mLayoutManager.scrollToPositionWithOffset(0, 0);
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

    @Override
    public void initView() {
        setContentView(R.layout.activity_new_order);
        ButterKnife.bind(this);
        initToolbar();
        mOrderBean = getIntent().getExtras().getParcelable("orderInfo");
        orderRecycler.setLayoutManager(new LinearLayoutManager(this));
        if (ShardeUtil.getInt("order") == 1) {
            ivYindao.setVisibility(View.GONE);
        } else {
            ivYindao.setVisibility(View.VISIBLE);
        }
        ivYindao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShardeUtil.putInt("order", 1);
                ivYindao.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void iniActivityAnimation() {

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.with(this);
        toolbar.setTitle(getResources().getString(R.string.order_title));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_ORDER && resultCode == RESULT_OK) {
            AdressBean adressBean = (AdressBean) data.getSerializableExtra("order");
            mOrderBean.setmAdressBean(adressBean);
            mOrderItemAdapter.notifyDataSetChanged();
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            int size = mOrderBean.getPay().size();
            for (int i = 0; i < size; i++) {
                NewConfimOrderBean.PayBean bean = mOrderBean.getPay().get(i);
                double giftMoney = GiftAddUtils.getInstance().getGiftPrice();
                if (bean.getSettlement_zp() < giftMoney) {
                    bean.setBeyond_zp(giftMoney - bean.getSettlement_zp());
                }
            }
            mOrderItemAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void commitListener() {
        setLoading(true);
        Map<String, String> map = new HashMap<>();
        map.put("uId", String.valueOf(MyApplication.getmUserBean().getUId()));
        List<ShopCarGoodsBean> list = GiftAddUtils.getInstance().getGoodsBeen();
        int size = list.size();
        StringBuilder zIdBuilder = new StringBuilder();
        StringBuilder zQuantityBuilder = new StringBuilder();
        StringBuilder zPriceBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            ShopCarGoodsBean bean = list.get(i);
            if (size == i + 1) {
                zIdBuilder.append(bean.getGoodsCode());
                zQuantityBuilder.append(bean.getNum());
                zPriceBuilder.append(bean.getPrice());
            } else {
                zIdBuilder.append(bean.getGoodsCode()).append(",");
                zQuantityBuilder.append(bean.getNum()).append(",");
                zPriceBuilder.append(bean.getPrice()).append(",");
            }
        }
        if (mOrderBean.getIs_gift() == 1) {
            map.put("zId", zIdBuilder.toString());
            map.put("QuantityList", zQuantityBuilder.toString());
            map.put("PriceList", zPriceBuilder.toString());
        }
        map.put("sId", getIntent().getExtras().getString("sId"));
        map.put("ua_id", mOrderBean.getmAdressBean().getUaId());
        map.put("Totaltax", String.valueOf(mOrderItemAdapter.getSpainListBean().getTotaltax()));
        map.put("TotalExpns", String.valueOf(mOrderBean.getTotalexpns()));
        map.put("U_FKFS", mOrderBean.getFkfs() + "");
        map.put("U_ZK1", String.valueOf(mOrderBean.getuZk1()));
        map.put("U_ZK2", String.valueOf(mOrderBean.getuZk2()));
        map.put("U_ZK3", String.valueOf(0));
        map.put("U_ZK4", String.valueOf(0));
        map.put("DocDisc", mOrderBean.getDocdisc());
        map.put("Comments", mOrderBean.getLy());
        map.put("settlement_zk", String.valueOf(mOrderItemAdapter.getSpainListBean().getSettlement_zk()));
        map.put("DocTotal", mOrderBean.getSpainTotal());
        map.put("pay_type", mOrderItemAdapter.getSpainListBean().getPay_id());
        ApiManager.saveorder(map, this, new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                setLoading(false);
                Intent intent = new Intent(NewOrderActivity.this, OrderCommitSuccessActivity.class);
                intent.putParcelableArrayListExtra("info", null);
                startActivity(intent);
            }

            @Override
            public void onError(String msg) {
                setLoading(false);
                ToastUti.show(msg);
            }
        });
    }
}

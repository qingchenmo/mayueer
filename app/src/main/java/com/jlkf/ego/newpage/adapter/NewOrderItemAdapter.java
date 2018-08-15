package com.jlkf.ego.newpage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.AdressActivity;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.activity.OrderOrIssusListActivity;
import com.jlkf.ego.adapter.OrderItemAdapter;
import com.jlkf.ego.adapter.OrderOrIssusAdapter;
import com.jlkf.ego.adapter.SpinnerAdapter;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.AdressBean;
import com.jlkf.ego.bean.ConfimOrderBean;
import com.jlkf.ego.bean.OrderBean;
import com.jlkf.ego.bean.PayType;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.newpage.activity.EventProductActivity;
import com.jlkf.ego.newpage.bean.NewConfimOrderBean;
import com.jlkf.ego.newpage.bean.NewYunFeiBean;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.GiftAddUtils;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.utils.NumberUtil;
import com.jlkf.ego.widget.GoodsInfoLayout;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class NewOrderItemAdapter extends RecyclerView.Adapter {

    public final static int TYPE_HEAD = 0;
    public final static int TYPE_GOODS_RECEIPT = 1;
    public final static int TYPE_MORE = 2;
    public final static int RESULT_ORDER = 555;

    private NewOrderItemAdapter.MoreHolder mMoreHolder;
    private Context context;
    private NewConfimOrderBean mOrderBean;
    private List<OrderBean.DataBean.OorderDetailBean> mDetailBeen; // 品牌部分

    private List<PayType> payTypes;// 支付方式
    private int mUPayId;
    private NewConfimOrderBean.PayBean mSpainListBean;
    private OrderItemAdapter.OnClickCommitListener mListener;

    public NewOrderItemAdapter(Context context, NewConfimOrderBean bean, List<PayType> payTypes,
                               OrderItemAdapter.OnClickCommitListener listener) {
        this.context = context;
        mOrderBean = bean;
        mDetailBeen = new ArrayList<>();
        this.payTypes = payTypes;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_HEAD) holder = new HeadHolder(LayoutInflater.from(context).inflate(
                R.layout.holder_head, parent, false));
        else if (viewType == TYPE_GOODS_RECEIPT)
            holder = new AdressHolder(LayoutInflater.from(context).inflate(
                    R.layout.holder_goods_receipt, parent, false));
        else holder = new MoreHolder(LayoutInflater.from(context).inflate(
                    R.layout.holder_more, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEAD:
                setHeadView(holder);
                break;
            case TYPE_GOODS_RECEIPT:
                setGoodsReceipt(holder);
                break;
            case TYPE_MORE:
                setPriceView(holder);
                break;
        }
    }

    /**
     * 设置顶部部分View
     *
     * @param holder
     */
    private void setHeadView(RecyclerView.ViewHolder holder) {
        HeadHolder headHolder = (HeadHolder) holder;
        headHolder.tv_pp.setText(mOrderBean.getBrandData().getName());
        Glide.with(context).load(mOrderBean.getBrandData().getPp_minlogo()).error(R.drawable.icon_img_load).into(headHolder.iv_pp);
        int count = mOrderBean.getTotal_num();
        for (int i = 0; i < mOrderBean.getListData().size(); i++) {
            count = count + mOrderBean.getListData().get(i).getQuantity();
        }
        headHolder.gl.setNum(String.valueOf(mOrderBean.getTotal_num()));
        headHolder.gl.setPrice(NumberUtil.fomater(mOrderBean.getTotal_sum()) + "");
        headHolder.gl.setImg(mOrderBean.getListData());
        headHolder.gl.setBackGrand(R.color.white);
        headHolder.gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProductList();
            }
        });
    }

    /**
     * 查看要结算商品列表信息
     */
    private void setProductList() {
        if (mDetailBeen.size() == 0) {
            for (ConfimOrderBean.DataBean.ListDataBean bean : mOrderBean.getListData()) {
                OrderBean.DataBean.OorderDetailBean oorderDetailBean = new OrderBean.DataBean.OorderDetailBean();
                oorderDetailBean.setId(bean.getSId());
                oorderDetailBean.setLogo(bean.getLogo());
                oorderDetailBean.setQuantity(bean.getQuantity());
                oorderDetailBean.setCodebars(bean.getCodebars());
                oorderDetailBean.setName(bean.getName());
                oorderDetailBean.setItemcode(bean.getItemcode());
                oorderDetailBean.setPrice(bean.getPrice() + "");
                mDetailBeen.add(oorderDetailBean);
            }
        }
        Intent intent = new Intent(context, OrderOrIssusListActivity.class);
        intent.putExtra("type", OrderOrIssusAdapter.ORDERLIST);
        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) mDetailBeen);
        context.startActivity(intent);
    }

    /**
     * 设置商品收获地址部分View
     *
     * @param holder
     */
    private void setGoodsReceipt(RecyclerView.ViewHolder holder) {
        final AdressBean adressBean = mOrderBean.getmAdressBean();
        AdressHolder adressHolder = (AdressHolder) holder;
        if (adressBean == null) {
            adressHolder.ll_adress.setVisibility(View.GONE);
            adressHolder.tv_right.setVisibility(View.VISIBLE);
        } else {
            adressHolder.ll_adress.setVisibility(View.VISIBLE);
            adressHolder.tv_right.setVisibility(View.GONE);
            adressHolder.tv_name.setText(context.getResources().getString(R.string.shr) + adressBean.getUaName() + "." + adressBean.getUaSurname());
            adressHolder.tv_phone.setText(context.getResources().getString(R.string.s_shouji) + adressBean.getUaContactphone());
            adressHolder.tv_zj.setText(context.getResources().getString(R.string.o_zuoji) + adressBean.getUaLandline());
            adressHolder.tv_adress.setText(adressBean.getUaCountriesName() + adressBean.getUaProvincialName() + adressBean.getUaDelivery());
        }
    }

    /**
     * 设置商品折扣部分View
     *
     * @param holder
     */
    private void setPriceView(final RecyclerView.ViewHolder holder) {
        final AdressBean adressBean = mOrderBean.getmAdressBean();
        mMoreHolder = (MoreHolder) holder;
        priceViewDataBind();
        spannerListener(adressBean);


    }

    /**
     * 折扣部分View数据绑定
     */
    private void priceViewDataBind() {
        mMoreHolder.mSpinner.setAdapter(new SpinnerAdapter(context, payTypes));
        mMoreHolder.mSpinner.setSelection(mOrderBean.getType());
        mMoreHolder.tv_khzk.setText(NumberUtil.fomaterToOne(mOrderBean.getuZk1()) + "%");
        mMoreHolder.tv_zfzk.setText(NumberUtil.fomaterToOne(mOrderBean.getuZk2()) + "%");
        if (mOrderBean.getuZk3() != 0.0) {
            mMoreHolder.tv_jezk.setText(NumberUtil.fomaterToOne(mOrderBean.getuZk3()) + "%");
        }
        mMoreHolder.tv_yf.setText(context.getString(R.string.money) + mOrderBean.getTotalexpns());
        mMoreHolder.tv_msg.setText(context.getString(R.string.money) + mOrderBean.getMsg());
        mMoreHolder.tv_xj.setText(context.getString(R.string.money) + NumberUtil.fomater(mOrderBean.getFinallyTotal()) + "(已含IVA)");
        mMoreHolder.et_ly.setText(mOrderBean.getLy());

        mMoreHolder.itemView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.commitListener();
            }
        });
        mMoreHolder.mSwitchButton.setChecked(mOrderBean.getIs_gift() == 1);
    }

    /**
     * Spanner 选中监听
     *
     * @param adressBean
     */
    private void spannerListener(final AdressBean adressBean) {
        mMoreHolder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PayType payType = payTypes.get(position);
                mUPayId = payType.getUPayId();
                mOrderBean.setType(position);
                mOrderBean.setFkfs(mUPayId + "");

                for (int i = 0; i < mOrderBean.getPay().size(); i++) {
                    if (mOrderBean.getPay().get(i).getPay_id().equals(mUPayId + "")) {
                        mSpainListBean = mOrderBean.getPay().get(i);
                        mMoreHolder.tv_khzk.setText(NumberUtil.fomaterToOne(mSpainListBean.getCustomer_discount() * 100) + "%");
                        mMoreHolder.tv_zfzk.setText(NumberUtil.fomaterToOne(mSpainListBean.getPay_discount() * 100) + "%");
                        mOrderBean.setuZk1(mSpainListBean.getCustomer_discount());
                        mOrderBean.setuZk2(mSpainListBean.getPay_discount());
                        mOrderBean.setuZk3(mSpainListBean.getCustomer_discount());
                        mOrderBean.setuZk4(mSpainListBean.getPay_discount());

                        if (mSpainListBean.getCustomer_discount() == 0.0) {
                            mMoreHolder.mRlJezk.setVisibility(View.GONE);
                        } else {
                            mMoreHolder.mRlJezk.setVisibility(View.VISIBLE);
                            mMoreHolder.tv_jezk.setText(NumberUtil.fomaterToOne(mSpainListBean.getCustomer_discount() * 100) + "%");
                        }
                        if (adressBean != null) {
                            getYunFei(adressBean, String.valueOf(mSpainListBean.getSettlement_zp()));
                        }
                        mMoreHolder.mTvZengPinPrice.setText(context.getText(R.string.money) + String.valueOf(mSpainListBean.getSettlement_zp()));
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public NewConfimOrderBean.PayBean getSpainListBean() {
        return mSpainListBean;
    }

    public void getYunFei(final AdressBean adressBean, final String settlement_zp) {
        ((BaseActivity) context).setLoading(true);
        Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
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
        map.put("settlement_zp", settlement_zp);
        map.put("total_sum", String.valueOf(mOrderBean.getTotal_sum()));
        if (adressBean != null)
            map.put("ua_id", adressBean.getUaId());
        if (mOrderBean.getIs_gift() == 1) {
            map.put("zId", zIdBuilder.toString());
            map.put("QuantityList", zQuantityBuilder.toString());
            map.put("PriceList", zPriceBuilder.toString());
        }
        ApiManager.giftsettlement(map, context, new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                ((BaseActivity) context).setLoading(false);
                NewYunFeiBean bean = JSON.parseObject(response, NewYunFeiBean.class);
                String zengpinPrice = "";
                try {
                    if (mOrderBean.getIs_gift() == 1) {
                        double price = GiftAddUtils.getInstance().getGiftPrice();
                        if (Double.valueOf(settlement_zp) < price) {
                            zengpinPrice = "\n包含赠品：" + context.getString(R.string.money) + settlement_zp;
                        } else {
                            zengpinPrice = "\n包含赠品：" + context.getString(R.string.money) + price;
                        }
                    }
                } catch (Exception e) {

                }
                mMoreHolder.tv_total.setText(context.getResources().getString(R.string.money) + NumberUtil.fomater(bean.getSettlement_total()) +context.getString(R.string.iva) + zengpinPrice);
                mMoreHolder.tv_yf.setText(context.getString(R.string.money) + NumberUtil.fomaterToOne(mOrderBean.getFreight().getNofreight()));
                mMoreHolder.tv_yf.setText(context.getString(R.string.money) + NumberUtil.fomaterToOne(bean.getTotalExpns()));
                if (bean.getTotalExpns() == 0) {
                    mMoreHolder.tv_msg.setText("已免运费");
                } else {
                    mMoreHolder.tv_msg.setText(context.getString(R.string.money) + "还需" + NumberUtil.fomater((mOrderBean.getFreight().getNofreight() - mOrderBean.getTotal_sum())) + "即可免费配送");
                }

                mMoreHolder.tv_xj.setText(context.getString(R.string.money) + NumberUtil.fomater(bean.getSettlement_total()) + "("
                        + context.getResources().getString(R.string.yh) + "IVA)");
                mOrderBean.setTotalexpns(bean.getTotalExpns());
                mOrderBean.setSpainTotal(String.valueOf(bean.getSettlement_total()));
                if (mOrderBean.getAddress() != null) {
                    mMoreHolder.btn.setBackgroundResource(R.drawable.adress_btn_selector);
                    mMoreHolder.btn.setEnabled(true);
                } else {
                    mMoreHolder.btn.setBackgroundResource(R.drawable.btn_bg);
                    mMoreHolder.btn.setEnabled(false);
                }
            }

            @Override
            public void onError(String msg) {
                ((BaseActivity) context).setLoading(false);
                ((BaseActivity) context).showToast(msg, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == 1) {
            return TYPE_GOODS_RECEIPT;
        } else {
            return TYPE_MORE;
        }
    }

    //頭部
    class HeadHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tv_pp;
        ImageView iv_pp;
        GoodsInfoLayout gl;

        public HeadHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view);

            tv_pp = (TextView) itemView.findViewById(R.id.tv_pp);
            iv_pp = (ImageView) itemView.findViewById(R.id.iv_pp);
            gl = (GoodsInfoLayout) itemView.findViewById(R.id.gl);

        }
    }

    // 收貨人
    class AdressHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_adress, ll_select_adress;
        TextView tv_name, tv_phone, tv_zj, tv_adress, tv_right;

        public AdressHolder(View itemView) {
            super(itemView);

            ll_adress = (LinearLayout) itemView.findViewById(R.id.ll_adress);
            ll_select_adress = (LinearLayout) itemView.findViewById(R.id.ll_select_adress);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_zj = (TextView) itemView.findViewById(R.id.tv_zj);
            tv_adress = (TextView) itemView.findViewById(R.id.tv_adress);
            tv_right = (TextView) itemView.findViewById(R.id.tv_right);

            ll_select_adress.setOnClickListener(new View.OnClickListener() {

                private Intent mIntent;

                @Override
                public void onClick(View v) {
                    mIntent = new Intent(context, AdressActivity.class);
                    mIntent.putExtra("tag", 0);
                    ((Activity) context).startActivityForResult(mIntent, RESULT_ORDER);
                }
            });
        }
    }

    private boolean isSelect = false;

    class MoreHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_bottom;
        TextView tv_khzk, tv_zfzk, tv_jezk, tv_yf, tv_msg, tv_xj, tv_total;
        Spinner mSpinner;
        EditText et_ly;
        Button btn;
        @BindView(R.id.rl_jezk)
        RelativeLayout mRlJezk;
        @BindView(R.id.switch_button)
        SwitchButton mSwitchButton;
        @BindView(R.id.tv_zengpin)
        TextView mTvZengPin;
        @BindView(R.id.tv_zengpin_price)
        TextView mTvZengPinPrice;

        public MoreHolder(final View itemView) {
            super(itemView);
            rl_bottom = (RelativeLayout) itemView.findViewById(R.id.rl_bottom);

            tv_khzk = (TextView) itemView.findViewById(R.id.tv_khzk);
            tv_zfzk = (TextView) itemView.findViewById(R.id.tv_zfzk);
            tv_jezk = (TextView) itemView.findViewById(R.id.tv_jezk);
            mSpinner = (Spinner) itemView.findViewById(R.id.spinner2);
            tv_yf = (TextView) itemView.findViewById(R.id.tv_yf);
            tv_msg = (TextView) itemView.findViewById(R.id.tv_msg);
            tv_xj = (TextView) itemView.findViewById(R.id.tv_xj);
            tv_total = (TextView) itemView.findViewById(R.id.tv_total);
            et_ly = (EditText) itemView.findViewById(R.id.et_ly);
            btn = (Button) itemView.findViewById(R.id.btn);
            ButterKnife.bind(this, itemView);
            mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mTvZengPin.setEnabled(b);
                    mOrderBean.setIs_gift(b ? 1 : 0);
                    notifyDataSetChanged();
                }
            });
            mTvZengPin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), EventProductActivity.class);
                    intent.putExtra("id", mOrderBean.getBrandData().getBrand_id());
                    intent.putExtra("type", EventProductActivity.ZENGPIN);
                    Log.e("tag", "brandID" + mOrderBean.getBrandData().getBrand_id());
                    ((Activity) itemView.getContext()).startActivityForResult(intent, 1);
                }
            });
            et_ly.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mOrderBean.setLy(editable.toString());
                }
            });
        }
    }

    public interface OnClickCommitListener {
        void commitListener();
    }
}

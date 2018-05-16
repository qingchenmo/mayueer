package com.jlkf.ego.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
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

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.AdressActivity;
import com.jlkf.ego.activity.OrderOrIssusListActivity;
import com.jlkf.ego.bean.AdressBean;
import com.jlkf.ego.bean.ConfimOrderBean;
import com.jlkf.ego.bean.OrderBean;
import com.jlkf.ego.bean.PayType;
import com.jlkf.ego.bean.YunfeiBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.NumberUtil;
import com.jlkf.ego.utils.ToastUti;
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

public class OrderItemAdapter extends RecyclerView.Adapter {

    public final static int TYPE_HEAD = 0;
    public final static int TYPE_GOODS_RECEIPT = 1;
    public final static int TYPE_MORE = 2;
    public final static int RESULT_ORDER = 555;


    private Context context;
    private int BasePosition;
    public List<ConfimOrderBean.DataBean> mData; // 订单全部
    private List<OrderBean.DataBean.OorderDetailBean> mDetailBeen; // 品牌部分
    private ConfimOrderBean mConfimOrderBean;
    private List<PayType> payTypes;// 支付方式
    private int mUPayId;
    private ConfimOrderBean.DataBean.SpainBean.SpainListBean mSpainListBean;
    private MoreHolder mMoreHolder;
    private OnClickCommitListener mListener;
    ConfimOrderBean mOrderBean;

    private List<Double> mPirces;       // 金额

    public OrderItemAdapter(Context context, int position, ConfimOrderBean data, List<PayType> payTypes, AdressBean adressBean, OnClickCommitListener listener) {
        this.context = context;
        this.BasePosition = position;
        this.mData = data.getData();
        mOrderBean = data;
        mDetailBeen = new ArrayList<>();
        this.mConfimOrderBean = data;

        if (adressBean != null && !TextUtils.isEmpty(adressBean.getPosition())) {
            data.getData().get(Integer.valueOf(adressBean.getPosition())).setAdressBean(adressBean);
        }

        if (mPirces == null) {
            mPirces = new ArrayList<>();
            for (int i = 0; i < data.getData().size(); i++) {
                mPirces.add(data.getData().get(i).getFinallyTotal());
            }
        } else {
            mPirces = data.getPirces();
        }


        this.payTypes = payTypes;
        mListener = listener;

    }


    /**
     * 先获取城市  和  城市id
     * 然后通过城市id 调用城市运费接口获取到运费 和小计
     * 折扣部分通过获取支付方式接口来获取
     *
     * @param parent
     * @param viewType
     * @return
     */


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_HEAD) {
            holder = new HeadHolder(LayoutInflater.from(context).inflate(
                    R.layout.holder_head, parent, false));
        } else if (viewType == TYPE_GOODS_RECEIPT) {
            holder = new AdressHolder(LayoutInflater.from(context).inflate(
                    R.layout.holder_goods_receipt, parent, false));
        } else {
            holder = new MoreHolder(LayoutInflater.from(context).inflate(
                    R.layout.holder_more, parent, false));
        }
        return holder;
    }

    double s = 0.0;
    double s1 = 0.0;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ConfimOrderBean.DataBean dataBean = mData.get(BasePosition);


        final AdressBean adressBean = dataBean.getAdressBean();
        switch (getItemViewType(position)) {
            case TYPE_HEAD:
                HeadHolder headHolder = (HeadHolder) holder;
                if (BasePosition == 0) {
                    headHolder.view.setVisibility(View.GONE);
                } else {
                    headHolder.view.setVisibility(View.VISIBLE);
                }

                headHolder.tv_pp.setText(dataBean.getBrandData().getName());
                Glide.with(context).load(dataBean.getBrandData().getPp_minlogo()).error(R.drawable.icon_img_load).into(headHolder.iv_pp);
                int count = 0;
                for (int i = 0; i < dataBean.getListData().size(); i++) {
                    count = count + dataBean.getListData().get(i).getQuantity();
                }
                headHolder.gl.setNum(count + "");
                headHolder.gl.setPrice(NumberUtil.fomater(dataBean.getTotal()) + "");
                headHolder.gl.setImg(dataBean.getListData());
                headHolder.gl.setBackGrand(R.color.white);
                headHolder.gl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mDetailBeen.clear();
                        for (ConfimOrderBean.DataBean.ListDataBean bean : dataBean.getListData()
                                ) {
                            OrderBean.DataBean.OorderDetailBean oorderDetailBean = new OrderBean.DataBean.OorderDetailBean();
                            oorderDetailBean.setId(bean.getSId());
                            oorderDetailBean.setLogo(bean.getLogo());
                            /**
                             * id : 13
                             * appdocnumber : 201708041979795
                             * itemcode : 1010001
                             * quantity : 1
                             * codebars : DSA
                             * price : 32
                             * name : HELLO
                             * logo : https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3605439283,2740432204&fm=26&gp=0.jpg
                             * brandname : null
                             * brandId : null
                             */
                            oorderDetailBean.setQuantity(bean.getQuantity());
                            oorderDetailBean.setCodebars(bean.getCodebars());
                            oorderDetailBean.setName(bean.getName());
                            oorderDetailBean.setItemcode(bean.getItemcode());
                            oorderDetailBean.setPrice(bean.getPrice() + "");
                            mDetailBeen.add(oorderDetailBean);
                        }


                        Intent intent = new Intent(context, OrderOrIssusListActivity.class);
                        intent.putExtra("type", OrderOrIssusAdapter.ORDERLIST);
                        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) mDetailBeen);
                        context.startActivity(intent);
                    }
                });

                break;
            case TYPE_GOODS_RECEIPT:
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


                break;
            case TYPE_MORE:
                mMoreHolder = (MoreHolder) holder;


                if (BasePosition == mData.size() - 1) {
                    mMoreHolder.rl_bottom.setVisibility(View.VISIBLE);
                } else {
                    mMoreHolder.rl_bottom.setVisibility(View.GONE);
                }
//
//                for (int i = 0; i < payTypes.size(); i++) {
//                    Log.v("支付方式", payTypes.get(i).getUPayName());
//                }
                for (int i = 0; i < mConfimOrderBean.getData().size(); i++) {
                    double finallyTotal = mConfimOrderBean.getData().get(i).getFinallyTotal();
                    s1 = s1 + finallyTotal;
                    Log.v("-------总金额", finallyTotal + "");
                }
//                mMoreHolder.tv_total.setText(NumberUtil.fomater(s) + "");

                mMoreHolder.mSpinner.setAdapter(new SpinnerAdapter(context, payTypes));


                mMoreHolder.mSpinner.setSelection(dataBean.getType());

                mMoreHolder.tv_khzk.setText(NumberUtil.fomaterToOne(dataBean.getuZk1()) + "%");
                mMoreHolder.tv_zfzk.setText(NumberUtil.fomaterToOne(dataBean.getuZk2()) + "%");
                if (dataBean.getuZk3() != 0.0) {
                    mMoreHolder.tv_jezk.setText(NumberUtil.fomaterToOne(dataBean.getuZk3()) + "%");
                }


                mMoreHolder.tv_yf.setText(context.getString(R.string.money) + dataBean.getTotalexpns());
                mMoreHolder.tv_msg.setText(context.getString(R.string.money) + dataBean.getMsg());
                mMoreHolder.tv_xj.setText(context.getString(R.string.money) + NumberUtil.fomater(dataBean.getFinallyTotal()) + "(已含IVA)");

                mMoreHolder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        PayType payType = payTypes.get(position);
                        mUPayId = payType.getUPayId();
                        dataBean.setType(position);
                        dataBean.setFkfs(mUPayId + "");

                        for (int i = 0; i < dataBean.getSpain().getSpainList().size(); i++) {
                            if (dataBean.getSpain().getSpainList().get(i).getName().equals(mUPayId + "")) {
                                mSpainListBean = dataBean.getSpain().getSpainList().get(i);
                                mMoreHolder.tv_khzk.setText(NumberUtil.fomaterToOne(mSpainListBean.getA() * 100) + "%");
                                mMoreHolder.tv_zfzk.setText(NumberUtil.fomaterToOne(mSpainListBean.getB() * 100) + "%");
                                Log.e("--------------", "" + mSpainListBean.getC());
                                dataBean.setuZk1(mSpainListBean.getA());
                                dataBean.setuZk2(mSpainListBean.getB());
                                dataBean.setuZk3(mSpainListBean.getC());
                                dataBean.setuZk4(mSpainListBean.getD());

                                if (mSpainListBean.getC() == 0.0) {
                                    mMoreHolder.mRlJezk.setVisibility(View.GONE);
                                    Log.e("--------------", "不显示");
                                } else {
                                    Log.e("--------------", "显示" + NumberUtil.fomaterToOne(mSpainListBean.getC() * 100));
                                    mMoreHolder.mRlJezk.setVisibility(View.VISIBLE);
                                    mMoreHolder.tv_jezk.setText(NumberUtil.fomaterToOne(mSpainListBean.getC() * 100) + "%");
                                }
                                if (adressBean != null) {

                                    getYunFei(adressBean, mSpainListBean.getSpainTotal(), dataBean, position);
                                }
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                mMoreHolder.et_ly.setText(dataBean.getLy());
                mMoreHolder.et_ly.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        dataBean.setLy(s.toString());
                    }
                });
                holder.itemView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.commitListener();
                    }
                });
                break;
        }
    }


    public void getYunFei(final AdressBean adressBean, final double total, final ConfimOrderBean.DataBean dataBean, final int position) {

        Map<String, String> object = new HashMap<>();
        if (adressBean != null) {

            object.put("addrId", adressBean.getUaId());
        } else {
            object.put("addrId", 21 + "");

        }
        object.put("total", total + "");
        HttpUtil.getInstacne((Activity) context).get(Urls.cityFreight, YunfeiBean.class, object, new HttpUtil.OnCallBack<YunfeiBean>() {


            @Override
            public void success(YunfeiBean data) {
                Log.v("BasePosition", BasePosition + "," + dataBean.getType());
                s1 = 0;
                mMoreHolder.tv_yf.setText(context.getString(R.string.money) + NumberUtil.fomaterToOne(data.getFreight()));
                mMoreHolder.tv_msg.setText(context.getString(R.string.money) + data.getMsg());
                mMoreHolder.tv_xj.setText(context.getString(R.string.money) + NumberUtil.fomater(data.getFinallyTotal()) + "("
                        + context.getResources().getString(R.string.yh) + "IVA)");

                dataBean.setSpainTotal(data.getFinallyTotal() + "");
                dataBean.setFinallyTotal(data.getFinallyTotal());
                dataBean.setMsg(data.getMsg());
                dataBean.setTotalexpns(data.getFreight());
                mOrderBean.getData().set(BasePosition, dataBean);


                int tag = 0;
                Log.v("-----------", BasePosition + "测试调用了吗" + data.getFinallyTotal());
                double s = 0;
                for (int i = 0; i < mOrderBean.getData().size(); i++) {
                    Log.v("-----------", "测试" + mOrderBean.getData().get(i).getFinallyTotal());
                    s = s + mOrderBean.getData().get(i).getFinallyTotal();

                    if (mOrderBean.getData().get(i).getAdressBean() != null) {
                        tag++;
                    }
                }
                if (tag == mOrderBean.getData().size()) {
                    mMoreHolder.btn.setBackgroundResource(R.drawable.adress_btn_selector);
                    mMoreHolder.btn.setEnabled(true);
                } else {
                    mMoreHolder.btn.setBackgroundResource(R.drawable.btn_bg);
                    mMoreHolder.btn.setEnabled(false);
                }
                mMoreHolder.tv_total.setText(context.getResources().getString(R.string.money) + NumberUtil.fomater(s) + "");

            }

            @Override
            public void onError(String msg, int code) {

                ToastUti.show(msg);
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
                    mIntent.putExtra("tag", BasePosition);
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

        public MoreHolder(View itemView) {
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
                }
            });
        }
    }

    public interface OnClickCommitListener {
        void commitListener();
    }
}

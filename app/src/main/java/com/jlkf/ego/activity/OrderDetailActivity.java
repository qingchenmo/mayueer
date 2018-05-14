package com.jlkf.ego.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.adapter.OrderOrIssusAdapter;
import com.jlkf.ego.bean.GilOrderRealBean;
import com.jlkf.ego.bean.MyBaseBean;
import com.jlkf.ego.bean.OrderBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.NumberUtil;
import com.jlkf.ego.utils.OrderUtils;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.widget.GoodsInfoLayout;
import com.jlkf.ego.widget.TitleView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jlkf.ego.R.id.tv_bottom_left;
import static com.jlkf.ego.R.id.tv_goods_phone;

/**
 * @author zcs
 *         订单详情页
 */
public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.tv_order_accopt_time)
    TextView tvOrderAccoptTime;
    @BindView(R.id.tv_goods_persion)
    TextView tvGoodsPersion;
    @BindView(tv_goods_phone)
    TextView tvGoodsPhone;
    @BindView(R.id.tv_goods_call_num)
    TextView tvGoodsCallNum;
    @BindView(R.id.tv_goods_address)
    TextView tvGoodsAddress;
    @BindView(R.id.tv_buy_msg)
    TextView tvBuyMsg;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.tv_kehu_zhekou)
    TextView tvKehuZhekou;
    @BindView(R.id.tv_zhifu_zhekou)
    TextView tvZhifuZhekou;
    @BindView(R.id.tv_jine_zhekou)
    TextView tvJineZhekou;
    @BindView(R.id.tv_tebie_zhekou)
    TextView tvTebieZhekou;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_has_pay)
    TextView tvHasPay;
    @BindView(R.id.tv_real_payment)
    TextView tvRealPayment;
    @BindView(R.id.tv_real_pay)
    TextView tvRealPay;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.tv_bottom_right)
    TextView tvBottomRight;
    @BindView(tv_bottom_left)
    TextView tvBottomLeft;
    @BindView(R.id.tv_bottom_instructions)
    TextView tvBottomInstructions;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.tv_order_code_left)
    TextView tvOrderCodeLeft;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.gil_order_list)
    GoodsInfoLayout gilOrderList;
    @BindView(R.id.tv_order_code_real_left)
    TextView tvOrderCodeRealLeft;
    @BindView(R.id.tv_order_real_num)
    TextView tvOrderRealNum;
    @BindView(R.id.rl_order_real_list)
    RelativeLayout rlOrderRealList;
    @BindView(R.id.gil_order_real_list)
    GoodsInfoLayout gilOrderRealList;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.fl_real_payment)
    FrameLayout flRealPayment;
    @BindView(R.id.fl_real_pay)
    FrameLayout flRealPay;
    @BindView(R.id.tv_order_list)
    TextView tv_order_list;
    private int orderType;//订单状态
    private OrderBean.DataBean mBean;
    private int type;//用于判断时从什么界面到这个界面的
    private ArrayList<OrderBean.DataBean.OorderDetailBean> mList;

    @Override
    public void initView() {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        orderType = getIntent().getIntExtra("orderType", 0);
        setView();
    }

    private void setView() {
        String[] states = getResources().getStringArray(R.array.order_detail);
        tvOrderState.setText(states[orderType]);
        title.setTitle(states[orderType]);
        type = getIntent().getIntExtra("type", 0);
        switch (orderType) {
            case 0:
                rlBottom.setVisibility(View.VISIBLE);
                tvOrderAccoptTime.setVisibility(View.VISIBLE);
                rlOrderRealList.setVisibility(View.GONE);
                gilOrderRealList.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                tv_order_list.setVisibility(View.GONE);
                flRealPayment.setVisibility(View.GONE);
                flRealPay.setVisibility(View.GONE);
                tvHasPay.setTextColor(mContext.getResources().getColor(R.color.price_red));
                break;
            case 1:
                rlOrderRealList.setVisibility(View.GONE);
                gilOrderRealList.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                rlBottom.setVisibility(View.VISIBLE);
                tvBottomInstructions.setText(getString(R.string.o_sjzkblhsfjeyshszdwz));
                tvBottomLeft.setVisibility(View.GONE);
                tvBottomRight.setText(getString(R.string.o_lxkf));
                tvBottomRight.setBackgroundResource(R.drawable.corner_bule);
                tv_order_list.setVisibility(View.GONE);
                flRealPayment.setVisibility(View.GONE);
                flRealPay.setVisibility(View.GONE);
                tvHasPay.setTextColor(mContext.getResources().getColor(R.color.price_red));
                break;
            case 2:
                rlBottom.setVisibility(View.VISIBLE);
                tvBottomInstructions.setVisibility(View.GONE);
                tvBottomRight.setText(getString(R.string.o_qrsh));
                tvBottomLeft.setText(getString(R.string.o_lxkf));
                break;
            case 3:
                tvBottom.setVisibility(View.VISIBLE);
                rlBottom.setVisibility(View.GONE);
                tvHasPay.setTextColor(getResources().getColor(R.color.text_title));
                break;
            case 4:
                tvBottom.setVisibility(View.VISIBLE);
                rlOrderRealList.setVisibility(View.GONE);
                rlBottom.setVisibility(View.GONE);
                gilOrderRealList.setVisibility(View.GONE);
                tvHasPay.setTextColor(getResources().getColor(R.color.price_red));
                line.setVisibility(View.GONE);
                flRealPay.setVisibility(View.GONE);
                flRealPayment.setVisibility(View.GONE);
                tv_order_list.setVisibility(View.GONE);
//                flRealPayment.setVisibility(View.GONE);
//                flRealPay.setVisibility(View.GONE);
//                tvHasPay.setTextColor(mContext.getResources().getColor(R.color.price_red));
                break;
        }
    }

    public void initData() {
        Map<String, String> map = new HashMap<>();
        if (getIntent().getStringExtra("orderId") != null)
            map.put("id", getIntent().getStringExtra("orderId"));
        if (getIntent().getStringExtra("code") != null || getIntent().getStringExtra("AppDocNumber") != null)
            map.put("AppDocNumber", getIntent().getStringExtra("AppDocNumber"));
        HttpUtil.getInstacne(this).get2(Urls.orderList, OrderBean.class, map, new HttpUtil.OnCallBack<OrderBean>() {
            @Override
            public void success(OrderBean data) {
                mBean = data.getData().get(0);
                addDataToView(data.getData().get(0));
                setAlrealRead();
                if (orderType == 2 || orderType == 3)
                    getGilOrderDetail(mBean.getAppdocnumber());
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    public void getGilOrderDetail(String appdocnumber) {
        Map<String, String> map = new HashMap<>();
        map.put("appdocnumber", appdocnumber);
        HttpUtil.getInstacne(this).get2(Urls.invoice, GilOrderRealBean.class, map, new HttpUtil.OnCallBack<GilOrderRealBean>() {
            @Override
            public void success(GilOrderRealBean data) {
//                ToastUti.show("实发商品清单数据暂时只有一个订单编号有此数据\n现为写死的订单编号");
                mList = new ArrayList<>();
                double price = 0;
                if (data == null || data.getData() == null||data.getData().size()<1) return;
                for (int i = 0; i < data.getData().size(); i++) {
                    OrderBean.DataBean.OorderDetailBean bean = new OrderBean.DataBean.OorderDetailBean();
                    GilOrderRealBean.DataBean dataBean = data.getData().get(i);
                    bean.setAppdocnumber(dataBean.getAppdocnumber());
                    bean.setItemcode(dataBean.getItemcode());
                    bean.setQuantity(dataBean.getQuantity());
                    bean.setLogo(dataBean.getPicturname());
                    bean.setName(dataBean.getCardname());
                    bean.setPrice(dataBean.getLinetotal());
                    price = price + Double.valueOf(bean.getPrice()) * dataBean.getQuantity();
                    mList.add(bean);
                }
//                tvRealPayment.setText(data.getData().get());
                tvRealPayment.setText(data.getData().get(0).getU_FKFS());
                gilOrderRealList.setView(mList, NumberUtil.fomater(price) + "");
            }

            @Override
            public void onError(String msg, int code) {

            }
        });

    }

    /**
     * 填充数据到view中
     *
     * @param bean
     */
    private void addDataToView(OrderBean.DataBean bean) {
        tvOrderNum.setText(bean.getAppdocnumber());
        addAddressToView(bean);
        addProductList(bean);
        addOrderInfoToView(bean);
    }

    /**
     * 设置订单信息
     *
     * @param bean
     */
    private void addOrderInfoToView(OrderBean.DataBean bean) {
        tvBuyMsg.setText(bean.getComments());
        tvOrderCode.setText(bean.getAppdocnumber());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        tvOrderTime.setText(format.format(bean.getDocdate()));
        int num = 0;
        double price = 0;
        for (int i = 0; i < bean.getOorderDetail().size(); i++) {
            price = price + bean.getOorderDetail().get(i).getQuantity() * Double.valueOf(bean.getOorderDetail().get(i).getPrice());
            num = num + bean.getOorderDetail().get(i).getQuantity();
        }
        tvGoodsNum.setText(num + getString(R.string.o_jian));
        tvGoodsPrice.setText(getString(R.string.money) + NumberUtil.fomater(price));
        tvPayWay.setText(bean.getUpayMethod().getUPayName());
        tvKehuZhekou.setText(bean.getUZk1() * 100 + "%");
        tvZhifuZhekou.setText(bean.getUZk2() * 100 + "%");
        tvJineZhekou.setText(bean.getUZk3() * 100 + "%");
        tvTebieZhekou.setText(bean.getUZk4() * 100 + "%");
        tvFreight.setText("+" + getString(R.string.money) + bean.getTotalexpns());
        double needPay = Double.valueOf(bean.getDoctotal()) / (bean.getUZk1() > 0 ? 1 - bean.getUZk1() : 1)
                / (bean.getUZk2() > 0 ? 1 - bean.getUZk2() : 1) / (bean.getUZk3() > 0 ? 1 - bean.getUZk3() : 1) / (bean.getUZk4() > 0 ? 1 - bean.getUZk4() : 1);
        tvHasPay.setText(getString(R.string.money) + NumberUtil.fomater(price + Double.valueOf(bean.getTotalexpns())));
//        tvRealPayment.setText(bean.getUpayMethod().getUPayName());
        tvRealPay.setText(getString(R.string.money) + bean.getDoctotal());
    }

    /**
     * 设置商品清单
     *
     * @param bean
     */
    private void addProductList(OrderBean.DataBean bean) {
        if (orderType == 0) {
            Calendar calendar = Calendar.getInstance();
            long time = (bean.getDocdate() + 4 * 60 * 60 * 1000) - calendar.getTimeInMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            int hour = (int) (time / (1000 * 60 * 60));
            int minute = (int) (time % (1000 * 60 * 60)) / (1000 * 60);
            tvOrderAccoptTime.setText("(" + hour + ":" + ((minute < 10) ? "0" : "") + minute + getString(R.string.o_xshjrdfhzt) + ")");
            MyThread thread = new MyThread(tvOrderAccoptTime, time);
            thread.start();
        }
        gilOrderList.setView(bean);
    }

    /**
     * 设置收货地址
     *
     * @param bean
     */
    private void addAddressToView(OrderBean.DataBean bean) {
        if (bean.getUaddress() == null) return;
        OrderBean.DataBean.UaddressBean uaddressBean = bean.getUaddress();
        tvGoodsPersion.setText(uaddressBean.getUaName() + "  " + uaddressBean.getUaSurname());
        tvGoodsPhone.setText(uaddressBean.getUaContactphone());
        tvGoodsCallNum.setText(uaddressBean.getUaLandline());
        tvGoodsAddress.setText(/*uaddressBean.getUaCountriesName() + */uaddressBean.getUaProvincialName() + uaddressBean.getUaCityName() + uaddressBean.getUaDelivery());
    }

    /**
     * 设置已读未读
     */
    private void setAlrealRead() {
        Map<String, String> map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("orderId"));
        HttpUtil.getInstacne(this).get2(Urls.read, MyBaseBean.class, map, new HttpUtil.OnCallBack<MyBaseBean>() {
            @Override
            public void success(MyBaseBean data) {

            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }


    @Override
    public void iniActivityAnimation() {

    }

    @OnClick(R.id.gil_order_list)
    void clickOrderList() {
        Intent intent = new Intent(mContext, OrderOrIssusListActivity.class);
        intent.putExtra("type", OrderOrIssusAdapter.ORDERLIST);
        intent.putParcelableArrayListExtra("list", mBean.getOorderDetail());
        startActivity(intent);
    }

    @OnClick(R.id.gil_order_real_list)
    void clickOrderRealList() {
        if (mList == null || mList.size() < 1) {
            ToastUti.show("暂无数据");
            return;
        }
        Intent intent = new Intent(mContext, OrderOrIssusListActivity.class);
        intent.putParcelableArrayListExtra("list", mList);
        intent.putExtra("type", OrderOrIssusAdapter.ISSUSLIST);
        startActivity(intent);
    }

    @OnClick(R.id.iv_title_left)
    void back() {
        if (type == 1) {
            Intent intent = new Intent(this, OrderListActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        } else {
            finish();
        }
    }

    @OnClick({R.id.tv_bottom, R.id.tv_bottom_left, R.id.tv_bottom_right})
    void click(View v) {
        switch (v.getId()) {
            case R.id.tv_bottom:
                againBuy();
                break;
            case R.id.tv_bottom_left:
                if (orderType == 0) {
                    OrderUtils.getIntance().cacelOrder(this, mBean.getAppdocnumber() + "");
                } else if (orderType == 2) {
                    OrderUtils.getIntance().ContactServer(this);
                }
                break;
            case R.id.tv_bottom_right:
                if (orderType == 0) {
                    OrderUtils.getIntance().updataOrder(this, mBean.getId() + "");
                } else if (orderType == 1) {
                    OrderUtils.getIntance().ContactServer(this);
                } else if (orderType == 2) {
                    OrderUtils.getIntance().confimShouhuo(this, mBean.getId() + "");
                }
                break;
        }
    }

    /**
     * 再次购买
     */
    private void againBuy() {
        OrderUtils.getIntance().againBuy(this, mBean.getId() + "");
    }

    class MyThread extends Thread {
        TextView tv;
        long time;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        protected MyThread(TextView tv, long time) {
            this.tv = tv;
            this.time = time;
        }

        @Override
        public void run() {
            while (!isCancel) {
                try {
                    sleep(1000 * 60);
                } catch (InterruptedException e) {
                }
                time = time - 1000 * 60;
                final int hour = (int) (time / (1000 * 60 * 60));
                final int minute = (int) (time % (1000 * 60 * 60)) / (1000 * 60);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("(" + hour + ":" + ((minute < 10) ? "0" : "") + minute + getString(R.string.o_xshjrdfhzt) + ")");
                    }
                });
                if (time < 0) {
                    isCancel = true;
                    orderType = 2;
                    setView();
                    initData();
                }
            }
        }
    }

    private boolean isCancel;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isCancel = true;
    }
}

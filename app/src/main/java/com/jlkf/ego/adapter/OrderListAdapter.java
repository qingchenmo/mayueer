package com.jlkf.ego.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.OrderDetailActivity;
import com.jlkf.ego.bean.OrderBean;
import com.jlkf.ego.fragment.OrderListFragment;
import com.jlkf.ego.utils.NumberUtil;
import com.jlkf.ego.utils.OrderUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zcs on 2017/7/17.
 * 订单列表Adapter
 */

public class OrderListAdapter extends RecyclerView.Adapter {
    private List<OrderBean.DataBean> mList;
    private Context mContext;
    private int mOrderType;
    private String[] orderTypes;
    private boolean bindView = true;
    private OrderListFragment mFragment;

    public OrderListAdapter(Context context, List<OrderBean.DataBean> list, int orderType, OrderListFragment fragment) {
        mContext = context;
        mList = list;
        mFragment = fragment;
        mOrderType = orderType;
        orderTypes = context.getResources().getStringArray(R.array.order_type);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderBean.DataBean bean = mList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_order_code.setText(bean.getAppdocnumber());
        try {
            viewHolder.tv_order_type.setText(orderTypes[Integer.parseInt(bean.getDocstatus()) + 1]);
            viewHolder.lin_order_item.setOnClickListener(new OnClickListener(Integer.parseInt(bean.getDocstatus()), position));
            viewHolder.tv_item_bottom_right.setOnClickListener(new OnClickListener(Integer.parseInt(bean.getDocstatus()), position));
            viewHolder.tv_item_bottom_left.setOnClickListener(new OnClickListener(Integer.parseInt(bean.getDocstatus()), position));
        } catch (Exception e) {

        }
        viewHolder.tv_wait_accept.setVisibility(View.GONE);

        int num = 0;
        double price = 0;
        for (int i = 0; i < bean.getOorderDetail().size(); i++) {
            num = num + bean.getOorderDetail().get(i).getQuantity();
            price = price + bean.getOorderDetail().get(i).getQuantity() * Double.valueOf(bean.getOorderDetail().get(i).getPrice());
            if (i == 0) {
                Glide.with(mContext).load(bean.getOorderDetail().get(i).getLogo()).placeholder(R.drawable.icon_img_load).error(R.drawable.icon_img_load_failed).into(viewHolder.iv_left);
            } else if (i == 1) {
                Glide.with(mContext).load(bean.getOorderDetail().get(i).getLogo()).placeholder(R.drawable.icon_img_load).error(R.drawable.icon_img_load_failed).into(viewHolder.iv_middle);
            } else if (i == 2) {
                Glide.with(mContext).load(bean.getOorderDetail().get(i).getLogo()).placeholder(R.drawable.icon_img_load).error(R.drawable.icon_img_load_failed).into(viewHolder.iv_right);
            }
        }
//        viewHolder.tv_product_price.setText(mContext.getString(R.string.money) + NumberUtil.fomater(price));
        viewHolder.tv_product_price.setText(mContext.getString(R.string.money) + NumberUtil.fomater(price + Double.valueOf(bean.getTotalexpns())));
        viewHolder.iv_middle.setVisibility(bean.getOorderDetail().size() > 1 ? View.VISIBLE : View.GONE);
        viewHolder.iv_right.setVisibility(bean.getOorderDetail().size() > 2 ? View.VISIBLE : View.GONE);
        viewHolder.iv_order_img_more.setVisibility(bean.getOorderDetail().size() > 3 ? View.VISIBLE : View.GONE);
        viewHolder.tv_product_num.setText(mContext.getString(R.string.o_gong) + num + mContext.getString(R.string.o_jian));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        viewHolder.tv_order_time.setText(format.format(bean.getDocdate()));
        if (bean.getDocstatus() != null)
            switch (bean.getDocstatus()) {
                case "0":
                    viewHolder.tv_order_type.setText(mContext.getString(R.string.o_dsl));
                    viewHolder.tv_wait_accept.setVisibility(View.VISIBLE);
                    Calendar calendar = Calendar.getInstance();
                    long time = (bean.getDocdate() + 4 * 60 * 60 * 1000) - calendar.getTimeInMillis();
                    final int hour = (int) (time / (1000 * 60 * 60));
                    final int minute = (int) (time % (1000 * 60 * 60)) / (1000 * 60);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    viewHolder.tv_wait_accept.setText(hour + ":" + ((minute < 10) ? "0" : "") + minute + mContext.getString(R.string.o_xshjrdfhzt));
//                Timer timer = new Timer();
//                MyTimeTask task = new MyTimeTask(viewHolder.tv_wait_accept, time, position, timer);

//                timer.schedule(task, 1000, 1000);
                    if (time > 0 && !bean.isCountDown()) {
                        new MyCountDownTimer(time, 1000 * 60, viewHolder.tv_wait_accept).start();
                        bean.setCountDown(true);
                    }
                    viewHolder.tv_item_bottom_right.setBackgroundResource(R.drawable.kuang_white_bg);
                    viewHolder.tv_item_bottom_left.setText(mContext.getString(R.string.o_qxdd));
                    viewHolder.tv_item_bottom_right.setText(mContext.getString(R.string.o_xgdd));
                    break;
                case "1":
                    viewHolder.tv_order_type.setText(mContext.getString(R.string.o_dfh));
                    viewHolder.tv_item_bottom_left.setVisibility(View.GONE);
                    viewHolder.tv_item_bottom_right.setText(mContext.getString(R.string.o_lxkf));
                    viewHolder.tv_item_bottom_right.setBackgroundResource(R.drawable.kuang_white_bg);
                    break;
                case "2":
                    viewHolder.tv_order_type.setText(mContext.getString(R.string.o_psz));
                    viewHolder.tv_item_bottom_left.setVisibility(View.VISIBLE);
                    viewHolder.tv_item_bottom_left.setText(mContext.getString(R.string.o_lxkf));
                    viewHolder.tv_item_bottom_right.setText(mContext.getString(R.string.o_qrsh));
                    viewHolder.tv_item_bottom_right.setTextColor(mContext.getResources().getColor(R.color.bg_brown));
                    viewHolder.tv_item_bottom_right.setBackgroundResource(R.drawable.kuang_brown_white_bg);
                    break;
                case "3":
                    viewHolder.tv_order_type.setText(mContext.getString(R.string.o_ywc));
                    viewHolder.tv_item_bottom_left.setVisibility(View.GONE);
                    viewHolder.tv_item_bottom_right.setText(mContext.getString(R.string.o_zcgm));
                    viewHolder.tv_item_bottom_right.setTextColor(mContext.getResources().getColor(R.color.bg_brown));
                    viewHolder.tv_item_bottom_right.setBackgroundResource(R.drawable.kuang_brown_white_bg);
                    break;
                case "4":
                    viewHolder.tv_order_type.setText(mContext.getString(R.string.o_yqx));
                    viewHolder.tv_item_bottom_left.setVisibility(View.GONE);
                    viewHolder.tv_item_bottom_right.setText(mContext.getString(R.string.o_zcgm));
                    viewHolder.tv_item_bottom_right.setTextColor(mContext.getResources().getColor(R.color.bg_brown));
                    viewHolder.tv_item_bottom_right.setBackgroundResource(R.drawable.kuang_brown_white_bg);
                    break;
            }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_order_code, tv_order_type, tv_product_price, tv_wait_accept,
                tv_product_num, tv_order_time, tv_item_bottom_left, tv_item_bottom_right;
        private ImageView iv_left, iv_middle, iv_right, iv_order_img_more;
        private LinearLayout lin_order_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_order_code = (TextView) itemView.findViewById(R.id.tv_order_code);
            tv_order_type = (TextView) itemView.findViewById(R.id.tv_order_type);
            tv_product_price = (TextView) itemView.findViewById(R.id.tv_product_price);
            tv_wait_accept = (TextView) itemView.findViewById(R.id.tv_wait_accept);
            tv_product_num = (TextView) itemView.findViewById(R.id.tv_product_num);
            tv_order_time = (TextView) itemView.findViewById(R.id.tv_order_time);
            tv_item_bottom_left = (TextView) itemView.findViewById(R.id.tv_item_bottom_left);
            tv_item_bottom_right = (TextView) itemView.findViewById(R.id.tv_item_bottom_right);

            iv_left = (ImageView) itemView.findViewById(R.id.iv_left);
            iv_middle = (ImageView) itemView.findViewById(R.id.iv_middle);
            iv_right = (ImageView) itemView.findViewById(R.id.iv_right);
            iv_order_img_more = (ImageView) itemView.findViewById(R.id.iv_order_img_more);
            lin_order_item = (LinearLayout) itemView.findViewById(R.id.lin_order_item);
        }
    }

    class OnClickListener implements View.OnClickListener {
        private int orderType;
        private int position;

        public OnClickListener(int orderType, int position) {
            this.orderType = orderType;
            this.position = position;
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_item_bottom_left:
                    clickLeftTv();
                    break;
                case R.id.tv_item_bottom_right:
                    clickRightTv();
                    break;
                case R.id.lin_order_item:
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.putExtra("orderType", orderType);
                    intent.putExtra("orderId", mList.get(position).getId() + "");
                    mContext.startActivity(intent);
                    break;
            }
        }

        /**
         * 点击右侧按钮
         */
        private void clickRightTv() {
            if (orderType == 3 || orderType == 4) {
                OrderUtils.getIntance().againBuy((Activity) mContext, mList.get(position).getId() + "");
            } else if (orderType == 2) {
                OrderUtils.getIntance().confimShouhuo((Activity) mContext, mList.get(position).getId() + "");
            } else if (orderType == 0) {
                OrderUtils.getIntance().updataOrder((Activity) mContext, mList.get(position).getId() + "");
            } else if (orderType == 1) {
                OrderUtils.getIntance().ContactServer(mContext);
            }
        }

        /**
         * 点击左侧按钮
         */
        private void clickLeftTv() {
            if (orderType == 0) {
                OrderUtils.getIntance().cacelOrder((Activity) mContext, mList.get(position).getAppdocnumber() + "");
            } else if (orderType == 2) {
                OrderUtils.getIntance().ContactServer(mContext);
            }
        }
    }


    class MyTimeTask extends TimerTask {
        private TextView tv;
        private long time;
        private int postion;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        private Timer mTimer;

        protected MyTimeTask(TextView tv, long time, int position, Timer timer) {
            this.tv = tv;
            this.time = time;
            this.postion = position;
            this.mTimer = timer;
        }

        @Override
        public void run() {
            if (!bindView) this.cancel();
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    time = time - 1000 * 60;
                    tv.setText(format.format(time) + mContext.getString(R.string.o_xshjrdfhzt));
                    if (time <= 0) {
//                        MyTimeTask.this.cancel();
//                        mTimer.cancel();
//                        mFragment.lazyLoad();
//                        mTimer = null;
//                        System.gc();
                    }
                }
            });
        }
    }

    class MyCountDownTimer extends CountDownTimer {
        boolean down = true;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        TextView tv;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView tv) {
            super(millisInFuture, countDownInterval);
            this.tv = tv;
        }

        @Override
        public void onTick(long time) {
            final int hour = (int) (time / (1000 * 60 * 60));
            final int minute = (int) (time % (1000 * 60 * 60)) / (1000 * 60);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            tv.setText(hour + ":" + ((minute < 10) ? "0" : "") + minute + mContext.getString(R.string.o_xshjrdfhzt));
//            tv.setText(format.format(millisUntilFinished) + "小时后进入待发货状态");
            if (time < 0) {
                cancel();
            }
        }

        @Override
        public void onFinish() {
            if (down) {
                down = false;
                mFragment.lazyLoad();
//                cancel();
            }
        }
    }
}

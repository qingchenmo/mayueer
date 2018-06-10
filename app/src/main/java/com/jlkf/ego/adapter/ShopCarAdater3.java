package com.jlkf.ego.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.ShopCarItemActivity;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.fragment.ShopCarFragment3;
import com.jlkf.ego.newpage.bean.RefreshShopCar;
import com.jlkf.ego.utils.NumberUtil;
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/23.
 */
public class ShopCarAdater3 extends RecyclerView.Adapter<ShopCarAdater3.ShopViewHolder> {

    private int mPosition;
    private Activity mContext;

    public List<GoodsBean> getGoodsBeen() {
        return mGoodsBeen;
    }

    List<GoodsBean> mGoodsBeen;
    ShopCarFragment3 mShopCarFragment3;

    public ShopCarAdater3(Context context, List<GoodsBean> goodsBeen, ShopCarFragment3 shopCarFragment3) {
        mContext = (Activity) context;
        mGoodsBeen = goodsBeen;
        mShopCarFragment3 = shopCarFragment3;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.shop_holder, parent, false);
        return new ShopViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        ShopViewHolder shopViewHolder = holder;
        GoodsBean goodsBean = mGoodsBeen.get(position);

        // 设置数据
        shopViewHolder.mTvPp.setText(goodsBean.getBrandData().getName());
        shopViewHolder.mTvPrice.setText(NumberUtil.fomater(goodsBean.getTotal()) + mContext.getResources().getString(R.string.money));

        shopViewHolder.mRv.setLayoutManager(new GridLayoutManager(mContext, 4));
        shopViewHolder.mRv.setAdapter(new IImageAdapter(mContext, goodsBean.getShopcart()));
        Glide.with(mContext).load(goodsBean.getBrandData().getPp_minlogo()).into(shopViewHolder.iv_pp);


        RichText.fromHtml(goodsBean.getBrandData().getPp_context()).into(shopViewHolder.mTv);
    }

    public int getmPosition() {
        return mPosition;
    }

    @Override
    public int getItemCount() {
        if (mGoodsBeen != null && mGoodsBeen.size() != 0) {
            return mGoodsBeen.size();
        }
        return 0;
    }


    public class ShopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_pp)
        TextView mTvPp;
        @BindView(R.id.rv)
        RecyclerView mRv;
        @BindView(R.id.tv)
        TextView mTv;
        @BindView(R.id.view)
        View mView;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.rl)
        RelativeLayout mRl;
        @BindView(R.id.carview)
        LinearLayout mCarview;
        @BindView(R.id.iv_pp)
        ImageView iv_pp;
        private Intent mIntent;

        public ShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    start();
                }
            });
            mRv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {//此处是点击按下时才执行跳转动作
                        start();
                    }
                    return false;
                }
            });


            mCarview.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    start();
                }
            });
        }

        private void start() {
            mPosition = getLayoutPosition();
            mIntent = new Intent(mContext, ShopCarItemActivity.class);
            mIntent.putExtra("data", mGoodsBeen.get(getLayoutPosition()));
            mShopCarFragment3.startActivityForResult(mIntent, 1);
        }
    }


}

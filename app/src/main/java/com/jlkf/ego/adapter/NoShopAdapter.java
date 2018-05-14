package com.jlkf.ego.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.ProductQuickSelectActivity;
import com.jlkf.ego.bean.Connection2;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.fragment.ShopCarFragment3;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.ProductAddShopCarUtils;
import com.jlkf.ego.widget.FullyGridLayoutManager;
import com.jlkf.ego.widget.SelectView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.jlkf.ego.utils.ShardeUtil.getUser;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class NoShopAdapter extends RecyclerView.Adapter {

    private static final int TOP = 0;
    private static final int ITEM = 1;


    public List<Connection2> getDatas() {
        return mDatas;
    }


    private List<Connection2> mDatas;
    private Context context;
    private ShopCarFragment3 mShopCarFragment;
    FullyGridLayoutManager mFullyGridLayoutManager;

    public NoShopAdapter(Context context, List<Connection2> mDatas, ShopCarFragment3 shopCarFragment, FullyGridLayoutManager layoutManager) {
        this.mDatas = mDatas;
        this.context = context;
        mShopCarFragment = shopCarFragment;
        this.mFullyGridLayoutManager = layoutManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        if (viewType == TOP) {
            View view = LayoutInflater.from(context).inflate(R.layout.holder_top_shop, parent, false);
            holder = new TopHolder(view);
        } else if (viewType == ITEM) {

            View view = LayoutInflater.from(context).inflate(R.layout.holder_no_shop, parent, false);
            holder = new ViewHolder(view);
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mholder, final int position) {
        if (position != 0) {
            final ViewHolder holder =
                    (ViewHolder) mholder;


            final Connection2 oitmView = mDatas.get(position - 1);
            if (!TextUtils.isEmpty(oitmView.getPicturname())) {
                Glide.with(context).load(oitmView.getPicturname()).
                        error(R.drawable.icon_img_load_failed).into(holder.mIvProductImg);
            }

            holder.mTvProductName.setText(oitmView.getItemname());
            holder.mTvProductStock.setText(oitmView.getOnhand() + "");
            holder.mTvProductPrice.setText(context.getResources().getString(R.string.money) + oitmView.getPrice() + "");
            holder.mRb1.setText(oitmView.getUUX()+context.getResources().getString(R.string.jian));
            holder.mRb2.setText(oitmView.getUUB()+context.getResources().getString(R.string.jian));
            holder.mSv.setMax((int) oitmView.getOnhand());
            holder.mSv.setCurVaule(0);
            holder.mSv.setOnCommitListener(new SelectView.OnCommitListener() {
                @Override
                public void onCurVaule(int vaule) {
                    holder.mSv.setCurVaule(vaule);

                    change(oitmView, vaule + "");
                }
            });
            holder.mSv.setOnCurVauleListener(new SelectView.OnCurVauleListener() {
                @Override
                public void onCurVaule(String vaule) {
                    holder.mSv.setCurVaule(Integer.valueOf(vaule));


                    change(oitmView, vaule);
                }
            });

            if (oitmView.getIsBig() == 0) {
                holder.mRg.check(holder.mRg.getChildAt(0).getId());
                holder.mSv.setValue(Integer.valueOf(oitmView.getUUX()));
            } else if (oitmView.getIsBig() == 1) {
                holder.mRg.check(holder.mRg.getChildAt(1).getId());
                holder.mSv.setValue(Integer.valueOf(oitmView.getUUB()));
            } else if (oitmView.getIsBig() == 1) {
                holder.mRg.clearCheck();
                holder.mSv.setValue(1);
            }
            holder.mRb1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (oitmView.getIsBig() == 0) {
                        holder.mRg.clearCheck();
                        oitmView.setIsBig(2);
                    } else {

                        oitmView.setIsBig(0);
                        holder.mSv.setValue(Integer.valueOf(oitmView.getUUX()));
                    }
                }
            });

            holder.mRb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (oitmView.getIsBig() == 1) {
                        holder.mRg.clearCheck();
                        oitmView.setIsBig(2);
                    } else {

                        oitmView.setIsBig(1);
                        holder.mSv.setValue(Integer.valueOf(oitmView.getUUB()));
                    }
                }
            });

            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                private Intent mIntent;
                @Override
                public void onClick(View v) {
                    mIntent = new Intent(context, ProductQuickSelectActivity.class);
                    mIntent.putExtra("codeBars",oitmView.getCodebars());
                    context.startActivity(mIntent);
                }
            });
        } else {
            TopHolder topHolder = (TopHolder) mholder;
            if (tag == 0) {
                topHolder.mTvDes.setText(context.getResources().getString(R.string.jp));
            } else {

                topHolder.mTvDes.setText(context.getResources().getString(R.string.my_connection));
            }
        }


    }


    public void change(final Connection2 oitmView, String vaule) {

        final GoodsBean.ShopcartBean info = new GoodsBean.ShopcartBean();
        info.setName(oitmView.getItemname());
        info.setPrice(oitmView.getPrice());
        info.setLogo(oitmView.getPicturname());
        info.setQuantity(Integer.valueOf(vaule));
        info.setItemcode(oitmView.getItemcode());
        info.setCodebars(oitmView.getCodebars());
        info.setBrandname(oitmView.getUXyms());
        info.setBrandId(oitmView.getUPp());

        try {
            JSONObject object = new JSONObject();
            object.put("name", info.getName());
            object.put("price", info.getPrice());
            object.put("logo", info.getLogo());
            object.put("itemcode", info.getItemcode());
            object.put("quantity", info.getQuantity() + "");
            object.put("codebars", info.getCodebars());
            object.put("brandname", info.getBrandname());
            object.put("brandId", info.getBrandId());
            object.put("uId", getUser().getUId() + "");

            HttpUtil.getInstacne((Activity) context).post(Urls.shopInsert, Object.class, object, new HttpUtil.OnCallBack<Object>() {
                @Override
                public void success(Object data) {
                    notifyDataSetChanged();
                    zcsShop(info, info.getQuantity());
                    mShopCarFragment. initShopCarData();
                }

                @Override
                public void onError(String msg, int code) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * zcs需要的方法
     *
     * @param info
     * @param count
     */
    private void zcsShop(GoodsBean.ShopcartBean info, int count) {
        ShopCarGoodsBean shopCarGoodsBean = new ShopCarGoodsBean();
        shopCarGoodsBean.setNum(count);
        shopCarGoodsBean.setGoodsCode(info.getItemcode());
        ProductAddShopCarUtils.getInstance().statisShopNum(shopCarGoodsBean);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOP;
        } else {
            return ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas.size() > 0 && mDatas != null) {
            return mDatas.size() + 1;
        }
        return 1;
    }

    private int tag = 0;

    public void setTag(int tag) {
        this.tag = tag;

        notifyDataSetChanged();
    }


    class TopHolder extends RecyclerView.ViewHolder {
        LinearLayout mLl;
        TextView mTvDes;

        public TopHolder(View itemView) {
            super(itemView);

            mLl = (LinearLayout) itemView.findViewById(R.id.ll);
            mTvDes = (TextView) itemView.findViewById(R.id.tv_des);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvProductImg;
        TextView mTvProductName;
        TextView mTvProductPrice;
        RadioButton mRb1;
        RadioButton mRb2;
        RadioGroup mRg;
        TextView mTvProductStock;
        SelectView mSv;
        LinearLayout ll_item;

        public ViewHolder(View itemView) {
            super(itemView);

            mIvProductImg = (ImageView) itemView.findViewById(R.id.iv_product_img);
            mTvProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
            mTvProductPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            mRb1 = (RadioButton) itemView.findViewById(R.id.rb1);
            mRb2 = (RadioButton) itemView.findViewById(R.id.rb2);
            mRg = (RadioGroup) itemView.findViewById(R.id.rg);
            mTvProductStock = (TextView) itemView.findViewById(R.id.tv_product_stock);
            mSv = (SelectView) itemView.findViewById(R.id.sv);
        }
    }
}

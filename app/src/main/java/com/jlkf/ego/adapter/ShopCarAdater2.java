package com.jlkf.ego.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.NumberUtil;
import com.jlkf.ego.utils.ProductAddShopCarUtils;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.utils.ToastUtil;
import com.jlkf.ego.widget.FullyLinearLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24 0024.
 * <p/>
 * 新的购物车适配器
 */

public class ShopCarAdater2 extends RecyclerView.Adapter implements  ShopItemAdatper.OnSelectShopListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final int TYPE_ITEM = 200;   // 普通
    private static final int TYPE_MORE = 300;   // 更多


    private Context mContext;

    public ShopItemAdatper getAdapter() {
        return mAdapter;
    }

    private ShopItemAdatper mAdapter;

    public List<GoodsBean.ShopcartBean> getSelectBean() {
        return selectBean;
    }

    List<GoodsBean.ShopcartBean> selectBean;
    private RelativeLayout tv_total_price;
    private TextView tv_favorites, tv_all_delet;
    private CheckBox checkBox;
    private boolean isALL = false;

    public ShopCarAdater2(Context context, List<GoodsBean> goodsBeanList,
                          RelativeLayout tvTotalPrice, TextView tv_favorites,
                          TextView tv_all_delet,
                          CheckBox checkBox) {
        mContext = context;
        mGoodsBeanList = goodsBeanList;

        if (selectBean == null) {
            selectBean = new ArrayList<>();
        }
        this.tv_total_price = tvTotalPrice;
        this.tv_favorites = tv_favorites;
        this.tv_all_delet = tv_all_delet;
        this.checkBox = checkBox;

        tv_favorites.setOnClickListener(this);
        tv_all_delet.setOnClickListener(this);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleALL();
            }
        });
    }

    /**
     * 设置全部的全选
     */
    private void seleALL() {

        if (isALL) {
            // 如果是已经全选的状态
            isALL = false;
            selectBean.clear();
            count = 0;
            for (int i = 0; i < mGoodsBeanList.size(); i++) {
                mGoodsBeanList.get(i).setChecked(isALL);
                for (int j = 0; j < mGoodsBeanList.get(i).getShopcart().size(); j++) {
                    mGoodsBeanList.get(i).getShopcart().get(j).setChecked(isALL);

                }
            }
        } else {
            isALL = true;
            count = getItemCount() - 1;
            for (int i = 0; i < mGoodsBeanList.size(); i++) {
                mGoodsBeanList.get(i).setChecked(isALL);
                for (int j = 0; j < mGoodsBeanList.get(i).getShopcart().size(); j++) {
                    mGoodsBeanList.get(i).getShopcart().get(j).setChecked(isALL);
                    selectBean.add(mGoodsBeanList.get(i).getShopcart().get(j));
                }
            }
        }
        notifyDataSetChanged();
    }

    public List<GoodsBean> getGoodsBeanList() {
        return mGoodsBeanList;
    }

    List<GoodsBean> mGoodsBeanList;


    public void setOnDeletAllListener(OnDeletAllListener onDeletAllListener) {
        this.onDeletAllListener = onDeletAllListener;
    }

    public OnDeletAllListener onDeletAllListener;

    // 所有的都被删除完了 就会调用该方法
    public interface OnDeletAllListener {
        void onDeletAll();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.holder_shopping_item, parent, false);
            holder = new ItemHolder(view);
        } else if (viewType == TYPE_MORE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.holder_shopping_more, parent, false);
            holder = new MoreHolder(view);
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            final GoodsBean goodsBean = mGoodsBeanList.get(position);

            Log.v("位置", goodsBean.getShopcart().size() + "");

            final ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.mTvDes.setText(goodsBean.getBrandData().getName());
            itemHolder.mRb.setChecked(goodsBean.isChecked());

            itemHolder.mRvItemList.setLayoutManager(new FullyLinearLayoutManager(mContext));
            mAdapter = new ShopItemAdatper(mContext, goodsBean, R.layout.holder_shopping, itemHolder.mRb, ShopCarAdater2.this, position);
            mAdapter.setOnDeletAllListener(new ShopItemAdatper.OnDeletAllListener() {
                @Override
                public void onDeletAll() {
                    if (onDeletAllListener != null) {
                        onDeletAllListener.onDeletAll();
                    }
                }
            });
            mAdapter.setOnSelectShopListener(this);
            itemHolder.mRvItemList.setAdapter(mAdapter);


            itemHolder.mRb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (goodsBean.isChecked()) {

                        setAllChecked(false, goodsBean, mAdapter);
                    } else {
                        setAllChecked(true, goodsBean, mAdapter);
                    }
                }
            });

            if (selectBean.size() == 0){
                isALL = false;
            }


            checkBox.setChecked(isALL);
            if (selectBean != null) {

                updateTotal();

                if (selectBean.size() == 0) {
                    tv_total_price.setBackgroundResource(R.drawable.btn_bg);
                } else {
                    tv_total_price.setBackgroundResource(R.drawable.adress_btn_selector);
                }
            } else {
                tv_total_price.setBackgroundResource(R.drawable.btn_bg);
            }


        } else {
            MoreHolder moreHolder = (MoreHolder) holder;
            moreHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnMoreListener != null) {
                        mOnMoreListener.onMore();
                    }
                }
            });
        }
    }

    public void updateTotal() {
        double s = 0.0;
        for (int i = 0; i < selectBean.size(); i++) {
            s = s + selectBean.get(i).getPrice() * selectBean.get(i).getQuantity();
            Log.v("总价格：", selectBean.get(i).getPrice() + "," + selectBean.get(i).getQuantity());
        }
        ((TextView) tv_total_price.findViewById(R.id.tv_total_price)).setText("¥" + NumberUtil.fomater(s));
    }

    /**
     * 收藏和删除
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_favorites:      // 我的收藏
                Alldelet(0);

                break;
            case R.id.tv_all_delet:
                Alldelet(1);
                break;
        }
    }

    private void Alldelet(int tag) {
        if (selectBean != null && selectBean.size() > 0) {

            if (tag == 0) {
                show();
            } else {
                String itemCode = getItemCode(selectBean, 1);
                showDialog(itemCode);
                Log.d("删除id", itemCode);
            }
        } else if (selectBean == null || selectBean.size() == 0) {
            ToastUti.show(mContext.getResources()
                    .getString(R.string.xzsp));
        }
    }

    private void show() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.delet_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(mContext)
                .setView(inflate)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_radius_white);

        ((TextView) inflate.findViewById(R.id.tv_title_dialog)).setText(mContext.getResources()
        .getString(R.string.sc_shop));
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String itemCode = getItemCode(selectBean, 0);
                loadConnection(itemCode);


                Log.d("收藏id", itemCode);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * 删除购物车dialog
     *
     * @param itemCode
     */
    private void showDialog(final String itemCode) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.delet_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(mContext)
                .setView(inflate)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_radius_white);

        ((TextView) inflate.findViewById(R.id.tv_title_dialog)).setText(mContext.getResources().getString(R.string.is_delet_shop));
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleGoods(itemCode);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * 删除购物车
     *
     * @param itemCode
     */
    private void deleGoods(String itemCode) {

        //    删除本地的数据
        //   GoodsUtil.getInstance().delet(mDatas.get(location));

        // 删除网络数据
        Map<String, String> map = new HashMap<>();
        map.put("sId", itemCode + "");
        HttpUtil.getInstacne((Activity) mContext).get(Urls.delete, JSONObject.class, map, new HttpUtil.OnCallBack() {
            @Override
            public void success(Object data) {
                for (int i = selectBean.size() - 1; i >= 0; i--) {
                    int baseposition = selectBean.get(i).getBaseposition();
                    mGoodsBeanList.get(baseposition).getShopcart().remove(selectBean.get(i));

                    // 如果子集合删除后 刚好没有数据了  就删除父类集合中的那条item
                    if (mGoodsBeanList.get(baseposition).getShopcart().size() == 0) {
                        mGoodsBeanList.remove(mGoodsBeanList.get(baseposition));
                    }
                    zcsShop(selectBean.get(i), 0);
                }
                selectBean.clear();

                if (getGoodsBeanList().size() == 0) {
                    if (onDeletAllListener != null) {
                        onDeletAllListener.onDeletAll();
                    }
                }


                notifyDataSetChanged();
                ToastUti.show("删除成功");
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
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


    /**
     * 收藏
     *
     * @param s
     */
    private void loadConnection(String s) {
        JSONObject object = new JSONObject();
        try {
            object.put("uId", MyApplication.getmUserBean().getUId());
            object.put("type", 1);
            object.put("itemCode", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.getInstacne((Activity) mContext).post(Urls.coller, String.class, object, new HttpUtil.OnCallBack<String>() {
            @Override
            public void success(String data) {
//                FavoritesDailog favoritesDailog = new FavoritesDailog(mContext);
//                favoritesDailog.show();
                ToastUtil.show(R.layout.dialog_favorites);

                isALL = true;
                selectBean.clear();

                seleALL();

            }

            @Override
            public void onError(String msg, int code) {


                notifyDataSetChanged();
            }
        });
    }


    private String getItemCode(List<GoodsBean.ShopcartBean> productInfos, int tag) {
        String mS1 = "";
        String s = "";
        for (int i = 0; i < productInfos.size(); i++) {
            if (i == 0) {
                if (tag == 0) {

                    s = productInfos.get(i).getItemcode();
                } else {
                    s = productInfos.get(i).getSId() + "";

                }
            } else {
                if (tag == 0) {

                    s = "," + productInfos.get(i).getItemcode();
                } else {
                    s = "," + productInfos.get(i).getSId();

                }
            }
            mS1 = mS1 + s;

        }
        return mS1;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }


    public void setOnMoreListener(onMoreListener onMoreListener) {
        mOnMoreListener = onMoreListener;
    }

    public onMoreListener mOnMoreListener;

    @Override
    public void onSelectShop(int position, GoodsBean.ShopcartBean shopcartBeen, int baseposition) {

        notifyDataSetChanged();
    }


    public interface onMoreListener {
        void onMore();
    }

//    /**
//     * 设置全选的状态
//     */
//    public void setAllChebox() {
//
//        notifyDataSetChanged();
//    }

    private int count = 0;

    /**
     * 设置全选
     *
     * @param b
     * @param goodsBean
     */
    private void setAllChecked(boolean b, GoodsBean goodsBean, ShopItemAdatper adapter) {
        goodsBean.setChecked(b);

        if (b) {
            for (int i = 0; i < goodsBean.getShopcart().size(); i++) {
                if (!goodsBean.getShopcart().get(i).isChecked()) {
                    selectBean.add(goodsBean.getShopcart().get(i));
                }
            }
            count++;

        } else {
            selectBean.removeAll(goodsBean.getShopcart());
            count--;
        }

        for (int i = 0; i < goodsBean.getShopcart().size(); i++) {
            goodsBean.getShopcart().get(i).setChecked(b);
        }

        if (count == getItemCount() - 1) {
            isALL = true;
        } else {
            isALL = false;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_MORE;

        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (mGoodsBeanList != null && mGoodsBeanList.size() > 0) {
            return mGoodsBeanList.size() + 1;// 加1是因为有两种类型数据
        }
        return 0;
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_des)
        TextView mTvDes;

        @BindView(R.id.rv_item_list)
        RecyclerView mRvItemList;

        @BindView(R.id.rb)
        CheckBox mRb;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

    class MoreHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_more;
        Button btn;

        public MoreHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rl_more = (RelativeLayout) itemView.findViewById(R.id.rl_more);
            btn = (Button) itemView.findViewById(R.id.btn);

        }
    }
}

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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.NumberUtil;
import com.jlkf.ego.utils.ProductAddShopCarUtils;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.widget.SelectView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jlkf.ego.utils.ShardeUtil.getUser;

/**
 * Created by Administrator on 2017/8/24 0024.
 * <p/>
 * 商品里面的二级item
 */

public class ShopItemAdatper extends RecyclerView.Adapter {

    private Context mContext;
    private int mTatol;
    private GoodsBean.OitmViewBean mOitmView;
    private ViewHolder mViewHolder;

    public List<GoodsBean.ShopcartBean> getShopcartBeen() {
        return mShopcartBeen;
    }

    public void setShopcartBeen(List<GoodsBean.ShopcartBean> shopcartBeen) {
        mShopcartBeen = shopcartBeen;
    }

    List<GoodsBean.ShopcartBean> mShopcartBeen;
    private int layoutid;
    private CheckBox mCheckBox;
    private GoodsBean goodsBean;
    private ShopCarAdater2 mShopCarAdater2;
    private int basePosition;


    public ShopItemAdatper(Context context, GoodsBean goodsBean, int layoutid, CheckBox rb, ShopCarAdater2 shopCarAdater2, int position) {
        this.layoutid = layoutid;
        mContext = context;
        mShopcartBeen = new ArrayList<>();

        mShopcartBeen.addAll(goodsBean.getShopcart());
        this.goodsBean = goodsBean;
        this.mShopCarAdater2 = shopCarAdater2;
        mCheckBox = rb;
        basePosition = position;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(layoutid, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        try {


            mViewHolder = (ViewHolder) holder;
            final GoodsBean.ShopcartBean shopcartBean = mShopcartBeen.get(position);

            shopcartBean.setBaseposition(basePosition);
            // 设置编辑
            isShowEdit(shopcartBean, mViewHolder);

            // 设置选择
            mViewHolder.rb.setChecked(shopcartBean.isChecked());

            // 设置列表信息
            mViewHolder.tvTitle.setText(shopcartBean.getName());
            mViewHolder.tvShoppingTotal.setText("¥" + NumberUtil.fomater(shopcartBean.getPrice() * shopcartBean.getQuantity()));
            mViewHolder.mTvPrice.setText(mContext.getResources().getString(R.string.dj) + mContext.getResources().getString(R.string.money) + NumberUtil.fomater(shopcartBean.getPrice()) + "");
            mViewHolder.mTvPrice1.setText(mContext.getResources().getString(R.string.money) + NumberUtil.fomater(shopcartBean.getPrice()) + "");
            mViewHolder.tvNumber.setText("x" + shopcartBean.getQuantity() + "");
            Glide.with(mContext).load(shopcartBean.getLogo()).into(mViewHolder.shoppingImg);


            for (int i = 0; i < shopcartBean.getOitmView().size(); i++) {
                if (shopcartBean.getOitmView().get(i).getArea().equals(MyApplication.getmUserBean().getArea())) {
                    mOitmView = shopcartBean.getOitmView().get(i);
                }
            }


            mViewHolder.tv_kc.setText(mOitmView.getOnhand() + "");
            if (mOitmView.getUUB() != null) {
                mViewHolder.rb1.setText(Integer.valueOf(mOitmView.getUUX()) + "");
            } else {
                mViewHolder.rb1.setText(0 + "");
            }
            if (mOitmView.getUUX() != null) {
                mViewHolder.rb2.setText(Integer.valueOf(mOitmView.getUUB()) + "");
            } else {
                mViewHolder.rb2.setText(0 + "");
            }

            if (shopcartBean.getIsSamll() == 1) {
                mViewHolder.iv1.setImageResource(R.mipmap.icon_large_package_brown);
                mViewHolder.rb1.setTextColor(mContext.getResources().getColor(R.color.bg_brown));
                mViewHolder.rb2.setTextColor(mContext.getResources().getColor(R.color.black));
                mViewHolder.iv2.setImageResource(R.mipmap.icon_small_package);
                mViewHolder.sv.setValue(Integer.valueOf(mOitmView.getUUX()));


            } else if (shopcartBean.getIsSamll() == 2) {
                mViewHolder.rb1.setTextColor(mContext.getResources().getColor(R.color.black));
                mViewHolder.rb2.setTextColor(mContext.getResources().getColor(R.color.bg_brown));
                mViewHolder.iv2.setImageResource(R.mipmap.icon_small_package_brown);
                mViewHolder.iv1.setImageResource(R.mipmap.icon_large_package);
                mViewHolder.sv.setValue(Integer.valueOf(mOitmView.getUUB()));

            } else if (shopcartBean.getIsSamll() == 0) {
                mViewHolder.iv1.setImageResource(R.mipmap.icon_large_package);
                mViewHolder.rb1.setTextColor(mContext.getResources().getColor(R.color.black));
                mViewHolder.rb2.setTextColor(mContext.getResources().getColor(R.color.black));
                mViewHolder.iv2.setImageResource(R.mipmap.icon_small_package);
                mViewHolder.sv.setValue(1);
            }

            mViewHolder.ll_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shopcartBean.getIsSamll() == 1) {
                        shopcartBean.setIsSamll(0);
                    } else {

                        shopcartBean.setIsSamll(1);
                    }
                    notifyDataSetChanged();

                }
            });

            mViewHolder.ll_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shopcartBean.getIsSamll() == 2) {

                        shopcartBean.setIsSamll(0);
                    } else {

                        shopcartBean.setIsSamll(2);
                    }
                    notifyDataSetChanged();


                }
            });

            mViewHolder.sv.setMax((int)mOitmView.getOnhand());
            mViewHolder.sv.setCurVaule(shopcartBean.getQuantity());


            mViewHolder.rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setChebox(shopcartBean, position);
                }
            });


            mViewHolder.ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    showDialog(0, shopcartBean);

                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setOnDeletAllListener(OnDeletAllListener onDeletAllListener) {
        this.onDeletAllListener = onDeletAllListener;
    }

    public OnDeletAllListener onDeletAllListener;

    // 所有的都被删除完了 就会调用该方法
    public interface OnDeletAllListener {
        void onDeletAll();
    }

    /**
     * 删除购物车dialog
     *
     * @param shopcartBean
     */
    private void showDialog(final int tag, final GoodsBean.ShopcartBean shopcartBean) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.delet_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(mContext)
                .setView(inflate)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_radius_white);

        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == 1) {
                    shopcartBean.setQuantity(1);
                    change(shopcartBean);
                }
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                deleGoods(shopcartBean);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 删除购物车
     *
     * @param shopcartBean
     */
    private void deleGoods(final GoodsBean.ShopcartBean shopcartBean) {

        //    删除本地的数据
        //   GoodsUtil.getInstance().delet(mDatas.get(location));

        // 删除网络数据
        Map<String, String> map = new HashMap<>();
        map.put("sId", shopcartBean.getSId() + "");
        HttpUtil.getInstacne((Activity) mContext).get(Urls.delete, JSONObject.class, map, new HttpUtil.OnCallBack() {
            @Override
            public void success(Object data) {

                // 判断删除的是不是被加入到编辑中的  如果是 就从编辑里面删除 同时刷新全选的状态
                if (mShopCarAdater2.getSelectBean() != null) {
                    // 遍历被选中的所有item  如果有就删除选中的
                    for (int i = 0; i < mShopCarAdater2.getSelectBean().size(); i++) {
                        if (mShopCarAdater2.getSelectBean().get(i) == shopcartBean) {
                            mShopCarAdater2.getSelectBean().remove(shopcartBean);
                            mTatol--;

                            ToastUti.show("删除被选中的");
                        }
                    }
                }
                zcsShop(shopcartBean, 0);
                // 不管是不是被选中的都需要 从子集合中删除
                mShopCarAdater2.getGoodsBeanList().get(basePosition).getShopcart().remove(shopcartBean);


                // 如果子集合删除后 刚好没有数据了  就删除父类集合中的那条item
                if (mShopCarAdater2.getGoodsBeanList().get(basePosition).getShopcart().size() == 0) {
                    mShopCarAdater2.getGoodsBeanList().remove(mShopCarAdater2.getGoodsBeanList().get(basePosition));
                }

                if (mShopCarAdater2.getGoodsBeanList().size() == 0) {
                    if (onDeletAllListener != null) {
                        onDeletAllListener.onDeletAll();
                    }
                }
                // 刷新全选的状态
                if (mOnSelectListener != null) {
                    mOnSelectListener.onSelect(mTatol, basePosition);
                }
                ToastUti.show("删除成功");
            }

            @Override
            public void onError(String msg, int code) {

            }
        });

    }


    /**
     * 设置chebox 选择状态
     *
     * @param shopcartBean
     * @param position
     */
    private void setChebox(GoodsBean.ShopcartBean shopcartBean, int position) {

        if (!shopcartBean.isChecked()) {
            mTatol++;
            shopcartBean.setChecked(true);
            mShopCarAdater2.getSelectBean().add(shopcartBean);
        } else {
            mTatol--;
            mShopCarAdater2.getSelectBean().remove(shopcartBean);
            shopcartBean.setChecked(false);
        }
//        setAllChebox(mTatol);
        if (mOnSelectListener != null) {
            mOnSelectListener.onSelect(mTatol, basePosition);
        }
        if (mOnSelectShopListener != null) {
            mOnSelectShopListener.onSelectShop(position, shopcartBean, basePosition);
        }
        notifyDataSetChanged();

    }

    public void setOnSelectListener(onSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public onSelectListener mOnSelectListener;


    public interface onSelectListener {
        void onSelect(int total, int position);
    }

    public void setOnSelectShopListener(OnSelectShopListener onSelectShopListener) {
        mOnSelectShopListener = onSelectShopListener;
    }

    public OnSelectShopListener mOnSelectShopListener;

    /**
     * 被选中的商品
     */
    public interface OnSelectShopListener {
        void onSelectShop(int position, GoodsBean.ShopcartBean shopcartBeen, int BasePosition);
    }


    public void change(final GoodsBean.ShopcartBean info) {

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

            HttpUtil.getInstacne((Activity) mContext).post(Urls.shopInsert, Object.class, object, new HttpUtil.OnCallBack<Object>() {
                @Override
                public void success(Object data) {
                    Log.v("修改：", "-------");

                    mShopCarAdater2.updateTotal();
                    notifyDataSetChanged();
                    zcsShop(info, info.getQuantity());
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

    /**
     * 设置编辑状态
     *
     * @param shoppingBean
     * @param viewHolder
     */
    public void isShowEdit(GoodsBean.ShopcartBean shoppingBean, ViewHolder viewHolder) {
        if (shoppingBean.isEditType()) {
            viewHolder.rlAdpaterEdit.setVisibility(View.GONE);
            viewHolder.rlAdpaterEdited.setVisibility(View.VISIBLE);
        } else {
            viewHolder.rlAdpaterEdit.setVisibility(View.VISIBLE);
            viewHolder.rlAdpaterEdited.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        if (mShopcartBeen != null && mShopcartBeen.size() > 0) {
            return mShopcartBeen.size();
        }
        return 0;
    }

    /**
     * 设置全选的状态
     *
     * @param tatol
     */
    public void setAllChebox(int tatol) {
        if (tatol == mShopcartBeen.size()) {
            mCheckBox.setChecked(true);
            goodsBean.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
            goodsBean.setChecked(false);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shopping_img)
        ImageView shoppingImg;
        @BindView(R.id.iv1)
        ImageView iv1;
        @BindView(R.id.iv2)
        ImageView iv2;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_shopping_total)
        TextView tvShoppingTotal;
        @BindView(R.id.tv_number)
        TextView tvNumber;

        @BindView(R.id.rl_adpater_edit)
        RelativeLayout rlAdpaterEdit;
        @BindView(R.id.rl_adpater_edited)
        RelativeLayout rlAdpaterEdited;

        @BindView(R.id.rb1)
        TextView rb1;
        @BindView(R.id.rb2)
        TextView rb2;
        @BindView(R.id.rg)
        LinearLayout rg;

        @BindView(R.id.rb)
        CheckBox rb;

        @BindView(R.id.ll)
        LinearLayout ll;
        @BindView(R.id.ll_1)
        LinearLayout ll_1;
        @BindView(R.id.ll_2)
        LinearLayout ll_2;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.tv_price1)
        TextView mTvPrice1;
        @BindView(R.id.tv_kc)
        TextView tv_kc;
        @BindView(R.id.sv)
        SelectView sv;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSubit();
                }
            });


            sv.setOnCurVauleListener(new SelectView.OnCurVauleListener() {
                @Override
                public void onCurVaule(String vaule) {
                    GoodsBean.ShopcartBean shopcartBean = mShopcartBeen.get(getLayoutPosition());
                    if (Integer.valueOf(vaule) == 0) {
                        showDialog(1, shopcartBean);
                    } else {

                        shopcartBean.setQuantity(Integer.valueOf(vaule));

                        change(shopcartBean);
                    }
                }
            });

            sv.setOnCommitListener(new SelectView.OnCommitListener() {
                @Override
                public void onCurVaule(int vaule) {
                    GoodsBean.ShopcartBean shopcartBean = mShopcartBeen.get(getLayoutPosition());
                    if (vaule == 0) {
                        showDialog(1, shopcartBean);
                    } else {

                        shopcartBean.setQuantity(vaule);
                        change(shopcartBean);
                    }
                }
            });
        }


        /**
         * 改变编辑状态
         */
        private void setSubit() {
            GoodsBean.ShopcartBean productInfo = mShopcartBeen.get(getLayoutPosition());
            if (productInfo.isEditType()) {
                productInfo.setEditType(false);
            } else {
                productInfo.setEditType(true);
            }
            notifyDataSetChanged();
        }
    }

}

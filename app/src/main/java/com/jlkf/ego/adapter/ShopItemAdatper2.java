package com.jlkf.ego.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.BaseActivity;
import com.jlkf.ego.activity.OrderActivity;
import com.jlkf.ego.activity.PhotoActivity;
import com.jlkf.ego.activity.ShopCarItemActivity;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.ComfimOrderBean;
import com.jlkf.ego.bean.ConfimOrderBean;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.newpage.activity.NewOrderActivity;
import com.jlkf.ego.newpage.bean.NewConfimOrderBean;
import com.jlkf.ego.newpage.utils.ApiManager;
import com.jlkf.ego.newpage.utils.HttpUtils;
import com.jlkf.ego.utils.NumberUtil;
import com.jlkf.ego.utils.ProductAddShopCarUtils;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.utils.ToastUtil;
import com.jlkf.ego.widget.SelectView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

import static com.jlkf.ego.utils.ShardeUtil.getUser;

/**
 * Created by Administrator on 2017/10/23.
 */
public class ShopItemAdatper2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity mContext;
    private GoodsBean.OitmViewBean mOitmView;

    public ShopItemAdatper2(Context context, List<GoodsBean.ShopcartBean> shopcartBeen) {
        mContext = (Activity) context;
        mShopcartBeen = shopcartBeen;

        selectBean = new ArrayList<>();
    }

    private List<GoodsBean.ShopcartBean> mShopcartBeen;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_ITEM) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.holder_shopping, parent, false);
            holder = new ShopItemViewHolder(inflate);
        } else if (viewType == TYPE_MORE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.holder_shopping_more, parent, false);
            holder = new MoreHolder(view);
        }
        return holder;
    }

    private static final int TYPE_ITEM = 200;   // 普通
    private static final int TYPE_MORE = 300;   // 更多

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_MORE;

        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        if (getItemViewType(position) == TYPE_ITEM) {
            try {
                final ShopItemViewHolder mViewHolder = (ShopItemViewHolder) viewHolder;
                final GoodsBean.ShopcartBean shopcartBean = mShopcartBeen.get(position);
                for (int i = 0; i < shopcartBean.getOitmView().size(); i++) {
                    if (shopcartBean.getOitmView().get(i).getArea().equals(MyApplication.getmUserBean().getArea())) {
                        mOitmView = shopcartBean.getOitmView().get(i);
                    }
                }

                // 设置编辑
                isShowEdit(shopcartBean, mViewHolder);


                // 设置列表信息
                mViewHolder.tvTitle.setText(shopcartBean.getName());


                mViewHolder.tvShoppingTotal.setText(mContext.getResources().getString(R.string.money) + NumberUtil.fomater(shopcartBean.getPrice() * shopcartBean.getQuantity()));
                mViewHolder.mTvPrice.setText(mContext.getResources().getString(R.string.dj) + mContext.getResources().getString(R.string.money) + NumberUtil.fomater(shopcartBean.getPrice()) + "");
                mViewHolder.mTvPrice1.setText(mContext.getResources().getString(R.string.money) + NumberUtil.fomater(shopcartBean.getPrice()) + "");
                mViewHolder.tvNumber.setText("x" + shopcartBean.getQuantity() + "");
                Glide.with(mContext).load(shopcartBean.getLogo()).into(mViewHolder.shoppingImg);


//                setBag(mViewHolder, shopcartBean.getIsSamll(), shopcartBean);

//                mViewHolder.tv_kc.setText(mOitmView.getOnhand() + "");
                mViewHolder.sv.setMax((int) mOitmView.getOnhand());
                mViewHolder.sv.setCurVaule(shopcartBean.getQuantity());

                if (mOitmView.getUUB() != null) {
                    mViewHolder.rb1.setText(Integer.valueOf(mOitmView.getUUX()) + mContext.getResources().getString(R.string.jian));
                } else {
                    mViewHolder.rb1.setText(0 + mContext.getResources().getString(R.string.jian));
                }
                if (mOitmView.getUUX() != null) {
                    mViewHolder.rb2.setText(Integer.valueOf(mOitmView.getUUB()) + mContext.getResources().getString(R.string.jian));
                } else {
                    mViewHolder.rb2.setText(0 + mContext.getResources().getString(R.string.jian));
                }


                mViewHolder.rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("--------------", "aaa");
                        GoodsBean.ShopcartBean productInfo = mShopcartBeen.get(position);

                        if (productInfo.isEditType()) {
                            productInfo.setEditType(false);
                        } else {
                            productInfo.setEditType(true);
                        }

                        isShowEdit(productInfo, mViewHolder);
                    }
                });


                /**
                 * 大包
                 */
                mViewHolder.ll_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                    shopcartBean.setQuantity(shopcartBean.getQuantity() + Integer.parseInt(mOitmView.getUUX()));
                        if (shopcartBean.getIsSamll() == 1) {

                            shopcartBean.setIsSamll(0);
                        } else {
                            shopcartBean.setIsSamll(1);

                        }
//                        change(shopcartBean);
                        setBag(mViewHolder, shopcartBean.getIsSamll(), shopcartBean);

                    }
                });

                /**
                 * 小包
                 */
                mViewHolder.ll_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                    shopcartBean.setQuantity(shopcartBean.getQuantity() + Integer.parseInt(mOitmView.getUUB()));
                        if (shopcartBean.getIsSamll() == 2) {

                            shopcartBean.setIsSamll(0);
                        } else {
                            shopcartBean.setIsSamll(2);

                        }

//                        change(shopcartBean);
                        setBag(mViewHolder, shopcartBean.getIsSamll(), shopcartBean);


                    }
                });


                mViewHolder.sv.setOnCurVauleListener(new SelectView.OnCurVauleListener() {
                    @Override
                    public void onCurVaule(String vaule) {
                        GoodsBean.ShopcartBean shopcartBean = mShopcartBeen.get(position);
                        if (Integer.valueOf(vaule) == 0) {
                            showDialog(1, shopcartBean);
                        } else {

                            shopcartBean.setQuantity(Integer.valueOf(vaule));

                            change(shopcartBean);
                        }
                    }
                });

                mViewHolder.sv.setOnCommitListener(new SelectView.OnCommitListener() {
                    @Override
                    public void onCurVaule(int vaule) {
                        GoodsBean.ShopcartBean shopcartBean = mShopcartBeen.get(position);
                        if (vaule == 0) {
                            showDialog(1, shopcartBean);
                        } else {

                            shopcartBean.setQuantity(vaule);
                            change(shopcartBean);
                        }
                    }
                });
                mViewHolder.rl.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showDialog(0, shopcartBean);
                        return true;
                    }
                });

                mViewHolder.ll.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        showDialog(0, shopcartBean);

                        return true;
                    }
                });


                mViewHolder.shoppingImg.setOnClickListener(new View.OnClickListener() {

                    private Intent mIntent;

                    @Override
                    public void onClick(View v) {
                        mIntent = new Intent(mContext, PhotoActivity.class);
                        mIntent.putExtra("data", (ArrayList) mShopcartBeen);
                        mIntent.putExtra("pos", position);
                        mContext.startActivity(mIntent);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MoreHolder moreHolder = (MoreHolder) viewHolder;
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

    public void setOnMoreListener(onMoreListener onMoreListener) {
        mOnMoreListener = onMoreListener;
    }

    public onMoreListener mOnMoreListener;

    public interface onMoreListener {
        void onMore();
    }

    private int mTatol;
    List<GoodsBean.ShopcartBean> selectBean;


    public void updateTotal() {
        double s = 0.0;
        for (int i = 0; i < selectBean.size(); i++) {
            s = s + selectBean.get(i).getPrice() * selectBean.get(i).getQuantity();
            Log.v("总价格：", selectBean.get(i).getPrice() + "," + selectBean.get(i).getQuantity());
        }

        if (tvTotalPrice != null) {
            tvTotalPrice.setText(getString(R.string.money) + NumberUtil.fomater(s));
        }

        if (selectBean != null) {
            if (selectBean.size() == 0) {
                mRlSettlement.setBackgroundResource(R.drawable.btn_bg);
            } else {
                mRlSettlement.setBackgroundResource(R.drawable.adress_btn_selector);
            }
        } else {
            mRlSettlement.setBackgroundResource(R.drawable.btn_bg);
        }
        Log.v("总价格：", s + "");
    }

    public String getString(int id) {
        return mContext.getResources().getString(id);
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
                deleGoods2(itemCode);
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
    private void deleGoods2(String itemCode) {

        //    删除本地的数据
        //   GoodsUtil.getInstance().delet(mDatas.get(location));

        // 删除网络数据
        Map<String, String> map = new HashMap<>();
        map.put("sId", itemCode + "");
        HttpUtil.getInstacne((Activity) mContext).get(Urls.delete, JSONObject.class, map, new HttpUtil.OnCallBack() {
            @Override
            public void success(Object data) {
                for (int i = selectBean.size() - 1; i >= 0; i--) {
                    zcsShop(selectBean.get(i), 0);
                    mShopcartBeen.remove(selectBean.get(i));
                    mTatol--;
                }
                selectBean.clear();


                updateTotal();

                // 如果子集合删除后 刚好没有数据了  就删除父类集合中的那条item
                if (mShopcartBeen.size() == 0) {
                    Intent intent = new Intent();
                    mContext.setResult(ShopCarItemActivity.BACK, intent);
                    ((Activity) mContext).finish();
                }


                notifyDataSetChanged();
//                ToastUti.show("删除成功");
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
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
                zcsShop(shopcartBean, 0);

                mShopcartBeen.remove(shopcartBean);


                // 遍历被选中的所有item  如果有就删除选中的
                for (int i = 0; i < selectBean.size(); i++) {
                    if (selectBean.get(i) == shopcartBean) {

                        selectBean.remove(shopcartBean);
                        mTatol--;
//                        ToastUti.show("删除被选中的");
                    }
                }


                updateTotal();
                notifyDataSetChanged();
                if (mShopcartBeen.size() == 0) {
                    Intent intent = new Intent();
                    mContext.setResult(ShopCarItemActivity.BACK, intent);
                    ((Activity) mContext).finish();
                }
//                ToastUti.show("删除成功");
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
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
                    zcsShop(info, info.getQuantity());
                    updateTotal();
                    notifyDataSetChanged();


                }

                @Override
                public void onError(String msg, int code) {
                    ToastUtil.show(msg);

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

    private void setBag(ShopItemViewHolder mViewHolder, int isSamll, GoodsBean.ShopcartBean shopcartBean) {
        if (isSamll == 1) {
            mViewHolder.iv1.setImageResource(R.mipmap.icon_large_package_brown);
            mViewHolder.rb1.setTextColor(mContext.getResources().getColor(R.color.bg_brown));
            mViewHolder.rb2.setTextColor(mContext.getResources().getColor(R.color.black));
            mViewHolder.iv2.setImageResource(R.mipmap.icon_small_package);
            mViewHolder.sv.setValue(Integer.valueOf(mOitmView.getUUX()));


        } else if (isSamll == 2) {
            mViewHolder.rb1.setTextColor(mContext.getResources().getColor(R.color.black));
            mViewHolder.rb2.setTextColor(mContext.getResources().getColor(R.color.bg_brown));
            mViewHolder.iv2.setImageResource(R.mipmap.icon_small_package_brown);
            mViewHolder.iv1.setImageResource(R.mipmap.icon_large_package);
            mViewHolder.sv.setValue(Integer.valueOf(mOitmView.getUUB()));

        } else if (isSamll == 0) {
            mViewHolder.iv1.setImageResource(R.mipmap.icon_large_package);
            mViewHolder.rb1.setTextColor(mContext.getResources().getColor(R.color.black));
            mViewHolder.rb2.setTextColor(mContext.getResources().getColor(R.color.black));
            mViewHolder.iv2.setImageResource(R.mipmap.icon_small_package);
            mViewHolder.sv.setValue(1);
        }
    }

    /**
     * 设置编辑状态
     *
     * @param shoppingBean
     * @param viewHolder
     */
    public void isShowEdit(GoodsBean.ShopcartBean shoppingBean, ShopItemViewHolder viewHolder) {
        if (shoppingBean.isEditType()) {     // 编辑
            viewHolder.rlAdpaterEdit.setVisibility(View.GONE);
            viewHolder.rlAdpaterEdited.setVisibility(View.VISIBLE);
        } else {                            // 普通
            viewHolder.rlAdpaterEdit.setVisibility(View.VISIBLE);
            viewHolder.rlAdpaterEdited.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (mShopcartBeen != null && mShopcartBeen.size() != 0) {
            return mShopcartBeen.size() + 1;
        }
        return 0;
    }

    private TextView tvTotalPrice;

    public void setView(TextView tvTotalPrice) {
        this.tvTotalPrice = tvTotalPrice;

    }

    private RelativeLayout mRlSettlement;

    public void setTotalView(RelativeLayout mRlSettlement) {
        this.mRlSettlement = mRlSettlement;

        this.mRlSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToClear();
            }
        });
    }

    public void goToClear(final int type) {
        com.alibaba.fastjson.JSONObject object = new com.alibaba.fastjson.JSONObject();
        int size = selectBean.size();
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            GoodsBean.ShopcartBean bean = selectBean.get(i);
            if (bean.isChecked()) {
                builder.append(bean.getSId()).append(",");
            }
        }
        if (builder.length() < 1) {
            ToastUtil.show(mContext.getResources().getString(R.string.xzsp));
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("uId", MyApplication.getmUserBean().getUId() + "");
        map.put("sId", builder.substring(0, builder.length() - 1));
        ApiManager.settlement(map, mContext, new HttpUtils.OnCallBack() {
            @Override
            public void success(String response) {
                ((BaseActivity) mContext).setLoading(false);
                NewConfimOrderBean bean = JSON.parseObject(response, NewConfimOrderBean.class);
                Intent intent = new Intent(mContext, NewOrderActivity.class);
                intent.putExtra("orderInfo", bean);
                intent.putExtra("sId", builder.substring(0, builder.length() - 1));
                mContext.startActivity(intent);
            }

            @Override
            public void onError(String msg) {
                ((BaseActivity) mContext).setLoading(false);
                ToastUti.show(msg);
            }
        });

        /*object.put("sId", builder.substring(0, builder.length() - 1));
        object.put("uId", MyApplication.getmUserBean().getUId() + "");
        ((BaseActivity) mContext).setLoading(true);
        ApiManager.goSettlement(MyApplication.getmUserBean().getUId() + "",
                builder.substring(0, builder.length() - 1), MyApplication.getmUserBean().getArea(), mContext, new HttpUtils.OnCallBack() {
                    @Override
                    public void success(String response) {
                        ((BaseActivity) mContext).setLoading(false);
                        ConfimOrderBean.DataBean data = JSON.parseObject(response, ConfimOrderBean.DataBean.class);
                        ConfimOrderBean bean = new ConfimOrderBean();
                        bean.setData(new ArrayList<ConfimOrderBean.DataBean>());
                        bean.getData().add(data);
                        if (type == 1) {
                            EventBus.getDefault().post(bean);
                            return;
                        }

                        Intent intent = new Intent(mContext, OrderActivity.class);
                        intent.putExtra("orderInfo", bean);
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onError(String msg) {
                        ((BaseActivity) mContext).setLoading(false);
                        ToastUti.show(msg);
                    }
                });*/
        /*HttpUtil.getInstacne(mContext).post2(Urls.goSettlement, ConfimOrderBean.class, object.toString(), new HttpUtil.OnCallBack<ConfimOrderBean>() {
            @Override
            public void success(ConfimOrderBean data) {
                Intent intent = new Intent(mContext, OrderActivity.class);
                intent.putExtra("orderInfo", data);
                mContext.startActivity(intent);
//                UIHelper.startOrer(getActivity(), "order");
            }

            @Override
            public void onError(String msg, int code) {
                ToastUti.show(msg);
            }
        });*/
    }

    private void goToClear() {
        goToClear(0);
    }

    private CheckBox rbAll;

    public void setAll(CheckBox rbAll) {
        this.rbAll = rbAll;
        this.rbAll.setChecked(true);
        seleALL();
    }


    /**
     * 设置全部的全选
     */
    private void seleALL() {

        if (!rbAll.isChecked()) {
            // 如果是已经全选的状态
            selectBean.clear();
            mTatol = 0;
            for (int j = 0; j < mShopcartBeen.size(); j++) {
                mShopcartBeen.get(j).setChecked(false);
            }
            rbAll.setChecked(false);
        } else {
            rbAll.setChecked(true);
            mTatol = getItemCount();
            for (int j = 0; j < mShopcartBeen.size(); j++) {
                mShopcartBeen.get(j).setChecked(true);
                selectBean.add(mShopcartBeen.get(j));
            }
        }

        updateTotal();
        notifyDataSetChanged();
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
            }

            @Override
            public void onError(String msg, int code) {


//                ToastUtil.show(msg);
//                notifyDataSetChanged();
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

    private TextView tvFavorites;

    public void setsc(TextView tvFavorites) {
        this.tvFavorites = tvFavorites;

        tvFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alldelet(0);     // 我的收藏
            }
        });
    }


    private TextView tvAllDelet;

    public void setshanchu(TextView tvAllDelet) {
        this.tvAllDelet = tvAllDelet;

        tvAllDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alldelet(1);     // 删除
            }
        });
    }


    public class ShopItemViewHolder extends RecyclerView.ViewHolder {
        ImageView shoppingImg;
        ImageView iv1;
        ImageView iv2;
        TextView tvTitle;
        TextView tvContent;
        TextView tvShoppingTotal;
        TextView tvNumber;
        RelativeLayout rlAdpaterEdit;
        RelativeLayout rlAdpaterEdited;
        TextView rb1;
        TextView rb2;

        RelativeLayout rl;
        LinearLayout ll;
        LinearLayout ll_1;
        LinearLayout ll_2;
        TextView mTvPrice;
        TextView mTvPrice1;
        TextView tv_kc;
        SelectView sv;

        public ShopItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            shoppingImg = (ImageView) itemView.findViewById(R.id.shopping_img);
            iv1 = (ImageView) itemView.findViewById(R.id.iv1);
            iv2 = (ImageView) itemView.findViewById(R.id.iv2);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvShoppingTotal = (TextView) itemView.findViewById(R.id.tv_shopping_total);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            rlAdpaterEdit = (RelativeLayout) itemView.findViewById(R.id.rl_adpater_edit);
            rlAdpaterEdited = (RelativeLayout) itemView.findViewById(R.id.rl_adpater_edited);
            rb1 = (TextView) itemView.findViewById(R.id.rb1);
            rb2 = (TextView) itemView.findViewById(R.id.rb2);
            rl = (RelativeLayout) itemView.findViewById(R.id.shop_item_rl);
            ll = (LinearLayout) itemView.findViewById(R.id.ll);
            ll_1 = (LinearLayout) itemView.findViewById(R.id.ll_1);
            ll_2 = (LinearLayout) itemView.findViewById(R.id.ll_2);
            mTvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            mTvPrice1 = (TextView) itemView.findViewById(R.id.tv_price1);
            tv_kc = (TextView) itemView.findViewById(R.id.tv_kc);
            sv = (SelectView) itemView.findViewById(R.id.sv);
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

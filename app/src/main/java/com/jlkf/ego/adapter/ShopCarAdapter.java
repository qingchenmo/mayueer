package com.jlkf.ego.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.bean.ProductInfo;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.widget.MyRadioButton;
import com.jlkf.ego.widget.SelectView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车适配器
 * <p>
 * Created by 杨诗洋 on 2017/7/17 0017.
 */

public class ShopCarAdapter extends RecyclerView.Adapter {

    private static final int TYPE_SELECT = 100;
    private static final int TYPE_ITEM = 200;
    private static final int TYPE_MORE = 300;


    private List<GoodsBean.ShopcartBean> mDatas1;
    private List<GoodsBean.ShopcartBean> mDatas2;
    private List<GoodsBean.ShopcartBean> mDatas;
    private List<GoodsBean.ShopcartBean> mConnections; // 我的收藏1
    private List<GoodsBean.ShopcartBean> mConnections1; // 我的收藏2
    private Context context;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    private boolean isEdit = false;

    private boolean isShow1 = false;
    private boolean isShow2 = false;

    private int tag;
    private TextView tv_favorites;

    Map<Integer, ProductInfo> map;

    public ShopCarAdapter(Context context, final List mDatas1, final List mDatas2, final CheckBox checkBox, TextView tv_favorites) {
        mConnections = new ArrayList<>();
        mConnections1 = new ArrayList<>();
        map = new HashMap<>();
        this.tv_favorites = tv_favorites;
        mDatas = new ArrayList();

        this.mDatas1 = mDatas1;
        this.mDatas2 = mDatas2;
        mDatas.addAll(mDatas1);
        mDatas.addAll(mDatas2);


        this.context = context;

        setType();


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).setChecked(true);
                    }
                    isShow2 = true;
                    isShow1 = true;
                } else {
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).setChecked(false);
                    }
                    isShow2 = false;
                    isShow1 = false;
                }


                notifyDataSetChanged();
            }
        });

        tv_favorites.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {    // 收藏

                mDatas1.removeAll(mConnections);
                mDatas2.removeAll(mConnections1);

                mDatas.removeAll(mConnections);
                mDatas.removeAll(mConnections1);

                String itemCode = getItemCode(mConnections);
                String itemCode1 = getItemCode(mConnections1);


                loadConnection(itemCode + "," + itemCode1);
                notifyDataSetChanged();

            }

            private String getItemCode(List<GoodsBean.ShopcartBean> productInfos) {
                String mS1 = "";
                for (int i = 0; i < productInfos.size(); i++) {
                    String s = "";
                    if (i == 0 || i == productInfos.size() - 1) {
                        s = productInfos.get(i).getItemcode() + "";
                    } else {
                        s = productInfos.get(i).getItemcode() + ",";
                    }
                    mS1 = s + mS1;
                }
                return mS1;
            }
        });

    }

    private void setType() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(ViewHolder v, int layoutPosition) {
                GoodsBean.ShopcartBean productInfo = mDatas.get(layoutPosition);

                if (productInfo.isEditType()) {
                    productInfo.setEditType(false);

                } else {

                    productInfo.setEditType(true);
                }
                notifyDataSetChanged();
            }
        });

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
        HttpUtil.getInstacne((Activity) context).post(Urls.coller, String.class, object, new HttpUtil.OnCallBack<String>() {

            @Override
            public void success(String data) {
                ToastUti.show("收藏成功");
            }

            @Override
            public void onError(String msg, int code) {

            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(ViewHolder v, int layoutPosition);
    }

    private int total1 = 0;
    private int total2 = 0;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_SELECT) {
            View view = LayoutInflater.from(context).inflate(R.layout.holder_select, parent, false);
            return new ViewHolder1(view);
        } else if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(context).inflate(R.layout.holder_shopping, parent, false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_MORE) {

            View view = LayoutInflater.from(context).inflate(R.layout.holder_shopping_more, parent, false);
            return new ViewHolder3(view);
        }
        return null;
    }

    GoodsBean.ShopcartBean shoppingBean;
    ViewHolder viewHolder;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        int mposition = 0;

        if (getItemViewType(position) == TYPE_SELECT) {
            final ViewHolder1 viewHolder1 = (ViewHolder1) holder;

            if (position < mDatas1.size() + 1) {
                tag = 0;
                viewHolder1.rb.setChecked(isShow1);

                viewHolder1.tvDes.setText("自营品牌");
                setpinpaiAll(viewHolder1, mDatas1, 0);

            } else {
                viewHolder1.rb.setChecked(isShow2);
                setpinpaiAll(viewHolder1, mDatas2, 1);
                viewHolder1.tvDes.setText("代理品牌");

                tag = 1;
            }


        } else if (getItemViewType(position) == TYPE_MORE) {


        } else {
            if (position > 0 && position < mDatas1.size() + 1) {
                mposition = position - 1;

            } else if (position >= mDatas1.size() + 1 && position != getItemCount() - 1) {
                mposition = position - 2;
            }
            shoppingBean = mDatas.get(mposition);
            viewHolder = (ViewHolder) holder;

            viewHolder.view2.setVisibility(View.GONE);

            final int finalMposition1 = mposition;


            viewHolder.rb1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsBean.ShopcartBean productInfo = mDatas.get(finalMposition1);
                    ((MyRadioButton) v).setChecked(true);
                }
            });

            viewHolder.rb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsBean.ShopcartBean productInfo = mDatas.get(finalMposition1);
                    ((MyRadioButton) v).setChecked(true);
                }
            });


            viewHolder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                }
            });

            viewHolder.tvTitle.setText(shoppingBean.getName() + "");

            final int finalMposition = mposition;

            // 选择
            viewHolder.rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsBean.ShopcartBean productInfo = mDatas.get(finalMposition);
                    if (((CheckBox) v).isChecked()) {
                        productInfo.setChecked(true);
                        // 添加收藏的选中
                        if (finalMposition < mDatas1.size()) {
                            mConnections.add(productInfo);
                        } else {
                            mConnections1.add(productInfo);
                        }

                        if (tag == 0) {
                            total1++;
                        } else {
                            total2++;
                        }
                    } else {
                        productInfo.setChecked(false);
                        if (finalMposition < mDatas1.size()) {
                            mConnections.remove(productInfo);
                        } else {
                            mConnections1.remove(productInfo);
                        }
                        if (tag == 0) {
                            total1--;
                        } else {
                            total2--;
                        }
                    }

                    if (tag == 0) {
                        isAll(total1, tag);
                    } else {
                        isAll(total2, tag);
                    }
                }
            });

            viewHolder.rb.setChecked(shoppingBean.isChecked());


            isShowEdit(shoppingBean, viewHolder);

            final int finalMposition2 = mposition;
            viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(viewHolder, finalMposition2);
                    }
                }
            });


            if (position == mDatas1.size() || position == mDatas.size() + 1) {

                viewHolder.view.setVisibility(View.GONE);
                viewHolder.view2.setVisibility(View.VISIBLE);
            }

        }
    }

    public void isShowEdit(GoodsBean.ShopcartBean shoppingBean, ViewHolder viewHolder) {
        if (shoppingBean.isEditType()) {
            viewHolder.rlAdpaterEdit.setVisibility(View.GONE);
            viewHolder.rlAdpaterEdited.setVisibility(View.VISIBLE);
        } else {
            viewHolder.rlAdpaterEdit.setVisibility(View.VISIBLE);
            viewHolder.rlAdpaterEdited.setVisibility(View.GONE);
        }
    }

    // 判断是否全选
    public void isAll(int total, int type) {
        if (type == 0) {
            if (total == mDatas1.size() + 1) {
                isShow1 = true;
            } else {
                isShow1 = false;
            }

        } else {
            if (total == mDatas2.size() + 1) {
                isShow2 = true;
            } else {
                isShow2 = false;
            }

        }
        notifyDataSetChanged();

    }


    /**
     * 删除购物车
     *
     * @param layoutPosition
     */
    private void showDialog(final int layoutPosition) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.delet_dialog, null);
        final Dialog dialog = new AlertDialog.Builder(context)
                .setView(inflate)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_radius_white);

        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUti.show("取消");
                dialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {

            private int mLocation;

            @Override
            public void onClick(View v) {
                ToastUti.show("删除" + layoutPosition);
                if (layoutPosition < mDatas1.size()) {
                    mLocation = layoutPosition;
                    GoodsBean.ShopcartBean productInfo = mDatas1.get(mLocation);
                    mDatas1.remove(productInfo);
                } else {
                    mLocation = layoutPosition - mDatas1.size();
                    GoodsBean.ShopcartBean productInfo = mDatas2.get(mLocation);
                    mDatas2.remove(productInfo);
                }
                mDatas.remove(mDatas.get(mLocation));

                deleGoods(mLocation);

                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * 删除购物车
     *
     * @param location
     */
    private void deleGoods(int location) {

        //    删除本地的数据
        //   GoodsUtil.getInstance().delet(mDatas.get(location));

        // 删除网络数据
        Map<String, String> map = new HashMap<>();
        map.put("sId", mDatas.get(location).getSId() + "");
        HttpUtil.getInstacne((Activity) context).get(Urls.delete, JSONObject.class, map, new HttpUtil.OnCallBack() {
            @Override
            public void success(Object data) {

            }

            @Override
            public void onError(String msg, int code) {

            }
        });

    }


    // 设置一种品牌全选
    private void setpinpaiAll(final ViewHolder1 viewHolder1, final List<GoodsBean.ShopcartBean> mData, final int s) {

        viewHolder1.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder1.rb.isChecked()) {
                    for (int i = 0; i < mData.size(); i++) {
                        mData.get(i).setChecked(true);
                    }

                    if (s == 0) {
                        isShow1 = true;
                        total1 = mDatas1.size();
                    } else {
                        isShow2 = true;
                        total2 = mDatas2.size();

                    }

                } else {
                    for (int i = 0; i < mData.size(); i++) {
                        mData.get(i).setChecked(false);
                    }

                    if (s == 0) {
                        total1 = 0;
                        isShow1 = false;
                    } else {
                        total2 = 0;
                        isShow2 = false;

                    }

                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDatas.size() > 0 && mDatas != null) {
            return mDatas.size() + 3;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || mDatas1.size() + 1 == position) {
            return TYPE_SELECT;
        }
        if (position == getItemCount() - 1) {
            return TYPE_MORE;

        } else {
            return TYPE_ITEM;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener, SelectView.OnCurVauleListener {

        @BindView(R.id.shopping_img)
        ImageView shoppingImg;
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
        MyRadioButton rb1;
        @BindView(R.id.rb2)
        MyRadioButton rb2;
        @BindView(R.id.rg)
        RadioGroup rg;

        @BindView(R.id.sv)
        SelectView sv;

        @BindView(R.id.rb)
        CheckBox rb;

        @BindView(R.id.ll)
        LinearLayout ll;

        @BindView(R.id.view)
        View view;

        @BindView(R.id.view2)
        View view2;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            rg.setOnCheckedChangeListener(this);
            sv.setOnCurVauleListener(this);

            ll.setOnLongClickListener(new View.OnLongClickListener() {
                public int mposition;

                @Override
                public boolean onLongClick(View v) {

                    int position = getLayoutPosition();
                    if (position > 0 && position < mDatas1.size() + 1) {
                        mposition = position - 1;

                    } else if (position >= mDatas1.size() + 1 && position != getItemCount() - 1) {
                        mposition = position - 2;
                    }
                    showDialog(mposition);

                    return true;


                }
            });

        }

        /**
         * 大小包的切换
         *
         * @param group
         * @param checkedId
         */
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//            shopCartUpdata();

        }


        /**
         * 选择器
         *
         * @param vaule
         */
        @Override
        public void onCurVaule(String vaule) {
//            shopCartUpdata();
        }
    }

    /**
     * 编辑购物车接口
     */
    private void shopCartUpdata() {


//        JSONObject object = new JSONObject();
//        try {
//            object.put("sId", sid);
//            object.put("name", name);
//            object.put("price", price);
//            object.put("logo", logo);
//            object.put("itemcode", itemcode);
//            object.put("quantity", quantity);
//            object.put("codebars", codebars);
//            object.put("brandname", brandname);
//            object.put("brandId", brandId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        HttpUtil.getInstacne((Activity) context).post(Urls.update, JSONObject.class, object, new HttpUtil.OnCallBack() {
//            @Override
//            public void success(Object mData) {
//
//            }
//
//            @Override
//            public void onError(String msg, int code) {
//
//            }
//        });

    }

    class ViewHolder1 extends RecyclerView.ViewHolder {

        @BindView(R.id.rb)
        CheckBox rb;
        @BindView(R.id.tv_des)
        TextView tvDes;

        public ViewHolder1(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


        }
    }

    class ViewHolder3 extends RecyclerView.ViewHolder {


        public ViewHolder3(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }


}

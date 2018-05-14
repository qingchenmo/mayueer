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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.MyConnectionActivity;
import com.jlkf.ego.activity.ProductInfoActivity;
import com.jlkf.ego.bean.Connection;
import com.jlkf.ego.bean.GoodsBean;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;
import com.jlkf.ego.utils.ProductAddShopCarUtils;
import com.jlkf.ego.widget.SelectView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jlkf.ego.utils.ShardeUtil.getUser;

/**
 * Created by Administrator on 2017/9/1 0001.
 * <p>
 * 我的收藏
 */

public class ConnectionAdatper extends RecyclerView.Adapter {


    public List<Connection> getConnections() {
        return mConnections;
    }

    private List<Connection> mConnections;

    public ConnectionAdatper(List<Connection> connections, Context context) {
        mConnections = connections;
        mContext = context;
    }

    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.holder_connection, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        final Connection connection = mConnections.get(position);


        if (connection.getOitmView() != null) {
            Glide.with(mContext).load(connection.getOitmView().getPicturname()).error(R.drawable.icon_img_load).into(viewHolder.mIvProductImg);
            viewHolder.mTvProductName.setText(connection.getOitmView().getItemname());
//            viewHolder.mTvProductPrice.setText(connection.getOitmView().getPrice() + "");
            viewHolder.mTvProductStock.setText(connection.getOitmView().getOnhand() + "");
            viewHolder.mTvProductNumLarge.setText(connection.getOitmView().getUUX() + mContext.getResources().getString(R.string.jian));
            viewHolder.mTvProductNumSmall.setText(connection.getOitmView().getUUB() + mContext.getResources().getString(R.string.jian));
            viewHolder.mTvProductPrice.setText(mContext.getResources().getString(R.string.money) + connection.getOitmView().getPrice() + "");

            viewHolder.mSv.setCurVaule(connection.getOitmView().getShopCount());
            viewHolder.mSv.setMax((int) connection.getOitmView().getOnhand());
//            viewHolder.mSv.setOnCommitListener(new SelectView.OnCommitListener() {
//                @Override
//                public void onCurVaule(int vaule) {
//                    connection.getOitmView().setShopCount(vaule);
//                    viewHolder.mSv.setCurVaule(vaule);
//                    change(connection.getOitmView(), vaule + "");
//                }
//            });

            viewHolder.mSv.setOnCurVauleListener(new SelectView.OnCurVauleListener() {
                @Override
                public void onCurVaule(String vaule) {
                    connection.getOitmView().setShopCount(Integer.valueOf(vaule));
                    viewHolder.mSv.setCurVaule(Integer.valueOf(vaule));
                    if (!vaule.equals("0")) {
                        change(connection.getOitmView(), vaule);
                    }

                }
            });

//            if (connection.getOitmView().getUIssale().equals("Y") || connection.getOitmView().getUIssale().equals("y")
//                    && connection.getOitmView().getIsShow() == 0) {
//                // 没有
//                viewHolder.mRl.setVisibility(View.VISIBLE);
//
//            } else {
//                // 有
//                viewHolder.mRl.setVisibility(View.GONE);
//            }

            // 大包
            viewHolder.mLinProductPackageLarge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (connection.getIsBig() == 0) {
                        connection.setIsBig(2);
                        setNormal(viewHolder, connection);
                    } else {

                        connection.setIsBig(0);
                        setdabao(viewHolder, connection);
                    }

                }
            });
            // 小包
            viewHolder.mLinProductPackageSmall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (connection.getIsBig() == 1) {
                        connection.setIsBig(2);
                        setNormal(viewHolder, connection);
                    } else {

                        connection.setIsBig(1);
                        setxiaobao(viewHolder, connection);
                    }
                }
            });

        } else {
            Glide.with(mContext).load(R.drawable.icon_img_load).into(viewHolder.mIvProductImg);

        }
        if (connection.getIsBig() == 0) {
            setdabao(viewHolder, connection);
        } else if (connection.getIsBig() == 1) {
            setxiaobao(viewHolder, connection);
        }



        viewHolder.mRlItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongListener != null) {
                    mOnLongListener.onLong(position);
                }
                return true;
            }
        });

        viewHolder.mRlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductInfoActivity.class);
                intent.putExtra("itemCode", connection.getItemcode());
                ((Activity) mContext).startActivityForResult(intent, MyConnectionActivity.CONNECTION_RESULT_CODE);
            }
        });
    }

    private void setNormal(ViewHolder viewHolder, Connection connection) {
        viewHolder.mIvProductPackageSmall.setImageResource(R.mipmap.icon_small_package);
        viewHolder.mIvProductPackageLarge.setImageResource(R.mipmap.icon_large_package);
        viewHolder.mTvProductNumLarge.setTextColor(mContext.getResources().getColor(R.color.black));
        viewHolder.mTvProductNumSmall.setTextColor(mContext.getResources().getColor(R.color.black));
        viewHolder.mSv.setValue(1);
    }

    private void setdabao(ViewHolder viewHolder, Connection connection) {
        viewHolder.mIvProductPackageLarge.setImageResource(R.mipmap.icon_large_package_brown);
        viewHolder.mTvProductNumLarge.setTextColor(mContext.getResources().getColor(R.color.bg_brown));

        viewHolder.mTvProductNumSmall.setTextColor(mContext.getResources().getColor(R.color.black));
        viewHolder.mIvProductPackageSmall.setImageResource(R.mipmap.icon_small_package);
        if (connection.getOitmView() != null && !TextUtils.isEmpty(connection.getOitmView().getUUX())) {

            viewHolder.mSv.setValue(Integer.valueOf(connection.getOitmView().getUUX()));
        }
    }

    private void setxiaobao(ViewHolder viewHolder, Connection connection) {
        viewHolder.mSv.setValue(Integer.valueOf(connection.getOitmView().getUUB()));
        viewHolder.mIvProductPackageSmall.setImageResource(R.mipmap.icon_small_package_brown);
        viewHolder.mTvProductNumSmall.setTextColor(mContext.getResources().getColor(R.color.bg_brown));

        viewHolder.mTvProductNumLarge.setTextColor(mContext.getResources().getColor(R.color.black));
        viewHolder.mIvProductPackageLarge.setImageResource(R.mipmap.icon_large_package);
    }

    public void change(final Connection.OitmViewBean oitmView, String vaule) {

        final GoodsBean.ShopcartBean info = new GoodsBean.ShopcartBean();
        info.setName(oitmView.getItemname());
        info.setPrice(Double.valueOf(oitmView.getPrice()));
        info.setLogo(oitmView.getPicturname());
        info.setQuantity(Integer.valueOf(vaule));
        info.setItemcode(oitmView.getItemcode());
        info.setCodebars(oitmView.getCodebars());
        info.setBrandname(oitmView.getPName());
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

            HttpUtil.getInstacne((Activity) mContext).post(Urls.shopInsert, Object.class, object, new HttpUtil.OnCallBack<Object>() {
                @Override
                public void success(Object data) {
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

    public void setOnLongListener(OnLongListener onLongListener) {
        mOnLongListener = onLongListener;
    }

    public OnLongListener mOnLongListener;

    public interface OnLongListener {
        void onLong(int position);
    }

    @Override
    public int getItemCount() {
        if (mConnections != null && mConnections.size() > 0) {
            return mConnections.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product_img)
        ImageView mIvProductImg;
        @BindView(R.id.tv_product_name)
        TextView mTvProductName;
        @BindView(R.id.tv_product_price)
        TextView mTvProductPrice;
        @BindView(R.id.iv_product_package_large)
        ImageView mIvProductPackageLarge;
        @BindView(R.id.tv_product_num_large)
        TextView mTvProductNumLarge;
        @BindView(R.id.lin_product_package_large)
        LinearLayout mLinProductPackageLarge;
        @BindView(R.id.iv_product_package_small)
        ImageView mIvProductPackageSmall;
        @BindView(R.id.tv_product_num_small)
        TextView mTvProductNumSmall;
        @BindView(R.id.lin_product_package_small)
        LinearLayout mLinProductPackageSmall;
        @BindView(R.id.tv_product_stock)
        TextView mTvProductStock;
        @BindView(R.id.rl)
        RelativeLayout mRl;
        @BindView(R.id.sv)
        SelectView mSv;

        @BindView(R.id.rl_item)
        RelativeLayout mRlItem;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

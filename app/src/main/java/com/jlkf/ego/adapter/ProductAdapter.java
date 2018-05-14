package com.jlkf.ego.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.utils.CompanyUtil;
import com.jlkf.ego.utils.ProductAddShopCarUtils;

import java.util.List;


/**
 * Created by zcs on 2017/7/11.
 * 商品列表适配器
 */

public class ProductAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private int mItemType;
    public static final int LISTITEM = 0;
    public static final int GRIDITEM = 1;
    private boolean showCheckBox = false;

    public List<ProductListBean.DataBean> getList() {
        return mList;
    }

    private List<ProductListBean.DataBean> mList;
    private OnItemClickListener mListener;
    private ProductAddShopCarUtils.OnAddShopCarListener mOnAddShopCarListener;

    public ProductAdapter(Context context, List<ProductListBean.DataBean> list, int itemType
            , OnItemClickListener listener, ProductAddShopCarUtils.OnAddShopCarListener onAddShopCarListener) {
        mContext = context;
        mItemType = itemType;
        mList = list;
        mListener = listener;
        this.mOnAddShopCarListener = onAddShopCarListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (mItemType == LISTITEM) {
            holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_product_list, parent, false));
        } else if (mItemType == GRIDITEM) {
            holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_product_grid, parent, false));
        }
        return holder;
    }

    public void changeLayout(boolean isShow) {
        showCheckBox = isShow;
    }


    /**
     * 是否显示CheckBox
     *
     * @param isShow
     */
    public void showCheckBox(boolean isShow) {
        int size = mList.size();
//        if (isShow)
        for (int i = 0; i < size; i++) {
//            mList.get(i).setChecked(isShow);
            ProductListBean.DataBean bean = mList.get(i);
            bean.setSelectNum(ProductAddShopCarUtils.getInstance().getGoodsNumInShopCar(bean.getItemcode()));
        }
        showCheckBox = isShow;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        if (mItemType == GRIDITEM && position % 2 == 0) {
            viewHolder.itemView.setPadding(CompanyUtil.dip2px(6), 0, CompanyUtil.dip2px(3), 0);
        } else if (mItemType == GRIDITEM) {
            viewHolder.itemView.setPadding(CompanyUtil.dip2px(3), 0, CompanyUtil.dip2px(6), 0);
        }
        viewHolder.et_product_select_num.setFocusable(false);
        viewHolder.mCbItem.setVisibility(showCheckBox ? View.VISIBLE : View.GONE);
        ClickListener listener = new ClickListener(viewHolder, position);
        viewHolder.mCbItem.setOnClickListener(listener);
        viewHolder.et_product_select_num.setOnClickListener(listener);


        viewHolder.iv_product_num_add.setOnClickListener(listener);
        viewHolder.iv_product_num_sub.setOnClickListener(listener);
        viewHolder.lin_product_package_small.setOnClickListener(listener);
        viewHolder.lin_product_package_large.setOnClickListener(listener);
        viewHolder.et_product_select_num.setEnabled(!showCheckBox);
        final ProductListBean.DataBean info = mList.get(position);
        if (!(info.getUUB() == null || info.getUUB().isEmpty()) && !(info.getUUX() == null || info.getUUX().isEmpty())) {
            viewHolder.lin_product_package_small.setVisibility(View.VISIBLE);
            viewHolder.lin_product_package_large.setVisibility(View.VISIBLE);
            info.setHavaPackage(true);
//            info.setLargePackage(true);
        } else {
            viewHolder.lin_product_package_small.setVisibility(View.INVISIBLE);
            viewHolder.lin_product_package_large.setVisibility(View.INVISIBLE);
        }
//        info.setSelectNum(Integer.parseInt(info.getShopCount()));
        info.setSelectNum(ProductAddShopCarUtils.getInstance().getGoodsNumInShopCar(info.getItemcode()));
        viewHolder.mCbItem.setChecked(info.isChecked());
        viewHolder.tv_product_price.setText(mContext.getString(R.string.money) + info.getPrice());
        viewHolder.tv_product_name.setText(info.getItemname());
        viewHolder.tv_product_stock.setText(info.getOnhand());
        viewHolder.et_product_select_num.setText(String.valueOf(info.getSelectNum()));
//        viewHolder.et_product_select_num.setText();
        viewHolder.tv_product_num_large.setText(info.getUUX());
        viewHolder.tv_product_num_small.setText(info.getUUB());
        Glide.with(mContext).load(info.getPicturname()).placeholder(R.drawable.icon_img_load).error(R.drawable.icon_img_load_failed).into(viewHolder.iv_product_img);
        if (info.isHavaPackage()) {
//            if (info.isLargePackage()) {
//                viewHolder.iv_product_package_large.setImageResource(R.mipmap.icon_large_package_brown);
//                viewHolder.iv_product_package_small.setImageResource(R.mipmap.icon_small_package);
//                viewHolder.tv_product_num_large.setTextColor(mContext.getResources().getColor(R.color.bg_brown2));
//                viewHolder.tv_product_num_small.setTextColor(mContext.getResources().getColor(R.color.text_title));
//            } else if (info.isSmallPackage()) {
//                viewHolder.iv_product_package_large.setImageResource(R.mipmap.icon_large_package);
//                viewHolder.iv_product_package_small.setImageResource(R.mipmap.icon_small_package_brown);
//                viewHolder.tv_product_num_large.setTextColor(mContext.getResources().getColor(R.color.text_title));
//                viewHolder.tv_product_num_small.setTextColor(mContext.getResources().getColor(R.color.bg_brown2));
//            } else {
//                viewHolder.iv_product_package_large.setImageResource(R.mipmap.icon_large_package);
//                viewHolder.iv_product_package_small.setImageResource(R.mipmap.icon_small_package);
//                viewHolder.tv_product_num_large.setTextColor(mContext.getResources().getColor(R.color.text_title));
//                viewHolder.tv_product_num_small.setTextColor(mContext.getResources().getColor(R.color.text_title));
//            }
            viewHolder.lin_product_package_large.setSelected(info.isLargePackage());
            viewHolder.lin_product_package_small.setSelected(info.isSmallPackage());

        }
        viewHolder.iv_product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCheckBox) {
                    viewHolder.mCbItem.setChecked(!viewHolder.mCbItem.isChecked());
                    mList.get(position).setChecked(viewHolder.mCbItem.isChecked());
                    return;
                }
                mListener.clickItemListener(info);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ClickListener implements View.OnClickListener {
        private ViewHolder holder;
        private int position;

        public ClickListener(ViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (showCheckBox && v.getId() != R.id.cb_product_item) {
                return;
            }
            switch (v.getId()) {
                case R.id.cb_product_item:
                    mList.get(position).setChecked(holder.mCbItem.isChecked());
                    break;
                case R.id.lin_product_package_large:
//                    mList.get(position).setLargePackage(true);
//                    ProductAddShopCarUtils.getInstance().EditShopCar(true, mList.get(position), holder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, holder.iv_product_img);
                    if (mList.get(position).isLargePackage()) {
                        mList.get(position).setLargePackage(false);
//                        holder.iv_product_package_large.setImageResource(R.mipmap.icon_large_package);
                        holder.lin_product_package_large.setSelected(false);
                        ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                        return;
                    }
                    ProductAddShopCarUtils.getInstance().startAlarm(mContext);
//                    holder.iv_product_package_small.setImageResource(R.mipmap.icon_small_package);
//                    holder.iv_product_package_large.setImageResource(R.mipmap.icon_large_package_brown);
//                    holder.tv_product_num_large.setTextColor(mContext.getResources().getColor(R.color.bg_brown2));
//                    holder.tv_product_num_small.setTextColor(mContext.getResources().getColor(R.color.text_title));
                    holder.lin_product_package_large.setSelected(true);
                    holder.lin_product_package_small.setSelected(false);
                    mList.get(position).setLargePackage(true);
                    mList.get(position).setSmallPackage(false);
                    break;
                case R.id.lin_product_package_small:
//                    mList.get(position).setSmallPackage(true);

//                    ProductAddShopCarUtils.getInstance().EditShopCar(true, mList.get(position), holder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, holder.iv_product_img);
                    if (mList.get(position).isSmallPackage()) {
                        mList.get(position).setSmallPackage(false);
                        holder.lin_product_package_small.setSelected(false);
//                        holder.iv_product_package_small.setImageResource(R.mipmap.icon_small_package);
                        ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                        return;
                    }
                    ProductAddShopCarUtils.getInstance().startAlarm(mContext);
                    holder.lin_product_package_small.setSelected(true);
                    holder.lin_product_package_large.setSelected(false);
//                    holder.iv_product_package_small.setImageResource(R.mipmap.icon_small_package_brown);
//                    holder.iv_product_package_large.setImageResource(R.mipmap.icon_large_package);
//                    holder.tv_product_num_large.setTextColor(mContext.getResources().getColor(R.color.text_title));
//                    holder.tv_product_num_small.setTextColor(mContext.getResources().getColor(R.color.bg_brown2));
                    mList.get(position).setLargePackage(false);
                    mList.get(position).setSmallPackage(true);
                    break;
                case R.id.iv_product_num_add:
                    ProductAddShopCarUtils.getInstance().EditShopCar(true, mList.get(position), holder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, holder.iv_product_img);
                    break;
                case R.id.iv_product_num_sub:
                    ProductAddShopCarUtils.getInstance().EditShopCar(false, mList.get(position), holder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, holder.iv_product_img);
                    break;
                case R.id.et_product_select_num:
//                    DialogUtil.addShopCarDia(mContext, false, mList.get(position), holder.et_product_select_num, (Activity) mContext, mOnAddShopCarListener, holder.iv_product_img);
                    break;
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCbItem;
        public ImageView iv_product_num_add, iv_product_num_sub, iv_product_img, iv_product_package_large, iv_product_package_small;
        private TextView tv_product_name, tv_product_price, tv_product_stock,
                tv_product_num_large, tv_product_num_small;
        private LinearLayout lin_product_package_large, lin_product_package_small;
        private TextView et_product_select_num;

        public ViewHolder(View itemView) {
            super(itemView);
            mCbItem = (CheckBox) itemView.findViewById(R.id.cb_product_item);
            iv_product_img = (ImageView) itemView.findViewById(R.id.iv_product_img);
            iv_product_num_add = (ImageView) itemView.findViewById(R.id.iv_product_num_add);
            iv_product_num_sub = (ImageView) itemView.findViewById(R.id.iv_product_num_sub);
            lin_product_package_large = (LinearLayout) itemView.findViewById(R.id.lin_product_package_large);
            lin_product_package_small = (LinearLayout) itemView.findViewById(R.id.lin_product_package_small);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_product_price = (TextView) itemView.findViewById(R.id.tv_product_price);
            tv_product_stock = (TextView) itemView.findViewById(R.id.tv_product_stock);
            et_product_select_num = (TextView) itemView.findViewById(R.id.et_product_select_num);
            tv_product_num_large = (TextView) itemView.findViewById(R.id.tv_product_num_large);
            tv_product_num_small = (TextView) itemView.findViewById(R.id.tv_product_num_small);
            iv_product_package_large = (ImageView) itemView.findViewById(R.id.iv_product_package_large);
            iv_product_package_small = (ImageView) itemView.findViewById(R.id.iv_product_package_small);

            /*itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnLongClickListener != null) {
                        mOnLongClickListener.onLong(getLayoutPosition());
                    }
                    return true;
                }
            });*/
        }
    }


    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    public OnLongClickListener mOnLongClickListener;

    public interface OnLongClickListener {
        void onLong(int position);
    }


    /**
     * 商品列表item点击回调
     */
    public interface OnItemClickListener {
        /**
         * @param info 商品信息实体类
         */
        void clickItemListener(ProductListBean.DataBean info);
    }

   /* public interface OnShowBottom {
        void showBottom(ViewHolder holder);
    }*/
}

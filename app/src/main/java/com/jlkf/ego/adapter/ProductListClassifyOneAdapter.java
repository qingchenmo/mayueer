package com.jlkf.ego.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.GoodTypeBean;

import java.util.List;

/**
 * Created by zcs on 2017/7/11.
 * 商品列表一级分类适配器
 */

public class ProductListClassifyOneAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GoodTypeBean> mList;
    private OnItemClickListener mOnItemClickListener;
    private int mPosition;
    public int mViewType;

    public ProductListClassifyOneAdapter(Context context, List<GoodTypeBean> list, OnItemClickListener listener, int type) {
        this.mContext = context;
        mList = list;
        mOnItemClickListener = listener;
        mViewType = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_product_classify, parent, false));
//        holder.itemView.setBackgroundResource(R.drawable.bg_white_grey);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodTypeBean bean = mList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mTvProductName.setText(bean.getItmsgrpnam());
        viewHolder.textView8.setText(bean.getItmsgrpcod());
        Glide.with(mContext).load(bean.getDrawable()).error(R.drawable.icon_img_load).into(viewHolder.mIvProductImg);
        OnClickLisener lisener = new OnClickLisener();
        lisener.setHolder(viewHolder);
        lisener.setmPosition(position);
        viewHolder.mLinProductItem.setOnClickListener(lisener);
        if (mPosition == position) {
            viewHolder.mLinProductItem.setSelected(true);
            viewHolder.itemView.setSelected(true);
        } else {
            viewHolder.mLinProductItem.setSelected(false);
            viewHolder.itemView.setSelected(false);
        }
        if (mPosition + 1 == position) {
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvProductName;
        private ImageView mIvProductImg;
        private View mViewLine;
        private TextView textView8;
        private LinearLayout mLinProductItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvProductName = (TextView) itemView.findViewById(R.id.tv_item);
            mIvProductImg = (ImageView) itemView.findViewById(R.id.iv_item);
            mViewLine = itemView.findViewById(R.id.view_line);
            textView8 = (TextView) itemView.findViewById(R.id.textView8);
            mLinProductItem = (LinearLayout) itemView.findViewById(R.id.lin_item);
            if (mViewType == -1) {
                mLinProductItem.setBackgroundResource(R.color.white);
            }
        }
    }

    class OnClickLisener implements View.OnClickListener {
        private ViewHolder mHolder;
        private int position;

        public void setHolder(ViewHolder holder) {
            this.mHolder = holder;
        }

        public void setmPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            mPosition = position;
            notifyDataSetChanged();
            mOnItemClickListener.clickLisener(position);
            mHolder.mLinProductItem.setSelected(true);
            mHolder.itemView.setSelected(true);
        }
    }

    /**
     * RecycleView的Item点击回调
     */
    public interface OnItemClickListener {
        void clickLisener(int position);
    }

}

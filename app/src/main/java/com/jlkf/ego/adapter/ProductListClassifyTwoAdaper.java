package com.jlkf.ego.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.GoodTypeBean;
import com.jlkf.ego.utils.CompanyUtil;

import java.util.List;

/**
 * Created by zcs on 2017/7/11.
 * 商品列表二级分类适配器
 */

public class ProductListClassifyTwoAdaper extends RecyclerView.Adapter {
    private Context mContext;

    public List<GoodTypeBean> getList() {
        return mList;
    }

    private List<GoodTypeBean> mList;
    private OnItemClickListener mListener;
    private int mViewType;

    public ProductListClassifyTwoAdaper(Context context, List<GoodTypeBean> list, OnItemClickListener listener, int viewType) {
        mContext = context;
        mList = list;
        mListener = listener;
        mViewType = viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_product_classify_two, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        GoodTypeBean goodTypeBean = mList.get(position);
        if (mViewType != -1) {
            if (position % 2 == 0) {
                viewHolder.itemView.setPadding(CompanyUtil.dip2px(20), 0, CompanyUtil.dip2px(10), 0);
            } else {
                viewHolder.itemView.setPadding(CompanyUtil.dip2px(10), 0, CompanyUtil.dip2px(20), 0);
            }
            Log.e("tag", "height-->" + viewHolder.mIvProductImg.getHeight() + "\nwidth-->" + viewHolder.mIvProductImg.getWidth() + "\nitemWidth-->" + viewHolder.itemView.getMeasuredWidth());
        }
        GoodTypeBean bean = mList.get(position);
//        viewHolder.mIvProductImg.setImageResource(bean.getDrawable());
        Glide.with(mContext).load("http://imgsrc.baidu.com/imgad/pic/item/b03533fa828ba" +
                "61e5e6d4c0d4b34970a304e5915.jpg").error(R.drawable.icon_img_load_failed).into(viewHolder.mIvProductImg);
        OnClickListener listener = new OnClickListener();
        listener.setPostion(position);
        viewHolder.mTvProductName.setText(goodTypeBean.getItmsgrpnam());
        viewHolder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvProductName;
        private ImageView mIvProductImg;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvProductName = (TextView) itemView.findViewById(R.id.tv_item);
            mIvProductImg = (ImageView) itemView.findViewById(R.id.iv_item);
            if (mViewType != -1) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIvProductImg.getLayoutParams();
                itemView.measure(0, 0);
                params.height = itemView.getMeasuredWidth() - CompanyUtil.dip2px(30);
                mIvProductImg.setLayoutParams(params);
            }
        }
    }

    class OnClickListener implements View.OnClickListener {
        private int postion;

        public void setPostion(int postion) {
            this.postion = postion;
        }

        @Override
        public void onClick(View view) {
            mListener.clickListener(postion);
        }
    }

    /**
     * RecycleView的Item点击回调
     */
    public interface OnItemClickListener {
        void clickListener(int position);
    }
}

package com.jlkf.ego.newpage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.newpage.bean.ClassificationBean;
import com.jlkf.ego.newpage.inter.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcs on 2018/5/15.
 *
 * @describe:
 */

public class ClassificationRightAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<ClassificationBean> mList;
    private OnItemClickListener<ClassificationBean> mListener;
    public static final int TOP = 1;
    public static final int LIST = 2;
    private String mTitle;
    private int mSelectIndex;

    public ClassificationRightAdapter(Context context, String title, int dex, List<ClassificationBean> list, OnItemClickListener<ClassificationBean> listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
        mListener = listener;
        mTitle = title;
        mSelectIndex = dex;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TOP)
            return new TopViewHolder(mInflater.inflate(R.layout.item_classification_right_top_layout, parent, false));
        else
            return new ItemViewHolder(mInflater.inflate(R.layout.item_classification_right_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TOP) {
            TopViewHolder viewHolder = (TopViewHolder) holder;
            viewHolder.mView.setText(mTitle);
        } else {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            Glide.with(mContext).load(mList.get(position - 1).getPicture()).into(viewHolder.mIvImg);
            viewHolder.mTvContent.setText(mList.get(position - 1).getItmsGrpNam());
            viewHolder.mLine.setVisibility(mList.size() == position ? View.GONE : View.VISIBLE);
            viewHolder.mTvContent.setSelected(mSelectIndex == position);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectIndex = position;
                    notifyDataSetChanged();
                    if (mListener != null)
                        mListener.itemClickListener(mList.get(position - 1), position - 1);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TOP;
        return LIST;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    static class TopViewHolder extends RecyclerView.ViewHolder {
        TextView mView;

        public TopViewHolder(View itemView) {
            super(itemView);
            mView = (TextView) itemView;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.line)
        View mLine;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

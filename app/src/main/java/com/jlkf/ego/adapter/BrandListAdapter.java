package com.jlkf.ego.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlkf.ego.R;
import com.jlkf.ego.activity.ProductListActivity;
import com.jlkf.ego.bean.BrandListBean;

import java.util.List;

/**
 * Created by zcs on 2017/7/19.
 * 品牌列表适配器
 */

public class BrandListAdapter extends RecyclerView.Adapter {
    private final int mTag;

    public List<BrandListBean> getmList() {
        return mList;
    }

    private List<BrandListBean> mList;
    private List<BrandListBean> mHotList;
    private Context mContext;

    public BrandListAdapter(Context context, List<BrandListBean> brandListBeen, List<BrandListBean> list, int tag) {
        mContext = context;
        mTag = tag;
        if (mTag == 0) {
            mList = brandListBeen;
            mHotList = list;
        } else {
            mList = list;
            mHotList = list;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTag == 0) {
            if (position == 0) {
                return 0;
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new BrandTypeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.brand_heard_layout, parent, false));
            case 1:
                return new RecommendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.brand_first_word_layout, parent, false));
            case 2:
                return new BrandViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_small_brand, parent, false));
            case 3:
                return new BrandViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_brand, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 0) {
            BrandTypeViewHolder brandTypeViewHolder = (BrandTypeViewHolder) holder;
            brandTypeViewHolder.rv_brand_heard.setLayoutManager(new GridLayoutManager(mContext, 3));
            brandTypeViewHolder.rv_brand_heard.setAdapter(new BrandListAdapter(mContext, mHotList, mHotList, 1));
        } else if (getItemViewType(position) == 1) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.brand_first_word.setText(mList.get(position).getFirstWord());
        } else if (getItemViewType(position) == 3) {
            final BrandListBean brandListBean = mList.get(position);

            BrandViewHolder brandViewHolder = (BrandViewHolder) holder;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) brandViewHolder.iv_brand_logo.getLayoutParams();
            if (position % 3 == 0) {
                params.rightMargin = 5;
            } else if (position % 3 == 1) {
                params.rightMargin = 3;
                params.leftMargin = 2;
            } else if (position % 3 == 2) {
                params.leftMargin = 5;
            }
            brandViewHolder.iv_brand_logo.setLayoutParams(params);
            brandViewHolder.itemView.setOnClickListener(new OnClickListener(position));

            Glide.with(mContext).load(brandListBean.getPpMinlogo()).into(brandViewHolder.iv_brand_logo);

            brandViewHolder.iv_brand_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startList(brandListBean.getCode());

                }
            });

        } else {
            BrandListBean brandListBean = null;
            if (position != 0) {

                brandListBean = mList.get(position - 1);
            }

            BrandViewHolder brandViewHolder = (BrandViewHolder) holder;

//            if (getPositionForSection(brandListBean.getSortLetters().charAt(0)) != position - 1) {
//
//                brandViewHolder.brand_first_word.setVisibility(View.GONE);
//            } else {
//                brandViewHolder.brand_first_word.setVisibility(View.VISIBLE);
////                brandViewHolder.brand_first_word.setText(brandListBean.getSortLetters());
//            }

            brandViewHolder.tv_brand_name.setText(brandListBean.getName());
//            brandViewHolder.brand_first_word.setText(brandListBean.getSortLetters());
            brandViewHolder.itemView.setOnClickListener(new OnClickListener(position));
            Glide.with(mContext).load(brandListBean.getPpMinlogo()).into(brandViewHolder.iv_brand_logo);
        }
    }


    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getmList().size(); i++) {
////            String sortStr = ((BrandListBean) getmList().get(i)).getSortLetters();
////            char firstChar = sortStr.toUpperCase().charAt(0);
//            if (firstChar == section) {
//                return i;
//            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0) {
            if (mTag == 0) {
                return 1 + mList.size();

            } else {

                return mList.size();
            }
        }
        return 0;
    }

    public int getPositionFirstWord(char str) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            if (mList.get(i).getFirstWord() != null && mList.get(i).getFirstWord().equals(String.valueOf(str))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 推荐品牌
     */
    static class BrandTypeViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rv_brand_heard;

        public BrandTypeViewHolder(View itemView) {
            super(itemView);
            rv_brand_heard = (RecyclerView) itemView.findViewById(R.id.rv_brand_heard);
        }
    }

    static class RecommendViewHolder extends RecyclerView.ViewHolder {
        private TextView brand_first_word;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            brand_first_word = (TextView) itemView.findViewById(R.id.brand_first_word);
        }
    }

    class BrandViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_brand_logo;
        private TextView tv_brand_name;
        private TextView brand_first_word;

        public BrandViewHolder(View itemView) {
            super(itemView);
            iv_brand_logo = (ImageView) itemView.findViewById(R.id.iv_brand_logo);
            tv_brand_name = (TextView) itemView.findViewById(R.id.tv_brand_name);
            brand_first_word = (TextView) itemView.findViewById(R.id.brand_first_word);


        }
    }

    class OnClickListener implements View.OnClickListener {
        private int position;

        public OnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            if (mTag == 0) {
                BrandListBean brandListBean = mList.get(position - 1);
                String code = brandListBean.getCode();
                startList(code);
            } else {

            }

        }
    }

    private void startList(String code) {

        Intent intent = new Intent(mContext, ProductListActivity.class);
        intent.putExtra("code", code);
        mContext.startActivity(intent);
    }
}

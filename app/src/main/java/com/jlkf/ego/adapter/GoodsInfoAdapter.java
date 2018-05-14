package com.jlkf.ego.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jlkf.ego.R;
import com.jlkf.ego.bean.GoodBean;

import java.util.List;

/**
 * Created by zcs on 2017/7/18.
 * 订单详情中商品信息的适配器
 */

public class GoodsInfoAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoodBean> mList;
    public GoodsInfoAdapter(Context context,List<GoodBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.order_img_layout,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    static class ViewHolder {
        private View itemView;
        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}

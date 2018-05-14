package com.jlkf.ego.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.bean.MessageListBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 系统消息列表adapter
 *
 * @author 邓超桂
 * @date 创建时间：2016年10月11日 下午1:11:23
 */
public class SystemMessageAdapter extends MyBaseAdapter<MessageListBean> {

    private Context mContext;

    private OnClickListener itemClick;

    public void setItemClick(OnClickListener itemClick) {
        this.itemClick = itemClick;
    }

    public SystemMessageAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        convertView = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_system_message, null, false);

            holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
            holder.tv_redCircle = (TextView) convertView.findViewById(R.id.tv_redCircle);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MessageListBean info = (MessageListBean) getItem(position);
        if (info != null) {
            holder.tv_title.setText(info.getMTitle());
            holder.tv_content.setText(info.getMContext());
            holder.tv_time.setText(getDate(info.getMCreationtime()));
            holder.tv_redCircle.setVisibility(info.getMsIsunread().endsWith("0") ? View.VISIBLE : View.GONE);
            holder.ll_item.setTag(info);
        }
        return convertView;
    }

    public String getDate(long s) {
        Date date = new Date(s);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public class ViewHolder {
        LinearLayout ll_item;
        ImageView iv_logo;
        TextView tv_redCircle;
        TextView tv_title;
        TextView tv_content;
        TextView tv_time;
    }
}



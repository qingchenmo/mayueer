package com.jlkf.ego.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jlkf.ego.R;
import com.jlkf.ego.bean.HistoryInfo;
import com.jlkf.ego.utils.AppUtil;

/**
 * 域名后缀选择器 adapter
 * @author  邓超桂
 * @date 创建时间：2016年10月11日 下午1:11:23
 */
public class GoodHistoryAdapter extends MyBaseAdapter<HistoryInfo>{


	private Context mContext;
	private OnClickListener intemClickListener;

	public GoodHistoryAdapter(Context context) {
		this.mContext = context ;
	}

	public void setIntemClickListener(OnClickListener intemClickListener) {
		this.intemClickListener = intemClickListener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_goods_history, null, false);

			holder.rl_item= (RelativeLayout) convertView.findViewById(R.id.rl_item);
			holder.tv_domain = (TextView) convertView.findViewById(R.id.tv_domain);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HistoryInfo info=(HistoryInfo) getItem(position);
		if(info!=null){
			//域名
			if(!AppUtil.IsNullString(info.getName())){
				holder.tv_domain.setText(info.getName());
			}else{
				holder.tv_domain.setText("");
			}
			holder.rl_item.setTag(info);
			holder.rl_item.setOnClickListener(intemClickListener);

		}
		return convertView;
	}
	class ViewHolder{
		RelativeLayout rl_item;
		TextView tv_domain;
	}

}


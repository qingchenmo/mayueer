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
import com.jlkf.ego.bean.BrandBean;
import com.jlkf.ego.utils.AppUtil;
import com.jlkf.ego.widget.spiner.ItemInfo;

/**
 * 选择语言列表adapter
 * @author  邓超桂
 * @date 创建时间：2016年10月11日 下午1:11:23
 */
public class LanguageTypeAdapter extends MyBaseAdapter<ItemInfo>{

	private Context mContext;
	private String langSelectId="0";


	private OnClickListener itemClick;

	public void setLangSelectId(String langSelectId) {this.langSelectId = langSelectId;}
	public void setItemClick(OnClickListener itemClick) {
		this.itemClick = itemClick;
	}

	public LanguageTypeAdapter(Context context) {
		this.mContext = context ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		convertView=null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_language, null, false);
			
			holder.ll_item= (LinearLayout) convertView.findViewById(R.id.ll_item);
			holder.iv_country_logo = (ImageView) convertView.findViewById(R.id.iv_country_logo);
			holder.tv_country_name= (TextView) convertView.findViewById(R.id.tv_country_name);
			holder.iv_select= (ImageView) convertView.findViewById(R.id.iv_select);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}

		ItemInfo info=(ItemInfo) getItem(position);
		if(info!=null){
			if(!AppUtil.IsNullString(info.getName())){
				holder.tv_country_name.setVisibility(View.VISIBLE);
				holder.tv_country_name.setText(info.getName());
			}else{
				holder.tv_country_name.setVisibility(View.GONE);
			}
			holder.iv_country_logo.setImageResource(info.getDrawable());

			if(!AppUtil.IsNullString(info.getObjId())){
				if(langSelectId.equals(info.getObjId())){
					holder.iv_select.setVisibility(View.VISIBLE);
				}else{
					holder.iv_select.setVisibility(View.GONE);
				}
			}

			holder.ll_item.setTag(info);
			holder.ll_item.setOnClickListener(itemClick);

		}
		return convertView;
	}
	public class ViewHolder{
		LinearLayout ll_item;
		ImageView iv_country_logo;
		TextView tv_country_name;
		ImageView iv_select;
	}
}



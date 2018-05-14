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
import com.jlkf.ego.bean.GoodTypeBean;

/**
 * 商品类型列表adapter
 * @author  邓超桂
 * @date 创建时间：2016年10月11日 下午1:11:23
 */
public class GoodTypeAdapter extends MyBaseAdapter<GoodTypeBean>{

	private Context mContext;
	private OnClickListener itemClick;

	private int selectPosition=0;

	public void setSelectPosition(int selectPosition) {this.selectPosition = selectPosition;}

	public void setItemClick(OnClickListener itemClick) {
		this.itemClick = itemClick;
	}

	public GoodTypeAdapter(Context context) {
		this.mContext = context ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		convertView=null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_goods_type, null, false);
			
			holder.ll_item= (LinearLayout) convertView.findViewById(R.id.ll_item);
			holder.iv_brand_logo = (ImageView) convertView.findViewById(R.id.iv_brand_logo);
			holder.tv_brand_name= (TextView) convertView.findViewById(R.id.tv_brand_name);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}

		GoodTypeBean info=(GoodTypeBean) getItem(position);
		if(info!=null){

			if (selectPosition==position){
				holder.ll_item.setBackgroundResource(R.color.white);
				holder.iv_brand_logo.setSelected(true);
			}else{
				holder.ll_item.setBackgroundResource(R.color.act_background);
				holder.iv_brand_logo.setSelected(false);
			}

//			if(!AppUtil.IsNullString(info.getName())){
//				holder.tv_brand_name.setVisibility(View.VISIBLE);
//				holder.tv_brand_name.setText(info.getName());
//			}else{
//				holder.tv_brand_name.setVisibility(View.GONE);
//			}

			if(info.getDrawable()!=0){
				holder.iv_brand_logo.setVisibility(View.VISIBLE);
				holder.iv_brand_logo.setImageResource(info.getDrawable());
			}else{
				holder.iv_brand_logo.setVisibility(View.GONE);
			}

			holder.ll_item.setTag(position);
			holder.ll_item.setOnClickListener(itemClick);

		}
		return convertView;
	}
	public class ViewHolder{
		LinearLayout ll_item;
		ImageView iv_brand_logo;
		TextView tv_brand_name;
	}
}



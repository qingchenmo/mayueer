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
import com.jlkf.ego.bean.GoodBean;
import com.jlkf.ego.utils.AppUtil;

/**
 * 商品列表adapter
 * @author  邓超桂
 * @date 创建时间：2016年10月11日 下午1:11:23
 */
public class GoodAdapter extends MyBaseAdapter<GoodBean>{

	private Context mContext;



	private OnClickListener itemClick;


	public void setItemClick(OnClickListener itemClick) {
		this.itemClick = itemClick;
	}

	public GoodAdapter(Context context) {
		this.mContext = context ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_good, null, false);
			
			holder.ll_item= (LinearLayout) convertView.findViewById(R.id.ll_item);
			holder.iv_brand_logo = (ImageView) convertView.findViewById(R.id.iv_brand_logo);
			holder.tv_brand_name= (TextView) convertView.findViewById(R.id.tv_brand_name);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}

		GoodBean info=(GoodBean) getItem(position);
		if(info!=null){

//			if(!AppUtil.IsNullString(info.getName())){
//				holder.tv_brand_name.setVisibility(View.VISIBLE);
//				holder.tv_brand_name.setText(info.getName());
//			}else{
//				holder.tv_brand_name.setVisibility(View.GONE);
//			}


		}
		return convertView;
	}
	public class ViewHolder{
		LinearLayout ll_item;
		ImageView iv_brand_logo;
		TextView tv_brand_name;
	}
}



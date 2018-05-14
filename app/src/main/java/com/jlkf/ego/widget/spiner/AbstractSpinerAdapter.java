package com.jlkf.ego.widget.spiner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlkf.ego.R;
import com.jlkf.ego.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractSpinerAdapter extends BaseAdapter {

	public static interface IOnItemSelectListener{
		public void onItemClick(int pos);
	};
	
	 private Context mContext;   
	 private List<ItemInfo> mObjects = new ArrayList<ItemInfo>();
	 private int mSelectItem = 0;
	    
	 private LayoutInflater mInflater;
	
	 public  AbstractSpinerAdapter(Context context){
		 init(context);
	 }
	 
	 public void refreshData(List<ItemInfo> list, int selIndex){
		 mObjects = list;
		 if (selIndex < 0){
			 selIndex = 0;
		 }
		 if (selIndex >= mObjects.size()){
			 selIndex = mObjects.size() - 1;
		 }
		 mSelectItem = selIndex;
	 }
	 
	 private void init(Context context) {
	        mContext = context;
	        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }
	    
	    
	@Override
	public int getCount() {

		return mObjects!=null?mObjects.size():0;
	}

	@Override
	public Object getItem(int pos) {
		return mObjects.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		 ViewHolder viewHolder;
    	 
	     if (convertView == null) {
	    	 convertView = mInflater.inflate(R.layout.spiner_item_layout, null);
	         viewHolder = new ViewHolder();
	         viewHolder.ll_country_item = (LinearLayout) convertView.findViewById(R.id.ll_country_item);
			 viewHolder.iv_country_logo = (ImageView) convertView.findViewById(R.id.iv_country_logo);
			 viewHolder.tv_country_name = (TextView) convertView.findViewById(R.id.tv_country_name);
			 viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			 viewHolder.tv_jia = (TextView) convertView.findViewById(R.id.tv_jia);
	         convertView.setTag(viewHolder);
	     } else {
	         viewHolder = (ViewHolder) convertView.getTag();
	     }

	     ItemInfo item = (ItemInfo) getItem(pos);

		if(!AppUtil.IsNullString(item.getName())){
			viewHolder.tv_country_name.setText(item.getName());
		}else{
			viewHolder.tv_country_name.setText("");
		}

		if(!AppUtil.IsNullString(item.getCountrName())){
			viewHolder.tv_title.setText(item.getCountrName());
		}else{
			viewHolder.tv_title.setText("");
		}

		if(item.getDrawable()!=0){
			viewHolder.iv_country_logo.setVisibility(View.VISIBLE);
			viewHolder.iv_country_logo.setImageResource(item.getDrawable());
            viewHolder.tv_title.setVisibility(View.GONE);
            viewHolder.tv_jia.setVisibility(View.GONE);

        }else{
            viewHolder.iv_country_logo.setVisibility(View.GONE);
			viewHolder.iv_country_logo.setImageResource(item.getDrawable());
		}

	     return convertView;
	}

	public static class ViewHolder
	{
		public LinearLayout ll_country_item;
		public ImageView iv_country_logo;
	    public TextView tv_country_name;
	    public TextView tv_title;
	    public TextView tv_jia;
    }

}

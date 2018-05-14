package com.jlkf.ego.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ExListView extends ListView {

	public ExListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ExListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public ExListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	            MeasureSpec.AT_MOST);  
	    super.onMeasure(widthMeasureSpec, expandSpec);  
	}
	
	
	public void setListViewHeightBasedOnChildren() {
		int totalHeight = 0;
		ListAdapter adapter = getAdapter();
		for (int i = 0, len = adapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = adapter.getView(i, null, this);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = this.getLayoutParams();
		params.height = totalHeight
				+ (this.getDividerHeight() * (this.getCount() - 1));
		this.setLayoutParams(params);
	}
}

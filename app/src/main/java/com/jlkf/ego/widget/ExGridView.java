package com.jlkf.ego.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * 自定义GridView 适应高度
 * @author dcg
 *2016-5-10
 */
public class ExGridView extends GridView {
	public ExGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ExGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public ExGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	            MeasureSpec.AT_MOST);  
	    super.onMeasure(widthMeasureSpec, expandSpec);  
	}
	
	
	@SuppressLint("NewApi")
	public void setListViewHeightBasedOnChildren() {
		int totalHeight = 0;
		ListAdapter adapter = getAdapter();
		int count = adapter.getCount();
		int number = 0;
		int l = getNumColumns();
		int mod = count % this.getNumColumns();
		if(mod != 0){
			number = count / getNumColumns() + 1;
		}else{
			number = count / getNumColumns();
		}
		
		for (int i = 0;i < number; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = adapter.getView(i * 3 + 0, null, this);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		
		ViewGroup.LayoutParams params = this.getLayoutParams();
		if (totalHeight > this.getMinimumHeight()){
			params.height = totalHeight;
		}else{
			params.height = this.getMinimumHeight();
		}

				//+ (this.getDividerHeight() * (this.getCount() - 1));
		this.setLayoutParams(params);
	}
}

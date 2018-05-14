package com.jlkf.ego.widget.swipefinish.widget;

import android.graphics.Point;
import android.graphics.Rect;

public interface SwipeFinishTouchCheckAction {
	
	/**
	 * 向右滑动是否可关闭activity
	 * @return
	 */
	boolean onScrollToClose();
	
	/**
	 * 是否点击横向滑动的view
	 * @param rect
	 * @param point
	 * @return
	 */
	boolean inChild(Rect rect, Point point);
}

package com.jlkf.ego.widget.swipefinish.widget;

public interface SwipeFinishAction extends SwipeFinishTouchCheckAction {
	
	void startScroll(int startX, int dx, boolean finish);
	
	void onTouchDown();
	
	void onScroll();
	
}

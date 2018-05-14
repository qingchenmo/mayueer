package com.jlkf.ego.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.widget.swipefinish.baseactivity.BaseSwipeFinishFragmentActivity;

/**
 * 1、fragmentactivity 继承自activity，用来解决android3.0
 * 之前没有fragment的api，所以在使用的时候需要导入support包
 * ，同时继承fragmentActivity，这样在activity中就能嵌入fragment来实现你想要的布局效果。
 * 2、当然3.0之后你就可以直接继承自Activity，并且在其中嵌入使用fragment了。 3、获得Manager的方式也不同
 * 3.0以下：getSupportFragmentManager() 3.0以上：getFragmentManager()
 * 
 * @author 邓超桂
 * 
 */
public abstract class BaseFragmentActivity extends BaseSwipeFinishFragmentActivity {
	public Context mContext;

	public Activity mActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=BaseFragmentActivity.this;
		mActivity = this;
		
		initView();
		//滑动关闭activity
		addSwipeFinishLayout();
		//首页activtiy可设置为不能滑动关闭
        setEnableGesture(false);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/** 通过ID绑定UI **/
	public <T extends View> T getView(int id) {
		return (T) findViewById(id);
	}


	/**
	 * 展示土司
	 * 
	 * @param text
	 *            文本内容
	 * @param isLong
	 *            1 为长时间 0 为短时间
	 */
	public void showToast(String text, int isLong) {
		if (isLong == 1) {
			Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 初始化view
	 */
	public abstract void initView();

	/**
	 * 弹出输入键盘
	 * 
	 * @param mContext
	 */
	public static void showInput(Activity mContext) {
		InputMethodManager inputMethodManager = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(0,
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 隐藏键盘
	 * 
	 * @param mContext
	 */
	public static void hideInput(Activity mContext) {
		try {
			((InputMethodManager) mContext
					.getSystemService(mContext.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(mContext.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (RuntimeException re) {
			AppLog.Logi("dcg", "re==" + re);
		}
	}

	/**
	 * 判断是否联网的方法
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkConnected(Context context) {

		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

}

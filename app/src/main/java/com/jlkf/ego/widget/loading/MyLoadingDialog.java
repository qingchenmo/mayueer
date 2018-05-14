package com.jlkf.ego.widget.loading;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.jlkf.ego.R;

/**
 * 自定义仿ios 加载等待对话框
 * @author  邓超桂
 * @E-mail  305436934@qq.com 
 *@创建时间 2016年11月16日 上午10:20:12
 */
public class MyLoadingDialog {
	/**
	 * 风格1(自定义View动画)
	 * @return
	 */
	public static LoadingDialog showDialogTypeOne(Context context){
		LoadingDialog dialog = new LoadingDialog(context, R.layout.view_tips_loading);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		return dialog;
	}
	/**
	 * 风格1(自定义View动画)（不可点击和按返回键无效）
	 * @return
	 */
	public static LoadingDialog showDialogTypeThree(Context context) {
		LoadingDialog dialog = new LoadingDialog(context,R.layout.view_tips_loading,1);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		// 屏蔽物理返回键
		dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_SEARCH) {
					return true;
				} else {
					return false; // 默认返回 false
				}
			}
		});
		dialog.show();
		return dialog;
	}
}

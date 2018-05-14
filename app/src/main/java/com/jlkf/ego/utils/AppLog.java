package com.jlkf.ego.utils;


import android.util.Log;

/**
 * 日志工具类
 */
public class AppLog {
	public static final String TAG = "dcg";
	private static final boolean DEBUG = true;//Log日志打开
//	private static final boolean DEBUG = false;//Log日志关闭

	/**
	 * @return void
	 * @param @param content
	 * @Description: show all log v type by TAG
	 */
	public static final void Logv(String content) {
		if (DEBUG) {
			Log.v(TAG, content);
		}
	}

	/**
	 * @return void
	 * @param tag
	 * @param content
	 * @Description: show all log v type by @param tag
	 */
	public static final void Logv(String tag, String content) {
		if (DEBUG) {
			Log.v(tag, content);
		}
	}

	/**
	 * @return void
	 * @param @param content
	 * @Description: show all log e type by TAG
	 */
	public static final void Loge(String content) {
		if (DEBUG) {
			Log.e(TAG, content);
		}
	}

	/**
	 * @return void
	 * @param tag
	 * @param content
	 * @Description: show all log e type by @param tag
	 */
	public static final void Loge(String tag, String content) {
		if (DEBUG) {
			Log.e(tag, content);
		}

	}

	public static final void Logi(String content) {
		if (DEBUG) {
			Logi(TAG, content);
		}

	}

	public static final void Logi(String tag, String content) {
		if (DEBUG) {
			Log.i(tag, content);
		}
	}

	public static final void Logw(String content) {
		if (DEBUG) {
			Logw(TAG, content);
		}
	}

	public static final void Logw(String tag, String content) {
		if (DEBUG) {
			Log.w(tag, content);
		}
	}

	public static final void Logd(String content) {
		if (DEBUG) {
			Logd(TAG, content);
		}
	}

	public static final void Logd(String tag, String content) {
		if (DEBUG) {
			Log.d(tag, content);
		}
	}

}
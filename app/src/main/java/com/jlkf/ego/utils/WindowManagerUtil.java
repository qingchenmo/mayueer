package com.jlkf.ego.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 获取屏幕宽高工具类
 * @author dcg
 *2016-5-11
 */
public class WindowManagerUtil {
	//方法一
	/**
	 * 获取屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getScreenWidth1(Context context){
		 WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		 return wm.getDefaultDisplay().getWidth();
	 } 
	 /**
	  * 获取屏幕高度
	  * @param context
	  * @return
	  */
	public static int getScreenHeight1(Context context){
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			return wm.getDefaultDisplay().getHeight();
	 }
	//方法二  这里必须注意，获得的尺寸单位为px，即像素，而不是屏幕的绝对尺寸
	/**
	 * 获取屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getScreenWidth2(Context context){
		DisplayMetrics dm =context.getResources().getDisplayMetrics();  
        return dm.widthPixels;
	 } 
	/**
	  * 获取屏幕高度
	  * @param context
	  * @return
	  */
	public static int getScreenHeight2(Context context){
		DisplayMetrics dm =context.getResources().getDisplayMetrics();  
        return dm.heightPixels;  
	 }
	
	//px与dip的概念及互相转化
	  public static int dip2px(Context context, float dipValue){           
	        final float scale = context.getResources().getDisplayMetrics().density;                   
	        return (int)(dipValue * scale + 0.5f);           
	    }              
	    public static int px2dip(Context context, float pxValue){            
	        final float scale = context.getResources().getDisplayMetrics().density;                   
	        return (int)(pxValue / scale + 0.5f);           
	    }   
}

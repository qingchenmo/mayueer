package com.jlkf.ego.application;

import android.app.Application;
import android.graphics.Bitmap;

import com.jlkf.ego.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ImageApplication extends Application {
	  
	public static ImageLoader imageLoader = ImageLoader.getInstance();
	/** options:轮播图**/
	public static DisplayImageOptions options;
	public static DisplayImageOptions options1;
	public static DisplayImageOptions options2;
	public static DisplayImageOptions options3;
	public static DisplayImageOptions options4;
	public static boolean isOpenTestAuto=false;//自动化测试开关，关
//	public static boolean isOpenTestAuto=true;//自动化测试开关，开
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.mipmap.ic_launcher) 	    //加载中图片
		.showImageForEmptyUri(R.mipmap.ic_launcher) 		//空图片
		.showImageOnFail(R.mipmap.ic_launcher)			//错误图片
		.cacheInMemory(true) 
		.cacheOnDisk(true).considerExifParams(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new RoundedBitmapDisplayer(0)).build();
		
		options1= new DisplayImageOptions.Builder()
		.showImageOnLoading(R.mipmap.empty_photo) 	    //加载中图片
		.showImageForEmptyUri(R.mipmap.empty_photo) 		//空图片
		.showImageOnFail(R.mipmap.empty_photo)			//错误图片
		.cacheInMemory(true) 
		.cacheOnDisk(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.delayBeforeLoading(0)  
		.resetViewBeforeLoading(false)  
		.considerExifParams(false)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new RoundedBitmapDisplayer(0)).build();
		
		options2= new DisplayImageOptions.Builder()
		.showImageOnLoading(R.mipmap.img_touxiang_default) 	    //加载中图片
		.showImageForEmptyUri(R.mipmap.img_touxiang_default) 		//空图片
		.showImageOnFail(R.mipmap.img_touxiang_default)			//错误图片
		.cacheInMemory(true) 
		.cacheOnDisk(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.delayBeforeLoading(0)  
		.resetViewBeforeLoading(false)  
		.considerExifParams(false)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new RoundedBitmapDisplayer(0)).build();
		
		options3= new DisplayImageOptions.Builder()
		.showImageOnLoading(R.mipmap.empty_photo) 	    //加载中图片
		.showImageForEmptyUri(R.mipmap.empty_photo) 		//空图片
		.showImageOnFail(R.mipmap.empty_photo)			//错误图片
		.cacheInMemory(true) 
		.cacheOnDisk(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.delayBeforeLoading(0)  
		.resetViewBeforeLoading(false)  
		.considerExifParams(false)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new RoundedBitmapDisplayer(30)).build();
		
		//聊天的头像
		options4= new DisplayImageOptions.Builder()
		.showImageOnLoading(R.mipmap.img_touxiang_chat) 	    //加载中图片
		.showImageForEmptyUri(R.mipmap.img_touxiang_chat) 		//空图片
		.showImageOnFail(R.mipmap.img_touxiang_chat)			//错误图片
		.cacheInMemory(true) 
		.cacheOnDisk(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.delayBeforeLoading(0)  
		.resetViewBeforeLoading(false)  
		.considerExifParams(false)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new RoundedBitmapDisplayer(0)).build();
	}

}

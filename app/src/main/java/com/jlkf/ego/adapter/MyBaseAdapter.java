package com.jlkf.ego.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * BaseAdapter 继承封装
 * @author 邓超桂
 *
 * @param <T>
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
	public DisplayImageOptions options = null;

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private List<T> list = new ArrayList<T>();
    
	
	public List<T> getList() {
		return list;
	}

	public void setData(List<T> data){
		if (data != null && data.size() >0){
			list.clear();
			list.addAll(data);
		}
	}
	
	public void clearData(boolean flag){
		if(flag){
			list.clear();
		}
	}
	
	public void appendData(List<T> data){
		if (data != null && data.size() >0){
			list.addAll(data);
		}
	}
	
	public void appendFirst(List<T> data){
		if (data != null){
			list.addAll(0, data);
		}
	}
	
	public void appendFirst(T data){
		if (data != null){
			list.add(0, data);
		}
	}
	
	public void appendData(T data){
		if (data != null){
			list.add(data);
		}
	}

	public void removeItem(int position){
		int size = this.getCount();
		if (position < size ){
			list.remove(position);
		}
	}
	public void removeItem(T data){
		if(data!=null && list.contains(data)){
			list.remove(data);
		}
	}
	
	
	public MyBaseAdapter(List<T> list) {
		super();
		this.list = list;
		initCache();
	}
	
	public MyBaseAdapter() {
		super();
		initCache();
	}

	// 返回行数
	@Override
	public int getCount() {
		return list!=null?list.size():0;
	}

	// 返回指定下标的实例
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 返回 行视图 显示指定下标的数据
	@Override
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

	private void initCache() {
		if (options == null) {
			options = new DisplayImageOptions.Builder()
					.resetViewBeforeLoading(true).cacheOnDisk(true)
					.cacheInMemory(false)
					.imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.considerExifParams(true)
					.displayer(new FadeInBitmapDisplayer(100)).build();
		}
	}

	public void displayImage(String url, ImageView view,
			DisplayImageOptions options) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		if (imageLoader != null) {
			imageLoader.displayImage(url, view, options, animateFirstListener);
		}
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 600);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}

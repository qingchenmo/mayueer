package com.jlkf.ego.zxing;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jlkf.ego.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class MainZxingActivity extends Activity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	/**
	 * 显示扫描结果
	 */
	private TextView mTextView ;
	/**
	 * 显示扫描拍的图片
	 */
	private ImageView mImageView;
	private ImageView my_bitmap;
	
	private String time;
	private static File file = null;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zxing_main);
		
		mTextView = (TextView) findViewById(R.id.result);
		mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
		my_bitmap= (ImageView) findViewById(R.id.my_bitmap);
		
		initImageLoader(getApplicationContext());
		
//		my_bitmap.setOnLongClickListener(new OnLongClickListener() {
//				@Override
//				public boolean onLongClick(View v) {
//					// 长按识别二维码
//					saveCurrentImage();
//					return true;
//				}
//			});
		
		//点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
		//扫描完了之后调到该界面
		Button mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainZxingActivity.this, MipcaActivityCapture.class);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
	}
	
	private void initImageLoader(Context context) {
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app
		
		config.memoryCacheExtraOptions(480, 800);
		config.diskCacheExtraOptions(480, 320, null); 
		config.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024));  
		config.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000));
		
		ImageLoader.getInstance().init(config.build());
		
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.mipmap.ic_launcher) 	    //加载中图片
		.showImageForEmptyUri(R.mipmap.ic_launcher) 		//空图片
		.showImageOnFail(R.mipmap.ic_launcher)			//错误图片
		.cacheInMemory(true) 
		.cacheOnDisk(true).considerExifParams(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new RoundedBitmapDisplayer(0)).build();
		
	}


	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				if(bundle!=null){
					//显示扫描到的内容
					if(!TextUtils.isEmpty(bundle.getString("result"))){
						mTextView.setText(bundle.getString("result"));
					}else{
						mTextView.setText("");
					}
					if((Bitmap) data.getParcelableExtra("bitmap")!=null){
						//显示
						Bitmap mBitmap=(Bitmap)data.getParcelableExtra("bitmap");
						
						imageLoader.displayImage("file://"+ saveBitmapFile(mBitmap).getPath(), mImageView,options, null);
//						imageLoader.displayImage("file://"+ saveBitmapFile(createQRImage(bundle.getString("result"))).getPath(), my_bitmap,options, null);
						
//						mImageView.setImageBitmap(BitmapFactory.decodeFile(saveBitmapFile(mBitmap).getPath(),getBitmapOption(2)));
						my_bitmap.setImageBitmap(createQRImage(bundle.getString("result")));
						my_bitmap.setVisibility(View.VISIBLE);
					}
					if(!TextUtils.isEmpty(bundle.getString("path"))){
						imageLoader.displayImage("file://"+ bundle.getString("path"), mImageView,options, null);
						
//						mImageView.setImageBitmap(BitmapFactory.decodeFile(bundle.getString("path"),getBitmapOption(2)));
						
						my_bitmap.setImageBitmap(createQRImage(bundle.getString("result")));
						my_bitmap.setVisibility(View.VISIBLE);
//						my_bitmap.setVisibility(View.GONE);
					}
				}
			}
			break;
		}
    }	

	
    private Options getBitmapOption(int inSampleSize){ 
    	System.gc(); 
    	Options options = new Options();
    	options.inPurgeable = true; 
    	options.inSampleSize = inSampleSize; 
    	return options; 
    	}


	//这种方法状态栏是空白，显示不了状态栏的信息
    private void saveCurrentImage()
    {
        //获取当前屏幕的大小
        int width = getWindow().getDecorView().getRootView().getWidth();
        int height = getWindow().getDecorView().getRootView().getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
        //找到当前页面的根布局
        View view =  getWindow().getDecorView().getRootView();
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片,创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        temBitmap = view.getDrawingCache();
        SimpleDateFormat df = new SimpleDateFormat("yyyymmddhhmmss");
        time = df.format(new Date());
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/screen",time + ".png");
            if(!file.exists()){
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                temBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/screen/" + time + ".png";
                    final Result result = parseQRcodeBitmap(path);
                    runOnUiThread(new Runnable() {
                        public void run() {
                        	if(null!=result){
                        		mTextView.setText(result.toString());
                        	}else{
                        		Toast.makeText(MainZxingActivity.this, "无法识别", Toast.LENGTH_LONG).show();
                        	}
                        	}
                    });
                }
            }).start();
            //禁用DrawingCahce否则会影响性能 ,而且不禁止会导致每次截图到保存的是缓存的位图
            view.setDrawingCacheEnabled(false);
        }
    }
	
    
    //解析二维码图片,返回结果封装在Result对象中  
    private Result  parseQRcodeBitmap(String bitmapPath){
        //解析转换类型UTF-8  
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();  
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");  
        //获取到待解析的图片  
        Options options = new Options();
        //如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)  
        //并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你  
        options.inJustDecodeBounds = true;  
        //此时的bitmap是null，这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了  
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath,options);  
        //我们现在想取出来的图片的边长（二维码图片是正方形的）设置为400像素  
        /** 
            options.outHeight = 400; 
            options.outWidth = 400; 
            options.inJustDecodeBounds = false; 
            bitmap = BitmapFactory.decodeFile(bitmapPath, options); 
        */  
        //以上这种做法，虽然把bitmap限定到了我们要的大小，但是并没有节约内存，如果要节约内存，我们还需要使用inSimpleSize这个属性  
        options.inSampleSize = options.outHeight / 400;  
        if(options.inSampleSize <= 0){  
            options.inSampleSize = 1; //防止其值小于或等于0  
        }  
        /** 
         * 辅助节约内存设置 
         *  
         * options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888 
         * options.inPurgeable = true;  
         * options.inInputShareable = true;  
         */  
        options.inJustDecodeBounds = false;  
        bitmap = BitmapFactory.decodeFile(bitmapPath, options);   
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象  
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);  
        //将图片转换成二进制图片  
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));  
        //初始化解析对象  
        QRCodeReader reader = new QRCodeReader();  
        //开始解析  
        Result result = null;  
        try {  
            result = reader.decode(binaryBitmap, hints);  
        } catch (Exception e) {  
            // TODO: handle exception  
        }  
          
        return result;  
    } 
	  //要转换的地址或字符串,可以是中文，输入内容生成二维码
    public Bitmap createQRImage(String string) {
  	  int QR_WIDTH=480;
  	  int QR_HEIGHT=480;
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(string, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
           int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
           //两个for循环是图片横列扫描的结果
           for (int y = 0; y < QR_HEIGHT; y++) {
               for (int x = 0; x < QR_WIDTH; x++) {
                   if (bitMatrix.get(x, y)) {
                       pixels[y * QR_WIDTH + x] = 0xff000000;
                   } else {
                       pixels[y * QR_WIDTH + x] = 0xffffffff;
                   }
               }
           }
           //生成二维码图片的格式，使用ARGB_8888
           Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
           bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
           return bitmap;
       } catch (WriterException e) {
           e.printStackTrace();
       }
       return null;
   }
    
    //Bitmap对象保存为图片文件
    public static File saveBitmapFile(Bitmap bitmap){
    	  SimpleDateFormat df = new SimpleDateFormat("yyyymmddhhmmss");
          String time = df.format(new Date());
          File file=null;
          if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
        	   file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/screen",time + ".png");
              if(!file.exists()){
                  file.getParentFile().mkdirs();
                  try {
                      file.createNewFile();
                  } catch (IOException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  }
              }
              FileOutputStream fos = null;
              try {
                  fos = new FileOutputStream(file);
                  bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                  fos.flush();
                  fos.close();
              } catch (FileNotFoundException e) {
                  e.printStackTrace();
              } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
          }
    	try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			        try {
			                bitmap.compress(Bitmap.CompressFormat.PNG, 20, bos);
			                bos.flush();//刷新缓冲区
			        } catch (IOException e) {
			                e.printStackTrace();
			        }finally {
			        			if(bos!=null) bos.close();//回收内存
					      	}
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	return file;
    }  
	
}

package com.jlkf.ego.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.BaseBean;
import com.jlkf.ego.common.CommonConstances;
import com.jlkf.ego.common.CommonEnum;
import com.jlkf.ego.jpush.ExampleUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NetworkUtil {
	public static AlertDialog dialog;
	public static Dialog mDialog;
	
	public static Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1://登录失效
				final Context context=(Context) msg.obj;
				new Thread(){
					@Override
					public void run() {
						Looper.prepare(); 
						showLoginAgain(context);
						 Looper.loop(); 
					}
				}.start();
				break;
			case 2://Token为空
				final Context context1=(Context) msg.obj;
				new Thread(){
					@Override
					public void run() {
						Looper.prepare(); 
						showLogin(context1);
						 Looper.loop(); 
					}
				}.start();
				break;
			case 3://签名错误
				final Context context2=(Context) msg.obj;
				new Thread(){
					@Override
					public void run() {
						Looper.prepare(); 
						showSignError(context2);
						 Looper.loop(); 
					}
				}.start();
				break;
			case 4://数据没有签名
				final Context context3=(Context) msg.obj;
				new Thread(){
					@Override
					public void run() {
						Looper.prepare(); 
						showNoSign(context3);
						 Looper.loop(); 
					}
				}.start();
				break;
			}
		}
	};
	/**
	 * 用户重新登录
	 * @param context 
	 */
	protected static void showLoginAgain(final Context context) {

		MyNotificationUtil.loginOutTimeNotification(context);
		
		//这会导致一个页面多个接口同时请求需要用户登录，连续出现几个对话框。导致线程阻塞，所有用toast处理
//	         Intent intent = new Intent("com.aiyilu.domainstar.ExitLoginReceiver");  
//	         intent.putExtra("title", "提示");
//          intent.putExtra("message", "登录信息已过期，请您重新登录~");
//          context.sendBroadcast(intent); 
          
		SharedPreferencesUtil shareUserUtil= new SharedPreferencesUtil(context, "user");
		shareUserUtil.clearSPValue();
		
//		//you'm'weng
//		UMGameAgent.onProfileSignOff();
		
		//发送广播告知刷新
		Intent intent2=new Intent();
		intent2.setAction(CommonConstances.LOGIN_CHANGE_Status);//此广播接收
		intent2.putExtra("login_change_status", "1");
		context.sendBroadcast(intent2);
        Toast.makeText(context, "登录信息已过期，请您重新登录~", Toast.LENGTH_SHORT).show();
	}
	/**
	 * 用户需要登录
	 * @param context 
	 */
	protected static void showLogin(final Context context) {
		
			MyNotificationUtil.loginOutTimeNotification(context);
			
			//这会导致一个页面多个接口同时请求需要用户登录，连续出现几个对话框。导致线程阻塞，所有用toast处理
//			 Intent intent = new Intent("com.aiyilu.domainstar.ExitLoginReceiver");  
//			 intent.putExtra("title", "应用提示");
//			 intent.putExtra("message", "该操作需要用户登录~");
//			 context.sendBroadcast(intent); 
	          
			SharedPreferencesUtil shareUserUtil= new SharedPreferencesUtil(context, "user");
			shareUserUtil.clearSPValue();
			
//			//you'm'weng
//			UMGameAgent.onProfileSignOff();
			
			//发送广播告知刷新
			Intent intent2=new Intent();
			intent2.setAction(CommonConstances.LOGIN_CHANGE_Status);//此广播接收
			intent2.putExtra("login_change_status", "1");
			context.sendBroadcast(intent2);
			
	        Toast.makeText(context, "该操作需要用户登录~", Toast.LENGTH_SHORT).show();
	}
	/**
	 * 签名错误
	 * @param context
	 */
	protected static void showSignError(final Context context) {
		dialog = new AlertDialog.Builder(context)
		.setTitle("应用提示")
		.setMessage("签名错误~")
		.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						return;
					}
			}).show();
	}
	/**
	 * 数据没有签名
	 * @param context
	 */
	protected static void showNoSign(final Context context) {
		dialog = new AlertDialog.Builder(context)
		.setTitle("应用提示")
		.setMessage("数据没有签名~")
		.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						return;
					}
			}).show();
	}
	/**
	 * 异步联网请求数据
	 * 
	 * @param url
	 *            :联网请求数据的“？”前的url
	 * @param params
	 *            ：联网请求数据的“？”后的参数
	 * @param handler
	 *            ：发送消息给主线程
	 * @param what
	 *            ：handler传送的what值
	 * @param isPost
	 *            ：是否是post请求 true 为doPost；false 为doGet
	 */
	public static void getHttpData(final String url, final RequestParams params,
			final Handler handler, final int what, boolean isPost,
			final Context context) {

			final Message message = handler.obtainMessage();
//			AsyncHttpClient client = new AsyncHttpClient();
			AsyncHttpClient client = HttpClientHelper.getHttpClient();
			
			//cookie处理
			CookieUtils.saveCookie(client, context);

			//增加头部
//	        if(AppUtil.IsLogin(context)){
//	        	client.addHeader("app_user_token", AppUtil.getUserToken(context));
//	        	client.addHeader("app_user_uid", AppUtil.getUid(context));
//	        	AppLog.Loge("dcg", "用户的token："+AppUtil.getUserToken(context));
//	        }
	        
	        String channelNumber = AppUtil.getAppMetaData(context, "UMENG_CHANNEL");//获取app当前的渠道号
	        
			//增加头部
	        client.addHeader("User-Agent", "package:"+context.getPackageName()+";versionName:"+AppUtil.getVersionName(context)+";versionCode:"+AppUtil.getVersionCode(context)+
	        		";type:Android;SDK:"+ android.os.Build.VERSION.SDK+";version:"+android.os.Build.VERSION.RELEASE+";CPU_ABI:"+ android.os.Build.CPU_ABI+";IMEI:"+ExampleUtil.getImei(context, "")+";MODEL:"+android.os.Build.MODEL+
	        		";channelName:"+channelNumber+";language:"+Locale.getDefault().getLanguage()+";country:"+Locale.getDefault().getCountry());
	        
			AppLog.Loge("head", "package:" + context.getPackageName() + ";versionName:" + AppUtil.getVersionName(context) + ";versionCode:" + AppUtil.getVersionCode(context) +
					";type:Android;SDK:" + android.os.Build.VERSION.SDK + ";version:" + android.os.Build.VERSION.RELEASE + ";CPU_ABI:" + android.os.Build.CPU_ABI + ";IMEI:" + ExampleUtil.getImei(context, "") + ";MODEL:" + android.os.Build.MODEL +
					";channelName:" + channelNumber + ";language:" + Locale.getDefault().getLanguage() + ";country:" + Locale.getDefault().getCountry());
			
			try {
				if (isPost) {
					client.post(url, params, new AsyncHttpResponseHandler() {
						/**
						 * 成功处理的方法 statusCode:响应的状态码; headers:相应的头信息 比如 响应的时间，响应的服务器
						 * ; responseBody:响应内容的字节
						 */
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							
							// 遍历头信息
							AppLog.Loge("head", "POST--url:"+url);
							for (int i = 0; i < headers.length; i++) {
								Header header = headers[i];
								AppLog.Loge("head", "---header------------Name:"+ header.getName() + ",--Value:"+ header.getValue());
							}
							//将Cookie进行处理，获取cookits
							CookieUtils.setCookies(CookieUtils.getCookie(context));
							//打印标准的cookie
							CookieUtils.getCookieText(context);
							
							String resultStr = new String(responseBody);
							AppLog.Loge("xxx",  resultStr);
							
							if(!AppUtil.IsNullString(resultStr)){
								if(AppUtil.isGoodJson(resultStr)){
									Gson gson=new Gson();
									BaseBean mBaseBean=gson.fromJson(resultStr, BaseBean.class);
									int code=mBaseBean.getReturnCode();//code标识
									AppLog.Loge("dcg","结果code标识:"+code+"--url:"+url);
									
									//因为有些手机拿不到mBaseBean.getReturnCode()也默认为0。而且我们的成功刚好就是0.这就尴尬了
						if(code!=0){
									//token错误,token过期
									if(code==CommonEnum.TOKEN_ERROR || code== CommonEnum.TOKEN_OUTTIME ){
										Message msg=new Message();
										msg.what = 1;
										msg.obj = context;
										mHandler.sendMessage(msg);
									}else if(code==CommonEnum.WITHOUT_TOKEN){//没有传递token
										AppLog.Loge("user","WITHOUT_TOKEN:"+url+params.toString());
										Message msg=new Message();
										msg.what = 2;
										msg.obj = context;
										mHandler.sendMessage(msg);
									}else if(code==CommonEnum.DATA_SIGN_ERROR){//签名错误
										Message msg=new Message();
										msg.what = 3;
										msg.obj = context;
										mHandler.sendMessage(msg);
									}else if(code==CommonEnum.DATA_NO_SIGN){//数据没有签名
										Message msg=new Message();
										msg.what = 4;
										msg.obj = context;
										mHandler.sendMessage(msg);	
									}else{
										//其他情况
										message.what = what;
										message.obj = resultStr;
										handler.sendMessage(message);
									}
						}else{
							message.what = what;
							message.obj = resultStr;
							handler.sendMessage(message);
						}
					}
				}else{
					AppLog.Loge("dcg","请求网络结果返回为空，请联系管理员。"+"POST--url:"+url);
				}
				
		}
						/**
						 * 失败处理的方法 error：响应失败的错误信息封装到这个异常对象中
						 */
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							error.printStackTrace();// 把错误信息打印出轨迹来
						}
					});
					
					
					
				} else {
					client.get(url, params, new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							
							AppLog.Loge("head", "GET--url:"+url);
							// 遍历头信息
							for (int i = 0; i < headers.length; i++) {
								Header header = headers[i];
								AppLog.Loge("head", "---header------------Name:"+ header.getName() + ",--Value:"+ header.getValue());
							}
							//将Cookie进行处理，获取cookits
							CookieUtils.setCookies(CookieUtils.getCookie(context));
							//打印标准的cookie
							CookieUtils.getCookieText(context);
							
							String resultStr = new String(responseBody);
							AppLog.Loge("xxx",  resultStr);
			if(!AppUtil.IsNullString(resultStr)){
					if(AppUtil.isGoodJson(resultStr)){
							Gson gson=new Gson();
							BaseBean mBaseBean=gson.fromJson(resultStr, BaseBean.class);
							int code=mBaseBean.getReturnCode();//code标识
							AppLog.Loge("dcg","结果code标识:"+code+"--url:"+url);
							//token错误,token过期
							
				//因为有些手机拿不到mBaseBean.getReturnCode()也默认为0。而且我们的成功刚好就是0.这就尴尬了
				if(code!=0){
							if(code==CommonEnum.TOKEN_ERROR || code==CommonEnum.TOKEN_OUTTIME ){
								Message msg=new Message();
								msg.what = 1;
								msg.obj = context;
								mHandler.sendMessage(msg);
							}else if(code==CommonEnum.WITHOUT_TOKEN){//没有传递token
								AppLog.Loge("user","WITHOUT_TOKEN:"+url+params.toString());
								Message msg=new Message();
								msg.what = 2;
								msg.obj = context;
								mHandler.sendMessage(msg);
							}else if(code==CommonEnum.DATA_SIGN_ERROR){//签名错误
								Message msg=new Message();
								msg.what = 3;
								msg.obj = context;
								mHandler.sendMessage(msg);
							}else if(code==CommonEnum.DATA_NO_SIGN){//数据没有签名
								Message msg=new Message();
								msg.what = 4;
								msg.obj = context;
								mHandler.sendMessage(msg);	
							}else{
								message.what = what;
								message.obj = resultStr;
								handler.sendMessage(message);
							}
				}else{
					message.what = what;
					message.obj = resultStr;
					handler.sendMessage(message);
				}
			}	
		}else{
			AppLog.Loge("dcg","请求网络结果返回为空，请联系管理员。"+"GET--url:"+url);
		}
	}
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							// 失败处理的方法
							error.printStackTrace();
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

	}





	/**
	 *用户登录
	 * @param phone 电话号码
	 * @param password 密码
	 */
	public static void setUserLoginAPI(String phone, String password,Handler handler, int what,
								  Context context) {
		String url = MyApplication.getHttp_IP() + "user/sms?"; // 定义请求的地址
		// 创建请求参数的封装的对象
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		params.put("password", password);
		AppLog.Loge("dcg", "url:" + url + params);
		// 执行post请求
		getHttpData(url, params, handler, what, true, context);
	}
	/**
	 * 获取短信验证码
	 * 
	 * @param phone
	 *            电话号码
	 * @param handler
	 *            handler(关联主线程)
	 * @param what
	 *            Messge.what
	 * @param context
	 *            context
	 */
	public static void getSmsCode(String phone, Handler handler, int what,
			Context context) {
		String url = MyApplication.getHttp_IP() + "user/sms?"; // 定义请求的地址
		// 创建请求参数的封装的对象
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		AppLog.Loge("dcg", "url:" + url + params);
		// 执行post请求
		getHttpData(url, params, handler, what, true, context);
	}

	/**
	 * 
	 * @param phoneNum
	 * @param password
	 *  @param vmobile	//平台给游客分配的虚拟手机号，没有则填空字符串
	 * @param mHandler
	 * @param what
	 * @param mContext
	 */
	public static void setUserLoginAPI(String phoneNum, String password,String vmobile,String registrationId,Handler mHandler, int what, Context mContext) {
		String url = MyApplication.getHttp_IP() + "/user/login?"; // 定义请求的地址
		// 创建请求参数的封装的对象
		RequestParams params = new RequestParams();
		params.put("account", phoneNum);
		params.put("password", Md5Util.getMd5(password));
		if(!AppUtil.IsNullString(vmobile)){
			params.put("vmobile", vmobile);
		}
		params.put("registrationId", registrationId);
		AppLog.Loge("dcg", "url:" + url + params);
		// 执行post请求
		getHttpData(url, params, mHandler, what, true, mContext);
	}
}
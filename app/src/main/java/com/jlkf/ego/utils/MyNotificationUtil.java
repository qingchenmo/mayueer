package com.jlkf.ego.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.jlkf.ego.R;
import com.jlkf.ego.activity.LoginActivity;

/**
 * 我的通知栏设置
 * @author  邓超桂
 * @E-mail  305436934@qq.com 
 *@创建时间 2016年12月16日 下午3:28:29
 */
public class MyNotificationUtil {
	
	public static final int LOGINOUT_NOTIFICATION_ID=888;
	
	public static void loginOutTimeNotification(Context mContext){
		//通知栏只希望存在一条登录失效的消息，所以当id相同自动覆盖
			int notificationId=LOGINOUT_NOTIFICATION_ID;
	       NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
	        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
	        Intent notificationIntent = new Intent(mContext, LoginActivity.class);
	        PendingIntent intent = PendingIntent.getActivity(mContext,notificationId, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
	        mBuilder
	                .setContentText("登录过期，请重新登录")
	                .setContentIntent(intent) //设置通知栏点击意图
	                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//	                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE);//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置Notification.DEFAULT_ALL，使用defaults属性，可以组合
	                .setDefaults(Notification.DEFAULT_LIGHTS);//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置Notification.DEFAULT_ALL，使用defaults属性，可以组合

	        //设置通知ICON
	        //android 状态栏图标 http://blog.csdn.net/self_study/article/details/45667037
	        if (android.os.Build.VERSION.SDK_INT < 21)  
	        	mBuilder.setSmallIcon(R.mipmap.applogo);   //设置通知小ICON
	        else {  
	         	mBuilder.setSmallIcon(R.mipmap.applogo);   //设置通知小ICON
	        } 
	        mBuilder.setContentTitle("易购");
	        mBuilder.setTicker("登录过期，请重新登录"); //通知首次出现在通知栏，带上升动画效果的
	        Notification notify = mBuilder.build();
	        notify.flags |= Notification.FLAG_AUTO_CANCEL;
	        mNotificationManager.notify(notificationId, notify);
	}
	
}

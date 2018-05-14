package com.jlkf.ego.umanalytics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.jlkf.ego.utils.AppUtil;


/**
 * 友盟工具类
 * @author  邓超桂
 * @date 创建时间：2016年10月31日 上午10:02:17
 */
public class UMUtils {
//	官方申请的Appkey
	  public static final String UM_APPKEY = "582534d58f4a9d5251003b67";
//		渠道号
	  public static final String UM_CHANNELID = "";
	  //统计页面是直接由Activity组成的
	  public static void onResumeUMActivity(Context mContext,String pageName){
//		  MobclickAgent.onPageStart(pageName); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"pageName"为页面名称，可自定义)
//		  MobclickAgent.onResume(mContext);          //统计时长
	  }
	  public static void onPauseUMActivity(Context mContext,String pageName){
//		  MobclickAgent.onPageEnd(pageName); //（仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"pageName"为页面名称，可自定义
//		  MobclickAgent.onPause(mContext);          //统计时长
	  }
	  //统计页面是直接由FragmentActivity 组成的
	  public static void onResumeUMFragmentActivity(Context mContext){
//		  MobclickAgent.onResume(mContext);          //统计时长
	  }
	  public static void onPauseUMFragmentActivity(Context mContext){
//		  MobclickAgent.onPause(mContext);          //统计时长
	  }
	  //统计页面是Fragment 组成的
	  public static void onResumeUMFragment(String pageName){
//		  MobclickAgent.onPageStart(pageName); //统计页面，"pageName"为页面名称，可自定义
	  }
	  public static void onPauseUMFragment(String pageName){
//		  MobclickAgent.onPageEnd(pageName); //统计页面，"pageName"为页面名称，可自定义
	  }
	   
	  
/**
 * 自定义事件，统计发送内容      
 * @return
 */
	public static Map<String, String> getUMEventValue(Map<String, String> map){
		Map<String, String> map_value = new HashMap<String, String>();
		if(map!=null && !map.isEmpty()){
			map_value.putAll(map);
		}
		map_value.put("time", AppUtil.getNowTimeString());
		
		//将map统一整理为一条长记录
		StringBuffer sb=new StringBuffer();
		 for (Map.Entry<String, String> entry : map_value.entrySet()) {
			 sb.append(entry.getKey() + ":" + entry.getValue()+"  ");
		 }
		 Map<String, String> map_total = new HashMap<String, String>();
		 map_total.put("mData", sb.toString());
		 
		return map_total;
	}

public static boolean checkPermission(Context context, String permission) {
    boolean result = false;
    if (Build.VERSION.SDK_INT >= 23) {
        try {
            Class<?> clazz = Class.forName("android.content.Context");
            Method method = clazz.getMethod("checkSelfPermission", String.class);
            int rest = (Integer) method.invoke(context, permission);
            if (rest == PackageManager.PERMISSION_GRANTED) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }
    } else {
        PackageManager pm = context.getPackageManager();
        if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            result = true;
        }
    }
    return result;
}
public static String getDeviceInfo(Context context) {
    try {
        org.json.JSONObject json = new org.json.JSONObject();
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = null;
        if (checkPermission(context, permission.READ_PHONE_STATE)) {
            device_id = tm.getDeviceId();
        }
        String mac = null;
        FileReader fstream = null;
        try {
            fstream = new FileReader("/sys/class/net/wlan0/address");
        } catch (FileNotFoundException e) {
            fstream = new FileReader("/sys/class/net/eth0/address");
        }
        BufferedReader in = null;
        if (fstream != null) {
            try {
                in = new BufferedReader(fstream, 1024);
                mac = in.readLine();
            } catch (IOException e) {
            } finally {
                if (fstream != null) {
                    try {
                        fstream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        json.put("mac", mac);
        if (TextUtils.isEmpty(device_id)) {
            device_id = mac;
        }
        if (TextUtils.isEmpty(device_id)) {
            device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
        }
        json.put("device_id", device_id);
        return json.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
}


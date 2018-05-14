package com.jlkf.ego.widget.rom;

import java.io.IOException;
import java.lang.reflect.Method;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.jlkf.ego.BuildConfig;

/**
 * Created with IntelliJ IDEA.
 * **********************************
 * User: zs
 * Date: 2016年 07月 26日
 * Time: 下午2:01
 *
 * @QQ : 1234567890
 * **********************************
 */
public class AndroidRomUtil {

    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";


    /**
     * 华为rom
     * @return
     */
    public static boolean isEMUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_EMUI_VERSION_CODE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 小米rom
     * @return
     */
    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            /*String rom = "" + prop.getProperty(KEY_MIUI_VERSION_CODE, null) + prop.getProperty(KEY_MIUI_VERSION_NAME, null)+prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null);
           AppLog.Logd("Android_Rom", rom);*/
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                  || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                  || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 魅族rom
     * @return
     */
    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    /** 
     * 跳转到miui的权限管理页面 
     */  
    public static void gotoMiuiPermission(Context mContext) {  
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");  
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");  
        i.setComponent(componentName);  
        i.putExtra("extra_pkgname", mContext.getPackageName());  
        try {  
        	mContext.startActivity(i);  
        } catch (Exception e) {  
            e.printStackTrace();  
            gotoMeizuPermission(mContext);  
        }  
    }
    /** 
     * 跳转到魅族的权限管理系统 
     */  
    public static void gotoMeizuPermission(Context mContext) {  
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");  
        intent.addCategory(Intent.CATEGORY_DEFAULT);  
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {  
        	mContext.startActivity(intent);  
        } catch (Exception e) {  
            e.printStackTrace();  
            gotoHuaweiPermission(mContext);
        }  
    }
    
    /** 
     * 华为的权限管理页面 
     */  
    public static void gotoHuaweiPermission(Context mContext) {  
        try {  
            Intent intent = new Intent();  
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理  
            intent.setComponent(comp);  
            mContext.startActivity(intent);  	
        } catch (Exception e) {  
            e.printStackTrace();  
            mContext.startActivity(getAppDetailSettingIntent(mContext));  
        }  
    }
    /** 
     * 获取应用详情页面intent 
     * 
     * @return 
     */  
    public static Intent getAppDetailSettingIntent(Context mContext) {  
        Intent localIntent = new Intent();  
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        if (Build.VERSION.SDK_INT >= 9) {  
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");  
            localIntent.setData(Uri.fromParts("package",mContext.getPackageName(), null));  
        } else if (Build.VERSION.SDK_INT <= 8) {  
            localIntent.setAction(Intent.ACTION_VIEW);  
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");  
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());  
        }  
        return localIntent;  
    } 
    
	/**
	 * 打开系统设置
	 */
    public static void showDialogToSystemSetting(Context mContext) {
    	
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName(mContext)));
        mContext.startActivity(intent);
        
//    	mContext.startActivity(AndroidRomUtil.getAppDetailSettingIntent(mContext));
    	
//		if(AndroidRomUtil.isMIUI()){//小米
//			AndroidRomUtil.gotoMiuiPermission(mContext);
//		}else if(AndroidRomUtil.isEMUI()){//华为		
//			AndroidRomUtil.gotoHuaweiPermission(mContext);
//		}else if(AndroidRomUtil.isFlyme()){//魅族
//			AndroidRomUtil.gotoMeizuPermission(mContext);
//		}else{//普通手机
//			mContext.startActivity(AndroidRomUtil.getAppDetailSettingIntent(mContext));
//		}
	}
    
    private static String getPackageName(Context context) {  
        PackageManager pm = context.getPackageManager();  
        PackageInfo packageInfo = null;  
        try {  
             packageInfo = pm.getPackageInfo(context.getPackageName(), 0);  
        } catch (PackageManager.NameNotFoundException e) {  
            e.printStackTrace();  
        }  
        return packageInfo.packageName;  
    }  
    
}

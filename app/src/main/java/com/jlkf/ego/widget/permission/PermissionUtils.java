package com.jlkf.ego.widget.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * 获取权限的工具类
 * Created by 陈元旺 on 2016/9/27.
 */
public class PermissionUtils {
	/**
     * 从Activity检查权限
     */
    public static boolean checkPermission(final Activity activity, String permission, int hintTextId, int requestCode) {
        return checkPermission(activity, permission, activity.getString(hintTextId), requestCode);
    }
	/**
     * 从Activity检查多权限
     */
    public static boolean checkPermission(final Activity activity, String[] permission, int hintTextId, int requestCode) {
        return checkPermission(activity, permission, activity.getString(hintTextId), requestCode);
    }

    /**
     * 从Activity检查权限，调用从fragment检查权限的方法，fragment参数传入一个null
     */
    public static boolean checkPermission(final Activity activity, String permission, String hint, int requestCode) {
        return checkPermission(null, activity, permission, hint, requestCode);
    }
    /**
     * 从Activity检查多权限，调用从fragment检查权限的方法，fragment参数传入一个null
     */
    public static boolean checkPermission(final Activity activity, String[] permission, String hint, int requestCode) {
        return checkPermission(null, activity, permission, hint, requestCode);
    }
    
    /**
     * 从fragment检查权限
     *
     * @param fragment
     * @param activity
     * @param permission
     * @param hintTextId
     * @param requestCode
     * @return
     */
    public static boolean checkPermission(Fragment fragment, final Activity activity, String permission, int hintTextId, int requestCode) {
        return checkPermission(fragment, activity, permission, activity.getString(hintTextId), requestCode);
    }

    /**
     * 从fragment检查权限，如果传入的fragment是null，则后面请求权限时会调用ActivityCompat的方法
     * <p/>
     * 检查6.0及以上版本时，应用是否拥有某个权限，拥有则返回true，未拥有则再判断上次
     * 用户是否拒绝过该权限的申请（拒绝过则shouldShowRequestPermissionRationale返回true——这里有些手机如小米永远返回false
     * 这里的处理是弹一个对话框（用于提示用户是否去申请打开权限），返回false时这里执行
     * requestPermissions方法，此方法会显示系统默认的一个权限授权提示对话框，并在
     * Activity或Fragment的onRequestPermissionsResult得到回调，注意方法中的requestCode
     * 要与此处相同）
     *
     * @param fragment：如果fragment不为null则调用fragment的方法申请权限（因为有些手机上在Fragment调用ActivityCompat的 方法申请权限得不到回调，例如小米手机）
     * @param activity：用于弹出提示窗和获取权限
     * @param permission：对应的权限名称，如：Manifest.permission.CAMERA
     * @param hint：引导用户进入设置界面对话框的提示文字
     * @param requestCode：请求码，对应Activity或Fragment的onRequestPermissionsResult方法的requestCode
     * @return：true-拥有对应的权限 false：未拥有对应的权限
     */
    public static boolean checkPermission(Fragment fragment, final Activity activity, String permission, String hint, int requestCode) {
        //检查权限
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionRequestDialog(fragment,activity,permission,hint,requestCode);
            } else {
                //申请权限
                requestPermission(fragment,activity,permission,requestCode);
            }
            return false;
        } else {  //已经拥有权限
            return true;
        }
    }
    
    /**
     * 多权限申请
     */
	public static boolean checkPermission(Fragment fragment, final Activity activity, String[] permission, String hint, int requestCode) {
    	boolean isCheckPermission=false;
        //检查权限
    	for (int i = 0; i < permission.length; i++) {
			String strPermission=permission[i];
			if (ContextCompat.checkSelfPermission(activity, strPermission) != PackageManager.PERMISSION_GRANTED) {
				//只要有一个权限不行，直接获取重新申请需要全部权限
				if (ActivityCompat.shouldShowRequestPermissionRationale(activity, strPermission)) {
					showPermissionRequestDialog(fragment,activity,permission,hint,requestCode);
				} else {
					//申请权限
					requestPermission(fragment,activity,permission,requestCode);
				}
				isCheckPermission= false;
			} else {  //已经拥有权限
				isCheckPermission= true;
			}
		}
    	return isCheckPermission;
    }


    /**
     * 申请权限（fragment不为null时使用fragment的申请方法，避免得不到回调）
     * @param fragment
     * @param activity
     * @param permission
     * @param requestCode
     */
    public static void requestPermission(Fragment fragment, final Activity activity, String permission, int requestCode) {
        if (fragment == null) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    requestCode);
        } else {
            fragment.requestPermissions(
                    new String[]{permission},
                    requestCode);
        }
    }
    
    /**
     * 申请权限（fragment不为null时使用fragment的申请方法，避免得不到回调）
     * @param fragment
     * @param activity
     * @param permission
     * @param requestCode
     */
    public static void requestPermission(Fragment fragment, final Activity activity, String[] permission, int requestCode) {
        if (fragment == null) {
            ActivityCompat.requestPermissions(activity,permission,requestCode);	
        } else {
            fragment.requestPermissions(permission, requestCode);
        }
    }

    /**
     * 展示请求权限的对话框(用户按下"申请权限"按钮则去申请权限，否则取消申请)
     * @param fragment
     * @param activity
     * @param permission
     * @param hint
     * @param requestCode
     */
    public static void showPermissionRequestDialog(final Fragment fragment, final Activity activity, final String permission, String hint, final int requestCode) {
        new AlertDialog.Builder(activity)
                .setMessage(hint)
                .setPositiveButton("申请权限", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //申请权限
                        requestPermission(fragment,activity,permission,requestCode);
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
    
    public static void showPermissionRequestDialog(final Fragment fragment, final Activity activity, final String[] permission, String hint, final int requestCode) {
        new AlertDialog.Builder(activity)
                .setMessage(hint)
                .setPositiveButton("申请权限", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //申请权限
                        requestPermission(fragment,activity,permission,requestCode);
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    /**
     * 判断有无拍照权限（在6.0以下使用拥有root权限的管理软件可以管理权限）
     *
     * @return
     */
    public static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }
}

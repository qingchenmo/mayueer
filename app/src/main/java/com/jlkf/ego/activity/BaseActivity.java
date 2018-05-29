package com.jlkf.ego.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.jlkf.ego.R;
import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.UserBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.SharedPreferencesUtil;
import com.jlkf.ego.widget.CenterToast;
import com.jlkf.ego.widget.loading.LoadingDialog;
import com.jlkf.ego.widget.loading.MyLoadingDialog;
import com.jlkf.ego.widget.swipefinish.baseactivity.BaseSwipeFinishActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * activity的父类
 *
 * @author 李静
 */
public abstract class BaseActivity extends BaseSwipeFinishActivity {
    private Bundle bundle = null;
    public Context mContext;
    public Activity mActivity;
    public static Dialog dialog;
    public LoadingDialog loadingDialog;// 搜索时进度条
    public RequestManager mImageLoader;
    protected ProgressDialog waitDialog;
    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
//		initImageLoader(getApplicationContext());
        mImageLoader = initImageLoaders();
        mActivity = this;

        initView();
        initData();


        iniActivityAnimation();

        bundle = savedInstanceState;

        //因为Dialog依附Activity显示。如果当前窗口不是activity时，就会报错
        //android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@425546d8 is not valid; is your activity running?

//		//设置用户状态变更监听器，在回调中进行相应的处理
//		TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
//		    @Override
//		    public void onForceOffline() {
//		        //被踢下线
//		    	AppLog.Loge("被踢下线");
//		       	exitLogin("您的账号于另一台设备上登录。");
//		    }
//			@Override
//		    public void onUserSigExpired() {
//		        //票据过期，需要换票后重新登录
//				AppLog.Loge("票据过期，需要换票后重新登录");
//		      	exitLogin("身份票据已过期，请重新登录。");
//		    }
//		});

    }

    public String filterEmoji(String source) {
        String string = "";
        if (!containsEmoji(source)) {
            return source;// 如果不包含，直接返回
        }
        StringBuilder buf = null;
        int len = source.length();
        System.out.println("filter running len = " + len);
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (buf == null) {
                buf = new StringBuilder(source.length());
            }
            if (!isEmojiCharacter(codePoint)) {
                string = String.valueOf(codePoint);
            } else {
                try {
                    StringBuilder builder = new StringBuilder(2);
                    byte[] str = builder.append(String.valueOf(codePoint))
                            .append(String.valueOf(source.charAt(i+1)))
                            .toString().getBytes("UTF-8");
                    String strin = Arrays.toString(str);
                    String newString = strin.substring(1, strin.length() - 1);
                    string = "Γ"+newString+"Γ";
                    System.out.println("filters running newStr = " + string);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i++;
            }
            buf.append(string+"⅞");
        }
        if (buf == null) {
            return "";
        } else {
            if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                System.out.println("filter running buf.toString() = " + buf.toString());
                String bufStr = buf.toString();
                String newBufStr= bufStr.substring(0, bufStr.length() - 1);
                return newBufStr;
            }
        }
    }

    // 判别是否包含Emoji表情
    private boolean containsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }


    /**
     * 规则3：必须同时包含大小写字母及数字
     * 是否包含
     *
     * @param str
     * @return
     */
    public  boolean isContainAll(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i)) || Character.isUpperCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        if (isDigit && isLowerCase&& str.matches(regex)){
            return true;
        }
        return false;
    }


    private RequestManager initImageLoaders() {
        return Glide.with(this);
    }

    public void initData() {

    }


    public void loadGet(String url, Class t) {
        HttpUtil.getInstacne(mActivity).get(url, t, new HttpUtil.OnCallBack() {
            @Override
            public void success(Object data) {

            }

            @Override
            public void onError(String msg, int code) {

            }

        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        // imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    /**
     * 通过ID绑定UI
     **/
    public <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 获取临时保存数据
     *
     * @return
     */
    public Bundle getOnCreateBundle() {
        return bundle;
    }

    /**
     * 展示土司
     *
     * @param text   文本内容
     * @param isLong 1 为长时间 0 为短时间
     */
    public void showToast(String text, int isLong) {
        CenterToast.showToast(getApplicationContext(), text);
//		if (isLong == 1) {
//			Toast.makeText(this, text, Toast.LENGTH_LONG).show();
//		} else {
//			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//		}
    }


    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 初始化activity 动画
     */
    public abstract void iniActivityAnimation();

    /**
     * 弹出输入键盘
     *
     * @param mContext
     */
    public static void showInput(Activity mContext) {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘
     *
     * @param mContext
     */
    public static void hideInput(Activity mContext) {
        try {
            ((InputMethodManager) mContext
                    .getSystemService(mContext.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(mContext.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (RuntimeException re) {
            AppLog.Logi("dcg", "re==" + re);
        }
    }

    /**
     * 获取用户
     *
     * @return
     */
    public UserBean getUser() {
        return MyApplication.getmUserBean();
    }

    /**
     * 区域
     *
     * @return
     */
    public String getArea() {
        return getUser().getuTypeName();
    }

    /**
     * 判断是否联网的方法
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {

        //当使用测试用例，忽略网络判断
        SharedPreferencesUtil mCsvSPUtil = new SharedPreferencesUtil(context, "csv");
        boolean m_csv = (Boolean) mCsvSPUtil.getSPValue("csv", false);
        if (m_csv) {
            return true;
        }

        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void startActivity(Intent intent, Activity activity) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }


    public static void startActivityForResult(Intent intent, Activity activity, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    public static void finish(Activity activity) {
        hideInput(activity);
        activity.finish();
        activity.overridePendingTransition(R.anim.push_none, R.anim.push_up_out);
//		overridePendingTransition(R.anim.push_right_out,R.anim.push_left_in);
    }

    /**
     * 显示进度框
     */
    public void showProgressDialog() {
        if (loadingDialog == null) {
            loadingDialog = MyLoadingDialog.showDialogTypeOne(mContext);
        } else {
            loadingDialog.show();
        }
    }

    /**
     * 隐藏进度框
     */
    public void dissmissProgressDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
    public void setLoading(boolean isLoading) {
        try {
            if (isLoading) {
                if (waitDialog == null || !waitDialog.isShowing()) {
                    waitDialog = new ProgressDialog(this, R.style.MyDialogStyleBottom);
                    waitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    waitDialog.setCanceledOnTouchOutside(false);
                    ImageView view = new ImageView(this);
                    view.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    Animation loadAnimation = AnimationUtils.loadAnimation(
                            this, R.anim.rotate);
                    view.startAnimation(loadAnimation);
                    loadAnimation.start();
                    view.setImageResource(R.mipmap.loading);
                    waitDialog.show();
                    waitDialog.setContentView(view);
                }
            } else {
                if (waitDialog != null && waitDialog.isShowing()) {
                    waitDialog.dismiss();
                    waitDialog = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
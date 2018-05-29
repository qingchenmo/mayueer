package com.jlkf.ego.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.jlkf.ego.bean.BrandListBean;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.bean.UserBean;
import com.jlkf.ego.utils.AppLog;
import com.jlkf.ego.utils.LanguageUtils;
import com.jlkf.ego.utils.RefreshUtils;
import com.jlkf.ego.utils.ShardeUtil;
import com.jlkf.ego.utils.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;


/**
 * MyApplication
 *
 * @author 邓超桂
 * @date 创建时间：2016年11月3日 上午10:55:51
 */
public class MyApplication extends Application {

    public static String IP = "";
    public static String Http_IP = "";
    public static String Http_IMAGE = "";
    //服务协议Url
    public static String Http_HELP_SERVER = "";
    //APP的Logo使用于分享
    public static String Http_APP_LOGO = "";
    //APP外网分享页面Url
    public static String Http_APP_URL = "";
    //whois 页面 Url
    public static String Http_WHOIS_URL = "";

    public static String testServer = "http://old.5.hn";
    public static String onlioneServer = "https://a.yuxingqiu.com";
    public static boolean isOpenTestOnline = false;//测试环境和正式环境开关，关

    public static Context mContext;
    public static MyApplication mAppApplication;
    private List<BrandListBean> mBrandListBeen;
    public List<ShopCarGoodsBean> mGoodsBeen;

    public List<BrandListBean> getBrandListBeen() {
        return mBrandListBeen;
    }

    public void setBrandListBeen(List<BrandListBean> brandListBeen) {
        mBrandListBeen = brandListBeen;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static Handler mHandler;

    public static Context getmContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new RefreshUtils(this);
        mContext = getApplicationContext();
        mAppApplication = this;

        mHandler = new Handler();

        OkGo.getInstance().init(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .setRetryCount(3);


        //异常处理，注册异常捕捉
//	        CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
//			mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());

        initImageLoader(getApplicationContext());

        if (isOpenTestOnline) {
            JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        } else {
            JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        }
        JPushInterface.init(getApplicationContext());            // 初始化 JPush

        AppLog.Loge("MyApplication", "app oncreate");

        // 友盟debug
        Config.DEBUG = true;
        UMShareAPI.get(this);

        initThree();

        initUser();
        mGoodsBeen = new ArrayList<>();
        String language = ShardeUtil.getString("language");
        if (language != null && !language.isEmpty()) {
            LanguageUtils.switchLanguage(this, language);
        }
//        ClassicsHeader.REFRESH_HEADER_PULLDOWN = MyApplication.mAppApplication.getResources().getString(R.string.xlkysx);
//        ClassicsHeader.REFRESH_HEADER_REFRESHING = MyApplication.mAppApplication.getResources().getString(R.string.zzjz);
//        ClassicsHeader.REFRESH_HEADER_LOADING = MyApplication.mAppApplication.getResources().getString(R.string.zzjz);
//        ClassicsHeader.REFRESH_HEADER_RELEASE = MyApplication.mAppApplication.getResources().getString(R.string.ljsx);
//        ClassicsHeader.REFRESH_HEADER_FINISH = MyApplication.mAppApplication.getResources().getString(R.string.jzwc);
//        ClassicsHeader.REFRESH_HEADER_FAILED = MyApplication.mAppApplication.getResources().getString(R.string.sxsb);
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = MyApplication.mAppApplication.getResources().getString(R.string.scgx) + "M-d HH:mm";
//
//        ClassicsFooter.REFRESH_FOOTER_PULLUP = MyApplication.mAppApplication.getResources().getString(R.string.load_more);
//        ClassicsFooter.REFRESH_FOOTER_RELEASE = MyApplication.mAppApplication.getResources().getString(R.string.ljsx);
//        ClassicsFooter.REFRESH_FOOTER_REFRESHING = MyApplication.mAppApplication.getResources().getString(R.string.zzsx);
//        ClassicsFooter.REFRESH_FOOTER_LOADING = MyApplication.mAppApplication.getResources().getString(R.string.zzjz);
//        ClassicsFooter.REFRESH_FOOTER_FINISH = MyApplication.mAppApplication.getResources().getString(R.string.jzwc);
//        ClassicsFooter.REFRESH_FOOTER_FAILED = MyApplication.mAppApplication.getResources().getString(R.string.jzsb);
//        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = MyApplication.mAppApplication.getResources().getString(R.string.jzwc);
    }


    public static final String WEXIN = "d7a0c7057b9ce7212e7042eb178558f9";

    /**
     * 生成第三方
     */
    private void initThree() {
        PlatformConfig.setWeixin("wxfa65abfa013aafa1", WEXIN);
        PlatformConfig.setTwitter("87ITLiye7j030sdycKhdFAbuY", "IYTlWY6mLeAI5zoavhyN9j8RFT9TDVJEwWqB80a1phCRwWzYvf");

    }

    /**
     * 初始化用户状态  方便测试调式
     */
    private void initUser() {
        UserBean user = ShardeUtil.getUser();
        if (user != null) {
            setUserBean(user);
        }
    }

    public static UserBean getmUserBean() {

        return mUserBean;
    }

    private static UserBean mUserBean;

    public static void setUserBean(UserBean userBean) {
        mUserBean = userBean;
    }

    /**
     * 获取Application
     */
    public static MyApplication getApp() {
        return mAppApplication;
    }

    public static void initImageLoader(Context context) {
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
    }

    /******************处理IP***************************/
    public static String getHttp_IP() {
        Http_IP = getIP() + ":8080";
        return Http_IP;
    }

    public static String getHttp_IMAGE() {
        Http_IMAGE = getIP() + "/";
        return Http_IMAGE;
    }

    public static String getHttp_HELP_SERVER() {
        Http_HELP_SERVER = getIP() + ":8080/help/";
        return Http_HELP_SERVER;
    }

    public static String getHttp_APP_LOGO() {
        Http_APP_LOGO = getIP() + "/static/web/imgs/img7.png";
        return Http_APP_LOGO;
    }

    public static String getHttp_APP_URL() {
//			Http_APP_URL="http://www.5.hn/user/share?domainName=";
        Http_APP_URL = "https://a.yuxingqiu.com/user/share?domainName=";
        return Http_APP_URL;
    }

    public static String getHttp_WHOIS_URL() {
        Http_WHOIS_URL = getHttp_IP() + "/domain/whois?";
        return Http_WHOIS_URL;
    }

    public static String getIP() {
        //服务器的选择
        SharedPreferencesUtil mServerSPUtil = new SharedPreferencesUtil(mContext, "server");
        boolean m_server = (Boolean) mServerSPUtil.getSPValue("server", false);

//	        强行改为测试服务器
//	        m_server=false;

//		        强行改为正式服务器
        if (isOpenTestOnline) {
            m_server = true;
        }

        if (m_server) {
//	        	setIP("https://www.5.hn");
            setIP(onlioneServer);
        } else {
            setIP(testServer);
        }
        return IP;
    }

    public static void setIP(String iP) {
        IP = iP;
    }

    static {

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.darker_gray, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }
}
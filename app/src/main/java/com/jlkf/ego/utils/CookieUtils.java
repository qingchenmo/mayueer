package com.jlkf.ego.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

/**
 * Cookie处理工具类
 * @author  邓超桂
 * @date 创建时间：2016-8-12 下午3:46:46
 */
public class CookieUtils {
    private static List<Cookie> cookies;  
    
    public static List<Cookie> getCookies() {  
        return cookies != null ? cookies : new ArrayList<Cookie>();  
    }  
  
    public static void setCookies(List<Cookie> cookies) {  
    	CookieUtils.cookies = cookies;  
    } 
    
    public static  void saveCookie(AsyncHttpClient client,Context context) {  
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);  
        client.setCookieStore(cookieStore);  
    }  
    
    public static  void savePostCookie(DefaultHttpClient client,Context context) {  
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);  
        client.setCookieStore(cookieStore);  
    } 
  
    public static List<Cookie> getCookie(Context context){  
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);  
        List<Cookie> cookies = cookieStore.getCookies();  
        return cookies;  
    }  
      
    public static void clearCookie(Context context){  
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);  
        cookieStore.clear();  
    }  
    
    /** 
     * 获取标准 Cookie
     */  
    public static String getCookieText(Context context) {  
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);  
        List<Cookie> cookies = myCookieStore.getCookies();
        AppLog.Logd("cookie", "cookies.size() = " + cookies.size());  
        
        //在这里处理，如果cookies大于1，就进行清除
        if(cookies.size()>1){
        	clearCookie(context);
        }
        
        CookieUtils.setCookies(cookies); 
        for (Cookie cookie : cookies) {  
        	AppLog.Logd("cookie", cookie.getName() + " = " + cookie.getValue());  
        }  
         StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < cookies.size(); i++) {  
             Cookie cookie = cookies.get(i);  
             String cookieName = cookie.getName();  
             String cookieValue = cookie.getValue();  
            if (!TextUtils.isEmpty(cookieName)  
                    && !TextUtils.isEmpty(cookieValue)) {  
                sb.append(cookieName + "=");  
                sb.append(cookieValue + ";");  
            }  
        }  
        AppLog.Loge("cookie", sb.toString());  
        return sb.toString();  
    }
}


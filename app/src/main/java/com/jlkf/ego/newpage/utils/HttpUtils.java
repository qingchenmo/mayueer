package com.jlkf.ego.newpage.utils;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.jlkf.ego.newpage.bean.BaseBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor zcs
 * @time 2017/8/10
 * @describe 网络请求工具类, okGo
 */

public class HttpUtils<T> {
    private static HttpUtils sUtils;

    private HttpUtils() {

    }

    public static HttpUtils getInstance() {
        if (sUtils == null)
            sUtils = new HttpUtils();
        return sUtils;
    }

    public void get(String url, Map<String, String> map, Object tag, OnCallBack<T> callBack) {
        GetRequest request = OkGo.<String>get(url);
        request.cacheKey(url);
        request.cacheMode(CacheMode.NO_CACHE);
        request.params(map);
        request.tag(tag);
        request.execute(new StringCallBack(callBack, url));
    }

    public void getWithCache(String url, Map<String, String> map, Object tag, OnCallBack<T> callBack) {
        GetRequest request = OkGo.<String>get(url);
        request.cacheKey(url);
        request.cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST);
        request.params(map);
        request.tag(tag);
        request.execute(new StringCallBack(callBack, url));
    }

    public void post(String url, Map<String, Object> map, OnCallBack<T> callBack) {
        System.out.println("=========url=" + url);
        PostRequest request = OkGo.<String>post(url)
                .cacheKey(url);
        if (map != null && map.size() > 0) {
            for (String o : map.keySet()) {
                if (map.get(o) instanceof File) {
                    request.params(o, (File) map.get(o));
                } else {
                    request.params(o, (String) map.get(o));
                }
            }
        }
        request.cacheMode(CacheMode.NO_CACHE)
                .tag(url)
                .execute(new StringCallBack(callBack, url));
    }


    public void post(String url, Map<String, Object> map, Object tag, OnCallBack<T> callBack) {
        System.out.println("=========url=" + url);
        PostRequest request = OkGo.<String>post(url).tag(tag)
                .cacheMode(CacheMode.NO_CACHE)
                .cacheKey(url);
        if (map != null && map.size() > 0) {
            for (String o : map.keySet()) {
                if (map.get(o) instanceof File) {
                    request.params(o, (File) map.get(o));
                } else {
                    request.params(o, (String) map.get(o));
                }
            }
        }
        request.cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .tag(url)
                .execute(new StringCallBack(callBack, url));
    }

    public void postParm(String url, HashMap<String, String> params, Map<String, Object> map, Object tag, OnCallBack<T> callBack) {
        String param = url;
        if (params != null && params.size() > 0) {
            for (String msg : params.keySet()) {
                param += msg + "=" + params.get(msg) + "&";
            }
            param = param.substring(0, param.length() - 1);
        }
        PostRequest request = OkGo.<String>post(param).tag(tag)
                .cacheMode(CacheMode.NO_CACHE)
                .cacheKey(url);
        if (map != null && map.size() > 0) {
            for (String o : map.keySet()) {
                if (map.get(o) instanceof File) {
                    request.params(o, (File) map.get(o));
                } else {
                    request.params(o, (String) map.get(o));
                }
            }
        }
        request.cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .tag(url)
                .execute(new StringCallBack(callBack, url));
    }

    public void postWithToken(String url, Map<String, Object> map, OnCallBack<T> callBack) {
        PostRequest request = OkGo.<String>post(url)
//                .isMultipart(true)
                .cacheMode(CacheMode.NO_CACHE)
                .cacheKey(url);
        if (map != null && map.size() > 0) {
            for (String o : map.keySet()) {
                if (map.get(o) instanceof File) {
                    request.params(o, (File) map.get(o));
                } else {
                    request.params(o, (String) map.get(o));
                }
            }
        }
        request.cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .tag(url)
                .execute(new StringCallBack(callBack, url));
    }

    public void upLoad(String url, Map<String, File> map, OnCallBack<T> callBack) {
        Log.e("okhttp", "upload:-->" + url + map);
        PostRequest request = OkGo.<String>post(url)
                .isMultipart(true)
                .tag(url)
                .cacheKey(url);
        for (String str : map.keySet()) {
            request.params(str, map.get(str));
        }
        request.execute(new StringCallBack(callBack, url));
    }

    private class StringCallBack extends StringCallback {
        private Class<T> mClass;
        private OnCallBack<T> mCallBack;
        private String url;

        public StringCallBack(OnCallBack<T> callBack, String url) {
            mCallBack = callBack;
            this.url = url;
        }

        @Override
        public void onStart(Request<String, ? extends Request> request) {
            super.onStart(request);
        }

        @Override
        public void onSuccess(Response<String> response) {
            try {
                BaseBean bean = JSON.parseObject(response.body(), BaseBean.class);
                if (bean != null && (bean.getStatus() == 1 || bean.getCode() == 200))
                    mCallBack.success(bean.getData());
                else
                    mCallBack.onError(TextUtils.isEmpty(bean.getMessage()) ? bean.getMsg() : bean.getMessage());
            } catch (JSONException e) {
                mCallBack.onError("服务器错误");
            }
        }

        @Override
        public void onCacheSuccess(Response<String> response) {
            super.onCacheSuccess(response);
            try {
                BaseBean bean = JSON.parseObject(response.body(), BaseBean.class);
                if (bean != null && (bean.getStatus() == 1 || bean.getCode() == 200))
                    mCallBack.success(bean.getData());
                /*else
                    mCallBack.onError(TextUtils.isEmpty(bean.getMessage()) ? bean.getMsg() : bean.getMessage());*/
            } catch (JSONException e) {
//                mCallBack.onError("服务器错误");
            }
        }

        @Override
        public void onError(Response<String> response) {
            super.onError(response);
            Log.e("okgo", "onError: " + response.message());
            mCallBack.onError("网络请求失败");
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }
    }



    public interface OnCallBack<T> {
        void success(String response);

        void onError(String msg);
    }
}

package com.jlkf.ego.net;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jlkf.ego.R;
import com.jlkf.ego.bean.BaseHttpBean;
import com.jlkf.ego.bean.MyBaseBean;
import com.jlkf.ego.utils.ToastUti;
import com.jlkf.ego.utils.ToastUtil;
import com.jlkf.ego.utils.Uiutils;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/14 0014.
 * <p/>
 * 网络工具封装  加载中的dialog到时候也会加入到这里
 */

public class HttpUtil<T> {

    private static HttpUtil mHttpUtil;
    private KProgressHUD dialog;

    public HttpUtil() {
    }

    public void get(String url, Class t, OnCallBack<T> onCallBack) {
        OkGo.<String>get(url)
                .tag(this)
                .cacheKey(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback(url, t, onCallBack));

    }

    //
    public void get(String url, Class t, Map<String, String> map, OnCallBack onCallBack) {
        OkGo.<String>get(url)
                .tag(this)
                .params(map)
                .cacheKey(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback(url, t, onCallBack));
        Log.e(url+"参数",map.toString());

    }

    public void post(String url, Class t, JSONObject map, OnCallBack onCallBack) {
        OkGo.<String>post(url)
                .tag(this)
                .cacheKey(url)
                .upJson(map)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback(url, t, onCallBack));

    }

    public void post(String url, Class t, OnCallBack onCallBack) {
        OkGo.<String>post(url)
                .tag(this)
                .cacheKey(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback(url, t, onCallBack));

    }

    public void post(String url, Class t, JSONObject map, List<File> file, OnCallBack onCallBack) {
        OkGo.<String>post(url)
                .tag(this)
                .cacheKey(url)
                .addFileParams("upLogo", file)
                .upJson(map)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback(url, t, onCallBack));

    }

    public void post(String url, Class t, File file, OnCallBack onCallBack) {
        OkGo.<String>post(url)
                .tag(this)
                .cacheKey(url)
                .params("file", file)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback(url, t, onCallBack));
    }

    public void postFile(String url, Class t, JSONObject map, List<File> files, OnCallBack onCallBack) {
        PostRequest post = OkGo.<String>post(url);
        for (int i = 0; i < files.size(); i++) {
            post.params("files" + i, files.get(i));
        }
        post.tag(this)
                .cacheKey(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback2(url, t, onCallBack));

    }

    public void postFile(String url, Class t, List<File> files, OnCallBack onCallBack) {
        PostRequest post = OkGo.<String>post(url);
        for (int i = 0; i < files.size(); i++) {
            post.params("files" + i, files.get(i));
        }
        post.tag(this)
                .cacheKey(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback(url, t, onCallBack));

    }

    public void postFile3(String url, Class t, List<File> files, OnCallBack onCallBack) {
        PostRequest post = OkGo.<String>post(url);
        for (int i = 0; i < files.size(); i++) {
            post.params("files" + i, files.get(i));
        }
        post.tag(this)
                .cacheKey(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback3(url, t, onCallBack));

    }

    class jsonObjectCallback2 extends StringCallback {

        private Class<T> mClass;
        private OnCallBack<T> mCallBack;
        private String url;

        public jsonObjectCallback2(String url, Class<T> clazz, OnCallBack<T> callBack) {
            mClass = clazz;
            mCallBack = callBack;
            this.url = url;
        }


        @Override
        public void onStart(Request<String, ? extends Request> request) {
            super.onStart(request);
            Log.e("tag", request.getHeaders().toString() + "--->" + request.getParams().toString() + "---->");
            if (mActivity != null) {

                showDialog(mActivity);
            }
        }


        @Override
        public void onError(Response<String> response) {
            super.onError(response);
            Log.e("tag", "HttpDataError-->" + response.code());
        }

        @Override
        public void onFinish() {
            super.onFinish();
            dismissDialog();
        }


        @Override
        public void onSuccess(Response<String> response) {
        }
    }


    public void get(String url, Map<String, String> map, StringCallback onCallBack) {
        OkGo.<String>get(url)
                .tag(this)
                .params(map)
                .cacheKey(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(onCallBack);

    }


    public void showDialog(Activity activity) {
        if (dialog == null || !dialog.isShowing() && activity != null && !activity.isFinishing()) {
            dialog = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                    .setLabel("正在加载...")
//                    .setDetailsLabel("Downloading mData")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }
    }

    class jsonObjectCallback extends StringCallback {

        private Class<T> mClass;
        private OnCallBack<T> mCallBack;
        private String url;

        public jsonObjectCallback(String url, Class<T> clazz, OnCallBack<T> callBack) {
            this.mClass = clazz;
            this.mCallBack = callBack;
            this.url = url;
        }


        @Override
        public void onStart(Request<String, ? extends Request> request) {
            super.onStart(request);
            Log.e("tag", request.getHeaders().toString() + "--->" + request.getParams().toString() + "---->");
            if (mActivity != null) {

                showDialog(mActivity);
            }
        }


        @Override
        public void onError(Response<String> response) {
            super.onError(response);
            Log.e("tag", "HttpDataError-->" + response.code());
            ToastUti.show("网络连接失败，请检查网络再试");
        }

        @Override
        public void onFinish() {
            super.onFinish();
            dismissDialog();
        }


        @Override
        public void onSuccess(Response<String> response) {
            Log.e("tag", "HttpData-->" + url + response.body());
            BaseHttpBean bean = JSON.parseObject(response.body(), BaseHttpBean.class);
            if (bean.getCode() == 200 || bean.getCode() == 0) {
                if (bean.getData() != null) {
                    T t = null;
                    try {
                        t = JSON.parseObject(bean.getData().toString(), mClass);
                        mCallBack.success(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                        t = JSON.parseObject(bean.getData().toString(), mClass);
                        mCallBack.success(t);
                    }
                } else {
                    T t = JSON.parseObject(response.body(), mClass);
                    mCallBack.success(t);
                }
            } else {

                int id = 0;
                switch (bean.getCode()) {
                    case 10001:
                        id = R.string.dhwk;
                        break;
                    case 10002:
                        id = R.string.mmwk;
                        break;
                    case 10004:
                        id = R.string.mmcw;
                        break;
                    case 10005:
                        id = R.string.yhbcz;
                        break;
                    case 500:
                        id = R.string.yhbcz;
                        break;
                    case 10006:
                        id = R.string.dhwk;
                        break;
                    case 10007:
                        id = R.string.yzmwk;
                        break;
                    case 10008:
                        id = R.string.mmwk;
                        break;
                    case 10009:
                        id = R.string.yzmygq;
                        break;
                    case 10010:
                        id = R.string.yzmsrcw;
                        break;
                    case 10011:
                        id = R.string.xgmmsb;
                        break;
                    case 10012:
                        id = R.string.yhbcz;
                        break;
                    case 10086:
                        id = R.string.cslbwk;
                    case 404:
                        id = R.string.cslbwk;
                        break;
                    case 10013:
                        id = R.string.yhmcbnwk;
                        break;
                    case 10014:
                        id = R.string.dhwk;
                        break;
                    case 10015:
                        id = R.string.gsjhybbd;
                        break;
                    case 10016:
                        id = R.string.jmmwk;
                        break;
                    case 10017:
                        id = R.string.xmmwk;
                        break;
                    case 10018:
                        id = R.string.zcqrmmwk;
                        break;
                    case 10019:
                        id = R.string.yhidwk;
                        break;
                    case 10020:
                        id = R.string.jmmbzq;
                        break;
                    case 10021:
                        id = R.string.lcmmbyz;
                        break;
                    case 11000:
                        id = R.string.dhwk;
                        break;
                    case 12000:
                        id = R.string.mmbnwk;
                        break;
                    case 13000:
                        id = R.string.yzmbnwk;
                        break;
                    case 14000:
                        id = R.string.yhyzc;
                        break;
                }

                try {
                    String getstring = Uiutils.getstring(id);
                    if (!TextUtils.isEmpty(getstring)) {
                        ToastUtil.show(getstring);
                    }
                    mCallBack.onError(bean.getMsg(), bean.getCode());
                } catch (Exception e) {
                    e.printStackTrace();
                    mCallBack.onError(bean.getMsg(), bean.getCode());
                }

            }
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing() && !mActivity.isFinishing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {

            }
            dialog = null;
        }
        if (mActivity.isFinishing()) dialog = null;
    }


    public interface OnCallBack<T> {
        void success(T data);

        void onError(String msg, int code);
    }

    private static Activity mActivity;

    public static HttpUtil getInstacne(Activity activity) {
        mActivity = activity;
        if (mHttpUtil == null) {
            synchronized (HttpUtil.class) {
                if (mHttpUtil == null) {
                    mHttpUtil = new HttpUtil();
                }
            }
        }
        return mHttpUtil;
    }

    public void get2(String url, Class t, Map<String, String> map, OnCallBack onCallBack) {
        Log.e("tag", url + map);
        OkGo.<String>get(url)
                .tag(this)
                .params(map)
                .cacheKey(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback3(url, t, onCallBack));
    }

    public void post2(String url, Class t, String json, OnCallBack onCallBack) {
        Log.e("tag", url + json);
        OkGo.<String>post(url)
                .tag(this)
                .cacheKey(url)
                .upJson(json)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new jsonObjectCallback3(url, t, onCallBack));

    }


    class jsonObjectCallback3 extends StringCallback {

        private Class<T> mClass;
        private OnCallBack<T> mCallBack;
        private String url;

        public jsonObjectCallback3(String url, Class<T> clazz, OnCallBack<T> callBack) {
            this.mClass = clazz;
            this.mCallBack = callBack;
            this.url = url;
        }


        @Override
        public void onStart(Request<String, ? extends Request> request) {
            super.onStart(request);
            Log.e("tag", request.getHeaders().toString() + "--->" + request.getParams().toString() + "---->");
            if (mActivity != null && !url.equals(Urls.coller) && !url.equals(Urls.shopInsert)) {
                showDialog(mActivity);
            }
        }


        @Override
        public void onError(Response<String> response) {
            super.onError(response);
//            mCallBack.onError("网络连接失败，请检查网络再试", 1);
            mCallBack.onError("", 1);
            Log.e("tag", "HttpDataError-->" + response.code());
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (mActivity != null && !url.equals(Urls.coller)) {
                dismissDialog();
            }
        }


        @Override
        public void onSuccess(Response<String> response) {
            Log.e("tag", "HttpData-->" + url + response.body());
            if (url.equals(Urls.getOitmViewByBrand)) {
            }
            if (MyBaseBean.class.isAssignableFrom(mClass)) {
                T t = JSON.parseObject(response.body(), mClass);
                MyBaseBean bean = (MyBaseBean) t;

                if (bean != null && (bean.getCode() == 200 || bean.getCode() == 0)) {
                    mCallBack.success(t);
                } else if (bean == null) {
                    Log.e("tag", "接口返回数据有问题");
//                    mCallBack.onError("接口返回数据有问题", bean.getCode());
                } else {
                    mCallBack.onError(bean.getMsg(), bean.getCode());
                }
            }
        }
    }

}

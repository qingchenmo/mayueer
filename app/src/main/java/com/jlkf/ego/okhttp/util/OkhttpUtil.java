package com.jlkf.ego.okhttp.util;

import com.jlkf.ego.okhttp.callback.MainCallBack;
import com.jlkf.ego.okhttp.handler.CallBackHandler;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * author: zcs
 * description:okhttp工具类
 * time: 2017/2/21 14:15
 */
public class OkhttpUtil {
    private static OkHttpClient mHttpClient;

    /**
     * @param url
     * @param map
     * @param callBack
     * @param requestCode
     */
    public static <T> void post(String url, Map<String, String> map, MainCallBack<T> callBack, int requestCode, Class aClass) {
        if (mHttpClient == null) mHttpClient = new OkHttpClient();
        mHttpClient = mHttpClient.newBuilder().writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null && map.size() > 0) {
            for (String str : map.keySet()) {
                builder.add(str, map.get(str));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        mHttpClient.newCall(request).enqueue(new CallBackHandler<T>(callBack, requestCode, aClass));
    }

    /**
     * @param url
     * @param map
     * @param callBack
     * @param requestCode
     */
    public static <T> void upLoad(String url, Map<String, Object> map, MainCallBack<T> callBack, int requestCode, Class<T> aClass) {
        if (mHttpClient == null) mHttpClient = new OkHttpClient();
        mHttpClient = mHttpClient.newBuilder().writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (String str : map.keySet()) {
            Object o = map.get(str);
            if (o instanceof List) {
                List<File> files = (List<File>) o;
                int size = files.size();
                for (int i = 0; i < size; i++) {
                    if (files.get(i) == null) continue;
                    builder.addFormDataPart(str, files.get(i).getName(), RequestBody.create(MediaType.parse("image/*"), files.get(i)));
                }
            } else {
                builder.addFormDataPart(str, (String) o);
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        mHttpClient.newCall(request).enqueue(new CallBackHandler<T>(callBack, requestCode, aClass));
    }
}

package com.jlkf.ego.okhttp.handler;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jlkf.ego.okhttp.callback.MainCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author: zcs
 * description:将网络请求结果放入主线程中回调
 * time: 2017/2/7 14:42
 */
public class CallBackHandler<T> implements Callback {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private MainCallBack mMainCallBack;
    private int requestCode;
    private Class mClass;

    public CallBackHandler(MainCallBack mainCallBack, int requestCode, Class aClass) {
        this.mMainCallBack = mainCallBack;
        this.requestCode = requestCode;
        this.mClass = aClass;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        final String errorMsg = e.getMessage();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMainCallBack.onFailure(errorMsg, false, requestCode);
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        final String result = response.body().string();
        Log.e("tag", result);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                formatResult(result);
            }
        });
    }

    /**
     * 用于按统一格式管理返回结果
     *
     * @param result
     * @return
     * @throws JSONException
     */
    private void formatResult(String result) {
        try {
            JSONObject object = new JSONObject(result);
            int code = object.getInt("code");
            if (code == -1) {
                mMainCallBack.onFailure(object.getString("msg"), true, requestCode);
            } else if (code == 0) {
                String s = object.getString("mData");
                if (s.startsWith("[")) {
                    ArrayList<T> list = (ArrayList<T>) JSON.parseArray(s, mClass);
                    mMainCallBack.onSuccess(list, requestCode);
                } else {
                    T t = (T) JSON.parseObject(s, mClass);
                    mMainCallBack.onSuccess(t, requestCode);
                }
            } else {
                mMainCallBack.onFailure("数据错误", false, requestCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mMainCallBack.onFailure("数据解析错误", false, requestCode);
        }

    }
}

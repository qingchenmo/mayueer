package com.jlkf.ego.okhttp.callback;

/**
 * author: zcs
 * description:回调方法，用于在主线程获取回调结果
 * time: 2017/2/7 14:36
 */
public interface MainCallBack<T> {
    /**
     * 网络访问失败
     *
     * @param error       失败信息String
     * @param isShow      是否应该展示的错误信息
     * @param requestCode 网络访问请求码，用于区分多次请求
     */
    void onFailure(String error, boolean isShow, int requestCode);

    /**
     * 网络访问成功
     *
     * @param result      网络访问获取结果
     * @param requestCode 网络访问请求码，用于区分多次请求
     */
    void onSuccess(T result, int requestCode);
}

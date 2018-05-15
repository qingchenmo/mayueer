package com.jlkf.ego.newpage.inter;

/**
 * Created by zcs on 2018/5/15.
 *
 * @describe: 列表item点击事件
 */

public interface OnItemClickListener<T> {
    void itemClickListener(T t, int position);
}

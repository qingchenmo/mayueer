package com.jlkf.ego.utils;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.jlkf.ego.bean.MyBaseBean;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.net.HttpUtil;
import com.jlkf.ego.net.Urls;

import java.util.ArrayList;
import java.util.List;

import static com.jlkf.ego.utils.ShardeUtil.getUser;

/**
 * @autor zcs
 * @time 2017/8/23
 * @describe 购物车工具类
 */

public class ShopUtils {

    public static ShopUtils sUtils;
    private List<String> mList;
    public static final int ADD = 1;
    public static final int DELETE = 2;
    public static final int EDIT = 3;
    private int mType;

    public List<String> getList() {
        return mList;
    }

    private ShopUtils() {

    }

    public static ShopUtils getUtils() {
        if (sUtils == null)
            sUtils = new ShopUtils();
        return sUtils;
    }

    /**
     * 设置购物车列表数据，以便对购物车列表进行增删改查
     */
    public void setList() {
        mList = new ArrayList<>();
    }

    public void editList(ProductListBean.DataBean bean, Activity activity) {
        int size = mList.size();
        mType=ADD;
        for (int i = 0; i < size; i++) {
            if (bean.getItemcode().equals(mList.get(i)) && bean.getSelectNum() == 0) {
                mType = DELETE;
                break;
            } else if (bean.getItemcode().equals(mList.get(i)) && bean.getSelectNum() != 0) {
                mType = EDIT;
                break;
            } else {
                mType = ADD;
                break;
            }
        }
        requestHttp(bean, activity);

    }

    private void requestHttp(final ProductListBean.DataBean bean, Activity activity) {
        JSONObject object = new JSONObject();
        String url = null;
        switch (mType) {
            case ADD:
                url = Urls.insert;
                object.put("name", bean.getItemname());
                object.put("price", bean.getPrice());
                object.put("logo", bean.getPicturname());
                object.put("itemcode", bean.getItemcode());
                object.put("quantity", "10");
                object.put("codebars", bean.getCodebars());
                object.put("brandname", bean.getpName());
                object.put("brandId", bean.getUPp());
                object.put("uId", getUser().getUId() + "");
                break;
            case DELETE:
                url = Urls.update;
                ToastUti.show("heheheh");
                break;
            case EDIT:
                url = Urls.update;
                object.put("sId", bean.getOitmId());
                object.put("price", bean.getPrice());
                object.put("logo", bean.getPicturname());
                object.put("itemcode", bean.getItemcode());
                object.put("quantity", "10");
                object.put("codebars", bean.getCodebars());
                object.put("brandname", bean.getpName());
                object.put("brandId", bean.getUPp());
                break;
        }
        HttpUtil.getInstacne(activity).post2(url, MyBaseBean.class, object.toString(), new HttpUtil.OnCallBack<MyBaseBean>() {
            @Override
            public void success(MyBaseBean data) {
                if (mType == ADD) mList.add(bean.getItemcode());
            }

            @Override
            public void onError(String msg, int code) {

            }
        });

    }


}

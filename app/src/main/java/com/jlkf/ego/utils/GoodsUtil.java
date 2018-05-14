package com.jlkf.ego.utils;

import com.google.gson.Gson;
import com.jlkf.ego.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22 0022.
 * <p>
 * 购物车
 */

public class GoodsUtil {

    private static GoodsUtil mShoppingCat;

    public void setGoodsBeens(List<GoodsBean.ShopcartBean> goodsBeens) {
        mGoodsBeens = goodsBeens;
    }

    private List<GoodsBean.ShopcartBean> mGoodsBeens;

    private GoodsUtil() {
        initData();
    }

    private void initData() {

        if (mGoodsBeens == null) {
            mGoodsBeens = new ArrayList();
        }
    }

    public static GoodsUtil getInstance() {
        if (mShoppingCat == null) {
            synchronized (GoodsUtil.class) {
                if (mShoppingCat == null) {
                    mShoppingCat = new GoodsUtil();
                }
            }
        }
        return mShoppingCat;
    }


    public void addGoods(GoodsBean.ShopcartBean data) {
        mGoodsBeens.add(data);

        save();
    }

    public void delet(GoodsBean data) {
        mGoodsBeens.remove(data);

        save();
    }


    /**
     * 保存在本地
     */
    private void save() {
        Gson gson = new Gson();


    }
}

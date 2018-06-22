package com.jlkf.ego.newpage.utils;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlkf.ego.application.MyApplication;
import com.jlkf.ego.bean.ProductListBean;
import com.jlkf.ego.bean.ShopCarGoodsBean;
import com.jlkf.ego.utils.ProductAddShopCarUtils;
import com.jlkf.ego.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 加入赠品工具类
 */

public class GiftAddUtils {
    private GiftAddUtils() {
    }

    private static GiftAddUtils mInstance;

    public static GiftAddUtils getInstance() {
        if (mInstance == null) {
            mInstance = new GiftAddUtils();
        }
        return mInstance;
    }

    private List<ShopCarGoodsBean> mGoodsBeen = new ArrayList<>();

    public List<ShopCarGoodsBean> getGoodsBeen() {
        return mGoodsBeen;
    }

    public void EditShopCar(boolean add, final ProductListBean.DataBean info, final TextView etSelectNum) {
        if (add) {
            if (info.getSelectNum() >= Integer.parseInt(info.getOnhand())) {
                ToastUtil.show("库存不足");
                return;
            }
            //判断是否要通过包增加
            if (info.isLargePackage()) {
                info.setSelectNum(info.getSelectNum() + Integer.parseInt(info.getUUX()));
            } else if (info.isSmallPackage()) {
                info.setSelectNum(info.getSelectNum() + Integer.parseInt(info.getUUB()));
            } else {
                info.setSelectNum(info.getSelectNum() + 1);
            }
            if (info.getSelectNum() > Integer.parseInt(info.getOnhand())) {
                ToastUtil.show("库存不足");
                info.setSelectNum(Integer.parseInt(info.getOnhand()));
                etSelectNum.setText(String.valueOf(info.getSelectNum()));
            } else {
                etSelectNum.setText(String.valueOf(info.getSelectNum()));
            }
        } else {
            if (info.getSelectNum() == 0) {
                return;
            }
            if (info.isLargePackage()) {
                info.setSelectNum(info.getSelectNum() - Integer.parseInt(info.getUUX()));
            } else if (info.isSmallPackage()) {
                info.setSelectNum(info.getSelectNum() - Integer.parseInt(info.getUUB()));
            } else {
                info.setSelectNum(info.getSelectNum() - 1);
            }
            if (info.getSelectNum() < 0) info.setSelectNum(0);
            etSelectNum.setText(String.valueOf(info.getSelectNum()));
        }
        ShopCarGoodsBean bean = new ShopCarGoodsBean();
        bean.setNum(info.getSelectNum());
        bean.setGoodsCode(info.getOitmId());
        try {
            bean.setPrice(Double.valueOf(info.getPrice()));
        } catch (Exception e) {
            bean.setPrice(0);
        }
        statisShopNum(bean);
    }

    /**
     * 编辑购物车中的商品信息
     *
     * @param bean
     */
    public void statisShopNum(ShopCarGoodsBean bean) {
        int size = mGoodsBeen.size();
        int position = -1;
        boolean isNeedAdd = true;
        for (int i = 0; i < size; i++) {
            ShopCarGoodsBean goodsBean = mGoodsBeen.get(i);
            if (bean.getGoodsCode().equals(goodsBean.getGoodsCode())) {
                isNeedAdd = false;
                goodsBean.setNum(bean.getNum());
                if (goodsBean.getNum() == 0) {
                    position = i;
                }
            }

        }
        if (isNeedAdd) {
            mGoodsBeen.add(bean);
        }
        if (position != -1) {
            mGoodsBeen.remove(position);
        }
    }

    /**
     * 获取商品在购物车中的数量
     *
     * @param itemCode
     * @return
     */
    public int getGoodsNumInShopCar(String itemCode) {
        int size = mGoodsBeen.size();
        for (int i = 0; i < size; i++) {
            ShopCarGoodsBean bean = mGoodsBeen.get(i);
            if (bean.getGoodsCode().equals(itemCode)) {
                return bean.getNum();
            }
        }
        return 0;
    }

    public double getGiftPrice() {
        int size = mGoodsBeen.size();
        double money = 0;
        for (int i = 0; i < size; i++) {
            ShopCarGoodsBean bean = mGoodsBeen.get(i);
            money = money + bean.getPrice() * bean.getNum();
        }
        return money;
    }
}
